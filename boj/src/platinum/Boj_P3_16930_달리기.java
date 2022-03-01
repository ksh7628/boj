package platinum;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 2.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 플래티넘3
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * 1. 방문 + 거리를 저장하는 dist 배열을 -1로 초기화하고 출발점부터 bfs를 수행한다.
 * 2. K칸까지 한 칸씩 전진하면서 다음 조건들을 고려하여 탐색한다.
 * 2-1. 배열 범위를 벗어나거나 벽이라면 해당 방향 탐색을 종료
 * 2-2. 아직 방문을 안했다면 방문처리 + 큐에 넣음
 * 2-3. 방문한 곳인데 최단거리보다 현재 거리가 크다면 해당 방향 탐색 종료
 * 3. 도착점에 도달한다면 최단거리 반환, 못간다면 -1 반환한다.
 * 느낀 점: 
 * 간단한 문제인줄 알았는데 2-3 조건을 고려못해서 시간을 많이 소모하게 되었다.
 * 플래티넘3 문제답게 생각의 전환이 중요한 문제라고 생각한다.
 */
public class Boj_P3_16930_달리기 {
	static char[][] map;
	static int[][] dist;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, K, sx, sy, ex, ey;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		dist = new int[N][M];

		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
			Arrays.fill(dist[i], -1);
		}

		st = new StringTokenizer(br.readLine(), " ");
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;
		ex = Integer.parseInt(st.nextToken()) - 1;
		ey = Integer.parseInt(st.nextToken()) - 1;

		System.out.println(bfs());
		br.close();
	}

	/* bfs로 최단거리를 반환한다 */
	private static int bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		dist[sx][sy] = 0;
		q.offer(new int[] { sx, sy });

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			// 도착
			if (cur[0] == ex && cur[1] == ey) {
				return dist[ex][ey];
			}

			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];
				int cnt = 0;

				// K길이까지 검사
				// 배열 밖 or 벽이면 더 이상 탐색 X
				// 방문 안한 경우, 방문처리 + 큐에 넣음
				// 방문 한 경우, 최단거리를 갱신하지 못한다면 더 이상 탐색 X
				while (isCheck(nx, ny) && map[nx][ny] == '.' && ++cnt <= K) {
					if (dist[nx][ny] == -1) {
						dist[nx][ny] = dist[cur[0]][cur[1]] + 1;
						q.offer(new int[] { nx, ny });
					}
					
					if (dist[nx][ny] != -1 && dist[nx][ny] <= dist[cur[0]][cur[1]]) {
						break;
					}

					nx += dx[d];
					ny += dy[d];
				}
			}
		}

		return -1;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}