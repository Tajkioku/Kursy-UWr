open RawAst

(* Zad 1 *)
exception Type_error  of (Lexing.position * Lexing.position) * string
(* Zad 2 *)
exception Type_errors of ((Lexing.position * Lexing.position) * string) list

(* Pomocnicza funkcja do wypisywania *)
let rec string_of_typ = function
  | TUnit -> "unit"
  | TInt  -> "int"
  | TBool -> "bool"
  | TPair(t1, t2) -> "(" ^ string_of_typ t1 ^ " * " ^ string_of_typ t2 ^ ")"
  | TArrow(t1, t2) -> "(" ^ string_of_typ t1 ^ " -> " ^ string_of_typ t2 ^ ")"

module Env = struct
  module StrMap = Map.Make(String)
  type t = typ StrMap.t

  let initial = StrMap.empty
  
  let add_var env x tp = StrMap.add x tp env
  
  let lookup_var env x = StrMap.find_opt x env
end

type error_info = (Lexing.position * Lexing.position) * string

let rec infer (env : Env.t) (e : expr) : typ * error_info list =
  match e.data with
  | Unit      -> (TUnit, [])
  | Int _     -> (TInt,  [])
  | Bool _    -> (TBool, [])

  (* Zad 1 *)
  | Var x ->
      begin match Env.lookup_var env x with
      | Some tp -> (tp, [])
      | None    -> raise (Type_error (e.pos, Printf.sprintf "Unbound variable %s" x))
      end

  (* Zad 2 *)
  | Binop((Add|Sub|Mult|Div), e1, e2) ->
      let errs = check_type env e1 TInt @ check_type env e2 TInt in
      (TInt, errs)

  | Binop((And|Or), e1, e2) ->
      let errs = check_type env e1 TBool @ check_type env e2 TBool in
      (TBool, errs)

  | Binop((Leq|Lt|Geq|Gt), e1, e2) ->
      let errs = check_type env e1 TInt @ check_type env e2 TInt in
      (TBool, errs)

  | Binop((Eq|Neq), e1, e2) ->
      let (tp1, errs1) = infer env e1 in
      let errs2 = check_type env e2 tp1 in
      (TBool, errs1 @ errs2)

  | If(b, e1, e2) ->
      let errs_b       = check_type env b TBool in
      let (tp1, errs1) = infer env e1 in
      let errs2        = check_type env e2 tp1 in
      (tp1, errs_b @ errs1 @ errs2)

  | Let(x, e1, e2) ->
      let (tp1, errs1) = infer env e1 in
      let env' = Env.add_var env x tp1 in
      let (tp2, errs2) = infer env' e2 in
      (tp2, errs1 @ errs2)

  | Pair(e1, e2) ->
      let (tp1, errs1) = infer env e1 in
      let (tp2, errs2) = infer env e2 in
      (TPair(tp1, tp2), errs1 @ errs2)

  (* Zad 1 *)
  | App(e1, e2) ->
      let (tp_fun, errs1) = infer env e1 in
      begin match tp_fun with
      | TArrow(arg_tp, res_tp) ->
          let errs2 = check_type env e2 arg_tp in
          (res_tp, errs1 @ errs2)
      | tp_non_fun ->
          raise (Type_error (e.pos,
                  Printf.sprintf "Cannot apply value of type %s (expected function)"
                    (string_of_typ tp_non_fun)))
      end

  | Fst e1 ->
      begin match infer env e1 with
      | (TPair(tp, _), errs) -> (tp, errs)
      | (tp_non_pair, _) ->
          raise (Type_error (e.pos,
                  Printf.sprintf "fst expects pair but got %s" (string_of_typ tp_non_pair)))
      end

  | Snd e1 ->
      begin match infer env e1 with
      | (TPair(_, tp), errs) -> (tp, errs)
      | (tp_non_pair, _) ->
          raise (Type_error (e.pos,
                  Printf.sprintf "snd expects pair but got %s" (string_of_typ tp_non_pair)))
      end

  | Fun(x, tp1, body) ->
      let env' = Env.add_var env x tp1 in
      let (tp2, errs) = infer env' body in
      (TArrow(tp1, tp2), errs)

  | Funrec(f, x, tp1, tp2, body) ->
      let env1 = Env.add_var env f (TArrow(tp1, tp2)) in
      let env2 = Env.add_var env1 x tp1 in
      let errs = check_type env2 body tp2 in
      (TArrow(tp1, tp2), errs)

and check_type env e expected : error_info list =
  let (actual, errs_sub) = infer env e in
  if actual = expected then errs_sub
  else
    let msg = Printf.sprintf "Type mismatch: expected %s but got %s"
                (string_of_typ expected) (string_of_typ actual) in
    (e.pos, msg) :: errs_sub

let check_program e =
  let (_tp, errs) = infer Env.initial e in
  if errs <> [] then raise (Type_errors errs);
  e
