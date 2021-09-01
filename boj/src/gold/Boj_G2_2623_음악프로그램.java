package gold;

import java.io.*;
import java.util.*;

public class Boj_G2_2623_음악프로그램 {
	static ArrayList<Integer>[] al;
	static int[] length, res;
	static int N, M;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		al = new ArrayList[N + 1];
		length = new int[N + 1];
		res = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			al[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int len = Integer.parseInt(st.nextToken());
			if (len == 0) {
				continue;
			}

			int pre = Integer.parseInt(st.nextToken());
			for (int j = 1; j < len; j++) {
				int cur = Integer.parseInt(st.nextToken());
				length[cur]++;
				al[pre].add(cur);
				pre = cur;
			}
		}

		topologicalSort();
		br.close();
	}

	private static void topologicalSort() {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		for (int i = 1; i <= N; i++) {
			if (length[i] == 0) {
				q.offer(i);
			}
		}

		for (int i = 1; i <= N; i++) {
			if (q.isEmpty()) {
				System.out.println(0);
				return;
			}

			int cur = q.poll();
			res[i] = cur;

			for (int nxt : al[cur]) {
				if (--length[nxt] == 0) {
					q.offer(nxt);
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= N; i++) {
			sb.append(res[i]).append("\n");
		}
		System.out.print(sb);
	}
}