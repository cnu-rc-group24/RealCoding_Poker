package repository;

import domain.Card;

import java.util.List;
import java.util.Queue;

public interface MockRepository {
    /*
    void initDecks();

    List<Card> handDeck();

    void checking(List<Card> handDecks);

    String findStraight(List<Card> handDecks);

    String findFlush(List<Card> handDecks);

    String findPairs(List<Card> handDecks);
    */
    Queue<Card> suffleCards(String anyString);

    boolean OverCheck21Card(List anyList);
}
