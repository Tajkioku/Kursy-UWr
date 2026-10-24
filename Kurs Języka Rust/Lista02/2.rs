// Kamil Wozniak 330601
fn number(bus_stops: &[(i32, i32)]) -> i32 {
    bus_stops
        .iter()
        .map(|&(inn, out)| inn - out)
        .sum()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn brak_przystankow() {
        assert_eq!(number(&[]), 0);
    }

    #[test]
    fn jeden_przystanek() {
        assert_eq!(number(&[(5, 0)]), 5);
    }

    #[test]
    fn kilka_przystankow() {
        assert_eq!(number(&[(2, 0), (1, 2), (5, 6)]), 0);
    }

    #[test]
    fn autobus_na_koncu_pusty() {
        assert_eq!(number(&[(4, 0), (0, 0)]), 4);
    }

    #[test]
    fn duzo_wsiadajacych_i_wysiadajacych() {
        assert_eq!(number(&[(10, 0), (10, 5), (0, 10), (1, 2)]), 4);
    }
}