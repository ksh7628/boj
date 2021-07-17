import java.io.*;
import java.util.StringTokenizer;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 7. 17.
 *
 * 분류: 이분 탐색, 매개 변수 탐색
 * 난이도: 실버3
 * 혼자 품: X
 * 풀이: 
 * 1. 랜선 길이들을 배열에 저장하고 while문을 통해 이분 탐색을 시작한다.
 * 2. mid값을 정하고 for문을 통해 배열을 순회하면서 sum에 각 배열값을 mid로 나눈 값들을 더한다.
 * 3. 랜선의 길이가 N보다 작다면 high를 갱신하고 그렇지 않으면 low를 갱신한다.
 * 4. 최댓값을 찾아야 하기 때문에 low > high가 될 때까지 반복한다.
 * 느낀 점: 
 * 나무 자르기(https://www.acmicpc.net/problem/2805) 문제랑 똑같은 방식으로 접근했는데 조건을 별 신경 쓰지 않다가 계속 틀려서
 * 결국 다른 사람의 코드를 참조하게 됐는데 자연수인 것을 간과하지 못해서 low를 1로 했더니 맞게 되었다. 항상 문제 지문을 잘 읽는 습관을 가지도록 해야겠다.
 */
public class Boj_S3_1654_랜선자르기 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int K = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		int[] arr = new int[K];
		for (int i = 0; i < K; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		long low = 1, high = Integer.MAX_VALUE;// 랜선의 길이가 자연수이므로 최솟값은 1
		while (low <= high) {
			long mid = (low + high) / 2;
			int sum = 0;
			for (int i = 0; i < K; i++) {
				sum += arr[i] / mid;
			}

			if (sum < N) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}

		System.out.println(high);
		br.close();
	}
}