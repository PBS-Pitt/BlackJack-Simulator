import java.util.Scanner;

public class Blackjack {


    public static void main(String [] args){

        int[] input = Input();
        System.out.println();
        System.out.println("Starting Blackjack with " + input[0] + 
            " rounds and " + input[1] + " decks in the shoe\n");

        BlackjackCards shoe = Shoe(input[1]);
        BlackjackCards discardPile = new BlackjackCards(shoe.capacity());
        BlackjackCards playerHand = new BlackjackCards(2);
        BlackjackCards dealerHand = new BlackjackCards(2);

        int playerWins = 0;
        int dealerWins = 0;
        int draws = 0;
        String player = "Player";
        String dealer = "Dealer";

        for (int i = 0; i < input[0]; i++){
            dealerHand.enqueue(shoe.dequeue()); playerHand.enqueue(shoe.dequeue());
            dealerHand.enqueue(shoe.dequeue()); playerHand.enqueue(shoe.dequeue());


            if (i < input[2]){
                System.out.println("Round " + i + " beginning");
                System.out.println("Player: " + playerHand.toString() + " : " + playerHand.getValue());
                System.out.println("Dealer: " + dealerHand.toString() + " : " + dealerHand.getValue());
            }


            if (dealerHand.getValue() == 21 && playerHand.getValue() != 21){
                dealerWins++;
                if (i < input[2])
                    System.out.println("Result: Dealer Blackjack wins!\n");
            }else if (playerHand.getValue() == 21 && dealerHand.getValue() != 21){
                playerWins++;
                if (i < input[2])
                    System.out.println("Result: Player Blackjack wins!\n");
            }else if (playerHand.getValue() == 21 && dealerHand.getValue() == 21){
                draws++;
                if (i < input[2])
                    System.out.println("Result: Push!\n");
            }else if (dealerHand.getValue() > 21){
                playerWins++;
                if (i < input[2])
                    System.out.println("Dealer BUSTS: " + dealerHand.toString() + " : " + dealerHand.getValue() + "\nResult: Player wins!\n");
            }else if (playerHand.getValue() > 21){
                dealerWins++;
                if (i < input[2])
                    System.out.println("Player BUSTS: " + playerHand.toString() + " : " + playerHand.getValue() + "\nResult: Dealer wins\n");
            }else{
                if(Hit(playerHand, shoe, i, input[2], player) == false){
                    dealerWins++;
                    if (i < input[2])
                        System.out.println("Result: Dealer wins!\n");
                }else if(Hit(dealerHand, shoe, i, input[2], dealer) == false){
                    playerWins++;
                    if (i < input[2])
                        System.out.println("Result: Player wins!\n");
                }else{
                    if(playerHand.getValue() == dealerHand.getValue()){
                        draws++;
                        if (i < input[2])
                            System.out.println("Result: Push!\n");
                    }else if(playerHand.getValue() < dealerHand.getValue()){
                        dealerWins++;
                        if (i < input[2])
                            System.out.println("Result: Dealer wins!\n");
                    }else if(playerHand.getValue() > dealerHand.getValue()){
                        playerWins++;
                        if (i < input[2])
                            System.out.println("Result: Player wins!\n");
                    }
                    }
            }

            Discard(discardPile, dealerHand, playerHand);

          if (shoe.size() <= (shoe.capacity() / 4)){
                Reshuffle(discardPile, shoe, i);
            }
        }

        System.out.println("After " + input[0] + " rounds, here are the results: ");
        System.out.println("Dealer Wins: " + dealerWins);
        System.out.println("Player Wins: " + playerWins);
        System.out.println("Pushes: " + draws);
        System.out.println("-------------------");
    }

    public static int[] Input(){
        Scanner scanner = new Scanner(System.in);
        int[] input = new int[3];
        for (int i = 0; i < 3; i++){
            input[i] = scanner.nextInt();
        }
        scanner.close();
        return input;
    }
     public static BlackjackCards Shoe(int decks){
        BlackjackCards shoe = new BlackjackCards(52 * decks);
        for (int i = 0; i <  decks; i++){
            for (Card.Suits s: Card.Suits.values()){
                for (Card.Ranks r: Card.Ranks.values()){
                    shoe.enqueue( new Card(s, r));
                }
            }
        }

        shoe.shuffle();
        return shoe;
     }

    public static void Discard(BlackjackCards pile, BlackjackCards dealerHand, BlackjackCards playerHand){
        while (!dealerHand.isEmpty()){
            pile.enqueue(dealerHand.dequeue());
        }
        while (!playerHand.isEmpty()){
            pile.enqueue(playerHand.dequeue());
        }
    }

    public static void Reshuffle(BlackjackCards pile, BlackjackCards shoe, int round){
        
        for (int i = 0; i < pile.size(); i++){
            shoe.enqueue(pile.dequeue());
        }
        shoe.shuffle();
        System.out.println("Reshuffling the shoe in round " + round + "\n");
    }

    public static boolean Hit(BlackjackCards hand, BlackjackCards shoe, int i, int trace, String person){
        boolean check = true;
        boolean result = true;
        while (check){
            if (hand.getValue() < 17){
                Card item = shoe.dequeue();
                hand.enqueue(item);
                if (i < trace)
                    System.out.println(person + " hits: " + item);
            }else if(hand.getValue() <= 21){
                if (i < trace)
                    System.out.println(person + " STANDS: " + hand.toString() + " : " + hand.getValue());
                check = false;
                result = true;
            }else{
                if (i < trace)
                    System.out.println(person + " BUSTS: " + hand.toString() + " : " + hand.getValue());
                check = false;
                result = false;
            }
        }
        return result;
    }

    
    
}
