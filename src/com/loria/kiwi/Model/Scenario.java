package com.loria.kiwi.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @author Chahrazed LABBA
 *
 */
public class Scenario {
	private StringProperty requestType;
	private StringProperty getscenarioType;
	private StringProperty postscenarioType;
	private StringProperty pathconfig;
	
	public Scenario() {
		this("","","","");
	}
    public Scenario(String requestType,String getscenarioType,String postscenarioType, String pathconfig) {
    	this.requestType=new SimpleStringProperty(requestType);
    	this.getscenarioType = new SimpleStringProperty(getscenarioType);
    	this.postscenarioType = new SimpleStringProperty(postscenarioType);
    	this.pathconfig= new SimpleStringProperty(pathconfig);
	}
    public String getpathconfig() {
		return pathconfig.get();
	}

	public void setpathconfig(String pathconfig) {
		this.pathconfig.set(pathconfig);
	}

	public StringProperty pathconfigTypeProperty() {
		return pathconfig;
	}
	
	public String getrequestType() {
		return requestType.get();
	}

	public void setrequestType(String requestType) {
		this.requestType.set(requestType);
	}

	public StringProperty requestTypeProperty() {
		return requestType;
	}
	
	
	public String get_getscenarioType() {
		return getscenarioType.get();
	}

	public void setgetscenarioType(String getscenarioType) {
		this.getscenarioType.set(getscenarioType);
	}

	public StringProperty getscenarioTypeProperty() {
		return getscenarioType;
	}
	
	
	public String get_postscenarioType() {
		return postscenarioType.get();
	}

	public void setpostscenarioType(String postscenarioType) {
		this.postscenarioType.set(postscenarioType);
	}

	public StringProperty postscenarioTypeProperty() {
		return postscenarioType;
	}

}
