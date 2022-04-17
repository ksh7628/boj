package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 17.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 실버1
 * 소요 시간: 0h 06m
 * 혼자 품: O
 * 풀이: 갈 수 있는 곳을 다 방문하면서 한 재귀가 끝날 때마다 방문 처리를 해제하는 방식(백트래킹)으로 풀이.
 * 느낀 점: 긴딘힌 벡트래킹 문제.
 */
public class Boj_S1_1189_컴백홈 {
	static char[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int R, C, K, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new char[R][C];
		visit = new boolean[R][C];

		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		dfs(R - 1, 0, 1);
		System.out.println(res);
		br.close();
	}

	/* 재귀가 끝날 때마다 방문처리 해제 */
	private static void dfs(int x, int y, int dist) {
		// 집 도착 + 거리가 K -> 가짓수 증가
		if (x == 0 && y == C - 1 && dist == K) {
			res++;
			return;
		}

		visit[x][y] = true;
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] == '.') {
				dfs(nx, ny, dist + 1);
			}
		}
		visit[x][y] = false;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < R && 0 <= y && y < C;
	}
}