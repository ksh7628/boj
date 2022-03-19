package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 19.
 * 
 * 분류: 수학, 그래프 이론, 그래프 탐색, 정수론, 너비 우선 탐색, 소수 판정, 에라토스테네스의 체
 * 난이도: 골드4
 * 소요 시간: 0h 40m
 * 혼자 품: O
 * 풀이:
 * 1. 에라토스테네스의 체로 10000미만의 소수를 최초 1번만 구한다. -> 테스트케이스마다 구할 필요 X
 * 2. bfs를 통해서 각 자릿수를 하나씩 변경해가며 최솟값을 구한다.
 * 느낀 점: 문제 조건을 잘못 보고 시간이 의외로 걸리게 된 문제였다. 그리고 약수의 성질을 이용한다면 더 성능이 개선될 것 같다.
 */
public class Boj_G4_1963_소수경로 {
	static boolean[] nonPrime = new boolean[10000];
	static int start, end;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		setPrime();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			start = Integer.parseInt(st.nextToken());
			end = Integer.parseInt(st.nextToken());
			sb.append(bfs()).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/* 소수 판별 세팅 -> 최초 1번만 */
	private static void setPrime() {
		for (int i = 2; i < 10000; i++) {
			for (int j = i * i; j < 10000; j += i) {
				nonPrime[j] = true;
			}
		}
	}

	/* bfs를 통해 변환의 최소 횟수를 찾는다 */
	private static String bfs() {
		boolean[] visit = new boolean[10000];
		ArrayDeque<Integer> q = new ArrayDeque<>();
		visit[start] = true;
		q.offer(start);

		int cnt = 0;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int k = 0; k < size; k++) {
				int cur = q.poll();
				if (cur == end) {
					return Integer.toString(cnt);
				}

				String str = Integer.toString(cur);
				StringBuilder sb = new StringBuilder();
				sb.append(cur);

				for (int i = 0; i < 4; i++) {
					for (char j = '0'; j <= '9'; j++) {
						char a = str.charAt(i);
						sb.setCharAt(i, j);
						if (a == 'j') {
							continue;
						}

						int nxt = Integer.parseInt(sb.toString());
						if (nxt >= 1000 && !nonPrime[nxt] && !visit[nxt]) {
							visit[nxt] = true;
							q.offer(nxt);
						}
						sb.setCharAt(i, a);
					}
				}
			}

			cnt++;
		}

		return "Impossible";
	}
}