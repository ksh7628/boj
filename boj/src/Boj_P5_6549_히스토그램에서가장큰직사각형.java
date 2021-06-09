import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 9.
 *
 * 분류: 자료 구조, 세그먼트 트리, 분할 정복, 스택
 * 난이도: 플래티넘5
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 값들을 사용하기 위해 인덱스와 높이를 가지는 Histo 객체를 정의
 * 2.1. 반복문을 돌면서 스택이 비었거나 스택의 top에 있는 높이보다 입력받은 높이가 크거나 같다면 스택에 넣는다.
 * 2.2.1. 그렇지 않다면 스택의 top에 있는 높이가 입력받은 높이보다 작거나 같을 때까지 스택에 있는 값을 꺼내면서 반복한다.
 * 2.2.2. 스택에서 꺼낸값에서 인덱스를 왼쪽으로 이동하면서 넓이의 최대값을 갱신한다.
 * 2.2.3. 스택이 빈다면 마지막으로 꺼낸 값의 높이와 현재 입력받은 값의 인덱스를 곱해서 넓이를 다시 갱신한다.
 * 3. 2.1의 반복문이 끝나면 다시 스택이 빌때까지 값을 꺼내면서 2.1과정을 통해 넓이를 갱신한다.
 * 느낀 점: 
 * 인덱스 실수와 결과 값이 int형 범위를 초과 해버린다는 것을 나중에 알게되어서 그간 디버깅 하는데 애를 먹은 문제였다.
 * 스택으로 풀었으나 위의 분류처럼 어떤 알고리즘 또는 자료구조를 쓰느냐에 따라 다양한 풀이가 존재하는데 세그먼트 트리는 취준생에겐 좀 생소한 개념이다 보니
 * 이 문제랑 똑같은 문제인 https://www.acmicpc.net/problem/1725의 문제는 나중에 분할 정복을 사용하여 풀어봐야겠다.
 */
public class Boj_P5_6549_히스토그램에서가장큰직사각형 {
	static class Histo {
		int idx;// 인덱스
		long h;// 높이

		Histo(int idx, long h) {
			this.idx = idx;
			this.h = h;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int n = Integer.parseInt(st.nextToken());
			if (n == 0) {// 값 0이 입력되면 종료
				break;
			}

			Stack<Histo> s = new Stack<>();
			long max = 0;
			for (int i = 0; i < n; i++) {
				int h = Integer.parseInt(st.nextToken());
				if (s.isEmpty() || s.peek().h <= h) {// 스택이 비었거나 스택의 top에 있는 높이보다 크거나 같으면 push
					s.push(new Histo(i, h));
					continue;
				}
				while (h < s.peek().h) {// 입력받은 높이보다 스택의 top에 있는 높이가 작거나 같을 때까지 반복 
					Histo histo = s.pop();
					if (s.isEmpty()) {// 스택이 비어버린다면 방금 꺼낸 값의 높이*입력 받은 위치로 넓이 갱신
						max = Math.max(max, histo.h * i);
						// System.out.println(histo.h * i);
						break;
					} else {// 스택이 빌때까지 왼쪽으로 인덱스를 옮기면서 넓이 갱신
						max = Math.max(max, histo.h * (i - 1 - s.peek().idx));
						// System.out.println(histo.h * (i - 1 - s.peek().idx));
					}
				}
				s.push(new Histo(i, h));// 반복문이 종료되면 입력값을 스택에 넣어줌
			}

			// 스택에 있는 나머지 값들을 계산해줌
			while (!s.isEmpty()) {
				Histo histo = s.pop();
				if (s.isEmpty()) {
					max = Math.max(max, histo.h * n);
					// System.out.println(histo.h * n);
				} else {
					max = Math.max(max, histo.h * (n - 1 - s.peek().idx));
					// System.out.println(histo.h * (n - 1 - s.peek().idx));
				}
			}

			sb.append(max).append("\n");
		}
		System.out.println(sb);
		br.close();
	}
}