package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CardDeck {
    private String shape;
    private String color;
    private int number;
}
