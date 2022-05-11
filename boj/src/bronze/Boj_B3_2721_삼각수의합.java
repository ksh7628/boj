package bronze;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 11.
 * 
 * 분류: 수학, 구현
 * 난이도: 브론즈3
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: T(n)과 W(n)을 미리 최대 범위까지 전처리한 후, 테스트케이스 마다 바로 구해서 출력.
 * 느낀 점: 점화식을 이해하는데 의외로 시간이 오래 걸렸다. 이해하는 것도 좋지만 보이는 그대로 받아드리는 것이 시험에서 더 중요한 것 같다.
 */
public class Boj_B3_2721_삼각수의합 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int[] T = new int[302];
		int[] W = new int[301];
		T[1] = 1;
		W[1] = 3;

		for (int i = 2; i <= 301; i++) {
			T[i] = T[i - 1] + i;
		}
		for (int i = 2; i <= 300; i++) {
			W[i] = W[i - 1] + i * T[i + 1];
		}

		int tc = Integer.parseInt(br.readLine());
		while (tc-- > 0) {
			sb.append(W[Integer.parseInt(br.readLine())]).append("\n");
		}
		
		System.out.print(sb);
		br.close();
	}
}