open RawAst
open Ast

let binop_name (bop : RawAst.bop) =
  match bop with
  | Add  -> "add"
  | Sub  -> "sub"
  | Mult -> "mult"
  | Div  -> "div"
  | And  -> "and"
  | Or   -> "or"
  | Eq   -> "eq"
  | Neq  -> "neq"
  | Leq  -> "leq"
  | Lt   -> "lt"
  | Geq  -> "geq"
  | Gt   -> "gt"

module Fresh = struct
  let counter = ref 0
  let next () = let n = !counter in incr counter; n
end

module StrMap = Map.Make (String)

type env = int StrMap.t
let empty_env = StrMap.empty
let add_env x id env = StrMap.add x id env
let find_env x env = StrMap.find_opt x env

let rec simplify_env (env : env) (e : RawAst.expr) : Ast.expr =
  match e.data with
  | Unit   -> Unit
  | Int  n -> Int n
  | Bool b -> Bool b

  | Var  x ->
      (match find_env x env with
       | Some id -> Var id
       | None -> failwith ("unbound variable: " ^ x))

  | Binop(bop, e1, e2) ->
      App ( App (Builtin (binop_name bop), simplify_env env e1),
             simplify_env env e2 )

  | If(b, e1, e2)  ->
      If (simplify_env env b, simplify_env env e1, simplify_env env e2)

  | Let(x, e1, e2) ->
      let id   = Fresh.next () in
      let e1'  = simplify_env env e1 in
      let env' = add_env x id env in
      let e2'  = simplify_env env' e2 in
      Let (id, e1', e2')

  | Pair(e1, e2) ->
      Pair (simplify_env env e1, simplify_env env e2)

  | App(e1, e2) ->
      App (simplify_env env e1, simplify_env env e2)

  | Fst e ->
      App (Builtin "fst", simplify_env env e)

  | Snd e ->
      App (Builtin "snd", simplify_env env e)

  | Fun(x, _tp, e) ->
      let id   = Fresh.next () in
      let env' = add_env x id env in
      let body = simplify_env env' e in
      Fun (id, body)

  | Funrec(f, x, _tp1, _tp2, e) ->
      let fid = Fresh.next () in  (* identyfikator funkcji *)
      let xid = Fresh.next () in  (* identyfikator parametru *)
      let env' = env |> add_env f fid |> add_env x xid in
      let body = simplify_env env' e in
      Funrec (fid, xid, body)

(* ------------------------------------------------------------------- *)
(*  API widoczne z zewnątrz – zachowujemy stary podpis simplify : expr *)
(* ------------------------------------------------------------------- *)
let simplify (e : RawAst.expr) : Ast.expr =
  simplify_env empty_env e
