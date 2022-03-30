package platinum;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 30.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 플래티넘5
 * 소요 시간: 1h 30m 이상
 * 혼자 품: O
 * 풀이: 
 * 1. B(0), U(1), L(2), F(3), R(4), D(5)로 인덱스를 가지는 3차원 배열을 정의한다.
 * 2. 큐브를 돌리는 면과 방향이 주어지면 다음과 같이 해결.
 * 2-1. U, F, R인 경우 정방향 그대로 수행한다(+: 시계방향, -: 반시계방향)
 * 2-2. 2-1이 아닌 경우 역방향으로 수행(+: 반시계방향, -: 시계방향)
 * 2-3. 주어진 면을 회전시킨 후 인접하는 4개의 면, 그 중에서도 인접한 1개의 행 또는 열을 tmp배열을 사용해서 4중 swap한다.
 * 느낀 점: 현장에서 해당 문제를 만났다면 디버깅하느라 시간내에 못 풀었을수도 있다고 느낀 문제였다. 꼼꼼하게 코딩하자.
 */
public class Boj_P5_5373_큐빙 {
	static char[][] tmp = new char[3][3];
	static char[] part = new char[3];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			// 뒤, 위, 왼, 앞, 오, 밑
			char[][][] cube = { { { 'o', 'o', 'o' }, { 'o', 'o', 'o' }, { 'o', 'o', 'o' } }, { { 'w', 'w', 'w' }, { 'w', 'w', 'w' }, { 'w', 'w', 'w' } },
					{ { 'g', 'g', 'g' }, { 'g', 'g', 'g' }, { 'g', 'g', 'g' } }, { { 'r', 'r', 'r' }, { 'r', 'r', 'r' }, { 'r', 'r', 'r' } },
					{ { 'b', 'b', 'b' }, { 'b', 'b', 'b' }, { 'b', 'b', 'b' } }, { { 'y', 'y', 'y' }, { 'y', 'y', 'y' }, { 'y', 'y', 'y' } } };
			int n = Integer.parseInt(br.readLine());

			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < n; i++) {
				process(cube, st.nextToken());
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					sb.append(cube[1][i][j]);
				}
				sb.append("\n");
			}
		}

		System.out.print(sb);
		br.close();
	}

	/* 해당 조건에 맞게 큐브를 돌린다 */
	private static void process(char[][][] cube, String s) {
		char pos = s.charAt(0), dir = s.charAt(1);
		int idx = 0;

		if (pos == 'B') {
			if (dir == '+') {
				copyCube(cube, idx, false);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[1][0][2 - j];
					cube[1][0][2 - j] = cube[4][2 - j][2];
					cube[4][2 - j][2] = cube[5][0][j];
					cube[5][0][j] = cube[2][j][2];
					cube[2][j][2] = part[j];
				}

			} else {
				copyCube(cube, idx, true);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[1][0][2 - j];
					cube[1][0][2 - j] = cube[2][j][2];
					cube[2][j][2] = cube[5][0][j];
					cube[5][0][j] = cube[4][2 - j][2];
					cube[4][2 - j][2] = part[j];
				}
			}

		} else if (pos == 'U') {
			idx = 1;

			if (dir == '+') {
				copyCube(cube, idx, true);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[0][0][j];
					cube[0][0][j] = cube[2][0][j];
					cube[2][0][j] = cube[3][0][2 - j];
					cube[3][0][2 - j] = cube[4][0][2 - j];
					cube[4][0][2 - j] = part[j];
				}

			} else {
				copyCube(cube, idx, false);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[0][0][j];
					cube[0][0][j] = cube[4][0][2 - j];
					cube[4][0][2 - j] = cube[3][0][2 - j];
					cube[3][0][2 - j] = cube[2][0][j];
					cube[2][0][j] = part[j];
				}
			}

		} else if (pos == 'L') {
			idx = 2;

			if (dir == '+') {
				copyCube(cube, idx, false);
				for (int i = 0; i < 3; i++) {
					part[i] = cube[1][2 - i][0];
					cube[1][2 - i][0] = cube[0][i][0];
					cube[0][i][0] = cube[5][i][0];
					cube[5][i][0] = cube[3][2 - i][0];
					cube[3][2 - i][0] = part[i];
				}

			} else {
				copyCube(cube, idx, true);
				for (int i = 0; i < 3; i++) {
					part[i] = cube[1][2 - i][0];
					cube[1][2 - i][0] = cube[3][2 - i][0];
					cube[3][2 - i][0] = cube[5][i][0];
					cube[5][i][0] = cube[0][i][0];
					cube[0][i][0] = part[i];
				}
			}

		} else if (pos == 'F') {
			idx = 3;

			if (dir == '+') {
				copyCube(cube, idx, true);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[1][2][j];
					cube[1][2][j] = cube[2][2 - j][0];
					cube[2][2 - j][0] = cube[5][2][2 - j];
					cube[5][2][2 - j] = cube[4][j][0];
					cube[4][j][0] = part[j];
				}

			} else {
				copyCube(cube, idx, false);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[1][2][j];
					cube[1][2][j] = cube[4][j][0];
					cube[4][j][0] = cube[5][2][2 - j];
					cube[5][2][2 - j] = cube[2][2 - j][0];
					cube[2][2 - j][0] = part[j];
				}
			}

		} else if (pos == 'R') {
			idx = 4;

			if (dir == '+') {
				copyCube(cube, idx, true);
				for (int i = 0; i < 3; i++) {
					part[i] = cube[1][i][2];
					cube[1][i][2] = cube[3][i][2];
					cube[3][i][2] = cube[5][2 - i][2];
					cube[5][2 - i][2] = cube[0][2 - i][2];
					cube[0][2 - i][2] = part[i];
				}

			} else {
				copyCube(cube, idx, false);
				for (int i = 0; i < 3; i++) {
					part[i] = cube[1][i][2];
					cube[1][i][2] = cube[0][2 - i][2];
					cube[0][2 - i][2] = cube[5][2 - i][2];
					cube[5][2 - i][2] = cube[3][i][2];
					cube[3][i][2] = part[i];
				}
			}

		} else {
			idx = 5;

			if (dir == '+') {
				copyCube(cube, idx, false);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[0][2][2 - j];
					cube[0][2][2 - j] = cube[4][2][j];
					cube[4][2][j] = cube[3][2][j];
					cube[3][2][j] = cube[2][2][2 - j];
					cube[2][2][2 - j] = part[j];
				}

			} else {
				copyCube(cube, idx, true);
				for (int j = 0; j < 3; j++) {
					part[j] = cube[0][2][2 - j];
					cube[0][2][2 - j] = cube[2][2][2 - j];
					cube[2][2][2 - j] = cube[3][2][j];
					cube[3][2][j] = cube[4][2][j];
					cube[4][2][j] = part[j];
				}
			}
		}
	}

	/* 해당 면을 90도 회전한다 / state(true): 시계 / state(false): 반시계 */
	private static void copyCube(char[][][] cube, int idx, boolean state) {
		if (state) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					tmp[j][2 - i] = cube[idx][i][j];
				}
			}
		} else {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					tmp[2 - j][i] = cube[idx][i][j];
				}
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cube[idx][i][j] = tmp[i][j];
			}
		}
	}
}