package com.loria.kiwi.Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.AuthManager;
import org.apache.jmeter.protocol.http.control.Authorization;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.gui.AuthPanel;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPFileArg;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.GaussianRandomTimer;
import org.apache.jmeter.timers.PoissonRandomTimer;
import org.apache.jmeter.timers.TimerService;
import org.apache.jmeter.timers.UniformRandomTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.loria.kiwi.Model.Configuration;
import com.loria.kiwi.Model.XAPIStatement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * @author Chahrazed LABBA
 *
 */
public class PostRuningTestController {

	@FXML
	private ProgressBar progressBar;
	@FXML
	private JmeterOutListener anchor3Controller;

	private Stage dialogStage;
	private boolean okClicked;
	private Task copyWorker;
	private String logFile = "resultat"+System.currentTimeMillis()+".csv";

	

	@FXML
	private void initialize() {

	}

	@FXML
	private void showResults() throws IOException {
		Path source = Paths.get("report-output");
		File file = new File("report-output_"+logFile);
	    file.mkdir();
	    Path newdir = Paths.get("report-output");
		Files.copy(source, newdir);
		File htmlFile = new File("report-output/index.html");
		Desktop.getDesktop().browse(htmlFile.toURI());

	}

	public void showprogressBar(Configuration configuration) {
		System.out.println("It is running here");
		progressBar.setProgress(0);
		copyWorker = createPostWorker(configuration);
		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(copyWorker.progressProperty());
		copyWorker.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			}
		});
		new Thread(copyWorker).start();

	}

	//
	public Task<Object> createPostWorker(Configuration configuration) {
		return new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				String jsonpath = "";
				int runbreakTime = configuration.getbreakTimeRuns();
				int breaktime = configuration.gettimeobj().gettimevalue();
				int duration = configuration.gettestduration();
				if (configuration.getScenario().get_postscenarioType().contains("Scenario_1")) {
					// Fixed Time Interval && Fixed Request Number && Fixed Statement Number
					generateJsonBody(configuration.getstatementNumber());
					jsonpath = "JsonFiles/learner" + configuration.getstatementNumber() + ".json";
					// Convertir le temps en milliseconds
					if (configuration.gettimeunit1().equals("Day")) {
						runbreakTime = (int) TimeUnit.DAYS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Hour")) {
						runbreakTime = (int) TimeUnit.HOURS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Minute")) {
						runbreakTime = (int) TimeUnit.MINUTES.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Second")) {
						runbreakTime = (int) TimeUnit.SECONDS.toMillis(runbreakTime);
					}

					if (configuration.gettimeobj().gettimeunit().equals("Day")) {
						breaktime = (int) TimeUnit.DAYS.toMillis(breaktime);
					} else if (configuration.gettimeobj().gettimeunit().equals("Hour")) {
						breaktime = (int) TimeUnit.HOURS.toMillis(breaktime);
					} else if (configuration.gettimeobj().gettimeunit().equals("Minute")) {
						breaktime = (int) TimeUnit.MINUTES.toMillis(breaktime);
						System.out.println("The break Time is ---------------- " + breaktime);
					} else if (configuration.gettimeobj().gettimeunit().equals("Second")) {
						breaktime = (int) TimeUnit.SECONDS.toMillis(breaktime);
					}

					// Loop For to Run the test scenario 1:
					for (int i = 0; i < configuration.getnumberTestRuns(); i++) {
						System.out.println(
								"----------------       the iteration Number  " + configuration.getnumberTestRuns());
						System.out.println("----------------       Starting The Round Number " + i);
						
						File jmeterProperties = new File("jmeter.properties");
						// StandardJMeterEngine jm = new StandardJMeterEngine();
						JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
						JMeterUtils.initLocale();
						if (i != 0) {
							Thread.sleep(runbreakTime);
						}
						if (jmeterProperties.exists()) {
							// Authorization
							AuthManager authManager = getauthManager(configuration);

							// Header Mananger
							HeaderManager manager = getHeaderMananger();

							// constant Timer
							ConstantTimer constantTimer = getConstantTimer(breaktime);

							// http sampler
							HTTPSamplerProxy httpSampler = getHttpSampler(configuration, i, jsonpath);

							// loop controller
							TestElement loopCtrl = getloopController(configuration);

							// thread group
							SetupThreadGroup threadGroup = getthreadGroup(configuration, loopCtrl);

							// Test Plan
							TestPlan testPlan = gettestPlan();

							// the rest
							getenginerun(jmeterProperties, httpSampler, manager, constantTimer, testPlan, threadGroup,
									authManager, configuration);

							updateMessage(configuration.getbreakTimeRuns() + " milliseconds");
							updateProgress(i + 1, configuration.getnumberTestRuns());
							System.out.println("----------------       FINISHED THE ROUND NUMBER " + i);

						} else {
							System.out.println("The jmeter.proprieties File is missing ");
						}

					}

				}
				if (configuration.getScenario().get_postscenarioType().contains("Scenario_2")) {
					// Set duration, Fixed statement number
					generateJsonBody(configuration.getstatementNumber());
					jsonpath = "JsonFiles/learner" + configuration.getstatementNumber() + ".json";

					if (configuration.gettimeunit1().equals("Day")) {
						runbreakTime = (int) TimeUnit.DAYS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Hour")) {
						runbreakTime = (int) TimeUnit.HOURS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Minute")) {
						runbreakTime = (int) TimeUnit.MINUTES.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Second")) {
						runbreakTime = (int) TimeUnit.SECONDS.toMillis(runbreakTime);
					}

					if (configuration.gettimeunit2().equals("Day")) {
						duration = (int) TimeUnit.DAYS.toSeconds(duration);
					} else if (configuration.gettimeunit2().equals("Hour")) {
						duration = (int) TimeUnit.HOURS.toSeconds(duration);
					} else if (configuration.gettimeunit2().equals("Minute")) {
						duration = (int) TimeUnit.MINUTES.toSeconds(duration);
					}

					// Loop For to Run the test scenario 2:
					for (int i = 0; i < configuration.getnumberTestRuns(); i++) {
						System.out.println(
								"----------------       the iteration Number  " + configuration.getnumberTestRuns());
						System.out.println("the duration is " + duration);
						System.out.println("----------------       Starting The Round Number " + i);
						File jmeterProperties = new File("jmeter.properties");
						// StandardJMeterEngine jm = new StandardJMeterEngine();
						JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
						JMeterUtils.initLocale();
						if (i != 0) {
							Thread.sleep(runbreakTime);
						}
						if (jmeterProperties.exists()) {
							// Authorization
							AuthManager authManager = getauthManager(configuration);
							// Header Mananger
							HeaderManager manager = getHeaderMananger();

							// http sampler
							HTTPSamplerProxy httpSampler = getHttpSampler(configuration, i, jsonpath);

							// loop controller
							TestElement loopCtrl = getloopControllerS2(configuration);

							SetupThreadGroup threadGroup = getthreadGroupS2(configuration, loopCtrl, duration);

							// Test Plan
							TestPlan testPlan = gettestPlan();
							// the rest
							getenginerunS2(jmeterProperties, httpSampler, manager, testPlan, threadGroup, authManager,
									configuration);

							updateMessage(configuration.getbreakTimeRuns() + " milliseconds");
							updateProgress(i + 1, configuration.getnumberTestRuns());
							System.out.println("----------------       FINISHED THE ROUND NUMBER " + i);

						} else {
							System.out.println("The jmeter.proprieties File is missing ");
						}

					}

				}
				if (configuration.getScenario().get_postscenarioType().contains("Scenario_3")) {
					// Set duration, random Time Interval, fixed statement number
					generateJsonBody(configuration.getstatementNumber());
					jsonpath = "JsonFiles/learner" + configuration.getstatementNumber() + ".json";
					

					if (configuration.gettimeunit1().equals("Day")) {
						runbreakTime = (int) TimeUnit.DAYS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Hour")) {
						runbreakTime = (int) TimeUnit.HOURS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Minute")) {
						runbreakTime = (int) TimeUnit.MINUTES.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Second")) {
						runbreakTime = (int) TimeUnit.SECONDS.toMillis(runbreakTime);
					}

					if (configuration.gettimeunit2().equals("Day")) {
						duration = (int) TimeUnit.DAYS.toSeconds(duration);
					} else if (configuration.gettimeunit2().equals("Hour")) {
						duration = (int) TimeUnit.HOURS.toSeconds(duration);
					} else if (configuration.gettimeunit2().equals("Minute")) {
						duration = (int) TimeUnit.MINUTES.toSeconds(duration);
					}
					
					// Add method to generate time dans un csv file
					configuration.setnumberRequest(duration);
					System.out.println("number of requests "+configuration.getnumberRequest());
					ArrayList<Long> arraytime = time_Files(configuration.getnumberRequest(),
							configuration.gettimeobj().getgenerationway(), configuration.getrange(),
							configuration.getconstant());
					generateCSVfileTime(arraytime);

					for (int i = 0; i < configuration.getnumberTestRuns(); i++) {
						System.out.println(
								"----------------       the iteration Number  " + configuration.getnumberTestRuns());
						System.out.println("----------------       Starting The Round Number " + i);
						File jmeterProperties = new File("jmeter.properties");
						// StandardJMeterEngine jm = new StandardJMeterEngine();
						JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
						JMeterUtils.initLocale();
						if (i != 0) {
							Thread.sleep(runbreakTime);
						}
						if (jmeterProperties.exists()) {

							// Authorization
							AuthManager authManager = getauthManager(configuration);
							// Header Mananger
							HeaderManager manager = getHeaderMananger();

							// http sampler
							HTTPSamplerProxy httpSampler = getHttpSampler(configuration, i, jsonpath);
							// loop controller
							TestElement loopCtrl = getloopController(configuration);
							// thread group

							SetupThreadGroup threadGroup = getthreadGroupS2(configuration, loopCtrl, duration);
							// Test Plan
							TestPlan testPlan = gettestPlan();

							// CSV dataset
							CSVDataSet csvdataset = getcsvdatasetTime("time.csv");

							// constant Timer
							ConstantTimer constantTimer = getConstantTimerVariable();
		
							getenginerunS3(jmeterProperties, httpSampler, manager, csvdataset, constantTimer, testPlan,
									threadGroup, authManager, configuration);
							updateMessage(configuration.getbreakTimeRuns() + " milliseconds");
							updateProgress(i + 1, configuration.getnumberTestRuns());
							
							System.out.println("----------------       FINISHED THE ROUND NUMBER " + i);

						} else {
							System.out.println("The jmeter.proprieties File is missing ");
						}

					}

				}
				if (configuration.getScenario().get_postscenarioType().contains("Scenario_4")) {
					// Time interval for the run
					if (configuration.gettimeunit1().equals("Day")) {
						runbreakTime = (int) TimeUnit.DAYS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Hour")) {
						runbreakTime = (int) TimeUnit.HOURS.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Minute")) {
						runbreakTime = (int) TimeUnit.MINUTES.toMillis(runbreakTime);
					} else if (configuration.gettimeunit1().equals("Second")) {
						runbreakTime = (int) TimeUnit.SECONDS.toMillis(runbreakTime);
					}
					// Time interval for the duration
					if (configuration.gettimeunit2().equals("Day")) {
						duration = (int) TimeUnit.DAYS.toMillis(duration);
					} else if (configuration.gettimeunit2().equals("Hour")) {
						duration = (int) TimeUnit.HOURS.toMillis(duration);
					} else if (configuration.gettimeunit2().equals("Minute")) {
						duration = (int) TimeUnit.MINUTES.toMillis(duration);
					} else if (configuration.gettimeunit2().equals("Second")) {
						duration = (int) TimeUnit.SECONDS.toMillis(duration);
					}
					// the breaktime between the requests to be sent to the LRS
					if (configuration.gettimeobj().gettimeunit().equals("Day")) {
						breaktime = (int) TimeUnit.DAYS.toMillis(breaktime);
					} else if (configuration.gettimeobj().gettimeunit().equals("Hour")) {
						breaktime = (int) TimeUnit.HOURS.toMillis(breaktime);
					} else if (configuration.gettimeobj().gettimeunit().equals("Minute")) {
						breaktime = (int) TimeUnit.MINUTES.toMillis(breaktime);
					} else if (configuration.gettimeobj().gettimeunit().equals("Second")) {
						breaktime = (int) TimeUnit.SECONDS.toMillis(breaktime);
					}
					
					configuration.setnumberRequest(duration / breaktime);
			
					ArrayList<String> arraystatement = statement_Files(configuration.getnumberRequest(),
							configuration.getstatementgenerationWay(), configuration.getrange(),
							configuration.getconstant());
					generateCSVfile(arraystatement);
					for (int i = 0; i < configuration.getnumberTestRuns(); i++) {

						System.out.println(
								"----------------       the iteration Number  " + configuration.getnumberTestRuns());
						System.out.println("----------------       Starting The Round Number " + i);
						File jmeterProperties = new File("jmeter.properties");
						// StandardJMeterEngine jm = new StandardJMeterEngine();
						JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
						JMeterUtils.initLocale();
						if (i != 0) {
							Thread.sleep(runbreakTime);
						}
						if (jmeterProperties.exists()) {

							// Authorization
							AuthManager authManager = getauthManager(configuration);
							// Header Mananger
							HeaderManager manager = getHeaderMananger();
							// CSV dataset
							CSVDataSet csvdataset = getcsvdataset("statement.csv");
							// constant Timer
							ConstantTimer constantTimer = getConstantTimer(breaktime);
							// http sampler
							HTTPSamplerProxy httpSampler = getHttpSampler1(configuration, i);
							// loop controller
							TestElement loopCtrl = getloopControllerS2(configuration);
							SetupThreadGroup threadGroup = getthreadGroupS2(configuration, loopCtrl, duration);

							// Test Plan
							TestPlan testPlan = gettestPlan();
							// the rest
							getenginerunS4(jmeterProperties, httpSampler, manager, csvdataset, constantTimer, testPlan,
									threadGroup, authManager, configuration);
							updateMessage(configuration.getbreakTimeRuns() + " milliseconds");
							updateProgress(i + 1, configuration.getnumberTestRuns());
							System.out.println("----------------       FINISHED THE ROUND NUMBER " + i);

						} else {
							System.out.println("The jmeter.proprieties File is missing ");
						}

					}

				}

				System.out.println("Finish ALL The Runs ");
				deleteDirectory("report-output");
				ReportGenerator generator = new ReportGenerator(logFile, null);
				System.out.println("   " + generator.REQUESTS_SUMMARY_CONSUMER_NAME);
				generator.generate();
				// System.exit(0);
				return true;
			}
		};

	}

	// Generate Json Body
	@SuppressWarnings("unchecked")
	public void generateJsonBody(long nbStatement) throws IOException {
		// Create the actor jsonObject
		ArrayList<XAPIStatement> list = ListStatementinitialize();
		JSONArray statementList = new JSONArray();
		int low = 0;
		int max = list.size();
		for (int i = 0; i < nbStatement; i++) {
			Random r = new Random();
			int result = r.nextInt(max - low) + low;
			JSONObject json_actor_details = new JSONObject();
			json_actor_details.put("mbox", list.get(result).getMail());
			json_actor_details.put("name", list.get(result).getActor());
			json_actor_details.put("objectType", "Agent");

			// System.out.println(json_actor_details);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("actor", json_actor_details);

			// Create the verb jsonObject
			JSONObject json_verb_details = new JSONObject();
			JSONObject json_display = new JSONObject();
			json_display.put("en-US", list.get(result).getVerb());
			json_verb_details.put("display", json_display);
			json_verb_details.put("id", new URL("http://example.com/xapi/" + list.get(result).getVerb()).toString());

			// System.out.println(json_verb_details);

			jsonObject.put("verb", json_verb_details);

			// Create obj definition
			JSONObject definitionObject = new JSONObject();
			JSONObject json_description = new JSONObject();
			JSONObject json_name = new JSONObject();
			json_description.put("en-US", "Testing Example");
			json_name.put("en-US", list.get(result).getResource());
			definitionObject.put("description", json_description);
			definitionObject.put("name", json_name);

			// Create the object jsonObject
			JSONObject json_obj_details = new JSONObject();
			json_obj_details.put("objectType", "Activity");
			json_obj_details.put("definition", definitionObject);

			URL url = new URL("http://example.com/xapi/" + list.get(result).getVerb());

			json_obj_details.put("id", url.toString());

			// System.out.println(json_obj_details);

			jsonObject.put("object", json_obj_details);

			statementList.add(jsonObject);
		}

		try (FileWriter file = new FileWriter("JsonFiles/learner" + nbStatement + ".json")) {
			file.write(statementList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public ArrayList<XAPIStatement> ListStatementinitialize() {
		ArrayList<XAPIStatement> list = new ArrayList<XAPIStatement>();
		XAPIStatement xapistatement = new XAPIStatement("user1", "viewed", "course", "mailto:user1@LRS.net");
		XAPIStatement xapistatement1 = new XAPIStatement("user2", "updated", "report", "mailto:user2@LRS.net");
		XAPIStatement xapistatement2 = new XAPIStatement("user3", "submitted", "activity", "mailto:user3@LRS.net");
		XAPIStatement xapistatement3 = new XAPIStatement("user4", "added", "exercice1", "mailto:user4@LRS.net");
		list.add(xapistatement);
		list.add(xapistatement1);
		list.add(xapistatement2);
		list.add(xapistatement3);

		return list;

	}

	// Return AUTHmanager
	public AuthManager getauthManager(Configuration configuration) {

		AuthManager authManager = new AuthManager();
		Authorization authorization = new Authorization();
		authorization.setURL(configuration.geturl());
		authorization.setUser(configuration.getlogin());
		authorization.setPass(configuration.getpassword());
		authManager.addAuth(authorization);
		authManager.setName(JMeterUtils.getResString("authManager"));
		authManager.setProperty(TestElement.TEST_CLASS, AuthManager.class.getName());
		authManager.setProperty(TestElement.GUI_CLASS, AuthPanel.class.getName());

		return authManager;

	}
	// Return Header Manager

	public HeaderManager getHeaderMananger() {

		HeaderManager manager = new HeaderManager();
		manager.add(new Header("X-Experience-API-Version", "1.0.3"));
		manager.add(new Header("Content-Type", "application/json"));
		manager.add(new Header("Accept", "application/json"));
		manager.setName(JMeterUtils.getResString("headers")); // $NON-NLS-1$
		manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
		manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

		return manager;

	}

	// Constant Timer
	public ConstantTimer getConstantTimer(int breaktime) {
		ConstantTimer constantTimer = new ConstantTimer();
		constantTimer.setProperty("ConstantTimer.delay", breaktime);
		return constantTimer;
	}

	// Constant Timer
	public ConstantTimer getConstantTimerVariable() {
		ConstantTimer constantTimer = new ConstantTimer();
		constantTimer.setDelay("${time_to_sleep}");
		return constantTimer;
	}

	// Http Sampler

	public HTTPSamplerProxy getHttpSampler(Configuration configuration, int i, String jsonpath) throws IOException {
		HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
		httpSampler.setDomain(configuration.getdomain());
		// httpSampler.setPort();
		// httpSampler.setProtocol("http");
		httpSampler.setName("POST" + i);
		httpSampler.setPath(configuration.getpath());
		httpSampler.setImplementation("HttpClient4");
		httpSampler.setMethod("POST");
		httpSampler.setHTTPFiles(new HTTPFileArg[] { getFileArg(jsonpath) });
		httpSampler.setFollowRedirects(true);
		httpSampler.setAutoRedirects(false);
		httpSampler.setUseKeepAlive(false);
		httpSampler.setDoMultipart(false);
		return httpSampler;
	}

	

	public HTTPSamplerProxy getHttpSampler1(Configuration configuration, int i) throws IOException {
		HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
		httpSampler.setDomain(configuration.getdomain());
		// httpSampler.setPort();
		// httpSampler.setProtocol("http");
		httpSampler.setName("POST" + i);
		httpSampler.setPath(configuration.getpath());
		httpSampler.setImplementation("HttpClient4");
		httpSampler.setMethod("POST");
		httpSampler.setHTTPFiles(new HTTPFileArg[] { getFileArg1() });
		httpSampler.setFollowRedirects(true);
		httpSampler.setAutoRedirects(false);
		httpSampler.setUseKeepAlive(false);
		httpSampler.setDoMultipart(false);
		return httpSampler;
	}

	// get the loop controller
	public TestElement getloopController(Configuration configuration) {
		// Loop Controller
		TestElement loopCtrl = new LoopController();
		((LoopController) loopCtrl).setLoops(configuration.getnumberRequest());
		((LoopController) loopCtrl).setFirst(true);
		// ((LoopController) loopCtrl).addTestElement(httpSampler);
		loopCtrl.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
		loopCtrl.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
		((LoopController) loopCtrl).initialize();
		return loopCtrl;
	}
	//

	public TestElement getloopControllerS2(Configuration configuration) {
		// Loop Controller
		TestElement loopCtrl = new LoopController();
		((LoopController) loopCtrl).setLoops(-1);
		((LoopController) loopCtrl).setFirst(true);
		// ((LoopController) loopCtrl).addTestElement(httpSampler);
		loopCtrl.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
		loopCtrl.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
		((LoopController) loopCtrl).initialize();
		return loopCtrl;
	}
	// Get the thread Group

	public SetupThreadGroup getthreadGroup(Configuration configuration, TestElement loopCtrl) {
		// Thread Group
		SetupThreadGroup threadGroup = new SetupThreadGroup();
		threadGroup.setName("Thread group");
		threadGroup.setNumThreads(configuration.getuserNumber());
		threadGroup.setRampUp(1);
		threadGroup.setSamplerController((LoopController) loopCtrl);
		threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
		threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
		return threadGroup;

	}

	public SetupThreadGroup getthreadGroupS2(Configuration configuration, TestElement loopCtrl, int duration) {
		// Thread Group
		SetupThreadGroup threadGroup = new SetupThreadGroup();
		threadGroup.setName("Thread group");
		threadGroup.setNumThreads(configuration.getuserNumber());
		threadGroup.setRampUp(1);
		threadGroup.setSamplerController((LoopController) loopCtrl);
		threadGroup.setScheduler(true);
		threadGroup.setDuration(duration);
		threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
		threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
		return threadGroup;

	}

// Get the Test plan
	public TestPlan gettestPlan() {
		// Test Plan
		TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
		testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
		testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
		testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
		testPlan.setEnabled(true);
		return testPlan;

	}

// Create a csv dataset 
	public CSVDataSet getcsvdataset(String pathtoCSV) {
		CSVDataSet csvDataSet = new CSVDataSet();
		csvDataSet.setName("CSV Data Set Config");
		csvDataSet.setProperty("delimiter", ",");
		csvDataSet.setProperty("filename", pathtoCSV);
		csvDataSet.setProperty("ignoreFirstLine", false);
		csvDataSet.setProperty("quotedData", false);
		csvDataSet.setProperty("recycle", false);
		// csvDataSet.setProperty("shareMode", "shareMode.all");
		csvDataSet.setProperty("shareMode", "shareMode.group");
		csvDataSet.setProperty("stopThread", true);
		csvDataSet.setProperty("variableNames", "json_file");
		csvDataSet.setProperty(TestElement.TEST_CLASS, csvDataSet.getClass().getName());
		csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
		return csvDataSet;
	}

	public CSVDataSet getcsvdatasetTime(String pathtoCSV) {
		CSVDataSet csvDataSet = new CSVDataSet();
		csvDataSet.setName("CSV Data Set Config");
		csvDataSet.setProperty("delimiter", ",");
		csvDataSet.setProperty("filename", pathtoCSV);
		csvDataSet.setProperty("ignoreFirstLine", false);
		csvDataSet.setProperty("quotedData", false);
		csvDataSet.setProperty("recycle", false);
		csvDataSet.setProperty("shareMode", "shareMode.all");
		csvDataSet.setProperty("stopThread", true);
		csvDataSet.setProperty("variableNames", "time_to_sleep");
		csvDataSet.setProperty(TestElement.TEST_CLASS, csvDataSet.getClass().getName());
		csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
		return csvDataSet;
	}

// Run the test 
	public void getenginerun(File jmeterProperties, HTTPSamplerProxy httpSampler, HeaderManager manager,
			ConstantTimer constantTimer, TestPlan testPlan, SetupThreadGroup threadGroup, AuthManager authManager,
			Configuration configuration) throws FileNotFoundException, IOException {
		StandardJMeterEngine jm = new StandardJMeterEngine();
		JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
		JMeterUtils.initLocale();
		HashTree hashTree = new HashTree();
		// Add everything to the HashTree
		HashTree httpRequestTree = new HashTree();
		// httpRequestTree.add(httpSampler, authManager);
		httpRequestTree.add(httpSampler, manager);
		httpRequestTree.add(httpSampler, constantTimer);
		hashTree.add(testPlan);
		HashTree threadGroupHashTree = hashTree.add(testPlan, threadGroup);
		threadGroupHashTree.add(authManager);
		threadGroupHashTree.add(httpRequestTree);

		// Save the created Plan into a JMX FILE

		SaveService.saveTree(hashTree, new FileOutputStream("planstructure.jmx"));

		// Collect the results of the requests
		Summariser summer = null;

		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");

		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}
		// Store execution results into a .jtl file
		
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		hashTree.add(hashTree.getArray()[0], logger);
		// Add all the listners to show the results
		JmeterOutListener jmeterOutListener = new JmeterOutListener();
		hashTree.add(hashTree.getArray()[0], jmeterOutListener);
		jm.configure(hashTree);

		anchor3Controller.postshowindicator(configuration.getnumberRequest());
		jm.run();
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
	}

	public void getenginerunS3(File jmeterProperties, HTTPSamplerProxy httpSampler, HeaderManager manager,
			CSVDataSet csvdataset, ConstantTimer constantTimer, TestPlan testPlan, SetupThreadGroup threadGroup,
			AuthManager authManager, Configuration configuration) throws FileNotFoundException, IOException {
		
		StandardJMeterEngine jm = new StandardJMeterEngine();
		JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
		JMeterUtils.initLocale();
		HashTree hashTree = new HashTree();
		// Add everything to the HashTree
		HashTree httpRequestTree = new HashTree();
		// httpRequestTree.add(httpSampler, authManager);
		httpRequestTree.add(httpSampler, manager);
		httpRequestTree.add(httpSampler, constantTimer);

		hashTree.add(testPlan);
		HashTree threadGroupHashTree = hashTree.add(testPlan, threadGroup);
		threadGroupHashTree.add(authManager);
		threadGroupHashTree.add(csvdataset);
		threadGroupHashTree.add(httpRequestTree);

		// Save the created Plan into a JMX FILE

		SaveService.saveTree(hashTree, new FileOutputStream("planstructure.jmx"));

		// Collect the results of the requests
		Summariser summer = null;

		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");

		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}
		// Store execution results into a .jtl file
		
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		hashTree.add(hashTree.getArray()[0], logger);
		// Add all the listners to show the results
		JmeterOutListener jmeterOutListener = new JmeterOutListener();
		hashTree.add(hashTree.getArray()[0], jmeterOutListener);
		jm.configure(hashTree);
		jm.run();
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
	}

	public void getenginerunS4(File jmeterProperties, HTTPSamplerProxy httpSampler, HeaderManager manager,
			CSVDataSet csvdataset, ConstantTimer constantTimer, TestPlan testPlan, SetupThreadGroup threadGroup,
			AuthManager authManager, Configuration configuration) throws FileNotFoundException, IOException {
		StandardJMeterEngine jm = new StandardJMeterEngine();
		JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
		JMeterUtils.initLocale();
		HashTree hashTree = new HashTree();
		// Add everything to the HashTree
		HashTree httpRequestTree = new HashTree();
		// httpRequestTree.add(httpSampler, authManager);
		httpRequestTree.add(httpSampler, manager);
		httpRequestTree.add(httpSampler, constantTimer);
		hashTree.add(testPlan);

		HashTree threadGroupHashTree = hashTree.add(testPlan, threadGroup);

		threadGroupHashTree.add(authManager);
		threadGroupHashTree.add(httpRequestTree);
		threadGroupHashTree.add(csvdataset);

		// Save the created Plan into a JMX FILE

		SaveService.saveTree(hashTree, new FileOutputStream("planstructure.jmx"));

		// Collect the results of the requests
		Summariser summer = null;

		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");

		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}
		// Store execution results into a .jtl file
		
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		hashTree.add(hashTree.getArray()[0], logger);
		// Add all the listners to show the results
		JmeterOutListener jmeterOutListener = new JmeterOutListener();
		hashTree.add(hashTree.getArray()[0], jmeterOutListener);
		jm.configure(hashTree);
		System.out.println("THE thread group is " + threadGroup.getName());
		jm.run();
		System.out.println("THE thread group is " + threadGroup.getThreadName());
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
	}

	public void getenginerunS3_1(File jmeterProperties, HTTPSamplerProxy httpSampler, HeaderManager manager,
			GaussianRandomTimer gaussianTimer, TestPlan testPlan, SetupThreadGroup threadGroup, AuthManager authManager,
			Configuration configuration) throws FileNotFoundException, IOException {
		StandardJMeterEngine jm = new StandardJMeterEngine();
		JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
		JMeterUtils.initLocale();
		HashTree hashTree = new HashTree();
		// Add everything to the HashTree
		HashTree httpRequestTree = new HashTree();
		// httpRequestTree.add(httpSampler, authManager);
		httpRequestTree.add(httpSampler, manager);
		httpRequestTree.add(httpSampler, gaussianTimer);
		hashTree.add(testPlan);
		HashTree threadGroupHashTree = hashTree.add(testPlan, threadGroup);
		threadGroupHashTree.add(authManager);
		threadGroupHashTree.add(httpRequestTree);

		// Save the created Plan into a JMX FILE

		SaveService.saveTree(hashTree, new FileOutputStream("planstructure.jmx"));

		// Collect the results of the requests
		Summariser summer = null;

		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");

		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}
		// Store execution results into a .jtl file
		
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		hashTree.add(hashTree.getArray()[0], logger);
		// Add all the listners to show the results
		JmeterOutListener jmeterOutListener = new JmeterOutListener();
		hashTree.add(hashTree.getArray()[0], jmeterOutListener);
		jm.configure(hashTree);
		jm.run();
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
	}

	public void getenginerunS3_2(File jmeterProperties, HTTPSamplerProxy httpSampler, HeaderManager manager,
			UniformRandomTimer randomTimer, TestPlan testPlan, SetupThreadGroup threadGroup, AuthManager authManager,
			Configuration configuration) throws FileNotFoundException, IOException {
		StandardJMeterEngine jm = new StandardJMeterEngine();
		JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
		JMeterUtils.initLocale();
		HashTree hashTree = new HashTree();
		// Add everything to the HashTree
		HashTree httpRequestTree = new HashTree();
		// httpRequestTree.add(httpSampler, authManager);
		httpRequestTree.add(httpSampler, manager);
		httpRequestTree.add(httpSampler, randomTimer);
		hashTree.add(testPlan);
		HashTree threadGroupHashTree = hashTree.add(testPlan, threadGroup);
		threadGroupHashTree.add(authManager);
		threadGroupHashTree.add(httpRequestTree);

		// Save the created Plan into a JMX FILE

		SaveService.saveTree(hashTree, new FileOutputStream("planstructure.jmx"));

		// Collect the results of the requests
		Summariser summer = null;

		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");

		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}
		// Store execution results into a .jtl file
		
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		hashTree.add(hashTree.getArray()[0], logger);
		// Add all the listners to show the results
		JmeterOutListener jmeterOutListener = new JmeterOutListener();
		hashTree.add(hashTree.getArray()[0], jmeterOutListener);
		jm.configure(hashTree);
		jm.run();
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
	}

	public void getenginerunS2(File jmeterProperties, HTTPSamplerProxy httpSampler, HeaderManager manager,
			TestPlan testPlan, SetupThreadGroup threadGroup, AuthManager authManager, Configuration configuration)
			throws FileNotFoundException, IOException {
		StandardJMeterEngine jm = new StandardJMeterEngine();
		JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
		JMeterUtils.initLocale();
		HashTree hashTree = new HashTree();
		// Add everything to the HashTree
		HashTree httpRequestTree = new HashTree();
		// httpRequestTree.add(httpSampler, authManager);
		httpRequestTree.add(httpSampler, manager);
		// httpRequestTree.add(httpSampler, constantTimer);
		hashTree.add(testPlan);
		HashTree threadGroupHashTree = hashTree.add(testPlan, threadGroup);
		threadGroupHashTree.add(authManager);
		threadGroupHashTree.add(httpRequestTree);

		// Save the created Plan into a JMX FILE

		SaveService.saveTree(hashTree, new FileOutputStream("planstructure.jmx"));

		// Collect the results of the requests
		Summariser summer = null;

		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");

		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}
		// Store execution results into a .jtl file
		
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		hashTree.add(hashTree.getArray()[0], logger);
		// Add all the listners to show the results
		JmeterOutListener jmeterOutListener = new JmeterOutListener();
		hashTree.add(hashTree.getArray()[0], jmeterOutListener);
		jm.configure(hashTree);
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
		jm.run();
		anchor3Controller.postupdatelabels();
		anchor3Controller.postshowindicatorS2();
	}

	private static HTTPFileArg getFileArg(String jsonPath) throws IOException {
		Path path = Paths.get(jsonPath);
		HTTPFileArg fileArg = new HTTPFileArg();
		fileArg.setPath(path.toFile().getCanonicalPath());
		fileArg.setMimeType("");
		fileArg.setParamName("");
		return fileArg;
	}

	private static HTTPFileArg getFileArg1() throws IOException {
		Path path = Paths.get("${json_file}");
		HTTPFileArg fileArg = new HTTPFileArg();
		fileArg.setPath(path.toFile().getCanonicalPath());
		fileArg.setMimeType("");
		fileArg.setParamName("");
		return fileArg;
	}

	static public void deleteDirectory(String emplacement) {

		File path = new File(emplacement);
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
				if (files[i].isDirectory()) {
					System.out.println(" here " + files[i]);
					deleteDirectory(files[i].toString());
				} else {
					files[i].delete();
				}
			}
		}
		path.delete();
	}

	public ArrayList<String> statement_Files(int nbrequest, String generationWay, int range, int  constant) throws IOException {
		ArrayList<String> statement_array = new ArrayList<String>();

		if (generationWay.equals("Gaussian")) {
			System.out.println("Gaussian Generation ");
			for (int i = 0; i < nbrequest; i++) {
				Random rng = new Random();
				long nbstatements = (long) (rng.nextGaussian() * range + constant);
				System.out.println("The number of statements is " + nbstatements);
				generateJsonBody(nbstatements);
				statement_array.add("JsonFiles/learner" + nbstatements + ".json");

			}

		

		} else {
			System.out.println("Random Generation ");
			for (int i = 0; i < nbrequest; i++) {
				int nbstatements = (int) ((Math.random() * (constant + 1 - range)) + range);
				generateJsonBody(nbstatements);
				statement_array.add("JsonFiles/learner" + nbstatements + ".json");

			}

		}
		for (int i = 0; i < statement_array.size(); i++) {
			System.out.println("The Generated FILE Number  " + i + "    is " + statement_array.get(i));
		}
		return statement_array;

	}

	public ArrayList<Long> time_Files(int nbrequest, String generationWay, int range, int constant) throws IOException {
		ArrayList<Long> statement_array = new ArrayList<Long>();

		if (generationWay.equals("Gaussian")) {
			System.out.println("Gaussian Generation ");
			for (int i = 0; i < nbrequest; i++) {
				Random rng = new Random();
				long generatedTime = (long) (rng.nextGaussian() * range + constant);
				statement_array.add(generatedTime);

			}

		} else {
			System.out.println("Random Generation ");
			for (int i = 0; i < nbrequest; i++) {
				long generatedTime  =  (long) ((Math.random() * (constant + 1 - range)) + range);
				statement_array.add(generatedTime);

			}

		}
		/*for (int i = 0; i < statement_array.size(); i++) {
			System.out.println("The Generated Time  " + i + "    is " + statement_array.get(i));
		}*/
		return statement_array;

	}

	// Create the csv file
	public void generateCSVfile(ArrayList<String> arrayFiles) {

		// String COMMA_DELIMITER = ";";
		String NEW_LINE_SEPARATOR = "\n";
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter("statement.csv");
			for (int i = 0; i < arrayFiles.size(); i++) {
				fileWriter.append(arrayFiles.get(i));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			//

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

		System.out.println(" Statement CSV file was created successfully !!!");

	}

	public void generateCSVfileTime(ArrayList<Long> arrayFiles) {

		// String COMMA_DELIMITER = ";";
		String NEW_LINE_SEPARATOR = "\n";
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter("time.csv");
			for (int i = 0; i < arrayFiles.size(); i++) {
				fileWriter.append(String.valueOf(arrayFiles.get(i)));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			//

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

		System.out.println(" Statement CSV file was created successfully !!!");

	}

}
