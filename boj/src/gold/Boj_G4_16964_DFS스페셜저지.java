package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 3.
 * 
 * 분류: 그래프 이론, 자료 구조, 그래프 탐색, 깊이 우선 탐색
 * 난이도: 골드4
 * 소요 시간: 0h 36m
 * 혼자 품: O
 * 풀이: 
 * https://www.acmicpc.net/problem/16940과 같은 방식으로 접근한다.
 * -> https://github.com/ksh7628/boj/blob/master/boj/src/gold/Boj_G3_16940_BFS%EC%8A%A4%ED%8E%98%EC%85%9C%EC%A0%80%EC%A7%80.java
 * 느낀 점: 
 * set을 매번 dfs에서 초기화 하는 것때문에 조금 아쉬운 코드라고 생각한다.
 * 다음에 풀 때에는 좀 더 리팩토링을 고민할만한 문제라고 느꼈다.
 */
public class Boj_G4_16964_DFS스페셜저지 {
	static List<Integer>[] list;
	static int[] order;
	static boolean[] visit;
	static int N, idx;
	static boolean flag;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		list = new ArrayList[N + 1];
		visit = new boolean[N + 1];
		order = new int[N];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		StringTokenizer st = null;
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			list[from].add(to);
			list[to].add(from);
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			order[i] = Integer.parseInt(st.nextToken());
		}

		if (order[0] == 1) {
			idx = 1;
			dfs(1);
		} else {
			flag = true;
		}

		System.out.println(!flag ? 1 : 0);
		br.close();
	}

	private static void dfs(int n) {
		if (flag) {
			return;
		}

		Set<Integer> set = new HashSet<>();
		visit[n] = true;
		for (int nxt : list[n]) {
			if (!visit[nxt]) {
				visit[nxt] = true;
				set.add(nxt);
			}
		}

		int size = set.size();
		for (int i = 0; i < size; i++) {
			if (set.remove(order[idx])) {
				dfs(order[idx++]);
			} else {
				flag = true;
				break;
			}
		}
	}
}
