module type OrderedType = sig
  type t
  val compare : t -> t -> int
end

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

module MakeMapDict (Ord : OrderedType)
  : (KDICT with type key = Ord.t) =
struct
  (* Tworzymy lokalny moduł M przy pomocy Map.Make *)
  module M = Map.Make(Ord)

  (* Typ klucza to Ord.t, a typ słownika to 'a M.t *)
  type key = Ord.t
  type 'a dict = 'a M.t

  let empty = M.empty

  let insert key value d =
    M.add key value d

  let remove key d =
    M.remove key d

  let find_opt key d =
    M.find_opt key d

  let find key d =
    M.find key d

  let to_list d =
    M.bindings d
end

module CharOrdered = struct
  type t = char
  let compare = Char.compare
end

module CharMapDict = MakeMapDict(CharOrdered)

(*
#use "zad4.ml";;
open CharMapDict;;

let d = empty;;
let d = insert 'a' 1 d;;
let d = insert 'b' 2 d;;
let d = insert 'a' 3 d;;

find 'a' d;;
(* 3 *)

find_opt 'c' d;;
(* None *)

let d = remove 'a' d;;
find_opt 'a' d;;
(* None *)

to_list d;;
(* [('b', 2)] *)
*)