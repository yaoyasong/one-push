package io.onepush.util;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.regex.Pattern;

/**
 * Criteria构造辅助工具
 * Created by yaoyasong on 2016/5/7.
 */
public final class CriteriaCreator {

    public static Criteria like(String parameter, String value) {
        Pattern pattern = Pattern.compile("^.*" + value + ".*$");
        return Criteria.where(parameter).regex(pattern);
    }
}
