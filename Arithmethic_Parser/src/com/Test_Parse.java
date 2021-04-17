/**
 * 
 */
package com;

import java.io.IOException;

/**
 * This class is designed to test the Parser Class
 * 
 * @author Mohd Saiful Akmal
 *
 */
public class Test_Parse {
	public static void main(String[] args) {

		try {
			MyLogger.setup();
			Parser p1;

			p1 = new Parser("((100+10-15*20/5+6*2/1*(20+10)))");
			System.out.println(p1.getOutPut());//-310

			Parser p2 = new Parser("-20--10");
			System.out.println(p2.getOutPut());//-10

			p2 = new Parser("-20--10+-55");
			System.out.println(p2.getOutPut());//45

			p2 = new Parser("-20-(-10)+-55");
			System.out.println(p2.getOutPut());//45

			p2 = new Parser("-20-(-10+2)+-55");//43
			System.out.println(p2.getOutPut());

			p2 = new Parser("(-10)");//-10
			System.out.println(p2.getOutPut());

			p2 = new Parser("-0+(-10)");//-10
			System.out.println(p2.getOutPut());

			p2 = new Parser("-0-(-10)");//10 
			System.out.println(p2.getOutPut());


			p2 = new Parser("10/(-10)");//-1
			System.out.println(p2.getOutPut());
			p2 = new Parser("10/-10");//-1
			System.out.println(p2.getOutPut());
			p2 = new Parser("-10/-10");//1
			System.out.println(p2.getOutPut());
			p2 = new Parser("(-10)/(-10)");//1
			System.out.println(p2.getOutPut());
			p2 = new Parser("(10)/-10");//-1
			System.out.println(p2.getOutPut());
			
			p2 = new Parser("A10");//-1
			System.out.println(p2.getOutPut());
			p2 = new Parser("(10)/A-10");//-1
			System.out.println(p2.getOutPut());
			

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
