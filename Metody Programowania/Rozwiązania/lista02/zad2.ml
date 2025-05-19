(* Mnożenie macierzy 2x2 *)
let matrix_mult (a, b, c, d) (e, f, g, h) =
  (a * e + b * g, a * f + b * h,
   c * e + d * g, c * f + d * h)

(* Macierz jednostkowa 2x2 *)
let matrix_id = (1, 0, 0, 1)

(* Potęgowanie macierzy przez wielokrotne mnożenie *)
let rec matrix_expt m k =
  if k = 0 then matrix_id
  else if k = 1 then m
  else matrix_mult m (matrix_expt m (k - 1))

(* Obliczanie n-tego elementu ciągu Fibonacciego *)
let fib_matrix n =
  if n = 0 then 0
  else 
    let (_, b, _, _) = matrix_expt (1, 1, 1, 0) n in
    b


(* Zad 3 *)

(* Szybkie potęgowanie macierzy 2x2 *)
let rec matrix_expt_fast m k =
    if k = 0 then matrix_id
    else if k mod 2 = 0 then 
      let half = matrix_expt_fast m (k / 2) in
      matrix_mult half half
    else
      matrix_mult m (matrix_expt_fast m (k - 1))
  
  (* Szybsze obliczanie Fibonacciego *)
  let fib_fast n =
    if n = 0 then 0
    else 
      let (_, b, _, _) = matrix_expt_fast (1, 1, 1, 0) n in
      b
  
  
