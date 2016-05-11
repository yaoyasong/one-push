package io.onepush.util;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * http query stirng的解析方法
 *
 * Created by yaoyasong on 2016/5/4.
 */
public final class QueryStringParser {
    public static Map<String,String> getParameters(String uri) {
        Map<String,String> params = new HashMap<>();
        if (StringUtils.hasText(uri)) {
            String queryString = uri.substring(uri.indexOf("?")+1);
            String[] paras = queryString.split("&");
            for (String para : paras) {
                String[] pvs = para.split("=");
                params.put(pvs[0],pvs[1]);
            }
        }
        return params;
    }

    /**
     * 将http uri中的参数解析出来
     * 如http://localhost:8080/dkd.ws?deviceId=fkdak"中的deviceId值
     * @param uri
     * @param parameterName
     * @return parameterValue
     */
    public static String getParameterValue(String uri, String parameterName) {
        if (StringUtils.hasText(uri)) {
            String queryString = uri.substring(uri.indexOf("?")+1);
            String[] paras = queryString.split("&");
            for (String para : paras) {
                String[] pvs = para.split("=");
                if (pvs[0].equalsIgnoreCase(parameterName)) {
                    return para.split("=")[1];
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String uri = "http://localhost:8080/dkd.ws?deviceId=fkdak,daaad";

        System.out.println(QueryStringParser.getParameterValue(uri,"deviceId"));
    }
}
