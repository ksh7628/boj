package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 10.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 혼자 품: O
 * 풀이: 
 * 1. 맵의 정보를 입력받는데 메모리를 조금이라도 아끼기 위해 boolean 타입의 배열에 벽:true, 이동할 수 있는 곳:false 로 정의
 * 2-1. bfs를 통해 탐색 시작
 * 2-2. 벽을 만났다면 현재까지 벽을 부순 횟수가 벽을 부술 수 있는 횟수 미만이고 벽을 부쉈을 때 방문을 해야만 되는 곳이면 벽을 부순 횟수 증가 및 거리 갱신 후 큐에 넣어줌
 * 2-3. 벽이 아니라면 거리 갱신 후 큐에 넣어줌
 * 느낀 점: 
 * https://www.acmicpc.net/problem/2206 주소에 있는 문제를 풀었을 때처럼 로직을 그대로 사용하고 횟수 처리만 해줬으면 진작 풀었을거 같은데
 * 다른 방식을 이용하려고 이것 저것 건들다 보니 코드가 꼬여버려서 결국 저번 방식으로 풀게 되었다.
 * 다른 사람들의 코드를 보니 3차원 배열로 나타내지 않고 방문처리를 하는 int형 2차원 배열에 벽을 부순 횟수를 갱신해 나가면서 푸는 방법이 있던데
 * 저렇게 풀면 메모리와 시간을 좀 더 절약할 수 있다는 것을 알게 된 문제였다.
 */
public class Boj_G3_14442_벽부수고이동하기2 {
	static class Point {
		int x, y, cnt;// 행, 열, 벽을 부순 횟수

		Point(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}

	static boolean[][] map;
	static int[][][] dist;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new boolean[N][M];
		dist = new int[N][M][K + 1];

		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			for (int j = 0; j < M; j++) {
				if (s.charAt(j) - '0' == 1) {
					map[i][j] = true;
				}
			}
		}

		System.out.println(bfs());
		br.close();
	}

	private static int bfs() {
		Queue<Point> q = new LinkedList<>();
		dist[0][0][0] = 1;
		q.offer(new Point(0, 0, 0));

		while (!q.isEmpty()) {
			Point p = q.poll();
			if (p.x == N - 1 && p.y == M - 1) {// 목적지에 도착했다면 거리 반환
				return dist[p.x][p.y][p.cnt];
			}

			for (int dir = 0; dir < 4; dir++) {
				int nx = p.x + dx[dir];
				int ny = p.y + dy[dir];
				if (check(nx, ny) || dist[nx][ny][p.cnt] > 0) {
					continue;
				}

				if (map[nx][ny]) {// 벽이 있다면
					// 이미 벽을 부술 수 있는 횟수를 초과했거나 벽을 부숴도 이미 방문한 곳이라면 continue
					if (p.cnt >= K || dist[nx][ny][p.cnt + 1] > 0) {
						continue;
					}
					dist[nx][ny][p.cnt + 1] = dist[p.x][p.y][p.cnt] + 1;
					q.offer(new Point(nx, ny, p.cnt + 1));
				} else {// 벽이 없다면
					dist[nx][ny][p.cnt] = dist[p.x][p.y][p.cnt] + 1;
					q.offer(new Point(nx, ny, p.cnt));
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