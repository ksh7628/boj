package gold;
import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 8. 29.
 *
 * 분류: 기하학, 다각형의 넓이
 * 난이도: 골드5
 * 소요 시간: 0h 20m
 * 혼자 품: O
 * 풀이: ccw 알고리즘을 사용하여 1번점과 2번점의 내적, 2번점과 3번점의 내적, ... , N번점과 1번점의 내적의 합 / 2를 통해 다각형의 넓이를 구함
 * 느낀 점: 좌표값은 10만 이하의 정수로 주어지지만 넓이는 int형을 초과할 수 있으므로 좌표를 long형으로 입력받아야 된다는 점을 알게 되었다.
 */
public class Boj_G5_2166_다각형의면적 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		long[][] pos = new long[2][N + 1];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			pos[0][i] = Long.parseLong(st.nextToken());
			pos[1][i] = Long.parseLong(st.nextToken());
		}
		pos[0][N] = pos[0][0];
		pos[1][N] = pos[1][0];

		double a = 0, b = 0;
		for (int i = 0; i < N; i++) {// ccw 알고리즘
			a += pos[0][i] * pos[1][i + 1];
			b += pos[1][i] * pos[0][i + 1];
		}

		System.out.printf("%.1f", Math.abs(a - b) / 2);
		br.close();
	}
}