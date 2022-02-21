package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 21.
 * 
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드3
 * 소요 시간: 1시간 30분 이상(time over)
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 숫자의 모든 자릿수 중 두 개의 수를 교환해야한다. -> 완전 탐색
 * 2. 각 depth마다 set을 초기화 해줘야 한다. -> depth마다 수행하기 위해 bfs사용
 * 3. 첫자리가 0으로 시작하는 경우는 제외한다.
 * 4. 마지막 K번째 연산은 최댓값을 갱신한다. 단, bfs 수행 도중 큐가 빈다면 -1
 * 느낀 점: 
 * 보통 여태까지 푼 dfs, bfs문제는 처음 시작하는 수를 큐에 넣음과 동시에 방문처리를 해줬었는데
 * 예로 100을 swap 후 bfs처리 할 때, 두번째 0과 세번째 0을 swap하면 그대로 100이라서 큐에 넣어줘야 한다.
 * 따라서 시작 전에는 set에 넣지않고 풀어야 한다는 것을 알게 된 문제, 이 부분을 찾지 못해 데드라인인 1시간 반 이상을 사용한 문제였다.
 * 그래프 유형은 어느 기업이든 나올 수 있는 문제이므로 설계를 확실히 해야 한다.
 */
public class Boj_G3_1039_교환 {
	static ArrayDeque<String> q = new ArrayDeque<>();
	static Set<String> set;
	static char[] num;
	static int N, K, M, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		String str = Integer.toString(N);
		M = str.length();

		// 처음에는 set에 넣지 않음 -> 방문체크 하지 않음
		q.offer(str);
		bfs();

		System.out.println(res);
		br.close();
	}

	/* bfs를 통해 depth마다 set 초기화 */
	private static void bfs() {
		for (int i = 1; i <= K; i++) {
			set = new HashSet<>();
			int size = q.size();

			for (int j = 0; j < size; j++) {
				String str = q.poll();

				// swap 연산 후 bfs 수행
				for (int x = 0; x < M; x++) {
					for (int y = x + 1; y < M; y++) {
						num = str.toCharArray();
						char tmp = num[x];
						num[x] = num[y];
						num[y] = tmp;

						String nxtStr = new String(num);

						// 연산된 수가 중복되지 않고 첫자리가 0으로 시작안해야 한다
						if (!set.contains(nxtStr) && num[0] != '0') {
							set.add(nxtStr);
							q.offer(nxtStr);

							// K번째 연산이 되면 최댓값 갱신
							if (i == K) {
								res = Math.max(res, Integer.parseInt(nxtStr));
							}
						}
					}
				}
			}
			
			// 연산 도중 큐가 빈다면 -1
			if (q.isEmpty()) {
				res = -1;
				break;
			}
		}
	}
}