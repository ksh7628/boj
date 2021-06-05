

import java.io.*;
import java.util.*;

/**
 * <pre>
 * two_pointer 
 * Boj_S4_3273_두수의합.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 15.
 * @version	: 0.2
 *
 * 분류: 정렬, 두 포인터
 * 난이도: 실버4
 * 혼자 품: O
 * 풀이: N이 10만이라서 O(N^2)풀이로 하면 시간초과가 나기 때문에 투 포인터 알고리즘으로 풀이했다.
 *      정렬을 한 후에 양 끝에 포인터를 두고 조건에 따라 점점 좁혀가는 식으로 풀었다.
 * 느낀 점: 기초적인 투 포인터 문제라서 어려움 없이 풀었다. 기초이긴 하지만 이걸 응용한 문제들이 많기 때문에 확실히 학습해야 겠다.
 */
public class Boj_S4_3273_두수의합 {
	static int[] num;
	static int N, x;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		num = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}
		x = Integer.parseInt(br.readLine());
		Arrays.sort(num);
		System.out.println(getCount());
		br.close();
	}

	private static int getCount() {
		int cnt = 0, left = 0, right = N - 1;
		while (left < right) {
			int sum = num[left] + num[right];
			if (sum == x) {
				++cnt;
			}

			if (sum > x) {
				--right;
			} else {
				++left;
			}
		}
		return cnt;
	}
}