package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 28.
 * 
 * 분류: 다이나믹 프로그래밍, 그래프 이론, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 0h 32m
 * 혼자 품: O
 * 풀이: dfs + 메모이제이션으로 풀이.
 * 느낀 점: 예전보다 접근법도 알고 빨리 풀었지만 실수를 줄여야겠다고 느낌.
 */
public class Boj_G3_1937_욕심쟁이판다 {
	static int[][] map, dp;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int n;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		dp = new int[n][n];

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int res = 0;
		// 모든 경로에서 출발해서 이동 횟수의 최댓값 갱신
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				res = Math.max(res, dfs(i, j));
			}
		}

		System.out.println(res);
		br.close();
	}

	/* dfs + 메모이제이션 */
	private static int dfs(int x, int y) {
		// 이미 기록됐다면 그대로 반환
		if (dp[x][y] != 0) {
			return dp[x][y];
		}

		// 초기화
		dp[x][y] = 1;
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			// 배열 범위 안이고 갈 수 있는 곳이면
			// -> 다음 재귀 수행 값 + 1과 현재 까지 기록된 횟수 비교해서 최댓값으로 갱신
			if (isCheck(nx, ny) && map[x][y] < map[nx][ny]) {
				dp[x][y] = Math.max(dp[x][y], dfs(nx, ny) + 1);
			}
		}

		return dp[x][y];
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < n && 0 <= y && y < n;
	}
}