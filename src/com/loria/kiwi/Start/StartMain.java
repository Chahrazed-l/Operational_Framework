package com.loria.kiwi.Start;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.loria.kiwi.Controller.PostConfigurationStepsController;
import com.loria.kiwi.Controller.RecapPostConfigurationController;
import com.loria.kiwi.Controller.PostRuningTestController;
import com.loria.kiwi.Model.Configuration;
import com.loria.kiwi.Model.Scenario;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Chahrazed LABBA
 *
 */
public class StartMain extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	public static Scenario scenario = new Scenario();
	public static Scenario scenario1 = new Scenario();
	public static Configuration config = new Configuration();
	public static Configuration config1 = new Configuration();

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("-- Performance Test Framework --");
		initRootLayout();
		intialInterface();

	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StartMain.class.getResource("/com/loria/kiwi/Controller/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void intialInterface() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StartMain.class.getResource("/com/loria/kiwi/Controller/WecomePage.fxml"));
			AnchorPane scenario = (AnchorPane) loader.load();
			rootLayout.setCenter(scenario);
			// WelcomePageController controller = loader.getController();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showGetConfigurationSteps(Configuration configuration) {

	
	}

	public void showPostConfigurationSteps(Configuration configuration) {

		scenario = configuration.getScenario();
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StartMain.class.getResource("/com/loria/kiwi/Controller/PostConfigurationSteps.fxml"));
			AnchorPane anchor = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Configuration Steps ");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			PostConfigurationStepsController controller = loader.getController();
			controller.interfaceManagement(scenario);
			Scene scene = new Scene(anchor);
			dialogStage.setScene(scene);
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showBothConfigurationSteps(Configuration configuration, Configuration configuration1) {


	}

	public void showGetRecapConfiguration(Configuration configuration) {
		
	}

	public void showPostRecapConfiguration(Configuration configuration) {
		configuration.setScenario(scenario);
		System.out.println(configuration.getprotocol());
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StartMain.class.getResource("/com/loria/kiwi/Controller/RecapPostConfiguration.fxml"));
			AnchorPane anchor = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Recap Configuration ");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(anchor);
			dialogStage.setScene(scene);
			RecapPostConfigurationController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.printConfiguration(configuration);
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showBothRecapConfiguration(Configuration configuration, Configuration configuration1) {
		
	}

	public void runPostTest(Configuration configuration) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StartMain.class.getResource("/com/loria/kiwi/Controller/PostGetRunningTest.fxml"));
			AnchorPane anchor = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Recap Configuration ");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(anchor);
			dialogStage.setScene(scene);

			PostRuningTestController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.showprogressBar(configuration);
			dialogStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void runBothConfig(Configuration configuration, Configuration configuration1) {
		
	}

	public void runGetTest(Configuration configuration) {

	}

	public void saveTextToFile(Configuration config, File file) {
		String COMMA_DELIMITER = ";";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "requestType;getscenarioType; postscenarioType; pathconfig;NumberTestRuns;breakTimeRuns;timeunit1;NumberRequest;testDuration;timeunit2;breakTimmeRequest;timeunit3;generationway;verb;agent;activity;domain;path;port;userNumber;protocol;URL;login;Password";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);

			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file

			fileWriter.append(config.getScenario().getrequestType());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getScenario().get_getscenarioType());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getScenario().get_postscenarioType());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getScenario().getpathconfig());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.getnumberTestRuns()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.getbreakTimeRuns()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.gettimeunit1()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.getnumberRequest()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf((config.gettestduration())));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf((config.gettimeunit2())));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.gettimeobj().gettimevalue()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.gettimeobj().gettimeunit());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.gettimeobj().getgenerationway());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getbodyrequestobj().getverb());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getbodyrequestobj().getagent());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getbodyrequestobj().getactivity());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getdomain());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getpath());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.getport()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(config.getuserNumber()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getprotocol());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.geturl());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getlogin());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(config.getpassword());
			fileWriter.append(NEW_LINE_SEPARATOR);

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

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
