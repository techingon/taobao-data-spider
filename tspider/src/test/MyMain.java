package test;

import java.util.Random;
import java.util.TimeZone;

public class MyMain {

	public static void main(String[] args) {
//		System.out.println(TimeZone.getTimeZone("GMT-8"));
//		System.out.println(TimeZone.getAvailableIDs()[0]);
		randomInt();
	}
	
	
	static int randomInt(){
		int result=0;
		int[] n = new int[1000];
		for (int j=0;j<1000;j++) {
			n[j] = 1;
		}
		for (int i = 0; i < 9000; i++) {
			int pos = new Random().nextInt(999);
			n[pos]++;
		}
		for (int i : n) {
			System.out.println(i);
		}
		return 1;
	}
	
}
