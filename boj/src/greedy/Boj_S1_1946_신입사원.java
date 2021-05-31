package greedy;

import java.io.*;
import java.util.*;

/**
 * <pre>
 * greedy 
 * Boj_S1_1946_신입사원.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 6. 1.
 * @version	: 0.1
 *
 * 분류: 그리디 알고리즘, 정렬
 * 난이도: 실버1
 * 혼자 품: O
 * 풀이: Pair 객체 배열을 생성하여 입력값들을 저장한 후 Pair 객체의 멤버 변수 둘 중 하나를 기준으로 잡고 정렬하여
 *      나머지 변수의 최소값이 갱신될때마다 횟수를 증가시키는 방식으로 풀이하였다. 
 * 느낀 점: 일단 풀기는 풀었는데 다른사람들에 비해 성능이 썩 좋지는 않은 것 같아서 다른 사람의 코드를 보고 리팩토링을 한번 해야겠다는 생각이 든다.
 */
public class Boj_S1_1946_신입사원 {
	static class Pair implements Comparable<Pair> {// 서류심사 성적과 면접시험 성적을 가지는 객체를 만들기 위한 클래스
		int doc, itv;

		Pair(int doc, int itv) {
			this.doc = doc;
			this.itv = itv;
		}

		@Override
		public int compareTo(Pair o) {// 둘 중 하나를 기준으로 잡기 위해 서류심사 성적을 기준으로 오름차순 정렬
			return this.doc - o.doc;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int tc = 1; tc <= T; tc++) {
			int n = Integer.parseInt(br.readLine());
			Pair[] p = new Pair[n];
			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				p[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}

			Arrays.sort(p);
			int cnt = 0, min = 100001;
			for (int i = 0; i < n; i++) {
				if (min > p[i].itv) {// i번째 지원자의 면접시험 성적이 다른 지원자보다 우수하다면
					min = p[i].itv;// 최소값 갱신
					cnt++;// 횟수 증가
				}
			}
			sb.append(cnt).append("\n");
		}
		System.out.println(sb);
		br.close();
	}
}