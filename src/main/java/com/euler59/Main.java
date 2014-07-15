package com.euler59;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

public class Main
{
    public static void main(String[] args) throws InterruptedException,
	    ExecutionException
    {
	Preconditions.checkArgument(args.length >= 2,String.format("Expected 2 arguments but got %s", args.length));
	File cipherFile = new File(args[0]);
	int ciperFileSize = Integer.valueOf(args[1]);

	Preconditions.checkArgument(cipherFile.exists(),
	        String.format("The file %s does not exist!", cipherFile.getAbsoluteFile()));
	Preconditions.checkArgument(ciperFileSize > 0,
		String.format("Expected 1 or higher but got %s", ciperFileSize));

	Stopwatch stopwatch = Stopwatch.createStarted();
	char[] cipherData = readCipherFile(cipherFile);
	cipherData = filterData(cipherData);

	CharArray dataWrapper = new CharArray(cipherData);
	List<XORDeciperTask> tasks = new ArrayList<>(ciperFileSize);
	for (int index = 0; index < ciperFileSize; index++)
	{
	    tasks.add(new XORDeciperTask(new CipherDataWrapper(dataWrapper,
		    index, ciperFileSize)));
	}

	DecipherEngine engine = new DecipherEngine();
	engine.submitAndWait(tasks);
	stopwatch.stop();

	engine.shutDown();

	int count = 0;
	for (int index = 0; index < dataWrapper.length(); index++)
	{
	    System.out.print(dataWrapper.get(index));
	    count += dataWrapper.get(index);
	}
	

	System.out.println("\nEND, EXEC TIME: "
	        + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms\n");
	System.out.println(count);

    }

    private static char[] filterData(char[] cipherData)
    {
	String[] temp = new String(cipherData).split(",");
	char[] data = new char[temp.length];

	int count = 0;
	for (String str : temp)
	{
	    data[count] = (char) ((int) Integer.valueOf(str));
	    count++;
	}

	return data;
    }

    private static char[] readCipherFile(File file)
    {
	CharArrayWriter writer = new CharArrayWriter();
	try (Reader reader = new BufferedReader(new FileReader(file)))
	{
	    int read = 0;
	    char[] buffer = new char[8192];
	    while ((read = reader.read(buffer)) != -1)
	    {
		writer.write(buffer, 0, read);
	    }
	}
	catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return writer.toCharArray();
    }
}
