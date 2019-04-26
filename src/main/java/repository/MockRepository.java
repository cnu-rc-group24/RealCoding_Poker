package repository;

import domain.CardDeck;

import java.util.List;

public interface MockRepository {

    void initDecks();

    List<CardDeck> handDeck();

    void checking(List<CardDeck> handDecks);

    String findStraight(List<CardDeck> handDecks);

    String findFlush(List<CardDeck> handDecks);

    String findPairs(List<CardDeck> handDecks);

}
