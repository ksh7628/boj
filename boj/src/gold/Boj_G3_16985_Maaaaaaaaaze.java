package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 28.
 * 
 * 분류: 구현, 그래프 이론, 브루트포스 알고리즘, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * 1. 회전 시키는 경우의 수(4^5) 만큼 map을 각각 회전시킨다.
 * 2. 맵 배치 경우의 수(5!)마다 bfs를 수행해서 최단거리를 찾는다.
 * -> 총 경우의 수: O(4^5 * 5! * 5^3) = 1536만
 * 느낀 점: 
 * 131번 라인의 도착점을 잘못 설정해서 디버깅 하는데 많은 시간을 썼다.
 * 이런 류의 문제는 실수를 최대한 줄여야 코테에서 시간을 잘 분배할 수 있다!!
 */
public class Boj_G3_16985_Maaaaaaaaaze {
	static int[][][] map, tmap;
	static int[] num;
	static int[] dx = { -1, 1, 0, 0, 0, 0 };
	static int[] dy = { 0, 0, -1, 1, 0, 0 };
	static int[] dz = { 0, 0, 0, 0, -1, 1 };
	static boolean[] isUsed;
	static final int INF = Integer.MAX_VALUE;
	static int res = INF;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		map = new int[5][5][5];
		tmap = new int[5][5][5];
		num = new int[5];
		isUsed = new boolean[5];

		for (int k = 0; k < 5; k++) {
			for (int i = 0; i < 5; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 5; j++) {
					map[k][i][j] = Integer.parseInt(st.nextToken());
				}
			}
		}

		setOrder();
		System.out.println((res == INF) ? -1 : res);
		br.close();
	}

	/* 회전 시키는 경우의 수를 모두 구한다 */
	private static void setOrder() {
		for (int a = 0; a < 4; a++) {
			rotateMap(0, a);
			for (int b = 0; b < 4; b++) {
				rotateMap(1, b);
				for (int c = 0; c < 4; c++) {
					rotateMap(2, c);
					for (int d = 0; d < 4; d++) {
						rotateMap(3, d);
						for (int e = 0; e < 4; e++) {
							rotateMap(4, e);
							perm(0);
							
							// 5x5x5 맵에서 최단거리는 12인데 이미 12로 결정났다면 더 이상 탐색 할 필요 X
							if (res == 12) {
								return;
							}
						}
					}
				}
			}
		}
	}

	/* 해당하는 번호의 맵을 시계방향으로 회전시킨다 */
	private static void rotateMap(int num, int dir) {
		switch (dir) {
		case 0:// 회전 X
			for (int i = 0; i < 5; i++) {
				System.arraycopy(map[num][i], 0, tmap[num][i], 0, 5);
			}
			break;
		case 1:// 90도 회전
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					tmap[num][j][4 - i] = map[num][i][j];
				}
			}
			break;
		case 2:// 180도 회전
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					tmap[num][4 - i][4 - j] = map[num][i][j];
				}
			}
			break;
		case 3:// 270도 회전
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					tmap[num][4 - j][i] = map[num][i][j];
				}
			}
			break;
		}
	}

	/* 판을 쌓는 순서를 결정 -> 순열 */
	private static void perm(int cnt) {
		if (cnt == 5) {
			res = Math.min(res, bfs());
			return;
		}

		for (int i = 0; i < 5; i++) {
			if (!isUsed[i]) {
				isUsed[i] = true;
				num[cnt] = i;
				perm(cnt + 1);
				isUsed[i] = false;
			}
		}
	}

	/* bfs로 최단거리를 구한다 */
	private static int bfs() {
		boolean[][][] visit = new boolean[5][5][5];
		ArrayDeque<int[]> q = new ArrayDeque<>();

		// 출발점 또는 도착점이 이동할 수 없는 칸이라면 탐색 X
		if (tmap[num[0]][0][0] == 0 || tmap[num[4]][4][4] == 0) {
			return INF;
		}

		visit[num[0]][0][0] = true;
		q.offer(new int[] { 0, 0, 0 });
		int dist = 0;

		while (!q.isEmpty()) {
			int size = q.size();

			if (dist == res) {
				return INF;
			}

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();

				// 도착
				if (cur[0] == 4 && cur[1] == 4 && cur[2] == 4) {
					return dist;
				}

				for (int d = 0; d < 6; d++) {
					int nz = cur[0] + dz[d];
					int nx = cur[1] + dx[d];
					int ny = cur[2] + dy[d];

					if (isCheck(nz, nx, ny) && !visit[num[nz]][nx][ny] && tmap[num[nz]][nx][ny] == 1) {
						visit[num[nz]][nx][ny] = true;
						q.offer(new int[] { nz, nx, ny });
					}
				}
			}

			dist++;
		}

		return INF;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int z, int x, int y) {
		return 0 <= z && z < 5 && 0 <= x && x < 5 && 0 <= y && y < 5;
	}
}