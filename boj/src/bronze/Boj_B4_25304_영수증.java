package bronze;

import java.io.*;
import java.util.*;

public class Boj_B4_25304_영수증 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int X = Integer.parseInt(br.readLine());
		int N = Integer.parseInt(br.readLine());
		int res = 0;

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			res += Integer.parseInt(st.nextToken()) * Integer.parseInt(st.nextToken());
		}

		if (X == res) {
			System.out.print("Yes");
		} else {
			System.out.print("No");
		}

		br.close();
	}
}