package service;

import domain.CardDeck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayPokerTest {
    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private PlayPoker playPoker;

    @Test
    public void isCard(){
        CardDeck cardDeck = mock(CardDeck.class);
        cardDeck.setColor("Black");
        cardDeck.setNumber(10);
        cardDeck.setShape("Diamond");

        verify(cardDeck,times(1)).setColor(anyString());
    }
    @Test
    public void testHandDeck(){
//        List<CardDeck> decks = mock(ArrayList.class);
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Spade","Black",5));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Spade","Black",8));
        decks.add(new CardDeck("Spade","Black",11));
        decks.add(new CardDeck("Spade","Black",7));

        when(playPoker.handDeck()).thenReturn(decks);
        List<CardDeck> myDeck = playPoker.handDeck();
        assertThat(myDeck.get(2).getShape(), is("Spade"));

    }

    @Test
    public void checkAllCards(){
        CardDeck cardDeck = mock(CardDeck.class);
        playPoker.initDecks();
    }
    @Test
    public void checkTwoPairs(){
        CardDeck cardDeck = mock(CardDeck.class);


    }

    @Test
    public void checkBet(){

    }
}