open OUnit2
open Ast
open Prefix  (* zakładamy, że moduł prefix jest dostępny *)
open Interp

let expr1 = Binop(Add, Int 1, Int 2)
let expr2 = If(Bool true, Int 5, Int 10)
let expr3 = Let("x", Int 3, Binop(Mult, Var "x", Int 4))

let test_to_polish _ =
  assert_equal "+ 1 2" (to_polish expr1);
  assert_equal "if true 5 10" (to_polish expr2);
  assert_equal "let x 3 * x 4" (to_polish expr3)

let test_eval_prefix _ =
  assert_equal (VInt 3) (eval_prefix "+ 1 2");
  assert_equal (VInt 5) (eval_prefix "if true 5 10");
  assert_equal (VInt 12) (eval_prefix "let x 3 * x 4")

let suite =
  "Prefix Tests" >::: [
    "to_polish" >:: test_to_polish;
    "eval_prefix" >:: test_eval_prefix;
  ]

let () = run_test_tt_main suite
