import java.util.*;
import java.util.function.BooleanSupplier;

@Deprecated
public class Dice implements BooleanSupplier {

    /** Dice I and Dice II. */
    //private final BitSet w1, w2;
    private final int[] d1, d2;

    public Dice(int[] d1, int[] d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        int cnt = scanner.nextByte();
        for (int i = 0; i < cnt; i++) {
            int[] d1 = new int[4], d2 = new int[4];
            for (int j = 0; j < 4; j++)
                d1[j] = scanner.nextByte();
            for (int j = 0; j < 4; j++)
                d2[j] = scanner.nextByte();
            printBool(new Dice(d1, d2).getAsBoolean());
        }
    }

    ///** @return d1.winCount == d2.winCount */
    // @Nullable
    protected Boolean whoIsWinner() {
        int w1 = 0, w2 = 0;
        for (int i = 0; i<4; i++) {
            var num1 = d1[i];
            for (int j = 0; j < 4; j++) {
                var num2 = d2[j];
                if (num1 > num2) {
                    w1++;
                } else if (num1 < num2) {
                    w2++;
                }
            }
        } return w1 == w2 ? null : w1 > w2;
    }

    @Override
    public boolean getAsBoolean() {
        var d1win = whoIsWinner();
        if (d1win == null) {
            return false;
        } int[] winner, loser;
        if (d1win) {
            winner = d1; loser = d2;
        } else {
            winner = d2; loser = d1;
        }
        int[] winnerD = new int[10];
        int[] loserD = new int[10];
        for (int i = 1; i <= 10; i++) {
            int index = i-1;
            for (int j = 0; j < 4; j++) {
                winnerD[index] += Integer.compare(i, winner[j]);
                loserD[index]  += Integer.compare(i, loser[j]);
            }
        }
        // we should:
        // find 4 that add winner to positive while add loser to negative
        return new WalkStack(winnerD, loserD).roll();
    }

    public static void printBool(boolean b) {
        System.out.println(b ? "yes" : "no");
    }

    private static final class WalkStack extends Stack<Integer> {
        private final int[] winner, loser;

        private WalkStack(int[] winner, int[] loser) {
            this.winner = winner;
            this.loser = loser;
        }

        public boolean roll() {
            for (int i = 0; i < 4; i++) this.push(i);
            if (verify()) return true;
            while (this.get(0) < 7) {
                var top = this.pop();
                if (top >= 9) {
                    continue;
                }
                this.push(top + 1);
                while (this.size() < 4) {
                    this.push(this.peek() + 1);
                }
                if (this.get(3) > 9) continue;
                if (this.size() == 4) {
                    if (verify()) return true;
                }
            } return false;
        }

        private boolean verify() {
            int w = 0, l = 0;
            for (var i = 0; i < 4; i++) {
                var k = this.get(i);
                w += winner[k];
                l += loser[k];
            } return w >0 && l<0;
        }
    }
}
