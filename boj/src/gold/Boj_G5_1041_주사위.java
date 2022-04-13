package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 13.
 * 
 * 분류: 수학, 그리디 알고리즘
 * 난이도: 골드5
 * 소요 시간: 0h 51m
 * 혼자 품: O
 * 풀이: 
 * 1. N = 1인 경우에는 모든 면의 합 - 최솟값
 * 2. N > 1이라면 다음과 같이 경우의 수를 나눈다.
 * 2-1. 1개의 면만 공유되는 주사위 개수: (N-2)^2 + (N-1)*(N-2)*4
 * 2-2. 3개의 면만 공유되는 주사위 개수: 4
 * 2-3. 2개의 면만 공유되는 주사위 개수: 전체 개수(N^2 * 5) - (2-1.의 주사위 개수) - (2-2.의 주사위 개수)
 * 3. 각 경우에서의 최솟값을 계산 후 전체 합을 구한다.
 * 느낀 점: 
 * 2년 전에 접근조차 못한 문제였는데 어느 정도 성장한 것을 느꼈다. 아쉬운 점은 하드코딩으로 풀게 되었는데 다른 사람의 코드를 보니
 * 마주보는 주사위 면의 최솟값을 계산해서 반복문을 돌지않고 한번에 계산하는 것이 가능하다는 것을 알게 되었다.
 * 하지만 주사위 문제는 하드코딩이 편한 것 같다.
 */
public class Boj_G5_1041_주사위 {
	static int[][] di2 = { { 4, 0, 1, 5 }, { 3, 0, 2, 5 }, { 3, 1, 2, 4 } };
	static int[][] di3 = { { 0, 1, 2 }, { 0, 1, 3 }, { 0, 2, 4 }, { 0, 3, 4 }, { 1, 2, 5 }, { 1, 3, 5 }, { 2, 4, 5 }, { 3, 4, 5 } };
	static int[] dice = new int[6];
	static long N;
	static int min = 51, max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Long.parseLong(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < 6; i++) {
			dice[i] = Integer.parseInt(st.nextToken());
			min = Math.min(min, dice[i]);
			max = Math.max(max, dice[i]);
		}

		System.out.println(solution());
		br.close();
	}

	private static long solution() {
		if (N == 1) {
			long res = 0;
			for (int i = 0; i < 6; i++) {
				res += dice[i];
			}

			res -= max;
			return res;
		}

		long a = (N - 2) * ((N - 2) + (N - 1) * 4);
		long b = 12;
		long c = N * N * 5 - a - b;

		b /= 3;
		c /= 2;

		long sumA = a * min;
		long sumB = b * getTop3();
		long sumC = c * getTop2();

		return sumA + sumB + sumC;
	}

	private static int getTop2() {
		int top2 = 101;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				top2 = Math.min(top2, dice[di2[i][j]] + dice[di2[i][(j + 1) % 4]]);
			}
		}
		return top2;
	}

	private static int getTop3() {
		int top3 = 151;
		for (int i = 0; i < 8; i++) {
			int sum = 0;
			for (int j = 0; j < 3; j++) {
				sum += dice[di3[i][j]];
			}
			top3 = Math.min(top3, sum);
		}
		return top3;
	}
}