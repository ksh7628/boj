package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 25.
 * 
 * 분류: 자료 구조, 그리디 알고리즘, 정렬, 우선순위 큐
 * 난이도: 골드5
 * 소요 시간: 0h 33m
 * 혼자 품: O
 * 풀이: 
 * 1. lesson 배열에 입력값들을 객체로 저장하고 시작 시간의 오름차순으로 정렬한다.
 * 2. 강의 종료 시간을 오름차순으로 뽑을 우선 순위 큐를 정의하고 첫 강의 종료 시간을 넣는다.
 * 3. lesson 배열을 순회하면서 큐의 첫번째 값과 시작 시간을 비교한다.
 * 3-1. 새 강의 시작이 가능하다면 큐에서 값을 하나 뽑는다.
 * 3-2. 아니라면 강의실 횟수를 하나 증가시킨다.
 * 4. 현재 강의 종료 시간을 큐에 넣는다.
 * 느낀 점: 
 * https://www.acmicpc.net/problem/1931 문제와 거의 비슷하지만 시작 시간의 오름차순으로 정렬하는게 핵심이다.
 * 그리디 알고리즘의 대표 유형 문제 중 하나라서 꼭 숙지하고 있어야겠다.
 */
public class Boj_G5_11000_강의실배정 {
	static class Lesson implements Comparable<Lesson> {
		int start, end;

		public Lesson(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Lesson o) {// 시작 시간의 오름차순, 같으면 끝나는 시간 오름차순
			if (this.start == o.start) {
				return Integer.compare(this.end, o.end);
			}
			return Integer.compare(this.start, o.start);
		}
	}

	static Lesson[] lesson;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		lesson = new Lesson[N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			lesson[i] = new Lesson(start, end);
		}

		System.out.println(solution());
		br.close();
	}

	/* 
	 * 최소 강의실 사용 개수
	 * 우선순위 큐를 사용하여 큐에 회의가 끝나는 시간들을 저장
	 * 정렬 된 lesson 배열을 순회하면서 큐의 첫번째 값(진행되는 강의 중 제일 빨리 끝나는 시간)과 시작 시간 비교
	 */
	private static int solution() {
		Arrays.sort(lesson);
		Queue<Integer> pq = new PriorityQueue<>();
		int res = 1;
		pq.offer(lesson[0].end);

		for (int i = 1; i < N; i++) {
			// 새 강의 시작 가능하다면 더 이상 큐의 최상단 값 필요 X
			if (!pq.isEmpty() && lesson[i].start >= pq.peek()) {
				pq.poll();
			} else {// 아니라면 강의실 개수 증가
				res++;
			}
			
			// 현재 강의 종료 시간을 큐에 넣는다
			pq.offer(lesson[i].end);
		}

		return res;
	}
}