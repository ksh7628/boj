package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 21.
 * 
 * 분류: 수학, 다이나믹 프로그래밍, 조합론
 * 난이도: 실버1
 * 소요 시간: 0h 30m
 * 혼자 품: O
 * 풀이: Top-Down DP로 접근해서 품.
 * 느낀 점: N, M이 작아서 수학적으로 접근이 가능하다는 사실을 알게 됨.
 */
public class Boj_S1_10164_격자상의경로 {
	static int[][][] dp;
	static int[][] map;
	static int N, M, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		dp = new int[2][N][M];

		int num = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				map[i][j] = ++num;
				dp[0][i][j] = -1;
				dp[1][i][j] = -1;
			}
		}

		System.out.println(topDown(0, 0, 0));
		br.close();
	}

	private static int topDown(int x, int y, int O) {
		if (map[x][y] == K) {
			O = 1;
		}

		if (dp[O][x][y] != -1) {
			return dp[O][x][y];
		}

		if (x == N - 1 && y == M - 1) {
			if (O == 1 && K != 0) {
				return 1;
			} else if (O == 0 && K == 0) {
				return 1;
			}
		}

		dp[O][x][y] = 0;

		if (x + 1 < N) {
			dp[O][x][y] += topDown(x + 1, y, O);
		}

		if (y + 1 < M) {
			dp[O][x][y] += topDown(x, y + 1, O);
		}

		return dp[O][x][y];
	}
}