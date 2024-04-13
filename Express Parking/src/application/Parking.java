package application;

import java.util.UUID;

public class Parking {
	public String uuid;
	public String parkingNumber1;
	public String parkingNumber2;
	public String parkingNumber3;
	public String licenseNumber1;
	public String licenseNumber2;
	public String licenseNumber3;
	public String parkingOne;
	public String parking1PayStatus;
	public String parking1Timestamp;
	public String parkingOneTime;
	public String parkingTwo;
	public String parking2PayStatus;
	public String parking2Timestamp;
	public String parkingTwoTime;
	public String parkingThree;
	public String parking3PayStatus;
	public String parking3Timestamp;
	public String parkingThreeTime;
	public int count;

	// uuid,parkingNumber,licenseNumber,bookingID,parkingOne,parkingOneTime,parkingTwo,parkingTwoTime,parkingThree,parkingThreeTime,count
	public Parking(String uuid, String parkingNumber1, String parkingNumber2, String parkingNumber3,
			String licenseNumber1, String licenseNumber2, String licenseNumber3, String parkingOne,
			String parking1PayStatus, String parking1Timestamp, String parkingOneTime, String parkingTwo,
			String parking2PayStatus, String parking2Timestamp, String parkingTwoTime, String parkingThree,
			String parking3PayStatus, String parking3Timestamp, String parkingThreeTime, int count) {
		super();
		this.uuid = uuid;
		this.parkingNumber1 = parkingNumber1;
		this.parkingNumber2 = parkingNumber2;
		this.parkingNumber3 = parkingNumber3;
		this.licenseNumber1 = licenseNumber1;
		this.licenseNumber2 = licenseNumber2;
		this.licenseNumber3 = licenseNumber3;
		this.parkingOne = parkingOne;
		this.parking1PayStatus = parking1PayStatus;
		this.parking1Timestamp = parking1Timestamp;
		this.parkingOneTime = parkingOneTime;
		this.parkingTwo = parkingTwo;
		this.parking2PayStatus = parking2PayStatus;
		this.parking2Timestamp = parking1Timestamp;
		this.parkingTwoTime = parkingTwoTime;
		this.parkingThree = parkingThree;
		this.parking3PayStatus = parking3PayStatus;
		this.parking3Timestamp = parking1Timestamp;
		this.parkingThreeTime = parkingThreeTime;
		this.count = count;
	}

	public String getParking1Timestamp() {
		return parking1Timestamp;
	}

	public void setParking1Timestamp(String parking1Timestamp) {
		this.parking1Timestamp = parking1Timestamp;
	}

	public String getParking2Timestamp() {
		return parking2Timestamp;
	}

	public void setParking2Timestamp(String parking2Timestamp) {
		this.parking2Timestamp = parking2Timestamp;
	}

	public String getParking3Timestamp() {
		return parking3Timestamp;
	}

	public void setParking3Timestamp(String parking3Timestamp) {
		this.parking3Timestamp = parking3Timestamp;
	}

	public String getParking1PayStatus() {
		return parking1PayStatus;
	}

	public void setParking1PayStatus(String parking1PayStatus) {
		this.parking1PayStatus = parking1PayStatus;
	}

	public String getParking2PayStatus() {
		return parking2PayStatus;
	}

	public void setParking2PayStatus(String parking2PayStatus) {
		this.parking2PayStatus = parking2PayStatus;
	}

	public String getParking3PayStatus() {
		return parking3PayStatus;
	}

	public void setParking3PayStatus(String parking3PayStatus) {
		this.parking3PayStatus = parking3PayStatus;
	}

	public String getParkingOneTime() {
		return parkingOneTime;
	}

	public void setParkingOneTime(String parkingOneTime) {
		this.parkingOneTime = parkingOneTime;
	}

	public String getParkingTwoTime() {
		return parkingTwoTime;
	}

	public void setParkingTwoTime(String parkingTwoTime) {
		this.parkingTwoTime = parkingTwoTime;
	}

	public String getParkingThreeTime() {
		return parkingThreeTime;
	}

	public void setParkingThreeTime(String parkingThreeTime) {
		this.parkingThreeTime = parkingThreeTime;
	}

	public String getParkingOne() {
		return parkingOne;
	}

	public void setParkingOne(String parkingOne) {
		this.parkingOne = parkingOne;
	}

	public String getParkingTwo() {
		return parkingTwo;
	}

	public void setParkingTwo(String parkingTwo) {
		this.parkingTwo = parkingTwo;
	}

	public String getParkingThree() {
		return parkingThree;
	}

	public void setParkingThree(String parkingThree) {
		this.parkingThree = parkingThree;
	}

	public Parking() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParkingNumber1() {
		return parkingNumber1;
	}

	public void setParkingNumber1(String parkingNumber1) {
		this.parkingNumber1 = parkingNumber1;
	}

	public String getParkingNumber2() {
		return parkingNumber2;
	}

	public void setParkingNumber2(String parkingNumber2) {
		this.parkingNumber2 = parkingNumber2;
	}

	public String getParkingNumber3() {
		return parkingNumber3;
	}

	public void setParkingNumber3(String parkingNumber3) {
		this.parkingNumber3 = parkingNumber3;
	}

	public String getLicenseNumber1() {
		return licenseNumber1;
	}

	public void setLicenseNumber1(String licenseNumber1) {
		this.licenseNumber1 = licenseNumber1;
	}

	public String getLicenseNumber2() {
		return licenseNumber2;
	}

	public void setLicenseNumber2(String licenseNumber2) {
		this.licenseNumber2 = licenseNumber2;
	}

	public String getLicenseNumber3() {
		return licenseNumber3;
	}

	public void setLicenseNumber3(String licenseNumber3) {
		this.licenseNumber3 = licenseNumber3;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Parking [uuid=" + uuid + ", parking number=" + parkingNumber1 + "|" + parkingNumber2 + "|"
				+ parkingNumber3 + ", licenseNumber=" + licenseNumber1 + "|" + licenseNumber2 + "|" + licenseNumber3
				+ ", booking id=" + parkingOne + "|" + parkingTwo + "|" + parkingThree + ", Time=" + parkingOneTime
				+ "|" + parkingTwoTime + "|" + parkingThreeTime + ", payStatus=" + parking1PayStatus + "|"
				+ parking2PayStatus + "|" + parking3PayStatus + ", count=" + count + "]";
	}
}
