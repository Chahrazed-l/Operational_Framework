package com.loria.kiwi.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Chahrazed LABBA
 *
 */
public class Configuration {
	private Scenario scenario;
	private IntegerProperty numberTestRuns;
	private IntegerProperty breakTimeRuns;
	private IntegerProperty numberRequest;
	private IntegerProperty statementNumber;
	private IntegerProperty testduration;
	private Timeobject timeobj;
	private BodyRequestObj bodyrequestObj;
	private StringProperty domain;
	private StringProperty path;
	private IntegerProperty port;
	private IntegerProperty userNumber;
	private StringProperty protocol;
	private StringProperty url;
	private StringProperty login;
	private StringProperty password;
	private StringProperty timeunit1;
	private StringProperty timeunit2;
	private StringProperty statementGenerationWay;
	private IntegerProperty constant;
	private IntegerProperty range;

	public Configuration() {
		this(null, 0, 0, 0, 0, null, null, "", "", 0, 0, "", "", "", "", "", "", 0, "",0,0);
	}

	public Configuration(Scenario scenario, int numberTestRuns, int breakTimeRuns, int numberRequest, int testduration,
			Timeobject timeobj, BodyRequestObj bodyrequestObj, String domain, String path, int port, int userNumber,
			String protocol, String url, String login, String password, String timeunit1, String timeunit2,
			int statetementNumber, String statementgenerationway, int constant, int range) {
		this.scenario = scenario;
		this.numberTestRuns = new SimpleIntegerProperty(numberTestRuns);
		this.breakTimeRuns = new SimpleIntegerProperty(breakTimeRuns);
		this.numberRequest = new SimpleIntegerProperty(numberRequest);
		this.statementNumber = new SimpleIntegerProperty(statetementNumber);
		this.testduration = new SimpleIntegerProperty(testduration);
		this.timeobj = timeobj;
		this.bodyrequestObj = bodyrequestObj;
		this.domain = new SimpleStringProperty(domain);
		this.path = new SimpleStringProperty(path);
		this.port = new SimpleIntegerProperty(port);
		this.userNumber = new SimpleIntegerProperty(userNumber);
		this.protocol = new SimpleStringProperty(protocol);
		this.url = new SimpleStringProperty(url);
		this.login = new SimpleStringProperty(login);
		this.password = new SimpleStringProperty(password);
		this.timeunit1 = new SimpleStringProperty(timeunit1);
		this.timeunit2 = new SimpleStringProperty(timeunit2);
		this.statementGenerationWay= new SimpleStringProperty(statementgenerationway);
		this.constant= new SimpleIntegerProperty(constant);
		this.range= new SimpleIntegerProperty(range);

	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Timeobject gettimeobj() {
		return timeobj;
	}

	public void settimeobj(Timeobject timeobj) {
		this.timeobj = timeobj;
	}

	public BodyRequestObj getbodyrequestobj() {
		return bodyrequestObj;
	}

	public void setbodyrequestobj(BodyRequestObj bodyrequestobj) {
		this.bodyrequestObj = bodyrequestobj;
	}

	public int getnumberTestRuns() {
		return numberTestRuns.get();
	}

	public void setnumberTestRuns(int numberTestRuns) {
		this.numberTestRuns.set(numberTestRuns);
	}

	public IntegerProperty getnumberTestRunsProperty() {
		return numberTestRuns;
	}

	public int getstatementNumber() {
		return statementNumber.get();
	}

	public void setstatementNumber(int statementNumber) {
		this.statementNumber.set(statementNumber);
	}

	public IntegerProperty getstatementNumberProperty() {
		return statementNumber;
	}

	public int getbreakTimeRuns() {
		return breakTimeRuns.get();
	}

	public void setbreakTimeRuns(int breakTimeRuns) {
		this.breakTimeRuns.set(breakTimeRuns);
	}

	public IntegerProperty getbreakTimeRunsProperty() {
		return breakTimeRuns;
	}

	public int getnumberRequest() {
		return numberRequest.get();
	}

	public void setnumberRequest(int numberRequest) {
		this.numberRequest.set(numberRequest);
	}

	public IntegerProperty getnumberRequestProperty() {
		return numberRequest;
	}

	public int gettestduration() {
		return testduration.get();
	}

	public void settestduration(int testduration) {
		this.testduration.set(testduration);
	}

	public IntegerProperty gettestdurationProperty() {
		return testduration;
	}

	public int getport() {
		return port.get();
	}

	public void setport(int port) {
		this.port.set(port);
	}

	public IntegerProperty getportProperty() {
		return port;
	}

	public int getuserNumber() {
		return userNumber.get();
	}

	public void setuserNumber(int userNumber) {
		this.userNumber.set(userNumber);
	}

	public IntegerProperty getuserNumberProperty() {
		return userNumber;
	}

	public String getdomain() {
		return domain.get();
	}

	public void setdomain(String domain) {
		this.domain.set(domain);
	}

	public StringProperty domainProperty() {
		return domain;
	}

	public String getpath() {
		return path.get();
	}

	public void setpath(String path) {
		this.path.set(path);
	}

	public StringProperty pathProperty() {
		return path;
	}

	public String geturl() {
		return url.get();
	}

	public void seturl(String url) {
		this.url.set(url);
	}

	public StringProperty urlProperty() {
		return url;
	}

	public String getprotocol() {
		return protocol.get();
	}

	public void setprotocol(String protocol) {
		this.protocol.set(protocol);
	}

	public StringProperty protocolProperty() {
		return protocol;
	}

	public String getlogin() {
		return login.get();
	}

	public void setlogin(String login) {
		this.login.set(login);
	}

	public StringProperty loginProperty() {
		return login;
	}

	public String getpassword() {
		return password.get();
	}

	public void setpassword(String password) {
		this.password.set(password);
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public String gettimeunit1() {
		return timeunit1.get();
	}

	public void settimeunit1(String timeunit1) {
		this.timeunit1.set(timeunit1);
	}

	public StringProperty timeunit1Property() {
		return timeunit1;
	}

	public String gettimeunit2() {
		return timeunit2.get();
	}

	public void settimeunit2(String timeunit2) {
		this.timeunit2.set(timeunit2);
	}

	public StringProperty timeunit2Property() {
		return timeunit2;
	}
	
	public String getstatementgenerationWay() {
		return statementGenerationWay.get();
	}

	public void setstatementgenerationWay(String statementgenerationway) {
		this.statementGenerationWay.set(statementgenerationway);
	}

	public StringProperty statementgenerationProperty() {
		return statementGenerationWay;
	}
	
	
	public int getconstant() {
		return constant.get();
	}

	public void setconstant(int constant) {
		this.constant.set(constant);
	}

	public IntegerProperty getconstantProperty() {
		return constant;
	}
	
	
	public int getrange() {
		return range.get();
	}

	public void setrange(int range) {
		this.range.set(range);
	}

	public IntegerProperty getrangeProperty() {
		return range;
	}
	
}
