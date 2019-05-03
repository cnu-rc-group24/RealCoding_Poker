package service;
import domain.CardDeck;
import repository.MockRepository;

import java.util.ArrayList;
import java.util.List;


public class PlayPoker {
    private final MockRepository mockRepository;

    enum Combine{
        Top, OnePair, TwoPair, Triple, Straight, Flush, FullHouse, FourCard, RoyalStraightFlush;
    }

    public PlayPoker(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public List<CardDeck> initDecks(){
        CardDeck card;
        List<CardDeck> cardDeck = new ArrayList<CardDeck>();
        for(int i=0; i < 4; i++){
            for(int j=0; j < 13; j++){
                switch (i){
                    case 0:
                        card = new CardDeck("Diamond","Red",j); cardDeck.add(card); break;
                    case 1:
                        card = new CardDeck("Heart","Red",j); cardDeck.add(card); break;
                    case 2:
                        card = new CardDeck("Clover","Black",j); cardDeck.add(card); break;
                    case 3:
                        card = new CardDeck("Spade","Black",j); cardDeck.add(card); break;
                }
            }
        }
        return cardDeck;
    }

    public List<CardDeck> handDeck(List<CardDeck> decks){
        List<CardDeck> handDecks = new ArrayList<CardDeck>();
        int size = 1;
        handDecks.add(decks.get((int)Math.random()*52));
        for(int i = 0; i < 4; i++){
            int pickedCardIndex = (int)(Math.random()*52);
            for(int j = 0; j < size; j++){
                if(handDecks.get(j) == decks.get(pickedCardIndex)) {
                    pickedCardIndex = (int)(Math.random()*52);
                    i--;
                }
            }
            handDecks.add(decks.get(pickedCardIndex));
            size++;
        }
        return handDecks;
    }

    public CardDeck pickOneCard (List<CardDeck> decks){
        int random = (int)Math.random()*decks.size();
        return decks.get(random);
    }
    public CardDeck findCardByNumber (int number){
        CardDeck card = mockRepository.findCardByNumber(number);
        return card;
    }
//    public void checking(List<CardDeck> handDecks){
//        findPairs(handDecks);
//        findFlush(handDecks);
//        findStraight(handDecks);
//    }

    public String findStraight(List<CardDeck> handDecks) {
        int minNumber = handDecks.get(0).getNumber();
        int checkStraight = 0;
        for(int i = 0; i < 5; i++){
            if(minNumber >handDecks.get(i).getNumber())
                minNumber = handDecks.get(i).getNumber();
        }
        int checkLoyal = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(handDecks.get(j).getNumber() == minNumber) { checkStraight++; break;}
            }
            if(handDecks.get(0).getShape()==handDecks.get(i).getShape()) checkLoyal++;
            minNumber++;
        }
        if(checkLoyal == 5 && checkStraight==5) return "Royal Straight Flush";
        if(checkStraight == 5) return "Straight";
        else return null;
    }

    public String findFlush(List<CardDeck> handDecks) {
        boolean flush = false;
        for(int i = 0; i < 5; i++){
            if(handDecks.get(i).getColor().equals("Red")) flush=true;
        }
        for(int i = 0; i < 5; i++){
            if(handDecks.get(i).getColor().equals("Black")) flush=true;
        }
        if(flush) return handDecks.get(0).getColor()+" Flush";
        else return null;
    }

    public String findPairs(List<CardDeck> handDecks) {
        int[] numbers = new int[5];
        int[] countPairs = {0,0,0,0,0};
        int max = 0;
        int checkPairs=0,checkTriples=0,checkFourcard=0;

        for(int i = 0; i < 5; i++){
            numbers[i] = handDecks.get(i).getNumber();
        }
        for(int i = 0; i < 5; i++){
            for(int j = i+1; j < 5; j++){
                if(numbers[i] == numbers[j]) countPairs[i]++;
            }
        }
        for(int i =0; i < 5; i++){
            if(countPairs[i]==1) checkPairs++;
            if(countPairs[i]==2) checkTriples++;
            if(countPairs[i]==3) checkFourcard++;
        }

        if(checkFourcard == 1) return "FourCards";
        else if(checkTriples==1 && checkPairs==2) return "Full House";
        else if(checkTriples == 1) return "Triple";
        else if(checkPairs==2) return "TwoPairs";
        else if (checkPairs==1) return"OnePair";
        else return "Top";
    }

    public void Play(){
        System.out.println("카드 덱 생성중...");
        List<CardDeck> decks = initDecks();
        System.out.println("덱을 5장 드로우!");
        handDeck(decks);
        if(findStraight(decks)!=null)
            System.out.println(findStraight(decks));
        else if(findFlush(decks)!=null)
            System.out.println(findFlush(decks));
        else
            System.out.println(findPairs(decks));
    }

    public CardDeck findByColor(String color){
        CardDeck cardDeck = mockRepository.findByColor(color);
        return cardDeck;
    }

    public CardDeck findByShape(String shape){
        CardDeck cardDeck = mockRepository.findByShape(shape);
        return cardDeck;
    }

    public CardDeck findByNumber(int number){
        CardDeck cardDeck = mockRepository.findByNumber(number);
        return cardDeck;
    }

    //number: 0 ~ 4
    public List<CardDeck> ThrowOutHandCard(List<CardDeck> decks, int number){
        List<CardDeck> my_handDeck = handDeck(decks);
        my_handDeck.remove(number);
        return my_handDeck;
    }
}
