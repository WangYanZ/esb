package io.transwarp.esb;

import org.springframework.boot.SpringApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.transwarp")
@EnableSwagger2
public class EsbApplication {

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EsbApplication.class, args);

	}

}
