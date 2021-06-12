import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 13.
 *
 * 분류: 자료구조, 그리디 알고리즘, 우선순위 큐
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 우선순위 큐(최소 힙)을 만들어서 입력값들을 모두 넣어준다.
 * 2. 큐 크기가 1이하가 될때까지 제일 작은 두 수를 뽑아서 합친 후 결과값에 더해주고 두 수의 합을 큐에 넣어주면서 반복한다.
 * 느낀 점: 
 * 예전에 학부생때 배웠던 허프만 트리랑 비슷한 개념으로 접근해서 풀었는데 어렵지 않았던것 같다.
 * 주의할 점은 입력값이 한개일 경우 비교를 할 다른 값이 없으므로 0을 출력해야 한다는 것을 놓치면 안되는거 같다.
 */
public class Boj_G4_1715_카드정렬하기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int i = 0; i < N; i++) {
			pq.offer(Integer.parseInt(br.readLine()));
		}

		int res = 0;
		while (pq.size() > 1) {// 큐의 크기가 2 이상일때만 반복 -> 1 또는 0 일경우 비교할게 없다
			int a = pq.poll();// 제일 작은 두 수를 꺼냄
			int b = pq.poll();
			res += a + b;
			pq.offer(a + b);// 합친 후 우선순위 큐에 넣어줌
		}
		System.out.println(res);
		br.close();
	}
}