

import java.io.*;
import java.util.*;

/**
 * <pre>
 * two_pointer 
 * Boj_G5_2470_두용액.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 15.
 * @version	: 0.1
 *
 * 분류: 이분 탐색, 두 포인터
 * 난이도: 골드4
 * 혼자 품: X
 * 풀이: 양 끝점에 포인터 변수를 하나씩 잡고 좁혀오면서 계산하는 방식으로 풀이하였다.
 *      처음에 정렬을 해준 후 두 수의 합이 0에 최대한 가까운 값이 정답이므로 절대값 함수를 사용했다.
 * 느낀 점: 다른 로직은 비슷했는데 정렬을 한다는 생각을 떠올리질 못해서 고민하다가 구글 검색을 통해 정렬을 시키면
 *        풀기 수월하다는 것을 알게 되었다.
 */
public class Boj_G5_2470_두용액 {
	static int[] acid;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		acid = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			acid[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(acid);
		twoPointer();
		br.close();
	}

	private static void twoPointer() {
		int min = Integer.MAX_VALUE, minA = 0, minB = 0;
		int start = 0, end = N - 1;
		while (start < end) {
			int sum = acid[start] + acid[end];
			if (Math.abs(sum) < min) {
				minA = acid[start];
				minB = acid[end];
				min = Math.abs(sum);
			}
			if (sum > 0) {
				end--;
			} else {
				start++;
			}
		}
		System.out.println(minA + " " + minB);
	}
}