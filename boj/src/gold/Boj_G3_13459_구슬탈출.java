package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 24.
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 혼자 품: O
 * 풀이: https://github.com/ksh7628/boj/blob/master/boj/src/Boj_G2_13460_%EA%B5%AC%EC%8A%AC%ED%83%88%EC%B6%9C2.java
 * 느낀 점: 이전 문제(https://www.acmicpc.net/problem/13460)을 복습 한다는 생각으로 풀었고 이러한 풀이방법을 잊지 않도록 해야겠다.
 */
public class Boj_G3_13459_구슬탈출 {
	static class Marble {
		int rx, ry, bx, by;

		public Marble(int rx, int ry, int bx, int by) {
			super();
			this.rx = rx;
			this.ry = ry;
			this.bx = bx;
			this.by = by;
		}
	}

	static char[][] map;
	static boolean[][][][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, ri, rj, bi, bj;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visit = new boolean[N][M][N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j);
				if (map[i][j] == 'R') {
					ri = i;
					rj = j;
				} else if (map[i][j] == 'B') {
					bi = i;
					bj = j;
				}
			}
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs를 통해 10번 이하로 기울여서 구멍에 들어간다면 1, 아니면 0을 반환 */
	private static int bfs() {
		ArrayDeque<Marble> q = new ArrayDeque<>();
		q.offer(new Marble(ri, rj, bi, bj));

		int cnt = 0;
		while (!q.isEmpty()) {
			if (cnt > 10) {// 10번을 넘어간다면 중단
				break;
			}

			int size = q.size();
			for (int i = 0; i < size; i++) {
				Marble m = q.poll();
				if (map[m.bx][m.by] == 'O') {// 파란 구슬이 구멍에 들어가면 현재까지의 탐색은 더 할 필요가 없음
					continue;
				}
				if (map[m.rx][m.ry] == 'O') {// 파란 구슬이 구멍에 안들어갔고 빨간 구슬이 들어갔다면 1을 반환
					return 1;
				}

				for (int dir = 0; dir < 4; dir++) {
					int nrx = m.rx, nry = m.ry, nbx = m.bx, nby = m.by;

					while (true) {
						nrx += dx[dir];
						nry += dy[dir];

						if (map[nrx][nry] == 'O') {
							break;
						}
						if (map[nrx][nry] == '#') {
							nrx -= dx[dir];
							nry -= dy[dir];
							break;
						}
					}

					while (true) {
						nbx += dx[dir];
						nby += dy[dir];

						if (map[nbx][nby] == 'O') {
							break;
						}
						if (map[nbx][nby] == '#') {
							nbx -= dx[dir];
							nby -= dy[dir];
							break;
						}
					}

					// 두 구슬이 같은 위치에 있고 모두 구멍에 들어가지 않았다면 이동 거리를 비교해서
					// 더 많이 이동한 구슬이 뒤에 있는 구슬이므로 한칸 이전으로 옮김
					if (nrx == nbx && nry == nby && map[nrx][nry] != 'O') {
						int rd = Math.abs(m.rx - nrx) + Math.abs(m.ry - nry);
						int bd = Math.abs(m.bx - nbx) + Math.abs(m.by - nby);

						if (rd > bd) {
							nrx -= dx[dir];
							nry -= dy[dir];
						} else {
							nbx -= dx[dir];
							nby -= dy[dir];
						}
					}

					// 아직 방문을 안했다면 방문 처리 후 큐에 넣음
					if (!visit[nrx][nry][nbx][nby]) {
						visit[nrx][nry][nbx][nby] = true;
						q.offer(new Marble(nrx, nry, nbx, nby));
					}
				}
			}
			cnt++;// 기울임 횟수 증가
		}

		return 0;
	}
}