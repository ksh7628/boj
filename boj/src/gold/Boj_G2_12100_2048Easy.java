package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 9.
 * 
 * 분류: 구현, 브루트포스 알고리즘, 시뮬레이션, 백트래킹
 * 난이도: 골드2
 * 소요 시간: 1h 15m
 * 혼자 품: O
 * 풀이: 
 * 1. 5번까지 이동시킬 수 있고 방향의 순서에 따라 최댓값이 달라질 수 있기 때문에 중복순열을 이용해서 4^5의 모든 경우의 수를 검사한다.
 * 2. 각 방향마다 전체적인 로직은 같음, 블록이 합쳐지거나 이동이 가능할 경우에만 pre변수를 증감시키고 그렇지 않으면 다음으로 넘어간다.
 * 3. 합칠 수 있는 경우를 체크하기 위해 boolean형 1차원 배열을 통해 현재 위치가 합쳐졌는지 체크하고 안합쳐졌다면 현 위치를 체크하고 합친다.
 * 느낀 점: 
 * 다시 푼 문제인데 예전과 달라진 점은 순서를 굳이 배열에 저장하지 않고 즉시 배열을 이동시키는 식으로 구현했다.
 * 그리고 질문게시판의 다양한 테스트케이스가 아니었다면 디버깅 하는데 시간을 더 많이 썻을 것 같다.
 */
public class Boj_G2_12100_2048Easy {
	static int N, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int[][] map = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		brute(map, 0);
		System.out.println(res);
		br.close();
	}

	/* 4^5의 경우의 수를 구한다 -> 중복순열 */
	private static void brute(int[][] map, int cnt) {
		if (cnt == 5) {
			res = Math.max(res, getMax(map));
			return;
		}

		for (int i = 0; i < 4; i++) {
			int[][] tmap = copyArray(map);
			switch (i) {
			case 0:
				moveUp(tmap);
				break;
			case 1:
				moveDown(tmap);
				break;
			case 2:
				moveLeft(tmap);
				break;
			case 3:
				moveRight(tmap);
			}
			brute(tmap, cnt + 1);
		}
	}

	/* 위로 이동 */
	private static void moveUp(int[][] map) {
		for (int j = 0; j < N; j++) {
			boolean[] isCombined = new boolean[N];
			int pre = 0;

			for (int i = 1; i < N; i++) {
				// 이동할 블록이 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[pre][j] == 0) {
					map[pre][j] = map[i][j];
					map[i][j] = 0;
				} else {
					// 서로 숫자가 같고 아직 안합친 곳
					if (map[pre][j] == map[i][j] && !isCombined[pre]) {
						isCombined[pre] = true;
						map[pre++][j] *= 2;
						map[i][j] = 0;

						// 이동 가능
					} else if (pre < i - 1) {
						map[++pre][j] = map[i][j];
						map[i][j] = 0;

						// 이동 불가능
					} else {
						pre++;
					}
				}
			}
		}
	}

	/* 밑으로 이동 */
	private static void moveDown(int[][] map) {
		for (int j = 0; j < N; j++) {
			boolean[] isCombined = new boolean[N];
			int pre = N - 1;

			for (int i = N - 2; i >= 0; i--) {
				// 이동할 블록이 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[pre][j] == 0) {
					map[pre][j] = map[i][j];
					map[i][j] = 0;
				} else {
					// 서로 숫자가 같고 아직 안합친 곳
					if (map[pre][j] == map[i][j] && !isCombined[pre]) {
						isCombined[pre] = true;
						map[pre--][j] *= 2;
						map[i][j] = 0;

						// 이동 가능
					} else if (pre > i + 1) {
						map[--pre][j] = map[i][j];
						map[i][j] = 0;

						// 이동 불가능
					} else {
						pre--;
					}
				}
			}
		}
	}

	/* 왼쪽으로 이동 */
	private static void moveLeft(int[][] map) {
		for (int i = 0; i < N; i++) {
			boolean[] isCombined = new boolean[N];
			int pre = 0;

			for (int j = 1; j < N; j++) {
				// 이동할 블록이 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[i][pre] == 0) {
					map[i][pre] = map[i][j];
					map[i][j] = 0;
				} else {
					// 서로 숫자가 같고 아직 안합친 곳
					if (map[i][pre] == map[i][j] && !isCombined[pre]) {
						isCombined[pre] = true;
						map[i][pre++] *= 2;
						map[i][j] = 0;

						// 이동 가능
					} else if (pre < j - 1) {
						map[i][++pre] = map[i][j];
						map[i][j] = 0;

						// 이동 불가능
					} else {
						pre++;
					}
				}
			}
		}
	}

	/* 오른쪽으로 이동 */
	private static void moveRight(int[][] map) {
		for (int i = 0; i < N; i++) {
			boolean[] isCombined = new boolean[N];
			int pre = N - 1;

			for (int j = N - 2; j >= 0; j--) {
				// 이동할 블록이 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[i][pre] == 0) {
					map[i][pre] = map[i][j];
					map[i][j] = 0;
				} else {
					// 서로 숫자가 같고 아직 안합친 곳
					if (map[i][pre] == map[i][j] && !isCombined[pre]) {
						isCombined[pre] = true;
						map[i][pre--] *= 2;
						map[i][j] = 0;

						// 이동 가능
					} else if (pre > j + 1) {
						map[i][--pre] = map[i][j];
						map[i][j] = 0;

						// 이동 불가능
					} else {
						pre--;
					}
				}
			}
		}
	}

	/* 가장 큰 블록을 찾는다 */
	private static int getMax(int[][] map) {
		int max = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				max = Math.max(max, map[i][j]);
			}
		}
		return max;
	}

	/* 배열 복사 */
	private static int[][] copyArray(int[][] map) {
		int[][] tmap = new int[N][N];
		for (int i = 0; i < N; i++) {
			System.arraycopy(map[i], 0, tmap[i], 0, N);
		}
		return tmap;
	}
}