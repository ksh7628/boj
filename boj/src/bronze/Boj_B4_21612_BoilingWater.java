package bronze;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 23.
 * 
 * 분류: 수학, 구현, 사칙연산
 * 난이도: 브론즈4
 * 소요 시간: 0h 04m
 * 혼자 품: O
 * 풀이: 첫번째 출력의 경우 지문에 주어진 5*B-400의 결과. 두번째 출력의 경우 100 초과라면 1, 100 이라면 0, 100 미만이라면 -1를 결과.
 * 느낀 점: 사칙연산 + 조건문을 활용한 문제였다.
 */
public class Boj_B4_21612_BoilingWater {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int B = Integer.parseInt(br.readLine());

		StringBuilder sb = new StringBuilder();
		sb.append(5 * B - 400).append("\n");
		sb.append(100 < B ? -1 : 100 > B ? 1 : 0);
		System.out.print(sb);
		br.close();
	}
}