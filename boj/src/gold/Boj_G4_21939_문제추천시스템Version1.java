package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 16.
 * 
 * 분류: 자료 구조, 트리를 사용한 집합과 맵, 우선순위 큐
 * 난이도: 골드4
 * 소요 시간: 1h 30m 이상(time over)
 * 혼자 품: X
 * 풀이: 
 * 1. 최소 힙과 최대 힙을 각각 만들어서 둘 다 Problem 객체로 넣어주고 문제 번호를 인덱스로 갖는 level 배열에 문제 난이도를 저장한다.
 * 2. 각 쿼리에 대해 다음과 같이 수행한다.
 * 2-1. recommend의 경우
 * 2-1-1. 값이 1인 경우, 난이도 내림차순 -> 문제 번호 내림차순 으로 저장된 minHeap에서 최상단에 있는 난이도 값과 level[문제] 값이 같으면 해당 문제 출력.
 * 2-1-2. 아니라면 최상단에 있는 값을 삭제
 * 2-1-3. 값이 -1인 경우, 난이도 오름차순 -> 문제 번호 오름차순 으로 저장된 maxHeap에서 최상단에 있는 난이도 값과 level[문제] 값이 같으면 해당 문제 출력.
 * 2-1-4. 아니라면 최상단에 있는 값을 삭제
 * 2-2. add인 경우, minHeap, maxHeap 두곳 모두 저장하고 해당 문제 번호를 인덱스로 갖는 level 배열에 문제 난이도를 저장한다.
 * 2-3. solved인 경우, 해당 문제 번호를 인덱스로 갖는 level 배열 값을 0으로 한다.
 * 느낀 점: 
 * 1차 접근은 treeMap을 써볼려고 했는데 단독으로 사용해서는 remove연산을 처리하지 못해서 포기했다.
 * 2차 접근으로 우선순위 큐 2개를 사용해서 위와 같은 풀이로 접근했는데 매번 큐에서 값을 뽑고 확인하는 방식이 recommend의 정의와 맞지 않아서 계속 틀리게 되었다.
 * 풀고 보니 그렇게 어려운 문제는 아니었는데 디버깅을 잘 하지 못해서 시간을 많이 사용하게 된 점이 아쉽다.
 */
public class Boj_G4_21939_문제추천시스템Version1 {
	static class Problem implements Comparable<Problem> {
		int num, lv;

		public Problem(int num, int lv) {
			super();
			this.num = num;
			this.lv = lv;
		}

		@Override
		public int compareTo(Problem o) {
			if (this.lv == o.lv) {
				return -Integer.compare(this.num, o.num);
			}
			return -Integer.compare(this.lv, o.lv);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int[] level = new int[100001];
		Queue<Problem> minHeap = new PriorityQueue<>();
		Queue<Problem> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int P = Integer.parseInt(st.nextToken());
			int L = Integer.parseInt(st.nextToken());

			level[P] = L;
			minHeap.offer(new Problem(P, L));
			maxHeap.offer(new Problem(P, L));
		}

		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int P = 0;

			switch (st.nextToken()) {
			case "recommend":
				int x = Integer.parseInt(st.nextToken());
				if (x == 1) {
					while (level[minHeap.peek().num] != minHeap.peek().lv) {
						minHeap.poll();
					}
					sb.append(minHeap.peek().num).append("\n");
				} else {
					while (level[maxHeap.peek().num] != maxHeap.peek().lv) {
						maxHeap.poll();
					}
					sb.append(maxHeap.peek().num).append("\n");
				}

				break;
			case "add":
				P = Integer.parseInt(st.nextToken());
				int L = Integer.parseInt(st.nextToken());
				level[P] = L;
				minHeap.add(new Problem(P, L));
				maxHeap.add(new Problem(P, L));
				break;
			case "solved":
				P = Integer.parseInt(st.nextToken());
				level[P] = 0;
				break;
			}
		}

		System.out.print(sb);
		br.close();
	}
}