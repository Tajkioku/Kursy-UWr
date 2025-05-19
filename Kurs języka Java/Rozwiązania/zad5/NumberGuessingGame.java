import java.util.*;
import java.util.logging.*;
import java.io.*;

/* Game stats */
class GameState {
    private List<Integer> secretPermutation;
    private int attempts;
    private List<String> history;
    private int maxAttempts;
    
    /* Statyski */
    public GameState(int N) {
        this.secretPermutation = generatePermutation(N);
        this.attempts = 0;
        this.history = new ArrayList<>();
        this.maxAttempts = N * N;
    }

    /* Generate ppermutation */
    private List<Integer> generatePermutation(int N) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }

    public List<Integer> getSecretPermutation() {
        return secretPermutation;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        attempts++;
        assert attempts <= maxAttempts : "Number of attempts exceeded max allowed!";
    }

    public void addToHistory(String entry) {
        history.add(entry);
    }

    public List<String> getHistory() {
        return history;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}

/* Exceptions */
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class DuplicateNumberException extends InvalidInputException {
    public DuplicateNumberException() {
        super("Input contains duplicate numbers.");
    }
}

class OutOfRangeNumberException extends InvalidInputException {
    public OutOfRangeNumberException() {
        super("Input contains numbers out of allowed range.");
    }
}

/* The main game class. */
public class NumberGuessingGame {
    private static final Logger logger = Logger.getLogger(NumberGuessingGame.class.getName());

    public static void main(String[] args) {
        setupLogger();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Number Guessing Game!");

        boolean playAgain; 
        do {
            int difficulty = chooseDifficulty(scanner);
            GameState gameState = new GameState(difficulty);

            logger.info("Game started with difficulty: " + difficulty);
            System.out.println("A permutation has been generated. Try to guess it!");

            boolean roundWon = false;
            while (gameState.getAttempts() < gameState.getMaxAttempts() && !roundWon) {
                System.out.print("Enter your guess (123...): ");
                String guess = scanner.next();

                /* Check the answet */
                try {
                    List<Integer> playerGuess = parseGuess(guess, difficulty);
                    gameState.incrementAttempts();

                    if (playerGuess.equals(gameState.getSecretPermutation())) {
                        System.out.println("Congratulations! You've guessed correctly.");
                        gameState.addToHistory("Guess " + guess + " - Success");
                        logger.info("Player guessed the correct permutation.");
                        roundWon = true;
                    } else {
                        int incorrectPositions = countIncorrectPositions(gameState.getSecretPermutation(), playerGuess);
                        int randomIncorrectPos = getRandomIncorrectPosition(gameState.getSecretPermutation(), playerGuess);
                        System.out.println("Incorrect positions: " + incorrectPositions);
                        System.out.println("Hint: Number " + playerGuess.get(randomIncorrectPos) + " should be to the left or right.");
                        String historyEntry = "Guess " + guess + " - " + incorrectPositions + " incorrect positions. Hint: " +
                        
                        playerGuess.get(randomIncorrectPos) + " should be to the left or right.";
                        gameState.addToHistory(historyEntry);
                        logger.info(historyEntry); // Add log for this attepmpt
                    }
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    logger.warning("Invalid input by player: " + e.getMessage());
                }
            }

            /* Game over */
            if (!roundWon) {
                System.out.println("Game over! You've exceeded the maximum attempts.");
                logger.info("Game ended with maximum attempts reached.");
            }

            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        } while (playAgain);

        System.out.println("Thank you for playing!");
        logger.info("Game session ended.");
    }

    /* Difficulty */
    private static int chooseDifficulty(Scanner scanner) {
        int difficulty = 0;
        while (difficulty < 3 || difficulty > 9) {
            System.out.print("Choose difficulty level (3-9): ");
            try {
                difficulty = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return difficulty;
    }

    /* Functions to check positions and parse guess */
    private static List<Integer> parseGuess(String guess, int N) throws InvalidInputException {
        if (guess.length() != N) {
            throw new InvalidInputException("Input must be exactly " + N + " digits.");
        }

        List<Integer> playerGuess = new ArrayList<>();
        for (char c : guess.toCharArray()) {
            int number = Character.getNumericValue(c);
            if (number < 1 || number > N) throw new OutOfRangeNumberException();
            if (playerGuess.contains(number)) throw new DuplicateNumberException();
            playerGuess.add(number);
        }
        return playerGuess;
    }

    private static int countIncorrectPositions(List<Integer> secret, List<Integer> guess) {
        int count = 0;
        for (int i = 0; i < secret.size(); i++) {
            if (!secret.get(i).equals(guess.get(i))) {
                count++;
            }
        }
        return count;
    }

    private static int getRandomIncorrectPosition(List<Integer> secret, List<Integer> guess) {
        List<Integer> incorrectPositions = new ArrayList<>();
        for (int i = 0; i < secret.size(); i++) {
            if (!secret.get(i).equals(guess.get(i))) {
                incorrectPositions.add(i);
            }
        }
        return incorrectPositions.get(new Random().nextInt(incorrectPositions.size()));
    }

    /* Setup logs */
    private static void setupLogger() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
            FileHandler fileHandler = new FileHandler("rozgrywka.log", true);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.info("Logger initialized.");
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
}
