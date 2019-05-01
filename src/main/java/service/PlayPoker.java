package service;

import domain.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayPokerTest {
    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private PlayPoker playPoker;

    @Test
    public void CheckCardDeck(){
        playPoker = new PlayPoker(mockRepository);
        assertThat(playPoker.decks.size(), is(52));
    }


    @Test
    public void isCard(){
        Card card = mock(Card.class);
        card.setColor("Black");
        card.setNumber(10);
        card.setShape("Diamond");

        verify(card,times(1)).setColor(anyString());
    }

    @Test
    public void CheckGetLastCardinDeckTest(){
        playPoker = new PlayPoker(mockRepository);
        assertThat(playPoker.decks.get(51), anything());
    }

    @Test
    public void CheckCardsSuffleSizeTest(){
        playPoker = new PlayPoker(mockRepository);
        playPoker.suffled_MyDeck();
        assertThat(playPoker.suffled_decks.size(), is(52));
    }

    public void CheckCardsSuffleMockTest(){
        List<Card> decks = mock(ArrayList.class);

    }

    @Test
    public void CheckingSettingCardShape(){
        Card card = mock(Card.class);

        card.setShape("Diamond");
        verify(card).setShape("Diamond");
    }

    @Test
    public void Over21NumberCardMockTest(){
        List<Card> handcards = mock(ArrayList.class);

        when(playPoker.OverCheck21Card(anyList())).thenReturn(true);
        boolean checkOver21 = playPoker.OverCheck21Card(handcards);
        assertThat(checkOver21, is(true));
        verify(mockRepository, times(1)).OverCheck21Card(anyList());
    }


    /*
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
    */
}
