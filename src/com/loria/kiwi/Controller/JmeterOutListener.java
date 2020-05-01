package com.loria.kiwi.Controller;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.http.nio.entity.NByteArrayEntity;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.Clearable;
import org.apache.jmeter.samplers.Remoteable;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

public class JmeterOutListener extends AbstractListenerElement
		implements SampleListener, Clearable, Serializable, TestListener, Remoteable, NoThreadClone {
	public static int countpost_i = 0;
	public static int countget_i = 0;
	public static int posttotsentRequest = 0;
	public static int gettotsentRequest = 0;
	public static long getFirstTimestamp = 0;
	public static long postFirstTimestamp = 0;
	public static long getLastTimestamp = 0;
	public static long postLastTimestamp = 0;
	public static double postelapsedTime;
	public static long getelapsedTime;
	public static double getsuccess = 0;
	public static double getfail = 0;
	public static double postsuccess = 0;
	public static double postfail = 0;
	Task copyWorker;
	Task copyWorker1;

	@FXML
	private ProgressIndicator postprogressindicator;
	@FXML
	private ProgressIndicator getprogressindicator;

	@FXML
	private Label postlabel1;

	@FXML
	private Label postlabel2;

	@FXML
	private Label postlabel3;

	@FXML
	private Label getlabel1;

	@FXML
	private Label getlabel2;

	@FXML
	private Label getlabel3;

	@FXML
	private Label postlabel4;

	@Override
	public void addError(Test test, Throwable e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFailure(Test test, AssertionFailedError e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTest(Test test) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTest(Test test) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sampleOccurred(SampleEvent event) {
		// TODO Auto-generated method stub
		SampleResult sample = event.getResult();
		if (sample.getSampleLabel().contains("POST")) {
			//System.out.println("The label of the request is " + sample.getSampleLabel());
			//System.out.println("the number of countpost_i  "+countpost_i);
			if (countpost_i == 0) {
				postFirstTimestamp = sample.getTimeStamp();
				//System.out.println("First Timestamp   " + postFirstTimestamp);
			} else {
				postLastTimestamp = sample.getTimeStamp();
				//System.out.println("Last Timestamp   " + postLastTimestamp);
			}
			postelapsedTime = postLastTimestamp - postFirstTimestamp;
			countpost_i++;
			posttotsentRequest++;

		}
		if (sample.getSampleLabel().contains("GET")) {
			//System.out.println("The label of the request is " + sample.getSampleLabel());
			countget_i++;
			gettotsentRequest++;
		}

		// System.out.println("La valeur du compteur i est : " + i);
		/*
		 * System.out.println("sampleOccurred().sample.getSampleCount() : " +
		 * sample.getSampleCount());
		 * System.out.println("sampleOccurred().sample.getTimeStamp() : " +
		 * sample.getTimeStamp());
		 * System.out.println("sampleOccurred().sample.getTime() : " +
		 * sample.getTime());
		 * System.out.println("sampleOccurred().sample.getSampleLabel() : " +
		 * sample.getSampleLabel());
		 * System.out.println("sampleOccurred().sample.getResponseCode() : " +
		 * sample.getResponseCode());
		 * System.out.println("sampleOccurred().sample.getResponseMessage() : " +
		 * sample.getResponseMessage());
		 * System.out.println("sampleOccurred().sample.getThreadName() : " +
		 * sample.getThreadName());
		 * System.out.println("sampleOccurred().sample.isSuccessful() : " +
		 * sample.isSuccessful());
		 */

		String message = null;
		AssertionResult[] results = sample.getAssertionResults();
		if (results != null) {
			for (int i = 0; i < results.length; ++i) {
				message = results[i].getFailureMessage();
				// System.out.println("sampleOccurred().message : " + message);
				if (message != null) {
					break;
				}
			}
		}
		if (sample.getResponseCode().equals("200") && sample.getSampleLabel().contains("POST")) {
			postsuccess++;
		}
		if (!sample.getResponseCode().equals("200") && sample.getSampleLabel().contains("POST")) {
			postfail++;
		}
		if (sample.getResponseCode().equals("200") && sample.getSampleLabel().contains("GET")) {
			getsuccess++;
		}
		if (!sample.getResponseCode().equals("200") && sample.getSampleLabel().contains("GET")) {
			getfail++;
		}

		// System.out.println("sampleOccurred().sample.getBytes() : " +
		// sample.getBytes());
		// System.out.println("sampleOccurred().sample.getGroupThreads() : " +
		// sample.getGroupThreads());
		/*
		 * System.out.println("sampleOccurred().sample.getAllThreads() : " +
		 * sample.getAllThreads());
		 * System.out.println("sampleOccurred().sample.getURL() : " + sample.getURL());
		 * System.out.println("sampleOccurred().sample.getLatency() : " +
		 * sample.getLatency()); System.out.println(
		 * "sampleOccurred().sample.getDataEncodingWithDefault() : " +
		 * sample.getDataEncodingWithDefault());
		 * System.out.println("sampleOccurred().sample.getSampleCount() : " +
		 * sample.getSampleCount());
		 * System.out.println("sampleOccurred().sample.getConnectTime() : " +
		 * sample.getConnectTime());
		 * System.out.println("sampleOccurred().sample.getStartTime() : " +
		 * sample.getStartTime());
		 * System.out.println("sampleOccurred().sample.getErrorCount() : " +
		 * sample.getErrorCount());
		 * System.out.println("sampleOccurred().sample.getEndTime() : " +
		 * sample.getEndTime()); System.out
		 * .println("sampleOccurred().sample.getResponseTime : " + (sample.getEndTime()
		 * - sample.getStartTime()));
		 */

	}

	@Override
	public void sampleStarted(SampleEvent event) {

	}

	@Override
	public void sampleStopped(SampleEvent e) {
		// TODO Auto-generated method stub

	}

	public Task postcreateWorker(int k) {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				//System.out.println("La valeur de countpost_i est  " + countpost_i);
				while (countpost_i <= k) {
					updateMessage("");
					updateProgress(countpost_i, k);
					countpost_i = countpost_i;
				}

				return true;
			}

		};

	}

	public Task getcreateWorker(int k) {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				//System.out.println("La valeur de countget_i est  " + countget_i);
				while (countget_i <= k) {
					updateMessage("");
					updateProgress(countget_i, k);
					countget_i = countget_i;
				}

				return true;
			}

		};

	}

	public void postupdatelabels() {
		Platform.runLater(() -> postlabel1.textProperty()
				.bind(new SimpleDoubleProperty(postsuccess * 100 / posttotsentRequest).asString()));
		Platform.runLater(() -> postlabel2.textProperty()
				.bind(new SimpleDoubleProperty(postfail * 100 / posttotsentRequest).asString()));
	}

	public void getupdatelabels() {
		Platform.runLater(() -> getlabel1.textProperty()
				.bind(new SimpleDoubleProperty(getsuccess * 100 / gettotsentRequest).asString()));
		Platform.runLater(() -> getlabel2.textProperty()
				.bind(new SimpleDoubleProperty(getfail * 100 / gettotsentRequest).asString()));
	}

	public void postshowindicator(int k) {

		countpost_i = 0;
		// progressindicator.setProgress(0);

		copyWorker = postcreateWorker(k);

		postprogressindicator.progressProperty().unbind();

		Platform.runLater(() -> postprogressindicator.progressProperty().bind(copyWorker.progressProperty()));
		copyWorker.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//System.out.println(newValue);
			}
		});
		
		new Thread(copyWorker).start();
	}

	public void getshowindicator(int k) {

		countget_i = 0;
		// progressindicator.setProgress(0);

		copyWorker1 = getcreateWorker(k);

		getprogressindicator.progressProperty().unbind();

		Platform.runLater(() -> getprogressindicator.progressProperty().bind(copyWorker1.progressProperty()));
		copyWorker1.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//System.out.println(newValue);
			}
		});

		new Thread(copyWorker1).start();
	}

	public void getshowindicatorS2() {

		Platform.runLater(() -> getlabel3.textProperty().bind(new SimpleDoubleProperty(gettotsentRequest).asString()));

	}

	public void postshowindicatorS2() {

		Platform.runLater(
				() -> postlabel3.textProperty().bind(new SimpleDoubleProperty(posttotsentRequest).asString()));
		//System.out.println("The elapsed Time is " + postelapsedTime);
		postelapsedTime = Math.round(postelapsedTime / 60000);

		Platform.runLater(() -> postlabel4.textProperty().bind(new SimpleDoubleProperty(postelapsedTime).asString()));

	}

	/*public void postshowindicatorS3() {
		System.out.println("the elapased Time is -------------------------  " + postelapsedTime);
		// Platform.runLater(() -> postlabel4.textProperty().bind(new
		// SimpleDoubleProperty(postelapsedTime).asString()));

	}*/

}
