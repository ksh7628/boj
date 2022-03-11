package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 11.
 * 
 * 분류: 그리디 알고리즘, 정렬
 * 난이도: 실버1
 * 소요 시간: 0h 11m
 * 혼자 품: O
 * 풀이: 
 * 입력값들을 배열에 저장한 후 정렬해서 큰값부터 배열 크기의 절반까지 결과값에 2를 곱해서 더해준다.
 * -> 문제에서 "묶음이 짝수인 경우 묶음 개수/2 + 1번째 값을 중앙값으로 정의".
 * -> 배열의 양 끝에 있는 두 수가 중앙으로 점점 모이면서 짝을 지으면 중앙값들의 합을 최대로 할 수 있음.
 * 홀수의 경우, 마지막 가운데 값이 홀로 묶음이 되므로 가운데 값을 다시 빼줌.
 * 느낀 점: 증명은 어려울 수 있으나 코테 수준에서 확신할 수 있는 방법이라고 생각하고 품.
 */
public class Boj_S1_20117_호반우상인의이상한품질계산법 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);
		int res = 0;
		for (int i = N - 1; i >= N / 2; i--) {
			res += arr[i] * 2;
		}

		if (N % 2 == 1) {
			res -= arr[N / 2];
		}

		System.out.println(res);
		br.close();
	}
}