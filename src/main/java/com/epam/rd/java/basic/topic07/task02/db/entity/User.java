package com.epam.rd.java.basic.topic07.task02.db.entity;

import java.util.Objects;

public class User {

	private int id;

	private String login;

	public User(String login, int id) {
		this.login = login;
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return this.login;
	}

	public static User createUser(String login) {
		User user = new User(login, 0);
		return user;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public long getId() {
		return this.id;
	}
}
