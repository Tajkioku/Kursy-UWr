(* compile.ml: integracja statycznego sprawdzania typów *)
open Ast
open Interp    (* dla parse *)
open Rpn       (* dla of_ast, optimize *)
open Emit      (* dla emit *)
open Runtime   (* dla with_runtime *)

(* --- Proste sprawdzanie typów zgarniające wiele błędów --- *)
module Typecheck = struct
  type error = string
  type env   = (ident * value_type) list

  let string_of_vtype = function
    | TInt  -> "int"  | TBool -> "bool"
    | TUnit -> "unit" | TPair -> "pair"
    | TFun  -> "fun"

  let lookup env x =
    try Some (List.assoc x env)
    with Not_found -> None

  let check env expr =
    let rec aux env e =
      match e with
      | Int _    -> (TInt, [])
      | Bool _   -> (TBool, [])
      | Unit     -> (TUnit, [])
      | Var x    ->
          (match lookup env x with
           | Some t -> (t, [])
           | None   -> (TUnit, ["Unbound variable: " ^ x]))
      | Binop (op, e1, e2) ->
          let (t1, errs1) = aux env e1 in
          let (t2, errs2) = aux env e2 in
          let base_errs = errs1 @ errs2 in
          let add_err cond msg = if not cond then [msg] else [] in
          let (tret, op_errs) = match op with
            | Add|Sub|Mult|Div ->
                (TInt,
                 add_err (t1=TInt) "Left of arithmetic must be int"
               @ add_err (t2=TInt) "Right of arithmetic must be int")
            | And|Or ->
                (TBool,
                 add_err (t1=TBool) "Left of logical must be bool"
               @ add_err (t2=TBool) "Right of logical must be bool")
            | Eq|Neq ->
                (TBool,
                 add_err (t1=t2)
                   ("Equality between incompatible types: "
                    ^ string_of_vtype t1 ^ " vs " ^ string_of_vtype t2))
            | Leq|Lt|Geq|Gt ->
                (TBool,
                 add_err (t1=TInt) "Left of comparison must be int"
               @ add_err (t2=TInt) "Right of comparison must be int")
          in
          (tret, base_errs @ op_errs)
      | If (c, tb, eb) ->
          let (tc, ec) = aux env c in
          let (tt, et) = aux env tb in
          let (te, ee) = aux env eb in
          let cond_err = if tc<>TBool then ["Condition must be bool"] else [] in
          let br_err   = if tt<>te
                         then ["Branches have different types: "
                                ^ string_of_vtype tt ^ " vs " ^ string_of_vtype te]
                         else [] in
          (tt, ec @ et @ ee @ cond_err @ br_err)
      | Let (x, e1, e2) ->
          let (t1, e1s) = aux env e1 in
          let (t2, e2s) = aux ((x,t1)::env) e2 in
          (t2, e1s @ e2s)
      | Pair (l, r) ->
          let (_, el) = aux env l in
          let (_, er) = aux env r in
          (TPair, el @ er)
      | Fst e ->
          let (t, es) = aux env e in
          let fst_err = if t<>TPair then ["fst expects pair"] else [] in
          (TUnit, es @ fst_err)
      | Snd e ->
          let (t, es) = aux env e in
          let snd_err = if t<>TPair then ["snd expects pair"] else [] in
          (TUnit, es @ snd_err)
      | Is (_, e) ->
          let (_, es) = aux env e in
          (TBool, es)
      | Fun _|Funrec _|App _ ->
          (TFun, [])  (* sprawdzanie dynamiczne *)
    in aux env expr
end

let compile (s : string) : string =
  (* 1. parsowanie *)
  let ast = parse s in
  (* 2. statyczne sprawdzanie typów *)
  let errs = snd (Typecheck.check [] ast) in
  if errs <> [] then begin
    List.iter (fun msg -> Printf.eprintf "Type error: %s\n" msg) errs;
    exit 1
  end;
  (* 3. reszta kompilacji *)
  ast
  |> of_ast []
  |> optimize
  |> emit
  |> with_runtime
