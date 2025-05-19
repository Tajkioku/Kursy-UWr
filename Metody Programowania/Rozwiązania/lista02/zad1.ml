(* Rekurencyjna wersja obliczania Fn *)
let rec fib n =
  if n = 0 then 0
  else if n = 1 then 1
  else fib (n - 1) + fib (n - 2)

(* Wersja iteracyjna wykorzystująca pomocniczą funkcję *)
let fib_iter n =
  let rec aux n a b =
    if n = 0 then a
    else aux (n - 1) b (a + b)
  in
  aux n 0 1