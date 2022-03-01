package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 1.
 * 
 * 분류: 자료 구조, 그리디 알고리즘, 정렬, 우선순위 큐
 * 난이도: 골드3
 * 소요 시간: 1h 00m
 * 혼자 품: O
 * 풀이: 
 * 1. 거리의 오름차순으로 fuel 배열을 정렬한다.
 * 2. fuel 배열을 순회하면서 가까운 거리부터 갈 수 있는지 확인한다.
 * 2-1. 갈 수 있는 거리라면 연료만 소진하고 다음으로 넘어간다.
 * 2-2. 갈 수 없는 거리라면 pq에 담긴 연료를 내림차순으로 빼서 갈 수 있을때까지 충전한다.
 * -> 전부 사용해도 갈 수 없다면 -1
 * 3. 2번 과정이 끝나면 현재 연료, 출발점을 갱신하고 pq에 현재 주유소에서 채울 수 있는 연료양을 넣는다.
 * 4. fuel 배열 탐색이 끝났다면 마지막 구간에 대해 2-2를 수행한다.
 * 느낀 점: -1 리턴 조건을 나중에 보고 수정해서 겨우 풀게 되었다. -> 문제 잘 읽기..
 */
public class Boj_G3_1826_연료채우기 {
	static class Fuel implements Comparable<Fuel> {
		int dist, amount;

		public Fuel(int dist, int amount) {
			super();
			this.dist = dist;
			this.amount = amount;
		}

		@Override
		public int compareTo(Fuel o) {
			return Integer.compare(this.dist, o.dist);
		}
	}

	static Fuel[] fuel;
	static int N, L, P;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		fuel = new Fuel[N];

		StringTokenizer st = null;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int dist = Integer.parseInt(st.nextToken());
			int amount = Integer.parseInt(st.nextToken());

			fuel[i] = new Fuel(dist, amount);
		}

		st = new StringTokenizer(br.readLine(), " ");
		L = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());

		System.out.println(solution());
		br.close();
	}

	/* 
	 * fuel -> 거리의 오름차순 정렬
	 * pq -> 충전할 수 있는 연료를 저장, 내림차순
	 */
	private static int solution() {
		Arrays.sort(fuel);
		Queue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		int start = 0, res = 0;

		for (int i = 0; i < N; i++) {
			while (fuel[i].dist - start > P) {
				if (pq.isEmpty()) {
					return -1;
				}

				P += pq.poll();
				res++;
			}
			
			// 현재 연료와 출발점 갱신 후 현재 위치한 주유소의 충전 가능 연료량을 pq에 넣는다
			P -= fuel[i].dist - start;
			start = fuel[i].dist;
			pq.offer(fuel[i].amount);
		}

		while (L - start > P) {
			if (pq.isEmpty()) {
				return -1;
			}
			
			P += pq.poll();
			res++;
		}

		return res;
	}
}