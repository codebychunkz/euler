package com.euler59;

public class CipherDataWrapper
{
    private final CharArray rawData;
    
    private final int offset;
    
    private final int numWorkers;
    
    private final int size;
    
    public CipherDataWrapper(CharArray rawData, int offset, int numWorkers)
    {
	this.rawData = rawData;
	this.offset = offset;
	this.numWorkers = numWorkers;
	//this.size = rawData.length() / numWorkers;
	
	int temp = rawData.length() / numWorkers;
	
	//FIX THIS HACK!
	if(offset == 0)
	{
	    this.size = temp + 1;
	}
	else
	{
	    this.size = temp;
	}
    }
    
    public final char getAt(int position)
    {
	return rawData.get(numWorkers * position + offset);
    }
    
    public final void replace(int position, char data)
    {
	rawData.put(numWorkers * position + offset, data);
    }
    
    public final int size()
    {
	return size;
    }
}
