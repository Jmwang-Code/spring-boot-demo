package allAlgorithm.ZmodelDynamicProgramming;

public class Bitwise {

    public int[] countBits(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i & (i - 1)] + 1;
        }
        return bits;

    }

    public static void main(String[] args) {
        Bitwise bitwise = new Bitwise();
        int[] ints = bitwise.countBits(5);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }

        System.out.println(5 & 4);
    }
}
