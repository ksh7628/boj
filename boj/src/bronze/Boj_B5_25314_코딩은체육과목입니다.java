package bronze;

import java.io.*;

public class Boj_B5_25314_코딩은체육과목입니다 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < N / 4; i++) {
			sb.append("long ");
		}

		sb.append("int");
		System.out.print(sb);
		br.close();
	}
}