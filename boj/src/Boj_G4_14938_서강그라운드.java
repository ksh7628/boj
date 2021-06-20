import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 20.
 *
 * 분류: 그래프 이론, 다익스트라, 플로이드-와샬
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 아이템 개수를 item에 저장하고 간선의 정보들을 인접 행렬 dist에 저장한다.
 * 2. 플로이드 와샬 알고리즘을 통해 각 경로별 최단거리를 저장한다.
 * 3. n개의 지역에 대해 각 지역에서 얻을 수 있는 아이템 개수의 최댓값을 갱신한다.
 * 느낀 점: 
 * 간단한 플로이드 와샬 알고리즘으로 풀긴 풀었는데 우선순위 큐를 사용한 다익스트라 보다는 성능이 조금 부족한것 같다.
 * 이제 플로이드는 감을 좀 잡았는데 다익스트라는 거의 기억이 나질 않아서 이제부터 다익스트라 문제를 좀 풀어봐야겠다는 생각이 들었다.
 */
public class Boj_G4_14938_서강그라운드 {
	static int[][] dist;
	static int[] item;
	static final int INF = 10000007;
	static int n, m, r;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());

		dist = new int[n + 1][n + 1];
		item = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			Arrays.fill(dist[i], INF);
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= n; i++) {
			item[i] = Integer.parseInt(st.nextToken());
		}
		for (int i = 0; i < r; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int l = Integer.parseInt(st.nextToken());
			dist[a][b] = l;
			dist[b][a] = l;
		}

		System.out.println(floyd());
		br.close();
	}

	/* 플로이드 와샬 알고리즘을 통해 각 구간별 최단거리를 찾고 얻을 수 있는 아이템의 최대 개수를 반환 */
	private static int floyd() {
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}

		for (int i = 1; i <= n; i++) {// 아이템 개수 계산을 위해 자기 자신으로 가는 경로를 0으로 만듬
			dist[i][i] = 0;
		}

		int max = 0;
		for (int i = 1; i <= n; i++) {
			int sum = 0;
			for (int j = 1; j <= n; j++) {
				if (dist[i][j] <= m) {
					sum += item[j];
				}
			}
			max = Math.max(max, sum);
		}

		return max;
	}
}
