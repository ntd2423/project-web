package com.ntd.utils;

/**
 * 二元组工具类
 *
 * @param <T> 第一个元素类型
 * @param <U> 第二个元素类型
 */
public class Tuple<T, U> {
	public final T first;
	public final U second;

	public Tuple(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public U getSecond() {
		return second;
	}
	
	public static <T, U> Tuple<T, U> of(T first, U second) {
		return new Tuple<>(first, second);
	}

	@Override
	public String toString() {
		return "Tuple [first=" + first + ", second=" + second + "]";
	}
}
