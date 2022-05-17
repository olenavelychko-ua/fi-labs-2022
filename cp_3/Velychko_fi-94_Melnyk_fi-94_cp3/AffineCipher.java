public class AffineCipher {

    public static int EuclidsAlgorithm(int a, int b) {
        int u2, v2, r, q;
        int rev_a = 0;
        int u0 = 1;
        int v0 = 0;
        int u1 = 0;
        int v1 = 1;
        int n=b;
        while (b != 1) {
            q = a / b;
            r = a % b;
            a = b;
            b = r;
            u2 = u0 - q * u1;
            v2 = v0 - q * v1;
            u0 = u1;
            v0 = v1;
            u1 = u2;
            v1 = v2;
            rev_a=u2;
        }
        if(rev_a<0) {
            rev_a = n + rev_a;
        }
        return rev_a;
    }

        public static void main(String[] args) throws Exception {
            int a = 321;
            int b = 748;
            int d = EuclidsAlgorithm(a, b);
            System.out.println("reverse to a modb is " + d);


        }
    }
