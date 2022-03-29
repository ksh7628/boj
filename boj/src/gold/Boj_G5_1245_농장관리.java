package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 29.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 골드5
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: dfs로 산봉우리 여부를 판단해서 전역 flag 변수 값이 그대로면 카운팅하는 방법으로 풀이.
 * 느낀 점: 
 * 문제를 잘못 읽어서 8방향 탐색인것도 나중에 알게 되었고 boolean 변수로 체크한다는 생각을 하지 못해서 다른 사람의 코드를 참조하게 되었다.
 * 아직 그래프 탐색 유형이 완벽하진 않은 것 같다는 생각이 듬.
 */
public class Boj_G5_1245_농장관리 {
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dy = { 0, 0, -1, 1, 1, -1, 1, -1 };
	static int N, M;
	static boolean flag;

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

		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (!visit[i][j]) {
					flag = true;
					dfs(i, j);
					// 산봉우리가 맞으면 카운팅
					if (flag) {
						res++;
					}
				}
			}
		}

		System.out.println(res);
		br.close();
	}

	/* dfs를 통해 산봉우리 집합 판단 */
	private static void dfs(int x, int y) {
		visit[x][y] = true;

		for (int d = 0; d < 8; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (isCheck(nx, ny)) {
				// 인접한 격자가 산봉우리의 높이보다 크다
				if (map[x][y] < map[nx][ny]) {
					flag = false;
					// 방문 안했고 높이가 같으면 산봉우리 집합에 포함되므로 dfs 진행
				} else if (!visit[nx][ny] && map[x][y] == map[nx][ny]) {
					dfs(nx, ny);
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}