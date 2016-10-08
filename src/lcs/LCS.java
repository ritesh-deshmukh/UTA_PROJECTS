package lcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class LCS {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        FileReader in = new FileReader("input.txt");
        BufferedReader br = new BufferedReader(in);

        String input = br.readLine();

        int hash = input.indexOf('#');
        System.out.println("Position of hash: " + hash);
        System.out.println("length of S_1: " + (hash));
        System.out.println("length of S_2: " + (input.length() - hash - 1));

        String S_1 = input.substring(0, hash).toUpperCase();
        String S_2 = input.substring(hash + 1, input.length()).toUpperCase();

        System.out.println("First DNA sequence S_1 : " + S_1);
        System.out.println("Second DNA sequence S_2 : " + S_2);

        calculate(S_1.toCharArray(), S_2.toCharArray());

        //to obtain the runtime in runtime.txt file
        File runtime = new File("runtime.txt");
        FileOutputStream run = new FileOutputStream(runtime);
        PrintStream rn = new PrintStream(run);
        System.setOut(rn);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("TotalTime: " + totalTime);
    }

    public static int calculate(char[] S_1, char[] S_2) throws FileNotFoundException {

        int LCS[][] = new int[S_1.length + 1][S_2.length + 1];
        String trace[][] = new String[S_1.length + 1][S_2.length + 1];

        // checking if S_1 or S_2 are null, if yes then LCS of S_1 and S_2 is "0"
        for (int i = 0; i <= S_2.length; i++) {
            LCS[0][i] = 0;
            trace[0][i] = "0";
        }
        for (int i = 0; i <= S_1.length; i++) {
            LCS[i][0] = 0;
            trace[i][0] = "0";
        }
        // forming the matrix and calculating the LCS from it using diagonal, up and left indicating the arrows
        for (int i = 1; i <= S_1.length; i++) {
            for (int j = 1; j <= S_2.length; j++) {
                if (S_1[i - 1] == S_2[j - 1]) {
                    LCS[i][j] = LCS[i - 1][j - 1] + 1;
                    trace[i][j] = "diagonal";
                } else {
                    LCS[i][j] = Math.max(LCS[i - 1][j], LCS[i][j - 1]);
                    if (LCS[i][j] == LCS[i - 1][j]) {
                        trace[i][j] = "up";
                    } else {
                        trace[i][j] = "left";
                    }
                }
            }
        }
        // the code below is used to print the LCS of S_1 and S_2
        String temp = trace[S_1.length][S_2.length];
        String solution = "";
        int a = S_1.length;
        int b = S_2.length;
        while (temp != "0") {
            if (trace[a][b] == "diagonal") {
                solution = S_1[a - 1] + solution;
                a--;
                b--;
            } else if (trace[a][b] == "left") {
                b--;
            } else {
                a--;
            }
            temp = trace[a][b];
        }

        //to obtain the output S_3 in the output.txt file
        File output = new File("output.txt");
        FileOutputStream out = new FileOutputStream(output);
        PrintStream op = new PrintStream(out);
        System.setOut(op);
        if (solution == "") {
            System.out.println("The Longest Common Subsequence S_3 is : null ");
        } else {
            System.out.println("The Longest Common Subsequence S_3 is : " + solution);
        }
        System.out.println();

        /*for (int i = 0; i <= S_1.length; i++) {
			for (int j = 0; j <= S_2.length; j++) {
				System.out.print(" " + LCS[i][j]);
			}
			System.out.println();
		}  */
        return LCS[S_1.length][S_2.length];
    }

}
