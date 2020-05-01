package com.loria.kiwi.Controller;

import com.loria.kiwi.Model.Configuration;
import com.loria.kiwi.Model.Scenario;
import com.loria.kiwi.Start.StartMain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Chahrazed LABBA
 *
 */

public class WelcomePageController {

	ObservableList<String> postList = FXCollections.observableArrayList("",
			"Scenario_1: Load Test (fixed time interval & fixed request number & fixed statement number)",
			"Scenario_2: Load Test (set test duration & fixed statement number)",
			"Scenario_3: Strategy Test (set test duration & random time interval & fixed statement number)",
			"Scenario_4: Strategy Test (set test duration & fixed time interval & random statement number");
	ObservableList<String> getList = FXCollections.observableArrayList("",
			"Scenario_1: Load Test (fixed time interval & fixed request number)",
			"Scenario_2: Load Test (set test duration)",
			"Scenario_3: Load Test: (set test duration & fixed time interval)",
			"Scenario_4: Strategy Test (set test duration, random time interval & fixed request number)");
	StartMain mainApp = new StartMain();
	public Configuration configuration = new Configuration();
	public Configuration configuration1 = new Configuration();

	@FXML
	private CheckBox getbox;
	@FXML
	private CheckBox postbox;
	@FXML
	private CheckBox bothbox;
	@FXML
	private TextField pathconfig;
	@FXML
	private CheckBox existingconfig;
	@FXML
	private CheckBox newConfig;
	@FXML
	private ComboBox<String> postcombox;
	@FXML
	private ComboBox<String> getcombox;
	@FXML
	private Button closeButton;
	@FXML
	private Button pathconfigbutton;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label label4;

	@SuppressWarnings("unused")
	private Stage dialogStage;

	@FXML
	private void initialize() {
		pathconfigbutton.setDisable(true);
		pathconfig.setDisable(true);
		getbox.setDisable(true);
		postbox.setDisable(true);
		bothbox.setDisable(true);
		getcombox.setDisable(true);
		postcombox.setDisable(true);
		getcombox.setValue("");
		postcombox.setValue("");
		label1.setTextFill(Color.web("#BEBEBE", 0.82));
		label2.setTextFill(Color.web("#BEBEBE", 0.82));
		label3.setTextFill(Color.web("#BEBEBE", 0.82));
		label4.setTextFill(Color.web("#BEBEBE", 0.82));
	}

	@FXML
	private void handleexistingconfig() {
		if (existingconfig.isSelected()) {
			newConfig.setSelected(false);
			pathconfigbutton.setDisable(false);
			pathconfig.setDisable(false);
			getbox.setDisable(true);
			getbox.setSelected(false);
			postbox.setDisable(true);
			postbox.setSelected(false);
			bothbox.setDisable(true);
			bothbox.setSelected(false);
			getcombox.setDisable(true);
			postcombox.setDisable(true);
			label1.setTextFill(Color.web("#BEBEBE", 0.82));
			label2.setTextFill(Color.web("#BEBEBE", 0.82));
			label3.setTextFill(Color.web("#BEBEBE", 0.82));
			label4.setTextFill(Color.web("#3d207c", 0.8));
		}

	}

	@FXML
	private void handlenewConfig() {
		if (newConfig.isSelected()) {
			existingconfig.setSelected(false);
			pathconfigbutton.setDisable(true);
			pathconfig.setDisable(true);
			getbox.setDisable(false);
			postbox.setDisable(false);
			bothbox.setDisable(false);
			getcombox.setDisable(true);
			postcombox.setDisable(true);
			label1.setTextFill(Color.web("#3d207c", 0.8));
			label2.setTextFill(Color.web("#BEBEBE", 0.82));
			label3.setTextFill(Color.web("#BEBEBE", 0.82));
			label4.setTextFill(Color.web("#BEBEBE", 0.82));

		}

	}

	@FXML
	private void handlegetbox() {
		if (getbox.isSelected()) {
			getcombox.setItems(getList);
			postbox.setSelected(false);
			bothbox.setSelected(false);
			postcombox.setDisable(true);
			getcombox.setDisable(false);
			label2.setTextFill(Color.web("#3d207c", 0.8));
			label3.setTextFill(Color.web("#BEBEBE", 0.82));
		}
	}

	@FXML
	private void handlepostbox() {
		if (postbox.isSelected()) {
			postcombox.setItems(postList);
			getbox.setSelected(false);
			bothbox.setSelected(false);
			getcombox.setDisable(true);
			postcombox.setDisable(false);
			label3.setTextFill(Color.web("#3d207c", 0.8));
			label2.setTextFill(Color.web("#BEBEBE", 0.82));
		}
	}

	@FXML
	private void handlebothbox() {
		if (bothbox.isSelected()) {
			getcombox.setItems(getList);
			postcombox.setItems(postList);
			getbox.setSelected(false);
			postbox.setSelected(false);
			getcombox.setDisable(false);
			postcombox.setDisable(false);
			label3.setTextFill(Color.web("#3d207c", 0.8));
			label2.setTextFill(Color.web("#3d207c", 0.8));
		}
	}

	@FXML
	private void handleCancel() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void handleOKbutton() {
		if (isInputValid()) {
			Scenario scenario = new Scenario();
			Scenario scenario1 = new Scenario();
			if (existingconfig.isSelected()) {
				scenario.setpathconfig(pathconfig.getText());
				// Call function to show the Recap Interface
				configuration.setScenario(scenario);
				// mainApp.showExistingRecapConfiguration(configuration);
			} else if (newConfig.isSelected()) {

				if (getbox.isSelected()) {
					scenario.setrequestType("GET");
					scenario.setgetscenarioType(getcombox.getSelectionModel().getSelectedItem().toString());
					configuration.setScenario(scenario);
				} else if (postbox.isSelected()) {
					scenario.setrequestType("POST");
					scenario.setpostscenarioType(postcombox.getSelectionModel().getSelectedItem().toString());
					configuration.setScenario(scenario);
				} else if (bothbox.isSelected()) {
					scenario.setrequestType("GET");
					scenario.setgetscenarioType(getcombox.getSelectionModel().getSelectedItem().toString());
					scenario1.setrequestType("POST");
					scenario1.setpostscenarioType(postcombox.getSelectionModel().getSelectedItem().toString());
					configuration.setScenario(scenario);
					configuration1.setScenario(scenario1);
					System.out.println("configuration " + configuration.getScenario().getrequestType()
							+ "configuration1" + configuration1.getScenario().getrequestType());
				}

				// Call the function to show the next INteface to finish the rest of the
				// configuration
				if (scenario.getrequestType().equals("GET")) {
					//mainApp.showGetConfigurationSteps(configuration);
				}

				if (scenario.getrequestType().contentEquals("POST")) {
					mainApp.showPostConfigurationSteps(configuration);
				}
				if (scenario.getrequestType().equals("GET") && scenario1.getrequestType().contentEquals("POST")) {
					//mainApp.showBothConfigurationSteps(configuration, configuration1);
				}

			}
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
		}

	}

	private boolean isInputValid() {
		String errorMessage = "";
		if (!existingconfig.isSelected() && !newConfig.isSelected()) {
			errorMessage += "Please Select a Configuration Type!\n";
		}
		if (newConfig.isSelected()) {
			if (!getbox.isSelected() && !postbox.isSelected() && !bothbox.isSelected()) {
				errorMessage += "Please Select a Request Type!\n";
			}

			else if (getbox.isSelected() && getcombox.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "Please Select a Get scenario Type!\n";
			} else if (postbox.isSelected() && postcombox.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "Please Select a Post scenario Type!\n";
			} else if (bothbox.isSelected() && getcombox.getSelectionModel().getSelectedItem().toString().equals("")
					&& postcombox.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "Please Select a Get and a Post scenario Type!\n";
			} else if (bothbox.isSelected() && postcombox.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "Please Select a Post scenario Type!\n";
			} else if (bothbox.isSelected() && getcombox.getSelectionModel().getSelectedItem().toString().equals("")) {
				errorMessage += "Please Select a Get scenario Type!\n";
			}
		} else if (existingconfig.isSelected())
			if (pathconfig.getText().equals("")) {
				errorMessage += "Please Upload your configuration File!\n";
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

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
