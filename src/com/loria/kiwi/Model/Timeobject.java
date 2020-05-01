package com.loria.kiwi.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Chahrazed LABBA
 *
 */
public class Timeobject {
	private IntegerProperty timevalue;
	private StringProperty timeunit;
	private StringProperty generationway;

	public Timeobject() {
		this(0, "","");
	}

	public Timeobject(int timevalue, String timeunit, String generationway) {
		this.timevalue= new SimpleIntegerProperty(timevalue);
		this.timeunit= new  SimpleStringProperty(timeunit);
		this.generationway= new SimpleStringProperty(generationway);
		
	}
	
	public int gettimevalue() {
		return timevalue.get();
	}

	public void settimevalue(int timevalue) {
		this.timevalue.set(timevalue);
	}

	public IntegerProperty gettimevalueProperty() {
		return timevalue;
	}
	
	
	public String gettimeunit() {
		return timeunit.get();
	}

	public void settimeunit(String timeunit) {
		this.timeunit.set(timeunit);
	}

	public StringProperty timeunitProperty() {
		return timeunit;
	}

	public String getgenerationway() {
		return generationway.get();
	}

	public void setgenerationway(String generationway) {
		this.generationway.set(generationway);
	}

	public StringProperty generationwayProperty() {
		return generationway; }
}
