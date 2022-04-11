package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 11.
 * 
 * 분류: 구현, 브루트포스 알고리즘
 * 난이도: 골드5
 * 소요 시간: 0h 26m
 * 혼자 품: O
 * 풀이: 밑에 있는 solution 메소드 위 주석문과 동일
 * 느낀 점: 예전에 이 문제를 풀었을 때는 하드코딩으로 모든 블록을 만들어서 탐색했는데 이번에 규칙을 발견하면서 코드가 간결해졌다.
 */
public class Boj_G5_14500_테트로미노 {
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 0, 1, 0 };// 북동남서(시계방향)
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		visit = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution();
		System.out.println(res);
		br.close();
	}

	/* 
	 * 모든 위치에서 놓을 수 있는 블록을 다 놓아본다
	 * 1. ooo 를 제외하고 4가지 블록을 dfs로 탐색해서 놓아본다. 
	 *     o
	 * 2. 남은 블록은 4방향중 3방향만 체크하면서 놓아본다.
	 * 3. 매번 최댓값을 갱신해간다.
	 */
	private static void solution() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				dfs(i, j, 0, map[i][j]);
				bfs(i, j);
			}
		}
	}

	/* 
	 * ooo 블록을 제외한 나머지 블록을 dfs로 만든다.
	 *  o
	 */
	private static void dfs(int x, int y, int cnt, int sum) {
		if (cnt == 3) {
			res = Math.max(res, sum);
			return;
		}

		visit[x][y] = true;
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (isCheck(nx, ny) && !visit[nx][ny]) {
				dfs(nx, ny, cnt + 1, sum + map[nx][ny]);
			}
		}
		visit[x][y] = false;
	}

	/* 
	 * ooo 블록만 따로 체크
	 *  o
	 */
	private static void bfs(int x, int y) {
		for (int i = 0; i < 4; i++) {
			int sum = map[x][y];
			boolean isAble = true;

			for (int d = i; d < i + 3; d++) {
				int nx = x + dx[d % 4];
				int ny = y + dy[d % 4];

				if (!isCheck(nx, ny)) {
					isAble = false;
					break;
				}

				sum += map[nx][ny];
			}

			if (isAble) {
				res = Math.max(res, sum);
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}
