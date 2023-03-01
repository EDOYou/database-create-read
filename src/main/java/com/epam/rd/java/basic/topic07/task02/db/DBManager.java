package com.epam.rd.java.basic.topic07.task02.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epam.rd.java.basic.topic07.task02.Constants;
import com.epam.rd.java.basic.topic07.task02.db.entity.*;

import static com.epam.rd.java.basic.topic07.task02.db.Fields.*;

/**
 * A singleton class that manages database connections and performs various database operations.
 *
 * @author Taghiyev Kanan
 * @since 01.03.2023
 */
public class DBManager {

	private static DBManager dbManager;

	/**
	 * @return the singleton instance of this class.
	 */
	public static synchronized DBManager getInstance() {
		if (dbManager == null) {
			dbManager = new DBManager();
		}
		return dbManager;
	}

	/**
	 * Retrieves a list of all users from the database.
	 *
	 * @return a list of all users from the database.
	 * @throws DBException if there is an error retrieving the users from the database.
	 */
	public List<User> findAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_USERS) ) {

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				users.add(getUser(resultSet.getString(USER_LOGIN)));
			}
			return users;
		} catch (SQLException e) {
			throw new DBException("Error finding all users", e);
		}
	}

	/**
	 * Inserts a new user into the database.
	 *
	 * @param user the user to insert into the database.
	 * @return true if the user was successfully inserted into the database, false otherwise.
	 * @throws DBException if there is an error inserting the user into the database.
	 */
	public boolean insertUser(User user) throws DBException {
		try (DatabaseConnectionManager manager = new DatabaseConnectionManager();
			 Connection connection = manager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(Constants.INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, user.getLogin());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No row is inserted");
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("User creation failure, no ID obtained.");
				}
			}
			return true;
		} catch (SQLException e) {
			throw new DBException("User insertion problem occurred", e);
		}
	}


	/**
	 * Retrieves a user from the database based on their login name.
	 *
	 * @param login the login name of the user to retrieve.
	 * @return the user with the specified login name.
	 * @throws DBException if there is an error retrieving the user from the database.
	 */
	public User getUser(String login) throws DBException {
		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.GET_USER) ) {

			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();

			User user = User.createUser(login);
			if (resultSet.next()) {
				user.setId(resultSet.getInt(USER_ID));
			}
			return user;
		} catch (SQLException e) {
			throw new DBException("User table data retrieving problem occurred", e);
		}
	}

	/**
	 * Retrieves a team object from the database with the specified name.
	 * @param name the name of the team to retrieve.
	 * @return a team object with the specified name.
	 * @throws DBException if there is an error retrieving the team from the database.
	 */
	public Team getTeam(String name) throws DBException {
		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.GET_TEAM) ) {

			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			Team team = Team.createTeam(name);
			if (resultSet.next()) {
				team.setId(resultSet.getInt(TEAM_ID));
			}
			return team;
		} catch (SQLException e) {
			throw new DBException("Team table data retrieving problem occurred", e);
		}
	}
	/**
	 * Retrieves a list of all the teams in the database.
	 * @return List of all teams.
	 * @throws DBException If a database access error occurs.
	 */
	public List<Team> findAllTeams() throws DBException {
		List<Team> teams = new ArrayList<>();
		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_TEAMS) ) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				teams.add(getTeam(resultSet.getString(TEAM_NAME)));
			}
			return teams;
		} catch (SQLException e) {
			throw new DBException("Error finding all users", e);
		}
	}

	/**
	 * Inserts a new team record into the database.
	 *
	 * @param team the team to insert
	 * @return true if the team was successfully inserted, false otherwise
	 * @throws DBException if an error occurs while inserting the team
	 */
	public boolean insertTeam(Team team) throws DBException {
		try( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			 Connection connection = manager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(Constants.INSERT_TEAM, Statement.RETURN_GENERATED_KEYS) ) {

			statement.setString(1, team.getName());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException ("No row is inserted");
			}

			try( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
				if (generatedKeys.next()) {
					team.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Team creation failure, no ID obtained");
				}
			}

			return true;
		} catch(SQLException e) {
			throw new DBException("Team insertion problem occurred", e);
		}
	}

	/**
	 * Deletes the specified users from the database.
	 *
	 * @param users an array of User objects to be deleted
	 * @return true if the users were successfully deleted, false otherwise
	 * @throws DBException if there is an error accessing the database
	 */
	public boolean deleteUsers(User... users) throws DBException {
		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.INSERT_USER, Statement.RETURN_GENERATED_KEYS) ) {

			for (User user : users) {
				if (user == null) {
					return false;
				}
				statement.setLong(1, user.getId());
				statement.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			throw new DBException("Deletion problem occurred", e);
		}
	}

	/**
	 * Deletes the given team from the database.
	 * @param team the team to be deleted
	 * @return true if the team is deleted successfully, false otherwise
	 * @throws DBException if an error occurs while accessing the database
	 */
	public boolean deleteTeam(Team team) throws DBException {
		if (team == null) {
			return false;
		}

		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.DELETE_TEAM) ) {

			statement.setLong(1, team.getId());
			statement.executeUpdate();

			return true;
		} catch (SQLException e) {
			throw new DBException("Error deleting team", e);
		}
	}

	/**
	 * Updates a team in the database with the given information
	 * @param team the team to update in the database
	 * @return true if the update is successful, false otherwise
	 * @throws DBException if there is a problem with the database connection or the update operation
	 */
	public boolean updateTeam(Team team) throws DBException {
		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection conn = manager.getConnection();
			  PreparedStatement statement = conn.prepareStatement(Constants.UPDATE_TEAM) ) {

			statement.setString(1, team.getName());
			statement.setLong(2, team.getId());
			statement.executeQuery();

		} catch (SQLException e) {
			throw new DBException("Team update error", e);
		}
		return true;
	}

	/**
	 * Retrieves a list of teams for the specified user from the database.
	 *
	 * @param user the user for whom to retrieve the teams
	 * @return a list of teams associated with the user
	 * @throws DBException if an error occurs while accessing the database
	 */
	public List<Team> getUserTeams(User user) throws DBException {
		List<Team> teamList = new ArrayList<>();
		try( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			 Connection connection = manager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(Constants.GET_USER_TEAMS) ) {

			ps.setLong(1, user.getId());
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Team team = getTeam(connection, resultSet);
				teamList.add(team);
			}

		} catch (SQLException e) {
			throw new DBException("No user teams found", e);
		}
		return teamList;
	}

	/**
	 * Retrieves a Team object from a result set row.
	 *
	 * @param connection A Connection object representing the database connection.
	 * @param rs A ResultSet object containing the result set row from which to retrieve the team.
	 * @return A-Team object representing the team associated with the given result set row.
	 * @throws SQLException if there is an error accessing the database.
	 */
	private static Team getTeam(Connection connection, ResultSet rs) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(Constants.FIND_TEAM_BY_ID);
		statement.setLong(1, rs.getLong("team_id"));

		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		Team team = Team.createTeam(resultSet.getString("name"));
		team.setId(resultSet.getInt("id"));
		return team;
	}

	/**
	 * Sets teams for a user in the database.
	 * @param user The User object for whom the teams are to be set
	 * @param teams The array of Team objects to be set for the user
	 * @return true if the operation is successful, false otherwise
	 * @throws DBException If there is an error accessing the database
	 * @throws NullPointerException If the user or team object is null
	 */
	public boolean setTeamsForUser(User user, Team... teams) throws DBException {
		if (user == null) {
			throw new DBException("User is NULL", new NullPointerException());
		}

		try ( DatabaseConnectionManager manager = new DatabaseConnectionManager();
			  Connection connection = manager.getConnection();
			  PreparedStatement statement = connection.prepareStatement(Constants.INSERT_TEAM_FOR_USER) ) {

			connection.setAutoCommit(false);
			for (Team team : teams) {
				if (team == null) {
					connection.rollback();
					throw new DBException("Team object is NULL", new NullPointerException());
				}

				statement.setString(1, String.valueOf(user.getId()));
				statement.setString(2, String.valueOf(team.getId()));
				statement.addBatch();
			}

			statement.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			throw new DBException("Rollback, operation is unsuccessful", e);
		}

		return true;
	}

}