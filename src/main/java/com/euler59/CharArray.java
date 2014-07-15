package com.euler59;

public class CharArray
{
    public final char[] data;
    
    public CharArray(char[] data)
    {
	this.data = data;
    }
    
    public final char get(int position)
    {
	return data[position];
    }
    
    public final void put(int position, char data)
    {
	this.data[position] = data;
    }
    
    public final int length()
    {
	return data.length;
    }
}
