import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] arr = solution(n);
		for (int i : arr) {
			System.out.print(i + " ");
		}
		sc.close();
	}

	static public int[] solution(int n) {
		int[] answer = new int[n * (n + 1) / 2];
		int[][] arr = new int[n][n];
		int size = n * (n + 1) / 2, len = n, idx = 0, i = 0, j = 0, num = 1;
		boolean down = true, right = true;
		while (num <= size) {
			if (down) {
				if (i < len) {
					arr[i++][j] = num++;
				} else {
					down = false;
					i--;
					j++;
				}
			} else if (right) {
				if (j < len) {
					arr[i][j++] = num++;
				} else {
					right = false;
					j--;
					len-=2;
				}
			} else if (idx++ < len) {
				arr[--i][--j] = num++;
			} else {
				down = true;
				right = true;
				idx = 0;
			}
		}

		idx = 0;
		for (i = 0; i < n; i++) {
			for (j = 0; j <= i; j++) {
				answer[idx++] = arr[i][j];
			}
		}

		return answer;
	}
}
