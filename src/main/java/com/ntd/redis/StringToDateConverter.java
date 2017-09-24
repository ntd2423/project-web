package com.ntd.redis;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringToDateConverter implements Converter {

	private static Log logger = LogFactory.getLog(StringToDateConverter.class);

	private String pattern;

	public StringToDateConverter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Object convert(Class type, Object value) {
		if (value == null)
			return null;
		if (value instanceof String) {
			String tmp = (String) value;
			if (tmp.trim().length() == 0)
				return null;
			else {
				try {
					return DateUtils.parseDate(tmp, pattern);
				} catch (ParseException e) {
					logger.warn("转换时间字段时发生异常", e);
				}
			}
		} else
			throw new ConversionException("not String");
		return value;
	}

	public static void main(String[] args){
		String input = "1990-12-31 12:12:12";
		String pattern = "yyyy-MM-dd HH:mm:ss";
		StringToDateConverter converter = new StringToDateConverter(pattern);
		System.out.println(converter.convert(Date.class, input));
	}

}
