package com.loria.kiwi.Model;

public class XAPIStatement {
	private String actor;
	private String verb;
	private String resource;
	private String mail;

	public XAPIStatement(String actor, String verb, String resource, String mail) {
		this.actor = actor;
		this.verb = verb;
		this.resource = resource;
		this.mail = mail;

	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
