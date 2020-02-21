package driverScript;

import org.testng.annotations.Test;
import java.io.File;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.Excelfileutil;
import commonFunctionLibrary.FunctionLibrary;


 
public class AppTest {
	public static WebDriver driver;
	static ExtentReports report;
	static ExtentTest test;
  @Test
  public void f() throws Exception{

		Excelfileutil excel=new Excelfileutil();
		
		for(int i=1;i<=excel.rowCount("MasterTestCases");i++){
			
			String ModuleStatus="";
			
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y")){
				String TCModule=excel.getData("MasterTestCases", i, 1);
			
				report=new ExtentReports(System.getProperty("user.dir")+"Reports\\"+TCModule+FunctionLibrary.generateDate()+".html");
				test=report.startTest(TCModule);
				for(int j=1;j<=excel.rowCount(TCModule);j++){
					
				    String Description=excel.getData(TCModule, j, 0);
					String Function_Name=excel.getData(TCModule, j, 1);
					String	Locator_Type=excel.getData(TCModule, j, 2);
					String Locator_Value=excel.getData(TCModule, j, 3);
					String Test_Data=excel.getData(TCModule, j, 4);
					try{
						if(Function_Name.equalsIgnoreCase("startBrowser")){
						      driver=FunctionLibrary.startBrowser();
						      test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("openApplication")){
							 FunctionLibrary.openApplication(driver);
							 test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("waitForElement")){
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("typeAction")){
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("clickAction")){
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("closeBrowser")){
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureData")){
							FunctionLibrary.captureData(driver,  Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("tableValidation")){
							FunctionLibrary.tableValidation(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						excel.setData(TCModule, j, 5, "PASS");
						ModuleStatus="PASS";
						test.log(LogStatus.PASS, Description);
						
					}catch(Exception e){
						System.out.println("the exception is ");
						e.printStackTrace();
						excel.setData(TCModule, j, 5, "FAIL");
						ModuleStatus="FAIL";
						String reqdate=FunctionLibrary.generateDate();
						
						File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\Screenshots\\"+Description+reqdate+".png"));
					   
						test.log(LogStatus.FAIL,Description);
					   
					    test.log(LogStatus.INFO, test.addScreenCapture(System.getProperty("user.dir")+"\\Screenshots\\"+Description+reqdate+".png"));
						
					    break;
					}
				}
				
				if(ModuleStatus.equalsIgnoreCase("Pass")){
					excel.setData("MasterTestCases",i,3,"Pass");
				}else{
					excel.setData("MasterTestCases",i,3,"Fail");
				}
				
				
			}else{
				excel.setData("MasterTestCases", i, 3,"Not executed");
			}
			
		}

	}

}
