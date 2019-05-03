package service;

import domain.CardDeck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayPokerTest {
    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private PlayPoker playPoker;

    @Test
    public void isCalledColorCard(){
        CardDeck cardDeck = mock(CardDeck.class);
        cardDeck.setColor("Black");
        cardDeck.setNumber(10);
        cardDeck.setShape("Diamond");

        verify(cardDeck,times(1)).setColor(anyString());
    }

    @Test
    public void returnBlack(){
        CardDeck cardDeck = mock(CardDeck.class);

        when(cardDeck.getNumber()).thenReturn(13);
        assertThat(cardDeck.getNumber(),is(13));
    }

    @Test
    public void deckSizeCheck(){
        List<CardDeck> decks = new ArrayList<CardDeck>();
        List<CardDeck> decksSpyList = spy(decks);

        decksSpyList.add(new CardDeck("Spade","Black",5));
        int decksSize = decksSpyList.size();

        assertThat(decksSize,is(1));
        verify(decksSpyList,times(1)).size();
    }

    @Test
    public void handDeckSizeisFive(){
        List<CardDeck> decks = new ArrayList<CardDeck>();
        List<CardDeck> decksSpyList = spy(decks);
        playPoker.initDecks(decks);
        decksSpyList = playPoker.handDeck(decks);

        int decksSize = decksSpyList.size();

        assertThat(decksSize,is(5));
    }

    @Test
    public void checkFlush(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Spade","Black",5));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Spade","Black",8));
        decks.add(new CardDeck("Spade","Black",11));
        decks.add(new CardDeck("Spade","Black",7));

        assertThat(playPoker.findFlush(decks),is("Black Flush"));

    }
    @Test
    public void checkRoyalStraightFlush(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Spade","Black",9));
        decks.add(new CardDeck("Spade","Black",10));
        decks.add(new CardDeck("Spade","Black",11));
        decks.add(new CardDeck("Spade","Black",12));
        decks.add(new CardDeck("Spade","Black",13));

        assertThat(playPoker.findStraight(decks),is("Royal Straight Flush"));

    }
    @Test
    public void checkStraight(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Heart","Red",6));
        decks.add(new CardDeck("Spade","Black",9));
        decks.add(new CardDeck("Diamond","Red",8));
        decks.add(new CardDeck("Clover","Black",10));
        decks.add(new CardDeck("Spade","Black",7));

        assertThat(playPoker.findStraight(decks),is("Straight"));

    }
    @Test
    public void checkFullHouse(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Diamond","Red",2));
        decks.add(new CardDeck("Diamond","Red",2));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Heart","Red",7));
        decks.add(new CardDeck("Heart","Red",7));

        assertThat(playPoker.findPairs(decks),is("Full House"));
    }
    @Test
    public void checkTwoPairs(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Diamond","Red",3));
        decks.add(new CardDeck("Clover","Black",3));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Heart","Red",10));
        decks.add(new CardDeck("Clover","Black",10));

        assertThat(playPoker.findPairs(decks),is("TwoPairs"));
    }
    @Test
    public void checkFourCards(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Diamond","Red",5));
        decks.add(new CardDeck("Clover","Black",5));
        decks.add(new CardDeck("Spade","Black",5));
        decks.add(new CardDeck("Heart","Red",5));
        decks.add(new CardDeck("Clover","Black",10));

        assertThat(playPoker.findPairs(decks),is("FourCards"));
    }
    @Test
    public void checkOnePairs(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Diamond","Red",10));
        decks.add(new CardDeck("Clover","Black",3));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Heart","Red",10));
        decks.add(new CardDeck("Clover","Black",10));

        assertThat(playPoker.findPairs(decks),is("Triple"));
    }

    @Test
    public void checkTop(){
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Diamond","Red",5));
        decks.add(new CardDeck("Clover","Black",8));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Heart","Red",10));
        decks.add(new CardDeck("Clover","Black",11));

        assertThat(playPoker.findPairs(decks),is("Top"));
    }
    @Test
    public void pickOneCard(){
        List<CardDeck> decks = new ArrayList<CardDeck>();
        //given
        given(mockRepository.findCardByNumber(anyInt())).willReturn(new CardDeck("Clover","Black",12));
        //when
        CardDeck deck = playPoker.findCardByNumber(12);
        //then
        verify(mockRepository,atLeast(1)).findCardByNumber(anyInt());
        assertThat(deck.getNumber(), is(12));
    }
}