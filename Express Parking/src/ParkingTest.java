import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import application.Database;
import application.ParkingDB;
import application.User;


class ParkingTest {
	
	Database db;
	ParkingDB parkingDB;
	ArrayList<User> testUsers;


	@Test
	void test_SetSpaces() throws Exception {
		db = new Database();
		db.loadSpaces();
		db.setSpaces(1);
		assertEquals(1, db.parkingSpace);
	}
	@Test
	void test_user_Exists() throws Exception {
		db = new Database();
		db.load();
		assertEquals(true, db.checkRegister("songwang@yorku.ca"));
	}
	
	@Test
	void test_perms() throws Exception {
		db = new Database();
		db.load();
		assertEquals(3, db.getUserPerms("songwang@yorku.ca"));
	}
	
	@Test
	void test_registeration() throws Exception {
		db = new Database();
		db.load();
		assertEquals(true, db.checkRegister("songwang@yorku.ca"));
	}
	@Test
	void test_login() throws Exception {
		db = new Database();
		db.load();
		assertEquals(true, db.checkLogin("songwang@yorku.ca", "admin"));
	}
	@Test
	void test_AddUser() throws Exception {
		db = new Database();
		db.load();
		db.RegisterUser("", "", "", "user1@gmail.com");
		assertEquals(true, db.checkRegister("user1@gmail.com"));
	}
	@Test
	void test_AddPeo() throws Exception {
		db = new Database();
		db.load();
		db.RegisterPeo("", "", "", "user2@gmail.com");
		assertEquals(true, db.checkRegister("user2@gmail.com"));
	}
	@Test
	void test_removeUser() throws Exception {
		db = new Database();
		db.load();
		db.RemoveUser("user1@gmail.com");
		assertEquals(false, db.checkRegister("user1@gmail.com"));
	}
	@Test
	void test_removePeo() throws Exception {
		db = new Database();
		db.load();
		db.RemoveUser("user2@gmail.com");
		assertEquals(false, db.checkRegister("user2@gmail.com"));
	}
	
	//parking DB
	
	@Test
	void test_pdb_addParking() throws Exception {
		parkingDB = new ParkingDB();
		parkingDB.load();
		parkingDB.addNewParking("12345", "1", "LKJ", "10");
		assertEquals(1, parkingDB.getUserParkingCount("12345"));
		//parkingDB.customerPayment("12345", parkingDB.tempBooking, "23:40:30");
		parkingDB.LoadUserBookings("12345");
		assertEquals(3, parkingDB.userBooking.size());
		assertEquals(true, parkingDB.checkOccupation("1"));
		assertEquals(false, parkingDB.checkOccupation("56"));
		//parkingDB.CancelBooking("12345", parkingDB.tempBooking);
		
	}
	

}
