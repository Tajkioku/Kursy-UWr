let rec suffixes xs =
  match xs with
  | [] -> [[]]
  | _ :: t as l -> l :: suffixes t

(* Przykład:
   suffixes [1; 2; 3; 4] 
   => [[1; 2; 3; 4]; [2; 3; 4]; [3; 4]; [4]; []] *)
