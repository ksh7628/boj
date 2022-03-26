package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 26.
 * 
 * 분류: 구현
 * 난이도: 골드5
 * 소요 시간: 0h 14m
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 오븐을 순회해서 바로 밑 오븐의 지름이 더 크다면 윗 오븐의 지름값으로 저장한다.
 * 2. 오븐의 역순으로 탐색해서 입력받은 피자의 지름이 들어갈 수 있다면 해당 위치에 피자를 넣고 다음 탐색 시 해당 위치부터 역순으로 탐색
 * 3. 도중에 오븐의 깊이보다 남은 피자 개수가 더 많다면 탐색 중단한다.
 * 느낀 점: 
 * 처음 O(D*N)으로 풀었다가 시간초과가 났고 이분탐색으로 접근했었는데 이 문제의 경우에는 이분탐색을 사용할 필요없이
 * 반죽이 내려갈 수 있는지를 생각하면 풀 수 있는 문제였다.
 */
public class Boj_G5_1756_피자굽기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int D = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int[] oven = new int[D];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < D; i++) {
			oven[i] = Integer.parseInt(st.nextToken());
		}

		// 내림차순으로 맞춰준다
		// -> 밑이 더 커도 위의 크기에 영향을 받음
		for (int i = 0; i < D - 1; i++) {
			if (oven[i + 1] > oven[i]) {
				oven[i + 1] = oven[i];
			}
		}

		int res = D;
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int pizza = Integer.parseInt(st.nextToken());
			
			// 처음 D-1 깊이부터 역순으로 탐색
			// 피자가 들어갈 수 있는 깊이를 찾았다면 다음 탐색은 바로 윗부분부터 역순으로
			while (--res >= 0) {
				if (pizza <= oven[res]) {
					break;
				}
			}

			// 현재 피자가 쌓여있는 깊이보다 남아있는 피자 개수가 더 많으면 탐색 X
			if (res - (N - 1 - i) < 0) {
				res = -1;
				break;
			}
		}

		System.out.println(res + 1);
		br.close();
	}
}