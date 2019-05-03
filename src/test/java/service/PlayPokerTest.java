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
import java.util.Collections;
import java.util.Comparator;

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
        List<CardDeck> decks = playPoker.initDecks();
        List<CardDeck> decksSpyList = playPoker.handDeck(decks);

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

    @Test
    public void RedColorCardMockingCheck(){
        when(playPoker.findByColor(anyString())).thenReturn(new CardDeck("Diamond", "Red", 1));
        String CardColor = playPoker.findByColor("Red").getColor();
        assertThat(CardColor, is("Red"));
        verify(mockRepository, times(1)).findByColor(anyString());
    }

    @Test
    public void IfHandDianmondCardthenReturnMockingCheck(){
        given(mockRepository.findByShape("Diamond")).willReturn(new CardDeck("Diamond", "Red", 1));
        CardDeck cardDeck = playPoker.findByShape("Diamond");
        verify(mockRepository, atLeast(1)).findByShape(anyString());
        assertThat(cardDeck.getShape(), is("Diamond"));
    }

    @Test
    public void ThrowoutOneCardMyHandDeck(){
        List<CardDeck> decks = playPoker.initDecks();
        List<CardDeck> decksSpyList;

        List<CardDeck> testDecks = new ArrayList<CardDeck>();
        testDecks.add(new CardDeck("Diamond","Red",5));
        testDecks.add(new CardDeck("Clover","Black",8));
        testDecks.add(new CardDeck("Spade","Black",2));
        testDecks.add(new CardDeck("Heart","Red",10));

        given(mockRepository.ThrowOutHandCard(decks, 0)).willReturn(testDecks);
        decksSpyList = playPoker.ThrowOutHandCard(decks, 0);
        System.out.printf(decksSpyList.toString());
        assertThat(decksSpyList.size(), is(4));
    }

    @Test
    public void IFThrowoutOneCard_MyHandCardIsFiveCards_Then_RemainFourCards(){
        List<CardDeck> decks = playPoker.initDecks();
        List<CardDeck> myHandDeck = playPoker.handDeck(decks);
        myHandDeck = playPoker.ThrowOutToMyHandDeck(myHandDeck, 0);
        assertThat(myHandDeck.size(), is(4));
    }

    //Card가 같은 지 카드를 주기전에 확인해야하는가 주고나서 확인해야하는가?
    @Test
    public void IFReceiveOneCard_MyHandCardIsFiveCards_Then_RemainSixCards(){
        List<CardDeck> decks = playPoker.initDecks();
        List<CardDeck> myHandDeck = playPoker.handDeck(decks);
        myHandDeck = playPoker.ReceiveToMyHandDeck(myHandDeck, new CardDeck("Heart","Red",10));
        assertThat(myHandDeck.size(), is(6));
    }
    @Test
    public void ifSameShape(){
        List<CardDeck> decks1 = new ArrayList<CardDeck>();
        List<CardDeck> decks2 = new ArrayList<CardDeck>();

        decks1.add(new CardDeck("Diamond","Red",5));
        decks1.add(new CardDeck("Diamond","Red",7));
        decks1.add(new CardDeck("Diamond","Red",9));
        decks1.add(new CardDeck("Diamond","Red",2));
        decks1.add(new CardDeck("Diamond","Red",1));

        decks2.add(new CardDeck("Clover","Black",5));
        decks2.add(new CardDeck("Clover","Black",8));
        decks2.add(new CardDeck("Clover","Black",2));
        decks2.add(new CardDeck("Clover","Black",10));
        decks2.add(new CardDeck("Clover","Black",11));

        assertThat(playPoker.ifSameShape(decks1, decks2),is(decks2));
    }
    
    @Test
    public void checkTopValue() {
        List<CardDeck> decks = new ArrayList<CardDeck>();

        decks.add(new CardDeck("Diamond","Red",5));
        decks.add(new CardDeck("Clover","Black",8));
        decks.add(new CardDeck("Spade","Black",2));
        decks.add(new CardDeck("Heart","Red",11));
        decks.add(new CardDeck("Clover","Black",6));

        //카드덱을 테스트 코드 내에서 직접 주게 되므로 테스트 함수 내에서 정렬 실행
        Collections.sort(decks, new Comparator<CardDeck>() {
            @Override
            public int compare (CardDeck d1, CardDeck d2) {
                if (d1.getNumber() > d2.getNumber()) {
                    return 1;
                } else if (d1.getNumber() < d2.getNumber()) {
                    return -1;
                } else
                    return 0;
            }
        });

        //오름차순으로 정렬된 마지막 값이 Top값
        assertThat(decks.get(4).getNumber(), is(11));

    }

    @Test
    public void compareTopVersusTop() {
        List<CardDeck> decksOne = new ArrayList<CardDeck>();

        decksOne.add(new CardDeck("Diamond","Red",5));
        decksOne.add(new CardDeck("Clover","Black",8));
        decksOne.add(new CardDeck("Spade","Black",2));
        decksOne.add(new CardDeck("Heart","Red",11));
        decksOne.add(new CardDeck("Clover","Black",6));

        //카드덱을 테스트 코드 내에서 직접 주게 되므로 테스트 함수 내에서 정렬 실행
        Collections.sort(decksOne, new Comparator<CardDeck>() {
            @Override
            public int compare (CardDeck d1, CardDeck d2) {
                if (d1.getNumber() > d2.getNumber()) {
                    return 1;
                } else if (d1.getNumber() < d2.getNumber()) {
                    return -1;
                } else
                    return 0;
            }
        });

        List<CardDeck> decksTwo = new ArrayList<CardDeck>();

        decksTwo.add(new CardDeck("Diamond","Red",3));
        decksTwo.add(new CardDeck("Clover","Black",8));
        decksTwo.add(new CardDeck("Spade","Black",7));
        decksTwo.add(new CardDeck("Heart","Red",6));
        decksTwo.add(new CardDeck("Clover","Black",5));

        //카드덱을 테스트 코드 내에서 직접 주게 되므로 테스트 함수 내에서 정렬 실행
        Collections.sort(decksTwo, new Comparator<CardDeck>() {
            @Override
            public int compare (CardDeck d1, CardDeck d2) {
                if (d1.getNumber() > d2.getNumber()) {
                    return 1;
                } else if (d1.getNumber() < d2.getNumber()) {
                    return -1;
                } else
                    return 0;
            }
        });

        //서로의 Top값을 비교, 참을 확인
        assertThat((decksOne.get(4).getNumber() > decksTwo.get(4).getNumber()), is(true));
    }

}
