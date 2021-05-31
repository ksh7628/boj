package bfs;

import java.io.*;
import java.util.*;

public class Boj_G2_4991_로봇청소기 {
	static char[][] map;
	static int[][] dist;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int H, W, si, sj, cnt, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			if (W == 0 && H == 0) {
				break;
			}
			map = new char[H][W];
			dist = new int[H][W];

			for (int i = 0; i < H; i++) {
				String str = br.readLine();
				for (int j = 0; j < W; j++) {
					map[i][j] = str.charAt(j);
					if (map[i][j] == 'o') {
						si = i;
						sj = j;
					}
				}
			}

			res = 0;
			bfs();
			sb.append(resultCheck()).append("\n");
		}
		System.out.print(sb);
		br.close();
	}

	private static void bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		dist[si][sj] = 1;
		q.offer(new int[] { si, sj });
		while (!q.isEmpty()) {
			int[] p = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = p[0] + dx[dir];
				int ny = p[1] + dy[dir];
				if (check(nx, ny) || map[nx][ny] == 'x' || dist[nx][ny] > 0) {
					continue;
				}
				if (map[nx][ny] == '*') {
					res += dist[p[0]][p[1]];
					dist = new int[H][W];
					q.clear();
					map[nx][ny] = '.';
					dist[nx][ny] = 1;
					q.offer(new int[] { nx, ny });
					break;
				}
				dist[nx][ny] = dist[p[0]][p[1]] + 1;
				q.offer(new int[] { nx, ny });
			}
		}
	}

	private static boolean check(int x, int y) {
		return x < 0 || x >= H || y < 0 || y >= W;
	}

	private static int resultCheck() {
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (map[i][j] == '*') {
					return -1;
				}
			}
		}
		return res;
	}
}