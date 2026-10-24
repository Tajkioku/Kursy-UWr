fn summy(s: &str) -> i32 {
    s.split_whitespace()
     .filter_map(|n| n.parse::<i32>().ok())
     .sum()
}

fn main() {
    let input = "1 2 3";
    println!("Suma: {}", summy(input));
}

#[cfg(test)]
mod tests {
    use super::summy;

    #[test]
    fn test_empty() {
        assert_eq!(summy(""), 0);
    }

    #[test]
    fn suma_positive() {
        assert_eq!(summy("1 2 3"), 6);
    }

    #[test]
    fn sum_negative() {
        assert_eq!(summy("-1 -2 -3"), -6);
    }

    #[test]
    fn sum_mixed() {
        assert_eq!(summy("-1 2 3"), 4);
    }

    #[test]
    fn test_many_whitespaces() {
        assert_eq!(summy("  1   2  3  "), 6);
    }
}