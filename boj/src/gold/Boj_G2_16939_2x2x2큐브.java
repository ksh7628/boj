package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: ksh76
 * @date	: 2022. 4. 1.
 * 
 * 분류: 구현
 * 난이도: 골드2
 * 소요 시간: 1h 00m
 * 혼자 품: O
 * 풀이: 하드코딩.. boj/kr/5373과 동일
 * 느낀 점: 굳이 하드코딩을 해야 한다면 메모를 해가면서 하자.
 */
public class Boj_G2_16939_2x2x2큐브 {
	static int[][][] cube = new int[6][2][2];
	static int[][][] rc = new int[6][2][2];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int k = 0; k < 6; k++) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					cube[k][i][j] = Integer.parseInt(st.nextToken());
				}
			}
		}

		System.out.println(solution());
		br.close();
	}

	// 6개의 면 * 시계방향 or 반시계 방향 -> 12가지
	// 인덱스 순서: 위 앞 밑 왼 오 뒤
	// 위 앞 오 -> 보이는 그대로 저장
	// 밑 왼 뒤 -> 뒤집어서 생각
	private static int solution() {
		// 위
		// 시계방향
		copyCube();
		rotate(0, true);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[1][0][i];
			rc[1][0][i] = rc[4][0][i];
			rc[4][0][i] = rc[5][0][i];
			rc[5][0][i] = rc[3][0][i];
			rc[3][0][i] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 반시계방향
		copyCube();
		rotate(0, false);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[1][0][i];
			rc[1][0][i] = rc[3][0][i];
			rc[3][0][i] = rc[5][0][i];
			rc[5][0][i] = rc[4][0][i];
			rc[4][0][i] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 앞
		// 시계방향
		copyCube();
		rotate(1, true);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][0][i];
			rc[2][0][i] = rc[4][1 - i][0];
			rc[4][1 - i][0] = rc[0][1][1 - i];
			rc[0][1][1 - i] = rc[3][i][1];
			rc[3][i][1] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 반시계방향
		copyCube();
		rotate(1, false);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][0][i];
			rc[2][0][i] = rc[3][i][1];
			rc[3][i][1] = rc[0][1][1 - i];
			rc[0][1][1 - i] = rc[4][1 - i][0];
			rc[4][1 - i][0] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 밑
		// 시계방향
		copyCube();
		rotate(2, true);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[5][1][i];
			rc[5][1][i] = rc[4][1][i];
			rc[4][1][i] = rc[1][1][i];
			rc[1][1][i] = rc[3][1][i];
			rc[3][1][i] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 반시계방향
		copyCube();
		rotate(2, false);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[5][1][i];
			rc[5][1][i] = rc[3][1][i];
			rc[3][1][i] = rc[1][1][i];
			rc[1][1][i] = rc[4][1][i];
			rc[4][1][i] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 왼
		// 시계방향
		copyCube();
		rotate(3, true);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][i][0];
			rc[2][i][0] = rc[1][i][0];
			rc[1][i][0] = rc[0][i][0];
			rc[0][i][0] = rc[5][1 - i][1];
			rc[5][1 - i][1] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 반시계방향
		copyCube();
		rotate(3, false);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][i][0];
			rc[2][i][0] = rc[5][1 - i][1];
			rc[5][1 - i][1] = rc[0][i][0];
			rc[0][i][0] = rc[1][i][0];
			rc[1][i][0] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 오
		// 시계방향
		copyCube();
		rotate(4, true);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][i][1];
			rc[2][i][1] = rc[5][1 - i][0];
			rc[5][1 - i][0] = rc[0][i][1];
			rc[0][i][1] = rc[1][i][1];
			rc[1][i][1] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 반시계방향
		copyCube();
		rotate(4, false);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][i][1];
			rc[2][i][1] = rc[1][i][1];
			rc[1][i][1] = rc[0][i][1];
			rc[0][i][1] = rc[5][1 - i][0];
			rc[5][1 - i][0] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 뒤
		// 시계방향
		copyCube();
		rotate(5, true);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][i][1];
			rc[2][i][1] = rc[3][i][0];
			rc[3][i][0] = rc[0][0][1 - i];
			rc[0][0][1 - i] = rc[4][1 - i][1];
			rc[4][1 - i][1] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		// 반시계방향
		copyCube();
		rotate(5, false);
		for (int i = 0; i < 2; i++) {
			int tmp = rc[2][i][1];
			rc[2][i][1] = rc[4][1 - i][1];
			rc[4][1 - i][1] = rc[0][0][1 - i];
			rc[0][0][1 - i] = rc[3][i][0];
			rc[3][i][0] = tmp;
		}
		if (isSolved()) {
			return 1;
		}

		return 0;
	}

	/* 해당 면을 회전 */
	private static void rotate(int k, boolean state) {
		if (state) {
			int tmp = rc[k][0][0];
			rc[k][0][0] = rc[k][1][0];
			rc[k][1][0] = rc[k][1][1];
			rc[k][1][1] = rc[k][0][1];
			rc[k][0][1] = tmp;
		} else {
			int tmp = rc[k][0][0];
			rc[k][0][0] = rc[k][0][1];
			rc[k][0][1] = rc[k][1][1];
			rc[k][1][1] = rc[k][1][0];
			rc[k][1][0] = tmp;
		}
	}

	/* 배열 복사 */
	private static void copyCube() {
		for (int k = 0; k < 6; k++) {
			for (int i = 0; i < 2; i++) {
				System.arraycopy(cube[k][i], 0, rc[k][i], 0, 2);
			}
		}
	}

	/* 큐브가 풀렸는지 확인 */
	private static boolean isSolved() {
		for (int k = 0; k < 6; k++) {
			int color = rc[k][0][0];
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if (color != rc[k][i][j]) {
						return false;
					}
				}
			}
		}
		return true;
	}
}