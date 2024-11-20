package com.mgs.Utils.Reporting;

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
import com.mgs.Utils.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

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
		reportPath = System.getProperty("user.dir") + "/Reports/";
		Path reportDirPath = Paths.get(reportPath);
		createDirectoryIfNotExists(reportDirPath);
		deleteOldReports(reportDirPath);
		String reportFilePath = FileUtil.generateReportFilePath(reportPath);
		extentReports = ExtentManager.createInstance(reportFilePath, "Automation Test Report", "Test Execution Report");

		if (extentReports == null) {
			throw new IllegalStateException("ExtentReports failed to initialize.");
		}
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		String className = qualifiedName.substring(qualifiedName.lastIndexOf('.', qualifiedName.lastIndexOf('.') - 1) + 1, qualifiedName.lastIndexOf('.'));

		System.out.printf("[============== Started Test method [%s] =========]%n", methodName);

		ExtentTest test = extentReports.createTest(methodName, result.getMethod().getDescription());
		test.assignCategory(result.getTestContext().getSuite().getName(), className);
		extentTest.set(test);

		extentTest.get().getModel().setStartTime(new Date(result.getStartMillis()));
	}


	@Override
	public synchronized void onTestFailure(ITestResult result) {
		String failureScreenshot = "";
		String className = result.getMethod().getRealClass().getSimpleName();
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		if (webDriver.get() != null) {
			failureScreenshot = ((TakesScreenshot) webDriver.get()).getScreenshotAs(OutputType.BASE64);
		}
		if (extentTest.get() != null) {
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
		} else {
			System.err.println("ExtentTest is null for Test Failure logging.");
		}
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		String logText = "<b>" + "Test Passed! \uD83D\uDE0A " + "</b>";
		if (extentTest.get() != null) {
			extentTest.get().pass(MarkupHelper.createLabel(logText, ExtentColor.GREEN));
		} else {
			System.err.println("ExtentTest is null for Test Success logging.");
		}
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		String methodName = FileUtil.enhancedMethodName(result.getMethod().getMethodName());
		String logText = "<b>" + "Test Case: '" + methodName + " Skipped" + "</b>";
		if (extentTest.get() != null) {
			extentTest.get().skip(MarkupHelper.createLabel(logText, ExtentColor.INDIGO));
		} else {
			System.err.println("ExtentTest is null for Test Skipped logging.");
		}
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println("[============== Finish Test method [" + context.getName() + "] =========]");
		if (extentReports != null) {
			extentReports.flush();
		}
		// sendExecutionReport(reportPath);
	}

	private static synchronized void sendExecutionReport(String reportPath) {
		String slackToken = getProperty(CommonConstants.COMMON, CommonConstants.MGS_SLACK_TOKEN);
		String slackChannel = getProperty(CommonConstants.COMMON, CommonConstants.MGS_SLACK_CHANENEL);
		// Send report to Slack
		if (slackToken != null && slackChannel != null) {
			new ReportingUtilSlack(slackToken, slackChannel)
					.sendTestExecutionReportToSlack(reportPath, "Test Execution Report");
		}
		// Send report via Email
		new ReportingEmailUtils().sendExecutionReport();
	}

}