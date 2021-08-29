package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 27.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드2
 * 혼자 품: O
 * 풀이: 
 * 1. 입력값들을 map에 저장하면서 출발 전 통나무의 좌표, 상태와 최종 위치에 있는 통나무의 좌표, 상태를 저장
 * 2. bfs를 통해 현재 통나무의 상태에 따라 네 방향의 검사를 다르게 한다.
 * 3. 회전을 할 수 있다면 mod 2 연산을 통해 방문 여부를 체크하고 방문을 안했다면 회전을 시킨다.
 * 4. 모양도 같고 최종 위치의 중앙 좌표와 통나무의 중앙 좌표가 일치한다면 동작 횟수를 구할 수 있다.
 * 느낀 점: 
 * 처음에는 세 점을 전부 큐에 넣어서 탐색을 시도했었는데 굳이 그럴 필요 없이 중앙 좌표만 넣고 검사할때 현재 모양과 방향에 따라 각각 다르게 처리해주면
 * 된다는 것을 알게 되었다. 또한 통나무의 상태를 어떻게 저장할 지 고민이 많았는데 3차원 visit을 통해 0:가로, 1:세로 로 따로 정의하여 풀게 되었다.
 * 꽤 많은 시간이 걸린 문제였지만 알고리즘 풀이 실력에 도움이 된 문제였다. 마지막으로 상태와 방향에 따라 검사하는 로직이 살짝 다른데 약간 하드코딩을 하게 되어서
 * 나중에 다시 코드 길이를 줄여봐야겠다고 느꼈다.
 */
public class Boj_G2_1938_통나무옮기기 {
	static class Tree {// 통나무 중앙 좌표(행,열), 동작 횟수, 통나무 상태(0:가로, 1:세로)
		int x, y, cnt, s;

		public Tree(int x, int y, int cnt, int s) {
			super();
			this.x = x;
			this.y = y;
			this.cnt = cnt;
			this.s = s;
		}
	}

	static char[][] map;
	static boolean[][][] visit;// 가로 통나무와 세로 통나무의 방문을 나타내기 위한 3차원 boolean형 배열
	static int[][] start = new int[3][2];
	static int[][] end = new int[3][2];
	static int[][][] dir = { { { -1, -1, -1 }, { -1, 0, 1 } }, { { 1, 1, 1 }, { -1, 0, 1 } },
			{ { -1, 0, 1 }, { 1, 1, 1 } }, { { -1, 0, 1 }, { -1, -1, -1 } } };
	static int N, startS, endS;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		visit = new boolean[2][N][N];

		int sr = 0, sc = 0, er = 0, ec = 0;
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 'B') {// 통나무 시작 좌표를 저장
					start[sr][sc++] = i;
					start[sr++][sc] = j;
					sc = 0;
				} else if (map[i][j] == 'E') {// 통나무 최종 위치 좌표를 저장
					end[er][ec++] = i;
					end[er++][ec] = j;
					ec = 0;
				}
			}
		}

		System.out.println(process());
	}

	private static int process() {
		checkState();
		return bfs();
	}

	/* 옮기기 전 통나무 상태와 옮긴 후 최종 위치의 통나무 상태를 저장  */
	private static void checkState() {
		if (start[0][1] == start[1][1] && start[1][1] == start[2][1]) {
			startS = 1;
		}

		if (end[0][1] == end[1][1] && end[1][1] == end[2][1]) {
			endS = 1;
		}
	}

	/* bfs를 통해 최소 동작 횟수를 반환, 갈 수 없다면 0 반환 */
	private static int bfs() {
		ArrayDeque<Tree> q = new ArrayDeque<>();
		visit[startS][start[1][0]][start[1][1]] = true;
		q.offer(new Tree(start[1][0], start[1][1], 0, startS));

		while (!q.isEmpty()) {
			Tree t = q.poll();
			// 최종 위치의 통나무 중앙 좌표가 현재 통나무 중앙 좌표와 같고 모양도 같다면 동작 횟수 반환
			if (t.x == end[1][0] && t.y == end[1][1] && t.s == endS) {
				return t.cnt;
			}

			boolean isRotate = true;// 주위 나무를 체크하여 회전 여부를 알 수 있도록 boolean형 변수 선언
			if (t.s == 0) {// 가로
				for (int d = 0; d < 2; d++) {// 가로는 통나무 바로 위와 아래의 각각 세방향을 체크해야함
					int nx1 = t.x + dir[d][0][0];
					int ny1 = t.y + dir[d][1][0];
					int nx2 = t.x + dir[d][0][1];
					int ny2 = t.y + dir[d][1][1];
					int nx3 = t.x + dir[d][0][2];
					int ny3 = t.y + dir[d][1][2];

					if (check(nx1, ny1) && check(nx2, ny2) && check(nx3, ny3)) {
						if (visit[t.s][nx2][ny2]) {
							continue;
						}

						// 인접한 좌표에 나무가 있다면 회전을 할 수 없다고 저장하고 continue
						if (map[nx1][ny1] == '1' || map[nx2][ny2] == '1' || map[nx3][ny3] == '1') {
							isRotate = false;
							continue;
						}

						visit[t.s][nx2][ny2] = true;
						q.offer(new Tree(nx2, ny2, t.cnt + 1, t.s));
					}
				}

				for (int d = 2; d < 4; d++) {
					int nx = t.x + dir[d][0][1];
					int ny = t.y + dir[d][1][1];

					if (check(nx + dir[d][0][1], ny + dir[d][1][1])) {
						if (visit[t.s][nx][ny]) {
							continue;
						}

						if (map[nx + dir[d][0][1]][ny + dir[d][1][1]] == '1') {
							continue;
						}

						visit[t.s][nx][ny] = true;
						q.offer(new Tree(nx, ny, t.cnt + 1, t.s));
					}
				}
			} else {// 세로
				for (int d = 0; d < 2; d++) {
					int nx = t.x + dir[d][0][1];
					int ny = t.y + dir[d][1][1];

					if (check(nx + dir[d][0][1], ny + dir[d][1][1])) {
						if (visit[t.s][nx][ny]) {
							continue;
						}

						if (map[nx + dir[d][0][1]][ny + dir[d][1][1]] == '1') {
							continue;
						}

						visit[t.s][nx][ny] = true;
						q.offer(new Tree(nx, ny, t.cnt + 1, t.s));
					}
				}

				for (int d = 2; d < 4; d++) {// 세로는 통나무 바로 왼쪽과 오른쪽의 각각 세방향을 체크해야함
					int nx1 = t.x + dir[d][0][0];
					int ny1 = t.y + dir[d][1][0];
					int nx2 = t.x + dir[d][0][1];
					int ny2 = t.y + dir[d][1][1];
					int nx3 = t.x + dir[d][0][2];
					int ny3 = t.y + dir[d][1][2];

					if (check(nx1, ny1) && check(nx2, ny2) && check(nx3, ny3)) {
						if (visit[t.s][nx2][ny2]) {
							continue;
						}

						// 인접한 좌표에 나무가 있다면 회전을 할 수 없다고 저장하고 continue
						if (map[nx1][ny1] == '1' || map[nx2][ny2] == '1' || map[nx3][ny3] == '1') {
							isRotate = false;
							continue;
						}

						visit[t.s][nx2][ny2] = true;
						q.offer(new Tree(nx2, ny2, t.cnt + 1, t.s));
					}
				}
			}

			// 회전을 하려고 하는데 배열 범위를 넘어갈 경우 continue
			if (t.x < 1 || t.x >= N - 1 || t.y < 1 || t.y >= N - 1) {
				continue;
			}

			int ns = (t.s + 1) % 2;// 회전(0->1, 1->0)
			if (isRotate && !visit[ns][t.x][t.y]) {// 주위에 장애물이 없고 회전한 상태로 방문 안했을 경우 회전하여 큐에 넣어줌
				q.offer(new Tree(t.x, t.y, t.cnt + 1, ns));
			}
		}

		return 0;
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}