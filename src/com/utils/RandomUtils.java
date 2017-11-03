package com.utils;

import java.util.ArrayList;
import java.util.Random;

public class RandomUtils {
	public String RandomId(ArrayList<String> arr) throws Exception {
		int len = arr.size();

		int ran = new Random().nextInt(len);

        String result = arr.get(ran);

        return result;
	}
}
