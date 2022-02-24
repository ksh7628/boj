package silver;

import java.io.*;
import java.util.*;

/**
 * @author : KimSeonhong
 * @date : 2022. 2. 24.
 * 
 * 분류: 구현, 브루트포스 알고리즘, 백트래킹
 * 난이도: 실버2 소요 시간: 0h 12m
 * 혼자 품: O
 * 풀이: 사용할 수 있는 연산자 갯수가 있으면 재귀를 통해 연산을 수행해나가면서 모든 연산이 끝났다면 최댓값과 최솟값을 갱신한다.
 * 느낀 점: https://www.acmicpc.net/problem/14888과 똑같은 문제라서 푸는 시간을 단축했다.
 */
public class Boj_S2_15658_연산자끼워넣기2 {
	static int[] num;
	static int N, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		num = new int[N];
		int[] op = new int[4];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < 4; i++) {
			op[i] = Integer.parseInt(st.nextToken());
		}

		brute(1, num[0], op[0], op[1], op[2], op[3]);
		System.out.println(max + "\n" + min);
		br.close();
	}

	/* 재귀를 통해 해당 연산자를 사용할 수 있으면 사용하면서 모두 썻다면 최댓값과 최솟값 갱신 */
	private static void brute(int idx, int sum, int add, int sub, int mul, int div) {
		if (idx == N) {
			max = Math.max(max, sum);
			min = Math.min(min, sum);
			return;
		}

		if (add > 0) {
			brute(idx + 1, sum + num[idx], add - 1, sub, mul, div);
		}

		if (sub > 0) {
			brute(idx + 1, sum - num[idx], add, sub - 1, mul, div);
		}

		if (mul > 0) {
			brute(idx + 1, sum * num[idx], add, sub, mul - 1, div);
		}

		if (div > 0) {
			brute(idx + 1, sum / num[idx], add, sub, mul, div - 1);
		}
	}
}