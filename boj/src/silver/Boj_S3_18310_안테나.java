package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 5. 3.
 * 
 * 분류: 수학, 그리디 알고리즘, 정렬
 * 난이도: 실버3
 * 소요 시간: 0h 06m
 * 혼자 품: O
 * 풀이: 위치 값들을 정렬한 후, 중앙 인덱스 값을 구해서 중앙 위치 값을 구한다. 짝수인 경우에는 중앙 인덱스 - 1을 해준다.
 * 느낀 점: 중앙 값을 그리디하게 구할 수 있다.
 */
public class Boj_S3_18310_안테나 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);
		System.out.println(arr[N % 2 == 0 ? N / 2 - 1 : N / 2]);
		br.close();
	}
}