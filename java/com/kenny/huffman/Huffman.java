package com.kenny.huffman;

import        java.util.*;
import static java.util.Collections.*;

public class Huffman 
{
	public Node tree;
	public Map<Character, String> bitmap;
	public String encoded, decoded;
	private boolean oneType = false;
	
	//Tree node, or left, right nodes
    static class Node implements Comparable<Node>  
    {
        char data;
        int  weight;
        Node left, right;

        public Node(char data, int weight)
        {
            this.data = data;
            this.weight = weight;
        }

        public Node(char data, int weight, Node left, Node right) 
        {
            this.data = data;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        public String getCharBitcode(char data, String parentbit) 
        {
        	if(this.data == data) return parentbit;
			else {
				if(left != null)
				{
					String bit = left.getCharBitcode(data, parentbit + 0);
					if(bit != null) return bit;
				}	
				if(right != null)
				{
					String bit = right.getCharBitcode(data, parentbit + 1);
					if(bit != null) return bit;
				}
			}
			return null; //Means we dont find usable path 
        }
        public int compareTo(Node o) { return o.weight - this.weight; }
    }
	
	public TreeMap<Character, Integer> getCharChances(char[] data)
	{
		TreeMap<Character, Integer> chancesMap = new TreeMap<Character, Integer>();
		for(char ch : data)
		{
			//get the chance and add it to map
			int chance = chancesMap.get(ch) != null ? chancesMap.get(ch) + 1 : 1;
			chancesMap.put(ch, chance);
		}
		return chancesMap;
	}

	public String encodeData(char[] data)
	{
		if(data.length <= 0) 
			return "";
		
		// Add notes for each char
        List<Node> nodes = new ArrayList<Node>();
        Map<Character, Integer> chancesMap = getCharChances(data);
        for(char ch : chancesMap.keySet())
            nodes.add(new Node(ch, chancesMap.get(ch)));
        
        // Case if in our data one type of char
        if(nodes.size() == 1)
        	oneType = true;
        
        // Build a tree from nodes using Huffman aloritm
        while (nodes.size() > 1) 
        {
        	sort(nodes);
                
            Node left = nodes.remove(nodes.size() - 1);
            Node right = nodes.remove(nodes.size() - 1);
            nodes.add(new Node((char) 0, right.weight + left.weight, left, right));
        }
        tree = nodes.get(0);
        
        // In bitmap put the char and its bits/count
        bitmap = new TreeMap<Character, String>();
        for(char ch : chancesMap.keySet())
            bitmap.put(ch, oneType ? "0" : tree.getCharBitcode(ch, ""));
        
        // Encode each char in bit string
        encoded = "";
        for(char ch : data)
        	 encoded += bitmap.get(ch);
        
        return encoded;
	}
	
	public String decodeData(char[] data, Node tree)
	{
		if(tree == null) throw new NullPointerException("Tree node is null!");
		
		Node node = tree;
		decoded = "";
		// Decode each char from bit in to char string, include oneType situation
		for(char ch : data) {
			if(oneType) {
				decoded += node.data;
				node = tree;
				continue;
			}
		
			node = (ch == '0') ? node.left : node.right;
			if(node.data != 0) {
				decoded += node.data;
				node = tree;
			}
		}
		return decoded;
	}
}