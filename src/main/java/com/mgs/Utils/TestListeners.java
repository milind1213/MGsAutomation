package com.mgs.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mgs.CommonConstants;
import com.mgs.DriverUtils.WebBrowser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import static com.mgs.Utils.FileUtil.*;

@Listeners(TestListeners.class)
public class TestListeners extends WebBrowser implements ITestListener {
	public static ExtentReports extentReports;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private String reportPath;

	@Override
	public synchronized void onStart(ITestContext context) {
		reportPath = System.getProperty("user.dir") + "/reports/";
		Path reportDirPath = Paths.get(reportPath);
		createDirectoryIfNotExists(reportDirPath);
		deleteOldReports(reportDirPath);
		String reportFilePath = FileUtil.generateReportFilePath(reportPath);
		extentReports = ExtentManager.createInstance(reportFilePath, "Automation Test Report", "Test Execution Report");
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName = FileUtil.enhancedMethodName(result.getMethod().getMethodName());
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last);
		System.out.println("[============== Started Test method [" + methodName + "] =========]");

		ExtentTest test = extentReports.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.assignCategory(result.getTestContext().getSuite().getName());
		test.assignCategory(className);
		extentTest.set(test);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(result.getStartMillis());
		Date startTime = calendar.getTime();
		extentTest.get().getModel().setStartTime(startTime);
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		String failureScreenshot = "";
		String className = result.getMethod().getRealClass().getSimpleName();
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		if (webDriver.get() != null) {
			failureScreenshot = ((TakesScreenshot) webDriver.get()).getScreenshotAs(OutputType.BASE64);
		}
		if (!failureScreenshot.isEmpty()) {
			extentTest.get().fail(
					"<details><summary><b><font color='red'>Exception Occurred: Click to View</font></b></summary>"
							+ exceptionMessage.replaceAll(",", "<br>") + "</details>");
			extentTest.get().fail("<b><font color='red'>Screenshot of Failure</font></b>",
					MediaEntityBuilder.createScreenCaptureFromBase64String(failureScreenshot).build());
		} else if (className.toLowerCase().contains("api")) {
			extentTest.get().fail(MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
			String stackTrace = exceptionMessage.replaceAll(",", "<br>");
			extentTest.get().fail(
					"<details><summary><b><font color='red'>Exception Occurred: Click to View</font></b></summary>"
							+ stackTrace + "</details>");
		}
		String failureLog = "<b>Test Failed! \uD83D\uDE12 </b>";
		Markup m = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		String logText = "<b>" + "Test Passed! \uD83D\uDE0A " + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().pass(m);
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		String methodName = FileUtil.enhancedMethodName(result.getMethod().getMethodName());
		String logText = "<b>" + "Test Case: '" + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.INDIGO);
		extentTest.get().skip(m);
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println("[============== Finish Test method [" + context.getName() + "] =========]");
		if (extentReports != null) {
			extentReports.flush();
		}
		EmailReportingUtils report = new EmailReportingUtils();
		report.sendReport();
		// SlackIntegration slackIntegration = new SlackIntegration(getProperty(CommonConstants.COMMON, CommonConstants.MGS_SLACK_TOKEN),
		//		getProperty(CommonConstants.COMMON, CommonConstants.MGS_SLACK_CHANENEL));
		// slackIntegration.sendTestExecutionReportToSlack(reportPath, "Test Execution Report");
	}
}