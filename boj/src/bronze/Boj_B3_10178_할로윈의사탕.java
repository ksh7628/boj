package bronze;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 1.
 * 
 * 분류: 수학, 사칙연산
 * 난이도: 브론즈3
 * 소요 시간: 0h 03m
 * 혼자 품: O
 * 풀이: 몫과 나머지를 구한 후 출력 포맷에 알맞게 출력한다.
 * 느낀 점: 반복문 + 사칙연산을 구현 가능한지 묻는 문제.
 */
public class Boj_B3_10178_할로윈의사탕 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int c = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			sb.append("You get ").append(c / v).append(" piece(s) and your dad gets ").append(c % v).append(" piece(s).\n");
		}

		System.out.print(sb);
		br.close();
	}
}