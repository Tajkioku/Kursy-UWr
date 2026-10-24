// Kamil Wozniak 330601
fn count_red_beads(n: u32) -> u32 {
    if n < 2 {
        return 0;
    }
    2 * (n - 1)
}

fn main() {
    let n = 5;
    let result = count_red_beads(n);

    println!("Liczba czerwonych koralikow: {}", result);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        assert_eq!(count_red_beads(0), 0);
    }

    #[test]
    fn test2() {
        assert_eq!(count_red_beads(1), 0);
    }

    #[test]
    fn test3() {
        assert_eq!(count_red_beads(2), 2);
    }

    #[test]
    fn test4() {
        assert_eq!(count_red_beads(5), 8);
    }

    #[test]
    fn test5() {
        assert_eq!(count_red_beads(10), 18);
    }
}
