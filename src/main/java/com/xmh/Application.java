package com.xmh;


import com.xmh.cache.EnableLocalCache;
import com.xmh.log.config.EnableMyLog;
import com.xmh.log.function.MyLogFunction;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * .
 *
 * @author 谢明辉
 */

@SpringBootApplication
@EnableBatchProcessing
@EnableMyLog
@EnableLocalCache
public class Application {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public MyLogFunction squareFunction() {

        return new MyLogFunction() {
            @Override
            public String getName() {
                return "pow";
            }

            @Override
            public String apply(Object o) {
                String s = String.valueOf(o);
                int i = Integer.parseInt(s);
                return String.valueOf(i * i);
            }
        };
    }
}
