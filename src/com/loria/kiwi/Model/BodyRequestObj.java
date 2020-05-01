package com.loria.kiwi.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @author Chahrazed LABBA
 *
 */
public class BodyRequestObj {
	private StringProperty verb;
	private StringProperty agent;
	private StringProperty activity;

	public BodyRequestObj() {
		this("", "","");
	}

	public BodyRequestObj(String verb, String agent, String activity) {
		this.verb = new SimpleStringProperty(verb);
		this.activity = new SimpleStringProperty(activity);
		this.agent = new SimpleStringProperty(agent);
	}
	
	public String getverb() {
		return verb.get();
	}

	public void setverb(String verb) {
		this.verb.set(verb);
	}

	public StringProperty verbProperty() {
		return verb;
	}
	
	public String getactivity() {
		return activity.get();
	}

	public void setactivity(String activity) {
		this.activity.set(activity);
	}

	public StringProperty activityProperty() {
		return activity;
	}
	
	public String getagent() {
		return agent.get();
	}

	public void setagent(String agent) {
		this.agent.set(agent);
	}

	public StringProperty agentProperty() {
		return agent;
	}

}
