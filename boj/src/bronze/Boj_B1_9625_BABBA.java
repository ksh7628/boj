package bronze;

import java.io.*;
/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 25.
 * 
 * 분류: 구현, 다이나믹 프로그래밍
 * 난이도: 브론즈1
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: n항과 n+1항 피보나치 수열을 출력한다.
 * 느낀 점: DP문제 풀 때에는 인덱스 범위를 잘 살피자 -> 배열 사용할 때도 동일
 */
public class Boj_B1_9625_BABBA {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int K = Integer.parseInt(br.readLine());

		int[] dp = new int[K + 2];
		dp[2] = 1;

		// 인덱스 주의
		if (K > 1) {
			// 피보나치 수열
			for (int i = 3; i <= K + 1; i++) {
				dp[i] = dp[i - 1] + dp[i - 2];
			}
		}

		System.out.println(dp[K] + " " + dp[K + 1]);
		br.close();
	}
}