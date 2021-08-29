package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 24.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 다익스트라
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: bfs를 통해 방을 바꾼 횟수를 오름차순으로 정의한 우선순위 큐를 사용하여 탐색을 하면서 검은방이라면 횟수를 하나 증가시키고 흰방이면 그대로 두는식으로 해서 끝방에 도착한다면 최소 횟수를 알 수 있다.
 * 느낀 점: https://www.acmicpc.net/problem/1261랑 배열 크기 범위만 다른걸 빼면 완전 똑같은 문제라서 어렵지 않게 풀었다.
 */
public class Boj_G4_2665_미로만들기 {
	static class Room implements Comparable<Room> {
		int x, y, cnt;

		public Room(int x, int y, int cnt) {
			super();
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Room o) {
			return this.cnt - o.cnt;
		}
	}

	static char[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		visit = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		System.out.println(bfs());
		br.close();
	}

	/* bfs를 통해 끝방에 도착했을 떄 검은방을 흰방으로 바꾼 최소 개수를 반환 */
	private static int bfs() {
		PriorityQueue<Room> pq = new PriorityQueue<>();
		visit[0][0] = true;
		pq.offer(new Room(0, 0, 0));

		while (!pq.isEmpty()) {
			Room r = pq.poll();
			if (r.x == N - 1 && r.y == N - 1) {// 끝방에 도착하면 횟수 반환(탐색을 하면 언젠가는 이 조건에 걸림)
				return r.cnt;
			}

			for (int dir = 0; dir < 4; dir++) {
				int nx = r.x + dx[dir];
				int ny = r.y + dy[dir];

				if (check(nx, ny) || visit[nx][ny]) {
					continue;
				}

				visit[nx][ny] = true;
				if (map[nx][ny] == '0') {// 검은방 이라면 횟수를 하나 더해주고 큐에 넣어줌
					pq.offer(new Room(nx, ny, r.cnt + 1));
				} else {// 흰방 이라면 횟수를 그대로 유지하고 큐에 넣어줌
					pq.offer(new Room(nx, ny, r.cnt));
				}
			}
		}

		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= N;
	}
}