package com.boot.shell.common.object;

import org.springframework.jdbc.support.JdbcUtils;

import java.util.HashMap;

public class CamelCaseMap extends HashMap {

    public Object put(Object key, Object value) {
        return super.put(JdbcUtils.convertUnderscoreNameToPropertyName((String) key), value);
    }
}
