package Customer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import application.Database;
import application.Parking;
import application.ParkingDB;
import application.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CustomerMain {

	private String uuid;
	double x, y;
	private String userEmail;
	private String userFirstLast;
	private String UUID;
	Database db = new Database();
	ParkingDB parkDB = new ParkingDB();
	User user;

	@FXML
	private ComboBox<Integer> customer_cb_space;
	@FXML
	private ComboBox<String> customer_cb_t2_bookingID;
	@FXML
	private Label customer_lbl_name;
	@FXML
	private ListView<String> customer_vb_list;
	@FXML
	private Label customer_lbl_status;
	@FXML
	private Label customer_payment_total;
	@FXML
	private Label customer_lb_payment;
	@FXML
	private TextField customer_tb_t2_bookingID;
	@FXML
	private TextField customer_tb_parkingNum;
	@FXML
	private CheckBox payment_m_credit;

	@FXML
	private CheckBox payment_m_paypal;

	@FXML
	private CheckBox payment_m_debit;

	@FXML
	private CheckBox payment_m_stripe;
	@FXML
	private TextField customer_tb_booktime;

	@FXML
	private TextField customer_tb_plateNum;

	@FXML
	private Button customer_btn_bookspace;

	@FXML
	void customer_mouse_dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);

	}

	@FXML
	void customer_mouse_pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	void customer_mouse_close(MouseEvent event) {
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
	void customer_mouse_minus(MouseEvent event) {
		System.out.println("min");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void customer_btn_logout_pressed(MouseEvent event) {
		System.out.println("logout");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			db.logOutUser(this.userEmail, false);
			Stage primaryStage = new Stage();

			Parent root = FXMLLoader.load(getClass().getResource("../Auth/Login/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Auth/Login/application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			stage.close();
			primaryStage.show();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	void LoadSpaces(MouseEvent event) throws Exception {
		System.out.println("Loading Spaces..");
		db.loadSpaces();
		customer_cb_space.getItems().clear();
		customer_cb_space.getItems()
				.addAll(IntStream.rangeClosed(1, Database.parkingSpace).boxed().collect(Collectors.toList()));
	}

	public void LoadBookings(MouseEvent event) throws Exception {
		System.out.println("Loading Bookings..");
		updateList();
	}

	public void LoadUserBookings() throws Exception {
		parkDB.parking.clear();
		parkDB.load();
		System.out.println("Loading User Bookings..");
		customer_vb_list.getItems().clear();
		for (Parking p : parkDB.parking) {
			if (p.getUuid().equals(UUID)) {
				if (p.getParkingOne() != "") {
					customer_vb_list.getItems().add("Booking ID: " + p.getParkingOne() + " - Time: "
							+ p.getParkingOneTime() + " - Status: " + p.getParking1PayStatus());
				}
				if (p.getParkingTwo() != "") {
					customer_vb_list.getItems().add("Booking ID: " + p.getParkingTwo() + " - Time: "
							+ p.getParkingTwoTime() + " - Status: " + p.getParking2PayStatus());
				}
				if (p.getParkingThree() != "") {
					customer_vb_list.getItems().add("Booking ID: " + p.getParkingThree() + " - Time: "
							+ p.getParkingThreeTime() + " - Status: " + p.getParking3PayStatus());
				}
			}
		}
	}

	public void initData(ArrayList<User> users, String currEmail) throws Exception {
		db.load();
		parkDB.load();
		updateList();
		customer_cb_space.getItems()
				.addAll(IntStream.rangeClosed(1, Database.parkingSpace).boxed().collect(Collectors.toList()));
		// TODO Auto-generated method stub
		this.userEmail = currEmail;
		for (User u : users) {
			if (u.getEmail().equals(currEmail)) {
				this.userFirstLast = u.getFirstname() + " " + u.getLastname();
				this.UUID = u.getUUID();
			}
		}
		customer_lbl_name.setText("Welcome, " + userFirstLast);
		LoadUserBookings();

		// observe payment total
		customer_vb_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		customer_vb_list.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			double x = customer_vb_list.getSelectionModel().getSelectedItems().size();
			customer_payment_total.setText("Total: $" + (double) (x * 10.55));
		});
	}

	private void countDown(int time, String bookingID, String UUID, int interval) {
		Timer timer = new java.util.Timer();
		int delay = 1000;
		int period = 1000;
		timer.scheduleAtFixedRate(new TimerTask() {
			int x = interval;

			public void run() {
				Platform.runLater(() -> {
					try {
						if (x == 1) {
							timer.cancel();
							for (Parking p : parkDB.parking) {
								if (p.getUuid().equals(UUID) && p.getParkingOne().equals(bookingID)) {
									p.setParkingOneTime("Expired");
									customer_lb_payment.setText("One of you parking has expired");
									customer_lb_payment.setTextFill(Color.YELLOW);
								}
								if (p.getUuid().equals(UUID) && p.getParkingTwo().equals(bookingID)) {
									p.setParking2PayStatus("Waiting on admin");
									p.setParkingTwoTime("Expired");
									customer_lb_payment.setText("One of you parking has expired");
									customer_lb_payment.setTextFill(Color.YELLOW);
								}
								if (p.getUuid().equals(UUID) && p.getParkingThree().equals(bookingID)) {
									p.setParkingThreeTime("Expired");
									customer_lb_payment.setText("One of you parking has expired");
									customer_lb_payment.setTextFill(Color.YELLOW);
								}
							}
							parkDB.updateDB("parking.csv");
							updateList();
						}
						x--;
						System.out.println("Timer for: " + bookingID + " - " + x);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		}, delay, period);
	}

	public void customerBookspace(ActionEvent event) throws Exception {
		// String uuid, String parkingNumber, String licenseNumber, String time
		if (!parkDB.checkOccupation(customer_cb_space.getValue().toString())) {
			if (parkDB.addNewParking(UUID, customer_cb_space.getValue().toString(),
					customer_tb_plateNum.getText().toString(), customer_tb_booktime.getText().toString())) {
				customer_lbl_status.setText("Suceessfully Added your parking.");
				customer_lbl_status.setTextFill(Color.LIMEGREEN);
			} else {
				customer_lbl_status.setText("You have reached your parking Limit");
				customer_lbl_status.setTextFill(Color.ORANGERED);
			}
		} else {
			customer_lbl_status.setText("This parking is occupied, please select another.");
			customer_lbl_status.setTextFill(Color.ORANGERED);
		}
	}

	public void customerCancelspace(ActionEvent event) throws Exception {
		// String uuid, String parkingNumber, String licenseNumber, String time
		String[] stringText = customer_cb_t2_bookingID.getValue().replaceAll(" ", "").split(":");
		System.out.println("selected to unbook =" + stringText[1].toString().split("-")[0]);
		parkDB.CancelBooking(UUID, stringText[1].toString().split("-")[0]);
		updateList();
		LoadUserBookings();
	}

	public void updateList() throws Exception {
		customer_cb_t2_bookingID.getItems().clear();
		LoadUserBookings();
		for (Parking p : parkDB.parking) {
			if (p.getUuid().equals(UUID)) {
				if (p.getParkingOne() != "") {
					customer_cb_t2_bookingID.getItems().add("Booking ID: " + p.getParkingOne() + " - Time: "
							+ p.getParkingOneTime() + " - Status: " + p.getParking1PayStatus());
				}
				if (p.getParkingTwo() != "") {
					customer_cb_t2_bookingID.getItems().add("Booking ID: " + p.getParkingTwo() + " - Time: "
							+ p.getParkingTwoTime() + " - Status: " + p.getParking2PayStatus());
				}
				if (p.getParkingThree() != "") {
					customer_cb_t2_bookingID.getItems().add("Booking ID: " + p.getParkingThree() + " - Time: "
							+ p.getParkingThreeTime() + " - Status: " + p.getParking3PayStatus());
				}
			}
		}
	}

	public void customerPay() throws Exception {
		if (payment_m_stripe.isSelected() || payment_m_debit.isSelected() || payment_m_paypal.isSelected()
				|| payment_m_credit.isSelected()) {
			customer_lb_payment.setText("Payment Successful. Please wait for admin!");
			customer_lb_payment.setTextFill(Color.LIMEGREEN);
			ObservableList<String> selected = customer_vb_list.getSelectionModel().getSelectedItems();
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			for (String s : selected) {
				String[] stringText = s.replaceAll(" ", "").split(":");
				parkDB.customerPayment(UUID, stringText[1].toString().split("-")[0], ts.toString());
				countDown(20, stringText[1].toString().split("-")[0], UUID,
						Integer.valueOf(stringText[2].toString().split("-")[0]));
				System.out.println(stringText[1].toString().split("-")[0]);
			}
			LoadUserBookings();
		} else {
			customer_lb_payment.setText("Please select payment method.");
			customer_lb_payment.setTextFill(Color.ORANGERED);
		}
	}
}