/**
 * 
 */
package com.rbac.system.base;

import java.text.MessageFormat;

/**
 * TODO
 * 
 * @author wlfei
 * @date 2021-05-01
 */
public class SomeTest {
	public static void main(String[] args) {
		String msg = MessageFormat.format("{0}_{1}", "1", "2");
		System.out.println(msg);
	}
}
