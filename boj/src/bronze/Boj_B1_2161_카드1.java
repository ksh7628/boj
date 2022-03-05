package bronze;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 6.
 * 
 * 분류: 구현, 자료 구조, 큐
 * 난이도: 브론즈1
 * 소요 시간: 0h 05m
 * 혼자 품: O
 * 풀이: 1 ~ N까지의 수를 큐에 넣은 후 큐의 크기가 1이 될 때까지 홀수번째 빼는 수는 sb에 저장, 짝수번째 빼는 수는 큐에 다시 넣음.
 * 느낀 점: .
 */
public class Boj_B1_2161_카드1 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		ArrayDeque<Integer> q = new ArrayDeque<>();
		for (int i = 1; i <= N; i++) {
			q.offer(i);
		}

		StringBuilder sb = new StringBuilder();
		while (q.size() > 1) {
			sb.append(q.poll()).append(" ");
			q.offer(q.poll());
		}

		sb.append(q.poll()).append(" ");
		System.out.println(sb);
		br.close();
	}
}