import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 27.
 *
 * 분류: 그래프 이론, 자료구조, 그래프 탐색, 분리 집합
 * 난이도: 골드3
 * 혼자 품: O
 * 풀이: 
 * 1. 각 학생들의 비용과 union-find 연산을 위한 p배열을 초기화 시켜주고 작은 숫자가 부모가 될 수 있도록 union 연산을 M번 수행한다.
 * 2. 1 ~ N 까지 find 연산을 통해 해당 부모를 사용하지 않았다면 사용한다고 저장하고 비용을 누적시켜준다.
 * 느낀 점: 
 * dfs, bfs, union-find 세 가지 방법으로 각각 풀어봤는데 성능이 union-find > dfs > bfs 순으로 좋았다.
 * union-find로 풀 수 있는 문제면 이 방법으로 푸는게 최적이라고 느끼게 된 문제였다.
 */
public class Boj_G3_16562_친구비 {
	static int[] cost, p;
	static boolean[] isUsed;
	static int N, M, k, c, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		cost = new int[N + 1];
		p = new int[N + 1];
		isUsed = new boolean[N + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			p[i] = i;// union-find 연산을 위해 p배열 초기화
			cost[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			// 작은 숫자가 부모가 될 수 있게 함
			if (a < b) {
				union(a, b);
			} else {
				union(b, a);
			}
		}

		for (int i = 1; i <= N; i++) {
			if (!isUsed[find(i)]) {// i의 부모를 아직 사용하지 않았다면
				isUsed[find(i)] = true;
				res += cost[p[i]];// 최소 비용을 더해줌
			}
		}

		if (res > k) {
			System.out.println("Oh no");
		} else {
			System.out.println(res);
		}
		br.close();
	}

	private static int find(int x) {
		if (x == p[x]) {
			return x;
		}
		return p[x] = find(p[x]);
	}

	private static void union(int x, int y) {
		int a = find(x);
		int b = find(y);
		if (a != b) {
			p[b] = a;// 부모 갱신
			// 합친 관계 중 작은 값을 갱신
			if (cost[a] < cost[b]) {
				cost[b] = cost[a];
			} else {
				cost[a] = cost[b];
			}
		}
	}
}