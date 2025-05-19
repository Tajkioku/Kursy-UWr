let parens_ok s =
  let rec check count = function
    | [] -> count = 0
    | c :: cs ->
        match c with
        | '(' -> check (count + 1) cs
        | ')' -> if count > 0 then check (count - 1) cs else false
        | _ -> false  (* inne znaki – błąd *)
  in
  check 0 (List.of_seq (String.to_seq s))

  (* # parens_ok "(())()";;
  - : bool = true
  
  # parens_ok "()())";;
  - : bool = false
  
  # parens_ok "((())";;
  - : bool = false
  
  # parens_ok "(x)";;
  - : bool = false  (* ponieważ pojawia się znak 'x' *)
   *)