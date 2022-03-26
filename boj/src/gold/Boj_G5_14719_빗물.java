package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 26.
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드5
 * 소요 시간: 0h 38m
 * 혼자 품: O
 * 풀이: 
 * 1. 시작 인덱스와 현재 높이를 결정한다. -> 0이 아닌 부분을 찾을때까지 순회
 * 2. 시작점부터 끝까지 순회하면서 최대 높이와 최대 높이를 가지는 인덱스를 찾는다.
 * 2-1. 도중에 현재 높이보다 최대 높이가 더 크다면 더 볼 필요 X
 * 2-2. 최대 높이 갱신이 안됐다면 더 이상 빗물을 못받기 때문에 종료
 * 3. (시작 인덱스, 끝 인덱스) 구간에서 받을 수 있는 빗물의 합을 누적한다.
 * 4. 전부 살폈다면 종료하고 그렇지 않다면 현재 높이와 시작 인덱스를 갱신하고 2번으로 돌아간다.
 * 느낀 점: 처음에 스택을 이용해서 풀려고 했는데 올바른 방법이 생각나지 않아서 위의 풀이로 접근해서 품.
 */
public class Boj_G5_14719_빗물 {
	static int[] height;
	static int W;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		st.nextToken();
		W = Integer.parseInt(st.nextToken());
		height = new int[W + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < W; i++) {
			height[i] = Integer.parseInt(st.nextToken());
		}
		
		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int start = 0, h = 0, res = 0;
		// 출발점 결정
		// 높이 0이면 스킵
		for (int i = 0; i < W; i++) {
			if (height[i] > 0) {
				start = i;
				h = height[i];
				break;
			}
		}

		for (int i = start; i < W; i++) {
			int max = 0, end = 0;
			
			// 최대 높이를 찾는다
			for (int j = i + 1; j < W; j++) {
				if (max < height[j]) {
					end = j;
					max = height[j];
					
					// 최대 높이가 현재 높이보다 크다면 더 볼 필요가 없음
					if (h < max) {
						max = h;
						break;
					}
				}

			}

			// 최대 높이 갱신이 안됐다면 더 이상 빗물을 못받으므로 종료
			if (end == 0) {
				break;
			}

			// 받을 수 있는 빗물들의 합 계산
			for (int j = i + 1; j < end; j++) {
				res += max - height[j];
			}

			// 전부 살폈다면 종료
			if (end == W - 1) {
				break;
			}

			// 현재 높이 갱신, 시작 인덱스 갱신
			h = height[end];
			i = end - 1;
		}
		
		return res;
	}
}