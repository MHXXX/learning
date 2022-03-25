package com.xmh;

import cn.hutool.core.util.StrUtil;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/3/24 12:53 PM
 */
public class SQLGenerator {

    /**
     * private String userName;
     * private String departmentName;
     * private String opMenu;
     * private String opButton;
     * private Integer opStatus;
     * private Long startTs;
     * private Long endTs;
     */
    public static void main(String[] args) {
//        String sql = insertGenerator("audit_op_log", new String[]{"user", "user_name", "department_id", "department_name", "op_menu", "op_button", "op_res", "op_status"}, "auditOpLogDO");
//        String sql = updateGenerator("audit_event_type", new String[]{"event_name", "scenario_id", "event_desc"}, new String[]{"id"}, "auditEventTypeDO");
//        String sql = selectGenerator("audit_event_type", new String[]{"event_name", "scenario_id", "status"}, null, "offset", "pageSize");
        String sql = selectGenerator("audit_op_log", new String[]{"user_name","department_name","op_menu","op_button","op_status"}, null, "offset", "pageSize");

        System.out.println(sql);
    }


    public static String selectGenerator(String tableName, String[] whereColumns, String paramName, String offSetName, String limitName) {
        StringBuilder sb = new StringBuilder().append("\"select * from ").append(tableName).append(" where 1=1 \" + \n");
        if (whereColumns.length > 0) {
            for (String whereColumn : whereColumns) {
                String insertExpression = getInsertExpression(whereColumn, paramName, true, false);
                sb.append(insertExpression);
            }
        }
        if (offSetName != null) {
            sb.append("\"#if(:").append(offSetName).append(" != null) { limit :").append(offSetName).append(",:").append(limitName).append(" } \"");
        }
        return sb.toString();
    }

    public static String updateGenerator(String tableName, String[] updateColumns, String[] whereColumns, String paramName) {
        String updaterParam = getParam("updater", paramName);

        StringBuilder sb = new StringBuilder().append("\"update ").append(tableName).append(" set updater=").append(updaterParam).append(" \"+\n");
        for (String updateColumn : updateColumns) {
            String updateExpression = getUpdateExpression(updateColumn, paramName, true);
            sb.append(updateExpression);
        }

        if (whereColumns.length > 0) {
            sb.append("\" where ");

            for (int i = 0; i < whereColumns.length; i++) {
                String whereColumn = whereColumns[i];
                if (i != 0) {
                    sb.append(" and ");
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

    public static String getInsertExpression(String column, String paramName, boolean text, boolean like) {
        String param = getParam(column, paramName);
        String expressionIf = getExpressionIf(param, text);
        String expressionDo = getExpressionDo(column, param, true, like);
        return "\"" + expressionIf + expressionDo + "\" +\n";
    }

    public static String getUpdateExpression(String column, String paramName, boolean text) {
        String param = getParam(column, paramName);
        String expressionIf = getExpressionIf(param, text);
        String expressionDo = getExpressionDo(column, param, false, false);

        return "\"" + expressionIf + expressionDo + "\" +\n";
    }

    public static String getExpressionDo(String column, String param, boolean select, boolean like) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (select) {
            sb.append(" and ").append(column);
            if (like) {
                sb.append("like concat('%', ").append(param).append(", '%')");
            } else {
                sb.append("=").append(param);
            }
        } else {
            sb.append(", ").append(column).append("=").append(param);
        }
        sb.append(" }");
        return sb.toString();
    }

    public static String getExpressionIf(String param, boolean text) {
        StringBuilder sb = new StringBuilder().append("#if(").append(param).append(" != null ");
        if (text) {
            sb.append("&& ").append(param).append(" != '' )");
        }
        return sb.toString();
    }


    public static String getParam(String column, String paramName) {
        String item = StrUtil.toCamelCase(column);
        StringBuilder sb = new StringBuilder()
                .append(":");
        if (paramName != null) {
            sb.append(paramName).append(".");
        }
        sb.append(item);
        return sb.toString();
    }


}
