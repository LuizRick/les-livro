package org.les.main;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;

public class MainClass {
	
	public static void main(String[] args) {
		try {
			System.out.println("Classe de teste(eg : teste.exemplo.Class): ");
			Scanner reader = new Scanner(System.in);
			String className = reader.nextLine();
			Class<?> classz = Class.forName(className);
			HashMap<Integer, String> options = new HashMap<>();
			while(true)
			{
				Object obj = classz.newInstance();
				System.out.println("Codigo: \tMetodo\t");
				Method[] methods = classz.getMethods();
				for(int i = 0;i < methods.length;i++) {
					if(methods[i].getName().startsWith("test")) {
						System.out.println(i + "\t\t" + methods[i].getName() + "\n");
						options.put(i, methods[i].getName());
					}
				}
				String r = reader.nextLine();
				if(r.indexOf("-1") >= 0)
					break;
				Integer index = Integer.parseInt(r);
				classz.getMethod(options.get(index), String.class)
				.invoke(obj, "SALVAR");
			}
			reader.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
