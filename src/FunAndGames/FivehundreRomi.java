package FunAndGames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class FivehundreRomi {
    public static int player1Score = 0;
    public static int player2Score = 0;
    public static ArrayList<Integer> discardPile = new ArrayList<>();
    public static final int WINNINGSCORE = 500;
    public static final int deckSize = 52;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] deck = createDeck();
        printDeck(deck);

        System.out.println("Welcome to 500 Rummy!");
        boolean gameOver = false;
        while (!gameOver) {
            player1Score = playerturn(player1Score, scanner, deck);
            if (player1Score >= WINNINGSCORE) {
                System.out.println("Player 1 wins!");
                break;
            }
            player2Score = playerturn(player2Score, scanner, deck);
            if (player2Score >= WINNINGSCORE) {
                System.out.println("Player 2 wins!");
                gameOver = true;
                break;
            }
        }
    }

    public static int[] createDeck() {
        int[] deck = new int[52];
        for (int i = 0; i < 52; i++) {
            deck[i] = i;
        }
        List<Integer> deckList = new ArrayList<>();
        for (int card : deck) {
            deckList.add(card);
        }
        Collections.shuffle(deckList);
        for (int i = 0; i < deckSize; i++) {
            deck[i] = deckList.get(i);
        }
        return deck;
    }

    public static void dealCards(int[] deck) {
        ArrayList<Integer> player1Hand = new ArrayList<>();
        ArrayList<Integer> player2Hand = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            player1Hand.add(i); // placeholder
            player2Hand.add(i + 7); // placeholder
        }
    }

    public static int playerturn(int playerScore, Scanner scanner, int[] deck) {
        // display current players name and score
        boolean hasPickedUpPile = false;
        System.out.println("Player's turn. Current score: " + playerScore);
        while (true) {
            System.out.println("Do you want to (1) draw from deck or (2) draw from discard pile or (3) Pick ud the whole pile? (Enter 1, 2 or 3)");
            System.out.println("Top card of discard pile: " + ANSI_GREEN + "Placeholdercard" + ANSI_RESET);
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("you drew from deck" + ANSI_GREEN + "Placeholdercard" + ANSI_RESET);
                break;
            } else if (choice == 2) {
                System.out.println("you drew from discard pile" + ANSI_GREEN + "Placeholdercard" + ANSI_RESET);
                break;
            } else if (choice == 3) {
                if (discardPile.isEmpty()) {
                    System.out.println("Discard pile is empty. Please choose another option.");
                    continue;
                }
                System.out.println("you picked up the whole pile" + ANSI_GREEN + "Placeholdercard" + ANSI_RESET);
                // add all cards from discard pile to player's hand
                discardPile.clear();
                hasPickedUpPile = true;
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            }
            System.out.println("Your hand: " + ANSI_GREEN + "Placeholderhand" + ANSI_RESET);
            System.out.println("What is your next move? (1) Make a set/add to a set. (2) Discard a card. (3) See all pairs on the table. (4)  ");
            choice = scanner.nextInt();
            while (true) {
                switch (choice) {
                    case 1:
                        System.out.println("You chose to make a set/add to a set.");
                        System.out.println("This is your hand" + showhand());
                        seeAvailableSets();
                    case 2:
                        System.out.println("You chose to discard a card. Which card do you want to discard? (enter card number)");
                        int cardToDiscard = scanner.nextInt();
                        if (cardToDiscard < 0 && cardToDiscard > 51) {
                            System.out.println("Invalid card number. Please enter a number between 0 and 51.");
                            continue;
                        } else if (!true /*check if player has the card*/) {
                            System.out.println("You don't have that card. Please choose a card from your hand.");
                            continue;
                        }
                        System.out.println("You discarded: " + ANSI_RED + cardToDiscard + ANSI_RESET);
                        discardPile.add(cardToDiscard);
                        // add card to discard pile
                        break;
                    case 3:
                        System.out.println("You chose to see all pairs on the table.");
                        seeAvailableSets();
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }
        }
        return playerScore;
    }

    public static int[] showhand() {

        int[] hand = new int[13];
        for (int i = 0; i < 13; i++) {
            hand[i] = i; // placeholder
        }
        printDeck(hand);
        return hand;
    }

    public static void seeAvailableSets() {

    }

    public static void printDeck(int[] deck) {
        for (int card : deck) {
            System.out.print(card + " ");
        }
        System.out.println();
    }

}
