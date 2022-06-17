import java.io.File;
import java.util.*;


public class VigenereCipher {

    private static boolean isCyrillicOrSpace(char ch) {
        return (ch >= 'а' && ch <= 'я' || ch == ' ');
    }

    private static String encryptText(String pureText, String key) {
        StringBuilder text = new StringBuilder();
        char op, k;
        int i = 0;
        int m = 0;
        int num;
        while (i < pureText.length()) {
            op = pureText.charAt(i);
            k = key.charAt(m);
            if (op != ' ') {
                num = ((op - 1072) + (k - 1072)) % 32 + 1072;
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
        int i;
        int n;
        double index = 0;
        for (char ch : text.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (char ch : map.keySet()) {
            n = map.get(ch);
            index = n * (n - 1) + index;
        }
        index = index / (text.length() * (text.length() - 1));
        return index;
    }

    private static int determineKeyLength(String text) {
        int temp;
        int max = 0;
        int maxr = 0;
        int r = 8;
        int d = 0;
        int shift = 0;
        int p;
        while (r < 20) {
            while (shift < r) {
                for (p = shift; (p + r) < text.length(); p = p + r) {
                    if (text.charAt(p) == text.charAt(p + r)) d++;
                }
                shift++;
            }
            System.out.println("r=" + r + " d=" + d);
            temp = d;
            if (temp > max) {
                max = temp;
                maxr = r;
            }
            r++;
            shift = 0;
            d = 0;
        }
        return maxr;
    }

    private static String findKey(int r, String text) {
        StringBuilder key = new StringBuilder();
        int k = 0;
        int num;
        while (key.length() < r) {
            StringBuilder text1 = new StringBuilder();
            for (int p = k; (p) < text.length(); p = r + p) {
                char tempch = text.charAt(p);
                text1.append(tempch);
            }
            k++;
            LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
            for (char ch : text1.toString().toCharArray()) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
            Character maxch = 'a';
            int count = -1;
            for (Character ch : map.keySet()) {
                if (map.get(ch) > count) {
                    maxch = ch;
                    count = map.get(ch);
                }
            }
            if ((maxch - 1072) - ('о' - 1072) >= 0) {
                num = ((maxch - 1072) - ('о' - 1072)) % 32 + 1072;
            } else {
                int m = 32 + (maxch - 1072) - ('о' - 1072);
                num = m % 32 + 1072;
            }
            key.append((char) num);
        }
        return key.toString();
    }

    private static String findKeyWithM(int r, String text) {
        final double[] Abc = new double[]{0.062, 0.014, 0.038, 0.013, 0.025, 0.072, 0.007, 0.016, 0.062, 0.010, 0.028, 0.035, 0.026, 0.053, 0.090, 0.023, 0.040, 0.045, 0.053, 0.021, 0.002, 0.009, 0.004, 0.012, 0.006, 0.003, 0.014, 0.016, 0.014, 0.003, 0.006, 0.018};
        final String alf = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
        StringBuilder key = new StringBuilder();
        int k = 0;
        while (key.length() < r) {
            StringBuilder text1 = new StringBuilder();
            for (int p = k; p < text.length(); p = r + p) {
                char tempch = text.charAt(p);
                text1.append(tempch);
            }
            k++;
            TreeMap<Character, Integer> map = new TreeMap<>();
            for (char ch : text1.toString().toCharArray()) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
            Map<Character, Double> mapKeySymbols = new LinkedHashMap<>();
            for (int shift = 0; shift < 32; shift++) {
                double sum = 0;
                for (int g = 0; g < 32; g++) {
                    Character ch1 = alf.charAt((g + shift) % 32);
                    int val1 = map.getOrDefault(ch1, 0);
                    double val2 = Abc[g];
                    sum += val1 * val2;
                }
                mapKeySymbols.put(alf.charAt(shift), sum);
            }
            Character maxch = 'a';
            double count = -1;
            for (Character ch : mapKeySymbols.keySet()) {
                System.out.println(ch + " " + String.format("%,.1f", mapKeySymbols.get(ch)));
            }
            System.out.println("__________");
            for (Character ch : mapKeySymbols.keySet()) {
                if (mapKeySymbols.get(ch) > count) {
                    maxch = ch;
                    count = mapKeySymbols.get(ch);
                }
            }
            key.append((char) maxch);
        }
        return key.toString();
    }

    private static String decryptText(String cipherText, String key) {
        StringBuilder text = new StringBuilder();
        char op, k;
        int i = 0;
        int temp = 0;
        int num;
        while (i < cipherText.length()) {
            op = cipherText.charAt(i);
            k = key.charAt(temp);
            if ((op - 1072) - (k - 1072) >= 0) {
                num = ((op - 1072) - (k - 1072)) % 32 + 1072;
            } else {
                int m = 32 + (op - 1072) - (k - 1072);
                num = m % 32 + 1072;
            }
            text.append((char) num);
            i++;
            temp++;
            if (temp == key.length()) temp = 0;
        }
        return text.toString();
    }

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
        System.out.println("Pure text is: " + finalString);

        String key2 = "ум";
        String key3 = "мир";
        String key4 = "игра";
        String key5 = "жизнь";
        String key12 = "пятоефевраля";

        String cipherText2 = encryptText(finalString, key2);
        System.out.println("key length is 2: " + cipherText2);
        String cipherText3 = encryptText(finalString, key3);
        System.out.println("key length is 3: " + cipherText3);
        String cipherText4 = encryptText(finalString, key4);
        System.out.println("key length is 4: " + cipherText4);
        String cipherText5 = encryptText(finalString, key5);
        System.out.println("key length is 5: " + cipherText5);
        String cipherText20 = encryptText(finalString, key12);
        System.out.println("key length is 12: " + cipherText20);

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

        File doc1 = new File("C:\\ciphertext_var1.txt");
        Scanner obj1 = new Scanner(doc1);
        StringBuilder text1 = new StringBuilder();
        while (obj1.hasNextLine()) {
            text1.append(obj1.nextLine());
        }
        System.out.println("Cipher text is: " + text1);
        int r = determineKeyLength(text1.toString());
        System.out.println("Key length is " + r);
        String key1 = findKey(r, text1.toString());
        System.out.println("Key1 is " + key1);
        String key = findKeyWithM(r, text1.toString());
        System.out.println("Key2 is " + key);

        String pureText = decryptText(text1.toString(), key1);
        System.out.println("The decoded text with Key1 is: " + pureText);
        String pureText1 = decryptText(text1.toString(), key);
        System.out.println("The decoded text with Key2 is: " + pureText1);
    }
}
