let build_list n f =
  let rec aux i =
    if i >= n then [] else f i :: aux (i + 1)
  in aux 0

let negatives n = build_list n (fun i -> -(i + 1))
let reciprocals n = build_list n (fun i -> 1.0 /. float_of_int (i + 1))
let evens n = build_list n (fun i -> 2 * i)
let identityM n =
  build_list n (fun i ->
    build_list n (fun j -> if i = j then 1 else 0))

(* 
negatives 5;;
reciprocals 5;;
evens 5;;
identityM 3;; 
*)