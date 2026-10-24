fn count_bits(integer: i64) -> u32 {
    integer.count_ones()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        assert_eq!(count_bits(0), 0);
    }

    #[test]
    fn test2() {
        assert_eq!(count_bits(1), 1);
    }

    #[test]
    fn test3() {
        assert_eq!(count_bits(5), 2);
    }

    #[test]
    fn test4() {
        assert_eq!(count_bits(9), 2);
    }

    #[test]
    fn tes5() {
        assert_eq!(count_bits(10), 2);
    }
}