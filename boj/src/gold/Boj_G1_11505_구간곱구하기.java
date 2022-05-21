package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 21.
 * 
 * 분류: 자료 구조, 세그먼트 트리
 * 난이도: 골드1
 * 소요 시간: 1h 00m
 * 혼자 품: X
 * 풀이: 
 * 1. 구간 곱을 저장하는 tree 배열을 선언해서 최초 구간 곱을 저장한다.
 * 2. a = 1일 경우, 세그먼트 트리의 수를 변경해서 구간 곱을 갱신한다.
 * 3. a = 2일 경우, 주어진 구간 곱이 저장된 인덱스를 찾아서 곱을 구한다.
 * 느낀 점: boj.kr/2042와 같은 문제로 합 대신 곱을 구하는 문제.
 */
public class Boj_G1_11505_구간곱구하기 {
	static long[] arr, tree;
	static final long MOD = 1000000007;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		arr = new long[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Long.parseLong(br.readLine());
		}

		int size = 1 << ((int) Math.ceil(Math.log(N) / Math.log(2)) + 1);
		tree = new long[size];

		init(1, 0, N - 1);

		for (int i = 0; i < M + K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long a = Long.parseLong(st.nextToken());
			long b = Long.parseLong(st.nextToken());
			long c = Long.parseLong(st.nextToken());

			if (a == 1) {
				update(1, 0, N - 1, b - 1, c);
			} else {
				sb.append(query(1, 0, N - 1, b - 1, c - 1)).append("\n");
			}
		}

		System.out.print(sb);
		br.close();
	}

	/* 세그먼트 트리 생성 */
	private static void init(int node, int start, int end) {
		if (start == end) {
			tree[node] = arr[start];
		} else {
			init(node * 2, start, (start + end) / 2);
			init(node * 2 + 1, (start + end) / 2 + 1, end);
			tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % MOD;
		}
	}

	/* 세그먼트 트리의 수를 변경해서 구간 곱을 갱신 */
	private static void update(int node, int start, int end, long idx, long val) {
		if (idx < start || idx > end) {
			return;
		}

		if (start == end) {
			arr[(int) idx] = val;
			tree[node] = val;
			return;
		}

		update(node * 2, start, (start + end) / 2, idx, val);
		update(node * 2 + 1, (start + end) / 2 + 1, end, idx, val);
		tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % MOD;
	}

	/* 세그먼트 트리에서 구간 곱을 구함 */
	private static long query(int node, int start, int end, long left, long right) {
		if (left > end || right < start) {
			return 1;
		}

		if (left <= start && end <= right) {
			return tree[node];
		}

		long leftMulti = query(node * 2, start, (start + end) / 2, left, right);
		long rightMulti = query(node * 2 + 1, (start + end) / 2 + 1, end, left, right);
		return (leftMulti * rightMulti) % MOD;
	}
}