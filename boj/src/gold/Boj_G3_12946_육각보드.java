package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 17.
 * 
 * 분류: 수학, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 현재 색깔을 인자로 받아서 아직 색칠하지 않은 곳이라면 1 - 현재색깔 (0 or 1)로 칠하고 인접한 면이 현재 색깔과 동일하다면 필요한 색의 최댓값(3)을 갱신.
 * 느낀 점: 1 - 현재색깔 이라는 로직을 생각하지 못해서 다른 사람의 코드를 참조해서 해당 방법을 알게 되었다.
 */
public class Boj_G3_12946_육각보드 {
	static char[][] map;
	static int[][] color;
	static int[] dx = { -1, -1, 0, 1, 1, 0 };
	static int[] dy = { 0, 1, 1, 0, -1, -1 };
	static int N, res;
	static boolean isMax;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		color = new int[N][N];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
			Arrays.fill(color[i], -1);
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (color[i][j] == -1 && map[i][j] == 'X') {
					dfs(i, j, 0);
				}
			}
		}

		System.out.println(res);
		br.close();
	}

	/* dfs로 현재 색깔과 비교해가며 다음 인접한 칸의 색깔을 정한다 */
	private static void dfs(int x, int y, int c) {
		if (isMax) {
			return;
		}

		color[x][y] = c;
		res = Math.max(res, 1);

		for (int d = 0; d < 6; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (!isCheck(nx, ny) || map[nx][ny] == '-') {
				continue;
			}

			// 아직 색칠하지 않은 곳 -> 1 - 현재 색깔 = 0 or 1로 색칠
			if (color[nx][ny] == -1) {
				dfs(nx, ny, 1 - c);
				res = Math.max(res, 2);
				// 현재 색깔과 동일한 곳 -> 색깔 종류의 최댓값인 3으로 갱신 후 dfs 종료
			} else if (color[nx][ny] == c) {
				res = Math.max(res, 3);
				isMax = true;
				return;
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}