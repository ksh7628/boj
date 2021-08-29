package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 25.
 *
 * 분류: 구현, 정렬, 시뮬레이션
 * 난이도: 골드4
 * 혼자 품: O
 * 풀이: 
 * 1. 3x3 배열에 입력값들을 저장한 후 매초마다 행의 크기와 열의 크기를 비교하여 행이 같거나 크다면 행 정렬, 열이 크다면 열 정렬을 수행한다.
 * 2. 두 정렬 모두 공통적인 부분은 배열의 행 단위면 행 단위로 정렬, 열 단위면 열 단위로 정렬을 한 후 숫자와 숫자에 해당하는 개수를 가지는 al 배열에 Number 객체로 저장한다.
 * 3. al 배열 중 가장 큰 길이의 두 배가 행 정렬에서는 열의 크기, 열 정렬에서는 행의 크기가 되고 100을 넘어가면 자동으로 크기가 100이 된다.
 * 4. 100을 넘지 않을 때까지 각 리스트의 길이만큼 Number 객체의 num과 cnt를 배열에 따로 저장한다.
 * 5. 마지막으로 열 정렬 같은 경우는 행 열을 반전시켜서 tmp에 저장을 하기 때문에 3번을 수행하기 전 열 크기를 행 크기로 재저장 해야한다.
 * 느낀 점: 
 * 문제는 이해가 어렵지 않았는데 정렬을 새롭게 구현하는데 있어서 고생을 한 문제였다. 매 연산마다 리스트를 따로 생성해서 원본 배열을 초기화시키고 리스트의 값들을 저장하는 식으로
 * 구현을 했었는데 다른 코드들을 보면서 여러 방법으로 구현을 할 수 있다는 것을 알게 되었다.
 */
public class Boj_G4_17140_이차원배열과연산 {
	static class Number implements Comparable<Number> {
		int num, cnt;

		public Number(int num, int cnt) {
			super();
			this.num = num;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Number o) {
			if (this.cnt == o.cnt) {// 등장 횟수가 같다면 숫자의 오름차순으로
				return this.num - o.num;
			}
			return this.cnt - o.cnt;// 등장 횟수의 오름차순으로
		}
	}

	static int[][] arr = new int[3][3];
	static int R = 3, C = 3, r, c, k;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		r = Integer.parseInt(st.nextToken())-1;
		c = Integer.parseInt(st.nextToken())-1;
		k = Integer.parseInt(st.nextToken());

		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 3; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(process());
		br.close();
	}

	/* 정렬 연산을 반복하다가 r,c위치에 k값이 되었다면 걸린 시간 반환, 100초가 넘으면 -1 반환 */
	private static int process() {
		int time = 0;
		while (time <= 100) {// 100초까지만 반복
			// r, c가 현재 배열 크기를 벗어나지 않고 arr[r][c]가 k값을 가진다면 걸린 시간 반환
			if (r < R && c < C && arr[r][c] == k) {
				return time;
			}

			if (R >= C) {// 행의 크기가 열의 크기보다 크거나 같다면 행마다 정렬
				rowSort();
			} else {// 아니라면 열마다 정렬
				colSort();
			}

			time++;
		}

		return -1;// 100초가 넘으면 -1 반환
	}

	/* 행 단위로 정렬 */
	@SuppressWarnings("unchecked")
	private static void rowSort() {
		ArrayList<Number>[] al = new ArrayList[R];
		for (int i = 0; i < R; i++) {
			al[i] = new ArrayList<>();
		}

		for (int i = 0; i < R; i++) {
			Arrays.sort(arr[i]);// arr 원소들을 정렬(일단 0도 포함되어 정렬됨)
			int num = arr[i][C - 1];// 제일 큰 숫자 저장
			int cnt = 0;// 해당 숫자 개수
			
			for (int j = C - 1; j >= 0; j--) {// 인덱스 역순으로 차례대로 반복(내림차순)
				if (arr[i][j] == 0) {// 0이면 뒤 원소들도 모두 0이므로 반복문 종료
					break;
				}

				if (num == arr[i][j]) {// 현재 숫자가 배열 값과 같다면 개수 증가
					cnt++;
				} else {// 아니라면 리스트에 현재 숫자와 해당 숫자의 개수를 저장하고 num과 cnt를 갱신
					al[i].add(new Number(num, cnt));
					num = arr[i][j];
					cnt = 1;
				}
			}
			
			al[i].add(new Number(num, cnt));// 열 탐색이 끝나면 저장하지 못한 num과 cnt를 다시 리스트에 저장
		}

		int max = 0;
		for (int i = 0; i < R; i++) {
			Collections.sort(al[i]);
			// 열 길이를 지정하기 위해 최댓값 갱신
			// 배열에는 숫자와 해당 숫자의 개수 모두를 저장해야 하므로 리스트 사이즈의 두배가 열 길이가 된다.
			max = Math.max(max, al[i].size() * 2);
		}

		if (C > 100) {// 100을 넘어가면 100까지만 저장
			C = 100;
		} else {
			C = max;
		}
		
		arr = new int[R][C];
		for (int i = 0; i < R; i++) {
			int idx = 0;
			for (Number n : al[i]) {// 정렬된 리스트 값들을 배열에 차례로 저장
				if (idx >= 100) {// 열 인덱스가 100이상이면 중단
					break;
				}
				arr[i][idx++] = n.num;
				arr[i][idx++] = n.cnt;
			}
		}
	}

	/* 열 단위로 정렬 */
	@SuppressWarnings("unchecked")
	private static void colSort() {
		// 열 단위로 정렬을 하기 때문에 R,C 값을 서로 바꿔줌
		int swap = R;
		R = C;
		C = swap;

		int[][] tmp = new int[R][C];// 새로운 배열 tmp에 바뀐 R,C로 배열 크기 설정
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				tmp[i][j] = arr[j][i];// 행,열을 반전시켜서 저장
			}
		}

		ArrayList<Number>[] al = new ArrayList[R];
		for (int i = 0; i < R; i++) {
			al[i] = new ArrayList<>();
		}

		for (int i = 0; i < R; i++) {
			Arrays.sort(tmp[i]);
			int num = tmp[i][C - 1];
			int cnt = 0;
			
			for (int j = C - 1; j >= 0; j--) {
				if (tmp[i][j] == 0) {
					break;
				}

				if (num == tmp[i][j]) {
					cnt++;
				} else {
					al[i].add(new Number(num, cnt));
					num = tmp[i][j];
					cnt = 1;
				}
			}
			
			al[i].add(new Number(num, cnt));
		}

		int max = 0;
		C = R;// 원본 배열에 정렬된 값들을 저장하기 위해 열 크기를 행 크기로 저장
		for (int i = 0; i < C; i++) {
			Collections.sort(al[i]);
			max = Math.max(max, al[i].size() * 2);
		}

		if (R > 100) {
			R = 100;
		} else {
			R = max;
		}
		
		arr = new int[R][C];
		for (int j = 0; j < C; j++) {
			int idx = 0;
			for (Number n : al[j]) {
				if (idx >= 100) {
					break;
				}
				arr[idx++][j] = n.num;
				arr[idx++][j] = n.cnt;
			}
		}
	}
}