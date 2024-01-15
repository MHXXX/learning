package com.xmh;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Range;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * .
 *
 * @author 谢明辉
 */

@Slf4j
public class Test {

    private static ThreadLocal<Boolean> redirected = ThreadLocal.withInitial(() -> false);

    @SneakyThrows
    public static void main(String[] args) throws Exception {
        String httpText = FileUtil.readString("/Users/admin/Documents/data", StandardCharsets.UTF_8);
        System.out.println(requestReplay(httpText));
    }

    public static String requestReplay(String httpText) {
        return requestReplay(httpText, false);
    }

    @SneakyThrows
    public static String requestReplay(String httpText, boolean https) {

        int port = https ? 443 : 80;
        String host = "";
        ArrayList<String> lines = new ArrayList<>();
        IoUtil.readLines(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(httpText.getBytes()))), lines);

        String line0 = lines.get(0);
        String method = line0.substring(0, line0.indexOf(" "));
        String path = line0.substring(line0.indexOf(" ") + 1, line0.lastIndexOf(" "));
        String version = line0.substring(line0.lastIndexOf(" ") + 1);
        path = URLUtil.encodeQuery(URLUtil.decode(path));
        int index;
        MediaType mediaType = null;
        boolean hasClose = false;
        // 组装 headers
        HashMap<String, String> headers = new HashMap<>();
        for (index = 1; index < lines.size(); index++) {
            String line = lines.get(index);
            if ("".equals(line)) {
                index++;
                break;
            }

            String key = line.substring(0, line.indexOf(":")).trim();
            String value = line.substring(line.indexOf(":") + 1).trim();
            if (key.equalsIgnoreCase(HttpHeaders.HOST)) {
                host = value;
            } else if (key.equalsIgnoreCase(HttpHeaders.ACCEPT_ENCODING)) {
                value = "deflate";
            } else if (key.toLowerCase().contains("connection")) {
                hasClose = true;
                value = "close";
            } else if (key.equalsIgnoreCase(HttpHeaders.CONTENT_TYPE)) {
                mediaType = MediaType.valueOf(value);
            }
            headers.put(key, value);
        }
        if (!hasClose) {
            headers.put(HttpHeaders.CONNECTION, "close");
        }
        String body = null;
        if (index < lines.size()) {
            StringBuilder bodyBuilder = new StringBuilder();
            while (index < lines.size()) {
                bodyBuilder.append(lines.get(index)).append("\r\n");
                index++;
            }
            if (mediaType != null && mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
                body = URLEncodeUtil.encode(bodyBuilder.toString());
                headers.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length()));
            } else {
                body = bodyBuilder.toString();
            }
        }

        if (host.contains(":")) {
            String[] sp = host.split(":");
            host = sp[0];
            port = Integer.parseInt(sp[1]);
        }

        if (https) {
            return sslHttpRequest(host, port, headers, body, method, path, version);
        } else {
            return httpRequest(host, port, headers, body, method, path, version);
        }
    }

    public static String assemblyRequest(HashMap<String, String> headers, String body, String method, String path, String version) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(method).append(" ").append(path).append(" ").append(version).append("\r\n");
        headers.forEach((key, value) -> messageBuilder.append(key).append(": ").append(value).append("\r\n"));
        if (body != null) {
            messageBuilder.append("\r\n").append(body);
        }
        return messageBuilder.append("\r\n").toString();
    }

    @SneakyThrows
    public static String httpRequest(String host, int port, HashMap<String, String> headers, String body, String method, String path, String version) {

        String request = assemblyRequest(headers, body, method, path, version);

        log.info(" 报文数据: \n{}", request);

        InetSocketAddress address = new InetSocketAddress(host, port);

        try (Socket socket = new Socket()) {
            socket.setSoTimeout(10000);
            socket.connect(address);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            ArrayList<String> lines = new ArrayList<>();
            IoUtil.readLines(socket.getInputStream(), StandardCharsets.UTF_8, lines);
            return redirect(lines, headers, body, method, version);
        }
    }

    @SneakyThrows
    public static String sslHttpRequest(String host, int port, HashMap<String, String> headers, String body, String method, String path, String version) {
        String request = assemblyRequest(headers, body, method, path, version);
        log.info(" 报文数据: \n{}", request);

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port)) {
            sslSocket.setSoTimeout(10000);
            sslSocket.startHandshake();

            OutputStream outputStream = sslSocket.getOutputStream();
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            ArrayList<String> lines = new ArrayList<>();
            IoUtil.readLines(sslSocket.getInputStream(), StandardCharsets.UTF_8, lines);
            return redirect(lines, headers, body, method, version);
        }
    }

    /**
     * 处理 301
     */
    private static String redirect(List<String> response, HashMap<String, String> headers, String body, String method, String version) {
        String line0 = response.get(0);
        boolean redirectRequired = line0.split(" ")[1].equalsIgnoreCase("301");
        if (redirected.get() || !redirectRequired) {
            redirected.remove();
            return String.join("\r\n", response);
        }

        URL url = null;
        for (String s : response) {
            if (s.startsWith(HttpHeaders.LOCATION)) {
                String u = s.substring(s.indexOf(":") + 1).trim();
                url = URLUtil.url(u);
            }
        }
        String path = url.getQuery() == null ? url.getPath() : url.getPath() + "?" + url.getQuery();
        int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        if (url.getPort() != -1) {
            headers.put(HttpHeaders.HOST, url.getHost() + ":" + port);
        } else {
            headers.put(HttpHeaders.HOST, url.getHost());
        }

        redirected.set(true);
        if (url.getProtocol().equalsIgnoreCase("https")) {
            return sslHttpRequest(url.getHost(), port, headers, body, method, path, version);
        } else {
            return httpRequest(url.getHost(), port, headers, body, method, path, version);
        }
    }
}
