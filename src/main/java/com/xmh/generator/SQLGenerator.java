package com.xmh.generator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/3/24 12:53 PM
 */
public class SQLGenerator {

    private static final String AND = " and ";

    public static void main(String[] args) {
        System.out.println(selectSql());
//        System.out.println(updateSql());
//        System.out.println(insertSql());
    }

    /**
     private Integer scenarioId;
     private String receiverEmail;
     private Boolean enabled;
     private String creatorEmail;
     private Long createTimeGTE;
     private Long createTimeLTE;
     */
    public static String selectSql() {
        List<WhereEntry> entries = new LinkedList<>();
        entries.add(WhereEntry.of("item", "uuid", String.class, Operation.EQUAL));
        entries.add(WhereEntry.of("item", "host", String.class, Operation.EQUAL));
        entries.add(WhereEntry.of("item", "creator_email", String.class, Operation.LIKE));

        entries.add(WhereEntry.of("item", "death_toll", Integer.class, Operation.GTE).setValueA("deathTollGTE"));
        entries.add(WhereEntry.of("item", "death_toll", String.class, Operation.LTE).setValueA("deathTollLTE"));


        entries.add(WhereEntry.of("item", "create_time", Date.class, Operation.GTE).setValueA("createTimeGTE"));
        entries.add(WhereEntry.of("item", "create_time", Date.class, Operation.LTE).setValueA("createTimeLTE"));
//
//        entries.add(WhereEntry.of("item", "sample_num", Integer.class, Operation.GTE).setValueA("sampleNumGTE"));
//        entries.add(WhereEntry.of("item", "sample_num", Integer.class, Operation.LTE).setValueA("sampleNumLTE"));
//        entries.add(WhereEntry.of("item", "params", String.class, Operation.LIKE).setValueA("paramsLike"));
//        entries.add(WhereEntry.of("item", "creator_email", String.class, Operation.LIKE).setValueA("creatorEmailLike"));
//        entries.add(WhereEntry.of("item", "create_time", Date.class, Operation.LTE).setValueA("createTimeLTE"));
//        entries.add(WhereEntry.of("item", "create_time", Date.class, Operation.GTE).setValueA("createTimeGTE"));
        return selectGenerator("soc_phishing_task", entries, "offset", "pageSize");
    }

    public static String updateSql() {
        List<WhereEntry> entries = new LinkedList<>();
        entries.add(WhereEntry.of("do", "name", String.class, Operation.LIKE));
        entries.add(WhereEntry.of("do", "domain", String.class, Operation.LIKE));
        entries.add(WhereEntry.of("do", "department", String.class, Operation.EQUAL));
        entries.add(WhereEntry.of("do", "threat_modeling", String.class, Operation.EQUAL));
        entries.add(WhereEntry.of("do", "hit_rules", String.class, Operation.EQUAL));
        return updateGenerator("soc_threat_modeling", entries, new String[]{"id"}, null);


    }


    public static String selectGenerator(String tableName, List<WhereEntry> entryList, String offSetName, String limitName) {
        StringBuilder sb = new StringBuilder().append("\"select * from ").append(tableName).append(" where 1=1 \" + \n");
        if (CollUtil.isNotEmpty(entryList)) {
            for (WhereEntry entry : entryList) {
                String insertExpression = getSelectExpression(entry);
                sb.append(insertExpression);
            }
        }
        sb.append("\" order by id desc \" + \n");

        if (offSetName != null) {
            sb.append("\"#if(:").append(offSetName).append(" != null) { limit :").append(offSetName).append(",:").append(limitName).append(" } \"");
        } else {
            sb.setLength(sb.length()-3);
        }
        return sb.toString();
    }

    public static String updateGenerator(String tableName, List<WhereEntry> entryList, String[] whereColumns, String paramName) {
        String updaterParam = getParam("id", paramName);

        StringBuilder sb = new StringBuilder().append("\"update ").append(tableName).append(" set id=").append(updaterParam).append(" \"+\n");
        for (WhereEntry entry : entryList) {
            String updateExpression = getUpdateExpression(entry);
            sb.append(updateExpression);
        }

        if (whereColumns.length > 0) {
            sb.append("\" where ");

            for (int i = 0; i < whereColumns.length; i++) {
                String whereColumn = whereColumns[i];
                if (i != 0) {
                    sb.append(AND);
                }
                sb.append(whereColumn).append("=").append(getParam(whereColumn, null));
            }

            sb.append("\"");
        }


        return sb.toString();
    }

    public static String insertGenerator(String tableName, String[] columns, String paramName) {
        StringBuilder sb = new StringBuilder().append("\"insert into ").append(tableName).append(" (");
        // 拼装 columns
        for (int i = 0; i < columns.length; i++) {
            sb.append(columns[i]);
            if (i != columns.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")\" +\n \" values \" +\n \"(");
        // 拼装 values
        for (int i = 0; i < columns.length; i++) {
            String param = getParam(columns[i], paramName);
            sb.append(param);
            if (i != columns.length - 1) {
                sb.append(",");
            }
        }
        sb.append(");\"");
        return sb.toString();
    }

    public static String getSelectExpression(WhereEntry entry) {
        String expressionIf = getExpressionIf(entry);
        String expressionDo = getExpressionDo(entry, true);
        return "\"" + expressionIf + expressionDo + "\" +\n";
    }

    public static String getUpdateExpression(WhereEntry entry) {
        String expressionIf = getExpressionIf(entry);
        String expressionDo = getExpressionDo(entry, false);

        return "\"" + expressionIf + expressionDo + "\" +\n";
    }

    public static String getExpressionDo(WhereEntry entry, boolean select) {
        StringBuilder sb = new StringBuilder();
        String fullName = entry.valueA != null ? entry.getAFullName() : entry.getFullName();
        String column = entry.getColumn();
        sb.append("{");
        if (select) {
            sb.append(AND).append(column).append(" ");
            switch (entry.operation) {

                case EQUAL:
                    sb.append(" = ").append(fullName);
                    break;
                case LIKE:
                    sb.append(" like concat('%', ").append(fullName).append(", '%')");
                    break;
                case GTE:
                    sb.append(" >= ").append(fullName);
                    break;
                case LTE:
                    sb.append(" <= ").append(fullName);
                    break;
                case IN:
                    sb.append(" in ").append(fullName);
                    break;
                case BETWEEN:
                    sb.append(" between ").append(entry.getAFullName()).append(AND).append(entry.getBFullName());
                    break;
            }
        } else {
            sb.append(", ").append(column).append("=").append(fullName);
        }
        sb.append(" }");
        return sb.toString();
    }

    public static String getExpressionIf(WhereEntry entry) {
        String fullName = StringUtils.isNotEmpty(entry.getValueA()) ? entry.getAFullName() : entry.getFullName();

        StringBuilder sb = new StringBuilder().append("#if(").append(fullName).append(" != null ");
        if (entry.getClazz() == String.class) {
            sb.append("&& ").append(fullName).append(" != ''");
        }
        sb.append(" )");
        return sb.toString();
    }


    public static String getParam(String column, String paramName) {
        String item = CharSequenceUtil.toCamelCase(column);
        StringBuilder sb = new StringBuilder()
                .append(":");
        if (paramName != null) {
            sb.append(paramName).append(".");
        }
        sb.append(item);
        return sb.toString();
    }


    public enum Operation {
        EQUAL(),
        GTE,
        LTE,
        BETWEEN,
        LIKE,
        IN
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class WhereEntry {
        private String column;
        private String paramName;
        private Class<?> clazz;
        private Operation operation;
        private String valueA;
        private String valueB;

        public static WhereEntry of(String column) {
            return of(null, column, String.class, Operation.EQUAL);
        }

        public static WhereEntry of(String paramName, String column) {
            return of(paramName, column, String.class, Operation.EQUAL);
        }

        public static WhereEntry of(String paramName, String column, Class<?> clazz) {
            return of(paramName, column, clazz, Operation.EQUAL);
        }

        public static WhereEntry of(String paramName, String column, Operation operation) {
            return of(paramName, column, String.class, operation);
        }

        public static WhereEntry of(String paramName, String column, Class<?> clazz, Operation operation) {
            return new WhereEntry().setColumn(column).setClazz(clazz).setOperation(operation).setParamName(paramName);
        }

        public String getFullName() {
            return getParam(column, paramName);
        }

        public String getAFullName() {
            return getParam(valueA, paramName);
        }

        public String getBFullName() {
            return getParam(valueB, paramName);
        }
    }

}
