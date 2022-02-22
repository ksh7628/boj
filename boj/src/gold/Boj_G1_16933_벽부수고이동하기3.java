package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 22.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드1
 * 소요 시간: 0h 58m
 * 혼자 품: O
 * 풀이: 
 * 1. 방문 배열을 3차원으로 정의한다. -> [현재 벽을 부순 횟수][행][열]
 * 2. bfs 방식으로 탐색하면서 낮/밤인 경우와 다시 벽/길인 경우로 나누어서 진행한다.
 * 2-1. 낮-벽인 경우, 아직 벽을 부술 수 있고 **예전에 부쉈던 벽이 아닐 경우**
 * 2-2. 낮-길인 경우, 방문하지 않은 경우
 * 2-3. 밤-벽인 경우, 제자리에서 기다림 -> **큐에 넣을 때 제자리 좌표를 넣고 방문 처리 하지 않는다**
 * 2-4. 밤-길인 경우. 2-2와 동일
 * 2-5. 나머지 경우, 더 이상 탐색이 불가능함
 * 3. 제일 먼저 도착점에 도착하는 경우 dist 값을 반환한다.
 * 느낀 점: 
 * 문제에서 제시하는 조건들을 모두 분석해서 조건문 분기처리 하지 않으면 시간 초과나 메모리 초과가 뜰 수 밖에 없는 문제였다.
 * 처음 시간초과가 나는 로직까지는 찾았는데 visit 배열을 4차원으로 선언하다 보니 2*10*1000*1000 = 2*10^7byte나 필요해서
 * 메모리 초과까지 발생했었는데 Wall 객체만으로도 낮과 밤을 구분할 수 있다는 것을 깨닫고 풀게 되었다.
 * 왜 골드1이나 되는지 알 수 있는 문제였고 문제의 요구사항이 짧아도 분석을 세밀하게 해야 된다는 것을 알 수 있는 문제였다.
 */
public class Boj_G1_16933_벽부수고이동하기3 {
	static class Wall {
		int x, y, cnt, state;

		public Wall(int x, int y, int cnt, int state) {
			super();
			this.x = x;
			this.y = y;
			this.cnt = cnt;
			this.state = state;
		}
	}

	static char[][] map;
	static boolean[][][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visit = new boolean[K + 1][N][M];// [횟수][행][열]

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs로 시간과 벽 판별하면서 탐색 */
	private static int bfs() {
		int dist = 1;
		ArrayDeque<Wall> q = new ArrayDeque<>();
		visit[0][0][0] = true;
		q.offer(new Wall(0, 0, 0, 1));

		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				Wall w = q.poll();

				// 도착
				if (w.x == N - 1 && w.y == M - 1) {
					return dist;
				}

				for (int d = 0; d < 4; d++) {
					int nx = w.x + dx[d];
					int ny = w.y + dy[d];
					int ns = 1 - w.state;

					if (!check(nx, ny)) {
						continue;
					}

					if (w.state == 1) {// 낮
						// 벽 만났을 때 아직 부술 수 있고 예전에 부쉈던 벽이 아님
						if (map[nx][ny] == '1' && w.cnt < K && !visit[w.cnt + 1][nx][ny]) {
							visit[w.cnt + 1][nx][ny] = true;
							q.offer(new Wall(nx, ny, w.cnt + 1, ns));
						} else if (map[nx][ny] == '0' && !visit[w.cnt][nx][ny]) {// 지나갈 수 있다
							visit[w.cnt][nx][ny] = true;
							q.offer(new Wall(nx, ny, w.cnt, ns));
						}
					} else {// 밤
						// 벽이고 아직 부술 수 있는 횟수가 남음
						// 하지만 밤 이므로 부술 수 없고 제자리에서 기다림
						// 방문처리 하지 않음
						if (map[nx][ny] == '1' && w.cnt < K) {
							q.offer(new Wall(w.x, w.y, w.cnt, ns));
						} else if (map[nx][ny] == '0' && !visit[w.cnt][nx][ny]) {// 지나갈 수 있다
							visit[w.cnt][nx][ny] = true;
							q.offer(new Wall(nx, ny, w.cnt, ns));
						}
					}
				}
			}

			dist++;
		}

		// 도착 못함
		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}