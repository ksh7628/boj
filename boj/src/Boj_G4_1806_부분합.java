import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 두 포인터
 * 난이도: 골드4
 * 소요 시간: 0h 13m
 * 혼자 품: O
 * 풀이: 
 * 1. s나 e가 N이 되기 전까지 반복문을 수행하면서 s와 e가 같다면 sum에 arr[s]를 저장시킨다.
 * 2. sum이 S보다 크거나 같으면 최소 길이를 갱신하고 현재 합한 수에서 arr[s]를 빼주고 s를 1 증가시킴
 * 3. 그렇지 않다면 e를 증가시키고 arr[e]를 sum에 더해줌
 * 느낀 점: 
 * https://www.acmicpc.net/problem/2230(수 고르기)문제와 접근 방식은 똑같고 합을 수정해주는 부분만 주의하여 어렵지 않게 풀었다.
 * 실전 코딩 테스트에서 투 포인터 알고리즘으로 풀 수 있는 문제인지 판별하는 능력을 길러야 써먹을 수 있을 것 같다.
 */
public class Boj_G4_1806_부분합 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());

		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int s = 0, e = 0, sum = 0;
		int min = Integer.MAX_VALUE;
		while (s != N) {
			if (s == e) {// s와 e가 같다면 합은 해당 위치의 수 자체가 됨
				sum = arr[s];
			}

			if (sum >= S) {// sum이 S보다 크거나 같다면
				min = Math.min(min, e - s + 1);// 최소 길이 갱신
				sum -= arr[s];// s를 증가시키기 위해 현재 위치의 수를 합에서 빼줌
				s++;
			} else {// sum이 S보다 작다면
				e++;
				if (e != N) {
					sum += arr[e];// 포인터를 하나 증가시키고 증가시킨 위치의 수를 sum에 더해줌
				} else {// e가 N이 된다면 반복문 종료
					break;
				}
			}
		}

		System.out.println(min == Integer.MAX_VALUE ? 0 : min);
		br.close();
	}
}