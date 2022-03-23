package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 23.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드2
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * 1. 주어진 블록을 파란색 보드와 초록색 보드로 블록을 만나거나 보드 끝에 도달할 때까지 이동한다.
 * 2. 각 색깔마다 연한색 보드에 차지하는 공간 개수를 센다.
 * 3. 각 보드에서 제거 조건을 만족하는 행 또는 열이 있다면 블록을 제거한다.
 * 4. 제거 된 행이나 열이 있다면 그 위or왼쪽에 있는 블록들을 제거 된 수만큼 밀어준다.
 * 5. 연한색 보드에 차지하는 공간의 개수가 2개고 좌표가 겹친다면 개수를 하나 빼준다.
 * -> ex) 파란색 보드는 열단위로 블록을 제거하는데 똑같은 열에 블록이 2개있으면 블록 개수는 2개지만 연한파랑색 보드에 차지하는 열은 1개이므로 제거할 열은 1개이다.
 * 6. 블록들을 다시 끝까지 밀어준다.
 * 느낀 점: 
 * 시간을 초과해서 겨우 풀게 된 문제였다. 실제 시험장에서 이 문제가 나왔더라면 시간 안에 못풀었을것 같다.
 * 그리고 코드가 너무 길어져서 다음에 풀 때에는 코드 길이를 짧게 해서 풀어봐야 겠다.
 */
public class Boj_G2_20061_모노미노도미노2 {
	static boolean[][] map = new boolean[10][];
	static int N, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		for (int i = 0; i < 10; i++) {
			if (i < 4) {
				map[i] = new boolean[10];
			} else {
				map[i] = new boolean[4];
			}
		}

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			solution(t, x, y);
		}

		System.out.println(res);
		System.out.println(getCount());
		br.close();
	}

	private static void solution(int t, int x, int y) {
		int[][] block;

		if (t == 1) {
			block = new int[1][2];
			block[0][0] = x;
			block[0][1] = y;
		} else if (t == 2) {
			block = new int[2][2];
			block[0][0] = x;
			block[0][1] = y;
			block[1][0] = x;
			block[1][1] = y + 1;
		} else {
			block = new int[2][2];
			block[0][0] = x;
			block[0][1] = y;
			block[1][0] = x + 1;
			block[1][1] = y;
		}

		moveBluezone(block);
		moveGreenzone(block);
	}

	/*
	 * 파랑 블록 이동
	 * 열 블록 검사해서 터트린다
	 * 4, 5열에 블록 있으면 제일 오른쪽 열부터 지운다
	 */
	private static void moveBluezone(int[][] block) {
		int[][] tb = new int[block.length][2];
		for (int i = 0; i < block.length; i++) {
			System.arraycopy(block[i], 0, tb[i], 0, 2);
		}

		// 블록을 만나거나 보드 끝을 만날때까지 블록 이동
		while (true) {
			boolean isMove = true;

			for (int[] b : tb) {
				int ny = b[1] + 1;
				if (ny >= 10 || map[b[0]][ny]) {
					isMove = false;
					break;
				}
			}

			if (isMove) {
				for (int[] b : tb) {
					b[1]++;
				}
			} else {
				for (int[] b : tb) {
					map[b[0]][b[1]] = true;
				}
				break;
			}
		}

		// 연한 칸에 있는 블록이 차지하는 공간만큼 끝부분부터 처리
		int cnt = 0;
		for (int[] b : tb) {
			if (b[1] == 4 || b[1] == 5) {
				cnt++;
			}
		}

		// 제거 가능한 곳 제거
		int start = 10, desCnt = 0;
		for (int[] b : tb) {
			boolean isDestroy = true;
			for (int i = 0; i < 4; i++) {
				if (!map[i][b[1]]) {
					isDestroy = false;
					break;
				}
			}

			if (isDestroy) {
				desCnt++;
				for (int i = 0; i < 4; i++) {
					map[i][b[1]] = false;
				}

				start = b[1];
			}
		}

		if (desCnt > 0) {
			for (int i = 0; i < 4; i++) {
				for (int j = start - desCnt; j >= 4; j--) {
					map[i][j + desCnt] = map[i][j];
					map[i][j] = false;
				}
			}

			res += desCnt;
		}

		if (cnt == 2 && tb[0][1] == tb[1][1]) {
			cnt--;
		}

		cnt -= desCnt;
		if (cnt > 0) {
			for (int i = 0; i < cnt; i++) {
				for (int j = 0; j < 4; j++) {
					map[j][9 - i] = false;
				}
			}

			// 연한 칸 열 개수만큼 블록을 밀어줌
			for (int j = 9 - cnt; j >= 6 - cnt; j--) {
				for (int i = 0; i < 4; i++) {
					map[i][j + cnt] = map[i][j];
					map[i][j] = false;
				}
			}
		}
	}

	/*
	 * 초록 블록 이동
	 * 행 블록 검사해서 터트린다
	 * 4, 5행에 블록 있으면 제일 밑 행부터 지운다
	 */
	private static void moveGreenzone(int[][] block) {
		int[][] tb = new int[block.length][2];
		for (int i = 0; i < block.length; i++) {
			System.arraycopy(block[i], 0, tb[i], 0, 2);
		}

		// 블록을 만나거나 보드 끝을 만날때까지 블록 이동
		while (true) {
			boolean isMove = true;

			for (int[] b : tb) {
				int nx = b[0] + 1;
				if (nx >= 10 || map[nx][b[1]]) {
					isMove = false;
					break;
				}
			}

			if (isMove) {
				for (int[] b : tb) {
					b[0]++;
				}
			} else {
				for (int[] b : tb) {
					map[b[0]][b[1]] = true;
				}
				break;
			}
		}

		// 연한 칸에 있는 블록이 차지하는 공간만큼 끝부분부터 처리
		int cnt = 0;
		for (int[] b : tb) {
			if (b[0] == 4 || b[0] == 5) {
				cnt++;
			}
		}

		// 제거 가능한 곳 제거
		int start = 0, desCnt = 0;
		for (int[] b : tb) {
			boolean isDestroy = true;
			for (int j = 0; j < 4; j++) {
				if (!map[b[0]][j]) {
					isDestroy = false;
					break;
				}
			}

			if (isDestroy) {
				desCnt++;
				for (int j = 0; j < 4; j++) {
					map[b[0]][j] = false;
				}

				start = b[0];
			}
		}

		if (desCnt > 0) {
			for (int j = 0; j < 4; j++) {
				for (int i = start - desCnt; i >= 4; i--) {
					map[i + desCnt][j] = map[i][j];
					map[i][j] = false;
				}
			}

			res += desCnt;
		}

		if (cnt == 2 && tb[0][0] == tb[1][0]) {
			cnt--;
		}

		cnt -= desCnt;
		if (cnt > 0) {
			for (int i = 0; i < cnt; i++) {
				for (int j = 0; j < 4; j++) {
					map[9 - i][j] = false;
				}
			}

			// 연한 칸 행 개수만큼 블록을 밀어줌
			for (int i = 9 - cnt; i >= 6 - cnt; i--) {
				for (int j = 0; j < 4; j++) {
					map[i + cnt][j] = map[i][j];
					map[i][j] = false;
				}
			}
		}
	}

	/* 남은 블록 개수를 구함 */
	private static int getCount() {
		int cnt = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 6; j < 10; j++) {
				if (map[i][j]) {
					cnt++;
				}

				if (map[j][i]) {
					cnt++;
				}
			}
		}

		return cnt;
	}
}