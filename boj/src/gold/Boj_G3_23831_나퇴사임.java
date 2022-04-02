package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 2.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 골드3
 * 소요 시간: 1h 30m 이상
 * 혼자 품: O
 * 풀이: 4차원 dp배열을 사용하여 Top-Down DP로 풀이.
 * 느낀 점: 경우의 수를 구하는 DP는 어느 정도 풀었었는데 최댓값을 구하는 DP는 리턴값을 다르게 줘야 한다는 것을 알게 됨.
 */
public class Boj_G3_23831_나퇴사임 {
	static int[][][][] dp;
	static int[][] satisCnt;
	static int N, A, B, res;
	static final int NINF = Integer.MIN_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());

		dp = new int[4][N][A + 1][N];
		satisCnt = new int[N][4];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 4; j++) {
				satisCnt[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// dp 초기화
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k <= A; k++) {
					Arrays.fill(dp[i][j][k], -1);
				}
			}
		}

		System.out.println(topDown(0, 0, 0, 0));
		br.close();
	}

	/* dp[이용한 방][날짜][요양 횟수][정독실 또는 소학습실 이용 횟수]로 표현 */
	private static int topDown(int pre, int day, int a, int b) {
		// N일까지 자습 완료
		if (day == N) {
			// B 이상 수행완료
			if (b >= B) {
				return 0;
			}
			// B 미만이므로 불가능
			return NINF;
		}

		// 이미 메모이제이션 완료
		if (dp[pre][day][a][b] != -1) {
			return dp[pre][day][a][b];
		}

		// 충분히 큰 음수로 초기화한다
		// -> 최댓값이 다른 경로에서 갱신될 수가 있다
		dp[pre][day][a][b] = NINF;
		for (int i = 0; i < 2; i++) {
			dp[pre][day][a][b] = Math.max(dp[pre][day][a][b], topDown(i, day + 1, a, b + 1) + satisCnt[day][i]);
		}
		if (pre != 2) {
			dp[pre][day][a][b] = Math.max(dp[pre][day][a][b], topDown(2, day + 1, a, b) + satisCnt[day][2]);
		}
		if (a + 1 <= A) {
			dp[pre][day][a][b] = Math.max(dp[pre][day][a][b], topDown(3, day + 1, a + 1, b) + satisCnt[day][3]);
		}
		return dp[pre][day][a][b];
	}
}