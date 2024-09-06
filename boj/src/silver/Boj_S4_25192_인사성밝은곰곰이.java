package silver;

import java.io.*;
import java.util.*;

public class Boj_S4_25192_인사성밝은곰곰이 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		HashSet<String> set = new HashSet<String>();
		int res = 0;

		while (N-- > 0) {
			String s = br.readLine();
			if (s.equals("ENTER")) {
				res += set.size();
				set.clear();
			} else {
				set.add(s);
			}
		}

		System.out.println(res + set.size());
		br.close();
	}
}