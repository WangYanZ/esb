package io.transwarp.esb;

import io.transwarp.esb.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
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
		ApplicationContext applicationContext = SpringApplication.run(EsbApplication.class, args);
		applicationContext.getBean(SocketServer.class).start();
	}

}
