fn solution(number: i32) -> i32 {
    let mut suma = 0;

    for i in 1..number {
        if i % 3 == 0 || i % 5 == 0 {
            suma += i;
        }
    }

    suma
}

fn main() {
    println!("{}", solution(10));
}

#[cfg(test)]
mod tests {
    use solution;

    #[test]
    fn test_zero() {
        assert_eq!(solution(0), 0);
    }

    #[test]
    fn test2() {
        assert_eq!(solution(2), 0);
    }

    #[test]
    fn test3() {
        assert_eq!(solution(4), 3);
    }

    #[test]
    fn test4() {
        assert_eq!(solution(6), 8);
    }

    #[test]
    fn test5() {
        assert_eq!(solution(10), 23);
    }
}