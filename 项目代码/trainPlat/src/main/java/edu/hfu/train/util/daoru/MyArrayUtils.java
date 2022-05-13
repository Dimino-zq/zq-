package edu.hfu.train.util.daoru;

public class MyArrayUtils {
	private MyArrayUtils() {

	}

	public static int getIndex(Object[] objs, Object object) {
		int index = -1;
		for (int i = 0; i < objs.length; i++) {
			Object obj = objs[i];
			if (obj.equals(object)) {
				return i;
			}
		}
		return index;
	}

}
