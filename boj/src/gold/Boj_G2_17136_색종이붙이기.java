package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 19.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 골드2
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: X
 * 풀이: 
 * 1. 10x10 배열의 좌표를 하나하나 확인해가며 dfs를 수행한다.
 * 2. 4개의 기저조건 고려
 * 2-1. 이전에 모두 색종이를 붙인 횟수보다 현재 붙인 횟수가 크거나 같다면 현재 탐색 종료
 * 2-2. (0,0)에서 (9,9)까지 탐색이 끝났다면 종료
 * 2-3. 열 좌표가 끝에 도달했다면 열 좌표 0으로 초기화, 행 좌표 1 증가 후 다시 dfs수행
 * 2-4. 현재 위치가 0이라면 y좌표 1 증가 후 다시 dfs 수행
 * 3. 반복문을 통해 1x1 ~ 5x5까지의 종이를 하나씩 붙일 수 있는지 체크하고 붙일 수 있다면 붙이고 다음 dfs를 수행한다.
 * 4. 현재 dfs가 도중에 가지치기로 잘리거나 끝까지 수행되었다면 붙인 색종이 떼고 사용한 색종이 횟수를 복구한다.
 * 느낀 점: 
 * 많은 기저조건들을 다 고려하지 못해서 시간 안에 풀지 못하고 다른 사람의 풀이를 참조하게 되었다.
 * 백트래킹 유형의 문제는 모든 기저조건을 고려하는 연습을 해야겠다고 느낀 문제였다.
 */
public class Boj_G2_17136_색종이붙이기 {
	static int[][] map = new int[10][10];
	static int[] paper = { 0, 5, 5, 5, 5, 5 };
	static int res = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < 10; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 10; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		dfs(0, 0, 0);
		if (res == Integer.MAX_VALUE) {
			res = -1;
		}

		System.out.println(res);
		br.close();
	}

	// 색종이 1x1 ~ 5x5 까지 각 5장 보유
	// 1은 모두 색종이로 덮어야 함
	// 딱 맞게 붙여야 함
	// 기저 조건 어떻게? -> 다 채워지면 최솟값 갱신?
	private static void dfs(int x, int y, int cnt) {
		if (cnt >= res) {
			return;
		}

		if (x == 9 && y == 10) {
			res = Math.min(res, cnt);
			return;
		}

		if (y == 10) {
			dfs(x + 1, 0, cnt);
			return;
		}

		if (map[x][y] == 0) {
			dfs(x, y + 1, cnt);
			return;
		}

		for (int i = 1; i <= 5; i++) {
			if (paper[i] > 0 && isAttach(x, y, i)) {
				paper[i]--;
				attachPaper(x, y, i, 0);
				dfs(x, y + 1, cnt + 1);
				attachPaper(x, y, i, 1);
				paper[i]++;
			}
		}
	}

	/* 색종이를 붙일 수 있는지 판단 */
	private static boolean isAttach(int x, int y, int s) {
		for (int i = x; i < x + s; i++) {
			for (int j = y; j < y + s; j++) {
				if (i >= 10 || j >= 10 || map[i][j] == 0)
					return false;
			}
		}

		return true;
	}

	/* 색종이 붙임 */
	private static void attachPaper(int x, int y, int size, int state) {
		for (int i = x; i < x + size; i++) {
			for (int j = y; j < y + size; j++) {
				map[i][j] = state;
			}
		}
	}
}