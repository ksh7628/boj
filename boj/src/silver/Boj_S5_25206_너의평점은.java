package silver;

import java.io.*;

public class Boj_S5_25206_너의평점은 {
	static double total = 0.0;
	static double score = 0.0;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < 20; i++) {
			String[] s = br.readLine().split(" ");
			if (s[2].equals("P")) {
				continue;
			}

			calculate(s[1], s[2]);
		}

		System.out.print(String.format("%.6f", score / total));
		br.close();
	}

	private static void calculate(String s1, String s2) {
		double subScore = Double.parseDouble(s1);
		double realScore = 0;

		if (s2.equals("A+")) {
			realScore = 4.5;
		} else if (s2.equals("A0")) {
			realScore = 4.0;
		} else if (s2.equals("B+")) {
			realScore = 3.5;
		} else if (s2.equals("B0")) {
			realScore = 3.0;
		} else if (s2.equals("C+")) {
			realScore = 2.5;
		} else if (s2.equals("C0")) {
			realScore = 2.0;
		} else if (s2.equals("D+")) {
			realScore = 1.5;
		} else if (s2.equals("D0")) {
			realScore = 1.0;
		}

		total += subScore;
		score += subScore * realScore;
	}
}