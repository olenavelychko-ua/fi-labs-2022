import java.io.File;
import java.util.*;

public class AffineCipher {

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> sortedMap) {

        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(sortedMap.entrySet());

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static int EuclidsAlgorithm(int a, int b) {
        int u2, v2, r, q;
        int rev_a = 0;
        int u0 = 1;
        int v0 = 0;
        int u1 = 0;
        int v1 = 1;
        int n = b;
        r = 1;
        while (b != 1) {
            q = a / b;
            r = a % b;
            a = b;
            b = r;
            if (b == 0) {
                b = 1;
                r = a;
            }
            u2 = u0 - q * u1;
            v2 = v0 - q * v1;
            u0 = u1;
            v0 = v1;
            u1 = u2;
            v1 = v2;
            rev_a = u2;
        }
        if (rev_a < 0) {
            rev_a = n + rev_a;
        }
        return rev_a;
    }

    public static ArrayList<Integer> congruenceSolution(int a, int b, int n) {
        ArrayList<Integer> answers = new ArrayList<Integer>();
        int bConst = b;
        int k = a;
        int koef = 1;
        int rev_a;
        int x = 0;
        while (k > 0) {
            if (a % k == 0 && n % k == 0) {
                a = a / k;
                n = n / k;
                koef = koef * k;
            }
            k--;
        }
        if (koef == 1) {
            rev_a = EuclidsAlgorithm(a, n);
            x = rev_a * b;
            while (x > n) x = x - n;
            answers.add(x);
        }
        if (koef != 1) {
            if (bConst % koef != 0) {
                answers.add(0);
                System.out.println(("Congruence has no solutions!"));
                return answers;
            } else if (bConst % koef == 0) {
                b = b / koef;
                rev_a = EuclidsAlgorithm(a, n);
                x = rev_a * b;
                while (x > n) x = x - n;
                while (koef > 0) {
                    int temp = x + (koef - 1) * n;
                    answers.add(temp);
                    koef--;
                }
            }
        }
        return answers;
    }

    public static LinkedHashMap<String, Integer> find5MostPopularBigrams(String text) {
        LinkedHashMap<String, Integer> mapbi = new LinkedHashMap<>();
        for (int k = 0; k + 1 < text.length(); k++) {
            String bigram = text.substring(k, k + 2);
            mapbi.put(bigram, mapbi.getOrDefault(bigram, 0) + 1);
        }
        HashMap<String, Integer> hm1 = sortByValue(mapbi);
        mapbi.clear();
        int q = 0;
        for (String str : hm1.keySet()) {
            mapbi.put(str, hm1.get(str));
            q += 1;
            if (q == 5) break;
        }
        return (mapbi);
    }


    public static void main(String[] args) throws Exception {
        File doc = new File("C:\\01.txt");
        Scanner obj = new Scanner(doc);
        StringBuilder text = new StringBuilder();
        while (obj.hasNextLine()) {
            text.append(obj.nextLine());
        }
        HashMap<String, Integer> mapbi = new HashMap<>();
        mapbi = find5MostPopularBigrams(text.toString());
        System.out.println(mapbi);
    }
}
