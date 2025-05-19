let rec mem x xs =
  match xs with
  | [] -> false
  | y :: ys -> x = y || mem x ys

(* Przykłady:
   mem 2 [1; 2; 3]  => true
   mem 4 [1; 2; 3]  => false *)
