package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 4.
 * 
 * 분류: 그래프 이론, 자료 구조, 분리 집합
 * 난이도: 골드1
 * 소요 시간: 1h 10m
 * 혼자 품: O
 * 풀이: dfs와 유니온 파인드로 풀이. 친구관계는 바로 union 연산해주고 원수관계는 인접 리스트에 저장 후 각 노드의 인접 노드가 2개 이상이면 union 연산해준다.
 * 느낀 점: 처음에는 풀긴 풀었는데 매번 visit을 초기화 해주고 dfs를 수행하다보니 성능이 좋지 않았다. 위 방법을 통해 성능을 향상시킴.
 */
public class Boj_G1_1765_닭싸움팀정하기 {
	static List<Integer>[] list;
	static int[] parent;
	static boolean[] visit;
	static int n;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());

		list = new ArrayList[n + 1];
		parent = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 1; i <= n; i++) {
			parent[i] = i;
		}

		for (int i = 0; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			String r = st.nextToken();
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());

			switch (r) {
			case "F":// 친구라면 union 연산 수행
				union(p, q);
				break;
			default:// 원수라면 dfs를 위해 인접 리스트로 연결해주고 탐색할 노드를 구하기 위해 enemy에 넣는다.
				list[p].add(q);
				list[q].add(p);
			}
		}

		// 노드가 2개 이상 연결된 인접 리스트의 노드들을 union 연산
		for (int i = 1; i <= n; i++) {
			if (list[i].size() < 2) {
				continue;
			}

			for (int j = 1; j < list[i].size(); j++) {
				union(list[i].get(j - 1), list[i].get(j));
			}
		}

		// set을 통해 합쳐진 집합의 수를 구한다
		Set<Integer> set = new HashSet<>();
		for (int i = 1; i <= n; i++) {
			set.add(find(parent[i]));
		}

		System.out.println(set.size());
		br.close();
	}

	private static void union(int x, int y) {
		int a = find(x), b = find(y);
		if (a != b) {
			parent[b] = a;
		}
	}

	private static int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return parent[x] = find(parent[x]);
	}
}