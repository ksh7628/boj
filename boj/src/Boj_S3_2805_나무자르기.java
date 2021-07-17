import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 7. 16.
 *
 * 분류: 이분 탐색
 * 난이도: 실버3
 * 혼자 품: X
 * 풀이: 
 * 1. 나무의 높이들을 배열에 저장한 후 while문을 통해 이분 탐색을 시작한다.
 * 2. 절단기 높이를 저장한 후 모든 배열 값을 순회하여 절단기 높이보다 크다면 두 값의 차를 sum에 더하여 누적시킨다.
 * 3. M보다 크거나 같다면 low에 절단기 높이를 저장하고 그렇지 않으면 high에 절단기 높이를 저장한다.
 * 4. low + 1 < high를 만족하는 한 계속 반복하여 low의 최댓값을 구한다.
 * 느낀 점: 
 * 이분 탐색 자체 원리는 잘 알고 있다고 생각했는데 well-known 문제였지만 처음 풀어보는 방식이라서 풀지 못하고 다른 사람의 코드를 보게 되었다.
 * 이분 탐색도 무궁무진하게 응용이 가능한 알고리즘이고 코딩 테스트에도 충분히 나올 수 있는 개념이기 때문에 여러 문제를 풀면서 감을 익혀야겠다.
 */
public class Boj_S3_2805_나무자르기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int low = 0, high = 1000000000;
		// 이분 탐색 시작
		while (low + 1 < high) {
			int middle = (low + high) / 2;// 절단기 높이 설정
			long sum = 0;

			for (int i = 0; i < N; i++) {
				if (arr[i] > middle) {// 절단기 높이보다 큰 나무라면 자르고 남은 양 저장
					sum += arr[i] - middle;
				}
			}

			// 자르고 남은 나무의 합의 양을 M과 비교하여 low나 high 재설정
			if (sum >= M) {
				low = middle;
			} else {
				high = middle;
			}
		}
		
		System.out.println(low);
		br.close();
	}
}