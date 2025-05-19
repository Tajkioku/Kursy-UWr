(* Pomocnicza funkcja: usuwa pierwsze wystąpienie elementu x z listy *)
let rec remove_first x xs =
  match xs with
  | [] -> []
  | y :: ys -> if y = x then ys else y :: remove_first x ys

(* Funkcja select – wybiera najmniejszy element oraz zwraca listę bez niego *)
let select xs =
  match xs with
  | [] -> failwith "select: pusta lista"
  | _ ->
      let min_val = List.fold_left min (List.hd xs) xs in
      (min_val, remove_first min_val xs)

(* Sortowanie przez wybór *)
let rec select_sort xs =
  match xs with
  | [] -> []
  | _ ->
      let (min_val, rest) = select xs in
      min_val :: select_sort rest

(* Przykład:
   select_sort [1; 5; 0; 7; 1; 4; 1; 0]
   => [0; 0; 1; 1; 1; 4; 5; 7] *)
