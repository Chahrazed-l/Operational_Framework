package com.loria.kiwi.Controller;

import java.io.File;

import com.loria.kiwi.Model.Configuration;
import com.loria.kiwi.Start.StartMain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Chahrazed LABBA
 *
 */

public class RecapPostConfigurationController {
	@FXML
	private Label requestType;
	@FXML
	private Label scenarioType;
	@FXML
	private Label numberTestRuns;
	@FXML
	private Label breakTimeRuns;
	@FXML
	private Label numberRequest;
	@FXML
	private Label brektimeRequest;
	@FXML
	private Label testduration;
	@FXML
	private Label time1;
	@FXML
	private Label time2;
	@FXML
	private Label time3;
	@FXML
	private Label randomTime;
	@FXML
	private Label statementNumber;
	@FXML
	private Label statementgenerationway;
	@FXML
	private Label protocolcombox;
	@FXML
	private Label domain;
	@FXML
	private Label path;
	@FXML
	private Label port;
	@FXML
	private Label userNumber;
	@FXML
	private Label url;
	@FXML
	private Label login;
	@FXML
	private Label password;
	@FXML
	private Button closeButton;

	private Stage dialogStage;
	

	private boolean okClicked = false;
	StartMain mainapp = new StartMain();

	public void printConfiguration(Configuration config) {
		requestType.setText(config.getScenario().getrequestType());
		scenarioType.setText(config.getScenario().get_postscenarioType());
		numberTestRuns.setText(Integer.toString(config.getnumberTestRuns()));
		breakTimeRuns.setText(Integer.toString(config.getbreakTimeRuns()));
		time1.setText(config.gettimeunit1());
		if (config.getScenario().get_postscenarioType().contains("Scenario_1")) {
			numberRequest.setText(Integer.toString(config.getnumberRequest()));
			testduration.setText("None");
			time2.setText("None");
			brektimeRequest.setText(Integer.toString(config.gettimeobj().gettimevalue()));
			time3.setText(config.gettimeobj().gettimeunit());
			randomTime.setText("None");
			statementgenerationway.setText("None");
			statementNumber.setText(Integer.toString(config.getstatementNumber()));

		}
		if (config.getScenario().get_postscenarioType().contains("Scenario_2")) {
			numberRequest.setText("None");
			testduration.setText(Integer.toString(config.gettestduration()));
			time2.setText(config.gettimeunit2());
			brektimeRequest.setText("None");
			time3.setText("None");
			randomTime.setText("None");
			statementgenerationway.setText("None");
			statementNumber.setText(Integer.toString(config.getstatementNumber()));
		}

		if (config.getScenario().get_postscenarioType().contains("Scenario_3")) {
			numberRequest.setText("None");
			testduration.setText(Integer.toString(config.gettestduration()));
			time2.setText(config.gettimeunit2());
			brektimeRequest.setText(Integer.toString(config.gettimeobj().gettimevalue()));
			time3.setText(config.gettimeobj().gettimeunit());
			randomTime.setText(config.gettimeobj().getgenerationway());
			statementgenerationway.setText("None");
		}
		if (config.getScenario().get_postscenarioType().contains("Scenario_4")) {
			numberRequest.setText(Integer.toString(config.getnumberRequest()));
			testduration.setText(Integer.toString(config.gettestduration()));
			time2.setText(config.gettimeunit2());
			time3.setText(config.gettimeobj().gettimeunit());
			brektimeRequest.setText(Integer.toString(config.gettimeobj().gettimevalue()));
			time3.setText(config.gettimeobj().gettimeunit());
			randomTime.setText(config.gettimeobj().getgenerationway());
			statementNumber.setText("None");
			statementgenerationway.setText(config.getstatementgenerationWay());
			
		}

		domain.setText(config.getdomain());
		port.setText(Integer.toString(config.getport()));
		path.setText(config.getpath());

		protocolcombox.setText(config.getprotocol());
		userNumber.setText(Integer.toString(config.getuserNumber()));
		url.setText(config.geturl());
		login.setText(config.getlogin());
		password.setText(config.getpassword());
		mainapp.config = config;

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void saveConfig() {

		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select CSV File!");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
		File file = chooser.showSaveDialog(this.dialogStage);
		if (file != null) {
			mainapp.saveTextToFile(mainapp.config, file);
		}
	}

	@FXML
	private void runConfig() {
		mainapp.runPostTest(mainapp.config);
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

}
