package bronze;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 18.
 * 
 * 분류: 구현
 * 난이도: 브론즈4
 * 소요 시간: 0h 05m
 * 혼자 품: O
 * 풀이: 문제의 조건 그대로 p가 50 이하면서 q도 10 이하라면 하양, q가 30보다 크다면 빨강, 그 외에는 노랑이다.
 * 느낀 점: 조건을 그대로 구현하는 문제.
 */
public class Boj_B4_20673_Covid19 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int p = Integer.parseInt(br.readLine());
		int q = Integer.parseInt(br.readLine());

		if (p <= 50 && q <= 10) {
			System.out.println("White");
		} else if (q > 30) {
			System.out.println("Red");
		} else {
			System.out.println("Yellow");
		}
		br.close();
	}
}