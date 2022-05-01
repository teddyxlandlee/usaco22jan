import java.util.Arrays;
import java.util.Scanner;
import java.util.function.LongSupplier;

public class Corn implements LongSupplier {
    private final long[] saturation;
    private long corns;

    public Corn(long[] saturation) {
        this.saturation = saturation;
    }

    @Override
    public long getAsLong() {
        if (findMaxIndex() < 0) return 0L;
        int maxIndex;
        while ((maxIndex = findMaxIndex()) >= 0) {
            if (reduce(maxIndex)) corns++;
            else return -1L;
        } return corns*2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final var samples = scanner.nextInt();
        for (int i = 0; i < samples; i++) {
            final var cowCnt = scanner.nextInt();
            final long[] saturation = new long[cowCnt];
            for (int j = 0; j < cowCnt; j++) {
                saturation[j] = scanner.nextLong();
            }
            System.out.println(new Corn(saturation).getAsLong());
        }
    }

    private boolean reduce(int index) {
        var l = saturation;
        long l0 = l[index], l1 = l[index+1];
        if (l0 <= 0L || l1 <= 0L) return false;
        l[index] = l0-1;
        l[index+1] = l1-1;
        return true;
    }

    // if all same: -1
    public int findMaxIndex() {
        long[] l = this.saturation;
        var len = l.length;
        long max = l[0];
        int maxIndex = 0;
        for (int i = 1; i<len; i++) {
            long l1 = l[i];
            if (max < l1) {
                max = l1;
                maxIndex = i;
            }
        }
        if (l[len-1] == max) {
            if (Arrays.stream(l).distinct().count() == 1L) return -1;
            return len-2;
        }
        if (maxIndex == 0) return 0;
        return l[maxIndex - 1] > l[maxIndex + 1] ? maxIndex - 1 : maxIndex;
    }
}
