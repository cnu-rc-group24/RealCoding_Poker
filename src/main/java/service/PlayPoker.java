package service;
import domain.CardDeck;
import repository.MockRepository;

import java.util.List;


public class PlayPoker {
    private final MockRepository mockRepository;
    List<CardDeck> decks;

    public PlayPoker(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public void initDecks(){
        CardDeck cardDeck = null;
        for(int i=0; i < 4; i++){
            for(int j=0; j < 13; j++){
                switch (i){
                    case 1:
                        cardDeck = new CardDeck("Diamond","Red",j); break;
                    case 2:
                        cardDeck = new CardDeck("Heart","Red",j); break;
                    case 3:
                        cardDeck = new CardDeck("Clover","Black",j); break;
                    case 4:
                        cardDeck = new CardDeck("Spade","Black",j); break;
                }
                decks.add(cardDeck);
            }
        }
    }

    public List<CardDeck> handDeck(List<CardDeck> decks){
        List<CardDeck> handDecks = null;

        for(int i = 0; i < 5; i++){
            int pickedCardIndex = (int)(Math.random()*52);
            for(int j = 0; j < handDecks.size(); j++){
                if(handDecks.get(j) == decks.get(pickedCardIndex)) i--;
                else handDecks.add(decks.get(pickedCardIndex));
            }
        }

        return handDecks;
    }

    public void checking(List<CardDeck> handDecks){
        findPairs(handDecks);
        findFlush(handDecks);
        findStraight(handDecks);
    }

    private String findStraight(List<CardDeck> handDecks) {
        int firstNumber =  handDecks.get(0).getNumber();
        int checkLoyal = 0;
        for(int i = 0; i < 5; i++){
            firstNumber++;
            if(handDecks.get(i).getNumber() != firstNumber) return null;
            if(handDecks.get(0).getShape()==handDecks.get(i).getShape()) checkLoyal++;
        }
        if(checkLoyal == 5) return "Royal Straight Flush";
        return "Straight";
    }

    private String findFlush(List<CardDeck> handDecks) {
        boolean flush = true;
        for(int i = 0; i < 5; i++){
            if("Red" != handDecks.get(i).getShape()) flush=false;
        }
        for(int i = 0; i < 5; i++){
            if("Black" != handDecks.get(i).getShape()) flush=false;
        }
        if(flush) return handDecks.get(0).getShape()+" Flush";
        else return null;
    }

    private String findPairs(List<CardDeck> handDecks) {
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

        if (checkPairs==1) return"One Pair";
        else if(checkPairs==2) return "Two Pairs";
        else if(checkTriples==1&&checkPairs==1) return "Full House";
        else if(checkTriples == 1) return "Triple";
        else if(checkFourcard == 1) return "Four Cards";
        else return null;
    }
}
