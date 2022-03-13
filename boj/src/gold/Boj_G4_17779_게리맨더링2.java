package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 13.
 * 
 * 분류: 구현, 브루트포스 알고리즘, 시뮬레이션
 * 난이도: 골드4
 * 소요 시간: 1h 30m 이상(time over)
 * 혼자 품: X
 * 풀이: 4중 for문을 이용하여 모든 경우의 수를 확인하면서 경계를 체크하고 각 선거구를 분배.
 * 느낀 점: 
 * 문제에 범위가 친절하게 나와있는데도 어렵게 생각해서 식을 잘못세우게 되었고 시간 안에 문제를 풀지 못했다.
 * 어려운 문제가 아닌데도 많이 헤매게 되었고 특히 근래 삼성A형은 어렵게 나오고 있기 때문에 이 정도 난이도는 1시간안에 쳐내야 한다는 것을 상기하게 됨.
 */
public class Boj_G4_17779_게리맨더링2 {
	static int[][] map;
	static boolean[][] isBound;
	static int[] cnt;
	static int N, total, res = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N + 1][N + 1];

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				total += map[i][j];
			}
		}

		solution();
		System.out.println(res);
		br.close();
	}

	/* 기준점과 경계 길이를 정한다 */
	private static void solution() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				for (int d1 = 1; d1 < N; d1++) {
					for (int d2 = 1; d2 < N; d2++) {
						// 기준점 조건 충족 검사
						if (i + d1 + d2 > N || j - d1 < 1 || j + d2 > N) {
							continue;
						}

						divideRegion(i, j, d1, d2);
					}
				}
			}
		}
	}

	/* 경계선을 만들고 나머지 선거구를 문제 조건에 맞게 나눔 */
	private static void divideRegion(int x, int y, int d1, int d2) {
		isBound = new boolean[N + 1][N + 1];
		int[] sum = new int[5];

		// 1, 4 조건 경계선
		for (int i = 0; i <= d1; i++) {
			isBound[x + i][y - i] = true;
			isBound[x + d2 + i][y + d2 - i] = true;
		}

		// 2, 3 조건 경계선
		for (int i = 0; i <= d2; i++) {
			isBound[x + i][y + i] = true;
			isBound[x + d1 + i][y - d1 + i] = true;
		}

		// 1번 선거구
		for (int i = 1; i < x + d1; i++) {
			for (int j = 1; j <= y; j++) {
				if (isBound[i][j]) {
					break;
				}

				sum[0] += map[i][j];
			}
		}

		// 2번 선거구
		for (int i = 1; i <= x + d2; i++) {
			for (int j = N; j > y; j--) {
				if (isBound[i][j]) {
					break;
				}

				sum[1] += map[i][j];
			}
		}

		// 3번 선거구
		for (int i = x + d1; i <= N; i++) {
			for (int j = 1; j < y - d1 + d2; j++) {
				if (isBound[i][j]) {
					break;
				}

				sum[2] += map[i][j];
			}
		}

		// 4번 선거구
		for (int i = x + d2 + 1; i <= N; i++) {
			for (int j = N; j >= y - d1 + d2; j--) {
				if (isBound[i][j]) {
					break;
				}

				sum[3] += map[i][j];
			}
		}

		// 5번 선거구 = 전체 - 1,2,3,4 선거구
		sum[4] = total;
		for (int i = 0; i < 4; i++) {
			sum[4] -= sum[i];
		}

		int max = 0, min = Integer.MAX_VALUE;
		for (int i = 0; i < 5; i++) {
			max = Math.max(max, sum[i]);
			min = Math.min(min, sum[i]);
		}

		res = Math.min(res, max - min);
	}
}