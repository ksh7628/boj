import java.io.*;
import java.util.StringTokenizer;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 17.
 *
 * 분류: 그리디 알고리즘
 * 난이도: 실버4
 * 혼자 품: O
 * 풀이: 
 * 1. long형으로 입력 값들을 저장한다. (최소 비용을 구할 떄 int형은 오버플로우가 발생할 수 있음)
 * 2. 두번째 도시를 가려면 무조건 첫번째 도시에서 주유해야 하므로 초기값을 첫번째 도시의 기름가격 * 두번쨰 도시까지의 거리 로 저장한다.
 * 3-1. 반복문을 통해 다음 도시의 주유비가 이전까지의 최소 주유비보다 작으면 최소값을 갱신하고 결과값에 최소 비용 * 다음 도시까지의 거리 를 더해준다.
 * 3-2. 작지 않다면 이전 최소 비용 * 다음 도시까지의 거리 를 더해준다.
 * 느낀 점: 1중 반복문 만으로도 간단하게 구현할 수 있는 그리디한 방법으로 접근 가능한 문제였다.
 */
public class Boj_S4_13305_주유소 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		long[] len = new long[N - 1];
		long[] price = new long[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N - 1; i++) {
			len[i] = Long.parseLong(st.nextToken());
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			price[i] = Long.parseLong(st.nextToken());
		}

		long cost = price[0];
		long res = cost * len[0];// 두번째 도시를 가려면 무조건 첫 번째 도시를 경유해야 함
		for (int i = 1; i < N - 1; i++) {
			cost = Math.min(cost, price[i]);// 최소값 갱신
			res += cost * len[i];
		}
		
		System.out.println(res);
		br.close();
	}
}