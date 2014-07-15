package com.euler59;

import java.util.concurrent.Callable;

public class XORDeciperTask implements Callable<Void>
{
    private static final int WHITESPACE = 32;
    
    private final CipherDataWrapper ciperData;

    public XORDeciperTask(CipherDataWrapper ciperData)
    {
	this.ciperData = ciperData;
    }

    @Override
    public Void call() throws Exception
    {
	char whitespace = findWhitespaceCharacter();
	int key = getDeciperingKey(whitespace);
	
	decipherAndUpdate(key);
	
	return null;
    }

    private void decipherAndUpdate(int decipherKey)
    {
	for(int index = 0; index < ciperData.size(); index++)
	{
	    int data = ciperData.getAt(index);
	    data = data^decipherKey;
	    ciperData.replace(index, (char)data);
	}
    }

    private int getDeciperingKey(char whitespace)
    {
	return whitespace^WHITESPACE;
    }

    private char findWhitespaceCharacter()
    {
	int[] tempHitCount = new int[255];

	for(int index = 0; index < ciperData.size(); index++)
	{
	    char data = ciperData.getAt(index);
	    tempHitCount[data] += 1;
	}

	int highestIndex = findHighestValueIndex(tempHitCount);
	
	return (char)highestIndex;
    }
    
    private int findHighestValueIndex(int[] values)
    {
	int maxIndex = -1;
	int maxValue = -1;
	
	for(int index = 0; index < values.length; index++)
	{
	    if(values[index] > maxValue)
	    {
		maxIndex = index;
		maxValue = values[index];
	    }
	}
	
	return maxIndex;
    }
}
