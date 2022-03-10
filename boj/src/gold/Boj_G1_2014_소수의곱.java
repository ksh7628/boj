package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 10.
 * 
 * 분류: 수학, 자료 구조, 정수론, 우선순위 큐
 * 난이도: 골드1
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: X
 * 풀이: 
 * 1. 입력받은 소수들을 우선순위 큐에 넣는다.
 * 2. 매 차례마다 큐에서 뽑은 원소를 prime 배열 값들과 하나씩 곱해서 다시 큐에 넣는다.
 * -> 이 때, 곱한 값이 2^31 이상이거나 뽑은 수에서 배열 값을 모듈러 연산한 값이 0이면 배열 순회를 멈추고 다음 차례를 본다.
 * 느낀 점: 
 * 처음 풀 때 메모리 초과를 피할 수 없었는데, 중복된 값들이 큐에 추가될 때 처리를 해주지 못했기 때문이다.
 * 결국 다른 사람의 코드를 참조하게 되었고 약수의 성질을 이용해서 모듈러 연산으로 중복을 제거해주는 방법을 알게 되었다.
 * 문제에서 우선순위 큐를 사용한다는 개념도 어렵게 알았는데 거기에 정수론 성질까지 활용해야 되는 문제라서 지금 수준에는 어려운 문제라고 생각한다.
 */
public class Boj_G1_2014_소수의곱 {
	static int[] prime;
	static int K, N;
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		K = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		prime = new int[K];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			prime[i] = Integer.parseInt(st.nextToken());
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		Queue<Integer> pq = new PriorityQueue<>();
		for (int i : prime) {
			pq.offer(i);
		}

		int res = 0, idx = 0;
		while (true) {
			res = pq.poll();

			// N번째 수가 됐음
			if (++idx == N) {
				break;
			}

			for (int i : prime) {
				if ((long) res * (long) i < INF) {
					pq.offer(res * i);
				} else {// 2^31 넘어가면 넣을 필요 X
					break;
				}

				// 약수의 성질을 이용해서 중복 제거
				if (res % i == 0) {
					break;
				}
			}
		}

		return res;
	}
}