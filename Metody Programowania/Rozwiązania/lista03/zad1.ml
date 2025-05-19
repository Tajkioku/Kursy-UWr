let product xs = 
  List.fold_left ( * ) 1 xs

  (* product [1; 2; 3] *)