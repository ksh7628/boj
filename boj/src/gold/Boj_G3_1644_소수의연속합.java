package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 수학, 정수론, 두 포인터, 소수 판정, 에라토스테네스의 체
 * 난이도: 골드3
 * 소요 시간: 0h 21m
 * 혼자 품: O
 * 풀이: 
 * 1. 에라토스테네스의 체로 소수를 구한 후 소수만 prime 리스트에 저장
 * 2. s와 e가 N이하의 소수 개수랑 같아지기 전까지 반복문을 수행
 * 3. s와 e값이 같다면 sum에 s나 e위치의 수로 저장하고 합이 N이 된다면 경우의 수를 증가시킨다.
 * 4. sum이 N보다 작다면 e를 증가시키고 sum에 e위치의 수를 더해준다.
 * 5. 그렇지 않다면 sum에 s위치의 수를 빼고 s을 증가시킨다.
 * 느낀 점: 에라토스테네스의 체 + 투 포인터 를 사용하면 간단하게 풀리는 문제였다. 연속합이라는 조건이 있는데 19가 2+17, 19 두 가지 경우로 된다고 생각해서 조금 늦게 풀렸다.
 */
public class Boj_G3_1644_소수의연속합 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		ArrayList<Integer> prime = new ArrayList<>();// 소수를 저장하는 리스트
		boolean[] noPrime = new boolean[N + 1];// 소수면 false, 소수가 아니면 true로 저장
		
		// 에라토스테네스의 체로 소수를 빠르게 구함
		for (int i = 2; i * i <= N; i++) {
			for (int j = i * i; j <= N; j += i) {
				noPrime[j] = true;
			}
		}

		// 소수만 prime 리스트에 저장
		for (int i = 2; i <= N; i++) {
			if (!noPrime[i]) {
				prime.add(i);
			}
		}

		int s = 0, e = 0, sum = 0, res = 0;
		int len = prime.size();
		while (s != len) {
			if (s == e) {// 위치가 같다면 sum을 해당 위치의 수로 저장
				sum = prime.get(s);
			}

			if (sum == N) {// 합이 N이면 경우의 수 증가
				res++;
			}
			
			if (sum < N) {// 합이 N보다 작다면 e를 증가시킨 후 sum에 e위치의 수를 더해줌
				e++;
				if (e == len) {// 인덱스를 초과한다면 종료
					break;
				}
				sum += prime.get(e);
			} else {// 아니라면 sum에서 s위치의 수를 빼고 s 증가
				sum -= prime.get(s);
				s++;
			}
		}

		System.out.println(res);
		br.close();
	}
}