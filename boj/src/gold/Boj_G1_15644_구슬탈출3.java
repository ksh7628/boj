package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 8.
 * 
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 시뮬레이션
 * 난이도: 골드1
 * 소요 시간: 0h 28m
 * 혼자 품: O
 * 풀이: 
 * 1. N, M이 최대 10이므로 빨간 구슬 좌표와 파랑 구슬 좌표를 상태로 표현하는 4차원 visit 배열을 정의한다.
 * 2. bfs를 수행하면서 빨간 구슬과 파랑 구슬을 벽 또는 구멍에 부딪힐때까지 이동시킨다.
 * 3. 파랑 구슬이 구멍에 빠진 경우는 현재 탐색을 중단하고 다음 탐색으로 넘어간다.
 * 4. 3에서 파랑 구슬이 빠지는 경우를 처리했기 때문에 빨간 구슬이 구멍에 빠질 경우, 기울인 횟수와 큐에 저장된 기울이는 순서를 출력하고 끝낸다.
 * 5. 3, 4에서 처리되지 않았다면 벽에 부딪혀서 멈춘 경우이므로 두 구슬의 좌표를 한칸 뒤로 이동시킨다.
 * 6. 빨간 구슬과 파란 구슬 좌표가 같다면 같은 방향으로 이동하다가 부딪힌 경우이므로 더 많이 이동한 구슬을 한칸 뒤로 이동시킨다.
 * 7. 마지막으로 방문처리가 안된 좌표라면 방문처리 후 큐에 넣고 다음 bfs를 수행한다. -> 4번에서 걸리거나 횟수 10번이 넘어가거나 모든 방문이 되어서 종료되거나
 * 느낀 점: 약 9개월전에 풀었던 구슬탈출 시리즈에서 경로가 추가된 문제였는데 4차원 배열을 생각해내서 어렵지 않게 푼 것 같다.
 */
public class Boj_G1_15644_구슬탈출3 {
	static class Marble {
		int rx, ry, bx, by;
		String order;

		public Marble(int rx, int ry, int bx, int by, String order) {
			super();
			this.rx = rx;
			this.ry = ry;
			this.bx = bx;
			this.by = by;
			this.order = order;
		}
	}

	static char[][] map;
	static boolean[][][][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static String[] ds = { "U", "D", "L", "R" };
	static int N, M, srx, sry, sbx, sby;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visit = new boolean[N][M][N][M];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 'R') {
					map[i][j] = '.';
					srx = i;
					sry = j;
				} else if (map[i][j] == 'B') {
					map[i][j] = '.';
					sbx = i;
					sby = j;
				}
			}
		}

		bfs();
		br.close();
	}

	private static void bfs() {
		ArrayDeque<Marble> q = new ArrayDeque<>();
		visit[srx][sry][sbx][sby] = true;
		q.offer(new Marble(srx, sry, sbx, sby, ""));

		int cnt = 0;
		while (!q.isEmpty()) {
			if (++cnt > 10) {
				break;
			}
			int size = q.size();

			for (int i = 0; i < size; i++) {
				Marble m = q.poll();

				for (int d = 0; d < 4; d++) {
					int nrx = m.rx + dx[d];
					int nry = m.ry + dy[d];
					int nbx = m.bx + dx[d];
					int nby = m.by + dy[d];
					String ns = m.order + ds[d];

					int rDist = 0, bDist = 0;
					boolean isGoalR = false, isGoalB = false;

					// 빨간 구슬이 벽에 부딪히거나 구멍에 빠질 때까지 이동
					while (map[nrx][nry] != '#') {
						rDist++;
						if (map[nrx][nry] == 'O') {
							isGoalR = true;
							break;
						}

						nrx += dx[d];
						nry += dy[d];
					}

					// 파란 구슬이 벽에 부딪히거나 구멍에 빠질 때까지 이동
					while (map[nbx][nby] != '#') {
						bDist++;
						if (map[nbx][nby] == 'O') {
							isGoalB = true;
							break;
						}

						nbx += dx[d];
						nby += dy[d];
					}

					// 파랑 구슬이 구멍에 빠짐
					if (isGoalB) {
						continue;
					}

					// 빨간 구슬만 구멍에 빠짐
					if (isGoalR) {
						System.out.println(cnt);
						System.out.println(ns);
						return;
					}
					
					// 현재 벽 좌표이므로 한칸 뒤로
					nrx -= dx[d];
					nry -= dy[d];
					nbx -= dx[d];
					nby -= dy[d];

					// 빨간 구슬 좌표와 파랑 구슬 좌표가 같다
					// -> 둘 중 더 이동한 구슬을 한칸 뒤로
					if (nrx == nbx && nry == nby) {
						if (rDist > bDist) {
							nrx -= dx[d];
							nry -= dy[d];
						} else {
							nbx -= dx[d];
							nby -= dy[d];
						}
					}

					if (!visit[nrx][nry][nbx][nby]) {
						visit[nrx][nry][nbx][nby] = true;
						q.offer(new Marble(nrx, nry, nbx, nby, ns));
					}
				}
			}
		}

		System.out.println(-1);
	}
}