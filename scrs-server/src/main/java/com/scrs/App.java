package com.scrs;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

		String url = "http://localhost:8080/swagger-ui/index.html";
		String clickableUrl = "\u001B[1;36m" + url + "\u001B[0m";
		System.out.println("Swagger UI: " + clickableUrl);

		// openBrowser(url);
	}

	@SuppressWarnings("unused")
	private static void openBrowser(String url) {
		try {
			String os = System.getProperty("os.name").toLowerCase();
			ProcessBuilder processBuilder = new ProcessBuilder();

			if (os.contains("win")) {
				processBuilder.command("cmd", "/c", "start", url);
			} else if (os.contains("mac")) {
				processBuilder.command("open", url);
			} else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
				processBuilder.command("xdg-open", url);
			} else {
				System.out.println("Unsupported OS. Please open the browser manually.");
				return;
			}

			processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Browser should open now.");
		}
	}
}
