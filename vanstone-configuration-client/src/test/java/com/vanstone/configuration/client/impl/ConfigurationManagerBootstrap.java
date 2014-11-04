/**
 * 
 */
package com.vanstone.configuration.client.impl;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vanstone.configuration.client.ConfigurationFactory;
import com.vanstone.configuration.client.IConfigurationManager;

/**
 * @author shipeng
 *
 */
public class ConfigurationManagerBootstrap {
	
	public static void main(String[] args) {
		final IConfigurationManager configurationManager = ConfigurationFactory.getConfigurationManager();
		
		Scanner scanner = new Scanner(System.in);
		String command = null;
		
		
		
		while ((command = scanner.next()) != null) {
			if (command.equalsIgnoreCase("exit")) {
				configurationManager.close();
				break;
			}
			if (command.equalsIgnoreCase("execute")) {
				System.out.println("execute");
				ExecutorService executorService = Executors.newFixedThreadPool(10);
				for (int i=0;i<100;i++) {
					executorService.submit(new Runnable() {
						@Override
						public void run() {
							System.out.println(configurationManager.getValue("weedfs", "weedfs.address"));
							System.out.println(configurationManager.getValue("weedfs", "weedfs.port"));
							System.out.println(configurationManager.getValue("weedfs", "weedfs.nginx.address"));
						}
					});
				}
				continue;
			}
		}
	}
	
}
