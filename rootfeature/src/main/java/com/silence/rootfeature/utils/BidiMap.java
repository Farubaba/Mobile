/* Copyright (c) 2014 Youku TV. All rights reserved.*/ 
package com.silence.rootfeature.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class BidiMap<K, V> implements Iterable<Entry<K, V>> {

	private final HashMap<K, V> forward;
	private final HashMap<V, K> reverse;

	public BidiMap() {
		forward = new HashMap<K, V>();
		reverse = new HashMap<V, K>();
	}

	public void put(K k, V v) {
		forward.put(k, v);
		reverse.put(v, k);
	}

	public K getKey(V v) {
		return reverse.get(v);
	}

	public int size() {
		return forward.size();
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return forward.entrySet().iterator();
	}

	public V getValue(K k) {
		return forward.get(k);
	}

	public static void main(String[] argv) {
		BidiMap<String, Integer> map = new BidiMap<String, Integer>();

		map.put("A", 1);
		map.put("B", 2);
		map.put("C", 3);
		map.put("D", 4);
		map.put("E", 5);

		System.out.println(map.getKey(1));

		System.out.println(map.getKey(3));

		System.out.println(map.getKey(5));

		System.out.println(map.getValue("A"));
		System.out.println(map.getValue("C"));
		System.out.println(map.getValue("E"));
	}

}
