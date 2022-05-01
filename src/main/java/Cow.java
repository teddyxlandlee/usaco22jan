import java.util.*;

public class Cow implements Runnable {
    private final char[] cows;
    private final char[] guess;

    public Cow(char[] cows, char[] guess) {
        this.cows = cows;
        this.guess = guess;
    }

    private static void addOne(Map<Character, Integer> map, char key) {
        Character k = key;
        if (map.containsKey(k)) {
            map.put(k, map.get(k) + 1);
        } else {
            map.put(k, 1);
        }
    }

    public static void main(String[] args) {
        // Input
        Scanner scanner = new Scanner(System.in);
        char[] cows = new char[9], guess = new char[9];
        for (int i = 0; i < 9; i += 3) {
            System.arraycopy(scanner.next().toCharArray(), 0, cows, i, 3);
        }
        for (int i = 0; i < 9; i += 3) {
            System.arraycopy(scanner.next().toCharArray(), 0, guess, i, 3);
        }
        new Cow(cows, guess).run();
    }

    private static class CowMap {
        private final int correct;
        private final Map<Character, Integer> cowsRate, guessRate;

        private CowMap(int correct,
                       Map<Character, Integer> cowsRate,
                       Map<Character, Integer> guessRate) {
            this.correct = correct;
            this.cowsRate = cowsRate;
            this.guessRate = guessRate;
        }
    }

    protected CowMap readCowMap() {
        int c = 0;
        Map<Character, Integer> cowsRate = new HashMap<>(),
                                guessRate = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            char cow = this.cows[i];
            char guess = this.guess[i];
            if (cow == guess)
                c++;
            else {
                addOne(cowsRate, cow);
                addOne(guessRate, guess);
            }
        } return new CowMap(c, cowsRate, guessRate);
    }

    public void run() {
        var cowMap = readCowMap();
        int yellowBlocks = 0;
        for (var entry : cowMap.cowsRate.entrySet()) {
            Character k = entry.getKey();
            int cRate = entry.getValue();
            var guessRateMap = cowMap.guessRate;
            if (guessRateMap.containsKey(k)) {
                yellowBlocks += Math.min(cRate, guessRateMap.get(k));
            }
        }
        System.out.println(cowMap.correct);
        System.out.println(yellowBlocks);
    }
}
