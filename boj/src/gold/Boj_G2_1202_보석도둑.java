package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 5.
 * 
 * 분류: 자료 구조, 그리디 알고리즘, 정렬, 우선순위 큐
 * 난이도: 골드2
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 
 * 1. 보석과 가방을 모두 무게의 오름차순으로 정렬한다.
 * 2. i번째 가방부터 순서대로 보석의 무게와 비교해서 넣을 수 있다면 start 증가 및 max pq에 넣는다.
 * 3. 더 이상 넣을 수 없다면 보석 순회를 종료하고 pq에서 max값 하나를 누적시키고 다음 보석 순회는 start부터 시작한다.
 * 느낀 점: 처음에는 보석 가격 내림차순으로 정렬해서 풀리지 않았다. 이번 로직은 도저히 생각하기 어려워서 다른 사람의 코드를 참조하게 되었다.
 */
public class Boj_G2_1202_보석도둑 {
	static class Jewel implements Comparable<Jewel> {
		int m, v;

		public Jewel(int m, int v) {
			super();
			this.m = m;
			this.v = v;
		}

		@Override
		public int compareTo(Jewel o) {
			return Integer.compare(this.m, o.m);
		}
	}

	static Jewel[] jewel;
	static int[] bag;
	static int N, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		jewel = new Jewel[N];
		bag = new int[K];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int m = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			jewel[i] = new Jewel(m, v);
		}

		for (int i = 0; i < K; i++) {
			bag[i] = Integer.parseInt(br.readLine());
		}

		System.out.println(solution());
		br.close();
	}

	private static long solution() {
		Arrays.sort(jewel);
		Arrays.sort(bag);
		
		Queue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		int start = 0;
		long res = 0;

		for (int i = 0; i < K; i++) {
			for (int j = start; j < N; j++) {
				if (bag[i] >= jewel[j].m) {
					pq.offer(jewel[j].v);
					start++;
				} else {
					break;
				}
			}

			if (!pq.isEmpty()) {
				res += pq.poll();
			}
		}

		return res;
	}
}