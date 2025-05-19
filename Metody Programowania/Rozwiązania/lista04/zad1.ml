module type DICT = sig
  type ('a, 'b) dict
  val empty : ('a, 'b) dict
  val insert : 'a -> 'b -> ('a, 'b) dict -> ('a, 'b) dict
  val remove : 'a -> ('a, 'b) dict -> ('a, 'b) dict
  val find_opt : 'a -> ('a, 'b) dict -> 'b option
  val find : 'a -> ('a, 'b) dict -> 'b
  val to_list : ('a, 'b) dict -> ('a * 'b) list
end

module ListDict : DICT = struct
  (* Słownik reprezentowany jest jako lista par klucz-wartość *)
  type ('a, 'b) dict = ('a * 'b) list

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

  let insert key value dict =
    (key, value) :: dict

  let rec remove key = function
    | [] -> []
    | (k, v) :: xs ->
        if key = k then remove key xs
        else (k, v) :: remove key xs

  let rec find_opt key = function
    | [] -> None
    | (k, v) :: xs ->
        if key = k then Some v
        else find_opt key xs

  let find key dict =
    match find_opt key dict with
    | Some v -> v
    | None -> failwith "Element nie znaleziony"

  let to_list dict = dict
end

(*
#use "zad1.ml";;
open ListDict;;
(* Otwieramy moduł, żeby nie pisać ListDict.coś za każdym razem *)

let d = empty;;

let d = insert "a" 1 d;;
let d = insert "b" 2 d;;
let d = insert "a" 3 d;;
(* Wstawiamy pary klucz-wartość (nadpisując klucz 'a' wartością 3) *)

find "a" d;;
(* Powinno zwrócić 3 *)

find_opt "c" d;;
(* None, bo nie ma klucza "c" *)

let d = remove "a" d;;

find_opt "a" d;;
(* None, bo 'a' usunięty *)

to_list d;;
(* Lista ("b", 2) *)
*)
