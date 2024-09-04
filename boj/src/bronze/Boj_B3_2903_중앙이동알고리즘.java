package bronze;

import java.io.*;

public class Boj_B3_2903_중앙이동알고리즘 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int res = (int) Math.pow(2, N) + 1;
		System.out.println(res * res);
		br.close();
	}
}