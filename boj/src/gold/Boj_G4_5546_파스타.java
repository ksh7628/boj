package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 28.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 골드4
 * 소요 시간: 0h 46m
 * 혼자 품: O
 * 풀이: Top-Down 방식의 DP로 풀이. [똑같은 파스타를 먹은 횟수][최근에 먹은 파스타][날짜]를 가지는 3차원 dp로 접근
 * 느낀 점: 예전에 손도 못댄 문제였는데 최근 Top-Down 방식으로 DP문제를 풀다 보니 익숙해져서 풀게 되었다.
 */
public class Boj_G4_5546_파스타 {
	static int[][][] dp;
	static int[] pasta;
	static int N;
	static final int MOD = 10000;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		dp = new int[4][4][N + 1];
		pasta = new int[N + 1];

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int day = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			pasta[day] = num;
		}

		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}

		if (pasta[1] != 0) {
			System.out.println(dfs(pasta[1], 1, 1));
		} else {
			System.out.println(dfs(0, 0, 0));
		}

		br.close();
	}

	/* Top-Down 방식의 DP */
	private static int dfs(int num, int day, int con) {
		// 이미 메모이제이션 완료됨
		if (dp[con][num][day] != -1) {
			return dp[con][num][day];
		}

		// 3일 연속으로 똑같은 파스타는 먹을 수 없다
		if (con == 3) {
			return 0;
		}

		// N일까지 모두 먹음
		if (day == N) {
			return 1;
		}

		dp[con][num][day] = 0;
		int next = day + 1;

		if (next <= N) {
			// 해당 날짜에 먹어야 되는 파스타가 있음
			if (pasta[next] != 0) {
				if (num == pasta[next]) {
					dp[con][num][day] = (dp[con][num][day] + dfs(num, next, con + 1)) % MOD;
				} else {
					dp[con][num][day] = (dp[con][num][day] + dfs(pasta[next], next, 1)) % MOD;
				}
				// 없다면 3가지 모두 먹을 수 있다
			} else {
				for (int i = 1; i <= 3; i++) {
					if (i == num) {
						dp[con][num][day] = (dp[con][num][day] + dfs(num, next, con + 1)) % MOD;
					} else {
						dp[con][num][day] = (dp[con][num][day] + dfs(i, next, 1)) % MOD;
					}
				}
			}
		}

		return dp[con][num][day];
	}
}