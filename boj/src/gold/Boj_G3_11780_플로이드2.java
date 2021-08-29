package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 16.
 *
 * 분류: 그래프 이론, 플로이드-와샬
 * 난이도: 골드3
 * 혼자 품: X
 * 풀이: 
 * 1. 최소 거리 정보를 입력받아서 dist배열에 저장하고 경로를 trace배열에 저장한다. 이 때, 똑같은 경로가 들어올 수 있으므로 최소 거리를 갱신해준다.
 * 2. 플로이드 와샬 알고리즘을 통해 최소 거리를 갱신해 주면서 그에 따른 경로를 설정해준다.
 * 3. 구한 최소 거리와 각 경로를 출력해준다.
 * 느낀 점: 
 * 여태까지 풀었던 경로 추적 문제는 역추적 형식으로 거꾸로 출력해줬는데 이번 문제는 정방향으로 설정해야 풀 수 있었던 문제였다.
 * 다른 방식이라는 것을 간과하고 풀어서 결국 다른 코드를 참조하여 풀었다. 항상 다른 로직을 생각할 수 있는 힘을 길러야겠다고 느꼈다.
 */
public class Boj_G3_11780_플로이드2 {
	static int[][] dist;
	static int[][] trace;
	static int n, m;
	static final int INF = 1000000;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());
		dist = new int[n + 1][n + 1];// 최소 거리 배열
		trace = new int[n + 1][n + 1];// 경로 추적 배열

		for (int i = 1; i <= n; i++) {// 처음에 초기값을 무한대로 주고 자기 자신에게 가는 값은 0으로 저장
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}

		for (int i = 0; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int distance = Integer.parseInt(st.nextToken());

			dist[from][to] = Math.min(dist[from][to], distance);
			trace[from][to] = to;
		}

		floyd();
		print();
		br.close();
	}

	/* 플로이드 와샬 알고리즘을 통해 최소 거리와 경로를 구함 */
	private static void floyd() {
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {// 최소 거리, 경로 갱신
						dist[i][j] = dist[i][k] + dist[k][j];
						trace[i][j] = trace[i][k];
					}
				}
			}
		}
	}

	/* 값 출력 */
	private static void print() throws Exception {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		/* i에서 j로 가는 최소 거리 출력 */
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (dist[i][j] == INF) {
					bw.write("0 ");
					continue;
				}
				bw.write(dist[i][j] + " ");
			}
			bw.write("\n");
		}

		/* 도시의 개수와 경로를 출력 */
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (dist[i][j] == 0 || dist[i][j] == INF) {// 갈 수 없다면 0 개행
					bw.write("0\n");
					continue;
				}

				ArrayList<Integer> pathList = new ArrayList<>();// i에서 j로 가는 최소 거리의 경로를 저장하는 리스트
				int start = i;

				while (start != j) {
					pathList.add(start);
					start = trace[start][j];
				}

				pathList.add(j);
				bw.write(pathList.size() + " ");// 도시의 개수

				for (int p : pathList) {// 경로 탐색
					bw.write(p + " ");
				}
				bw.write("\n");
			}
		}

		bw.flush();
		bw.close();
	}
}