package com.kenny.huffman;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class Utils 
{
	public static void print(String msg) 
	{ 
		System.out.print(msg + "\n"); 
	}
	
	public static void print(char[] msg) 
	{ 
		System.out.println(msg); 
	}
	
	//Console input utility
	public static String input()
	{
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in).useDelimiter("\n");
		String input = in.next();
		in = null;
		return input;
	}
	
	//Utility
	public static float findMin(Float[] array)
	{
        float min = array[0];
        for (int i = 1; i < array.length; i++) 
        {
            if (array[i] < min) min = array[i];
        }
        return min;
    }
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) 
	{
	    for (Entry<T, E> entry : map.entrySet())
	        if (Objects.equals(value, entry.getValue())) 
	            return entry.getKey();
	    return null;
	}
	
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    Set<T> keys = new HashSet<T>();
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
}
