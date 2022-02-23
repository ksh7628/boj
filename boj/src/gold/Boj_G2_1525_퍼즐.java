package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 23.
 * 
 * 분류: 그래프 이론, 자료 구조, 그래프 탐색
 * 난이도: 골드2
 * 소요 시간: 1h 13m
 * 혼자 품: O
 * 풀이: 
 * 1. 입력을 받을 때 공백을 제거한 하나의 문자열로 저장한다.
 * 2. 현재 0의 위치를 pos에 저장하고 4방향을 살펴서 빈칸과 바꿀 수 있는지 확인한다.
 * 3. 문자열에서 문자 위치를 서로 바꾸기 위해 StringBuilder를 통해 문자 위치를 바꾼다.
 * 4. 새로 만들어진 문자열이 이전에 방문했던 상태가 아니라면 bfs를 반복한다.
 * 느낀 점: 
 * 처음에는 2차원 배열을 큐에 담아서 bfs를 돌렸는데 메모리 초과가 나와서 문자열로 변환시켜서 풀게 되었다.
 * 단순히 좌표 방문이 아닌 현재 상태를 어떻게 표현할 수 있는지가 관건이었던 문제였다.
 */
public class Boj_G2_1525_퍼즐 {
	static int[] direction = { -3, 3, -1, 1 };
	static final String res = "123456780";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = "";

		for (int i = 0; i < 3; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 3; j++) {
				str += st.nextToken();
			}
		}

		System.out.println(bfs(str));
		br.close();
	}

	/* bfs를 통해 최소 이동 거리를 반환한다 */
	private static int bfs(String str) {
		Set<String> set = new HashSet<>();
		ArrayDeque<String> q = new ArrayDeque<>();
		int cnt = 0;

		set.add(str);
		q.offer(str);

		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				String curStr = q.poll();

				// 모양이 일치한다면 거리를 반환한다
				if (curStr.equals(res)) {
					return cnt;
				}

				int pos = 0;
				for (int j = 0; j < curStr.length(); j++) {
					if (curStr.charAt(j) == '0') {
						pos = j;
						break;
					}
				}

				for (int d = 0; d < 4; d++) {
					int npos = pos + direction[d];

					// 왼쪽 벗어남
					if (d == 2 && (pos == 3 || pos == 6)) {
						continue;
					}

					// 오른쪽 벗어남
					if (d == 3 && (pos == 2 || pos == 5)) {
						continue;
					}

					if (npos < 0 || npos >= 9) {
						continue;
					}

					// 문자 swap을 위해 StringBuilder 사용
					StringBuilder sb = new StringBuilder();
					sb.append(curStr);

					char tmp = curStr.charAt(pos);
					sb.setCharAt(pos, curStr.charAt(npos));
					sb.setCharAt(npos, tmp);

					String nxtStr = sb.toString();
					if (!set.contains(nxtStr)) {
						set.add(nxtStr);
						q.offer(nxtStr);
					}
				}
			}

			cnt++;
		}

		return -1;
	}
}