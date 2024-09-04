package bronze;

import java.io.*;

public class Boj_B5_27866_문자와문자열 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		int i = Integer.parseInt(br.readLine());
		System.out.print(S.charAt(i - 1));
		br.close();
	}
}