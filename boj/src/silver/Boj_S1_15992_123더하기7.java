package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 29.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 실버1
 * 소요 시간: 0h 31m
 * 혼자 품: O
 * 풀이: 
 * 1. 최초 한번만 dp 배열의 최댓값 길이를 선언 후 -1로 초기화한다.
 * 2. n, m 부터 출발해서 하나씩 답을 구한 후 모두 합친 정답을 출력한다.
 * 느낀 점: 
 * 테스트 케이스 마다 초기화해서 구하면 시간초과가 발생한다.
 * 이 문제의 경우 모든 해가 부분해이기도 해서 다음 케이스에서 이전에 구한 해를 활용할 수 있으므로 초기화를 최초 한번만 하면 된다.
 */
public class Boj_S1_15992_123더하기7 {
	static int[][] dp;
	static int n, m;
	static final int MOD = 1000000009;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		// 최초 한번만 초기화
		dp = new int[1001][1001];
		for (int i = 0; i <= 1000; i++) {
			Arrays.fill(dp[i], -1);
		}

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			sb.append(dfs(n, m)).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* Top-Down DP */
	private static int dfs(int num, int cnt) {
		if (num == 0 && cnt == 0) {
			return 1;
		}

		if (num <= 0 || cnt <= 0) {
			return 0;
		}

		if (dp[num][cnt] != -1) {
			return dp[num][cnt];
		}

		dp[num][cnt] = 0;

		for (int i = 1; i <= 3; i++) {
			dp[num][cnt] = (dp[num][cnt] % MOD + dfs(num - i, cnt - 1) % MOD) % MOD;
		}

		return dp[num][cnt];
	}
}