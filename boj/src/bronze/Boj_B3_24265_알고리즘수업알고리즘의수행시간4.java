package bronze;

import java.io.*;

public class Boj_B3_24265_알고리즘수업알고리즘의수행시간4 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		System.out.println(n * (n - 1) / 2 + "\n" + 2);
		br.close();
	}
}