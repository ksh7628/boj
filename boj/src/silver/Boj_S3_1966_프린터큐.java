package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 8.
 *
 * 분류: 구현, 자료 구조, 시뮬레이션, 큐
 * 난이도: 실버3
 * 혼자 품: O
 * 풀이: 
 * 1. 문서의 개수 N개만큼 반복문을 통해 차례로 큐에 넣어주면서 배열에도 값을 저장한다.
 * 2. 값들을 저장한 배열을 정렬한다. 정렬을 통해 문서의 중요도를 파악 가능
 * 3. 무한 루프를 돌려서 원하는 순서의 문서가 인쇄될때까지 반복
 * 느낀 점: 
 * 예전에 클래스를 사용해서 알고리즘 문제를 푼다는 개념도 없었을 때에는 시도도 못한 문제였는데 이번에 다시 도전해서 풀어보니
 * 조건문만 적절하게 써도 풀리는 문제여서 그 동안 문제풀이를 통해 실력이 향상되었다는 것을 느끼게 해준 문제다.
 */
public class Boj_S3_1966_프린터큐 {
	static class Docu {
		int idx, num;

		Docu(int idx, int num) {
			this.idx = idx;
			this.num = num;
		}
	}

	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			int[] arr = new int[N];
			Queue<Docu> q = new LinkedList<>();
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				int num = Integer.parseInt(st.nextToken());
				q.offer(new Docu(i, num));
				arr[i] = num;
			}

			Arrays.sort(arr);
			int res = 0;// 인쇄 순서
			while (true) {
				int size = q.size();
				Docu d = q.poll();
				if (d.num == arr[size - 1 - res]) {// 현재 큐에서 가장 중요도가 높은 문서를 찾음
					res++;
					if (d.idx == M) {// 원하는 위치에 있는 문서를 찾았다면 반복문 종료
						break;
					} else {// 못찾았다면 큐의 제일 뒤로 배치
						q.offer(d);
					}
				} else {// 못찾았다면 큐의 제일 뒤로 배치
					q.offer(d);
				}

			}
			sb.append(res).append("\n");
		}
		System.out.println(sb);
		br.close();
	}
}