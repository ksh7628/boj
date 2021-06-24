import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 6. 24.
 *
 * 분류: 그리디 알고리즘, 정렬
 * 난이도: 골드3
 * 혼자 품: X
 * 풀이: 
 * 1. 입력 받은 값들을 arr에 저장하고 정렬한다.
 * 2. x이하의 수는 모두 가능하고 x+arr[i]라는 수가 만들어지면 x+arr[i]의 수는 모두 만들 수 있으므로 arr[i+1]값이 x+arr[i]값보다 커질때 까지 누적해서 더해준다.
 * 3. 값이 커진다면 res를 만들 수 없으므로 res를 출력한다. 
 * 느낀 점: 
 * 쉽게 봤던 문제였는데 도저히 생각을 할 수 없어서 다른 사람의 풀이를 참조하여 풀게 되었다. 그리디 알고리즘은 진짜 이런 발상을 해내는게 어려운 것 같다.
 */
public class Boj_G3_2437_저울 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);
		int res = 1;// +1을 해주기 위해 처음에 1로 초기화
		
		// x이하의 수는 모두 가능하고 x+arr[i]라는 수가 만들어지면 x+arr[i]의 수는 모두 만들 수 있다.
		for (int i = 0; i < N; i++) {
			if (res < arr[i]) {// 다음 배열 값이 현재 누적 된 값보다 작으면 반복문 종료
				break;
			}
			res += arr[i];// 아니라면 res에 누적 시켜줌
		}
		
		System.out.println(res);
		br.close();
	}
}