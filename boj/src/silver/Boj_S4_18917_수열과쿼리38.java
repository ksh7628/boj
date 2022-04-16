package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 16.
 * 
 * 분류: 수학, 구현
 * 난이도: 실버4
 * 소요 시간: 0h 14m
 * 혼자 품: O
 * 풀이: 1번 쿼리의 경우 현재 합에 x를 더함, 2번 쿼리의 경우 현재 합에 x를 뺌, 1, 2번 쿼리 모두 현재까지 xor 연산한 수에 x를 xor연산한다.
 * 느낀 점: 같은 수를 xor 연산 2번 하면 원래 수로 돌아온다는 것을 알게 되었다.
 */
public class Boj_S4_18917_수열과쿼리38 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int M = Integer.parseInt(br.readLine());

		long sum = 0, xor = 0;
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int query = Integer.parseInt(st.nextToken());

			if (query <= 2) {
				int x = Integer.parseInt(st.nextToken());
				xor ^= x;

				if (query == 1) {
					sum += x;
				} else {
					sum -= x;
				}

			} else if (query == 3) {
				sb.append(sum).append("\n");
			} else {
				sb.append(xor).append("\n");
			}
		}

		System.out.print(sb);
		br.close();
	}
}