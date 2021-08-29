package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 정렬, 투 포인터
 * 난이도: 골드5
 * 소요 시간: 0h 23m
 * 혼자 품: O
 * 풀이:
 * 1. 두 개의 포인터 s, e를 사용하여 arr[s]와 arr[e]의 차이가 M보다 크거나 같으면 최솟값을 갱신하고 s값을 1 증가시킨다.
 * 2. 두 수의 차가 M보다 작다면 e값을 1 증가시키면서 s나 e값이 N이 되기 전까지 반복한다.
 * 느낀 점: 
 * 정렬을 해주면 e가 먼저 N-1이 되고나서 s값을 증가시켜도 값의 차이가 점점 감소하기 때문에 while문의 조건을 s!=N&&e!=N로 주었다.
 * 최근 투 포인터 알고리즘과 윈도우 슬라이딩 알고리즘이 코딩테스트에 나오는 빈도가 증가했기 때문에 기초 문제를 통해 투 포인터를 알아가게 되었다.
 */
public class Boj_G5_2230_수고르기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		Arrays.sort(arr);
		int s = 0, e = 0, min = Integer.MAX_VALUE;
		while (s != N && e != N) {
			int dis = Math.abs(arr[s] - arr[e]);
			if (dis >= M) {
				min = Math.min(min, dis);
				s++;
			} else {
				e++;
			}
		}

		System.out.println(min);
		br.close();
	}
}