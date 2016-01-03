package com.mycompany.app;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class VideoRecorder {

	private final String RECORD_DIRECTORY = "c:\\temp\\";
	private ScreenRecorder screenRecorder;
	public void startRecording(WebDriver driver) {

		try {
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			File dir = new File(RECORD_DIRECTORY);

			Point point = driver.manage().window().getPosition();
			Dimension dimension = driver.manage().window().getSize();

			Rectangle rectangle = new Rectangle(point.x, point.y, dimension.width, dimension.height);
			this.screenRecorder = new ScreenRecorder(gc, rectangle, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,CompressorNameKey,ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60), new Format( MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, dir);
			this.screenRecorder.start();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void stopRecording(String recordName) {

		try {
			this.screenRecorder.stop();

			if (recordName != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH.mm.ss");
				File newFileName = new File(String.format("%s%s %s.avi", RECORD_DIRECTORY, recordName, dateFormat.format(new Date())));
				this.screenRecorder.getCreatedMovieFiles().get(0).renameTo(newFileName);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
