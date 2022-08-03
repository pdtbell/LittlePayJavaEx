/**
 * 
 */
package com.exercises.littlepay;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.csv.CSVRecord;

/**
 * @author pdtbe
 *
 */
public class Trip {
	private String id;
	private String dateTimeUTC;
	private String tapType;
	private String stopId;
	private String companyId;
	private String busId;
	private String pan;
	private LocalDateTime start = null;
	private LocalDateTime end = null;
	private String fromStopId = "";
	private String toStopId = "";
	private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
	
	/**
	 * Trip - Constructor using passed CSVRecord
	 * @param csvRecord
	 */
	public Trip(CSVRecord csvRecord) {
		this.id = csvRecord.get("ID");
		this.dateTimeUTC = csvRecord.get("DateTimeUTC");
		this.tapType = csvRecord.get("TapType");
		this.stopId = csvRecord.get("StopId");
		this.companyId = csvRecord.get("CompanyId");
		this.busId = csvRecord.get("BusId");
		this.pan = csvRecord.get("PAN");
	}
	
	/**
	 * Trip - Empty Constructor
	 */
	public Trip() {

	}

	/**
	 * tripStatus - returns the trip status
	 * @return String
	 */
	public String tripStatus() {
		if (this.getToStopId().equals("")) {
			return "INCOMPLETE";
		} else if (this.getToStopId().equals(this.getFromStopId())) {
			return "CANCELLED";
		} else {
			return "COMPLETED";
		}
	}
	
	/**
	 * chargeAmount - determine the trip cost
	 * @return Float
	 */
	public Float chargeAmount() {
		
		Float tripFee = Float.valueOf("0.00");
		
		if (this.getFromStopId().equals(this.getToStopId())) {

			tripFee = Float.valueOf("0.00");
			
		} else if (this.getToStopId() == null || this.getToStopId().equals("")) {
			
			if (this.getFromStopId().equals("Stop1") || this.getFromStopId().equals("Stop3")) {
					// Greatest fare from stop 1 or 3 is 7.30
					tripFee = Float.valueOf("7.30");
			} else {
					// Greatest fare from stop 2 is 5.50
					tripFee = Float.valueOf("5.50");
			}
			
		} else {
			Integer stopSum = this.getStopNumber(this.getFromStopId()) + this.getStopNumber(this.getToStopId());
			if (stopSum == 3) {
				// Stop1 <-> Stop2
				tripFee = Float.valueOf("3.25");				
			} else if (stopSum == 4) {
				// Stop1 <-> Stop3
				tripFee = Float.valueOf("7.30");
			} else if (stopSum == 5) {
				// Stop2 <-> Stop3
				tripFee = Float.valueOf("5.50");
			}
		
		}
		
		return tripFee;
		
	}
	
	/**
	 * getStopNumber - Return the integer value of the stop number
	 * @param String stopExpr
	 * @return Integer
	 */
	public static Integer getStopNumber(String stopExpr) {
		return Integer.valueOf(stopExpr.replace("Stop", ""));
	}
	
	/**
	 * addTap - Adds tap to trip
	 * @param CSVRecord csvRecord
	 */
	public void addTap(CSVRecord csvRecord) {
    	if (csvRecord.get("TapType").equals("OFF")) {
    		this.setToStopId(csvRecord.get("StopID"));
    		this.setEnd(LocalDateTime.parse(csvRecord.get("DateTimeUTC"), CSV_DATE_FORMAT));
    	} else {
    		this.setFromStopId(csvRecord.get("StopID"));
    		this.setStart(LocalDateTime.parse(csvRecord.get("DateTimeUTC"), CSV_DATE_FORMAT));
    	}

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateTimeUTC() {
		return dateTimeUTC;
	}

	public void setDateTimeUTC(String dateTimeUTC) {
		this.dateTimeUTC = dateTimeUTC;
	}

	public String getTapType() {
		return tapType;
	}

	public void setTapType(String tapType) {
		this.tapType = tapType;
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public String getStartString() {
		if (start != null) {
			return CSV_DATE_FORMAT.format(start);
		} else {
			return "";
		}
			
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public String getEndString() {
		if (end != null) {
			return CSV_DATE_FORMAT.format(end);
		} else {
			return "";
		}
			
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Long getTripDurationInSeconds() {
	
		if (this.getStart() != null && this.getEnd() != null) {
			return Duration.between(this.getStart(), this.getEnd()).toSeconds();
		} else {
			return Long.valueOf("0");
		}
			
	}
	
	public String getFromStopId() {
		return fromStopId;
	}

	public Integer getFromStopNumber() {
		return this.getStopNumber(this.getFromStopId());
	}

	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}

	public String getToStopId() {
		return toStopId;
	}

	public Integer getToStopNumber() {
		return this.getStopNumber(this.getToStopId());
	}


	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}
	
//	/**
//	 * tripChargeAmount - determine the trip cost (long way)
//	 * @return
//	 */
//	public Float tripChargeAmount() {
//		
//		if (this.tripStatus().equals("COMPLETED")) {
//			if ((this.getFromStopId().equals("Stop1")||this.getFromStopId().equals("Stop2")) && 
//					(this.getToStopId().equals("Stop2")||this.getToStopId().equals("Stop1"))) {
//				
//				return Float.valueOf("3.25");
//				
//			} else if ((this.getFromStopId().equals("Stop2")||this.getFromStopId().equals("Stop3")) && 
//						(this.getToStopId().equals("Stop2")||this.getToStopId().equals("Stop3"))) {
//				
//				return Float.valueOf("5.50");
//				
//			} else if ((this.getFromStopId().equals("Stop1")||this.getFromStopId().equals("Stop3")) && 
//					(this.getToStopId().equals("Stop1")||this.getToStopId().equals("Stop3"))) {
//				
//				return Float.valueOf("7.30");
//				
//			}
//		} else if (this.tripStatus().equals("INCOMPLETE")) {
//			if (this.getFromStopId().equals("Stop1") || this.getFromStopId().equals("Stop3")) {
//				
//				return Float.valueOf("7.30");
//				
//			} else if (this.getFromStopId().equals("Stop2")) {
//				
//				return Float.valueOf("5.50");
//				
//			}
//			
//		}
//		
//		return Float.valueOf("0.00");
//	}
	
//public String getTapsKey() {
//	return this.getCompanyId() + "." + this.getBusId() + "." + this.getPan();
//}
//
///**
//* completTrip - completes a trip
//* @param csvRecord
//*/
//public void completeTrip(CSVRecord csvRecord) {
//	this.setToStopId(csvRecord.get("StopID"));
//	this.setEnd(LocalDateTime.parse(csvRecord.get("DateTimeUTC"), CSV_DATE_FORMAT));
//}
//

}
