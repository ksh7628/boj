package bronze;

import java.io.*;

public class Boj_B2_24267_알고리즘수업알고리즘의수행시간6 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		System.out.println(n * (n - 1) * (n - 2) / 6 + "\n" + 3);
		br.close();
	}
}