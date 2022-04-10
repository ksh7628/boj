package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 10.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드4
 * 소요 시간: 0h 51m
 * 혼자 품: O
 * 풀이: di라는 배열에 각각 주사위가 상하, 좌우로 움직일 때의 인덱스를 저장하고 매번 움직임에 따라 갱신시켜준다.
 * 느낀 점: 1년전에 푼 문제여서 코드를 더 간결하게 짜려고 했는데 오히려 저번 로직인 단순 4중 swap 방식이 더 간단한것 같다.
 */
public class Boj_G4_14499_주사위굴리기 {
	static StringBuilder sb = new StringBuilder();
	static int[][] map;
	static int[] dice = new int[6];
	static int[][] di = { { 0, 2, 5, 3 }, { 0, 4, 5, 1 } };
	static int[] dx = { 0, 0, -1, 1 };// 동서북남
	static int[] dy = { 1, -1, 0, 0 };
	static int N, M, x, y, ti, bi, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			move(Integer.parseInt(st.nextToken()) - 1);
		}

		System.out.print(sb);
		br.close();
	}

	// 주사위 초기 상태 0:윗면, 2:오른쪽
	//   1
	// 3 0 2
	//   4
	//   5
	private static void move(int d) {
		if (!isCheck(x + dx[d], y + dy[d])) {
			return;
		}

		x += dx[d];
		y += dy[d];
		int tmp = 0;

		switch (d) {
		case 0:
			di[1][0] = ti = di[0][3];
			di[1][2] = bi = di[0][1];
			tmp = di[0][3];
			for (int i = 2; i >= 0; i--) {
				di[0][i + 1] = di[0][i];
			}
			di[0][0] = tmp;
			break;
		case 1:
			di[1][0] = ti = di[0][1];
			di[1][2] = bi = di[0][3];
			tmp = di[0][0];
			for (int i = 0; i < 3; i++) {
				di[0][i] = di[0][i + 1];
			}
			di[0][3] = tmp;
			break;
		case 2:
			di[0][0] = ti = di[1][1];
			di[0][2] = bi = di[1][3];
			tmp = di[1][0];
			for (int i = 0; i < 3; i++) {
				di[1][i] = di[1][i + 1];
			}
			di[1][3] = tmp;
			break;
		case 3:
			di[0][0] = ti = di[1][3];
			di[0][2] = bi = di[1][1];
			tmp = di[1][3];
			for (int i = 2; i >= 0; i--) {
				di[1][i + 1] = di[1][i];
			}
			di[1][0] = tmp;
			break;
		}

		if (map[x][y] == 0) {
			map[x][y] = dice[bi];
		} else {
			dice[bi] = map[x][y];
			map[x][y] = 0;
		}

		sb.append(dice[ti]).append("\n");
	}

	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}