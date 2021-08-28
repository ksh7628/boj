import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 이분 탐색, 두 포인터
 * 난이도: 골드5
 * 소요 시간: 0h 20m
 * 혼자 품: X
 * 풀이: 
 * 1. 배열의 시작 인덱스와 끝 인덱스를 각각 포인터로 잡아서 두 수의 합의 절댓값이 최솟값보다 작다면 최솟값을 절댓값으로 갱신하고 두 수를 저장한다.
 * 2. 두 수의 합이 0보다 크다면 끝 인덱스를 1 감소시키고 그렇지 않다면 시작 인덱스를 1 증가시켜 s < e를 만족하는 한 반복한다.
 * 느낀 점: 
 * 이전에 푼 https://www.acmicpc.net/problem/2470(두 용액) 문제와 똑같은데도 푼지 오래되서 그런지 감을 잃어서
 * 결국 2470 풀이를 보고 풀게 되었다. 투 포인터를 적용하는 문제는 정렬 시킬 수 있는지도 생각해 보고 시작점에서 같이 시작하는 경우와 반대 점에서 각각 시작하는 경우를
 * 생각하여 풀어야겠다.
 */
public class Boj_G5_2467_용액 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);
		int s = 0, e = N - 1, res1 = 0, res2 = 0;
		int min = Integer.MAX_VALUE;

		while (s < e) {
			int sum = arr[s] + arr[e];
			if (Math.abs(sum) < min) {
				min = Math.abs(sum);
				res1 = Math.min(arr[s], arr[e]);
				res2 = Math.max(arr[s], arr[e]);
			}

			if (sum > 0) {
				e--;
			} else {
				s++;
			}
		}

		System.out.println(res1 + " " + res2);
		br.close();
	}
}