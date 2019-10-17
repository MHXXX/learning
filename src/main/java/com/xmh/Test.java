package com.xmh;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.tomcat.jni.Local;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;


/**
 * .
 *
 * @author 谢明辉
 */

public class Test {

    private static String TOKEN = "NWmoOhGWei/v345SrT/DRYTHmaRSFfj7";

    public static void main(String[] args) throws IOException {
        randomTest();
    }

    private void restTemplate() {
        ResponseEntity<String> res = new com.xmh.utils.HttpClient<String>("https://v2.jinrishici.com/one.json")
                .addHeader("X-User-Token", TOKEN)
                .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
                .get(String.class);

        JSONObject origin = new JSONObject(res.getBody()).getJSONObject("data").getJSONObject("origin");
        String title = origin.getString("title");
        String dynasty = origin.getString("dynasty");
        String author = origin.getString("author");
        JSONArray content = origin.getJSONArray("content");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            sb.append(content.getString(i)).append("\n");
        }

        System.out.println(MessageFormat.format("{0}\n{1}\n{2}\n{3}", title, dynasty, author, sb.toString()));
    }


    private void webClient() {
        TcpClient tcpClient = TcpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100000).doOnConnected(connect -> connect.addHandlerLast(new ReadTimeoutHandler(3600)).addHandlerLast(new WriteTimeoutHandler(3600)));

        WebClient client = WebClient.builder()
                .defaultHeader("Content-type", "application/json")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();

        Mono<HashMap> stringMono = client.get()
                .uri("https://v2.jinrishici.com/sentence")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .header("X-User-Token", TOKEN)
                .retrieve()
                .onStatus(HttpStatus::isError, res -> Mono.error(new RuntimeException(res.statusCode().value() + ":" + res.statusCode().getReasonPhrase())))
                .bodyToMono(HashMap.class)
                .doOnError(err -> System.out.println(err.getMessage()))
                .doOnSuccess(item -> {
                    HashMap map = (HashMap) item.get("data");
                    String content = (String) map.get("content");
                    System.out.println(content);
                });
        stringMono.block();
    }

    private static void test2() throws IOException {
        Path path = Paths.get("C:\\insertTimeRecord.txt");
        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(System.out::println);
        }
    }

    private static void randomTest() {
        int i = ThreadLocalRandom.current().nextInt(0, 100);
        System.out.println(i);
//        SplittableRandom splittableRandom = new SplittableRandom();
//        splittableRandom.ints(10, 0, 10).forEach(System.out::println);
    }

    private static void instantFormatTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        String format = formatter.format(Instant.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()));
        System.out.println(format);
    }

}
