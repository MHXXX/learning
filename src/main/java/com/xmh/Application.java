package com.xmh;


import com.xmh.cache.EnableLocalCache;
import com.xmh.log.config.EnableMyLog;
import com.xmh.log.function.MyLogFunction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * .
 *
 * @author 谢明辉
 */

@SpringBootApplication
@EnableBatchProcessing
@EnableMyLog
@EnableLocalCache
public class Application{

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
