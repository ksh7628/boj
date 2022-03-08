package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 8.
 * 
 * 분류: 자료 구조, 백트래킹, 스택
 * 난이도: 골드3
 * 소요 시간: 0h 40m
 * 혼자 품: O
 * 풀이: 모든 경우의 수를 구한 후 deque를 통해 연산자 우선순위에 맞게 계산해서 품.
 * 느낀 점: 
 * 성능이 썩 좋은 방법은 아니다. 다른 사람의 코드를 보니 최초에 brute 메소드 인자에 +,-를 수행하는 계산값과 *,/를 계산하는 값을 따로 준다면
 * 우선순위에 맞게 한 번에 계산이 가능하다는 것을 알게 되었다.
 */
public class Boj_G3_15659_연산자끼워넣기3 {
	static int[] num, op = new int[4];
	static int N;
	static int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		num = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < 4; i++) {
			op[i] = Integer.parseInt(st.nextToken());
		}

		brute(1, "", op[0], op[1], op[2], op[3]);
		System.out.println(max + "\n" + min);
		br.close();
	}

	/* 모든 경우의 수를 구해서 연산의 최댓값과 최솟값 갱신 */
	private static void brute(int cnt, String oper, int add, int sub, int mul, int div) {
		if (cnt == N) {
			int res = getNum(oper);
			max = Math.max(max, res);
			min = Math.min(min, res);
			return;
		}

		if (add > 0) {
			brute(cnt + 1, oper + "+", add - 1, sub, mul, div);
		}
		if (sub > 0) {
			brute(cnt + 1, oper + "-", add, sub - 1, mul, div);
		}
		if (mul > 0) {
			brute(cnt + 1, oper + "*", add, sub, mul - 1, div);
		}
		if (div > 0) {
			brute(cnt + 1, oper + "/", add, sub, mul, div - 1);
		}
	}

	/* 연산자 우선순위에 맞게 계산 */
	private static int getNum(String str) {
		ArrayDeque<String> dq = new ArrayDeque<>();
		dq.offer(Integer.toString(num[0]));

		// 곱하기, 나누기 먼저 계산
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c == '*') {
				int n = Integer.parseInt(dq.pollLast()) * num[i + 1];
				dq.offer(Integer.toString(n));

			} else if (c == '/') {
				int n = Integer.parseInt(dq.pollLast()) / num[i + 1];
				dq.offer(Integer.toString(n));
			} else {
				dq.offer(Character.toString(c));
				dq.offer(Integer.toString(num[i + 1]));
			}
		}

		// 나머지 연산 식 계산
		int res = Integer.parseInt(dq.poll());
		while (!dq.isEmpty()) {
			String op = dq.poll();

			if (op.equals("+")) {
				res += Integer.parseInt(dq.poll());
			} else if (op.equals("-")) {
				res -= Integer.parseInt(dq.poll());
			}
		}

		return res;
	}
}