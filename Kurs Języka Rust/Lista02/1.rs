// Kamil Wozniak 330601
fn get_count(string: &str) -> usize {
    string
        .chars()
        .filter(|&c| match c {
            'a' | 'e' | 'i' | 'o' | 'u' => true,
            _ => false,
        })
        .count()
}

fn main() {
    println!("{}", get_count("abracadabra"));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        assert_eq!(get_count("abracadabra"), 5);
    }

    #[test]
    fn test2() {
        assert_eq!(get_count("hello"), 2);
    }

    #[test]
    fn test3() {
        assert_eq!(get_count("abc"), 1);
    }

    #[test]
    fn test4() {
        assert_eq!(get_count("xyz"), 0);
    }

    #[test]
    fn test5() {
        assert_eq!(get_count(""), 0);
    }
}