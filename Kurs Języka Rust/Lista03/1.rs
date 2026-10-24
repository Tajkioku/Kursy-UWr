// Kami Wozniak, 330601
fn open_or_senior(data: Vec<(i32, i32)>) -> Vec<String> {
    let mut out = Vec::with_capacity(data.len());
    for (age, h) in data {
        if age >= 55 && h > 7 {
            out.push("Senior".into());
        } else {
            out.push("Open".into());
        }
    }
    out
}

fn main() {
    let input = vec![(18, 20), (45, 2), (61, 12), (37, 6), (21, 21), (78, 9)];
    let result = open_or_senior(input);
    println!("{:?}", result);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        let input = vec![(18, 20), (45, 2), (37, 6)];
        let expected = vec!["Open", "Open", "Open"];

        assert_eq!(open_or_senior(input), expected);
    }

    #[test]
    fn test2() {
        let input = vec![(56, 20), (55, 8), (60, 10)];
        let expected = vec!["Senior", "Senior", "Senior"];

        assert_eq!(open_or_senior(input), expected);
    }

    #[test]
    fn test3() {
        let input = vec![(18, 20), (55, 8), (37, 6)];
        let expected = vec!["Open", "Senior", "Open"];

        assert_eq!(open_or_senior(input), expected);
    }

    #[test]
    fn test_empty() {
        assert_eq!(open_or_senior(vec![]), Vec::<String>::new());
    }

    #[test]
    fn test5() {
        let input = vec![(55, 8), (54, 7), (55, 7)];
        let expected = vec!["Senior", "Open", "Open"];

        assert_eq!(open_or_senior(input), expected);
    }
}