/**
 * 
 */
package com.vanstone.centralserver.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author shipeng
 *
 */
public class DebugUtil {
	
	public static void print(Object object) {
		if (object == null) {
			System.out.println(object);
			return;
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		System.out.println(gson.toJson(object));
	}
	
}
