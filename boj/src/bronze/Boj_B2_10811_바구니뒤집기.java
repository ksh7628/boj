package bronze;

import java.io.*;
import java.util.*;

public class Boj_B2_10811_바구니뒤집기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] arr = new int[N];

		for (int i = 0; i < N; i++) {
			arr[i] = i + 1;
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken()) - 1;
			int end = Integer.parseInt(st.nextToken()) - 1;

			while (start < end) {
				int tmp = arr[start];
				arr[start++] = arr[end];
				arr[end--] = tmp;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append(arr[i] + " ");
		}

		System.out.print(sb);
		br.close();
	}
}