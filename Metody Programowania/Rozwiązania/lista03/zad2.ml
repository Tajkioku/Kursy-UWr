let square x = x * x



let inc x = x + 1

let compose f g x = f (g x)



(*
compose square inc 5 -> square (inc 5) -> square 6 -> 6*6 -> 36
compose inc square 5 -> inc (square 5) -> inc 25 -> 25+1 -> 26
*)