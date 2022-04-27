package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 27.
 * 
 * 분류: 수학, 다이나믹 프로그래밍
 * 난이도: 실버5
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: 피보나치 수열로 구성되는 사각형의 변 둘레를 구하면 되는 문제. N항까지의 정보를 슬라이딩 윈도우로 구한 후, (PN + P(N-1)) * 2의 값을 구하면 현재 N번째 사각형까지의 둘레가 됨.
 * 느낀 점: 피보나치 수열 응용 문제로 어렵지 않았다.
 */
public class Boj_S5_13301_타일장식물 {
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		System.out.println(solution());
		br.close();
	}

	private static long solution() {
		if (N == 1) {
			return 4;
		}
		if (N == 2) {
			return 6;
		}

		long a = 1, b = 1, c = a + b;
		for (int i = 3; i <= N; i++) {
			a = b;
			b = c;
			c = a + b;
		}

		return (b + c) * 2;
	}
}