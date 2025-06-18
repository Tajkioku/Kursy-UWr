%{
open RawAst

let node pos data = { data; pos }

(* ------------------------------------------------------------------------- *)
(*  >>> HELPER 1  [arrow_chain]                                              *)
(*  Buduje długi typ funkcyjny:                                              *)
(*     (x2:τ2) … (xn:τn)  +  τret   ↦   τ2 -> … -> τn -> τret                *)
(* ------------------------------------------------------------------------- *)
let rec arrow_chain params ret_tp =
  match params with
  | [] -> ret_tp                                             (* brak więcej parametrów *)
  | (_, tp) :: rest -> TArrow (tp, arrow_chain rest ret_tp)   (* doklej «->» i rekurencja *)

(* ------------------------------------------------------------------------- *)
(*  >>> HELPER 2  [fun_chain]                                                *)
(*  Owijka na ciało funkcji kolejnymi lambda‑abstrakcjami:                   *)
(*     (x2:τ2)…(xn:τn)  +  body   ↦   fun (x2:τ2) -> … -> fun (xn:τn) -> body*)
(*  [loc] — zakres całej definicji; przypisujemy go każdemu węzłowi Fun,     *)
(*          żeby przy błędach wskazywany był poprawny fragment kodu.         *)
(* ------------------------------------------------------------------------- *)
let rec fun_chain loc params body =
  match params with
  | [] -> body                                                (* brak parametrów → zwróć body *)
  | (x, tp) :: rest ->                                        
      node loc (Fun (x, tp, fun_chain loc rest body))         (* Fun + rekurencja *)
%}
%token <bool> BOOL
%token <int> INT
%token <string> IDENT
%token IF THEN ELSE LET IN
%token PLUS MINUS TIMES DIV AND OR LEQ LT GEQ GT EQ NEQ
%token LPAREN RPAREN UNIT FST SND ARR FUN FUNREC
%token KW_INT KW_BOOL KW_UNIT
%token COMMA COLON EOF

%start <RawAst.expr> main

%right ARR
%left AND OR
%nonassoc EQ NEQ LEQ LT GT GEQ
%left PLUS MINUS
%left TIMES DIV

%%

main:
  | e = mexpr; EOF { e }
  ;

type_expr:
  | tp = type_simpl { tp }
  | tp1 = type_expr; ARR; tp2 = type_expr { TArrow(tp1, tp2) }
  | tp1 = type_expr; TIMES; tp2 = type_expr { TPair(tp1, tp2) }
  ;

type_simpl:
  | KW_INT  { TInt  }
  | KW_BOOL { TBool }
  | KW_UNIT { TUnit }
  | LPAREN; tp = type_expr; RPAREN { tp }
;

mexpr:
  | IF; e1 = mexpr; THEN; e2 = mexpr; ELSE; e3 = mexpr
      { node $loc (If (e1, e2, e3)) }
  | LET; x = IDENT; EQ; e1 = mexpr; IN; e2 = mexpr
      { node $loc (Let (x, e1, e2)) }
  | FUN; LPAREN; x = IDENT; COLON; tp = type_expr; RPAREN; ARR; e = mexpr
      { node $loc (Fun (x, tp, e)) }

  // Zad 3 Funrec
  | FUNREC; f = IDENT; params = param_grp_list;
    COLON; ret_tp = type_simpl; ARR; body = mexpr
      {
        match params with
        | (x1, tp1) :: rest ->                    (* ≥1 parametr — typowe użycie *)
            let loc        = $loc in              (* zakres całej definicji *)
            let ret_tp'    = arrow_chain rest ret_tp in   (* τ2 -> … -> τret *)
            let nested     = fun_chain loc rest body in   (* Fun‑kaskada *)
            node loc (Funrec (f, x1, tp1, ret_tp', nested))
        | [] ->                                   (* «funrec f : τ -> body» –   *)
            node $loc (Funrec (f, "_", TUnit, ret_tp, body)) (* zachowujemy się jak stary parser *)
      }
  | e = expr { e }
  ;

expr:
  | e1 = expr; PLUS; e2 = expr { node $loc (Binop (Add,  e1, e2)) }
  | e1 = expr; MINUS; e2 = expr { node $loc (Binop (Sub,  e1, e2)) }
  | e1 = expr; DIV; e2 = expr { node $loc (Binop (Div,  e1, e2)) }
  | e1 = expr; TIMES; e2 = expr { node $loc (Binop (Mult, e1, e2)) }
  | e1 = expr; AND; e2 = expr { node $loc (Binop (And,  e1, e2)) }
  | e1 = expr; OR;  e2 = expr { node $loc (Binop (Or,   e1, e2)) }
  | e1 = expr; EQ;  e2 = expr { node $loc (Binop (Eq,   e1, e2)) }
  | e1 = expr; NEQ; e2 = expr { node $loc (Binop (Neq,  e1, e2)) }
  | e1 = expr; LEQ; e2 = expr { node $loc (Binop (Leq,  e1, e2)) }
  | e1 = expr; LT;  e2 = expr { node $loc (Binop (Lt,   e1, e2)) }
  | e1 = expr; GT;  e2 = expr { node $loc (Binop (Gt,   e1, e2)) }
  | e1 = expr; GEQ; e2 = expr { node $loc (Binop (Geq,  e1, e2)) }
  | e = app_expr { e }
  ;

app_expr:
  | FST; e = base_expr { node $loc (Fst e) }
  | SND; e = base_expr { node $loc (Snd e) }
  | e1 = app_expr; e2 = base_expr { node $loc (App (e1, e2)) }
  | e = base_expr { e }
  ;

base_expr:
  | x = IDENT { node $loc (Var x) }
  | i = INT { node $loc (Int i) }
  | b = BOOL { node $loc (Bool b) }
  | UNIT { node $loc Unit }
  | LPAREN; e1 = mexpr; COMMA; e2 = mexpr; RPAREN { node $loc (Pair (e1, e2)) }
  | LPAREN; e = mexpr; RPAREN { node $loc (e.data) }
  ;

// Lista parametrow dla funrec
param_grp_list:
  | p = param_grp                        { [p] }
  | ps = param_grp_list; p = param_grp   { ps @ [p] }
  ;

param_grp:
  | LPAREN; x = IDENT; COLON; tp = type_expr; RPAREN { (x, tp) }
  ;
