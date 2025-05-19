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

module Freq (D : KDICT) = struct
  let freq (xs : D.key list) : (D.key * int) list =
    (* Funkcja pomocnicza aktualizująca licznik wystąpień *)
    let add_key dict key =
      let count = match D.find_opt key dict with
        | Some n -> n + 1
        | None -> 1
      in
      D.insert key count dict
    in
    let dict = List.fold_left add_key D.empty xs in
    D.to_list dict
end

(* Funkcja obliczajaca częstotliwość znakow. *)
let char_freq (s : string) : (char * int) list =
  let list_of_string s = String.to_seq s |> List.of_seq in
  let module F = Freq(CharMapDict) in
  F.freq (list_of_string s)

(*
  #use "zad5.ml";;

  let freq_result = char_freq "abrakadabra";;
  (* Obliczamy częstotliwość znaków w "abrakadabra" *)
*)


