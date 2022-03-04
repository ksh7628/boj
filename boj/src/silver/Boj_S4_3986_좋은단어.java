package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 4.
 * 
 * 분류: 자료 구조, 스택
 * 난이도: 실버4
 * 소요 시간: 0h 07m
 * 혼자 품: O
 * 풀이: 
 * 문자를 하나씩 확인하면서 스택 최상단에 같은 문자가 있다면 스택에서 제거, 아니라면 스택에 넣는다.
 * 또한 문자열 길이가 홀수라면 절대 짝지어지지 않음이 보장된다.
 * 느낀 점: 전형적인 스택 기초 응용문제라 쉽게 품.
 */
public class Boj_S4_3986_좋은단어 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int res = 0;

		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			if (str.length() % 2 == 1) {
				continue;
			}

			Stack<Character> s = new Stack<>();
			for (int j = 0; j < str.length(); j++) {
				char c = str.charAt(j);

				if (s.isEmpty()) {
					s.push(c);
					continue;
				}

				// 같은 짝이면 스택에서 뽑는다 -> 짝지어짐
				// 아니라면 스택에 넣는다
				if (c == s.peek()) {
					s.pop();
				} else {
					s.push(c);
				}
			}

			if (s.isEmpty()) {
				res++;
			}
		}
		
		System.out.println(res);
		br.close();
	}
}