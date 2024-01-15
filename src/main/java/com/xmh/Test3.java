package com.xmh;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.math.RoundingMode;
import java.util.List;


/**
 * .
 *
 * @author 谢明辉
 * @date 2022/2/14
 */

@Slf4j
public class Test3 {


    @SneakyThrows
    public static void main(String[] args) {

        int id = 6;
        String beginTime = "2023-11-27 19:21:26";
        String endTime = "2023-11-27 19:32:17";
        String host = "www.zhipin.com";
        String path = "/";
        request(id, beginTime, endTime, host, path);
        ip(id);
        nginx(id);
    }

    @SneakyThrows
    public static void nginx(int id) {
        String template = "insert into soc_cc_attack_request_statistic(record_id, data_source, domestic, statistics_time, total_request)\n" +
                "VALUES (" + id + ", 'NGINX', true, {}, {});";
        if (!FileUtil.file("/Users/admin/Downloads/n.csv").exists()) {
            return;
        }
        CsvReader reader = CsvUtil.getReader(new FileReader("/Users/admin/Downloads/n.csv"));
        CsvData data = reader.read();
        List<CsvRow> rows = data.getRows();
        rows.remove(0);
        rows.stream().map(x -> StrUtil.format(template, "'" + x.get(0) + "'", x.get(1)))
                .forEach(System.out::println);
    }

    @SneakyThrows
    public static void request(Integer id, String beginTime, String endTime, String host, String path) {


        String template = "insert into soc_cc_attack_request_statistic(record_id, data_source, domestic, statistics_time, total_request)\n" +
                "VALUES (" + id + ", 'WAF', {}, {}, {});";
        CsvReader reader = CsvUtil.getReader(new FileReader("/Users/admin/Downloads/req.csv"));
        CsvData data = reader.read();
        List<CsvRow> rows = data.getRows();
        rows.remove(0);
        int sum = rows.stream().map(x -> x.get(0)).map(Integer::parseInt).mapToInt(x -> x).sum();
        int avg = rows.stream().map(x -> x.get(0)).mapToInt(Integer::parseInt).map(x -> (int) NumberUtil.div(x, 10, 0, RoundingMode.CEILING))
                .max().orElse(0);
        System.out.println(StrUtil.format("INSERT INTO boss_shield.soc_cc_attack_record (id, begin_time, end_time, domain, path, waf_total_request, nginx_total_request, maximum_tps, protection_strategy)\n" +
                "VALUES ({}, '{}', '{}', '{}', '{}', {}, 0, {}, '[\n" +
                "  \"WAF\"\n" +
                "]');", id, beginTime, endTime, host, path, sum, avg));

        rows.stream().map(x -> StrUtil.format(template, Boolean.parseBoolean(x.get(2)), "'" + x.get(1) + "'", x.get(0)))
                .forEach(System.out::println);
    }

    @SneakyThrows
    public static void ip(Integer id) {
        String template = "insert into soc_cc_ip_traffic_ranking(record_id, ip, total_request) VALUES (" + id + ",{},{});";
        CsvReader reader = CsvUtil.getReader(new FileReader("/Users/admin/Downloads/ip.csv"));
        CsvData data = reader.read();
        List<CsvRow> rows = data.getRows();
        rows.remove(0);
        rows.stream().map(x -> StrUtil.format(template, "'" + x.get(0) + "'", x.get(1)))
                .forEach(System.out::println);
    }
}