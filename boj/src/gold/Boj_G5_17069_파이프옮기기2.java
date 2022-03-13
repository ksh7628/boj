package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 13.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 골드5
 * 소요 시간: 0h 26m
 * 혼자 품: O
 * 풀이: [파이프방향][행][열] 을 정보로 가지는 3차원 dp배열을 선언해서 top-down 방식으로 품.
 * 느낀 점: 얼마 전에 비슷한 문제를 풀어서 오래 걸리지는 않은 것 같다. dp는 꾸준히 풀어야 된다고 느낌.
 */
public class Boj_G5_17069_파이프옮기기2 {
	static int[][] map;
	static long[][][] dp;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		dp = new long[3][N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < N; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}

		System.out.println(solution(0, 1, 0));
		br.close();
	}

	private static long solution(int x, int y, int s) {
		if (dp[s][x][y] != -1) {
			return dp[s][x][y];
		}

		if (x == N - 1 && y == N - 1) {
			return 1;
		}

		dp[s][x][y] = 0;

		if (s == 0) {// 가로
			if (y + 1 < N && map[x][y + 1] == 0) {
				dp[s][x][y] += solution(x, y + 1, 0);
			}

			if (x + 1 < N && y + 1 < N && map[x + 1][y] == 0 && map[x][y + 1] == 0 && map[x + 1][y + 1] == 0) {
				dp[s][x][y] += solution(x + 1, y + 1, 2);
			}

		} else if (s == 1) {// 세로
			if (x + 1 < N && map[x + 1][y] == 0) {
				dp[s][x][y] += solution(x + 1, y, 1);
			}

			if (x + 1 < N && y + 1 < N && map[x + 1][y] == 0 && map[x][y + 1] == 0 && map[x + 1][y + 1] == 0) {
				dp[s][x][y] += solution(x + 1, y + 1, 2);
			}

		} else {// 대각
			if (y + 1 < N && map[x][y + 1] == 0) {
				dp[s][x][y] += solution(x, y + 1, 0);
			}

			if (x + 1 < N && map[x + 1][y] == 0) {
				dp[s][x][y] += solution(x + 1, y, 1);
			}

			if (x + 1 < N && y + 1 < N && map[x + 1][y] == 0 && map[x][y + 1] == 0 && map[x + 1][y + 1] == 0) {
				dp[s][x][y] += solution(x + 1, y + 1, 2);
			}
		}

		return dp[s][x][y];
	}
}