let parens_ok_extended s =
  let rec check stack = function
    | [] -> stack = []
    | c :: cs ->
      match c with
      | '(' | '[' | '{' -> check (c :: stack) cs
      | ')' ->
          (match stack with
          | '(' :: rest -> check rest cs
          | _ -> false)
      | ']' ->
          (match stack with
          | '[' :: rest -> check rest cs
          | _ -> false)
      | '}' ->
          (match stack with
          | '{' :: rest -> check rest cs
          | _ -> false)
      | _ -> false
  in
  check [] (List.of_seq (String.to_seq s))

  (* # parens_ok_extended "()[]";;
  - : bool = true
  
  # parens_ok_extended "[()[{}]]";;
  - : bool = true
  
  # parens_ok_extended "{(]}";;
  - : bool = false
  
  # parens_ok_extended "({)";;
  - : bool = false *)
  