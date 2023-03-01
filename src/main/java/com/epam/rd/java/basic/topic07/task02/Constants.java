package com.epam.rd.java.basic.topic07.task02;

public class Constants {
	public static final String SETTINGS_FILE = "app.properties";
	public static final String GET_ALL_USERS = "SELECT * FROM users";
	public static final String GET_ALL_TEAMS = "SELECT * FROM teams";
	public static final String INSERT_USER = "INSERT INTO users (login) VALUES (?)";
	public static final String INSERT_TEAM = "INSERT INTO teams (name) VALUES (?)";
	public static final String GET_USER = "SELECT * FROM users WHERE login = ?";
	public static final String GET_TEAM = "SELECT * FROM teams WHERE name = ?";
	public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
	public static final String DELETE_TEAM = "DELETE FROM teams WHERE id = ?";
	public static final String UPDATE_TEAM = "UPDATE teams SET name = ? WHERE id = ?";
	public static final String GET_USER_TEAMS = "SELECT * FROM users_teams WHERE user_id = ?";
	public static final String FIND_TEAM_BY_ID = "SELECT * FROM teams WHERE id = ?" ;
	public static final String INSERT_TEAM_FOR_USER = "INSERT INTO users_teams (user_id, team_id) VALUES (?, ?)";
}
