package silver;

import java.io.*;
import java.util.*;

/**
 * @author : KimSeonhong
 * @date : 2022. 2. 26.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 실버2
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 하향식 DP 방식과 메모이제이션을 사용한 풀이
 * 1. dp 배열 -1 초기화하고 0,0 부터 시작
 * 2. 도착했거나 방문한 경우가 아니고 맵 밖으로 나가는게 아니라면 다음 좌표를 인자로 갖는 함수 실행
 * 3. 재귀적으로 합 누적해서 모든 경우의 수를 찾는다.
 * 느낀 점: 
 * 하향식 DP인 Top-Down 방식을 아직 잘 이해하지 못한 것 같다.
 * 그리 어려운 문제가 아닌데도 불구하고 결국 다른 사람의 코드를 참조하게 되었다.
 * DP 문제는 꾸준한 연습이 필요할 것 같다고 느끼게 되었다.
 */
public class Boj_S2_1890_점프 {
	static int[][] map;
	static long[][] dp;
	static int N;
	static long res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		dp = new long[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = -1;
			}
		}

		System.out.println(topDown(0, 0));
		br.close();
	}

	// Top-Down DP
	private static long topDown(int x, int y) {
		// 도착
		if (x == N - 1 && y == N - 1) {
			return 1;
		}

		// 이미 방문한 좌표
		if (dp[x][y] != -1) {
			return dp[x][y];
		}
		
		// 메모이제이션
		dp[x][y] = 0;
		int nx = x + map[x][y], ny = y + map[x][y];
		
		if (nx < N) {
			dp[x][y] += topDown(nx, y);
		}
		
		if (ny < N) {
			dp[x][y] += topDown(x, ny);
		}

		return dp[x][y];
	}
}