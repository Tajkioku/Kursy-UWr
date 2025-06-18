(* Zad 2 *)
let rec is_non_decreasing lst =
  match lst with
  | [] | [_] -> true
  | x :: y :: rest -> x <= y && is_non_decreasing (y :: rest)

(* Zad 3 *)
let rec insert x lst =
  match lst with
  | [] -> [x]
  | h :: t -> if x <= h then x :: lst else h :: insert x t

let rec insertion_sort lst =
  match lst with
  | [] -> []
  | h :: t -> insert h (insertion_sort t)

let test lst =
  let sorted = insertion_sort lst in
  Printf.printf "Czy posortowana niemalejąco: %b\n" (is_non_decreasing sorted)

let () = test [3;1;4;1;5;9]