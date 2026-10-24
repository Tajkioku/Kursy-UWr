fn longest(s1: &str, s2: &str) -> String {
    let mut ans = String::new();

    for c in 'a'..='z' {
        if s1.contains(c) || s2.contains(c) {
            ans.push(c);
        }
    }

    ans
}

fn main() {
    let a = "xyaabbbccccdefww";
    let b = "xxxxyyyyabklmopq";
    println!("{}", longest(a, b));
}

#[cfg(test)]
mod tests {
    use longest;

    #[test]
    fn test_empty() {
        assert_eq!(longest("", ""), "");
    }

    #[test]
    fn test_first_empty() {
        assert_eq!(longest("", "b"), "b");
    }

    #[test]
    fn test_second_empty() {
        assert_eq!(longest("a", ""), "a");
    }

    #[test]
    fn test_equal() {
        assert_eq!(longest("abc", "abc"), "abc");
    }

    #[test]
    fn test5() {
        assert_eq!(longest("aabbcc", "ccbbaa"), "abc");
    }

    #[test]
    fn test6() {
        assert_eq!(longest("xyaabbbccccdefww", "xxxxyyyyabklmopq"), "abcdefklmopqwxy");
    }
}