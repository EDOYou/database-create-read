package com.epam.rd.java.basic.topic07.task02.db.entity;

import java.util.Objects;

public class Team {

	private String name;
	private int id;

	public Team(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static Team createTeam(String name) {
		return new Team(name, 0);
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Team team = (Team) o;
		return name.equals(team.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public long getId() {
		return this.id;
	}
}