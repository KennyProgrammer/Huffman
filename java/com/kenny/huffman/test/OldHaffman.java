package com.kenny.huffman.test;

import static com.kenny.huffman.Utils.*;
import java.util.*;

public class OldHaffman
{	
	//Map contains the char-bits pair
	private Map<String, String> charbits = new TreeMap<String, String>();
	
	public void addToCharbits(String c1, String c2, int bit1, int bit2)
	{
		if(!charbits.containsKey(c1))
			charbits.put(c1, "" + bit1);
		else
			charbits.put(c1, charbits.get(c1) + bit1);
		
		if(!charbits.containsKey(c2))
			charbits.put(c2, "" + bit2);
		else
			charbits.put(c2, charbits.get(c2) + bit2);
	}
	
	//Internal/Base algorithm with Maps
	public void copressData(Map<String, Float> data, int count)
	{
		//final int byteVal = 2;
		
		//Check conditions
		if(data == null || data.size() == 0)
			throw new NullPointerException("Data map is null or his length zero!");
		
		int dataCount = data.size();
		if(count > 0)//that means count is set by user
			if(dataCount != count)
				throw new RuntimeException("User count: "+count+" is not data count of input elements: " + dataCount);
		else count = dataCount; // set form data length
		
		//Create dynamic arr 'dicreaser' from count to rem small elm
		Set<String> cset = data.keySet();
		List<Float> dicreaser = new ArrayList<Float>(count);
		String[] charArr = cset.toArray(new String[cset.size()]);
		for(int i = 0; i < cset.size(); i++)
			dicreaser.add(data.get(charArr[i]));
		
		print("Char-Chance: " + data.toString() + "\n");
		print("Chances: " + dicreaser.toString());
		while(dicreaser.get(0) != 1.0f)
		{
			//Finding min1 and min2 values
			float min1 = findMin(dicreaser.toArray(new Float[0]));
			dicreaser.remove(min1);
			float min2 = findMin(dicreaser.toArray(new Float[0]));
			dicreaser.remove(min2);
			
			int bit1 = 0, bit2 = 0;
			if(min1 > min2) bit1++;
			else            bit2++;
			
			//add two mins as result (A + B) (AB = sum)
			float sum = min1 + min2;
			if(min1 != min2)
			{
				String c1 = getKeyByValue(data, min1);
				String c2 = getKeyByValue(data, min2);
				data.put(c1 + c2, sum);
				this.addToCharbits(c1, c2, bit1, bit2);
							
			} else {
				Set<String> set = getKeysByValue(data, min1); //Set with keys witch have same values
				String[] setArr = set.toArray(new String[set.size()]);
				data.put(setArr[0] + setArr[1], sum);
				for(int i = 0; i < set.size(); i++)
					this.addToCharbits(setArr[0], setArr[1], bit1, bit2);
			}
			dicreaser.add(sum);
		
			//debug output
			print("Min1: " + min1);
			print("Min2: " + min2);
			print("Chances: " + dicreaser.toString());
		}
		print("Char-Chance: " + data.toString() + "\n");
	}
}
