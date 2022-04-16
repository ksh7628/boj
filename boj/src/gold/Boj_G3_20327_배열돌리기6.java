package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 16.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드3
 * 소요 시간: 1h 30m 이상
 * 혼자 품: O
 * 풀이: 3, 4, 6, 7번 연산은 배열을 copy해서 구현, 나머지 연산은 swap해서 구현함.
 * 느낀 점: 점화식을 너무 어렵게 짜서 풀었는데 다른 사람의 코드가 더 간결한 것으로 봐서 쉽게 생각하는 사고력을 길러야겠다.
 */
public class Boj_G3_20327_배열돌리기6 {
	static int[][] map, tmap;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = 1 << Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		tmap = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int k = Integer.parseInt(st.nextToken());
			int p = 1 << Integer.parseInt(st.nextToken());
			process(k, p);
		}

		printMap();
		br.close();
	}

	/* p * p의 부분 배열로 나누어서 k번 연산을 수행 */
	private static void process(int k, int p) {
		switch (k) {
		case 1:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N; j += p) {
					cal1(i, j, p);
				}
			}
			break;
		case 2:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N; j += p) {
					cal2(i, j, p);
				}
			}
			break;
		case 3:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N; j += p) {
					cal3(i, j, p);
				}
			}
			break;
		case 4:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N; j += p) {
					cal4(i, j, p);
				}
			}
			break;
		case 5:
			for (int i = 0; i < N / 2; i += p) {
				for (int j = 0; j < N; j += p) {
					cal5(i, j, p);
				}
			}
			break;
		case 6:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N / 2; j += p) {
					cal6(i, j, p);
				}
			}
			break;
		case 7:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N; j += p) {
					cal7(i, j, p);
				}
			}
			copyMap();
			break;
		case 8:
			for (int i = 0; i < N; i += p) {
				for (int j = 0; j < N; j += p) {
					cal8(i, j, p);
				}
			}
			copyMap();
			break;
		}
	}

	/* 1번 연산: 각 부분 배열을 상하 반전 */
	private static void cal1(int x, int y, int p) {
		for (int i = x; i < x + p / 2; i++) {
			for (int j = y; j < y + p; j++) {
				int tmp = map[i][j];
				map[i][j] = map[x + p - 1 + x - i][j];
				map[x + p - 1 + x - i][j] = tmp;
			}
		}
	}

	/* 2번 연산: 각 부분 배열을 좌우 반전 */
	private static void cal2(int x, int y, int p) {
		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p / 2; j++) {
				int tmp = map[i][j];
				map[i][j] = map[i][y + p - 1 + y - j];
				map[i][y + p - 1 + y - j] = tmp;
			}
		}
	}

	/* 3번 연산: 각 부분 배열을 오른쪽으로 90도 회전 */
	private static void cal3(int x, int y, int p) {
		int[][] rmap = new int[p][p];

		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				rmap[j - y][x + p - 1 - i] = map[i][j];
			}
		}

		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				map[i][j] = rmap[i - x][j - y];
			}
		}
	}

	/* 4번 연산: 각 부분 배열을 왼쪽으로 90도 회전 */
	private static void cal4(int x, int y, int p) {
		int[][] rmap = new int[p][p];

		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				rmap[y + p - 1 - j][i - x] = map[i][j];
			}
		}

		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				map[i][j] = rmap[i - x][j - y];
			}
		}
	}

	/* 5번 연산: 부분 배열을 한 칸으로 생각하여 상하 반전시키는 연산 */
	private static void cal5(int x, int y, int p) {
		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				int tmp = map[i][j];
				map[i][j] = map[N - p - x - x + i][j];
				map[N - p - x - x + i][j] = tmp;
			}
		}
	}

	/* 6번 연산: 부분 배열을 한 칸으로 생각하여 좌우 반전시키는 연산 */
	private static void cal6(int x, int y, int p) {
		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				int tmp = map[i][j];
				map[i][j] = map[i][N - p - y - y + j];
				map[i][N - p - y - y + j] = tmp;
			}
		}
	}

	/* 7번 연산: 부분 배열을 한 칸으로 생각하여 오른쪽으로 90도 회전시키는 연산 */
	private static void cal7(int x, int y, int p) {
		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				tmap[j / p * p + i % p][N - p - i / p * p + j % p] = map[i][j];
			}
		}
	}

	/* 8번 연산: 부분 배열을 한 칸으로 생각하여 왼쪽으로 90도 회전시키는 연산 */
	private static void cal8(int x, int y, int p) {
		for (int i = x; i < x + p; i++) {
			for (int j = y; j < y + p; j++) {
				tmap[N - p - j / p * p + i % p][i / p * p + j % p] = map[i][j];
			}
		}
	}

	/* tmap -> map 복사 */
	private static void copyMap() {
		for (int i = 0; i < N; i++) {
			System.arraycopy(tmap[i], 0, map[i], 0, N);
		}
	}

	/* 결과 출력 */
	private static void printMap() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sb.append(map[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.print(sb);
	}
}