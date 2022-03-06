package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 6.
 * 
 * 분류: 그래프 이론, 다익스트라
 * 난이도: 골드4
 * 소요 시간: 0h 18m
 * 혼자 품: O
 * 풀이: 인접 행렬과 우선순위 큐를 사용한 다익스트라로 품.
 * 느낀 점: 다익스트라 기본 문제라서 어렵지 않게 풀었다.
 */
public class Boj_G4_4485_녹색옷입은애가젤다지 {
	static class Node implements Comparable<Node> {
		int x, y, dist;

		public Node(int x, int y, int dist) {
			super();
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.dist, o.dist);
		}
	}

	static int[][] map, distance;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N;
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int tc = 0;

		while (true) {
			tc++;
			N = Integer.parseInt(br.readLine());

			if (N == 0) {
				break;
			}

			map = new int[N][N];
			distance = new int[N][N];

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					distance[i][j] = INF;
				}
			}

			sb.append("Problem ").append(tc).append(": ").append(dijkstra()).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 인접행렬 + 우선순위 큐를 사용한 다익스트라 알고리즘 */
	private static int dijkstra() {
		boolean[][] visit = new boolean[N][N];
		Queue<Node> pq = new PriorityQueue<>();
		distance[0][0] = map[0][0];
		pq.offer(new Node(0, 0, distance[0][0]));

		while (!pq.isEmpty()) {
			Node node = pq.poll();

			if (node.x == N - 1 && node.y == N - 1) {
				return node.dist;
			}

			if (visit[node.x][node.y]) {
				continue;
			}
			visit[node.x][node.y] = true;

			for (int d = 0; d < 4; d++) {
				int nx = node.x + dx[d];
				int ny = node.y + dy[d];

				if (isCheck(nx, ny) && distance[node.x][node.y] + map[nx][ny] < distance[nx][ny]) {
					distance[nx][ny] = distance[node.x][node.y] + map[nx][ny];
					pq.offer(new Node(nx, ny, distance[nx][ny]));
				}
			}
		}

		return 0;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}