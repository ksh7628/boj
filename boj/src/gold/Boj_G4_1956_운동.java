package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 22.
 *
 * 분류: 그래프 이론, 플로이드-와샬
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 도로 정보를 dist에 저장할 때 단방향 이므로 dist[a][b] = c 만 저장한다.
 * 2. 플로이드 와샬 알고리즘을 이용하여 각 경로에 따른 최단거리를 저장한다.
 * 3. 사이클이 형성되는 좌표들만 검사하여 최단 거리를 갱신한다.
 * 느낀 점: 
 * 플로이드 알고리즘을 적용하면 딱히 어려운 문제는 아니었다. 단방향으로만 정보가 주어지기 때문에 저장할 때 주의를 하고 사이클이 형성된다는 것은
 * 자기 자신으로 가는 경로가 있다는 뜻이므로 dist[1][1] 부터 dist[V][V] 까지만 검사해주면 쉽게 풀 수 있다.
 */
public class Boj_G4_1956_운동 {
	static int[][] dist;
	static int V, E;
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		dist = new int[V + 1][V + 1];
		for (int i = 1; i <= V; i++) {
			Arrays.fill(dist[i], INF);
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			dist[a][b] = c;
		}

		System.out.println(floyd());
		br.close();
	}

	/* 플로이드 와셜 알고리즘을 통해 사이클을 이루는 최단거리를 반환 */
	private static int floyd() {
		for (int k = 1; k <= V; k++) {
			for (int i = 1; i <= V; i++) {
				for (int j = 1; j <= V; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}

		int min = INF;
		for (int i = 1; i <= V; i++) {
			min = Math.min(min, dist[i][i]);// 자기 자신으로 가는 경로 = 사이클 이므로 최단 거리를 갱신
		}

		if (min == INF) {// 존재하지 않는다면 -1
			min = -1;
		}
		return min;
	}
}