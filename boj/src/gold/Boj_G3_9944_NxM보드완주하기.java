package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 5.
 * 
 * 분류: 구현, 백트래킹
 * 난이도: 골드3
 * 소요 시간: 0h 53m
 * 혼자 품: O
 * 풀이: 
 * 1. 모든 빈 칸에서 탐색을 시작한다.
 * 2. 한 방향으로 계속 이동하면서 이동한 자리는 벽으로 변경한다.
 * 3. 더 이상 이동할 수 없다면 다음 방향을 탐색하러 넘어간다.
 * 4. 3의 재귀 탐색이 모두 끝나면 2에서 바꾼 벽을 다시 빈칸으로 복구한다.
 * 5. 탐색 도중 최솟값을 넘어가면 해당 탐색을 종료하고 모든 칸을 방문했다면 최솟값을 갱신한다.
 * 느낀 점: 
 * 저번에 재귀 구조를 잘못 짜서 오늘 다시 풀게 된 문제인데 처음에 문제를 잘못 읽고 한칸씩 이동할 때마다 방향 전환을 하니 시간초과가 발생했다.
 * 문제를 다시 읽고 한 방향으로 끝까지 방문하는 식으로 처리를 했고 굳이 visit배열을 사용하지 않아도 풀 수 있다는 것을 알게 되었다.
 */
public class Boj_G3_9944_NxM보드완주하기 {
	static char[][] map;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, totalCnt, res;
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String input = "";
		int tc = 1;

		while ((input = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(input, " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new char[N][M];

			totalCnt = 0;
			for (int i = 0; i < N; i++) {
				map[i] = br.readLine().toCharArray();
				for (int j = 0; j < M; j++) {
					if (map[i][j] == '.') {
						totalCnt++;
					}
				}
			}

			res = INF;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (map[i][j] == '.') {
						dfs(i, j, -1, 0, 1);
					}
				}
			}

			sb.append("Case ").append(tc++).append(": ").append(res == INF ? -1 : res).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 
	 * 한 방향으로 계속 이동하다가 방향을 전환할 때마다 방향 전환 횟수를 증가
	 * -> 도중에 최솟값을 넘었다면 탐색 종료
	 * -> 모든 칸을 방문했다면 최솟값 갱신
	 * 방문할 때마다 벽으로 바꾸고 탐색이 끝나면 다시 빈칸으로 복구
	 */
	private static void dfs(int x, int y, int dir, int dCnt, int kCnt) {
		// 가지치기 -> 최솟값 넘으면 탐색할 필요 X
		if (dCnt >= res) {
			return;
		}

		// 모두 탐색함 -> 최솟값 갱신
		if (totalCnt == kCnt) {
			res = Math.min(res, dCnt);
			return;
		}

		map[x][y] = '#';

		for (int d = 0; d < 4; d++) {
			// 같은 방향은 탐색 안함
			if (d == dir) {
				continue;
			}

			int nx = x + dx[d];
			int ny = y + dy[d];
			int len = 0;

			// 이동할 수 있는 칸수를 샘
			while (isCheck(nx, ny) && map[nx][ny] == '.') {
				len++;
				map[nx][ny] = '#';
				nx += dx[d];
				ny += dy[d];
			}

			nx -= dx[d];
			ny -= dy[d];

			// 제자리면 탐색 X
			if (nx == x && ny == y) {
				continue;
			}

			dfs(nx, ny, d, dCnt + 1, kCnt + len);

			// 복구
			while (!(nx == x && ny == y)) {
				map[nx][ny] = '.';
				nx -= dx[d];
				ny -= dy[d];
			}
		}

		// 복구
		map[x][y] = '.';
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}