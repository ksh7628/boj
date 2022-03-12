package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 12.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 골드5
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: coin[0]원으로 만들 수 있는 경우의 수를 구하고 coin[i]원으로 이전에 구한 값을 이용해서 다시 구한다.
 * 느낀 점: 동전 dp는 아직 구현을 어떻게 해야될지 좀 부족한것 같아서 나중에 다시 꼭 풀어봐야 겠다는 생각이 든다.
 */
public class Boj_G5_2293_동전1 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[] coin = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			coin[i] = Integer.parseInt(br.readLine());
		}

		int[] dp = new int[k + 1];
		dp[0] = 1;

		// dp[i] = j
		// -> i: 금액, j: i원을 만드는데 가능한 경우의 수
		for (int i = 1; i <= n; i++) {
			for (int j = coin[i]; j <= k; j++) {
				dp[j] += dp[j - coin[i]];
			}
		}

		System.out.println(dp[k]);
		br.close();
	}
}