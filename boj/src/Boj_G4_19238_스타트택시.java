import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 26.
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 시뮬레이션
 * 난이도: 골드4
 * 소요 시간: 1h 21m
 * 혼자 품: O
 * 풀이: 
 * 1. 택시의 좌표에서 출발하여 연료가 다 소비되기 전까지 bfs로 탐색하여 가까운 승객을 태움
 * 2. 거리가 같은 승객이 여러명이면 행번호가 작은 승객을, 행번호가 같은 승객이 여러명이라면 열 번호가 작은 승객을 태움
 * 3. 승객을 태웠다면 소비된 연료의 양을 빼주고 해당 승객의 목적지까지 연료가 충분하다면 목적지에 승객을 내려주고 소비된 연료의 양을 더해줌
 * 4. 승객을 태우러 가거나 승객을 내려주는 과정에서 연료가 부족하다면 -1을 출력하고 그렇지 않다면 업무가 끝났을 때의 연료의 양을 출력
 * 느낀 점: 
 * 아기 상어 문제와 유사한 문제였는데 처음에 승객의 목적지가 여러 곳일 수 있다는 점을 생각하지 못해서 시간이 더 걸렸다.
 * 시뮬레이션 문제는 지문을 정말 꼼꼼히 읽어야만 한다고 풀때마다 느끼지만 막상 문제를 풀때 놓치는 점이 꼭 생겨서 지문 해석 또한 꼼꼼히 해야된다고 느끼게 되었다.
 */
public class Boj_G4_19238_스타트택시 {
	static class Pair {
		int x, y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static int[][] map, start;
	static Pair[] end;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, amount, sx, sy;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		amount = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		start = new int[N][N];
		end = new Pair[M + 1];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;

		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int sr = Integer.parseInt(st.nextToken()) - 1;
			int sc = Integer.parseInt(st.nextToken()) - 1;
			int er = Integer.parseInt(st.nextToken()) - 1;
			int ec = Integer.parseInt(st.nextToken()) - 1;

			start[sr][sc] = i;
			end[i] = new Pair(er, ec);
		}

		solution();
		System.out.println(amount);
		br.close();
	}

	private static void solution() {
		for (int i = 0; i < M; i++) {
			int res = startBfs();// 택시 -> 승객 탑승
			if (res == -1 || amount < res) {
				amount = -1;
				return;
			}
			amount -= res;// 택시에서 승객까지 소비된 연료를 빼줌

			res = endBfs();// 승객 -> 목적지
			if (res == -1 || amount < res) {
				amount = -1;
				return;
			}
			amount += res;// 목적지까지 소비된 연료를 빼고 두 배를 더해줌 -> 소비된 연료를 더해줌
		}
	}

	// 택시가 승객을 태울 때까지 소비되는 연료의 양을 구함
	private static int startBfs() {
		int dist = 0, x = N, y = N;
		boolean enable = false;// 승객을 태웠는지 안태웠는지 판단
		visit = new boolean[N][N];
		ArrayDeque<Pair> q = new ArrayDeque<>();
		visit[sx][sy] = true;
		q.offer(new Pair(sx, sy));

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				if (start[p.x][p.y] > 0) {
					enable = true;
					if (p.x < x) {// 행 번호가 작다면 작은 값으로 갱신
						x = p.x;
						y = p.y;
					} else if (p.x == x && p.y < y) { // 행 번호가 같고 열 번호가 작다면 작은 값으로 갱신
						x = p.x;
						y = p.y;
					}
				}

				for (int dir = 0; dir < 4; dir++) {
					int nx = p.x + dx[dir];
					int ny = p.y + dy[dir];

					if (check(nx, ny) && !visit[nx][ny] && map[nx][ny] != 1) {
						visit[nx][ny] = true;
						q.offer(new Pair(nx, ny));
					}
				}
			}

			if (enable) {// 승객을 태웠다면 탐색 종료
				break;
			}
			dist++;
		}

		if (!enable) {// 승객을 못태웠다면 -1 리턴
			return -1;
		}

		sx = x;// 택시 좌표 갱신
		sy = y;
		return dist;
	}

	// 탑승한 승객을 태우고 그 승객의 목적지까지 소비되는 연료의 양을 구함
	private static int endBfs() {
		int dist = 0, num = start[sx][sy];
		visit = new boolean[N][N];
		ArrayDeque<Pair> q = new ArrayDeque<>();
		start[sx][sy] = 0;
		visit[sx][sy] = true;
		q.offer(new Pair(sx, sy));

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();

				if (end[num].x == p.x && end[num].y == p.y) {// 해당 승객의 목적지에 도착했다면
					sx = p.x;
					sy = p.y;
					return amount >= dist ? dist : -1;// 현재 연료양 보다 소비된 연료양이 초과한다면 -1 리턴
				}

				for (int dir = 0; dir < 4; dir++) {
					int nx = p.x + dx[dir];
					int ny = p.y + dy[dir];

					if (check(nx, ny) && !visit[nx][ny] && map[nx][ny] != 1) {
						visit[nx][ny] = true;
						q.offer(new Pair(nx, ny));
					}
				}
			}

			dist++;
		}

		return -1;
	}

	// 배열 경계값 체크
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}