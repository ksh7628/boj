package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 30.
 * 
 * 분류: 그리디 알고리즘
 * 난이도: 실버2
 * 소요 시간: 0h 23m
 * 혼자 품: O
 * 풀이: 각 등급별 최고 금액을 더해가면서 만 원 차이로 현재 등급을 유지할 수 있게 누적시킨다.
 * 느낀 점: 풀 때는 몰랐는데 풀고 나서 그리디하게 접근했다는 것을 알게 됨.
 */
public class Boj_S2_20413_MVP다이아몬드Easy {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int s = Integer.parseInt(st.nextToken());
		int g = Integer.parseInt(st.nextToken());
		int p = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());

		String grade = br.readLine();
		int res = 0, cash = 0;
		char c = grade.charAt(0);

		if (c == 'B') {
			cash = s - 1;
		} else if (c == 'S') {
			cash = g - 1;
		} else if (c == 'G') {
			cash = p - 1;
		} else if (c == 'P') {
			cash = d - 1;
		} else {
			cash = d;
		}
		res = cash;

		for (int i = 1; i < N; i++) {
			c = grade.charAt(i);

			if (c == 'B') {
				cash = s - 1 - cash;
			} else if (c == 'S') {
				cash = g - 1 - cash;
			} else if (c == 'G') {
				cash = p - 1 - cash;
			} else if (c == 'P') {
				cash = d - 1 - cash;
			} else {
				cash = d;
			}
			res += cash;
		}

		System.out.println(res);
		br.close();
	}
}