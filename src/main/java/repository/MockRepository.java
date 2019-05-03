package repository;

        import domain.Card;
        import domain.CardDeck;

        import java.util.List;

public interface MockRepository {

    void initDecks();

    List<CardDeck> handDeck();

    void checking(List<CardDeck> handDecks);

    String findStraight(List<CardDeck> handDecks);

    CardDeck findCardByNumber (int number);
    String findFlush(List<CardDeck> handDecks);

    String findPairs(List<CardDeck> handDecks);
    CardDeck pickOneCard (List<CardDeck> decks);
    void Play(List<CardDeck> decks);

    CardDeck findByColor(String color);
    CardDeck findByShape(String Shape);
    CardDeck findByNumber(int number);
    List<CardDeck> ThrowOutHandCard(List<CardDeck> decks, int number);
    List<CardDeck> ThrowOutToMyHandDeck(List<CardDeck> HandDeck, int number);
    List<CardDeck> ReceiveToMyHandDeck(List<CardDeck> HandDeck, CardDeck card);

}
