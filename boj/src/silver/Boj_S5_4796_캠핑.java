package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 3.
 * 
 * 분류: 수학, 그리디 알고리즘
 * 난이도: 실버5
 * 소요 시간: 0h 07m
 * 혼자 품: O
 * 풀이: L < P < V 이므로 V / P의 몫 * L 값을 구한 후 나머지가 L보다 크거나 같다면 L, 아니라면 나머지를 더해준다.
 * 느낀 점: 발상이 어렵지 않은 쉬운 문제였다.
 */
public class Boj_S5_4796_캠핑 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int tc = 1;; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int L = Integer.parseInt(st.nextToken());
			int P = Integer.parseInt(st.nextToken());
			int V = Integer.parseInt(st.nextToken());
			if (L == 0) {
				break;
			}

			int res = V / P * L + (V % P >= L ? L : V % P);
			sb.append("Case ").append(tc).append(": ").append(res).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}