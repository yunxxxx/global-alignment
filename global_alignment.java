import java.io.*;
import java.util.stream.Collectors;

public class q1_Yuan_Bo {

    private static final int[][] matrix = {
            {4, -1, -2, -2, 0, -1, -1, 0, -2, -1, -1, -1, -1, -2, -1, 1, 0, -3, -2, 0},
            {-1, 5, 0, -2, -3, 1, 0, -2, 0, -3, -2, 2, -1, -3, -2, -1, -1, -3, -2, -3},
            {-2, 0, 6, 1, -3, 0, 0, 0, 1, -3, -3, 0, -2, -3, -2, 1, 0, -4, -2, -3},
            {-2, -2, 1, 6, -3, 0, 2, -1, -1, -3, -4, -1, -3, -3, -1, 0, -1, -4, -3, -3},
            {0, -3, -3, -3, 9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1},
            {-1, 1, 0, 0, -3, 5, 2, -2, 0, -3, -2, 1, 0, -3, -1, 0, -1, -2, -1, -2},
            {-1, 0, 0, 2, -4, 2, 5, -2, 0, -3, -3, 1, -2, -3, -1, 0, -1, -3, -2, -2},
            {0, -2, 0, -1, -3, -2, -2, 6, -2, -4, -4, -2, -3, -3, -2, 0, -2, -2, -3, -3},
            {-2, 0, 1, -1, -3, 0, 0, -2, 8, -3, -3, -1, -2, -1, -2, -1, -2, -2, 2, -3},
            {-1, -3, -3, -3, -1, -3, -3, -4, -3, 4, 2, -3, 1, 0, -3, -2, -1, -3, -1, 3},
            {-1, -2, -3, -4, -1, -2, -3, -4, -3, 2, 4, -2, 2, 0, -3, -2, -1, -2, -1, 1},
            {-1, 2, 0, -1, -3, 1, 1, -2, -1, -3, -2, 5, -1, -3, -1, 0, -1, -3, -2, -2},
            {-1, -1, -2, -3, -1, 0, -2, -3, -2, 1, 2, -1, 5, 0, -2, -1, -1, -1, -1, 1},
            {-2, -3, -3, -3, -2, -3, -3, -3, -1, 0, 0, -3, 0, 6, -4, -2, -2, 1, 3, -1},
            {-1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4, 7, -1, -1, -4, -3, -2},
            {1, -1, 1, 0, -1, 0, 0, 0, -1, -2, -2, 0, -1, -2, -1, 4, 1, -3, -2, -2},
            {0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 1, 5, -2, -2, 0},
            {-3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1, 1, -4, -3, -2, 11, 2, -3},
            {-2, -2, -2, -3, -2, -1, -2, -3, 2, -1, -1, -2, -1, 3, -3, -2, -2, 2, 7, -1},
            {0, -3, -3, -3, -1, -2, -2, -3, -3, 3, 1, -2, 1, -1, -2, -2, 0, -3, -1, 4}};

    public static void main(String[] args) throws IOException {
//        String inputFile = args[0];
//        File file = new File(inputFile);
//        BufferedReader input = new BufferedReader(new FileReader(file));
//        String firstLine = input.readLine();
//        String secondLine = input.readLine();
        File file = new File("test_1.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String firstLine = input.readLine();
        String seq1 = "";
        String seq2 = "";
        if (!firstLine.equals(">Sequence 1")) {
            System.out.println("Incorrect input");
        }
        else {
            String allLine = input.readLine();
            while (!allLine.equals(">Sequence 2")) {
                seq1 += allLine;
                allLine = input.readLine();
            }
            seq2 =  input.lines().collect(Collectors.joining());
        }
        String result = globalAlignment(seq1, seq2);
        FileWriter writer = new FileWriter("output_q1_Yuan_Bo.txt");
        writer.write(result);
        writer.close();
    }

    public static String globalAlignment(String seq1, String seq2) {
        String resultSe1 = "";
        String resultSe2 = "";
        int[][] map = new int[seq1.length()][seq2.length()];
        String[][] edge = new String[seq1.length()][seq2.length()];
        String blosum62 = "ARNDCQEGHILKMFPSTWYV";

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int com1 = 0;
                while (blosum62.charAt(com1) != seq1.charAt(i)) {
                    com1++;
                }
                int com2 = 0;
                while (blosum62.charAt(com2) != seq2.charAt(j)) {
                    com2++;
                }
                map[i][j] = matrix[com1][com2];
                edge[i][j] = "";
            }
        }

        for (int i = 1; i < map.length; i++) {
            map[i][0] = map[i - 1][0] - 5;
            edge[i][0] += "U";
        }
        for (int i = 1; i < map[0].length; i++) {
            map[0][i] = map[0][i - 1] - 5;
            edge[0][i] += "L";
        }

        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map[0].length; j ++) {
                int valU = map[i - 1][j] - 5;
                int valL = map[i][j - 1] - 5;
                int valD = map[i - 1][j - 1] + map[i][j];
                int best = Math.max(valD, Math.max(valU, valL));
                map[i][j] = best;
                if (best == valL) {
                    edge[i][j] += "L";
                }
                if (best == valU) {
                    edge[i][j] += "U";
                }
                if (best == valD) {
                    edge[i][j] += "D";
                }
            }
        }
        //map printing:
//        System.out.print("  ");
//        for (int i = 0; i < map[0].length; i++) {
//            System.out.print(seq2.charAt(i) + ", ");
//        }
//        System.out.println();
//        for (int i = 0; i < map[0].length; i++) {
//            System.out.print("___");
//        }
//        System.out.println();
//
//        for (int i = 0; i < map.length; i++) {
//            System.out.print(seq1.charAt(i) + "|");
//            for (int j = 0; j < map[0].length; j++) {
//                System.out.print(map[i][j] + ", ");
//            }
//            System.out.println();
//        }
//
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[0].length; j++) {
//                System.out.print(edge[i][j] + ", ");
//            }
//            System.out.println();
//        }
        //map printing end
        int x = map.length - 1;
        int y = map[0].length - 1;
        int index = map.length - 1;
        int index2 = map[0].length - 1;
        boolean finalHit = false;
        while (x != 0 || y != 0) {
            int maxValue = Integer.MIN_VALUE;
            String current = "OvO";
            if (edge[x][y].contains("D")) {
                maxValue = map[x - 1][y - 1];
                current = "D";
            }
            if (edge[x][y].contains("L")) {
                if (maxValue < map[x][y - 1]) {
                    maxValue = map[x][y - 1];
                    current = "L";
                }
            }
            if (edge[x][y].contains("U")) {
                if (maxValue < map[x - 1][y]) {
                    maxValue = map[x - 1][y];
                    current = "U";
                }
            }
            if (current.equals("D")) {
                resultSe1 += seq1.charAt(index);
                resultSe2 += seq2.charAt(index2);
                x--;
                y--;
                index--;
                index2--;
            }
            else if (current.equals("U")) {
                x--;
                if (x == 0  && y == 0) {
                    finalHit = true;
                    resultSe1 += seq1.charAt(index);
                    resultSe2 += seq2.charAt(index2);
                    index--;
                    index2--;
                }
                else {
                    resultSe2 += "-";
                    resultSe1 += seq1.charAt(index);
                    index--;
                }
            }
            else if (current.equals("L")) {
                y--;
                if (x == 0  && y == 0) {
                    finalHit = true;
                    resultSe1 += seq1.charAt(index);
                    resultSe2 += seq1.charAt(index);
                    index--;
                    index2--;
                }
                else {
                    resultSe1 += "-";
                    resultSe2 += seq2.charAt(index2);
                    index2--;
                }
            }
        }
        while (index2 > -1) {
            if (index2 == 0 && !finalHit) {
                resultSe1 += seq1.charAt(0);
                resultSe2 += seq2.charAt(0);
                index2--;
                index--;
            }

            else {
                resultSe1 += "-";
                resultSe2 += seq2.charAt(index2);
            }
            index2--;
        }
        while (index > -1) {
            if (index2 == 0 && !finalHit) {
                resultSe1 += seq1.charAt(0);
                resultSe2 += seq2.charAt(0);
                index2--;
                index--;
            }
            else {
                resultSe2 += "-";
                resultSe1 += seq1.charAt(index);
            }
            index--;
        }

        int score = 0;
        resultSe2 = new StringBuilder(resultSe2).reverse().toString();
        resultSe1 = new StringBuilder(resultSe1).reverse().toString();
        for (int i = 0; i < resultSe1.length(); i++) {
            int resultChar1 = resultSe2.charAt(i);
            int resultChar2 = resultSe1.charAt(i);
            if (resultChar1 == '-' || resultChar2 == '-') {
                score += -5;
            }
            else {
                int com1 = 0;
                while (blosum62.charAt(com1) != resultChar1) {
                    com1++;
                }
                int com2 = 0;
                while (blosum62.charAt(com2) != resultChar2) {
                    com2++;
                }
                score += matrix[com1][com2];
            }
        }

        return score + "\n" + resultSe1 + "\n" + resultSe2;
    }

}



