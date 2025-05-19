(* Funkcja split – dzieli listę na dwie części *)
let rec split xs =
  match xs with
  | [] -> ([], [])
  | [x] -> ([x], [])
  | x :: y :: zs ->
      let (l1, l2) = split zs in
      (x :: l1, y :: l2)

(* Funkcja merge – scala dwie posortowane listy w jedną posortowaną *)
let rec merge xs ys =
  match (xs, ys) with
  | ([], _) -> ys
  | (_, []) -> xs
  | (x :: xs', y :: ys') ->
      if x <= y then x :: merge xs' ys
      else y :: merge xs ys'

(* Sortowanie przez złączanie *)
let rec merge_sort xs =
  match xs with
  | [] -> []
  | [_] -> xs
  | _ ->
      let (left, right) = split xs in
      merge (merge_sort left) (merge_sort right)

(*
Odpowiedź do pytania:
Funkcję merge_sort można uznać za nie będącą strukturalnie rekurencyjną, ponieważ:
Zamiast od razu wywoływać samą siebie na "bezpośrednich" częściach oryginalnej listy, najpierw dzieli listę na dwie części (przy użyciu funkcji split).
Dopiero na wynikowych częściach (które nie są dokładnymi fragmentami oryginalnej listy, lecz wynikami operacji podziału) wykonuje się rekurencyjne wywołania.
*)
