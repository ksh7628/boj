package bronze;

import java.io.*;

public class Boj_B5_9086_문자열 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		while (T-- > 0) {
			String s = br.readLine();
			sb.append(s.charAt(0)).append(s.charAt(s.length() - 1)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}