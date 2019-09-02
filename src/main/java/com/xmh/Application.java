package com.xmh;


import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


/**
 * .
 *
 * @author 谢明辉
 * @date 2019-7-12 14:13
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
