package org.les.main;

import java.lang.reflect.Method;
import java.util.Scanner;

public class MainClass {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Classe de teste(eg : teste.exemplo.Class): ");
		Scanner reader = new Scanner(System.in);
		String className = reader.nextLine();
		Class<?> classz = Class.forName(className);
		Object obj = classz.newInstance();
		while(true)
		{
			Method[] method = classz.getMethods();
		}
	}
}
