package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 4. 20.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 실버4
 * 소요 시간: 0h 22m
 * 혼자 품: O
 * 풀이: 
 * 1. 초기에 0,0 좌표를 리스트에 저장한 후 명령어중 F일 경우에만 좌표를 수정해서 리스트에 넣고 그렇지 않으면 방향만 수정한다.
 * 2. 그 후 리스트를 행 오름차순 -> 열 오름차순 으로 정렬한 후 리스트에서 y좌표의 최솟값과 최댓값을 구한다.
 * 3. 2중 for문을 수행하면서 x의 최솟값 ~ 최댓값, y의 최솟값 ~ 최댓값 까지 수행하면서 리스트의 좌표랑 동일하다면 .를, 아니라면 #을 출력한다.
 * 3-1. 리스트에 동일한 좌표가 있을 수 있기 때문에 동일한 좌표는 최초 한번만 표시한다.
 * 느낀 점: 
 * 약간 어렵게 생각해서 리스트에 정렬까지 써서 풀었지만 다른 사람의 코드를 보니 입력 제한이 작은 문제라서 크기 100x100 배열을 잡고
 * 배열에 표시하는 풀이가 더 나은것 같다.
 */
public class Boj_S4_1347_미로만들기 {
	static class Pair implements Comparable<Pair> {
		int x, y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Pair o) {
			if (this.x == o.x) {
				return Integer.compare(this.y, o.y);
			}
			return Integer.compare(this.x, o.x);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int len = Integer.parseInt(br.readLine());
		String str = br.readLine();
		List<Pair> list = new ArrayList<>();

		int[] dx = { 1, 0, -1, 0 };
		int[] dy = { 0, -1, 0, 1 };
		int x = 0, y = 0, d = 0;
		list.add(new Pair(x, y));

		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (c == 'F') {
				x += dx[d];
				y += dy[d];
				list.add(new Pair(x, y));
				continue;
			}

			if (c == 'L') {
				d = (d + 3) % 4;
			} else {
				d = (d + 1) % 4;
			}
		}

		Collections.sort(list);
		int sx = list.get(0).x, sy = Integer.MAX_VALUE;
		int ex = list.get(list.size() - 1).x, ey = Integer.MIN_VALUE;
		for (Pair p : list) {
			sy = Math.min(sy, p.y);
			ey = Math.max(ey, p.y);
		}

		StringBuilder sb = new StringBuilder();
		int idx = 0;
		for (int i = sx; i <= ex; i++) {
			for (int j = sy; j <= ey; j++) {
				if (idx < list.size() && list.get(idx).x == i && list.get(idx).y == j) {
					sb.append(".");
					int nxt = idx;
					while (++nxt < list.size()) {
						if (list.get(idx).x == list.get(nxt).x && list.get(idx).y == list.get(nxt).y) {
							idx++;
						}
					}
					idx++;
					
				} else {
					sb.append("#");
				}
			}
			sb.append("\n");
		}

		System.out.print(sb);
		br.close();
	}
}