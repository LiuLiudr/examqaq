package com.zzxx.exam.test;

import com.zzxx.exam.util.Config;

import java.io.File;

public class ConfigTest {
	public static void main(String[] args) {
		Config config = new Config("config.properties");
		//System.out.println(new File("config.properties").getAbsoluteFile());
		int timeLimit = config.getInt("TimeLimit");
		System.out.println(timeLimit);

	}
}
