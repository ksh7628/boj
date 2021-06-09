import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 9.
 *
 * 분류: 그래프 이론, 플로이드-와샬
 * 난이도: 실버1
 * 혼자 품: O
 * 풀이: 삼중 반복문을 경유지, 출발지, 도착지 순으로 수행하여 경유지를 거쳐 도착가능하다면 갈 수 있다고 갱신하면 풀이가 끝난다.
 * 느낀 점: 플로이드 와샬 알고리즘을 사용할 수 있는지를 묻는 문제여서 쉽게 풀었다. 경유지-출발지-도착지 순서를 잊지만 않으면 될 것 같다.
 */
public class Boj_S1_11403_경로찾기 {
	static int[][] graph;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		graph = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		floyd();
		print();
		br.close();
	}

	/* 플로이드-와샬 알고리즘을 사용하여 갈 수 있는 경로를 갱신 */
	private static void floyd() {
		for (int k = 0; k < N; k++) {// 경유지
			for (int i = 0; i < N; i++) {// 출발지
				for (int j = 0; j < N; j++) {// 도착지
					if (graph[i][k] == 1 && graph[k][j] == 1) {// 경유지를 경유할수 있다면 갈 수 있으므로 값 갱신
						graph[i][j] = 1;
					}
				}
			}
		}
	}

	/* 결과 출력 */
	private static void print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sb.append(graph[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}