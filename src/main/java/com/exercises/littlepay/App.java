package com.exercises.littlepay;

import java.io.File;
import java.io.IOException;

public class App {
	
	public static void main(String[] args) {
		// non-standard assumption of user input value
		String csvTapsFileName = "res/taps.csv";
		String csvTripsFileName = "res/trips.csv";
		if (args.length > 0) {
			csvTapsFileName = args[0];
		}
		if (args.length > 1) {
			csvTripsFileName = args[1];
		}
		
		File csvTapsFile = new File(csvTapsFileName);
		if (!csvTapsFile.exists() || !csvTapsFile.isFile() || !csvTapsFile.canRead()) {
			System.out.println("The taps file provided (" + csvTapsFileName + ") either doesn't exist, is inaccessible or unreadable.");
			System.exit(1);
		}
		File csvTripsFile = new File(csvTripsFileName);
		if (!csvTripsFile.canWrite()) {
			System.out.println("The trips output file provided (" + csvTripsFileName + ") is inaccessible or non-writeable.");
			System.exit(1);
		}

		CSVInput tapsCSVInput = new CSVInput();
		
		try {
			tapsCSVInput.parseTapsCSV(csvTapsFileName);
			
			tapsCSVInput.outputTripsCSV(csvTripsFileName);
			
			System.out.println("Processing complete!");

		} catch (IOException ex) {
			
			System.out.println("Processing Error: " + ex.getMessage());
			
		}

		System.out.println("Total Taps: " + tapsCSVInput.getTotalTaps());
		System.out.println("Total Trips: " + tapsCSVInput.getTrips().size());
		System.out.println("Trips CSV file written to:: " + csvTripsFileName);
		
	}

}

