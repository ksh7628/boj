

import java.util.*;

public class Boj_G4_13913_숨바꼭질4 {
	static StringBuilder sb = new StringBuilder();
	static int[] time, pre;
	static int N, K;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		time = new int[100001];
		pre = new int[100001];

		bfs();
		tracePath();
		System.out.print(sb);
		sc.close();
	}

	private static void bfs() {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		time[N] = 1;
		q.offer(N);

		while (!q.isEmpty()) {
			int pos = q.poll();
			if (pos == K) {
				sb.append(--time[pos]).append("\n");
				return;
			}
			for (int i = 0; i < 3; i++) {
				int npos = 0;
				switch (i) {
				case 0:
					npos = pos - 1;
					break;
				case 1:
					npos = pos + 1;
					break;
				case 2:
					npos = pos * 2;
					break;
				}
				if (npos < 0 || npos > 100000 || time[npos] > 0) {
					continue;
				}
				time[npos] = time[pos] + 1;
				q.offer(npos);
				pre[npos] = pos;
			}
		}
	}

	private static void tracePath() {
		List<Integer> path = new ArrayList<>();
		int cur = K;
		while (cur != N) {
			path.add(cur);
			cur = pre[cur];
		}
		path.add(cur);
		Collections.reverse(path);
		for (int i : path) {
			sb.append(i).append(" ");
		}
	}
}