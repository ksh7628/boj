package gold;

import java.io.*;
import java.util.*;

/**
 * @author : KimSeonhong
 * @date : 2022. 3. 28.
 * 
 * 분류: 자료 구조, 스택
 * 난이도: 골드3
 * 소요 시간: 0h 37m 
 * 혼자 품: O
 * 풀이: 
 * 1. 역순으로 탐색을 해서 F(i)와 스택의 F값을 비교한다.
 * 2. F(i)가 크거나 같다면 스택에서 F(i)보다 큰 F값이 나올때까지 뽑아서 큰 F값을 찾았다면 NGF(i)에 해당 수를 저장하고 스택에 넣는다.
 * 3. 못찾았다면 스택에 새로운 수를 넣고 현재 NGF(i)는 -1로 저장한다.
 * 4. F(i)가 작다면 NGF(i)에 해당 수를 저장하고 스택에 넣는다.
 * 느낀 점: 스택 응용 문제를 적당한 시간내에 푼 것 같다. 추가로 DP 풀이로도 풀리는 문제여서 나중에 DP로 풀어봐야겠다.
 */
public class Boj_G3_17299_오등큰수 {
	static int[] arr, F = new int[1000001];
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			F[arr[i]]++;
		}

		solution();
		br.close();
	}

	private static void solution() {
		Stack<Integer> s = new Stack<>();
		int[] NGF = new int[N];
		// 처음에는 마지막 인덱스 수를 스택에 넣고 오른쪽에 수가 없으므로 NGF(N-1) = -1
		s.push(arr[N - 1]);
		NGF[N - 1] = -1;

		// 역순으로 탐색
		loop: for (int i = N - 2; i >= 0; i--) {
			// 현재 F(i)이 스택 최상단 F값보다 크거나 같다
			if (F[arr[i]] >= F[s.peek()]) {
				while (!s.isEmpty()) {
					// 스택에서 F값이 큰 수를 찾음
					// 현재 수의 NGF값 갱신 후 스택에 해당 수 넣음
					if (F[arr[i]] < F[s.peek()]) {
						NGF[i] = s.peek();
						s.push(arr[i]);
						continue loop;
					}
					s.pop();
				}

				// 못찾았다면 스택에 새로운 수를 넣는다
				NGF[i] = -1;
				s.push(arr[i]);

				// 값이 작다면 NGF값 갱신 후 스택에 해당 수 넣는다
			} else {
				NGF[i] = s.peek();
				s.push(arr[i]);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i : NGF) {
			sb.append(i).append(" ");
		}
		System.out.println(sb);
	}
}