package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 10.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: https://www.acmicpc.net/problem/2206에 있는 문제와 똑같음
 * 느낀 점: 이 문제처럼 좌표나 거리 외에 현재 상태를 큐에 넣으면서 bfs를 쓰는 문제 유형이 무엇인지를 알게 되었다.
 */
public class Boj_G4_14923_미로탈출 {
	static class Point {
		int x, y, dist, cnt;

		Point(int x, int y, int dist, int cnt) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.cnt = cnt;
		}
	}

	static int[][] map;
	static boolean[][][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, hx, hy, ex, ey;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		hx = Integer.parseInt(st.nextToken()) - 1;
		hy = Integer.parseInt(st.nextToken()) - 1;

		st = new StringTokenizer(br.readLine(), " ");
		ex = Integer.parseInt(st.nextToken()) - 1;
		ey = Integer.parseInt(st.nextToken()) - 1;

		map = new int[N][M];
		visit = new boolean[N][M][2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(bfs());
	}

	/* bfs를 통해 이동하면서 벽을 부쉈는지 안부쉈는지를 3차원 배열 인덱스로 표시 */
	private static int bfs() {
		Queue<Point> q = new LinkedList<>();
		visit[hx][hy][0] = true;
		q.offer(new Point(hx, hy, 0, 0));
		while (!q.isEmpty()) {
			Point p = q.poll();
			if (p.x == ex && p.y == ey) {
				return p.dist;
			}

			for (int dir = 0; dir < 4; dir++) {
				int nx = p.x + dx[dir];
				int ny = p.y + dy[dir];
				if (check(nx, ny)) {
					continue;
				}

				if (map[nx][ny] == 1 && p.cnt == 0 && !visit[nx][ny][p.cnt]) {
					visit[nx][ny][p.cnt + 1] = true;
					q.offer(new Point(nx, ny, p.dist + 1, p.cnt + 1));
				} else if (map[nx][ny] == 0 && !visit[nx][ny][p.cnt]) {
					visit[nx][ny][p.cnt] = true;
					q.offer(new Point(nx, ny, p.dist + 1, p.cnt));
				}
			}
		}
		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= M;
	}
}