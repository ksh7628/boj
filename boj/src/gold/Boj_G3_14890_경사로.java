package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 16.
 * 
 * 분류: 구현
 * 난이도: 골드3
 * 소요 시간: 0h 30m
 * 혼자 품: O
 * 풀이: 
 * 1. 행 단위로 열을 읽는 map 배열과 열 단위로 행을 읽는 revMap 배열에 입력 값을 저장한다.
 * 2. 0 ~ N-1까지 모든 행과 모든 열을 검사한다. 이 때, 내리막 경사로와 오르막 경사로로 나누어서 검사한다.
 * 2-1. 내리막 경사로의 경우, 좌측 높이가 1 더 큰 경우에만 L 길이만큼 놓을 수 있는지 검사하고 가능하면 해당 구간을 isBuild 배열에 true로 저장한다.
 * 2-2. 오르막 경사로인 경우, 우측 높이가 1 더 큰 경우에만 L 길이만큼 놓을 수 있는지 검사한다. 이 때, 이미 경사로가 건설된 구간이면 false 반환
 * 2-3. 두 경우 모두 경사로를 건설할 수 있다면 h에 현재 높이를 갱신하고 L길이 만큼 스킵한다.(i +- L-1)
 * 2-4. 도중에 높이가 2 이상 차이나는 구간은 건설할 수 없으므로 false를 반환, 경사로를 건설하는 과정에서 높이가 같지 않거나 배열 인덱스를 벗어나는 경우에도 false를 반환한다.
 * 느낀 점: 경사로의 조건을 구현하기가 조금 까다로웠지만 몇 번 풀었던 문제라서 이전보다 빠르게 풀 수 있게 되었다.
 */
public class Boj_G3_14890_경사로 {
	static int[][] map, revMap;
	static int N, L;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		revMap = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				revMap[j][i] = map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int res = 0;
		for (int i = 0; i < N; i++) {
			if (isBuild(map[i])) {
				res++;
			}
			if (isBuild(revMap[i])) {
				res++;
			}
		}

		return res;
	}

	private static boolean isBuild(int[] map) {
		boolean[] isUsed = new boolean[N];
		int h = map[0];

		for (int i = 0; i < N; i++) {
			if (Math.abs(h - map[i]) > 1) {
				return false;
			}

			// 좌측 높이가 1 더 큰 경우
			// -> L길이의 경사로를 놓을 수 있는지 체크
			if (h > map[i]) {
				for (int j = i; j < i + L; j++) {
					if (j >= N || map[i] != map[j]) {
						return false;
					}
					isUsed[j] = true;
				}

				i += L - 1;
				h = map[i];
			} else {
				h = map[i];
			}
		}

		h = map[N - 1];
		for (int i = N - 1; i >= 0; i--) {
			if (Math.abs(h - map[i]) > 1) {
				return false;
			}

			// 우측 높이가 1 더 큰 경우
			// -> L길이의 경사로를 놓을 수 있는지 체크
			if (h > map[i]) {
				for (int j = i; j > i - L; j--) {
					if (j < 0 || map[i] != map[j] || isUsed[j]) {
						return false;
					}
				}

				i -= L - 1;
				h = map[i];
			} else {
				h = map[i];
			}
		}

		return true;
	}
}