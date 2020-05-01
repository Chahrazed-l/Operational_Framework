package com.loria.kiwi.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.loria.kiwi.Model.BodyRequestObj;
import com.loria.kiwi.Model.Configuration;
import com.loria.kiwi.Model.Scenario;
import com.loria.kiwi.Model.Timeobject;
import com.loria.kiwi.Start.StartMain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Chahrazed LABBA
 *
 */
public class PostConfigurationStepsController {
	ObservableList<String> timeList = FXCollections.observableArrayList("", "Second", "Minute", "Hour", "Day");
	ObservableList<String> randomList = FXCollections.observableArrayList("", "Gaussian", "Random");
	ObservableList<String> protocolList = FXCollections.observableArrayList("", "http", "https");
	@FXML
	private TextField numberTestRuns;
	@FXML
	private TextField breakTimeRuns;
	@FXML
	private TextField numberRequest;
	@FXML
	private TextField brektimeRequest;
	@FXML
	private TextField testduration;
	@FXML
	private ComboBox<String> timecombox1;
	@FXML
	private ComboBox<String> timecombox2;
	@FXML
	private ComboBox<String> timecombox3;
	@FXML
	private ComboBox<String> randomTime;
	@FXML
	private ComboBox<String> randomstatement;
	@FXML
	private TextField statementNumber;
	@FXML
	private ComboBox<String> protocolcombox;
	@FXML
	private TextField domain;
	@FXML
	private TextField path;
	@FXML
	private TextField port;
	@FXML
	private TextField userNumber;
	@FXML
	private TextField url;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label label4;
	@FXML
	private Label label5;
	@FXML
	private Label label6;
	@FXML
	private Label label7;
	@FXML
	private Label label8;
	@FXML
	private Label label9;
	@FXML
	private Label label10;
	@FXML
	private Label label11;
	@FXML
	private Label labelrange;
	@FXML
	private Label labelms1;
	@FXML
	private Label labelms2;
	@FXML
	private Button closeButton;
	@FXML
	private TextField login;
	@FXML
	private PasswordField password;
	@FXML
	private TextField constant;
	@FXML
	private TextField range;
	@FXML
	private Tooltip tooltip;
	private Stage dialogStage;

	private StartMain mainApp = new StartMain();

	Configuration configuration = new Configuration();

	@FXML
	private void initialize() {
		timecombox1.setItems(timeList);
		timecombox2.setItems(timeList);
		timecombox3.setItems(timeList);
		randomTime.setItems(randomList);
		randomstatement.setItems(randomList);
		timecombox1.setValue("");
		timecombox2.setValue("");
		timecombox3.setValue("");
		randomTime.setValue("");
		randomstatement.setValue("");
		numberRequest.setText("0");
		brektimeRequest.setText("0");
		testduration.setText("0");
		statementNumber.setText("0");
		constant.setText("0");
		range.setText("0");
		protocolcombox.setItems(protocolList);
		protocolcombox.setValue("");
		tooltip.setText("This Fields allows you to define how to mimic a real thinking Time ");

	}

	@FXML
	private void uploadlastConfig() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select CSV File!");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
		File file = chooser.showOpenDialog(this.dialogStage);

		if (file != null) {
			String fileAsString = file.toString();
			getConfiguration(fileAsString);
		}
	}

	public void interfaceManagement(Scenario scenario) {

		if (scenario.get_postscenarioType().contains("Scenario_1")) {
			System.out.println("Scenario 1 is On");
			testduration.setDisable(true);
			label4.setTextFill(Color.web("#BEBEBE", 0.82));
			timecombox2.setDisable(true);
			randomTime.setDisable(true);
			label7.setTextFill(Color.web("#BEBEBE", 0.82));
			label10.setTextFill(Color.web("#BEBEBE", 0.82));
			randomstatement.setDisable(true);
			label11.setTextFill(Color.web("#BEBEBE", 0.82));
			labelrange.setTextFill(Color.web("#BEBEBE", 0.82));
			labelms1.setTextFill(Color.web("#BEBEBE", 0.82));
			labelms2.setTextFill(Color.web("#BEBEBE", 0.82));
			constant.setDisable(true);
			range.setDisable(true);

		}

		if (scenario.get_postscenarioType().contains("Scenario_2")) {

			label3.setTextFill(Color.web("#BEBEBE", 0.82));
			numberRequest.setDisable(true);
			label5.setTextFill(Color.web("#BEBEBE", 0.82));
			label6.setTextFill(Color.web("#BEBEBE", 0.82));
			label7.setTextFill(Color.web("#BEBEBE", 0.82));
			brektimeRequest.setDisable(true);
			timecombox3.setDisable(true);
			randomTime.setDisable(true);
			label10.setTextFill(Color.web("#BEBEBE", 0.82));
			randomstatement.setDisable(true);
			label11.setTextFill(Color.web("#BEBEBE", 0.82));
			labelrange.setTextFill(Color.web("#BEBEBE", 0.82));
			labelms1.setTextFill(Color.web("#BEBEBE", 0.82));
			labelms2.setTextFill(Color.web("#BEBEBE", 0.82));
			constant.setDisable(true);
			range.setDisable(true);

		}

		if (scenario.get_postscenarioType().contains("Scenario_3")) {
			label3.setTextFill(Color.web("#BEBEBE", 0.82));
			numberRequest.setDisable(true);
			label6.setTextFill(Color.web("#BEBEBE", 0.82));
			brektimeRequest.setDisable(true);
			timecombox3.setDisable(true);
			// timecombox2.setDisable(true);
			// testduration.setDisable(true);
			label10.setTextFill(Color.web("#BEBEBE", 0.82));
			randomstatement.setDisable(true);

		}

		if (scenario.get_postscenarioType().contains("Scenario_4")) {
			label3.setTextFill(Color.web("#BEBEBE", 0.82));
			numberRequest.setDisable(true);
			label7.setTextFill(Color.web("#BEBEBE", 0.82));
			label9.setTextFill(Color.web("#BEBEBE", 0.82));
			labelms1.setTextFill(Color.web("#FFFBFB", 0.82));
			labelms2.setTextFill(Color.web("#FFFBFB", 0.82));
			randomTime.setDisable(true);
			statementNumber.setDisable(true);

		}
	}

	@FXML
	public void handleFinish() {

		if (isInputValid()) {
			Timeobject timeobj = new Timeobject();
			BodyRequestObj bodyobj = new BodyRequestObj();
			configuration.setnumberTestRuns(Integer.parseInt(numberTestRuns.getText()));
			configuration.setbreakTimeRuns(Integer.parseInt(breakTimeRuns.getText()));
			configuration.setnumberRequest(Integer.parseInt(numberRequest.getText()));
			configuration.settestduration(Integer.parseInt(testduration.getText()));
			configuration.settimeunit1(timecombox1.getSelectionModel().getSelectedItem().toString());
			configuration.settimeunit2(timecombox2.getSelectionModel().getSelectedItem().toString());
			timeobj.setgenerationway(randomTime.getSelectionModel().getSelectedItem().toString());
			timeobj.settimeunit(timecombox3.getSelectionModel().getSelectedItem().toString());
			timeobj.settimevalue(Integer.parseInt(brektimeRequest.getText()));
			configuration.setstatementNumber(Integer.parseInt(statementNumber.getText()));
			configuration.setstatementgenerationWay(randomstatement.getSelectionModel().getSelectedItem().toString());
			configuration.settimeobj(timeobj);
			configuration.setbodyrequestobj(bodyobj);
			configuration.setuserNumber(Integer.parseInt(userNumber.getText()));
			configuration.setport(Integer.parseInt(port.getText()));
			configuration.setdomain(domain.getText());
			configuration.setprotocol(protocolcombox.getSelectionModel().getSelectedItem().toString());
			configuration.setpath(path.getText());
			configuration.seturl(url.getText());
			configuration.setlogin(login.getText());
			configuration.setpassword(password.getText());
			configuration.setconstant(Integer.parseInt(constant.getText()));
			configuration.setrange(Integer.parseInt(range.getText()));
			mainApp.showPostRecapConfiguration(configuration);
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	private void handleCancel() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	// Read the file and remplir les champs avec le necessaire
	public void getConfiguration(String path1) {

		try (Stream<String> lines = Files.lines(Paths.get(path1))) {
			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(";"))).skip(1)
					.collect(Collectors.toList());
			domain.setText(values.get(0).get(16));
			path.setText(values.get(0).get(17));
			port.setText(values.get(0).get(18));
			userNumber.setText(values.get(0).get(19));
			protocolcombox.setValue(values.get(0).get(20));
			url.setText(values.get(0).get(21));
			login.setText(values.get(0).get(22));
			password.setText(values.get(0).get(23));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	private boolean isInputValid() {
		int nbuser = -1;
		int nbtestrun = -1;
		int breaktime = -1;
		int nbrequest = -1;
		int duration = -1;
		int timereq = -1;
		int nbstatement = -1;
		int theconstant = -1;
		int therange = -1;
		String errorMessage = "";
		System.out.println("   " + numberRequest.disableProperty().getValue());
		if (numberTestRuns.getText() == null || numberTestRuns.getText().length() == 0) {
			errorMessage += "No valid Test Run Number Field!\n";
		} else {
			try {
				nbtestrun = Integer.parseInt(numberTestRuns.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid Test Run Number Field (must be an integer)!\n";
			}
		}

		if (nbtestrun == 0) {
			errorMessage += "No valid Test Run NUmber Field (must be superior to ZERO)!\n";
		}

		if (breakTimeRuns.getText() == null || breakTimeRuns.getText().length() == 0) {
			errorMessage += "No valid Break Time  Field!\n";
		} else {
			try {
				nbtestrun = Integer.parseInt(breakTimeRuns.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid Braek Time Field (must be an integer)!\n";
			}
		}
		if (breaktime == 0) {
			errorMessage += "No valid Break Time Field (must be superior to ZERO)!\n";
		}
		if (timecombox1.getSelectionModel().getSelectedItem().toString().equals("")) {
			errorMessage += "No valid Time unit !\n";
		}
		// Scenario 1
		if (testduration.disableProperty().getValue() && timecombox2.disabledProperty().getValue()
				&& randomTime.disabledProperty().getValue() && constant.disabledProperty().getValue()
				&& range.disabledProperty().getValue()) {

			if (numberRequest.getText() == null || numberRequest.getText().length() == 0) {
				errorMessage += "No valid Request Number  Field!\n";
			} else {
				try {
					nbrequest = Integer.parseInt(numberRequest.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Request Number Field (must be an integer)!\n";
				}
			}
			if (nbrequest == 0) {
				errorMessage += "No valid Request Number Field (must be superior to ZERO)!\n";
			}

			if (brektimeRequest.getText() == null || brektimeRequest.getText().length() == 0) {
				errorMessage += "No valid break Request Time  Field!\n";
			} else {
				try {
					timereq = Integer.parseInt(brektimeRequest.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Break Request Time Field (must be an integer)!\n";
				}
			}
			if (timereq == 0) {
				errorMessage += "No valid break Request Time Field (must be superior to ZERO)!\n";
			}
			if (timecombox3.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Time unit Fo the Request Time Interval!\n";
			}
			if (statementNumber.getText() == null || statementNumber.getText().length() == 0) {
				errorMessage += "No valid Statement NUmber Field!\n";

			} else {
				try {
					nbstatement = Integer.parseInt(statementNumber.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  statement Number Field (must be an integer)!\n";
				}
			}
			if (nbstatement == 0) {
				errorMessage += "No valid statement Number Field (must be superior to ZERO)!\n";
			}

		}
		// Scenario 2
		else if (numberRequest.disabledProperty().getValue() && brektimeRequest.disabledProperty().getValue()
				&& randomTime.disabledProperty().getValue() && timecombox3.disabledProperty().getValue()
				&& constant.disabledProperty().getValue() && range.disabledProperty().getValue()) {
			System.out.println(" Execute this part of the code ");
			if (testduration.getText() == null || testduration.getText().length() == 0) {
				errorMessage += "No valid Test Duration  Field!\n";
			} else {
				try {
					duration = Integer.parseInt(testduration.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Test Duration Field (must be an integer)!\n";
				}
			}
			if (duration == 0) {
				errorMessage += "No valid Test Duration Field (must be superior to ZERO)!\n";
			}
			if (timecombox2.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Time unit Fo the Test Duration!\n";
			}
			if (statementNumber.getText() == null || statementNumber.getText().length() == 0) {
				errorMessage += "No valid Statement NUmber Field!\n";

			} else {
				try {
					nbstatement = Integer.parseInt(statementNumber.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  statement Number Field (must be an integer)!\n";
				}
			}
			if (nbstatement == 0) {
				errorMessage += "No valid statement Number Field (must be superior to ZERO)!\n";
			}
		}
		// Scenario 3
		else if (numberRequest.disabledProperty().getValue() && randomTime.disabledProperty().getValue()
				&& randomstatement.disabledProperty().getValue()) {

			if (testduration.getText() == null || testduration.getText().length() == 0) {
				errorMessage += "No valid Test Duration  Field!\n";
			} else {
				try {
					duration = Integer.parseInt(testduration.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Test Duration Field (must be an integer)!\n";
				}
			}
			if (duration == 0) {
				errorMessage += "No valid Test Duration Field (must be superior to ZERO)!\n";
			}
			if (timecombox2.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Time unit Fo the Test Duration!\n";
			}
			if (brektimeRequest.getText() == null || brektimeRequest.getText().length() == 0) {
				errorMessage += "No valid break Request Time  Field!\n";
			} else {
				try {
					timereq = Integer.parseInt(brektimeRequest.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Break Request Time Field (must be an integer)!\n";
				}
			}
			if (timereq == 0) {
				errorMessage += "No valid break Request Time Field (must be superior to ZERO)!\n";
			}
			if (timecombox3.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Time unit Fo the Request Time Interval!\n";
			}
			if (statementNumber.getText() == null || statementNumber.getText().length() == 0) {
				errorMessage += "No valid Statement NUmber Field!\n";

			} else {
				try {
					nbstatement = Integer.parseInt(statementNumber.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  statement Number Field (must be an integer)!\n";
				}
			}
			if (nbstatement == 0) {
				errorMessage += "No valid statement Number Field (must be superior to ZERO)!\n";
			}

////////////

			if (constant.getText() == null || constant.getText().length() == 0) {
				errorMessage += "No valid Constant Field!\n";

			} else {
				try {
					theconstant = Integer.parseInt(constant.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  constant Field (must be an integer)!\n";
				}
			}
			if (theconstant == 0) {
				errorMessage += "No valid constant Field (must be superior to ZERO)!\n";
			}

			if (range.getText() == null || range.getText().length() == 0) {
				errorMessage += "No valid Range Field!\n";

			} else {
				try {
					therange = Integer.parseInt(range.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  range Field (must be an integer)!\n";
				}
			}
			if (therange == 0) {
				errorMessage += "No valid range Field (must be superior to ZERO)!\n";
			}
			if (therange >= theconstant) {
				errorMessage += "The Test Range should be inferior to the Constant!\n";
			}

			////////

			if (duration > 0 && timereq > 0 && !timecombox2.disabledProperty().getValue()
					&& !timecombox3.disabledProperty().getValue()) {

				if (timecombox2.disabledProperty().getValue().equals(timecombox3.disabledProperty().getValue())
						&& duration < timereq) {
					errorMessage += "The Test Duration is inferior to the Time Request Interval!\n";
				} else {
					if (timecombox2.getSelectionModel().getSelectedItem().toString().equals("Day")) {
						duration = duration * 84000;
					}
					if (timecombox2.getSelectionModel().getSelectedItem().toString().equals("Hour")) {
						duration = duration * 3600;
					}
					if (timecombox2.getSelectionModel().getSelectedItem().toString().equals("Minute")) {
						duration = duration * 60;
					}
					if (timecombox3.getSelectionModel().getSelectedItem().toString().equals("Day")) {
						timereq = timereq * 84000;
					}
					if (timecombox3.getSelectionModel().getSelectedItem().toString().equals("Hour")) {
						timereq = timereq * 3600;
					}
					if (timecombox3.getSelectionModel().getSelectedItem().toString().equals("Minute")) {
						timereq = timereq * 60;
					}
					System.out.println("Compare " + duration + " " + timereq);
					if (duration < timereq) {
						errorMessage += "The Test Duration is inferior to the Time Request Interval!\n";
					}
				}

			}

		}

		// Scenario 4

		else if (numberRequest.disabledProperty().getValue() && statementNumber.disabledProperty().getValue()) {
			System.out.println(" WE are here " + statementNumber.disabledProperty().getValue());

			if (testduration.getText() == null || testduration.getText().length() == 0) {
				errorMessage += "No valid Test Duration  Field!\n";
			} else {
				try {
					duration = Integer.parseInt(testduration.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Test Duration Field (must be an integer)!\n";
				}
			}
			if (duration == 0) {
				errorMessage += "No valid Test Duration Field (must be superior to ZERO)!\n";
			}

			if (timecombox2.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Time Unit for the test Duration!\n";
			}
			if (brektimeRequest.getText() == null || brektimeRequest.getText().length() == 0) {
				errorMessage += "No valid Break Time Request  Field!\n";
			} else {
				try {
					breaktime = Integer.parseInt(brektimeRequest.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  Break Time Request Field (must be an integer)!\n";
				}
			}
			if (breaktime == 0) {
				errorMessage += "No valid Break Time Request Field (must be superior to ZERO)!\n";
			}
			if (timecombox3.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Time Unit for the Request Break Time!\n";
			}
			if (randomstatement.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "No valid Random Genaration of Statements";
			}
			if (constant.getText() == null || constant.getText().length() == 0) {
				errorMessage += "No valid Constant Field!\n";

			} else {
				try {
					theconstant = Integer.parseInt(constant.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  constant Field (must be an integer)!\n";
				}
			}
			if (theconstant == 0) {
				errorMessage += "No valid constant Field (must be superior to ZERO)!\n";
			}

			if (range.getText() == null || range.getText().length() == 0) {
				errorMessage += "No valid Range Field!\n";

			} else {
				try {
					therange = Integer.parseInt(range.getText());
				} catch (NumberFormatException e) {
					errorMessage += "No valid  range Field (must be an integer)!\n";
				}
			}
			if (therange == 0) {
				errorMessage += "No valid range Field (must be superior to ZERO)!\n";
			}
			if (therange >= theconstant) {
				errorMessage += "The Test Range should be inferior to the Constant!\n";
			}
		}

		if (domain.getText() == null || domain.getText().length() == 0) {
			errorMessage += "No valid Domain Field!\n";
		}
		if (path.getText() == null || path.getText().length() == 0) {
			errorMessage += "No valid Path Field !\n";
		}
		if (protocolcombox.getSelectionModel().getSelectedItem().toString().equals("")) {
			errorMessage += "No valid Protocol Type Field !\n";
		}
		if (userNumber.getText() == null || userNumber.getText().length() == 0) {
			errorMessage += "No valid User Number Field!\n";
		} else {
			try {
				nbuser = Integer.parseInt(userNumber.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid userNumber Field (must be an integer)!\n";
			}
		}
		if (nbuser == 0) {
			errorMessage += "No valid userNumber Field (must be superior to ZERO)!\n";
		}

		if (url.getText() == null || url.getText().length() == 0) {
			errorMessage += "No valid URL Field !\n";
		}
		if (login.getText() == null || login.getText().length() == 0) {
			errorMessage += "No valid Login Field !\n";
		}
		if (password.getText() == null || password.getText().length() == 0) {
			errorMessage += "No valid password Field !\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			// alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
