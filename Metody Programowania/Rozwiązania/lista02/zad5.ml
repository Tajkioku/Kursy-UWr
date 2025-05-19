let maximum xs =
  match xs with
  | [] -> neg_infinity
  | x :: xs' -> List.fold_left (fun acc y -> if y > acc then y else acc) x xs'

(* Przykłady:
   maximum [1.; 5.; 0.; 7.; 1.; 4.; 1.; 0.]  => 7.
   maximum []                                   => neg_infinity *)
