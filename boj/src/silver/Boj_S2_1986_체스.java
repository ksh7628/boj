package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 2.
 * 
 * 분류: 구현
 * 난이도: 실버2
 * 소요 시간: 0h 39m
 * 혼자 품: O
 * 풀이: 모든 퀸과 나이트를 이동시킬 수 있는 칸들에 대해 모두 이동시켜 전체 칸 개수에서 말들의 총 개수와 각각 이동할 수 있는 칸수의 합을 뺀 값을 구한다.
 * 느낀 점: 지문이 직관적이라서 구현하기 어렵지 않았는데 모르고 나이트도 맵 끝까지 확장시켜서 시간을 더 쓴 것 같다. 문제를 잘 읽자.
 */
public class Boj_S2_1986_체스 {
	static int[][] map, queen, knight;
	static int[] qx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] qy = { 0, 1, 1, 1, 0, -1, -1, -1 };
	static int[] kx = { -2, -1, 1, 2, 2, 1, -1, -2 };
	static int[] ky = { 1, 2, 2, 1, -1, -2, -2, -1 };
	static int N, M, qn, kn, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		res = N * M;

		st = new StringTokenizer(br.readLine(), " ");
		qn = Integer.parseInt(st.nextToken());
		queen = new int[qn][2];

		for (int i = 0; i < qn; i++) {
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;

			queen[i][0] = r;
			queen[i][1] = c;
			map[r][c] = 1;
		}

		st = new StringTokenizer(br.readLine(), " ");
		kn = Integer.parseInt(st.nextToken());
		knight = new int[kn][2];

		for (int i = 0; i < kn; i++) {
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;

			knight[i][0] = r;
			knight[i][1] = c;
			map[r][c] = 1;
		}

		st = new StringTokenizer(br.readLine(), " ");
		int pn = Integer.parseInt(st.nextToken());

		for (int i = 0; i < pn; i++) {
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;

			map[r][c] = 1;
		}

		res -= qn + kn + pn;
		solution();
		System.out.println(res);
		br.close();
	}

	/*
	 * 빈칸: 0, 잡을 수 있는 경로: -1, 말이 있는 곳: 1(말 종류 상관없이 1로 표시)
	 * 퀸은 장애물이나 다른 말에 부딪힐 때까지 확장
	 * 나이트는 확장X, 잡을 수 있는 8칸만 확인
	 * 전체 맵 칸수에서 모든 말의 수와 각 말들이 잡을 수 있는 칸수를 빼면 안전한 칸의 개수
	 */
	private static void solution() {
		moveQueen();
		moveKnight();
	}

	/* 퀸이 잡을 수 있는 영역을 표시한다 */
	private static void moveQueen() {
		for (int i = 0; i < qn; i++) {
			for (int d = 0; d < 8; d++) {
				int nx = queen[i][0] + qx[d];
				int ny = queen[i][1] + qy[d];

				while (isCheck(nx, ny) && map[nx][ny] <= 0) {
					if (map[nx][ny] == 0) {
						map[nx][ny] = -1;
						res--;
					}

					nx += qx[d];
					ny += qy[d];
				}
			}
		}
	}

	/* 나이트가 잡을 수 있는 영역을 표시한다 */
	private static void moveKnight() {
		for (int i = 0; i < kn; i++) {
			for (int d = 0; d < 8; d++) {
				int nx = knight[i][0] + kx[d];
				int ny = knight[i][1] + ky[d];

				if (isCheck(nx, ny) && map[nx][ny] <= 0) {
					if (map[nx][ny] == 0) {
						map[nx][ny] = -1;
						res--;
					}
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}