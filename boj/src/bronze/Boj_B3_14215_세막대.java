package bronze;

import java.io.*;
import java.util.*;

public class Boj_B3_14215_세막대 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[3];

		for (int i = 0; i < 3; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);

		if (arr[0] + arr[1] <= arr[2]) {
			arr[2] = arr[0] + arr[1] - 1;
		}

		int res = 0;
		for (int i = 0; i < 3; i++) {
			res += arr[i];
		}

		System.out.println(res);
		br.close();
	}
}