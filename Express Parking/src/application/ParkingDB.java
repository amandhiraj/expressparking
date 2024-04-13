package application;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class ParkingDB {
	Database db = new Database();
	public ArrayList<Parking> parking = new ArrayList<Parking>();
	public HashMap<Integer, String> userBooking = new HashMap<Integer, String>();
	public static String path = "parking.csv";

	public void load() throws Exception {
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();
		// uuid,parkingNumber1,parkingNumber2,parkingNumber3,licenseNumber1,
		// licenseNumber2,licenseNumber3,parkingOne,parkingOneTime,parkingTwo,
		// parkingTwoTime,parkingThree,parkingThreeTime,count
		while (reader.readRecord()) {
			Parking park = new Parking();
			// name,id,email,password
			park.setUuid(reader.get("uuid"));
			park.setParkingNumber1(reader.get("parkingNumber1"));
			park.setParkingNumber2(reader.get("parkingNumber2"));
			park.setParkingNumber3(reader.get("parkingNumber3"));
			park.setLicenseNumber1(reader.get("licenseNumber1"));
			park.setLicenseNumber2(reader.get("licenseNumber2"));
			park.setLicenseNumber3(reader.get("licenseNumber3"));
			park.setParkingOne(reader.get("parkingOne"));
			park.setParking1PayStatus(reader.get("parking1PayStatus"));
			park.setParking1Timestamp(reader.get("parking1Timestamp"));
			park.setParkingOneTime(reader.get("parkingOneTime"));
			park.setParkingTwo(reader.get("parkingTwo"));
			park.setParking2PayStatus(reader.get("parking2PayStatus"));
			park.setParking2Timestamp(reader.get("parking2Timestamp"));
			park.setParkingTwoTime(reader.get("parkingTwoTime"));
			park.setParkingThree(reader.get("parkingThree"));
			park.setParking3PayStatus(reader.get("parking3PayStatus"));
			park.setParking3Timestamp(reader.get("parking3Timestamp"));
			park.setParkingThreeTime(reader.get("parkingThreeTime"));
			park.setCount(Integer.valueOf(reader.get("count")));
			parking.add(park);
		}
	}

	public void updateDB(String path) throws Exception {
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			// uuuid,parkingNumber,licenseNumber,parkingOne,parkingOneTime,parkingTwo,parkingTwoTime,parkingThree,parkingThreeTime,count
			csvOutput.write("uuid");
			csvOutput.write("parkingNumber1");
			csvOutput.write("parkingNumber2");
			csvOutput.write("parkingNumber3");
			csvOutput.write("licenseNumber1");
			csvOutput.write("licenseNumber2");
			csvOutput.write("licenseNumber3");
			csvOutput.write("parkingOne");
			csvOutput.write("parking1PayStatus");
			csvOutput.write("parking1Timestamp");
			csvOutput.write("parkingOneTime");
			csvOutput.write("parkingTwo");
			csvOutput.write("parking2PayStatus");
			csvOutput.write("parking2Timestamp");
			csvOutput.write("parkingTwoTime");
			csvOutput.write("parkingThree");
			csvOutput.write("parking3PayStatus");
			csvOutput.write("parking3Timestamp");
			csvOutput.write("parkingThreeTime");
			csvOutput.write("count");
			csvOutput.endRecord();

			// else assume that the file already has the correct header line
			// write out a few records
			for (Parking p : parking) {
				csvOutput.write(p.getUuid());
				csvOutput.write(p.getParkingNumber1());
				csvOutput.write(p.getParkingNumber2());
				csvOutput.write(p.getParkingNumber3());
				csvOutput.write(p.getLicenseNumber1());
				csvOutput.write(p.getLicenseNumber2());
				csvOutput.write(p.getLicenseNumber3());
				csvOutput.write(p.getParkingOne());
				csvOutput.write(p.getParking1PayStatus());
				csvOutput.write(p.getParking1Timestamp());
				csvOutput.write(p.getParkingOneTime());
				csvOutput.write(p.getParkingTwo());
				csvOutput.write(p.getParking2PayStatus());
				csvOutput.write(p.getParking2Timestamp());
				csvOutput.write(p.getParkingTwoTime());
				csvOutput.write(p.getParkingThree());
				csvOutput.write(p.getParking3PayStatus());
				csvOutput.write(p.getParking3Timestamp());
				csvOutput.write(p.getParkingThreeTime());
				csvOutput.write(String.valueOf(p.getCount()));
				csvOutput.endRecord();
			}
			csvOutput.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadUserBookings(String uuid) throws Exception {
		for (Parking p : parking) {
			if (p.getUuid().equals(uuid)) {
				userBooking.put(userBooking.size() + 1, p.getParkingOne());
				userBooking.put(userBooking.size() + 1, p.getParkingTwo());
				userBooking.put(userBooking.size() + 1, p.getParkingThree());
			}
		}
	}

	public Boolean checkOccupation(String parkingNumber) {

		for (Parking p : parking) {

			if (p.getParkingNumber1().equals(parkingNumber) || p.getParkingNumber2().equals(parkingNumber)
					|| p.getParkingNumber3().equals(parkingNumber)) {

				return true;
			}
		}
		return false;
	}

	// uuid,parkingNumber,licenseNumber,parkingOne,parkingOneTime,parkingTwo,parkingTwoTime,parkingThree,parkingThreeTime,count
	public Boolean addNewParking(String uuid, String parkingNumber, String licenseNumber, String parkingTime)
			throws Exception {

		for (Parking p : parking) {
			if (p.getUuid().equals(uuid) && p.getParkingOne().isBlank() && p.getParkingTwo().isBlank()
					&& p.getParkingThree().isBlank()) {
				parking.remove(parking.indexOf(p));
				updateDB(path);
				break;
			}
		}
		// uuid,parkingNumber1,parkingNumber2,parkingNumber3,licenseNumber1,licenseNumber2,licenseNumber3,parkingOne,parking1PayStatus,parkingOneTime,parkingTwo,parking2PayStatus,parkingTwoTime,parkingThree,parking3PayStatus,parkingThreeTime,count

		if (getUserParkingCount(uuid) == 0) {
			System.out.println("adding first entry");
			parking.add(new Parking(uuid, parkingNumber, "", "", licenseNumber, "", "", genNums(6), "Not Paid", "",
					parkingTime, "", "", "", "", "", "", "", "", 1));
			updateDB(path);
			return true;
		} else if (getUserParkingCount(uuid) == 1) {
			System.out.println("adding second entry");
			for (Parking p : parking) {
				if (p.getUuid().equals(uuid)) {
					p.setParkingTwo(genNums(6));
					p.setParkingNumber2(parkingNumber);
					p.setLicenseNumber2(licenseNumber);
					p.setParkingTwoTime(parkingTime);
					p.setParking2PayStatus("Not Paid");
					p.setCount(p.getCount() + 1);
				}
			}
			updateDB(path);
			return true;
		} else if (getUserParkingCount(uuid) == 2) {
			System.out.println("adding third entry");
			for (Parking p : parking) {
				if (p.getUuid().equals(uuid)) {
					p.setParkingThree(genNums(6));
					p.setParkingNumber3(parkingNumber);
					p.setLicenseNumber3(licenseNumber);
					p.setParkingThreeTime(parkingTime);
					p.setParking3PayStatus("Not Paid");
					p.setCount(p.getCount() + 1);
				}
			}
			updateDB(path);
			return true;
		} else {
			System.err.println("Max parking reached");
			return false;
		}
	}

	public void CancelBooking(String UUID, String bookingID) throws Exception {
		// TODO Auto-generated method stub
		for (Parking p : parking) {
			if (p.getUuid().equals(UUID) && p.parkingOne.equals(bookingID)) {
				System.out.println("Cancelling Parking 1");
				p.setParkingNumber1("");
				p.setParkingOne("");
				p.setParkingOneTime("");
				p.setLicenseNumber1("");
				p.setParking1PayStatus("");
				p.setParking1Timestamp("");
				p.setCount(p.getCount() - 1);
				if (p.getCount() == 0) {
					p.setCount(1);
				}
				updateDB(path);
				break;
			}
			if (p.getUuid().equals(UUID) && p.parkingTwo.equals(bookingID)) {
				System.out.println("Cancelling Parking 2");
				p.setParkingNumber2("");
				p.setParkingTwo("");
				p.setParkingTwoTime("");
				p.setLicenseNumber2("");
				p.setParking2PayStatus("");
				p.setParking2Timestamp("");
				p.setCount(p.getCount() - 1);
				if (p.getCount() == 0) {
					p.setCount(1);
				}
				updateDB(path);
				break;
			}
			if (p.getUuid().equals(UUID) && p.parkingThree.equals(bookingID)) {
				System.out.println("Cancelling Parking 3");
				p.setParkingNumber3("");
				p.setParkingThree("");
				p.setParkingThreeTime("");
				p.setLicenseNumber3("");
				p.setParking3PayStatus("");
				p.setParking3Timestamp("");
				if (p.getCount() == 0) {
					p.setCount(1);
				}
				updateDB(path);
				break;
			}
		}
	}

	public void customerPayment(String UUID, String bookingID, String timeStamp) throws Exception {
		// TODO Auto-generated method stub
		for (Parking p : parking) {
			if (p.getUuid().equals(UUID) && p.getParkingOne().equals(bookingID)) {
				p.setParking1PayStatus("Waiting on admin");
				p.setParking1Timestamp(timeStamp);
			}
			if (p.getUuid().equals(UUID) && p.getParkingTwo().equals(bookingID)) {
				p.setParking2PayStatus("Waiting on admin");
				p.setParking2Timestamp(timeStamp);
			}
			if (p.getUuid().equals(UUID) && p.getParkingThree().equals(bookingID)) {
				p.setParking3PayStatus("Waiting on admin");
				p.setParking3Timestamp(timeStamp);;
			}
		}
		updateDB(path);
	}

	public int getUserParkingCount(String uuid) {
		int count = 0;
		for (Parking p : parking) {
			if (p.getUuid().equals(uuid)) {
				if (p.getParkingOne() != "") {
					count++;
				}
				if (p.getParkingTwo() != "") {
					count++;
				}
				if (p.getParkingThree() != "") {
					count++;
				}
			}
		}
		return count;
	}

	public static String genNums(int n) {

		String x = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (x.length() * Math.random());
			sb.append(x.charAt(index));
		}
		return sb.toString();
	}
}
