package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 26.
 * 
 * 분류: 수학, 정수론
 * 난이도: 실버4
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: 2와 5의 거듭제곱으로 나눠보면서 개수를 누적시킨 후 둘 중 작은 개수가 0의 개수가 된다.
 * 느낀 점: boj.kr/1676과 동일한 문제. 2년 전 푼 기억이 있어서 쉽게 풀게 되었다.
 */
public class Boj_S4_3474_교수가된현우 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			int cnt2 = 0, cnt5 = 0;
			
			// 2의 개수를 구한다
			for (int i = 2; i <= N; i *= 2) {
				cnt2 += N / i;
			}
			// 5의 개수를 구한다
			for (int i = 5; i <= N; i *= 5) {
				cnt5 += N / i;
			}
			// 0의 개수: 2*5의 개수이므로 둘 중 작은 개수가 0의 개수가 됨
			sb.append(Math.min(cnt2, cnt5)).append("\n");
		}
		
		System.out.print(sb);
		br.close();
	}
}