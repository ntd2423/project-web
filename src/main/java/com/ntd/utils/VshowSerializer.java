/**
 * @(zhajunjun)EstateSerializer.java, 2013-8-14. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ntd.utils;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class VshowSerializer {

	public static ObjectMapper json = new ObjectMapper();

	static {
        json.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
		.configure(Feature.ALLOW_SINGLE_QUOTES, true)
		.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
