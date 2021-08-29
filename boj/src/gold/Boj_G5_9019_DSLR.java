package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 6.
 *
 * 분류: 그래프 이론, 그래프 탐색, 너비 우선 탐색
 * 난이도: 골드5
 * 혼자 품: O
 * 풀이: bfs + 최단거리 역추적 형식의 문제로 bfs를 통해 값들을 계속 확장해 가면서 값이 변환되는 과정을 배열에 따로 저장한 후
 *      결과값이 만들어지면 경로 복원을 위해 스택을 사용하여 풀이하였다.
 * 느낀 점: 전에 경로를 복원하는 알고리즘 문제 몇개를 풀고 이문제를 풀어보니 어렵진 않게 풀게 되었다. 그런데 더 좋은 방법은 스택을 굳이 사용하지 않아도
 *        경로를 출력하는 문제라면 StringBuilder 클래스에 있는 reverse() 메서드를 사용하면 된다는 것을 알게 되었다.
 */
public class Boj_G5_9019_DSLR {
	static StringBuilder sb = new StringBuilder();
	static char[] cmd = { 'D', 'S', 'L', 'R' };// 명령어 배열
	static char[] trace;// 경로 복원을 위한 배열
	static int[] npos;
	static boolean[] used;
	static int A, B;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			A = Integer.parseInt(st.nextToken());// 초기값
			B = Integer.parseInt(st.nextToken());// 만들어야 되는 값
			bfs();
			pathRestore();
		}
		System.out.println(sb);
		br.close();
	}

	/* 결과값을 만들 때까지 bfs */
	private static void bfs() {
		trace = new char[10000];
		npos = new int[10000];
		used = new boolean[10000];
		ArrayDeque<Integer> q = new ArrayDeque<>();
		used[A] = true;
		q.offer(A);
		while (!q.isEmpty()) {
			int num = q.poll();
			if (num == B) {
				return;
			}
			for (int i = 0; i < 4; i++) {
				int nxt = transValue(num, cmd[i]);
				if (used[nxt]) {
					continue;
				}
				trace[nxt] = cmd[i];
				npos[nxt] = num;
				used[nxt] = true;
				q.offer(nxt);
			}
		}
	}

	/* 각 명령어에 따라 다른 방식으로 값 변환 */
	private static int transValue(int n, char c) {
		int res = -1;
		switch (c) {
		case 'D':// 2배 연산
			if (2 * n > 9999) {
				res = 2 * n % 10000;
			} else {
				res = 2 * n;
			}
			break;
		case 'S':// 빼기 1 연산
			if (n == 0) {
				res = 9999;
			} else {
				res = n - 1;
			}
			break;
		case 'L':// << 연산
			res = n % 1000 * 10 + n / 1000;
			break;
		case 'R':// >> 연산
			res = n / 10 + n % 10 * 1000;
			break;
		}
		return res;
	}

	/* 경로 복원 */
	private static void pathRestore() {
		Stack<Character> s = new Stack<>();
		int pos = B;
		while (pos != A) {
			s.push(trace[pos]);
			pos = npos[pos];
		}

		while (!s.isEmpty()) {
			sb.append(s.pop());
		}
		sb.append("\n");
	}
}