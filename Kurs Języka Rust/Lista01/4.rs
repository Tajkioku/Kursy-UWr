// Kamil Wozniak 330601
fn dna_strand(dna: &str) -> String {
    dna.chars()
        .map(|c| match c {
            'A' => 'T',
            'T' => 'A',
            'C' => 'G',
            'G' => 'C',
            _ => c,
        })
        .collect()
}

fn main() {
    let dna = "ATCG";
    println!("Komplementarne DNA: {}", dna_strand(dna));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test1() {
        assert_eq!(dna_strand("A"), "T");
    }

    #[test]
    fn test2() {
        assert_eq!(dna_strand("AT"), "TA");
    }

    #[test]
    fn test3() {
        assert_eq!(dna_strand("GCTA"), "CGAT");
    }

    #[test]
    fn test4() {
        assert_eq!(dna_strand("ATCG"), "TAGC");
    }

    #[test]
    fn test5() {
        assert_eq!(dna_strand("AAAATTTT"), "TTTTAAAA");
    }
}
