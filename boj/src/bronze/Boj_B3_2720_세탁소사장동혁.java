package bronze;

import java.io.*;

public class Boj_B3_2720_세탁소사장동혁 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		while (T-- > 0) {
			int C = Integer.parseInt(br.readLine());
			int Q = 0, D = 0, N = 0, P = 0;

			Q = C / 25;
			C %= 25;

			D = C / 10;
			C %= 10;

			N = C / 5;
			C %= 5;

			P = C;

			sb.append(Q + " ").append(D + " ").append(N + " ").append(P + "\n");
		}

		System.out.println(sb);
		br.close();
	}
}