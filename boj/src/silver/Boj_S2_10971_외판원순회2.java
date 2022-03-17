package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 17.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹, 외판원 순회 문제
 * 난이도: 실버2
 * 소요 시간: 0h 30m
 * 혼자 품: O
 * 풀이: 0 ~ N-1에서 각각 출발하여 모든 점을 경유하면서 비용을 더해가며 출발점으로 갈 수 있다면 해당 경로의 비용까지 합산해서 최솟값을 구한다.
 * 느낀 점: 
 * 간단한 브루트포스로 풀 수 있는건데 문제를 잘못 이해해서 조금 시간이 걸렸다.
 * N이 16인 경우에는 비트마스킹+DP를 사용해야 되는데 나중에 https://www.acmicpc.net/problem/2098를 풀어봐야겠다.
 */
public class Boj_S2_10971_외판원순회2 {
	static int[][] cost;
	static boolean[] fromUsed, toUsed;
	static int N, s, res = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		cost = new int[N][N];
		fromUsed = new boolean[N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				cost[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < N; i++) {
			fromUsed[i] = true;
			s = i;
			tsp(i, 0, 0);
		}

		System.out.println(res);
		br.close();
	}

	private static void tsp(int start, int cnt, int sum) {
		if (sum > res) {
			return;
		}

		if (cnt == N - 1) {
			if (cost[start][s] != 0) {
				res = Math.min(res, sum + cost[start][s]);
			}
			return;
		}

		for (int i = 0; i < N; i++) {
			if (fromUsed[i] || cost[start][i] == 0) {
				continue;
			}

			fromUsed[i] = true;
			tsp(i, cnt + 1, sum + cost[start][i]);
			fromUsed[i] = false;
		}
	}
}