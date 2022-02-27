package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 27.
 * 
 * 분류: 다이나믹 프로그래밍
 * 난이도: 실버1
 * 소요 시간: 1h 30m 이상(time over)
 * 혼자 품: X
 * 풀이: 
 * 1. 입력 값을 저장하는 배열과 dp 배열을 모두 N+2의 크기로 선언한다. -> 1 based index와 N+1에 끝나는 상담까지 가능
 * 2. 매번 max값과 dp배열의 최댓값을 max에 갱신한다.
 * 3. 현재 진행하려는 상담이 마감일을 넘지 않으면 상담이 끝나는 날짜와 max에 현재 진행하는 상담의 금액을 더한 값을 비교해서 갱신한다.
 * 느낀 점: 
 * 배열 크기를 하나 더 크게 잡아야 된다는 것과 매번 max값을 최신화 시키는 것을 생각하지 못해서 풀지 못했다.
 * DP는 끊임없이 풀어야 실력이 오른다 하지만 아직 실버 상위 문제도 버거운 것 같다.
 */
public class Boj_S1_15486_퇴사2 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] day = new int[N + 2];
		int[] money = new int[N + 2];

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			day[i] = Integer.parseInt(st.nextToken());
			money[i] = Integer.parseInt(st.nextToken());
		}

		int[] dp = new int[N + 2];
		int max = 0;
		for (int i = 1; i <= N + 1; i++) {
			max = Math.max(max, dp[i]);

			// 현재 날짜에서 진행하는 상담이 끝나는 날짜
			int nxtDay = i + day[i];
			// 상담이 끝나는 날짜의 금액과 이전 최대 금액에서 현재 상담 금액을 비교하여 최댓값 갱신
			if (nxtDay <= N + 1) {
				dp[nxtDay] = Math.max(dp[nxtDay], max + money[i]);
			}
		}

		System.out.println(max);
		br.close();
	}
}