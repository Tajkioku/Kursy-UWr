open Ast
open Interp

let rec to_polish (e : expr) : string =
  match e with
  | Int n        -> string_of_int n
  | Bool b       -> string_of_bool b
  | Unit         -> "()"
  | Var x        -> x
  | Binop (op,e1,e2) ->
      let op_s = match op with
        | Add  -> "+"  | Sub  -> "-"
        … (* pozostałe operatory *)
      in
      String.concat " " [op_s; to_polish e1; to_polish e2]
  | If (c,t,e)   -> String.concat " " ["if"; to_polish c; to_polish t; to_polish e]
  | Let (x,e1,e2)-> String.concat " " ["let"; x; to_polish e1; to_polish e2]
  | Match (e1,(x,y),e2)
                 -> String.concat " " ["match"; to_polish e1; x; y; to_polish e2]
  | Pair (e1,e2) -> String.concat " " ["pair"; to_polish e1; to_polish e2]
  | Fst e        -> String.concat " " ["fst"; to_polish e]
  | Snd e        -> String.concat " " ["snd"; to_polish e]
  | IsPair e     -> String.concat " " ["pair?"; to_polish e]
  | Fun (arg,b)  -> String.concat " " ["fun"; arg; to_polish b]
  | Funrec (f,a,b)->String.concat " " ["funrec"; f; a; to_polish b]
  | App (f,a)    -> String.concat " " ["app"; to_polish f; to_polish a]

(*
   [eval_prefix s] bierze łańcuch tokenów s rozdzielonych spacjami,
   wykorzystuje algorytm oparty na stosie do ewaluacji wyrażenia
   i zwraca wynik typu value.
*)
let eval_prefix (s : string) : value =
  let toks = String.split_on_char ' ' s in
  let rec process toks stack =
    match toks with
    | [] ->
        (match stack with [Val v] -> v | _ -> failwith "Invalid prefix expression")
    | t :: ts ->
        (* 1) zamiana tokenu na element stosu *)
        let elem = match int_of_string_opt t with
          | Some n -> Val (VInt n)
          | None ->
              if t = "true" then Val (VBool true)
              else if t = "false" then Val (VBool false)
              else if t = "()" then Val VUnit
              else let bop = (* rozpoznanie operatora *) … in Op bop
        in
        let stack = elem :: stack in
        (* 2) jeśli na szczycie jest wzór [Val v2; Val v1; Op op], to obliczamy op v1 v2 *)
        let rec reduce = function
          | Val v2 :: Val v1 :: Op op :: rest ->
              reduce (Val (eval_op op v1 v2) :: rest)
          | st -> st
        in
        process ts (reduce stack)
  in
  process toks []

