
(* The type of tokens. *)

type token = 
  | WITH
  | UNIT
  | TIMES
  | THEN
  | SND
  | SEMICOLON
  | RPAREN
  | RBRACKET
  | PLUS
  | OR
  | MINUS
  | MATCH
  | LPAREN
  | LET
  | LEQ
  | LBRACKET
  | ISUNIT
  | ISPAIR
  | ISNUMBER
  | ISBOOLEAN
  | INT of (int)
  | IN
  | IF
  | IDENT of (string)
  | FST
  | FOLDAND
  | FOLD
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
