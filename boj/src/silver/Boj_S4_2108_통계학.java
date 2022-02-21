package silver;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 21.
 * 
 * 분류: 수학, 구현, 정렬
 * 난이도: 실버4
 * 소요 시간: 0h 18m
 * 혼자 품: O
 * 풀이: 
 * 1. 산술평균: 평균을 구한 후 round 메소드로 소수점 첫째자리 반올림을 수행한다.
 * 2. 중앙값: 임시배열에 원본배열 복사 후 중앙 인덱스 N/2로 중앙값을 구한다.
 * 3. 최빈값: Number형 리스트를 cnt의 내림차순 정렬 후 제일 앞 두 객체를 비교 후 문제 조건에 맞는 최빈값을 구한다.
 * 3-1. 먼저 cnt배열에 음수도 포함해야 하므로 4000을 더해서 인덱싱하고 빈도값 카운팅
 * 3-2. cnt 배열 중 양수만 리스트에 Number 객체로 변환해서 추가
 * 3-3. 리스트의 첫번째 인덱스와 두번째 인덱스 cnt값을 비교하여 같다면 두번째 인덱스의 number 멤버변수 선택, 아니라면 첫번째 인덱스 선택
 * 4. 최댓값과 최솟값을 각각 구한다.
 * 느낀 점: 
 * 산술평균을 구할 때 float로 구했더니 오차가 있어서 오답이 나와서 long으로 선언했다.
 * 최빈값을 구할 때 카운팅 정렬을 따로 정의해서 푼다면 더 좋은 성능이 나올 것으로 기대된다.
 */
public class Boj_S4_2108_통계학 {
	static class Number implements Comparable<Number> {
		int number, cnt;

		public Number(int number, int cnt) {
			super();
			this.number = number;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Number o) {
			if (this.cnt == o.cnt) {
				return this.number - o.number;
			}
			return -Integer.compare(this.cnt, o.cnt);
		}
	}

	static int[] num;
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		num = new int[N];

		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(br.readLine());
		}

		solution();
		br.close();
	}

	private static void solution() {
		int avg = (int) getAvg();
		int mid = getMid();
		int maxFreq = getMaxFreq();
		int range = getRange();

		StringBuilder sb = new StringBuilder();
		sb.append(avg).append("\n").append(mid).append("\n").append(maxFreq).append("\n").append(range);
		System.out.print(sb);
	}

	/* 산술 평균 */
	private static long getAvg() {
		double avg = 0;

		for (int i = 0; i < N; i++) {
			avg += num[i];
		}

		avg /= N;
		return Math.round(avg);
	}

	/* 중앙값 */
	private static int getMid() {
		int[] arr = new int[N];
		System.arraycopy(num, 0, arr, 0, N);
		Arrays.sort(arr);
		return arr[N / 2];
	}

	/* 최빈값 */
	private static int getMaxFreq() {
		int[] cnt = new int[8001];
		for (int i = 0; i < N; i++) {
			cnt[num[i] + 4000]++;
		}

		ArrayList<Number> al = new ArrayList<>();
		for (int i = 0; i <= 8000; i++) {
			if (cnt[i] > 0) {
				al.add(new Number(i - 4000, cnt[i]));
			}
		}

		Collections.sort(al);
		if (al.size() == 1 || al.get(0).cnt > al.get(1).cnt) {
			return al.get(0).number;
		}
		return al.get(1).number;
	}

	/* 범위 */
	private static int getRange() {
		int max = -4001;
		int min = 4001;

		for (int i = 0; i < N; i++) {
			max = Math.max(max, num[i]);
			min = Math.min(min, num[i]);
		}

		return max - min;
	}
}