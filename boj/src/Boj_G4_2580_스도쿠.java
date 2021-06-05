

import java.io.*;
import java.util.*;

/**
 * <pre>
 * backtracking 
 * Boj_G4_2580_스도쿠.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 18.
 * @version	: 0.1
 *
 * 분류: 백트래킹
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 리스트에 아직 숫자를 채우지 않은 좌표들을 저장하고 dfs를 이용하여 가로 영역, 세로 영역, 3x3 영역에
 *      사용하지 않은 숫자들을 채워가면서 중복이 있을 경우에는 다시 0으로 되돌리는 식으로 백트래킹을 이용하여 풀었다.
 *      주의해야 되는 점은 답이 여러개라면 사전식으로 가장 앞서는 것을 출력해야 되므로 제일 처음 답이 나온것을 출력하고
 *      프로그램을 종료하게 했다.
 * 느낀 점: 일단 풀기는 풀었는데 해당 문제의 채점 현황을 보니 성능이 더 좋은 코드들이 많아서 이 방법이 좋은 방법은 아닌 것 같고
 *        다른 방식으로 접근해서 다시 풀어봐야 될 것 같다.
 */
public class Boj_G4_2580_스도쿠 {
	static List<int[]> al = new ArrayList<>();// 빈칸 좌표를 저장하는 리스트
	static int[][] board = new int[9][9];// 9x9 스도쿠 보드

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 9; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 9; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if (board[i][j] == 0) {// 빈칸이라면 해당 좌표를 리스트에 저장
					al.add(new int[] { i, j });
				}
			}
		}
		br.close();
		dfs(0);
	}

	/* dfs방식으로 모든 빈칸 탐색 */
	private static void dfs(int idx) {
		if (idx == al.size()) {// 모든 빈칸을 채웠다면 출력해준 후 시스템 종료
			printAnswer();
			System.exit(0);
		}

		int x = al.get(idx)[0];
		int y = al.get(idx)[1];

		boolean[] usedRow = new boolean[10];
		boolean[] usedCol = new boolean[10];
		boolean[] usedSquare = new boolean[10];

		drawRow(usedRow, x);// 가로 탐색
		drawCol(usedCol, y);// 세로 탐색
		drawSquare(usedSquare, x, y);// 3x3 영역 탐색

		for (int i = 1; i <= 9; i++) {
			if (usedRow[i] || usedCol[i] || usedSquare[i]) {// i숫자가 사용되었다면 continue
				continue;
			}
			board[x][y] = i;// 해당 숫자로 마킹
			dfs(idx + 1);// 다음 빈칸을 채우러 감
			board[x][y] = 0;// 다시 0으로 되돌려줌
		}
	}

	/* 가로 탐색 */
	private static void drawRow(boolean[] used, int row) {
		for (int j = 0; j < 9; j++) {
			if (board[row][j] > 0) {// 빈칸이 아니라면 숫자를 사용했다고 true로 저장
				used[board[row][j]] = true;
			}
		}
	}

	/* 세로 탐색 */
	private static void drawCol(boolean[] used, int col) {
		for (int i = 0; i < 9; i++) {
			if (board[i][col] > 0) {// 빈칸이 아니라면 숫자를 사용했다고 true로 저장
				used[board[i][col]] = true;
			}
		}
	}

	/* 3x3  탐색 */
	private static void drawSquare(boolean[] used, int x, int y) {
		int sx = (x / 3) * 3, sy = (y / 3) * 3;
		for (int i = sx; i < sx + 3; i++) {
			for (int j = sy; j < sy + 3; j++) {
				if (board[i][j] > 0) {// 빈칸이 아니라면 숫자를 사용했다고 true로 저장
					used[board[i][j]] = true;
				}
			}
		}
	}
	
	/* 정답 출력 */
	private static void printAnswer() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
}