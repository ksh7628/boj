package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 14.
 * 
 * 분류: 자료 구조, 정렬, 스위핑, 우선순위 큐
 * 난이도: 골드2
 * 소요 시간: 0h 58m
 * 혼자 품: O
 * 풀이: 
 * 1. 입력 받은 h와 o값을 Pos 배열에 저장할 때 둘 중 작은 값을 h(시작점)으로, 큰 값을 o(끝점)으로 저장하고 끝점의 오름차순으로 정렬한다.
 * 2. 배열을 순회하면서 길이 d의 선분이 포함할 수 있다면 start 변수를 갱신한다.
 * 3. pq에 현재 시작점의 최솟값보다 더 작은 값이 있다면 선분을 만들 수 없으므로 큐에서 뽑는다.
 * 4. 길이 d의 선분보다 현재 선분이 작거나 같다면 포함할 수 있으므로 pq에 넣는다.
 * 5. 포함한 선분 개수의 최댓값을 갱신한다.
 * 느낀 점: 
 * 처음에는 시작점을 오름차순으로 정렬하는 방식으로 접근했는데 이 경우 반례가 존재해서 다시 생각하다가 끝점 순으로 정렬하면 된다는 것을 알게 되었다.
 * N이 최대 10만이라서 NlogN 알고리즘을 사용해야 풀리는 문제였기 때문에 시간복잡도를 잘 생각해서 정렬과 우선순위 큐로 접근할 수 있게 되었다.
 */
public class Boj_G2_13334_철로 {
	static class Pos implements Comparable<Pos> {
		int h, o;

		public Pos(int h, int o) {
			super();
			this.h = h;
			this.o = o;
		}

		// 끝점의 오름차순으로 정렬
		@Override
		public int compareTo(Pos o) {
			return Integer.compare(this.o, o.o);
		}
	}

	static Pos[] pos;
	static int n, d;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());

		pos = new Pos[n];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int h = Integer.parseInt(st.nextToken());
			int o = Integer.parseInt(st.nextToken());
			// 둘 중 작은 값을 h로, 큰 값을 o로
			pos[i] = new Pos(Math.min(h, o), Math.max(h, o));
		}

		d = Integer.parseInt(br.readLine());
		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		Arrays.sort(pos);
		// 시작점의 좌표를 가지는 최소 힙
		Queue<Integer> pq = new PriorityQueue<>();
		int res = 0, start = 0;

		for (int i = 0; i < n; i++) {
			// 길이 d의 선분이 포함할 수 있는 시작점의 최솟값
			start = pos[i].o - d;
			// pq에 현재 시작점의 최솟값보다 더 작은 값이 있다면 선분을 만들 수 없으므로 큐에서 뽑는다
			while (!pq.isEmpty() && pq.peek() < start) {
				pq.poll();
			}

			// 길이 d의 선분보다 현재 선분이 작거나 같다면 포함할 수 있으므로 pq에 넣는다
			if (start <= pos[i].h) {
				pq.offer(pos[i].h);
			}

			// 포함한 선분 개수의 최댓값 갱신
			res = Math.max(res, pq.size());
		}

		return res;
	}
}