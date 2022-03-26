package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 26.
 * 
 * 분류: 구현, 브루트포스 알고리즘
 * 난이도: 골드1
 * 소요 시간: 1h 25m
 * 혼자 품: O
 * 풀이: n개 중 3개의 재료를 뽑는 nP3의 순열 + 각 재료별 회전여부 4가지 + 각 재료별 격자 위치 4가지
 * 느낀 점: 집중력이 요구되는 문제였고 마지막 폭탄의 품질 출력을 잘못 이해해서 여러 번 문제를 읽고 풀게 되었다. -> 문제 잘 읽자..
 */
public class Boj_G1_15898_피아의아틀리에신비한대회의연금술사 {
	static class Material {
		int qual;
		char color;

		public Material(int qual, char color) {
			super();
			this.qual = qual;
			this.color = color;
		}
	}

	static Material[][][][] mat;
	static Material[][] map1, map2, map3;
	static int[] num;
	static boolean[] isUsed;
	static int n, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());

		mat = new Material[n][4][4][4];
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < 4; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 4; j++) {
					mat[k][0][i][j] = new Material(Integer.parseInt(st.nextToken()), 'W');
				}
			}

			for (int i = 0; i < 4; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < 4; j++) {
					mat[k][0][i][j].color = st.nextToken().charAt(0);
				}
			}
		}

		solution();
		System.out.println(res);
		br.close();
	}

	private static void solution() {
		setRotation();
		num = new int[3];
		isUsed = new boolean[n];
		perm(0);
	}

	/* 모든 재료들의 회전 상태를 전처리 */
	private static void setRotation() {
		for (int k = 0; k < n; k++) {
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						mat[k][d + 1][j][3 - i] = mat[k][d][i][j];
					}
				}
			}
		}
	}

	/*
	 * 1. nP3의 순열을 뽑는다
	 * 2. 재료 배치 경우의 수 4가지
	 * 3. 재료 회전 경우의 수 4가지
	 */
	private static void perm(int cnt) {
		if (cnt == 3) {
			pickFirst();
			return;
		}

		for (int i = 0; i < n; i++) {
			if (!isUsed[i]) {
				isUsed[i] = true;
				num[cnt] = i;
				perm(cnt + 1);
				isUsed[i] = false;
			}
		}
	}

	/* map 초기화 */
	private static void initMap() {
		map1 = new Material[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				map1[i][j] = new Material(0, 'W');
			}
		}
	}

	/* 순열에서 뽑은 재료 중 첫번째 재료를 넣는다 */
	private static void pickFirst() {
		for (int d = 0; d < 4; d++) {
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < 2; c++) {
					initMap();

					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (mat[num[0]][d][i][j].qual > 0) {
								map1[i + r][j + c].qual = mat[num[0]][d][i][j].qual;
							}
							map1[i + r][j + c].color = mat[num[0]][d][i][j].color;
						}
					}

					pickSecond();
				}
			}
		}
	}

	/* 순열에서 뽑은 재료 중 두번째 재료를 넣는다 */
	private static void pickSecond() {
		for (int d = 0; d < 4; d++) {
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < 2; c++) {
					map2 = new Material[5][5];
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 5; j++) {
							map2[i][j] = new Material(map1[i][j].qual, map1[i][j].color);
						}
					}

					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							map2[i + r][j + c].qual += mat[num[1]][d][i][j].qual;
							if (map2[i + r][j + c].qual < 0) {
								map2[i + r][j + c].qual = 0;
							} else if (map2[i + r][j + c].qual > 9) {
								map2[i + r][j + c].qual = 9;
							}

							if (mat[num[1]][d][i][j].color != 'W') {
								map2[i + r][j + c].color = mat[num[1]][d][i][j].color;
							}
						}
					}

					pickThird();
				}
			}
		}
	}

	/* 순열에서 뽑은 재료 중 세번째 재료를 넣는다 */
	private static void pickThird() {
		for (int d = 0; d < 4; d++) {
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < 2; c++) {
					map3 = new Material[5][5];
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 5; j++) {
							map3[i][j] = new Material(map2[i][j].qual, map2[i][j].color);
						}
					}

					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							map3[i + r][j + c].qual += mat[num[2]][d][i][j].qual;
							if (map3[i + r][j + c].qual < 0) {
								map3[i + r][j + c].qual = 0;
							} else if (map3[i + r][j + c].qual > 9) {
								map3[i + r][j + c].qual = 9;
							}

							if (mat[num[2]][d][i][j].color != 'W') {
								map3[i + r][j + c].color = mat[num[2]][d][i][j].color;
							}
						}
					}

					res = Math.max(res, getSum());
				}
			}
		}
	}

	/* 폭탄의 품질 계산 */
	private static int getSum() {
		int R = 0, B = 0, G = 0, Y = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				switch (map3[i][j].color) {
				case 'R':
					R += map3[i][j].qual;
					break;
				case 'B':
					B += map3[i][j].qual;
					break;
				case 'G':
					G += map3[i][j].qual;
					break;
				case 'Y':
					Y += map3[i][j].qual;
					break;
				}
			}
		}

		return 7 * R + 5 * B + 3 * G + 2 * Y;
	}
}