package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: ksh76
 * @date	: 2022. 3. 26.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 실버1
 * 소요 시간: 0h 06m
 * 혼자 품: O
 * 풀이: BFS로 풀이.
 * 느낀 점: 타임어택 하기위한 기초 bfs문제였다.
 */
public class Boj_S1_16948_데스나이트 {
	static boolean[][] visit;
	static int[] dx = { -2, -2, 0, 0, 2, 2 };
	static int[] dy = { -1, 1, -2, 2, -1, 1 };
	static int N, sx, sy, ex, ey;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		sx = Integer.parseInt(st.nextToken());
		sy = Integer.parseInt(st.nextToken());
		ex = Integer.parseInt(st.nextToken());
		ey = Integer.parseInt(st.nextToken());

		System.out.println(bfs());
		br.close();
	}

	private static int bfs() {
		visit = new boolean[N][N];
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[sx][sy] = true;
		q.offer(new int[] { sx, sy });

		int dist = 0;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();
				if (cur[0] == ex && cur[1] == ey) {
					return dist;
				}

				for (int d = 0; d < 6; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (isCheck(nx, ny) && !visit[nx][ny]) {
						visit[nx][ny] = true;
						q.offer(new int[] { nx, ny });
					}
				}
			}
			dist++;
		}

		return -1;
	}

	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}