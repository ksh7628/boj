package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 30.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 1h 0m
 * 혼자 품: O
 * 풀이: 주어진 입력값을 통해 그래프를 표현하기 위해 인접 리스트 2개를 선언한 후 최대 등수와 최소 등수를 dfs를 통해 구한다.
 * 느낀 점: 각 인접리스트가 단방향으로 연결되기 때문에 두번째 인접리스트를 순회할 때에는 방문 체크 배열을 초기화해주지 않아도 된다는 것을 알게 되었다.
 */
public class Boj_G3_17616_등수찾기 {
	static ArrayList<Integer>[][] al;
	static boolean[] visit;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());

		al = new ArrayList[2][N + 1];
		visit = new boolean[N + 1];
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j <= N; j++) {
				al[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			al[0][to].add(from);// 최대 등수를 알아내는 인접 리스트
			// visit = new boolean[N + 1];
			al[1][from].add(to);// 최소 등수를 알아내는 인접 리스트
		}

		int maxRank = dfs(0, X);
		int minRank = N - dfs(1, X) + 1;

		StringBuilder sb = new StringBuilder();
		sb.append(maxRank).append(" ").append(minRank);
		System.out.println(sb);
		br.close();
	}

	// 인접 리스트를 dfs로 순회하여 등수를 반환
	private static int dfs(int idx, int num) {
		int rank = 1;
		visit[num] = true;
		for (int i : al[idx][num]) {
			if (!visit[i]) {
				rank += dfs(idx, i);
			}
		}
		return rank;
	}
}