package com.kenny.huffman;

import static com.kenny.huffman.Utils.*;

public class Main {
	public static void main(String[] args) {
    	while(true) {
		    print("\nВведите строку: ");
			
			Huffman huf = new Huffman();
			String data = input();
		        
		    String encoded = huf.encodeData(data.toCharArray());
		    String decoded = huf.decodeData(encoded.toCharArray(), huf.tree);
		        
		    print("==========================");
		    print("Строка оригинал: " + data);
			print("Вес: " + data.getBytes().length * 8 + " бит");
			print("==========================");
			print("Закодированная строка: " + encoded);
			print("Бит на символ: " + huf.bitmap.toString());
			print("Вес: " + encoded.length() + " бит");
			print("==========================");
			print("Раскодированна строка: " + decoded);
			print("Вес: " + decoded.getBytes().length * 8 + " бит");
    	}
	}
}
