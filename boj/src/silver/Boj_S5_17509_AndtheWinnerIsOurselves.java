package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 25.
 * 
 * 분류: 그리디 알고리즘, 정렬
 * 난이도: 실버5
 * 소요 시간: 0h 24m
 * 혼자 품: O
 * 풀이: 걸리는 시간에 대해서 오름차순 정렬을 하고 시간의 누적합을 더해주면서 틀린 횟수의 패널티는 따로 계산해서 더해준다.
 * 느낀 점: 정렬로 푸는 간단한 그리디 알고리즘이었다.
 */
public class Boj_S5_17509_AndtheWinnerIsOurselves {
	static class Problem implements Comparable<Problem> {
		int minute, wrongCnt;

		public Problem(int minute, int wrongCnt) {
			super();
			this.minute = minute;
			this.wrongCnt = wrongCnt;
		}

		@Override
		public int compareTo(Problem o) {
			return Integer.compare(this.minute, o.minute);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Problem[] p = new Problem[11];

		for (int i = 0; i < 11; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int minute = Integer.parseInt(st.nextToken());
			int wrongCnt = Integer.parseInt(st.nextToken());
			p[i] = new Problem(minute, wrongCnt);
		}

		Arrays.sort(p);
		int res = 0, sum = 0;

		for (int i = 0; i < 11; i++) {
			sum += p[i].minute;
			res += 20 * p[i].wrongCnt + sum;
		}

		System.out.println(res);
		br.close();
	}
}