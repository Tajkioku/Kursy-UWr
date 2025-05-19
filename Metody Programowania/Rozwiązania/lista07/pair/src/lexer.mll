{
open Parser
}

let white = [' ' '\t']+
let digit = ['0'-'9']
let number = '-'? digit+
let char = ['a'-'z' 'A'-'Z' '_']
let ident = char(char|digit)*

rule read =
    parse
    | white { read lexbuf }
    (* Zadanie 2 *)
    | "number?" { ISNUMBER }
    | "boolean?" { ISBOOLEAN }
    | "pair?" { ISPAIR }
    | "unit?" { ISUNIT }
    (* Zadanie 3 *)
    | "[" { LBRACKET }
    | "]" { RBRACKET }
    | ";" { SEMICOLON }
    (* Zadanie 4 *)
    | "fold" { FOLD }      (* nowy token: FOLD dla słowa "fold" *)
    | "and"  { FOLDAND }   (* nowy token: FOLDAND dla słowa "and" w składni fold *)
    | "*" { TIMES }
    | "+" { PLUS }
    | "-" { MINUS }
    | "/" { DIV }
    | "&&" { AND }
    | "||" { OR }
    | "=" { EQ }
    | "<=" { LEQ }
    | "(" { LPAREN }
    | ")" { RPAREN }
    | "->" { ARR }
    | "if" { IF }
    | "then" { THEN }
    | "let" { LET }
    | "in" { IN }
    | "else" { ELSE }
    | "true" { BOOL true }
    | "false" { BOOL false }
    | "," { COMMA }
    | "()" { UNIT }
    | "fst" { FST }
    | "snd" { SND }
    | "match" { MATCH }
    | "with" { WITH }
    | number { INT ( int_of_string (Lexing.lexeme lexbuf)) }
    | ident { IDENT (Lexing.lexeme lexbuf) }
    | eof { EOF }
