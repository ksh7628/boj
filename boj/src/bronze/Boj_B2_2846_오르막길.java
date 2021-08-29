package bronze;


import java.io.*;
import java.util.StringTokenizer;

public class Boj_B2_2846_오르막길 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int size = 0;
		for (int i = 0; i < N; i++) {
			int sum = 0;
			for (int j = i + 1; j < N; j++) {
				if (arr[j - 1] < arr[j]) {
					sum += arr[j] - arr[j - 1];
				}
				else {
					break;
				}
			}
			size = Math.max(size, sum);
		}
		System.out.println(size);
		br.close();
	}
}