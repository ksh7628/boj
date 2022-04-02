package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 2.
 * 
 * 분류: 수학, 정수론
 * 난이도: 실버4
 * 소요 시간: 0h 34m
 * 혼자 품: O
 * 풀이: 모듈러 연산을 사용해서 해당 달의 남는 공간이 없다면 1증가 해주고 누적시킴.
 * 느낀 점: 출력문 포맷을 잘못 설정해서 많이 틀리게 된 문제였다. 출력 형식 잘 읽자.
 */
public class Boj_S4_12437_새로운달력small {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			sb.append("Case #").append(tc).append(": ");
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int month = Integer.parseInt(st.nextToken());
			int dPm = Integer.parseInt(st.nextToken());
			int dPw = Integer.parseInt(st.nextToken());

			if (dPm % dPw == 0) {
				sb.append(month * dPm / dPw).append("\n");
				continue;
			}

			int day = 0, res = 0;
			for (int i = 0; i < month; i++) {
				day += dPm;
				res += day / dPw;
				day %= dPw;
				
				// 달력에 남는 공간이 있다면 1 증가
				if (day != 0) {
					res++;
				}
			}
			sb.append(res).append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}