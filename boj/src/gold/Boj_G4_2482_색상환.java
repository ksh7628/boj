package gold;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 3.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 골드4
 * 소요 시간: 1h 30m 이상
 * 혼자 품: X
 * 풀이: 
 * Bottom-Up DP로 풀이. K가 N/2보다 크다면 경우의수는 0
 * 그렇지 않다면 이전 색깔을 선택한 경우와 선택하지 않은 경우로 나눠서 생각
 * -> dp[i][j] = dp[i - 2][j - 1] + dp[i - 1][j] 점화식 도출
 * 느낀 점: 원형으로 생각해야 되는 DP문제는 처음 접해서 결국 못 풀고 다른 사람의 풀이를 참조하게 되었다. 새로운 유형을 익히게 됨.
 */
public class Boj_G4_2482_색상환 {
	static final int MOD = 1000000003;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());
		int res = 0;

		if (N / 2 >= K) {
			int[][] dp = new int[N + 1][K + 1];
			for (int i = 0; i <= N; i++) {
				dp[i][1] = i;
				dp[i][0] = 1;
			}

			for (int i = 2; i <= N; i++) {
				for (int j = 2; j <= K; j++) {
					dp[i][j] = (dp[i - 2][j - 1] % MOD + dp[i - 1][j] % MOD) % MOD;
				}
			}

			res = (dp[N - 1][K] % MOD + dp[N - 3][K - 1] % MOD) % MOD;
		}

		System.out.println(res);
		br.close();
	}
}