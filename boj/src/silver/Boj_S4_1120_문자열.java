package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 3.
 * 
 * 분류: 구현, 문자열, 브루트포스 알고리즘
 * 난이도: 실버4
 * 소요 시간: 0h 08m
 * 혼자 품: O
 * 풀이: B문자열에 접근하는 인덱스를 하나씩 증가시키면서 A문자열과 비교하여 서로 다른 문자의 개수를 구해서 최솟값을 갱신시킨다.
 * 느낀 점: 처음에 잠깐 잘못 생각했다가 위의 방법으로 잘 접근하게 되었다.
 */
public class Boj_S4_1120_문자열 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		String A = st.nextToken();
		String B = st.nextToken();

		int min = 50;
		for (int i = 0; i < B.length(); i++) {
			if (A.length() + i > B.length() || min == 0) {
				break;
			}

			int sum = 0;
			for (int j = 0; j < A.length(); j++) {
				if (A.charAt(j) != B.charAt(i + j)) {
					sum++;
				}
			}

			min = Math.min(min, sum);
		}

		System.out.println(min);
		br.close();
	}
}