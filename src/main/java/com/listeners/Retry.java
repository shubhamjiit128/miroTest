package com.listeners;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.extentreports.ExtentTestManager;
import com.utility.Constants;

public class Retry implements IRetryAnalyzer {
	public int retryCount = 1;
//	private int maxRetryCount = 1; // Run the failed test 2 times
	public ScreenRecorder screenRecorder;
	String RecordingClassName;
	String RecordingMethodName;
	File RecordedFile;
	String fileRecordingLocation;

	/**
	 * Below method returns 'true' if the test method has to be retried else 'false'
	 * and it takes the 'Result' as parameter of the test method that just ran
	 */
	public boolean retry(ITestResult iTestResult) {
		try {
			if (retryCount < Constants.maxRetryCount) {
				ExtentTestManager.removeTest();
				startRecording();
				retryCount++;
				return true;
			}
			stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getResultStatusName(int status) {
		String resultName = null;
		if (status == 1)
			resultName = "SUCCESS";
		if (status == 2)
			resultName = "FAILURE";
		if (status == 3)
			resultName = "SKIP";
		return resultName;
	}

	/**
	 * This method will start the recording.
	 */

	public void startRecording() throws IOException, AWTException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
		String dateAsString = simpleDateFormat.format(new Date());
		System.out.println(dateAsString);
		RecordingClassName = new Exception().getStackTrace()[2].getClassName();
		RecordingMethodName = new Throwable().fillInStackTrace().getStackTrace()[2].getMethodName();
		fileRecordingLocation = "c://Execution Videos//" + RecordingClassName + "_" + RecordingMethodName + "_"
				+ dateAsString;
		System.out.println(fileRecordingLocation);
		RecordedFile = new File(fileRecordingLocation);

		GraphicsConfiguration gc = GraphicsEnvironment//
				.getLocalGraphicsEnvironment()//
				.getDefaultScreenDevice()//
				.getDefaultConfiguration();
		try {
			screenRecorder = new ScreenRecorder(gc, null,
					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, (int) 24, FrameRateKey,
							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, (int) (15 * 60)),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
					null, RecordedFile);

			screenRecorder.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will stop the recording.
	 */
	public void stopRecording() {
		if (screenRecorder != null) {
			try {
				screenRecorder.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}