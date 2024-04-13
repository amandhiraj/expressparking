package Admin;

import java.util.ArrayList;

import application.Database;
import application.Parking;
import application.ParkingDB;
import application.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class AdminMain {
	double x, y;
	private String userEmail;
	private String userFirstLast;
	Database db = new Database();
	ParkingDB parkDB = new ParkingDB();

	@FXML
	private TextField admin_tb_fn;

	@FXML
	private TextField admin_tb_ln;

	@FXML
	private TextField admin_tb_password;

	@FXML
	private TextField admin_tb_email;

	@FXML
	private TextField admin_tb2_fn;

	@FXML
	private TextField admin_tb2_ln;
	@FXML
	private TextField admin_tb2_email;

	@FXML
	private Label admin_lbl_name;
	@FXML
	private Label admin_lb_status;
	@FXML
	private Label admin_lb_statusPay;
	@FXML
	private Button admin_btn_logout;
	@FXML
	private ComboBox<String> admin_cb_booking;

	@FXML
	void LoadBookings(MouseEvent event) {
		System.out.println("hitting");
		admin_cb_booking.getItems().clear();
		for (Parking p : parkDB.parking) {
			if (p.getParkingOne() != "") {
				admin_cb_booking.getItems().add(p.getParkingOne());
			}
			if (p.getParkingTwo() != "") {
				admin_cb_booking.getItems().add(p.getParkingTwo());
			}
			if (p.getParkingThree() != "") {
				admin_cb_booking.getItems().add(p.getParkingThree());
			}
		}
	}

	@FXML
	void admin_mouse_dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);

	}

	@FXML
	void admin_mouse_pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	void admin_mouse_close(MouseEvent event) {
		System.out.println("close");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			db.logOutUser(this.userEmail, false);
			stage.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	void admin_mouse_minus(MouseEvent event) {
		System.out.println("min");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void admin_btn_logout_pressed(MouseEvent event) {
		System.out.println("logout");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			db.logOutUser(this.userEmail, false);
			Stage primaryStage = new Stage();

			Parent root = FXMLLoader.load(getClass().getResource("../Auth/Login/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			stage.close();
			primaryStage.show();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void initData(ArrayList<User> users, String currEmail) throws Exception {
		db.load();
		parkDB.load();
		// TODO Auto-generated method stub
		this.userEmail = currEmail;
		for (User u : users) {
			if (u.getEmail().toLowerCase().equals(userEmail)) {
				this.userFirstLast = u.getFirstname() + " " + u.getLastname();
			}
		}
		admin_lbl_name.setText("Welcome, " + userFirstLast);
	}

	public void AdminAddPeo() throws Exception {
		if (!db.checkRegister(admin_tb_email.getText().toLowerCase())) {
			if (admin_tb_fn.getText().isBlank() || admin_tb_ln.getText().isBlank() || admin_tb_email.getText().isBlank()
					|| admin_tb_password.getText().isBlank()) {
				admin_lb_status.setText("Please Fill add Fields");
				admin_lb_status.setTextFill(Color.ORANGERED);
			} else if (admin_tb_email.getText().length() < 3 || !admin_tb_email.getText().contains("@")) {
				admin_lb_status.setText("Invalid Email.");
				admin_lb_status.setTextFill(Color.ORANGERED);
			} else {
				db.RegisterPeo(admin_tb_fn.getText(), admin_tb_ln.getText(), admin_tb_password.getText(),
						admin_tb_email.getText().toLowerCase());
				admin_lb_status.setText("Successfully Added PEO.");
				admin_lb_status.setTextFill(Color.LIMEGREEN);
			}
		} else {
			admin_lb_status.setText("Email already exists");
			admin_lb_status.setTextFill(Color.ORANGERED);
		}
	}

	public void AdminRemovePeo() throws Exception {
		if (db.checkRegister(admin_tb_email.getText().toLowerCase())) {
			db.RemoveUser(admin_tb_email.getText().toLowerCase());
			admin_lb_status.setText("Successfully Removed PEO.");
			admin_lb_status.setTextFill(Color.LIMEGREEN);
		} else {
			admin_lb_status.setText("PEO Does not exist.");
			admin_lb_status.setTextFill(Color.ORANGERED);
		}
	}

	public void AdminPayUser() throws Exception {
		if (db.checkRegister(admin_tb2_email.getText().toLowerCase())) {
			for (Parking p : parkDB.parking) {
				if (p.getParkingOne().equals(admin_cb_booking.getValue())) {
					p.setParking1PayStatus("Paid");
					admin_lb_statusPay.setText("Payment Status Paid");
					admin_lb_statusPay.setTextFill(Color.LIMEGREEN);
				}
				if (p.getParkingTwo().equals(admin_cb_booking.getValue())) {
					p.setParking2PayStatus("Paid");
					admin_lb_statusPay.setText("Payment Status Paid");
					admin_lb_statusPay.setTextFill(Color.LIMEGREEN);
				}
				if (p.getParkingThree().equals(admin_cb_booking.getValue())) {
					p.setParking3PayStatus("Paid");
					admin_lb_statusPay.setText("Payment Status Paid");
					admin_lb_statusPay.setTextFill(Color.LIMEGREEN);
				}
			}
		parkDB.updateDB("parking.csv");
		} else {
			admin_lb_statusPay.setText("User does not exist.");
			admin_lb_statusPay.setTextFill(Color.ORANGERED);
		}
	}

}
