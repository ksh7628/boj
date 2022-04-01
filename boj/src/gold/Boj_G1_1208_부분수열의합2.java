package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 1.
 * 
 * 분류: 이분 탐색, 중간에서 만나기
 * 난이도: 골드1
 * 소요 시간: 0h 19m
 * 혼자 품: O
 * 풀이: 
 * middle in the middle로 풀이.
 * 0 ~ N/2-1 구간에서 map에 부분수열 합 등장횟수를 저장하고 N/2 ~ N-1 구간에서 구한 합을 S에서 뺀 값이 map에 있다면 해당 경우의 수를 누적한다.
 * 느낀 점: 처음 사용하는 알고리즘이었는데 개념을 조금이나마 익혔다고 느낌.
 */
public class Boj_G1_1208_부분수열의합2 {
	static Map<Integer, Integer> map = new HashMap<>();
	static int[] num;
	static int N, S, sum;
	static long res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		num = new int[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}

		subset1(0);
		subset2(N / 2);
		System.out.println(S == 0 ? res - 1 : res);
		br.close();
	}

	/* 0 ~ N/2-1 까지의 부분수열 합과 합 등장 개수를 map에 저장 */
	private static void subset1(int cnt) {
		if (cnt == N / 2) {
			map.put(sum, map.getOrDefault(sum, 0) + 1);
			return;
		}

		subset1(cnt + 1);
		sum += num[cnt];
		subset1(cnt + 1);
		sum -= num[cnt];
	}

	/* N/2 ~ N-1까지의 부분수열 합을 구하고 S에서 sum을 뺀 값이 맵에 존재하면 해당 value만큼 경우의 수 누적 */
	private static void subset2(int cnt) {
		if (cnt == N) {
			if (map.containsKey(S - sum)) {
				res += map.get(S - sum);
			}
			return;
		}

		subset2(cnt + 1);
		sum += num[cnt];
		subset2(cnt + 1);
		sum -= num[cnt];
	}
}