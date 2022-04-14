package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 14.
 * 
 * 분류: 구현, 브루트포스 알고리즘, 시뮬레이션
 * 난이도: 골드4
 * 소요 시간: 0h 20m
 * 혼자 품: O
 * 풀이: cctv의 개수만큼 dfs를 수행하면서 감시할 수 있는 칸을 -1로 표시, 복사된 배열을 다음 인자로 넘겨주면서 모든 cctv를 사용했다면 최솟값을 갱신
 * 느낀 점: 몇 번 풀어봤던 문제라서 이전보다 빠르게 해결했다.
 */
public class Boj_G4_15683_감시 {
	static List<int[]> cctv = new ArrayList<>();
	static int[] dx = { -1, 0, 1, 0 };// 북동남서
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		int[][] map = new int[N][M];
		int emptySpot = 0;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (1 <= map[i][j] && map[i][j] <= 5) {
					cctv.add(new int[] { i, j });
				} else if (map[i][j] == 0) {// 빈 칸의 개수를 카운팅
					emptySpot++;
				}
			}
		}

		res = emptySpot;
		brute(map, 0, emptySpot);
		System.out.println(res);
	}

	/* 모든 경우의 수를 수행해서 최솟값을 갱신 */
	private static void brute(int[][] map, int idx, int cnt) {
		if (idx == cctv.size()) {
			res = Math.min(res, cnt);
			return;
		}

		int[] cur = cctv.get(idx);
		int num = map[cur[0]][cur[1]];

		switch (num) {
		case 1:
			for (int d = 0; d < 4; d++) {
				int[][] tmap = copyArray(map);
				int wc = getCount(tmap, cur, d);
				brute(tmap, idx + 1, cnt - wc);
			}
			break;
		case 2:
			for (int d = 0; d < 2; d++) {
				int[][] tmap = copyArray(map);
				int wc = getCount(tmap, cur, d) + getCount(tmap, cur, d + 2);
				brute(tmap, idx + 1, cnt - wc);
			}
			break;
		case 3:
			for (int d = 0; d < 4; d++) {
				int[][] tmap = copyArray(map);
				int wc = getCount(tmap, cur, d) + getCount(tmap, cur, (d + 1) % 4);
				brute(tmap, idx + 1, cnt - wc);
			}
			break;
		case 4:
			for (int d = 0; d < 4; d++) {
				int[][] tmap = copyArray(map);
				int wc = getCount(tmap, cur, d) + getCount(tmap, cur, (d + 1) % 4) + getCount(tmap, cur, (d + 2) % 4);
				brute(tmap, idx + 1, cnt - wc);
			}
			break;
		case 5:
			int[][] tmap = copyArray(map);
			int wc = 0;
			for (int d = 0; d < 4; d++) {
				wc += getCount(tmap, cur, d);
			}
			brute(tmap, idx + 1, cnt - wc);
			break;
		}
	}

	/* 감시할 수 있는 칸의 개수를 구한다 */
	private static int getCount(int[][] map, int[] p, int d) {
		int x = p[0] + dx[d];
		int y = p[1] + dy[d];
		int res = 0;

		while (isCheck(x, y) && map[x][y] != 6) {
			// 빈 칸이면 감시 가능하므로 감시중인 칸(-1)으로 표시하고 개수 증가
			if (map[x][y] == 0) {
				map[x][y]--;
				res++;
			}

			x += dx[d];
			y += dy[d];
		}

		return res;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}

	/* 원본 배열 복사 */
	private static int[][] copyArray(int[][] map) {
		int[][] tmap = new int[N][M];
		for (int i = 0; i < N; i++) {
			System.arraycopy(map[i], 0, tmap[i], 0, M);
		}
		return tmap;
	}
}