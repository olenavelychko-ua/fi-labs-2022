import java.io.File;
import java.util.*;


public class VigenereCipher {

    public static HashMap<Character, Integer> sortByValue(HashMap<Character, Integer> sortedMap) {

        List<Map.Entry<Character, Integer>> list =
                new LinkedList<Map.Entry<Character, Integer>>(sortedMap.entrySet());

        list.sort(new Comparator<Map.Entry<Character, Integer>>() {
            public int compare(Map.Entry<Character, Integer> o1,
                               Map.Entry<Character, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<Character, Integer> temp = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static boolean isCyrillicOrSpace(char ch) {
        return (ch >= 'а' && ch <= 'я' || ch == ' ');
    }

    private static String encryptText(String pureText, String key) {
        StringBuilder text = new StringBuilder();
        char op, k;
        int i = 0;
        int m = 0;
        int num = 0;
        while (i < pureText.length()) {
            op = pureText.charAt(i);
            k = key.charAt(m);
            if (op != ' ') {
                num = ((op - 1072) + (k - 1072)) % 31 + 1072;
                text.append((char) num);
            }
            i++;
            m++;
            if (m == key.length()) m = 0;
        }
        return text.toString();
    }

    private static double matchingIndex(String text) {
        HashMap<Character, Integer> map = new HashMap<>();
        int i = 0;
        int n = 0;
        double index = 0;
        for (char ch : text.toCharArray()) {
            if (!map.containsKey(ch)) {
                map.put(ch, 1);
            } else {
                i = map.get(ch);
                i++;
                map.put(ch, i);
            }
        }
        for (char ch : map.keySet()) {
            n = map.get(ch);
            index = n * (n - 1) + index;
        }
        index = index / (text.length() * (text.length() - 1));
        return index;
    }

  /*  private static int determineKeyLength(String text) {
        String blockOfText = text.substring(0, 2);
        int r = 2;
        double ind;
        int i = 2;
        ind = matchingIndex(blockOfText);
        double sum = 0.31;
        while (ind == 0.0) {
            r = i;
            blockOfText = text.substring(0, i);
            ind = matchingIndex(blockOfText);
            i++;
        }
        while (Math.abs(sum - 0.055) >= Math.abs(sum - 0.03125)) {
            sum = 0;
            r = i;
            blockOfText = text.substring(0, i);
            sum = matchingIndex(blockOfText) + sum;
            i++;
            System.out.println("######" + blockOfText);
            System.out.println("######" + ind);
        }
        return r;
    } */

    private static int determineKeyLengthWithD(String text) {
        int temp;
        int max = 0;
        int maxr=0;
        int r = 6;
        int d = 0;
        while (r < 50) {
            d=0;
            for (int p = 0; (p + r) < text.length(); p = p + r) {
                if (text.charAt(p) == text.charAt(p + r)) d++;
            }
            System.out.println("______" + d+" ///////"+r);
            temp = d;
            if (temp > max) {
                max = temp;
                maxr=r;
            }
            r++;
        }
        return maxr;
    }


    private static String findKey(int r, String text) {
        StringBuilder key = new StringBuilder();
        int k = 0;
        int num, i;
        while (key.length() < r) {
            StringBuilder text1 = new StringBuilder();
            for (int p = 0 + k; (p + r + k) < text.length(); p = r + p) {
                char tempch = text.charAt(p);
                text1.append(tempch);
                //System.out.println("______" + p);

            }
            k++;
            //System.out.println("%%%%%" + text1.toString());
            LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
            i = 0;
            for (char ch : text1.toString().toCharArray()) {
                if (!map.containsKey(ch)) {
                    map.put(ch, 1);
                } else {
                    i = map.get(ch);
                    i++;
                    map.put(ch, i);
                }
            }

         /*   Character maxch = 'a';
            int count = -1;
            for (Character ch : map.keySet()) {
                if (map.get(ch) >= count) {
                    maxch = ch;
                    count = map.get(ch);
                }
            }*/
            Map<Character, Integer> hm1 = sortByValue(map);
            Character f = hm1.keySet().iterator().next();
            char maxch = f;
            System.out.println("%%%%%" + maxch);
            if ((maxch - 1072) - ('о' - 1072) >= 0) {
                num = ((maxch - 1072) - ('о' - 1072)) % 31 + 1072;
            } else {
                int m = 32 + (maxch - 1072) - ('о' - 1072);
                num = m % 31 + 1072;
            }
            key.append((char) num);

        }
        return key.toString();
    }

  /*  private static String findKeyWithD(int r, String text){

    } */


    public static void main(String[] args) throws Exception {
        File doc = new File("C:\\text2.txt");
        Scanner obj = new Scanner(doc);
        StringBuilder builder = new StringBuilder();
        while (obj.hasNextLine()) {
            String word = obj.nextLine().toLowerCase();
            for (char ch : word.toCharArray()) {
                if (isCyrillicOrSpace(ch))
                    builder.append(ch);
                else
                    builder.append(' ');
            }
            builder.append(' ');
        }
        String finalString = builder.toString().trim().replaceAll("[ ]+", " ");
        System.out.println(finalString);

        String key2 = "ум";
        String key3 = "мир";
        String key4 = "игра";
        String key5 = "жизнь";
        String key20 = "двестипятьдесяттысяч";

        String cipherText2 = encryptText(finalString, key2);
        System.out.println("key length is 2: " + cipherText2);
        String cipherText3 = encryptText(finalString, key3);
        System.out.println("key length is 3: " + cipherText3);
        String cipherText4 = encryptText(finalString, key4);
        System.out.println("key length is 4: " + cipherText4);
        String cipherText5 = encryptText(finalString, key5);
        System.out.println("key length is 5: " + cipherText5);
        String cipherText20 = encryptText(finalString, key20);
        System.out.println("key length is 20: " + cipherText20);

        double index1 = matchingIndex(finalString);
        System.out.println("index of pure text : " + String.format("%,.4f", index1));
        double index2 = matchingIndex(cipherText2);
        System.out.println("index of 1-st ciphertext : " + String.format("%,.4f", index2));
        double index3 = matchingIndex(cipherText3);
        System.out.println("index of 2-st ciphertext : " + String.format("%,.4f", index3));
        double index4 = matchingIndex(cipherText4);
        System.out.println("index of 3-st ciphertext : " + String.format("%,.4f", index4));
        double index5 = matchingIndex(cipherText5);
        System.out.println("index of 4-st ciphertext : " + String.format("%,.4f", index5));
        double index20 = matchingIndex(cipherText20);
        System.out.println("index of 5-st ciphertext : " + String.format("%,.4f", index20));

        File doc1 = new File("C:\\ciphertext.txt");
        Scanner obj1 = new Scanner(doc1);
        StringBuilder text1 = new StringBuilder();
        while (obj1.hasNextLine()) {
            text1.append(obj1.nextLine());
        }
        System.out.println(text1);
        int r = determineKeyLengthWithD(text1.toString());
        System.out.println(r);
        String key = findKey(r, text1.toString());
        System.out.println(key);
    }
}
