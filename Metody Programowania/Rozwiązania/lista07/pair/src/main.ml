open Ast

let parse (s : string) : expr =
  Parser.main Lexer.read (Lexing.from_string s)

type value =
  | VInt of int
  | VBool of bool
  | VUnit
  | VPair of value * value

let eval_op (op : bop) (val1 : value) (val2 : value) : value =
  match op, val1, val2 with
  | Add,  VInt  v1, VInt  v2 -> VInt  (v1 + v2)
  | Sub,  VInt  v1, VInt  v2 -> VInt  (v1 - v2)
  | Mult, VInt  v1, VInt  v2 -> VInt  (v1 * v2)
  | Div,  VInt  v1, VInt  v2 -> VInt  (v1 / v2)
  | And,  VBool v1, VBool v2 -> VBool (v1 && v2)
  | Or,   VBool v1, VBool v2 -> VBool (v1 || v2)
  | Leq,  VInt  v1, VInt  v2 -> VBool (v1 <= v2)
  | Eq,   _,        _        -> VBool (val1 = val2)
  | _,    _,        _        -> failwith "type error"

let rec subst (x : ident) (s : expr) (e : expr) : expr =
  match e with
  | Binop (op, e1, e2) -> Binop (op, subst x s e1, subst x s e2)
  | If (b, t, e) -> If (subst x s b, subst x s t, subst x s e)
  | Var y -> if x = y then s else e
  | Let (y, e1, e2) ->
      Let (y, subst x s e1, if x = y then e2 else subst x s e2)
  | Pair (e1, e2) -> Pair (subst x s e1, subst x s e2)
  | Fst e -> Fst (subst x s e)
  | Snd e -> Snd (subst x s e)
  | Match (e1, z, y, e2) -> Match (subst x s e1, z, y, (if x = z || x = y then e2 else subst x s e2))
  | _ -> e

let rec reify (v : value) : expr =
  match v with
  | VInt a -> Int a
  | VBool b -> Bool b
  | VPair (v1, v2) -> Pair (reify v1, reify v2)
  | VUnit -> Unit

let rec eval (e : expr) : value =
  match e with
  (* Zadanie 1 *)
  | Binop (And, e1, e2) ->
    let v1 = eval e1 in
    (match v1 with
       | VBool false -> VBool false
       | VBool true  -> eval e2
       | _ -> failwith "Operacja && oczekuje wartości boolowskiej")
  | Binop (Or, e1, e2) ->
    let v1 = eval e1 in
    (match v1 with
       | VBool true -> VBool true
       | VBool false -> eval e2
       | _ -> failwith "Operacja || oczekuje wartości boolowskiej")
  (* Zadanie 2 *)
  | IsNumber e1 ->
  let v = eval e1 in
  VBool (match v with VInt _ -> true | _ -> false)
  | IsBoolean e1 ->
      let v = eval e1 in
      VBool (match v with VBool _ -> true | _ -> false)
  | IsPair e1 ->
      let v = eval e1 in
      VBool (match v with VPair _ -> true | _ -> false)
  | IsUnit e1 ->
      let v = eval e1 in
        VBool (match v with VUnit -> true | _ -> false)
  | Fold(e_list, (x, acc), e_body, e_base) ->
    let list_val = eval e_list in
    let rec fold_right v =
      match v with
      | VUnit -> eval e_base  (* gdy lista jest pusta, zwróć wartość bazową *)
      | VPair(v_head, v_tail) ->
          let acc_val = fold_right v_tail in
          (* Podstaw w ciele folda: zmienną x na (reify v_head) oraz acc na (reify acc_val) *)
          let e_body_subst = subst x (reify v_head) (subst acc (reify acc_val) e_body) in
          eval e_body_subst
      | _ -> failwith "fold oczekuje listy"
    in
    fold_right list_val
  | Int i -> VInt i
  | Bool b -> VBool b
  | Binop (op, e1, e2) ->
      eval_op op (eval e1) (eval e2)
  | If (b, t, e) ->
      (match eval b with
        | VBool true -> eval t
        | VBool false -> eval e
        | _ -> failwith "type error")
  | Let (x, e1, e2) ->
      eval (subst x (reify (eval e1)) e2)
  | Pair (e1, e2) -> VPair (eval e1, eval e2)
  | Unit -> VUnit
  | Fst e ->
      (match eval e with
        | VPair (v1, _) -> v1
        | _ -> failwith "Type error")
  | Snd e ->
      (match eval e with
        | VPair (_, v2) -> v2
        | _ -> failwith "Type error")
  | Match (e1, x, y, e2) ->
      (match eval e1 with
        | VPair (v1, v2) ->
            e2
            |> subst x (reify v1)
            |> subst y (reify v2)
            |> eval
        | _ -> failwith "Type error")
  | Var x -> failwith ("unknown var " ^ x)

let interp (s : string) : value =
  eval (parse s)
