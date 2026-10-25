package com.edureka.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonData 
{
	
	public String getData(String key) throws IOException
	{
		FileInputStream fis = new FileInputStream("./src/main/resources/CommonData.properties");
		
		Properties p = new Properties();
		
		p.load(fis);
		
		return p.getProperty(key);
	}

}
