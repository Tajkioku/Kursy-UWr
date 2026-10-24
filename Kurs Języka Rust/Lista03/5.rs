// Kami Wozniak, 330601
fn even_numbers(array: &Vec<i32>, number: usize) -> Vec<i32> {
    let mut result = Vec::new();

    for i in (0 .. array.len()).rev() {
        if array[i] % 2 == 0 {
            result.push(array[i]);
        }
        if result.len() >= number {
            break;
        }
    }

    if number == 0 {
        return Vec::new();
    }
    result.reverse();
    result
}

fn main() {
    let array = vec![1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    let result = even_numbers(&array, 3);

    println!("{:?}", result);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_empty() {
        let array = vec![];
        let number = 1;
        let expected: Vec<i32> = vec![];
        assert_eq!(even_numbers(&array, number), expected);
    }

    #[test]
    fn test_empty_2() {
        let array = vec![1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        let number = 0;
        let expected: Vec<i32> = vec![];
        assert_eq!(even_numbers(&array, number), expected);
    }

    #[test]
    fn test3() {
        let array = vec![1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        let number = 3;
        let expected = vec![6, 8, 10];
        assert_eq!(even_numbers(&array, number), expected);
    }

    #[test]
    fn test4() {
        let array = vec![1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        let number = 5;
        let expected = vec![2, 4, 6, 8, 10];
        assert_eq!(even_numbers(&array, number), expected);
    }

    #[test]
    fn test5() {
        let array = vec![1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        let number = 1;
        let expected = vec![10];
        assert_eq!(even_numbers(&array, number), expected);
    }
}
