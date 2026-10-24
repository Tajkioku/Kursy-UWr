
fn string_to_number(s: &str) -> i32 {
    s.parse::<i32>().unwrap()
}

fn main() {
    let s = "-321";
    println!("Liczba: {}", string_to_number(s));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        assert_eq!(string_to_number("123"), 123);
    }

    #[test]
    fn test2() {
        assert_eq!(string_to_number("-123"), -123);
    }

    #[test]
    fn test3() {
        assert_eq!(string_to_number("0"), 0);
    }

    #[test]
    fn test4() {
        assert_eq!(string_to_number("1111"), 1111);
    }

    #[test]
    fn test5() {
        assert_eq!(string_to_number("-5"), -5);
    }
}
