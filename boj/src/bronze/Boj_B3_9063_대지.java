package bronze;

import java.io.*;
import java.util.*;

public class Boj_B3_9063_대지 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int minX = 10000, minY = 10000, maxX = -10000, maxY = -10000;

		while (N-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			minX = Math.min(minX, x);
			minY = Math.min(minY, y);
			maxX = Math.max(maxX, x);
			maxY = Math.max(maxY, y);
		}

		int w = maxX - minX;
		int h = maxY - minY;

		System.out.println(w * h);
		br.close();
	}
}