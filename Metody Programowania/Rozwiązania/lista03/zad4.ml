type 'a set = 'a -> bool

let empty_set = fun _ -> false

let singleton a = fun x -> x = a

let in_set a s = s a

let union s t = fun x -> s x || t x

let intersect s t = fun x -> s x && t x

(*
in_set 5 empty_set;;
let s = singleton 10;;
in_set 10 s;;
in_set 5 s;;
let s1 = singleton 3;;
let s2 = singleton 4;;
let s_union = union s1 s2;;
in_set 3 s_union;;
in_set 4 s_union;;
in_set 5 s_union;;
let s3 = intersect s_union (fun x -> x > 3);;
in_set 3 s3;;
in_set 4 s3;;
*)
