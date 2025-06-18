(* Zad 5 *)
type var = int
type int_lit  = int

type expr =
  | Unit
  | Int  of int_lit
  | Bool of bool
  | Var  of var
  | If   of expr * expr * expr
  | Let  of var * expr * expr
  | App  of expr * expr
  | Pair of expr * expr
  | Fun  of var * expr
  | Funrec of var (* f *) * var (* x *) * expr
  | Builtin of string