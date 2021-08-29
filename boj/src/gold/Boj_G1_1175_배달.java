package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 19.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드1
 * 혼자 품: X
 * 풀이: 
 * 1. 입력값들을 map에 저장하면서 시작 좌표와 배달 목적지 값은 따로 저장한다.
 * 2. bfs를 통해 탐색 전 값들을 큐에 넣을 때, 시작 전 방향은 정해진게 없으므로 0~3을 제외한 임의의 값을 넣는다(-1)
 * 3-1. 탐색을 하면서 이전 방향값이랑 다음 방향값이 같다면 탐색하지 않는다.
 * 3-2. 각 배달 장소에 도착했다면 1번 장소(1), 2번 장소(2) 값을 state에 더해준다.
 * 3-3. state값이 3이 되었다면 모두 배달이 완료됬으므로 그 동안 걸린 시간값을 출력한다. 그렇지 않고 탐색이 끝났다면 -1를 출력한다.
 * 느낀 점: 
 * 방향 관련 부분은 접근 방법이 맞았으나, 상태를 4차원 배열로 나타낸다는 생각은 전혀 하지 못해서 다른 사람의 코드를 참조하게 되었다.
 * 아직 안풀어본 새로운 bfs 유형이라서 어떻게 이런 사고를 할 수 있는건지 푼사람들이 그저 대단하다는 생각만 들게 되었는데 이번을 계기로 셀프리뷰를 통해 습득하게 되었다.
 */
public class Boj_G1_1175_배달 {
	static class Move {
		int x, y, time, dir, state;// 행,열,거리,방향,현재 선물 배달 상태

		Move(int x, int y, int time, int dir, int state) {
			this.x = x;
			this.y = y;
			this.time = time;
			this.dir = dir;
			this.state = state;
		}
	}

	static char[][] map;
	static boolean[][][][] visit;
	static int[][] gift;// 배달 목적지 배열
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, sx, sy;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		gift = new int[2][2];
		int idx = 0;
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j);
				if (map[i][j] == 'S') {// 시작 좌표 저장
					sx = i;
					sy = j;
				} else if (map[i][j] == 'C') {// 배달 목적지 좌표 저장
					gift[idx][0] = i;
					gift[idx++][1] = j;
				}
			}
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs를 통해 최소 시간을 구함 */
	private static int bfs() {
		visit = new boolean[3][4][N][M];// [선물 배달 상태][방향][행][열]
		ArrayDeque<Move> q = new ArrayDeque<>();
		q.offer(new Move(sx, sy, 0, -1, 0));// 시작 전 방향은 정해진게 없으므로 -1을 넣음

		while (!q.isEmpty()) {
			Move m = q.poll();
			if (m.state == 3) {// 선물을 모두 배달 했다면 걸린 시간 반환(0: 어느 곳도 배달 못함, 1: 1번 장소에만 배달
														 //2: 2번 장소에만 배달, 3: 1,2번 모두 배달
				return m.time;
			}
			for (int d = 0; d < 4; d++) {
				if (d == m.dir) {// 이전 방향이랑 똑같은 방향은 갈 수 없다.
					continue;
				}
				int nx = m.x + dx[d];
				int ny = m.y + dy[d];

				// 1.배열 범위를 벗어나거나 2.이전 선물 배달 상태와 방향이 같은 좌표이거나(의미 없는 경로) 3.갈 수 없는 곳이면 continue
				if (check(nx, ny) || visit[m.state][d][nx][ny] || map[nx][ny] == '#') {
					continue;
				}

				visit[m.state][d][nx][ny] = true;
				if (nx == gift[0][0] && ny == gift[0][1] && m.state != 1) {// 1번 장소에 도착했는데 현재 경로로 배달한 적이 없다면
					q.offer(new Move(nx, ny, m.time + 1, d, m.state + 1));// 1번 장소에 배달 완료했다는 상태를 나타내기 위해 1을 더해줌
				} else if (nx == gift[1][0] && ny == gift[1][1] && m.state < 2) {// 2번 장소에 도착했는데 현재 경로로 배달한 적이 없다면
					q.offer(new Move(nx, ny, m.time + 1, d, m.state + 2));// 2번 장소에 배달 완료했다는 상태를 나타내기 위해 1을 더해줌
				} else {
					q.offer(new Move(nx, ny, m.time + 1, d, m.state));
				}
			}
		}

		return -1;// 배달할 수 없다면 -1 반환
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= M;
	}
}