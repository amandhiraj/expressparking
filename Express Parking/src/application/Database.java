package application;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.UUID;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import application.User;

public class Database {
	public ArrayList<User> users = new ArrayList<User>();
	public static int parkingSpace;
	public static String path = "user.csv";
	public static String path2 = "parkingspaces.csv";

	public void load() throws Exception {
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();

		while (reader.readRecord()) {
			User user = new User();
			// name,id,email,password
			user.setFirstname(reader.get("firstname"));
			user.setLastname(reader.get("lastname"));
			user.setUUID(reader.get("uuid"));
			user.setEmail(reader.get("email"));
			user.setPassword(reader.get("password"));
			user.setPermissions(Integer.valueOf(reader.get("permissions")));
			user.setLoginStatus(Boolean.valueOf(reader.get("loginstatus")));
			users.add(user);
		}
		reader.close();
	}

	public void loadSpaces() throws Exception {
		CsvReader reader = new CsvReader(path2);
		reader.readHeaders();
		while (reader.readRecord()) {
			int x = Integer.valueOf(reader.get("space"));
			parkingSpace = x;

		}
	}

	public void setSpaces(int spaceNum) throws Exception {
		System.out.println("in space=" + spaceNum);
		CsvWriter csvOutput = new CsvWriter(new FileWriter(path2, false), ',');
		// name,id,email,password
		csvOutput.write("space");
		csvOutput.endRecord();
		csvOutput.write(String.valueOf(spaceNum));
		csvOutput.endRecord();
		csvOutput.close();
	}

	// register users
	public void RegisterUser(String firstname, String lastname, String password, String email) throws Exception {
		users.add(new User(firstname, lastname, UUID.randomUUID().toString().replace("-", "") + "", email, password, 1,
				false));
		updateDB(path);
	}

	// register peo
	public void RegisterPeo(String firstname, String lastname, String password, String email) throws Exception {
		users.add(new User(firstname, lastname, UUID.randomUUID().toString().replace("-", "") + "", email, password, 2,
				false));
		updateDB(path);
	}

	// remove peo
	public void RemoveUser(String email) throws Exception {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email)) {
				users.remove(users.indexOf(u));
				updateDB(path);
				break;
			}
		}
	}

	public Boolean checkLogin(String email, String password) {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email) && u.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public Boolean checkRegister(String email) {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email)) {
				return true;
			}
		}
		return false;
	}

	public void logOutUser(String email, boolean status) throws Exception {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email)) {
				u.setLoginStatus(status);
				updateDB(path);
			}
		}
	}

	public void updateLoginStatus(String email, String pass, Boolean status) throws Exception {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email) && u.getPassword().equals(pass)) {
				u.setLoginStatus(status);
				updateDB(path);
			}
		}
	}

	public void updateDB(String path) throws Exception {
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			// name,id,email,password
			csvOutput.write("firstname");
			csvOutput.write("lastname");
			csvOutput.write("uuid");
			csvOutput.write("email");
			csvOutput.write("password");
			csvOutput.write("permissions");
			csvOutput.write("loginstatus");
			csvOutput.endRecord();

			// else assume that the file already has the correct header line
			// write out a few records
			for (User u : users) {
				csvOutput.write(u.getFirstname());
				csvOutput.write(u.getLastname());
				csvOutput.write(u.getUUID());
				csvOutput.write(u.getEmail());
				csvOutput.write(u.getPassword());
				csvOutput.write(String.valueOf(u.getPermissions()));
				csvOutput.write(String.valueOf(u.getLoginStatus()));
				csvOutput.endRecord();
			}
			csvOutput.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getUUID(String email) {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email)) {
				return u;
			}
		}
		return null;
	}

	public int getUserPerms(String email) {
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(email)) {
				return u.getPermissions();
			}
		}
		return 0;
	}
}
