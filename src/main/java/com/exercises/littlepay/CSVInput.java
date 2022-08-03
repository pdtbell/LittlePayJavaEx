package com.exercises.littlepay;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

class CSVInput {
	
	private Integer totalTaps = 0;
	private HashMap<String, Trip> trips = new HashMap<String, Trip>();
	final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
	final DateTimeFormatter DURATION_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
	
	enum TapsInput {
		ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
	}
	
	enum TripsOutput {
		Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status
	}

	public CSVInput() {
		
	}
	
	/**
	 * parseTapsCSV - parses taps from csvInputFile CSV file
	 * 
	 * @param String csvInputFile
	 * @throws IOException
	 */
	public void parseTapsCSV(String csvInputFile) throws IOException {
		
        Reader reader = Files.newBufferedReader(Paths.get(csvInputFile));
        org.apache.commons.csv.CSVParser csvParser = new org.apache.commons.csv.CSVParser(reader, org.apache.commons.csv.CSVFormat.DEFAULT
                .withHeader(TapsInput.class)
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
	    
        for(CSVRecord csvRecord : csvParser) {
        	totalTaps+=1;
        	Trip trip;
        	String tripTapsKey = csvRecord.get("CompanyID") + "." + csvRecord.get("BusID") + "." + csvRecord.get("PAN");
         	
        	if (trips.containsKey(tripTapsKey)) {
        		trip = trips.get(tripTapsKey);
        	} else {
        		trip = new Trip(csvRecord);
        		
        	}
        	
        	//System.out.println(csvRecord.get("StopID") + " - DateTimeUTC: " + csvRecord.get("DateTimeUTC"));
        	trip.addTap(csvRecord);
        	
        	trips.put(tripTapsKey, trip);
        	
        }
        csvParser.close();

	}

	/**
	 * outputTripsCSV - writes trips to csvTripsFileName CSV file
	 * 
	 * @param csvTripsFileName
	 * @throws IOException
	 */
	public void outputTripsCSV(String csvTripsFileName) throws IOException {
		
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvTripsFileName));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
				.withHeader(TripsOutput.class));
		
		for (Map.Entry<String, Trip> e : this.getTrips().entrySet()) {
			Trip trip = e.getValue();
			//System.out.println(trip.getStartString() + ", " + trip.getEndString() + ", " + trip.getFromStopId() + ", " + trip.getToStopId() + ", " + trip.getCompanyId() + ", " + trip.getBusId() + ", " + trip.getPan());
			csvPrinter.printRecord(trip.getStartString(), trip.getEndString(), trip.getTripDurationInSeconds(), 
					trip.getFromStopId(), trip.getToStopId(), trip.chargeAmount(), 
					trip.getCompanyId(), trip.getBusId(), trip.getPan(), trip.tripStatus());
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}
	
	/**
	 * getTrips - returns class member HashMap<String, Trip> trips
	 * @return HashMap<String, Trip>
	 */
	public HashMap<String, Trip> getTrips() {
		return trips;
	}
	
	/**
	 * setTrips - sets class member HashMap<String, Trip> trips
	 * @param HashMap<String, Trip> trips
	 */
	public void setTrips(HashMap<String, Trip> trips) {
		this.trips = trips;
	}
	
	/**
	 * getTotalTaps - returns total taps processed
	 * @return Integer
	 */
	public Integer getTotalTaps() {
		return totalTaps;
	}

}
