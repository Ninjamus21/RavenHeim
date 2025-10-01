package FunAndGames;

import java.util.*;


public class FivehundreRomi {
    public static int player1Score = 0;
    public static int player2Score = 0;
    public static ArrayList<Integer> discardPile = new ArrayList<>();
    public static ArrayList<Integer> player1Hand = new ArrayList<>();
    public static ArrayList<Integer> player2Hand = new ArrayList<>();
    public static int counterForDeck = 14;
    public static List<List<Integer>> tableSets = new ArrayList<>();
    public static final int WINNINGSCORE = 500;
    public static final int deckSize = 52;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] deck = createDeck();
        discardPile.add(deck[0]); // add first card to discard pile
        dealCards(deck);
        System.out.println("Welcome to 500 Rummy!");
        boolean gameOver = false;
        while (!gameOver) {
            player1Score = playerturn(player1Score, scanner, deck, player1Hand);
            player1Score = calculateScore(player1Hand);
            if (player1Score >= WINNINGSCORE) {
                System.out.println("Player 1 wins!");
                break;
            }
            player2Score = playerturn(player2Score, scanner, deck, player2Hand);
            player2Score = calculateScore(player2Hand);
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
        for (int i = 0; i < 7; i++) {
            player1Hand.add(deck[i + 1]); // the first card is for the discard pile
            player2Hand.add(deck[i + 8]);

        }
        System.out.println("Player 1's hand:");
        printTypeandValue(player1Hand);
        System.out.println("\n");
        System.out.println("Player 2's hand:");
        printTypeandValue(player2Hand);
    }

    public static void printTypeandValue(ArrayList<Integer> cards) {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (int card : cards) {
            int suit = card / 13;
            int rank = card % 13;
            System.out.print("Card: " + ranks[rank] + " of " + suits[suit]);
        }
    }

    public static int playerturn(int playerScore, Scanner scanner, int[] deck, ArrayList<Integer> playerHand) {
        // display current players name and score
        boolean hasPickedUpPile = false;
        System.out.println("Player's turn. Current score: " + playerScore);
        while (true) {
            int lastCardInDiscardPile = discardPile.get(discardPile.size() - 1);
            System.out.println("Do you want to (1) draw from deck or (2) draw from discard pile or (3) Pick ud the whole pile? (Enter 1, 2 or 3)");
            System.out.println("Top card of discard pile: " + ANSI_GREEN + printTypeandValue(lastCardInDiscardPile) + ANSI_RESET);
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("you drew from deck" + ANSI_GREEN + deck[counterForDeck] + ANSI_RESET);
                counterForDeck++;
                break;
            } else if (choice == 2) {
                if (discardPile.isEmpty()) {
                    System.out.println("Discard pile is empty. Please choose another option.");
                    continue;
                }
                System.out.println("you drew from discard pile" + ANSI_GREEN + lastCardInDiscardPile + ANSI_RESET);
                discardPile.remove(discardPile.size() - 1);
                break;
            } else if (choice == 3) {
                if (discardPile.isEmpty()) {
                    System.out.println("Discard pile is empty. Please choose another option.");
                    continue;
                }
                System.out.println("you picked up the whole pile" + ANSI_GREEN + discardPile + ANSI_RESET);
                playerHand.addAll(discardPile); // add all cards from discard pile to player's hand
                discardPile.clear();
                hasPickedUpPile = true;
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            }
            System.out.println("Your hand: " + ANSI_GREEN + playerHand + ANSI_RESET + //make space between cards);
                    "\nYour current score: " + ANSI_GREEN + playerScore + ANSI_RESET);

            System.out.println("What is your next move? (1) Make a set/add to a set. (2) Discard a card. (3) See all pairs on the table. (4)  ");
            choice = scanner.nextInt();
            while (true) {
                switch (choice) {
                    case 1:
                        System.out.println("You chose to make a set/add to a set.");
                        seeAvailableSequences(playerHand);
                        makeASet(playerHand);

                    case 2:
                        System.out.println("You chose to discard a card. Which card do you want to discard? (enter card number)");
                        int cardToDiscard = scanner.nextInt();
                        if (cardToDiscard < 0 && cardToDiscard > 51) {
                            System.out.println("Invalid card number. Please enter a number between 0 and 51.");
                            continue;
                        } else if (!playerHand.contains(cardToDiscard)) {
                            System.out.println("You don't have that card. Please choose a card from your hand.");
                            continue;
                        } else {
                            System.out.println("You discarded: " + ANSI_RED + cardToDiscard + ANSI_RESET);
                            playerHand.remove(cardToDiscard); // remove card from player's hand
                            discardPile.add(cardToDiscard); // add card to discard pile
                            break;
                        }
                    case 3:
                        System.out.println("You chose to see all pairs on the table.");
                        seeAllTablesSets();
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }
        }
        return playerScore;
    }


    public static void seeAvailableSequences(ArrayList<Integer> playerHand) {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        Map<Integer, TreeSet<Integer>> suitToRanks = new HashMap<>();

        for (int card : playerHand) {
            int suit = card / 13;
            int rank = card % 13;
            suitToRanks.computeIfAbsent(suit, k -> new TreeSet<>()).add(rank);
        }

        for (var entry : suitToRanks.entrySet()) {
            List<Integer> sortedRanks = new ArrayList<>(entry.getValue());
            List<Integer> sequence = new ArrayList<>();
            for (int i = 0; i < sortedRanks.size(); i++) {
                if (sequence.isEmpty() || sortedRanks.get(i) == sequence.get(sequence.size() - 1) + 1) {
                    sequence.add(sortedRanks.get(i));
                } else {
                    if (sequence.size() >= 3) {
                        printSequence(entry.getKey(), sequence);
                    }
                    sequence.clear();
                    sequence.add(sortedRanks.get(i));
                }
            }
            if (sequence.size() >= 3) {
                printSequence(entry.getKey(), sequence);
            }
        }
    }

    private static void printSequence(int suit, List<Integer> sequence) {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        System.out.print("Sequence in " + suits[suit] + ": ");
        for (int r : sequence) {
            System.out.print(ranks[r] + " ");
        }
        System.out.println();
    }


    public static void makeASet(ArrayList<Integer> playerHand) {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        Map<Integer, List<Integer>> rankToSuits = new HashMap<>();

        for (int card : playerHand) {
            int suit = card / 13;
            int rank = card % 13;
            rankToSuits.computeIfAbsent(rank, k -> new ArrayList<>()).add(suit);
        }

        for (Map.Entry<Integer, List<Integer>> entry : rankToSuits.entrySet()) {
            if (entry.getValue().size() >= 3) {
                System.out.print("Set of " + ranks[entry.getKey()] + ": ");
                for (int suit : entry.getValue()) {
                    System.out.print(ranks[entry.getKey()] + " of " + suits[suit] + " ");
                }
                System.out.println();
                tableSets.add(new ArrayList<>(entry.getValue()));
            }
        }
    }

    public static void seeAllTablesSets() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        if (tableSets.isEmpty()) {
            System.out.println("No sets on the table.");
            return;
        }
        System.out.println("Sets on the table:");
        for (List<Integer> set : tableSets) {
            for (int card : set) {
                int suit = card / 13;
                int rank = card % 13;
                System.out.print(ranks[rank] + " of " + suits[suit] + " ");
            }
            System.out.println();
        }
    }

    public static int calculateScore(ArrayList<Integer> playerHand) {
        int score = 0;
        for (int card : playerHand) {
            int rank = card % 13;
            if (rank >= 0 && rank <= 9) { // 2 to 10
                score += rank + 2;
            } else if (rank >= 10 && rank <= 11) { // J, Q
                score += 10;
            } else if (rank == 12) { // K
                score += 10;
            } else if (rank == 0) { // A
                score += 15;
            }
        }
        return score;
    }

    public static void printDeck(int[] deck) {
        for (int card : deck) {
            System.out.print(card + " ");
        }
        System.out.println();
    }

}
