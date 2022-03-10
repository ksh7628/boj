package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 10.
 * 
 * 분류: 누적 합, 두 포인터, 슬라이딩 윈도우
 * 난이도: 실버3
 * 소요 시간: 0h 08m
 * 혼자 품: O
 * 풀이: [0, K) 구간을 먼저 더한 후 다음 구간의 누적 합을 하나씩 갱신해가면서 최댓값을 갱신하여 품.
 * 느낀 점: Prefix Sum 기초 문제라서 쉽게 품.
 */
public class Boj_S3_2559_수열 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] arr = new int[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int sum = 0;
		for (int i = 0; i < K; i++) {
			sum += arr[i];
		}

		int res = sum;
		for (int i = 0; i < N - K; i++) {
			sum = sum - arr[i] + arr[i + K];
			res = Math.max(res, sum);
		}
		
		System.out.println(res);
		br.close();
	}
}