import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 24.
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드2
 * 혼자 품: X
 * 풀이: 
 * 1-1. 입력받은 보드의 정보를 map에 저장하고 4차원 boolean형 배열을 선언한다.(visit[빨간 구슬 행][빨간 구슬 열][파란 구슬 행][파란 구슬 열])
 * 1-2. 입력값 중 빨간 구슬과 파란 구슬을 따로 Marble 객체에 좌표 형태로 저장한다.
 * 2-1. 빨간 구슬의 좌표와 파란 구슬의 좌표를 각각 큐에 넣고 탐색을 시작한다.
 * 2-2. 네 방향으로 각 구슬들을 구멍에 들어가거나 벽을 만날때까지 각 한방향으로 계속 이동시킨다.
 * 2-3. 두 구슬이 같은 위치에 있고 모두 구멍에 들어가지 않았다면 움직인 거리를 비교하여 더 많이 움직인 구슬의 좌표를 한칸 뒤로 되돌린다.
 * 2-4. 현재 구슬들이 아직 방문안한 좌표에 있다면 방문 처리 후 큐에 넣는다.
 * 3-1. 파란 구슬이 구멍에 들어갔다면 현재좌표에서의 탐색은 더이상 할 필요가 없으므로 다음 큐를 검사한다.
 * 3-2. 파란 구슬이 구멍에 안들어갔고 빨간 구슬이 구멍에 들어갔다면 현재까지의 기울인 횟수를 반환한다.
 * 4. 모든 탐색을 반복하면서 기울인 횟수가 10이 넘어가면 -1을 반환한다.
 * 느낀 점: 
 * 처음에 bfs를 쓰지 않고도 풀 수 있을 줄 알았는데 계속 로직이 꼬이다보니 bfs로 재시도를 하려 했는데 방문처리를 어떤식으로 해줘야 될지 감이 안잡혀서
 * 다른 사람의 코드를 참조했는데 4차원 배열을 선언해서 처리하는 방식이 있었다. 또한 어떤 방향으로 기울일 때 앞에 있는 구슬과 뒤에 있는 구슬을 어떻게 처리를
 * 해줘야 될지도 막막했었는데 이동한 거리를 기준으로 더 많이 이동한 구슬의 좌표를 한칸 이전으로 되돌리는 방법이 있다는 것도 알게 되었다.
 * 예전부터 풀려고 애썼던 문제였는데 결국 혼자 힘으론 풀지 못했지만 다른 방법을 깨닫게 되어 좋았다고 생각한다.
 */
public class Boj_G2_13460_구슬탈출2 {
	static class Marble {
		int x, y;

		public Marble(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static char[][] map;
	static boolean[][][][] visit;// 빨간구슬의 좌표 + 파란구슬의 좌표의 방문 처리를 위한 4차원 배열
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visit = new boolean[N][M][N][M];
		Marble red = null, blue = null;

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 'R') {
					red = new Marble(i, j);
				} else if (map[i][j] == 'B') {
					blue = new Marble(i, j);
				}
			}
		}

		System.out.println(bfs(red, blue));
		br.close();
	}

	/* bfs를 통해 최소 기울임 횟수를 반환, 10번을 넘어가면 -1을 반환 */
	private static int bfs(Marble red, Marble blue) {
		ArrayDeque<Marble> rq = new ArrayDeque<>();
		ArrayDeque<Marble> bq = new ArrayDeque<>();
		rq.offer(red);
		bq.offer(blue);

		int cnt = 0;
		while (!rq.isEmpty() && !bq.isEmpty()) {
			if (cnt > 10) {// 10번을 넘어간다면 중단
				break;
			}

			int size = rq.size();
			for (int i = 0; i < size; i++) {
				Marble r = rq.poll();
				Marble b = bq.poll();

				if (map[b.x][b.y] == 'O') {// 파란 구슬이 구멍에 들어가면 현재까지의 탐색은 더 할 필요가 없음
					continue;
				}

				if (map[r.x][r.y] == 'O') {// 파란 구슬이 구멍에 안들어갔고 빨간 구슬이 들어갔다면 기울인 횟수 반환
					return cnt;
				}

				for (int dir = 0; dir < 4; dir++) {
					int nrx = r.x, nry = r.y, nbx = b.x, nby = b.y;

					while (true) {
						nrx += dx[dir];
						nry += dy[dir];

						// 구멍에 들어가거나 벽을 만났다면 이동을 중단함, 이 때 벽이었다면 구슬의 위치를 한칸 이전으로 옮김
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

						// 구멍에 들어가거나 벽을 만났다면 이동을 중단함, 이 때 벽이었다면 구슬의 위치를 한칸 이전으로 옮김
						if (map[nbx][nby] == 'O') {
							break;
						}
						if (map[nbx][nby] == '#') {
							nbx -= dx[dir];
							nby -= dy[dir];
							break;
						}
					}

					// 두 구슬이 같은 위치에 있고 모두 구멍에 들어가지 않았다면 이동 거리를 비교
					// 더 많이 이동한 구슬이 뒤에 있는 구슬이므로 한칸 뒤로 옮김
					if (nrx == nbx && nry == nby && map[nrx][nry] != 'O') {
						int rdist = Math.abs(r.x - nrx) + Math.abs(r.y - nry);
						int bdist = Math.abs(b.x - nbx) + Math.abs(b.y - nby);

						if (rdist > bdist) {
							nrx -= dx[dir];
							nry -= dy[dir];
						} else {
							nbx -= dx[dir];
							nby -= dy[dir];
						}
					}

					if (!visit[nrx][nry][nbx][nby]) {// 현재 구슬들이 이전에 방문한 위치가 아니라면 방문 처리 후 큐에 넣어줌
						visit[nrx][nry][nbx][nby] = true;
						rq.offer(new Marble(nrx, nry));
						bq.offer(new Marble(nbx, nby));
					}
				}
			}
			cnt++;// 네 방향으로 모두 기울여봤다면 횟수 증가
		}

		return -1;
	}
}