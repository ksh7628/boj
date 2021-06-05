

import java.io.*;
import java.util.*;

/**
 * <pre>
 * floyd_warshall 
 * Boj_G4_2458_키순서.java
 * </pre>
 *
 * @author : KimSeonhong
 * @date : 2021. 4. 22.
 * @version : 0.1
 *
 * 분류: 그래프 이론, 그래프 탐색, 깊이 우선 탐색, 플로이드-와샬
 * 난이도: 골드4 혼자 품: O
 * 풀이: 문제에서 주어진 관계를 단방향 인접 행렬로 구성하여 플로이드-와샬 알고리즘을 이용해서 연결 여부를 판단한 후
 *      자신의 키가 몇 번째인지 아는 학생의 수를 구하여 풀어하였다.
 * 느낀 점: dfs나 bfs로도 풀 수 있지만 플로이드-와샬 알고리즘이 코드길이도 짧고 n이 500이하라서
 *        충분히 돌아가기 때문에 코드를 되새길겸 풀기에 좋은 문제인것 같다.
 */
public class Boj_G4_2458_키순서 {
	static int[][] adj;
	static int[] connCnt;
	static int N, M;
	static final int INF = 100000007;// 오버플로우에 주의하여 적당히 큰 수를 무한대라고 저장

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		adj = new int[N + 1][N + 1];// 번호가 주어지므로 배열 크기 N+1*N+1로 선언

		for (int i = 1; i <= N; i++) {
			Arrays.fill(adj[i], INF);// 처음에 무한대로 초기화
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());// 번호가 a인 학생
			int b = Integer.parseInt(st.nextToken());// 번호가 b인 학생
			adj[a][b] = 1;// a학생이 b학생보다 키가 작다는 것을 1로 표현
		}

		floyd();
		System.out.println(checkTall());;
		br.close();
	}

	/* 플로이드-와샬 알고리즘 */
	private static void floyd() {
		for (int k = 1; k <= N; k++) { // 경유지
			for (int i = 1; i <= N; i++) { // 출발지
				for (int j = 1; j <= N; j++) { // 도착지
					if (adj[i][j] > adj[i][k] + adj[k][j]) {
						adj[i][j] = adj[i][k] + adj[k][j];
					}
				}
			}
		}
	}

	/* 자신의 키가 몇 번째인지 알 수 있는 학생의 수를 구함 */
	private static int checkTall() {
		int cnt = 0;
		connCnt = new int[N + 1];// 자신과 몇명이 연결되어 있는지(자신이 몇명의 키를 알수있는지) 횟수를 저장하는 배열
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (adj[i][j] != INF) {// 무한대가 아니라면 자신과 연결된 학생의 수를 더해줌
					++connCnt[i];
					++connCnt[j];
				}
			}
		}
		for (int i = 1; i <= N; i++) {
			if (connCnt[i] == N - 1) {// 모두 연결되어 있다면 자신의 키가 몇 번째인지 알 수 있는 학생의 수 증가
				++cnt;
			}
		}
		return cnt;
	}
}