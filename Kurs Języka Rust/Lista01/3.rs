// Kamil Wozniak 330601
fn square_area_to_circle(area: f64) -> f64 {
    std::f64::consts::PI * area / 4.0
}

fn main() {
    let area = 16.0;
    let result = square_area_to_circle(area);

    println!("Pole kola: {:.8}", result);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        assert!((square_area_to_circle(0.0) - 0.0).abs() < 1e-4);
    }

    #[test]
    fn test2() {
        assert!((square_area_to_circle(4.0) - 3.14159265).abs() < 1e-4);
    }

    #[test]
    fn test3() {
        assert!((square_area_to_circle(16.0) - 12.56637061).abs() < 1e-4);
    }

    #[test]
    fn test4() {
        assert!((square_area_to_circle(1.0) - 0.78539816).abs() < 1e-4);
    }

    #[test]
    fn test5() {
        assert!((square_area_to_circle(10.0) - 7.853981634).abs() < 1e-4);
    }
}