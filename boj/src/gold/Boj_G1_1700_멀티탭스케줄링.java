package gold;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 25.
 * 
 * 분류: 그리디 알고리즘
 * 난이도: 골드1
 * 소요 시간: 1h 15m
 * 혼자 품: O
 * 풀이: 
 * 1. 멀티탭 공간이 없을 때까지 전기용품 번호를 저장한 p배열을 순서대로 multiTap에 넣는다.
 * 2. 더 이상 공간이 없을 경우 현재 번호부터 마지막 전기용품 번호까지 탐색해서 남은 순서의 최솟값을 map에 저장한다.
 * 3-1. multiTap을 순회하면서 더 이상 사용하지 않는 번호가 있다면 즉시 갱신하고 순회를 종료한다.
 * 3-2. 전부 나중에 사용한다면 그 중 다음으로 사용하는 기간이 가장 긴 번호를 갱신한다.
 * 4. 3에서 찾은 번호와 현재 꽂을 번호를 멀티탭에서 교체한다.
 * 느낀 점: 
 * 로직은 찾았는데 적합한 자료구조를 생각하느라 시간이 걸린 문제였다.
 * 골드1 문제답게 결코 쉽진 않았지만 그리디한 방법을 배워가는 기회가 된 좋은 문제라고 생각한다.
 */
public class Boj_G1_1700_멀티탭스케줄링 {
	static int[] p;
	static int N, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		p = new int[K];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			int idx = Integer.parseInt(st.nextToken());
			p[i] = idx;
		}

		System.out.println(solution());
		br.close();
	}

	// 가장 오래 뒤에 사용할 or 사용하지 않을 플러그 뽑기
	private static int solution() {
		// 사용중인 전기용품의 번호 set
		Set<Integer> multiTap = new HashSet<>();
		// preIdx: 가장 최근에 멑티탭에 꽂은 전기용품 번호
		int res = 0, cnt = 0, preIdx = 0;

		for (int i = 0; i < K; i++) {
			// 이미 꼽힌 제품
			if (multiTap.contains(p[i])) {
				continue;
			}

			// 꼽을 수 있다
			if (cnt < N) {
				multiTap.add(p[i]);
				preIdx = p[i];
				cnt++;
				continue;
			}

			// key: 전기용품 이름, value: 해당 전기용품 사용까지 남은 순서
			Map<Integer, Integer> map = new HashMap<>();
			for (int j = i; j < K; j++) {
				// 해당 전기용품의 남은 순서 최솟값 저장
				if (!map.containsKey(p[j])) {
					map.put(p[j], j - i);
				}
			}

			int len = 0;
			for (int plug : multiTap) {
				// 더 이상 사용하지 않는 전기용품이 멀티탭에 있다면 탐색 종료하고 해당 번호를 뽑자
				if (!map.containsKey(plug)) {
					preIdx = plug;
					break;
				}
				
				// 사용 기간이 더 길다면 해당 전기용품 번호와 사용 기간 갱신
				if (map.get(plug) > len) {
					preIdx = plug;
					len = map.get(plug);
				}
			}

			multiTap.remove(preIdx);
			multiTap.add(p[i]);
			res++;
		}

		return res;
	}
}