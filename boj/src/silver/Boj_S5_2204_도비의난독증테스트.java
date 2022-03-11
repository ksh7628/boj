package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 11.
 * 
 * 분류: 문자열, 정렬
 * 난이도: 실버5
 * 소요 시간: 0h 09m
 * 혼자 품: O
 * 풀이: 단어 배열을 정렬할 때 toLowerCase()를 사용해서 모두 소문자로 치환해서 정렬한다.
 * 느낀 점: 간단한 정렬 문제. 실제 코테에서도 람다식 생각해내야함.
 */
public class Boj_S5_2204_도비의난독증테스트 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		while (true) {
			int n = Integer.parseInt(br.readLine());
			if (n == 0) {
				break;
			}

			String[] word = new String[n];
			for (int i = 0; i < n; i++) {
				word[i] = br.readLine();
			}

			// 모두 소문자로 바꿔서 오름차순 정렬
			Arrays.sort(word, (String s1, String s2) -> {
				return s1.toLowerCase().compareTo(s2.toLowerCase());
			});

			sb.append(word[0]).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}