package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 24.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 실버2
 * 소요 시간: 0h 10m
 * 혼자 품: O
 * 풀이: 각 영역마다 dfs를 수행하면서 양의 숫자와 늑대의 숫자를 센 후, 양의 숫자가 더 많다면 양의 숫자만 결과에 더하고 그렇지 않다면 늑대의 숫자만 더한다.
 * 느낀 점: dfs나 bfs 둘 다 접근할 수 있는 문제. 이 경우에는 dfs의 수행 속도가 더 빠름.
 */
public class Boj_S2_3187_양치기꿍 {
	static char[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int R, C, sCnt, wCnt, sRes, wRes;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new char[R][C];
		visit = new boolean[R][C];

		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (!visit[i][j] && map[i][j] != '#') {
					sCnt = 0;
					wCnt = 0;
					dfs(i, j);

					if (sCnt > wCnt) {
						sRes += sCnt;
					} else {
						wRes += wCnt;
					}
				}
			}
		}

		System.out.println(sRes + " " + wRes);
		br.close();
	}

	/* dfs를 통해 각 영역의 양의 숫자와 늑대의 숫자를 카운팅 */
	private static void dfs(int x, int y) {
		if (map[x][y] == 'v') {
			wCnt++;
		} else if (map[x][y] == 'k') {
			sCnt++;
		}

		visit[x][y] = true;

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] != '#') {
				dfs(nx, ny);
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < R && 0 <= y && y < C;
	}
}