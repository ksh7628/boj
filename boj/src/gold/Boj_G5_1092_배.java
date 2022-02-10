package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 10.
 * 
 * 분류: 정렬, 그리디 알고리즘
 * 난이도: 골드5
 * 소요 시간: 1h 08m
 * 혼자 품: O
 * 풀이: 
 * 1. 크레인 배열과 박스 리스트를 내림차순 정렬한다.
 * 2. 중량이 가장 큰 크레인보다 더 큰 박스가 있다면 옮길 수 없으므로 -1을 리턴한다.
 * 3. 그렇지 않다면 모두 옮길 수 있으므로 리스트 인덱스와 배열 인덱스를 따로 선언해서 순차적으로 검사한다.
 * 느낀 점: 
 * 처음에는 우선순위 큐로 접근했는데 시간초과가 나서 박스의 수가 최대 10000이기 때문에
 * ArrayList의 remove 메소드를 사용해도 시간안에 돌아가므로 ArrayList를 사용해서 풀었다.
 * remove 메소드 사용을 기피했었는데 N이 크지 않다면 이 방법이 좋은 것 같다.
 * 또한 이분탐색으로 접근한다면 더 빨리 풀 수 있겠지만 아직 이분탐색이 익숙하지 않아서 나중에 해당 방법으로 풀어봐야겠다.
 */
public class Boj_G5_1092_배 {
	static ArrayList<Integer> box = new ArrayList<>();
	static Integer[] arr;
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new Integer[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			box.add(Integer.parseInt(st.nextToken()));
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		// 내림차순
		Arrays.sort(arr, Collections.reverseOrder());
		// Integer 배열을 int 배열로 형변환 후 내림차순 정렬
		int[] crane = Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
		Collections.sort(box, Collections.reverseOrder());

		// 최대 중량 크레인보다 더 무거운 박스는 옮길 수 없음
		if (crane[0] < box.get(0)) {
			return -1;
		}

		int res = 0;
		while (!box.isEmpty()) {
			res++;

			// i: 크레인 배열 접근 인덱스, j: 박스 리스트 접근 인덱스
			for (int i = 0, j = 0; i < N && j < box.size();) {
				// 현재 크레인이 현재 박스를 옮길 수 있다면 해당 박스 제거
				if (crane[i] >= box.get(j)) {
					box.remove(j);
					i++;
				} else {// 그렇지 않다면 다음 박스 검사
					j++;
				}
			}
		}

		return res;
	}
}