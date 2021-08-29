package silver;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 7.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 플로이드-와샬
 * 난이도: 실버1
 * 혼자 품: O
 * 풀이: 친구 관계를 인접리스트에 저장한 후 bfs를 통해 각각의 케빈 베이컨 수를 구하고 최소값을 갱신해나가면서 최소값이 같을 경우
 *      번호의 최소값을 갱신하는 식으로 풀었다.
 * 느낀 점: 간단한 bfs 문제여서 딱히 어려운 점은 없었으나 각각의 깊이를 따로 계산해주어야 된다는 점을 알게 되었다.
 */
public class Boj_S1_1389_케빈베이컨의6단계법칙 {
	static ArrayList<Integer>[] al;// 인접 리스트
	static int[][] depth;
	static int N, M, res, min = Integer.MAX_VALUE;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		al = new ArrayList[N + 1];
		depth = new int[N + 1][N + 1];
		for (int i = 1; i <= N; i++) {
			al[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			al[a].add(b);
			al[b].add(a);
		}

		res = N + 1;// 번호가 가장 작은 사람을 구할려면 최소값을 계속 갱신해줘야 되기 때문에 초기값을 사람수+1로 정의
		for (int i = 1; i <= N; i++) {
			bfs(i);
		}
		System.out.println(res);
		br.close();
	}

	/* bfs를 통해 x번호 사람의 케빈 베이컨 수를 구하는 메서드 */
	private static void bfs(int x) {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		for (int i = 1; i <= N; i++) {// 처음에 -1로 초기화
			depth[x][i] = -1;
		}
		depth[x][x] = 0;
		q.offer(x);
		while (!q.isEmpty()) {
			int pos = q.poll();
			for (int i : al[pos]) {
				if (depth[x][i] != -1) {
					continue;
				}
				depth[x][i] = depth[x][pos] + 1;
				q.offer(i);
			}
		}

		int sum = 0;
		for (int i = 1; i <= N; i++) {// x번호를 갖는 사람의 단계 수를 모두 더해줌
			sum += depth[x][i];
		}

		if (sum < min) {// 케빈 베이컨 수 최소값 갱신
			res = x;
			min = sum;
		} else if (sum == min) {// 케빈 베이컨 수가 같다면 번호의 최소값을 갱신
			res = Math.min(res, x);
		}
	}
}
