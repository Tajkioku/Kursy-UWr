module type KDICT = sig
  type key
  type 'a dict
  val empty : 'a dict
  val insert : key -> 'a -> 'a dict -> 'a dict
  val remove : key -> 'a dict -> 'a dict
  val find_opt : key -> 'a dict -> 'a option
  val find : key -> 'a dict -> 'a
  val to_list : 'a dict -> (key * 'a) list
end

(* Sygnatura modułu porządkującego *)
module type OrderedType = sig
  type t
  val compare : t -> t -> int
end

(* Funktor implementujący słownik na liście par *)
module MakeListDict (Ord: OrderedType)
  : (KDICT with type key = Ord.t) =
struct
  type key = Ord.t
  type 'a dict = (key * 'a) list

  let empty = []

  let rec insert key value dict =
    match dict with
    | [] -> [(key, value)]
    | (k, v) :: rest when k=key -> (k,value)::rest
    | h::t -> h::(insert key value t)
  
  let rec remove key dict =
    match dict with
    | [] -> dict
    | (k,v)::rest when k=key -> rest
    | h::t -> h::(remove key t)
  
  let rec find_opt key dict =
    match dict with
    | [] -> None
    |(k,v)::rest when k=key -> Some v
    | h::t -> find_opt key t
  
  let find key dict =
    match find_opt key dict with
    | Some v -> v
    |None -> failwith "Blad"

  (* let insert key value dict =
    (key, value) :: dict

  let rec remove key = function
    | [] -> []
    | (k, v) :: xs ->
        if Ord.compare key k = 0 then remove key xs
        else (k, v) :: remove key xs

  let rec find_opt key = function
    | [] -> None
    | (k, v) :: xs ->
        if Ord.compare key k = 0 then Some v
        else find_opt key xs

  let find key dict =
    match find_opt key dict with
    | Some v -> v
    | None -> failwith "Element nie znaleziony" *)

  let to_list dict = dict
end

(* Moduł porządkujący dla znaków *)
module CharOrdered : OrderedType = struct
  type t = char
  let compare = Char.compare
end

(* Tworzymy moduł słownika dla znaków korzystając z funktora. *)
module CharListDict = MakeListDict(CharOrdered)

(*
  Pierwotna definicja funktora MakeListDict nie pozwalała na
  "dodawanie" elementów z zewnątrz, ponieważ typ klucza był ukryty.
  Używając "with type key = Ord.t" jawnie ujawniamy typ, dzięki czemu
  można tworzyć nowe wartości kluczy
*)

(*
#use "zad3.ml";;
(* Wczytujemy MakeMapDict i CharMapDict *)

open CharMapDict;;

let d = empty;;
let d = insert 'a' 1 d;;
let d = insert 'b' 2 d;;
let d = insert 'a' 3 d;;
(* Wstawiamy klucze 'a', 'b' *)

find 'a' d;;
(* 3 *)

find_opt 'c' d;;
(* None *)

let d = remove 'a' d;;
find_opt 'a' d;;
(* None *)

to_list d;;
(* Powinno zwrócić [('b', 2)] *)
*)