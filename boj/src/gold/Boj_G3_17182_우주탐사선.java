package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 12.
 * 
 * 분류: 그래프 이론, 비트마스킹, 백트래킹, 플로이드-와샬
 * 난이도: 골드3
 * 소요 시간: 0h 21m
 * 혼자 품: O
 * 풀이: 
 * 1. 플로이드-와샬 알고리즘을 통해 모든 경로의 최단 거리를 찾는다.
 * 2. 모든 행성을 순회하기 위해 비트마스킹을 이용하여 아직 방문하지 않았다면 해당 경로 탐색에 걸리는 시간을 더해가면서 최솟값을 구한다.
 * 느낀 점: 처음에는 플로이드-와샬만 쓰면 되는 줄 알았는데 TSP 문제처럼 모든 경우에 대해 조사를 해야 되기 때문에 비트마스킹을 사용한 순열을 구하게 되었다.
 */
public class Boj_G3_17182_우주탐사선 {
	static int[][] map;
	static int N, K, res = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		solution(K);
		System.out.println(res);
		br.close();
	}

	/*
	 * 1. 플로이드-와샬 알고리즘을 통해 모든 경로의 최단 거리를 찾는다.
	 * 2. 모든 행성을 탐사하면서 최소 시간을 구한다.
	 */
	private static void solution(int K) {
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] > map[i][k] + map[k][j]) {
						map[i][j] = map[i][k] + map[k][j];
					}
				}
			}
		}

		perm(K, 1 << K, 0);
	}
	
	/* 비트마스킹을 통해 모든 순열을 구하고 최솟값을 갱신한다. */
	private static void perm(int cur, int bit, int sum) {
		if (bit == (1 << N) - 1) {
			res = Math.min(res, sum);
			return;
		}

		for (int i = 0; i < N; i++) {
			if ((bit & (1 << i)) == 0) {
				perm(i, bit | (1 << i), sum + map[cur][i]);
			}
		}
	}
}