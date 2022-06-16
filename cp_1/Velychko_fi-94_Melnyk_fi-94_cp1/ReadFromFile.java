import java.io.File;
import java.util.*;


public class ReadFromFile {

    public static HashMap<Character, Double> sortByValue(HashMap<Character, Double> sortedMap) {

        List<Map.Entry<Character, Double>> list =
                new LinkedList<Map.Entry<Character, Double>>(sortedMap.entrySet());

        list.sort(new Comparator<Map.Entry<Character, Double>>() {
            public int compare(Map.Entry<Character, Double> o1,
                               Map.Entry<Character, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<Character, Double> temp = new LinkedHashMap<Character, Double>();
        for (Map.Entry<Character, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static boolean isCyrillicOrSpace(char ch) {
        return (ch >= 'а' && ch <= 'я' || ch == ' ');
    }

    public static void main(String[] args) throws Exception {
        File doc = new File("C:\\text.txt");
        Scanner obj = new Scanner(doc);

        int num_of_chars;
        int num_of_chars1;
        int num_of_bi = 0;
        int num_of_bi0 = 0;
        int num_of_bi1 = 0;
        int num_of_bi2 = 0;
        double H1_1 = 0.0;
        double H1_2 = 0.0;
        double H2_1 = 0.0;
        double H2_2 = 0.0;
        double H2_3 = 0.0;
        double H2_4 = 0.0;
        double val1, val2, Log, h1_1, h1_2;

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

        StringBuilder strWithoutSps = new StringBuilder();
        String finalString = builder.toString().trim().replaceAll("[ ]+", " ");
        for(int k=0; k<finalString.length(); k++){
            if(finalString.charAt(k)!=' ') strWithoutSps.append(finalString.charAt(k));
        }
        num_of_chars = finalString.length();
        num_of_chars1 = strWithoutSps.length();
        HashMap<Character, Double> sortedMap = new HashMap<>();
        for (char ch : finalString.toCharArray()) {
            sortedMap.put(ch, sortedMap.getOrDefault(ch, 0.0) + 1.0);
        }
        Map<Character, Double> hm1 = sortByValue(sortedMap);

        HashMap<Character, Double> sortedMap1 = new HashMap<>();
        for (char ch : strWithoutSps.toString().toCharArray()) {
            sortedMap1.put(ch, sortedMap.getOrDefault(ch, 0.0) + 1.0);
        }
        Map<Character, Double> hm2 = sortByValue(sortedMap1);

        System.out.println("Table with spaces");
        System.out.println("-------------------------");
        for (Character ch : hm1.keySet()) {
            val1 = hm1.get(ch) / num_of_chars;
            System.out.println(ch + " - " + String.format("%,.4f", val1));
        }

        System.out.println("Table without spaces");
        System.out.println("-------------------------");
        for (Character ch : hm2.keySet()) {
            val2 = hm2.get(ch) /num_of_chars1;
            System.out.println(ch + " - " + String.format("%,.4f", val2));
        }

        HashMap<String, Double> mapbi_intersections = new HashMap<>();
        for (int k = 0; k + 1 < finalString.length(); k++) {
            String bigram = finalString.substring(k, k + 2);
            mapbi_intersections.put(bigram, mapbi_intersections.getOrDefault(bigram, 0.0) + 1.0);
            num_of_bi0++;
        }

        HashMap<String, Double> mapbi_intersections1 = new HashMap<>();
        for (int k = 0; k + 1 < strWithoutSps.length(); k++) {
            String bigram = strWithoutSps.substring(k, k + 2);
            mapbi_intersections1.put(bigram, mapbi_intersections1.getOrDefault(bigram, 0.0) + 1.0);
            num_of_bi++;
        }

        System.out.println("Table for bigrams with spaces and intersections");
        System.out.println("------------------------------------------------");
        double[][] tableAB = new double[33][33];
        for (String str : mapbi_intersections.keySet()) {
                int j;
                int k;
                if (str.charAt(0) == ' ') {
                    j = 32;
                } else {
                    j = str.charAt(0) - 'а';
                }
                if (str.charAt(1) == ' ') {
                    k = 32;
                } else {
                    k = str.charAt(1) - 'а';
                }
                tableAB[j][k] = mapbi_intersections.get(str) / num_of_bi0;
        }

        System.out.println("    а       б       в       г       д       е       ж       з       и       й       к       л       м       н       о       п       р       с       т       у       ф       х       ц       ч       ш       щ       ъ       ы       ь       э       ю       я       ' '");
        System.out.println("========================================================================================================================================================================================================================================================================");
        for (int j = 0; j < 33; j++) {
            char c = (char) ('а' + j);
            if (j == 32) c = '_';
            System.out.print(c + " ");
            for (int k = 0; k < 33; k++) {
                    System.out.print(String.format("%,.4f", tableAB[j][k]) + "  ");
            }
            System.out.print(c + " ");
            System.out.println();
        }

        System.out.println("Table for bigrams with intersections");
        System.out.println("-------------------------------------");
        double[][] tableA_B = new double[32][32];
        for (String str : mapbi_intersections.keySet()) {
            if (str.charAt(0) != ' ' && str.charAt(1) != ' ') {
                int j;
                int k;
                j = str.charAt(0) - 'а';
                k = str.charAt(1) - 'а';
                tableA_B[j][k] = mapbi_intersections.get(str) / num_of_bi;
            }
        }

        System.out.println("    а       б       в       г       д       е       ж       з       и       й       к       л       м       н       о       п       р       с       т       у       ф       х       ц       ч       ш       щ       ъ       ы       ь       э       ю       я");
        System.out.println("================================================================================================================================================================================================================================================================");
        for (int j = 0; j < 32; j++) {
            char c = (char) ('а' + j);
            System.out.print(c + " ");
            for (int k = 0; k < 32; k++) {
                System.out.print(String.format("%,.4f", tableA_B[j][k]) + "  ");
            }
            System.out.print(c + " ");
            System.out.println();
        }

        HashMap<String, Double> mapbi = new HashMap<>();
        for (int k = 0; k + 1 < finalString.length(); k=k+2) {
            String bigram = finalString.substring(k, k + 2);
            mapbi.put(bigram, mapbi.getOrDefault(bigram, 0.0) + 1.0);
            num_of_bi2++;
        }

        HashMap<String, Double> mapbi1 = new HashMap<>();
        for (int k = 0; k + 1 < strWithoutSps.length(); k=k+2) {
            String bigram = strWithoutSps.substring(k, k + 2);
            mapbi1.put(bigram, mapbi1.getOrDefault(bigram, 0.0) + 1.0);
            num_of_bi1++;
        }

        System.out.println("Table for bigrams with spaces");
        System.out.println("------------------------------");
        double[][] tableBС = new double[33][33];
        for (String str : mapbi.keySet()) {
            int l;
            int m;
            if (str.charAt(0) == ' ') {
                l = 32;
            } else {
                l = str.charAt(0) - 'а';
            }
            if (str.charAt(1) == ' ') {
                m = 32;
            } else {
                m = str.charAt(1) - 'а';
            }
            tableBС[l][m] = mapbi.get(str) / num_of_bi1;
        }

        System.out.println("    а       б       в       г       д       е       ж       з       и       й       к       л       м       н       о       п       р       с       т       у       ф       х       ц       ч       ш       щ       ъ       ы       ь       э       ю       я       ' '");
        System.out.println("=========================================================================================================================================================================================================================================================================");
        for (int l = 0; l < 33; l++) {
            char d = (char) ('а' + l);
            if (l == 32) d = '_';
            System.out.print(d + " ");
            for (int m = 0; m < 33; m++) {
                System.out.print(String.format("%,.4f", tableBС[l][m]) + "  ");
            }
            System.out.print(d + " ");
            System.out.println();
        }

        System.out.println("Table for bigrams");
        System.out.println("------------------");
        double[][] tableB_С = new double[32][32];
        for (String str : mapbi.keySet()) {
            if (str.charAt(0) != ' ' && str.charAt(1) != ' ') {
                int l;
                int m;
                l = str.charAt(0) - 'а';
                m = str.charAt(1) - 'а';

                tableB_С[l][m] = mapbi.get(str) / num_of_bi1;
            }
        }

        System.out.println("    а       б       в       г       д       е       ж       з       и       й       к       л       м       н       о       п       р       с       т       у       ф       х       ц       ч       ш       щ       ъ       ы       ь       э       ю       я");
        System.out.println("================================================================================================================================================================================================================================================================");
        for (int l = 0; l < 32; l++) {
            char d = (char) ('а' + l);
            System.out.print(d + " ");
            for (int m = 0; m < 32; m++) {
                System.out.print(String.format("%,.4f", tableB_С[l][m]) + "  ");
            }
            System.out.print(d + " ");
            System.out.println();
        }

        for (Character ch : sortedMap.keySet()) {
            val1 = sortedMap.get(ch) / num_of_chars;
            Log = Math.log(val1) / Math.log(2);
            h1_1 = val1 * Log;
            H1_1 = -h1_1 + H1_1;
        }

        for (Character ch : sortedMap1.keySet()) {
            val2 = sortedMap.get(ch) / num_of_chars1;
            Log = Math.log(val2) / Math.log(2);
            h1_2 = val2 * Log;
            H1_2 = -h1_2 + H1_2;
        }

        for (String str : mapbi_intersections.keySet()) {
            val1 = mapbi_intersections.get(str) / num_of_bi0;
            Log = Math.log(val1) / Math.log(2);
            h1_1 = val1 * Log;
            H2_1 = (-h1_1 + H2_1);
        }

        for (String str : mapbi_intersections1.keySet()) {
            val2 = mapbi_intersections1.get(str) / num_of_bi;
            Log = Math.log(val2) / Math.log(2);
            h1_2 = val2 * Log;
            H2_2 = (-h1_2 + H2_2);
        }

        for (String str : mapbi.keySet()) {
            val1 = mapbi.get(str) / num_of_bi2;
            Log = Math.log(val1) / Math.log(2);
            h1_1 = val1 * Log;
            H2_3 = (-h1_1 + H2_3);
        }

        for (String str : mapbi1.keySet()) {
            val2 = mapbi1.get(str) / num_of_bi1;
            Log = Math.log(val2) / Math.log(2);
            h1_2 = val2 * Log;
            H2_4 = (-h1_2 + H2_4);
        }

        System.out.println("H1 with spaces  = " + String.format("%,.4f", H1_1));
        System.out.println("H1 without spaces  = " + String.format("%,.4f", H1_2));
        System.out.println("H2 with spaces and intersections  = " + String.format("%,.4f", H2_1 / 2));
        System.out.println("H2 without spaces and with intersections  = " + String.format("%,.4f", H2_2 / 2));
        System.out.println("H2 with spaces  = " + String.format("%,.4f", H2_3 / 2));
        System.out.println("H2 without spaces  = " + String.format("%,.4f", H2_4 / 2));

    }
}
