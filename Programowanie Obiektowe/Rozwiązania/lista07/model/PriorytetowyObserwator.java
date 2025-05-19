package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

import java.io.Serializable;

public interface PriorytetowyObserwator extends Obserwator, Serializable {
    int getPriority();
}
