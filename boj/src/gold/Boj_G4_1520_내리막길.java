package gold;


import java.io.*;
import java.util.*;

public class Boj_G4_1520_내리막길 {
	static int[][] map = new int[501][501];
	static int[][] dp = new int[501][501];
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = -1;
			}
		}

		System.out.println(dfs(0, 0));
		br.close();
	}

	private static int dfs(int x, int y) {
		if (x == N - 1 && y == M - 1) {
			return 1;
		}
		if (dp[x][y] != -1) {
			return dp[x][y];
		}

		dp[x][y] = 0;
		for (int dir = 0; dir < 4; dir++) {
			int nx = x + dx[dir];
			int ny = y + dy[dir];
			if (check(nx, ny) || map[x][y] <= map[nx][ny]) {
				continue;
			}
			dp[x][y] += dfs(nx, ny);
		}
		return dp[x][y];
	}

	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= M;
	}
}