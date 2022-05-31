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

    public static int getKeyByChar1(char orig) {
        int temp = (int) orig - 1072;
        if (temp > 25) temp--;
        return temp;
    }

    public static int getKeyByChar2(char orig) {
        int temp = (int) orig - 1072;
        if (temp > 25) temp--;
        if (temp == 26) temp++;
        else if (temp == 27) temp--;
        return temp;
    }

    public static char getCharByKey1(int key) {
        if (key > 24) key += 1;
        return (char) (key + 1072);
    }

    public static char getCharByKey2(int key) {
        if (key == 26) key += 1;
        else if (key == 27) key -= 1;
        if (key > 25) key += 1;
        return (char) (key + 1072);
    }

    public static int modularArithmetic(int num, int mod) {
        while (num > mod) num = num - mod;
        while (num < 0) num = num + mod;
        return num;
    }

    public static int[] EuclidsAlgorithm(int a, int b) {
        int[] gcdAndrev = new int[2];
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
        rev_a = modularArithmetic(rev_a, n);
        gcdAndrev[0] = r;
        gcdAndrev[1] = rev_a;
        return gcdAndrev;
    }

    public static ArrayList<Integer> congruenceSolution(int a, int b, int n) {
        ArrayList<Integer> answers = new ArrayList<Integer>();
        int[] gcdAndrev = new int[2];
        gcdAndrev = EuclidsAlgorithm(a, n);
        int k = gcdAndrev[0];
        int i = 1;
        int rev_a = gcdAndrev[1];
        int x = 0;
        if (k == 1) {
            x = rev_a * b;
            x = modularArithmetic(x, n);
            answers.add(x);
        }
        if (k != 1) {
            a = a / k;
            n = n / k;
            if (b % k != 0) {
                answers.add(0);
                //System.out.println(("Congruence has no solutions!"));
                return answers;
            } else if (b % k == 0) {
                b = b / k;
                gcdAndrev = EuclidsAlgorithm(a, n);
                x = gcdAndrev[1] * b;
                x = modularArithmetic(x, n);
                while (i < k) {
                    int temp = x + (i - 1) * n;
                    answers.add(temp);
                    i++;
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
            if (q == 25) break;
        }
        System.out.println("Top 25 popular bigrams " +mapbi);
        return (mapbi);
    }

    public static ArrayList<Integer> findingKey(HashMap<String, Integer> mapbi1) {
        StringBuilder arrayBi = new StringBuilder();
        ArrayList<Integer> answers = new ArrayList<Integer>();
        ArrayList<Integer> aAndb = new ArrayList<Integer>();
        ArrayList<Integer> aAll = new ArrayList<Integer>();
        int[] gcdAndrev = new int[2];
        char[] arrayLang = {'с', 'т', 'н', 'о', 'т', 'о', 'н', 'а', 'е', 'н'};
        int m = (int) Math.pow(31, 2);
        int x, y, b, yBi1, xBi1, yBi2, xBi2;
        for (String str : mapbi1.keySet()) {
            arrayBi.append(str);
        }
        for (int i = 0; i < 8; i += 2) {
            for (int j = 0; j < 2; j += 2) {
                for (int k = j + 2; k < 50; k += 2) {
                    xBi1 = getKeyByChar2(arrayLang[i]) * 31 + getKeyByChar2(arrayLang[i + 1]);
                    xBi2 = getKeyByChar2(arrayLang[i + 2]) * 31 + getKeyByChar2(arrayLang[i + 3]);
                    yBi1 = getKeyByChar2(arrayBi.charAt(j)) * 31 + getKeyByChar2(arrayBi.charAt(j + 1));
                    yBi2 = getKeyByChar2(arrayBi.charAt(k)) * 31 + getKeyByChar2(arrayBi.charAt(k + 1));
                    x = xBi1 - xBi2;
                    x = modularArithmetic(x, m);
                    y = yBi1 - yBi2;
                    y = modularArithmetic(y, m);
                    aAll = congruenceSolution(x, y, m);
                    if (aAll.get(0) != 0) {
                        gcdAndrev = EuclidsAlgorithm(aAll.get(0), m);
                        if (aAll.size() == 1 && gcdAndrev[0] == 1) {
                            b = yBi1 - aAll.get(0) * xBi1;
                            b = modularArithmetic(b, m);
                            aAndb.add(aAll.get(0));
                            aAndb.add(b);
                        }
                    } else if (aAll.size() > 1) {
                        int c = 0;
                        while (c < aAll.size()) {
                            gcdAndrev = EuclidsAlgorithm(aAll.get(c), m);
                            if (gcdAndrev[0] == 1 && aAll.get(c) != 0) {
                                b = yBi1 - aAll.get(c) * xBi1;
                                b = modularArithmetic(b, m);
                                aAndb.add(aAll.get(c));
                                aAndb.add(b);

                            }
                            c++;
                        }
                    }
                }

            }
        }
        return aAndb;
    }

    public static int recognizer(String text) {
        LinkedHashMap<Character, Double> mapLet = new LinkedHashMap<>();
        int num = 0;
        double tempO = 0;
        double tempA = 0;
        double tempE = 0;
        for (int k = 0; k + 1 < text.length(); k++) {
            mapLet.put(text.charAt(k), mapLet.getOrDefault(text.charAt(k), 0.0) + 1.0);
            num++;
        }
        for (char ch : mapLet.keySet()) {
            if (ch == 'о') tempO = mapLet.get(ch);
            if (ch == 'а') tempA = mapLet.get(ch);
            if (ch == 'е') tempE = mapLet.get(ch);
        }
        tempO = (tempO * 100) / num;
        tempA = (tempA * 100) / num;
        tempE = (tempE * 100) / num;

        if(tempO<9.28 && tempA<8.66 && tempE<8.10){
            return 0;
        }
        else return 1;

    }

    public static String decryption(String text) {
        LinkedHashMap<String, Integer> biMap = new LinkedHashMap<>();
        ArrayList<Integer> keyParams = new ArrayList<Integer>();
        StringBuilder text1 = new StringBuilder();
        StringBuilder pureText = new StringBuilder();
        int[] gcdAndrev = new int[2];
        text1.append(text);
        int y, x1, x2;
        int m = (int) Math.pow(31, 2);
        biMap = find5MostPopularBigrams(text);
        keyParams = findingKey(biMap);
        for (int q = 0; q < keyParams.size(); q += 2) {
            int i = 0;
            StringBuilder pipiText = new StringBuilder();
            gcdAndrev = EuclidsAlgorithm(keyParams.get(q), m);
            while (pipiText.length() != text.length()) {
                y = getKeyByChar2(text1.charAt(i)) * 31 + getKeyByChar2(text1.charAt(i + 1));
                int temp = gcdAndrev[1] * (y - keyParams.get(q + 1));
                temp = modularArithmetic(temp, m);
                x1 = temp / 31;
                x2 = temp % 31;
                pipiText.append(getCharByKey2(x1));
                pipiText.append(getCharByKey2(x2));
                i += 2;
            }
            int indicate;
            indicate = recognizer(pipiText.toString());
            if(indicate==1) {
                pureText.append(pipiText.toString());
                System.out.println("a "+keyParams.get(q)+" b "+keyParams.get(q+1));
            }
        }
        return pureText.toString();
    }

    public static void main(String[] args) throws Exception {
        File doc = new File("C:\\01.txt");
        Scanner obj = new Scanner(doc);
        StringBuilder text = new StringBuilder();
        while (obj.hasNextLine()) {
            text.append(obj.nextLine());
        }
        String pureText = decryption(text.toString());
        System.out.println(pureText);
    }
}
