package bronze;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 6. 1.
 * 
 * 분류: 수학, 다이나믹 프로그래밍
 * 난이도: 브론즈1
 * 소요 시간: 0h 08m
 * 혼자 품: O
 * 풀이: 문제에서 코드1의 수행시간 = 피보나치 n번째 항, 코드2의 수행시간 = n - 2로 계산해서 출력.
 * 느낀 점: 예제로 주어진 코드의 동작 원리를 알면 쉽게 풀 수 있는 문제.
 */
public class Boj_B1_24416_알고리즘수업피보나치수1 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		int a = 1, b = 2, c = 3;
		for (int i = 5; i <= n; i++) {
			a = b;
			b = c;
			c = a + b;
		}

		System.out.println(c + " " + (n - 2));
		br.close();
	}
}