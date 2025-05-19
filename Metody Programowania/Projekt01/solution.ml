(* Alpha = 3/4 *)
let alpha_num = 3
let alpha_denom = 4

type 'a tree = Leaf | Node of 'a tree * 'a * 'a tree
type 'a sgtree = { tree : 'a tree; size : int; max_size: int }

(* Log(4/3)(n) = Log(n) / log(4/3) *)
let alpha_height (n : int) : int =
  if n <= 0 then 0
  else int_of_float (log (float_of_int n) /. log ((float_of_int alpha_denom) /. (float_of_int alpha_num)))

  (* Zawraca liste odwiedzanych elementow w porzadku in-order *)
  let inorder t =
  let rec aux t acc =
    match t with
    | Leaf -> acc
    | Node(l, v, r) -> aux l (v :: aux r acc)
  in
  aux t []

(* Przebudowa drzewa do postaci drzewa kompletnego *)
let rebuild_balanced (t : 'a tree) : 'a tree =
  let sorted_elems = inorder t in
  let rec build_tree elements =
    match elements with
    | [] -> Leaf
    | _ ->
        let len = List.length elements in
        let mid = len / 2 in
        (* Funkcja split_list dzieli listę na dwie części, count określa ilosc elementów w lewej części *)
        let rec split_list count lst =
          if count = 0 then ([], lst)
          else
            match lst with
            | [] -> ([], [])
            (* Wyciagamu pierwszy element a pozniej go doklejamy (y::left_part), 
            zeby pierwszy element byl zawsze w lewej czesci *)
            | y :: ys ->
                let (left_part, right_part) = split_list (count - 1) ys in
                (y :: left_part, right_part)
        in
        let (left_elems, rest_elems) = split_list mid elements in
        match rest_elems with
        | [] -> failwith "rebuild_balanced: Pusta lista"
        | mid_elem :: right_elems ->
            (* Pierwszy element w reszcie (srodkowy element) to nowy korzen, a pozostałe elementy
            rozdzielamhy na lewe i prawe poddrzewo *)
            Node (build_tree left_elems, mid_elem, build_tree right_elems)
  in
  build_tree sorted_elems

let empty : 'a sgtree = { tree = Leaf; size = 0; max_size = 0 }

(* Rekurencyjnie sprawdza czy x jest w drzewie *)
let rec find_aux (x : 'a) (t : 'a tree) : bool =
  match t with
  | Leaf -> false
  | Node (l, v, r) ->
      if x = v then true
      else if x < v then find_aux x l
      else find_aux x r

let find (x : 'a) (sgt : 'a sgtree) : bool =
  find_aux x sgt.tree

let rec insert_BST x t =
  match t with
  | Leaf -> Node(Leaf, x, Leaf)
  | Node(l, v, r) ->
      if x < v then Node(insert_BST x l, v, r)
      else Node(l, v, insert_BST x r)
    
let rec depth x t =
  match t with
  | Leaf -> failwith "depth_of: Element nie znaleziony"
  | Node(l, v, r) ->
      if x = v then 0
      else if x < v then 1 + depth x l
      else 1 + depth x r

let rec size_tree t =
  match t with
  | Leaf -> 0
  | Node(l,_,r) -> 1 + size_tree l + size_tree r

(* Znajduje kozla ofirnego *)
let rec find_scapegoat (x : 'a) (t : 'a tree) : 'a tree =
  match t with
  | Leaf -> failwith "find_scapegoat: Brak kozla ofiarnego"
  | Node(l, v, r) ->
      let s = size_tree t in
      let s_left = size_tree l in
      let s_right = size_tree r in
      (* Sprawdza czy rozmiar lewej lub prawej jest wiekszy od (alpha * size(node)) *)
      if s_left > (alpha_num * s) / alpha_denom || s_right > (alpha_num * s) / alpha_denom then
        t
      else if x < v then find_scapegoat x l
      else if x > v then find_scapegoat x r
      else t

(* Zastępuje poddrzewo target w drzewie t przez new_subtree *)
let rec replace_subtree t target new_subtree =
  match t with
  | Leaf -> Leaf
  | Node(l, v, r) ->
      (* Elementy sa unikalne, wiec porownujemy wartosci wezlow *)
      match target with
      | Node(_, tv, _) when v = tv -> new_subtree
      | _ -> Node(replace_subtree l target new_subtree, v, replace_subtree r target new_subtree)
      
(* Funkcja: insert *)
let insert (x : 'a) (sgt : 'a sgtree) : 'a sgtree =
  if find x sgt then failwith "insert: Element już istnieje"
  else
    let new_tree = insert_BST x sgt.tree in
    let new_size = sgt.size + 1 in
    let new_max_size = max sgt.max_size new_size in
    let d = depth x new_tree in
    if d <= alpha_height new_size then
      { tree = new_tree; size = new_size; max_size = new_max_size }
    else
      let scapegoat = find_scapegoat x new_tree in
      let rebuilt = rebuild_balanced scapegoat in
      let final_tree = replace_subtree new_tree scapegoat rebuilt in
      { tree = final_tree; size = new_size; max_size = new_max_size }

(* Usuwanie z drzewa BST *)
let rec remove_BST x t =
  match t with
  | Leaf -> failwith "remove_BST: Element nie istnieje"
  | Node(l, v, r) ->
      if x < v then Node(remove_BST x l, v, r)
      else if x > v then Node(l, v, remove_BST x r)
      else
        match l, r with
        | Leaf, _ -> r
        | _, Leaf -> l
        | _ ->
            let rec find_min t =
              match t with
              | Leaf -> failwith "remove_BST: Minimum nie znalezione"
              | Node(Leaf, v, _) -> v
              | Node(l, _, _) -> find_min l
            in
            let minimum = find_min r in
            Node(l, minimum, remove_BST minimum r)

(* Funkcja remove *)
let remove (x : 'a) (sgt : 'a sgtree) : 'a sgtree =
  if not (find x sgt) then failwith "remove: Element nie istnieje"
  else
    let new_tree = remove_BST x sgt.tree in
    let new_size = sgt.size - 1 in
    let final_tree =
      (* Podobnie jak w insert_BST sprawdzamy warunek z alpha * size(node) *)
      if new_size < (alpha_num * sgt.max_size) / alpha_denom then
        rebuild_balanced new_tree
      else new_tree
    in
    (* Przypisujemmy nowy rozmiar drzewa *)
    let new_max_size = if new_size < sgt.max_size then new_size else sgt.max_size in
    { tree = final_tree; size = new_size; max_size = new_max_size }


(* ********** Troche przykladowych testow ********** *)


(* Testy height *)
(*
alpha_height 0;;
- : int = 0
alpha_height 1;;
- : int = 0
alpha_height 20;;
- : int = 10
*)


(* Testy inorder *)
(*
let t0 = Leaf;;
inorder t0;;
- : int list = []
let t1 = Node(Leaf, 5, Leaf);;
inorder t1;;
- : int list = [5]
let t2 = Node(Leaf, 10, Node(Leaf, 20, Leaf));;
inorder t2;;
- : int list = [10; 20]
let t3 = Node(Node(Leaf, 5, Leaf), 15, Node(Leaf, 20, Leaf));;
inorder t3;;
- : int list = [5; 15; 20]
*)


(* Testy rebuild_balanced *)
(*
let degenerate = Node(Leaf, 1, Node(Leaf, 2, Node(Leaf, 3, Node(Leaf, 4, Leaf))));;
inorder degenerate;;
- : int list = [1; 2; 3; 4]
let balanced_deg = rebuild_balanced degenerate;;
inorder balanced_deg;;
- : int list = [1; 2; 3; 4]

let rec height t = match t with
  | Leaf -> 0
  | Node(l,_,r) -> 1 + max (height l) (height r);;

height degenerate;;
- : int = 4
height balanced_deg;;
- : int = 3
*)


(* Test empty *)
(*
empty;;
- : int sgtree = { tree = Leaf; size = 0; max_size = 0 }
*)


(* Testy find *)
(*
let bst = { tree = Node(Node(Leaf, 5, Leaf), 10, Node(Leaf, 15, Leaf));
             size = 3; max_size = 3 };;
find 5 bst;;
- : bool = true
find 10 bst;;
- : bool = true
find 15 bst;;
- : bool = true
find 7 bst;;
- : bool = false
*)


(* Testy insert *)
(*
let sgt0 = { tree = Leaf; size = 0; max_size = 0 } ;;
let sgt1 = insert 10 sgt0 ;;
inorder sgt1.tree ;;
- : int list = [10]
let sgt2 = insert 5 sgt1 ;;
inorder sgt2.tree ;;
- : int list = [5; 10]
let sgt3 = insert 15 sgt2 ;;
inorder sgt3.tree ;;
- : int list = [5; 10; 15]
let sgt4 = insert 7 sgt3 ;;
inorder sgt4.tree ;;
- : int list = [5; 7; 10; 15]
*)