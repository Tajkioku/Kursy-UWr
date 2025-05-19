let rec is_sorted xs =
  match xs with
  | [] | [_] -> true
  | x :: (y :: _ as t) -> (x <= y) && is_sorted t

(* Przykład:
   is_sorted [1; 3; 3; 5]  => true
   is_sorted [1; 5; 3; 7]  => false *)
