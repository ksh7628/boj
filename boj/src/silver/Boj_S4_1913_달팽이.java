package silver;

import java.io.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 28.
 * 
 * 분류: 구현
 * 난이도: 실버4
 * 소요 시간: 0h 12m
 * 혼자 품: O
 * 풀이: 북동남서 순으로 달팽이 모양을 배열에 저장. 방향이 두번 바뀔 때마다 길이를 1씩 증가시킨다.
 * 느낀 점: 10분안에 달팽이를 풀려고 했는데 초기값을 잘못 설정해서 시간을 조금 더 쓰게 되었다. 꼼꼼한 구현력을 기르자.
 */
public class Boj_S4_1913_달팽이 {
	static int[][] arr;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static int N, target;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		target = Integer.parseInt(br.readLine());
		solution();
		br.close();
	}

	private static void solution() {
		arr = new int[N][N];
		int x = N / 2, y = N / 2;
		int d = 0, len = 1, num = 0;
		int resX = 0, resY = 0;
		arr[x][y] = ++num;

		loop: while (true) {
			for (int i = 0; i < len; i++) {
				if (num == target) {
					resX = x + 1;
					resY = y + 1;
				}
				
				if (num == N * N) {
					break loop;
				}

				x += dx[d];
				y += dy[d];
				arr[x][y] = ++num;


			}

			if (++d % 2 == 0) {
				len++;
			}

			if (d == 4) {
				d = 0;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sb.append(arr[i][j]).append(" ");
			}
			sb.append("\n");
		}
		sb.append(resX).append(" ").append(resY);
		System.out.print(sb);
	}
}