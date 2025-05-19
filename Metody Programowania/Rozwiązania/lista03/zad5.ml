type 'a bst =
  | Leaf
  | Node of 'a bst * 'a * 'a bst

let rec insert_bst x t =
  match t with
  | Leaf -> Node (Leaf, x, Leaf)
  | Node (l, v, r) ->
      if x = v then t
      else if x < v then Node (insert_bst x l, v, r)
      else Node (l, v, insert_bst x r)

(* 
let t =
  Node (Node (Leaf, 2, Leaf),
        5,
        Node (Node (Leaf, 6, Leaf),
              8,
              Node (Leaf, 9, Leaf)));;

let t' = insert_bst 7 t;;
*)
