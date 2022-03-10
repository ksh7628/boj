package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 3. 10.
 * 
 * 분류: 자료 구조, 이분 탐색, 중간에서 만나기
 * 난이도: 골드4
 * 소요 시간: 0h 25m
 * 혼자 품: O
 * 풀이: 
 * 1. 입력받은 수들을 배열에 저장한 후 정렬을 한다.
 * 2. 배열에서 두 수의 합을 모두 구해서 리스트에 저장한 후 리스트를 정렬한다.
 * -> pairSum(arr[a]+arr[b]) + arr[c] = arr[d]가 나올 수 있도록 하기 위해서..
 * 3. 배열을 끝에서부터 탐색하면서 arr[d] - arr[c] 값이 리스트에서 이진탐색으로 찾아진다면 해당 값이 최댓값이다.
 * -> 배열을 끝에서부터 탐색하기 때문에 최초에 발견된 값이 최댓값
 * -> O(N^2(logN))에 수행 가능해진다.
 * 느낀 점: 이분 탐색 응용이 방대하다는 것을 알게 되었다.
 */
public class Boj_G4_2295_세수의합 {
	static List<Integer> pairSum = new ArrayList<>();
	static int[] arr;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		Arrays.sort(arr);
		for (int i = 0; i < N; i++) {
			for (int j = i; j < N; j++) {
				pairSum.add(arr[i] + arr[j]);
			}
		}

		Collections.sort(pairSum);
		System.out.println(binarySearch());
		br.close();
	}

	private static int binarySearch() {
		for (int i = N - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				// pairSum + arr[j] = arr[i]가 되는 값을 찾음
				if (Collections.binarySearch(pairSum, arr[i] - arr[j]) >= 0) {
					return arr[i];
				}
			}
		}
		
		// 의미 없는 값(문제에서 항상 답이 존재하는 경우만 입력으로 주어진다고 함)
		return -1;
	}
}