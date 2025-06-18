
(* The type of tokens. *)

type token = 
  | UNIT
  | TIMES
  | THEN
  | SND
  | RPAREN
  | REC
  | PLUS
  | OR
  | NEQ
  | MINUS
  | LT
  | LPAREN
  | LET
  | LEQ
  | ISUNIT
  | ISPAIR
  | ISINT
  | ISFUN
  | ISBOOL
  | INT of (int)
  | IN
  | IF
  | IDENT of (string)
  | GT
  | GEQ
  | FUNREC
  | FUN
  | FST
  | EQ
  | EOF
  | ELSE
  | DIV
  | COMMA
  | BOOL of (bool)
  | ARR
  | AND

(* This exception is raised by the monolithic API functions. *)

exception Error

(* The monolithic API. *)

val main: (Lexing.lexbuf -> token) -> Lexing.lexbuf -> (Ast.expr)
