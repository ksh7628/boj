package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 11.
 * 
 * 분류: 자료 구조, 우선순위 큐
 * 난이도: 골드2
 * 소요 시간: 0h 07m
 * 혼자 품: O
 * 풀이: https://www.acmicpc.net/problem/2696과 동일.
 * 느낀 점: .
 */
public class Boj_G2_1655_가운데를말해요 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int N = Integer.parseInt(br.readLine());

		Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		Queue<Integer> minHeap = new PriorityQueue<>();
		int mid = Integer.parseInt(br.readLine());
		sb.append(mid).append("\n");

		for (int i = 1; i < N; i++) {
			int num = Integer.parseInt(br.readLine());

			if (i % 2 == 0) {
				if (mid > num) {
					maxHeap.offer(num);
					mid = maxHeap.poll();
				} else {
					minHeap.offer(num);
					mid = minHeap.poll();
				}
				sb.append(mid).append("\n");

			} else {
				if (mid > num) {
					maxHeap.offer(num);
					minHeap.offer(mid);
				} else {
					minHeap.offer(num);
					maxHeap.offer(mid);
				}
				sb.append(maxHeap.peek()).append("\n");
			}
		}

		System.out.print(sb);
		br.close();
	}
}