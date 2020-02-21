package Utilities;
import java.io.FileInputStream;

import java.util.Properties;

public class propertyfileutil {
	public static String getvalueForKey(String Key)throws Exception{
		Properties p=new Properties();
		FileInputStream fis=new FileInputStream("D:\\pavani_82\\StockAccounting_Hybrid\\PropertiesFile\\Environment.prpoerties");
		p.load(fis);
		return p.getProperty(Key);
		
	}

}

