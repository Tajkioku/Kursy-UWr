type ty_name = string
type ctor_name = string

type ctor = Ctor of ctor_name * ty_name list
type ty_def = ty_name * ctor list

let print_induction_principle (tyname, ctors) =
  Printf.printf "Aby udowodnić, że P : %s -> Prop,\n" tyname;
  List.iter (function
    | Ctor (ctor_name, []) ->
        Printf.printf "- wystarczy pokazać, że P (%s)\n" ctor_name
    | Ctor (ctor_name, args) ->
        let args_with_names =
          List.mapi (fun i ty -> Printf.sprintf "x%d:%s" i ty) args in
        let args_str = String.concat " " args_with_names in
        let pats = String.concat ", " (List.mapi (fun i _ -> Printf.sprintf "x%d" i) args) in
        Printf.printf "- dla każdego %s, jeśli %s to P (%s(%s))\n"
          args_str
          (String.concat " ∧ " (List.mapi (fun i ty ->
             if ty = tyname then Printf.sprintf "P(x%d)" i else "") args
          |> List.filter (fun s -> s <> "")
          ))
          ctor_name pats
  ) ctors

let int_list_ty = ("int_list", [Ctor ("Nil", []); Ctor ("Cons", ["int"; "int_list"])])
let () = print_induction_principle int_list_ty