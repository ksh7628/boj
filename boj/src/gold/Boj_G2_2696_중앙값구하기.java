package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 11.
 * 
 * 분류: 자료 구조, 우선순위 큐
 * 난이도: 골드2
 * 소요 시간: 0h 36m
 * 혼자 품: O
 * 풀이: 중간값보다 작은 값은 maxHeap에, 큰 값은 minHeap에 저장해 나가면서 홀수 차례 때 maxHeap.peek < mid < minHeap.peek을 유지.
 * 느낀 점: 연산과정을 이해하고 나니 어렵지 않게 푼 것 같다.
 */
public class Boj_G2_2696_중앙값구하기 {
	static StringBuilder sb = new StringBuilder();
	static int[] num;
	static int M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			M = Integer.parseInt(br.readLine());
			num = new int[M];

			StringTokenizer st = null;
			for (int i = 0; i < M; i++) {
				if (i % 10 == 0) {
					st = new StringTokenizer(br.readLine(), " ");
				}
				num[i] = Integer.parseInt(st.nextToken());
			}

			solution();
		}

		System.out.print(sb);
		br.close();
	}

	private static void solution() {
		sb.append(M / 2 + 1).append("\n");

		Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		Queue<Integer> minHeap = new PriorityQueue<>();
		int mid = num[0], cnt = 1;
		sb.append(mid).append(" ");

		for (int i = 1; i < M; i++) {
			if (i % 2 == 0) {// 홀
				if (mid > num[i]) {
					maxHeap.offer(num[i]);
					mid = maxHeap.poll();
				} else {
					minHeap.offer(num[i]);
					mid = minHeap.poll();
				}

				if (cnt++ % 10 == 0) {
					sb.append("\n");
				}
				sb.append(mid).append(" ");

			} else {// 짝
				if (mid > num[i]) {
					maxHeap.offer(num[i]);
					minHeap.offer(mid);
				} else {
					minHeap.offer(num[i]);
					maxHeap.offer(mid);
				}
			}
		}

		if (cnt % 10 > 0) {
			sb.append("\n");
		}
	}
}