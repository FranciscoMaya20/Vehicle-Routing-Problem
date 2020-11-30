/*
 

  Based off Dr. Thangiah template of Zeus

  VRPB is created through VRPBRoot.  VRPB is used for the main throughput of zeus. It gets called many times to run the different
  heuristics,  if you add another heueistic add it to the switch statement below and add it to the VRPBRoot string array

 Group 5
 Nassir Weaver
 Francisco Maya
 Tyler Kimmel

 */




package edu.sru.group1.zeus.vrpb;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.group1.zeus.ZeusGUI.ZeusVRPBGui;
import edu.sru.group1.zeus.vrpbqualityassurance.VRPBQualityAssurance;

/**
*
* <p>VRPB:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPB
{
	int numCustomers=0; //number of customers
	int maxCapacity =0; //max capacity of trucks
	int maxDistanceTravAble=0; //max distance travelable by trucks
	int numBackhauls =0; //number of backhauls
	int numShipments =0; //number of shipments
	static double backhaulCount=0.0;
	static double shipmentCount = 0.0;
	static int numLineHaulsToSelect =0;

	long startTime, endTime; //track the CPU processing time
	private Vector mainOpts = new Vector(); //contains the collections of optimizations
	private Vector optInformation = new Vector(); //contains information about routes
	private VRPBShipmentLinkedList mainShipments = new VRPBShipmentLinkedList(); //customers read in from a file or database that are available
	private VRPBDepotLinkedList mainDepots = new VRPBDepotLinkedList(); //depots linked list for the VRPB problem
	private VRPBQualityAssurance vrpQA; //check the integrity and quality of the solution

	/*
	 * 
	 * 
	 * @param fileName and the heuristic that is running
	 */
	public VRPB(String dataFile, String heuristic)
	{

		ZeusProblemInfo.setTruckTypes(new Vector());
		ZeusProblemInfo.setNumTrucks(1);
		readDataAllFromFile(dataFile);
		printDataToConsole();
		writeDataFile(dataFile.substring(dataFile.lastIndexOf("/") + 1),heuristic);


		if (mainShipments.getVRPBHead() == null)
		{
			Settings.printDebug(Settings.ERROR,
					"VRPB: Shipment linked list is empty");
		}
		//different heursitics go here
		
		switch(heuristic)
		{
		case "EuclidianClosest": 
			ZeusProblemInfo.setSelectShipType(new ClosestEuclideanDistToDepot());
			Settings.printDebug(Settings.COMMENT, ClosestEuclideanDistToDepot.WhoAmI());
			ZeusProblemInfo.setInsertShipType(new LinearGreedyInsertShipment());
			Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());
			break;
		case "PolarClosest":
			ZeusProblemInfo.setSelectShipType(new SmallestPolarAngleShortestDistToDepot());
			Settings.printDebug(Settings.COMMENT, SmallestPolarAngleShortestDistToDepot.WhoAmI());
			ZeusProblemInfo.setInsertShipType(new LinearGreedyInsertShipment());
			Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());
			break;
		case "Polar":
			ZeusProblemInfo.setSelectShipType(new SmallestPolarAngleToDepot());
			Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());
			ZeusProblemInfo.setInsertShipType(new LinearGreedyInsertShipment());
			Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());
			break;
		}
		
	
		

		startTime = System.currentTimeMillis();
		createInitialRoutes();
		System.out.println("Completed initial routes");

		Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");

		String tempString = mainDepots.getSolutionString();
		int tempCounter =0;
		StringTokenizer info = new StringTokenizer(tempString);
		String realOutputString="";

		//changes the output from mainDepots.getSolutionString() (core change) to show the total amount of linehauls
		//and the total amount of backhauls, to the console and the gui.
		while (info.hasMoreTokens())
		{
			if(tempCounter != 1)
			{
				String att = info.nextToken("|").trim();
				realOutputString += att + " | ";
			}
			else
			{
				String att = info.nextToken("|").trim();
				realOutputString += "Total linehaul = " + shipmentCount + " | Total backhaul = " + backhaulCount + " | ";

			}
			tempCounter++;
		}


		Settings.printDebug(Settings.COMMENT,
				"Initial Stats: " + realOutputString) ;
		
		
		System.out.println("Starting QA");
		vrpQA = new VRPBQualityAssurance(mainDepots, mainShipments);
		if (vrpQA.runQA() == false) {
			Settings.printDebug(Settings.ERROR, "QA FAILED!");
		}
		else {
			Settings.printDebug(Settings.COMMENT, "QA succeeded");


		}
		writeLongSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1),heuristic);
		writeShortSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1),heuristic); 
		//outputResultsToFile(dataFile, realOutputString);
		checkSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));
		if(dataFile.equals("n5.xls") && heuristic.equals("Polar"))
		{
			ZeusVRPBGui guiPost = new ZeusVRPBGui(mainDepots, mainShipments,shipmentCount,backhaulCount);
		}
		

	}

	/*
	 * 
	 * 
	 * this checks the solution with the values in the "An innovative Metaheuritic Solution Approached from the Vehicle Routing Problem With Backhauls
	 * By Emmanouil E Zachariadis, Chris T Kiranoudis
	 */
	void checkSolution(String fileString)
	{
		File solutionFile = new File(ZeusProblemInfo.getInputPath() + fileString + "_solutionCheck.xls");
		File checkFile = new File(ZeusProblemInfo.getInputPath() + "checkAnswers.xls");
		String[] headings = new String[6];
		float[] data = new float[6];
		if(solutionFile.exists())
		{
			
			try
			{
				FileInputStream fileIn = new FileInputStream(new File(ZeusProblemInfo.getOutputPath() + fileString + "_solutionCheck.xls"));
				HSSFWorkbook workbook2 = new HSSFWorkbook(fileIn);
				HSSFSheet sheet2 = workbook2.getSheetAt(0);
				int rowNum = sheet2.getLastRowNum();
				for(int n=0;n<6;n++)
				{
					headings[n]= sheet2.getRow(0).getCell(n+3).toString();
				}
				for(int n=0;n<6;n++)
				{
					String convertString = sheet2.getRow(1).getCell(n+3).toString();
					data[n]= Float.parseFloat(convertString);
				}
				Row row2 = sheet2.createRow(++rowNum);
				Cell cell2 = row2.createCell(0);

				String selectHeuristic = ZeusProblemInfo.getSelectShipType().toString();
				String[] detailedSelectHeurisitc = selectHeuristic.split("\\.");
				selectHeuristic = detailedSelectHeurisitc[detailedSelectHeurisitc.length-1];
				int findAmp = selectHeuristic.indexOf("@");
				selectHeuristic = selectHeuristic.substring(0,findAmp);
				cell2.setCellValue(selectHeuristic);

				String detailedString = mainDepots.getAttributes().toDetailedString();
				String[] detailedStringSplit = detailedString.split(" | ");
				int lengthOfDetailedSplit = detailedStringSplit.length;
				detailedString = detailedStringSplit[lengthOfDetailedSplit-1];
				detailedString = detailedString.substring(0,detailedString.length()-1);

				cell2=row2.createCell(1);
				cell2.setCellValue(detailedString);
				detailedString = detailedString.substring(1,detailedString.length());
				float integerDetailed = Float.parseFloat(detailedString);
				int arrayInt = 0;
				float percent=0;
				for(int i=3;i<9;i++)
				{

					cell2=row2.createCell(i);
					percent = ((integerDetailed - data[arrayInt])/data[arrayInt])*100;
					cell2.setCellValue(percent + "%");
					arrayInt++;

				}


				for(int i=0; i < 10; i++)
				{
					sheet2.autoSizeColumn(i);
				}
				try {
					FileOutputStream checkSolution =
							new FileOutputStream(new File(ZeusProblemInfo.getOutputPath() + fileString + "_solutionCheck.xls"));
					workbook2.write(checkSolution);
					checkSolution.close();


				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}


		}
		else
		{
			if(checkFile.exists())
			{

				try
				{
					int rowNumIn = 0;
					FileInputStream checkIn = new FileInputStream(new File(ZeusProblemInfo.getInputPath() + "checkAnswers.xls"));
					HSSFWorkbook workBookIn = new HSSFWorkbook(checkIn);
					HSSFSheet inFile = workBookIn.getSheetAt(0);
					for(int n=0;n<6;n++)
					{
						headings[n]= inFile.getRow(0).getCell(n+1).toString();
					}

					String checkString = fileString.substring(0,2);
					for (Row row : inFile)
					{
						for (Cell cell : row) 
						{

							if(cell.toString().equals(checkString))
							{
								rowNumIn= row.getRowNum();
							}


						}
					}
					for(int n=0;n<6;n++)
					{
						String convertString = inFile.getRow(rowNumIn).getCell(n+1).toString();
						data[n]= Float.parseFloat(convertString);
					}

					checkIn.close();


				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				HSSFWorkbook workbook2 = new HSSFWorkbook();
				HSSFSheet sheet2 = workbook2.createSheet("Check Solution data");
				int rowNum = 0;

				Row row2 = sheet2.createRow(rowNum);
				Cell cell2 = row2.createCell(0);

				cell2.setCellValue("File:");
				cell2=row2.createCell(1);
				cell2.setCellValue(fileString);
				int arrayCounter =0;
				for(int i=3;i<9;i++)
				{

					cell2=row2.createCell(i);
					cell2.setCellValue(headings[arrayCounter]);
					arrayCounter++;
				}
				arrayCounter =0;

				row2=sheet2.createRow(++rowNum);
				for(int i=3;i<9;i++)
				{

					cell2=row2.createCell(i);
					cell2.setCellValue(data[arrayCounter]);
					arrayCounter++;
				}
				row2=sheet2.createRow(++rowNum);

				cell2=row2.createCell(0);
				String selectHeuristic = ZeusProblemInfo.getSelectShipType().toString();
				String[] detailedSelectHeurisitc = selectHeuristic.split("\\.");
				selectHeuristic = detailedSelectHeurisitc[detailedSelectHeurisitc.length-1];
				int findAmp = selectHeuristic.indexOf("@");
				selectHeuristic = selectHeuristic.substring(0,findAmp);
				cell2.setCellValue(selectHeuristic);

				String detailedString = mainDepots.getAttributes().toDetailedString();
				String[] detailedStringSplit = detailedString.split(" | ");
				int lengthOfDetailedSplit = detailedStringSplit.length;
				detailedString = detailedStringSplit[lengthOfDetailedSplit-1];
				detailedString = detailedString.substring(0,detailedString.length()-1);

				cell2=row2.createCell(1);
				cell2.setCellValue(detailedString);
				detailedString = detailedString.substring(1,detailedString.length());
				float integerDetailed = Float.parseFloat(detailedString);
				int arrayInt = 0;
				float percent=0;
				for(int i=3;i<9;i++)
				{

					cell2=row2.createCell(i);
					percent = ((integerDetailed - data[arrayInt])/data[arrayInt])*100;
					cell2.setCellValue(percent + "%");
					arrayInt++;

				}

				for(int i=0; i < 10; i++)
				{
					sheet2.autoSizeColumn(i);
				}



				try {
					FileOutputStream checkSolution =
							new FileOutputStream(new File(ZeusProblemInfo.getOutputPath() + fileString + "_solutionCheck.xls"));
					workbook2.write(checkSolution);
					checkSolution.close();


				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				//checkFile doesnt exist
			}

		}






		
	}
	//obsolete function, didnt have the heart to erase my time away..... .
	void outputResultsToFile(String dataFile, String output)
	{
		File outAnswer = new File(ZeusProblemInfo.getOutputPath() + "outAnswers.xls");
		if(outAnswer.exists())
		{
			try{
				FileInputStream readFile = new FileInputStream(new File(ZeusProblemInfo.getOutputPath() + "outAnswers.xls" ));

				//create workbook,sheet,and cell for POI to read
				HSSFWorkbook workbook2 = new HSSFWorkbook(readFile);
				HSSFSheet sheet2 = workbook2.getSheetAt(0);
				int rowNum=sheet2.getPhysicalNumberOfRows();
				rowNum+=2;

				Row row2 = sheet2.createRow(rowNum);
				Cell cell2 = row2.createCell(0);


				cell2.setCellValue(dataFile);
				rowNum +=1;
				row2 = sheet2.createRow(rowNum);
				cell2 = row2.createCell(0);
				cell2.setCellValue(output);
				try {
					FileOutputStream outAnswers =
							new FileOutputStream(new File(ZeusProblemInfo.getOutputPath()+"outAnswers.xls"));
					workbook2.write(outAnswers);
					outAnswers.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//if this is the first time running through, go into this loop and output starting info
		else
		{
			int rowNum=0;
			HSSFWorkbook workbook1 = new HSSFWorkbook();
			HSSFSheet sheet1 = workbook1.createSheet("Answers data");

			Row row1 = sheet1.createRow(rowNum);
			Cell cell1 = row1.createCell(0);

			cell1.setCellValue(dataFile);


			rowNum=1;
			row1=sheet1.createRow(rowNum);
			cell1 = row1.createCell(0);
			cell1.setCellValue(output);

			try {
				FileOutputStream outAnswers =
						new FileOutputStream(new File(ZeusProblemInfo.getOutputPath()+"outAnswers.xls"));
				workbook1.write(outAnswers);
				outAnswers.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}






	}

	//function called to create the initial routes, no matter which heuristics are picked the heuristic picked will run
	public void createInitialRoutes()
	{
		//OptInfo has old and new attributes
		VRPBDepot currDepot = null; //current depot
		VRPBShipment currShip = null; //current shipment
		//int countLoop=0;

		//check if selection and insertion type methods have been selected
		if (ZeusProblemInfo.getSelectShipType() == null) {
			Settings.printDebug(Settings.ERROR,
					"No selection shipment type has been assigned");

		}
		if (ZeusProblemInfo.getInsertShipType() == null) {
			Settings.printDebug(Settings.ERROR,
					"No insertion shipment type has been assigned");
		}


		//countLoop=1;
		while (!mainShipments.isAllShipsAssigned()) {
			double x, y;
			int i = 0;
			//Get the x an y coordinate of the depot
			//Then use those to get the customer, that has not been allocated,
			// that is closest to the depot
			currDepot = (VRPBDepot) mainDepots.getVRPBHead().getNext();
			x = mainDepots.getHead().getXCoord();
			y = mainDepots.getHead().getYCoord();
			//Send the entire mainDepots and mainShipments to get the next shipment
			//to be inserted including the current depot

			VRPBShipment theShipment = mainShipments.getNextInsertShipment(mainDepots,
					currDepot, mainShipments, currShip);

			if (theShipment == null) { //shipment is null, print error message
				Settings.printDebug(Settings.COMMENT, "No shipment was selected");
			}
			//The selected shipment will be inserted into the route


			//try to insert the shipment
			if (!mainDepots.insertShipment(theShipment)) {
				Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +
						"> cannot be routed");
			}
			else {
				Settings.printDebug(Settings.COMMENT,
						"The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
						"> was routed");
				//tag the shipment as being routed
				theShipment.setIsAssigned(true);
			}
		}

		ZeusProblemInfo.getDepotLLLevelCostF().calculateTotalsStats(mainDepots); //calculate the stats 
	}

	//read all the data in from the files using POI 
	//@param fileName
	public int readDataAllFromFile(String VRPBFileName)
	{
		try {
			FileInputStream file = new FileInputStream(new File(ZeusProblemInfo.getInputPath() + VRPBFileName));

			//create workbook,sheet,and cell for POI to read
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Cell cell = null;
			int colCounter = 0;
			String numCustomersString;
			String maxCapacityString;
			String maxDistanceTravAbleString;
			String numBackhaulsString;
			String numShipmentsString;

			cell = sheet.getRow(0).getCell(colCounter);
			//first line of file...... number of shipments, max capacity of truck, max distance , ignore , ignore, num backhauls, num shipments
			while(colCounter <=6)
			{

				//switch statement to run through first row
				switch (colCounter) {
				case 0: //number of customers case
					numCustomersString = cell.toString(); //convert from a "cell" to a string
					int numCustomersStringSize = numCustomersString.length(); //get length of that string
					numCustomersString = numCustomersString.substring(0,numCustomersStringSize-2); //get rid of the decimal point that POI puts
					numCustomers = Integer.parseInt(numCustomersString);//parse int
					// System.out.println("numCustomers is " + numCustomers);
					break;
				case 1: //max capacity case
					maxCapacityString = cell.toString();
					int maxCapacityStringSize = maxCapacityString.length();
					maxCapacityString = maxCapacityString.substring(0,maxCapacityStringSize-2);
					maxCapacity = Integer.parseInt(maxCapacityString);
					// System.out.println("maxCapcity is " + maxCapacity);
					break;
				case 2: //max distance case
					maxDistanceTravAbleString = cell.toString();
					int maxDistanceTravAbleSize = maxDistanceTravAbleString.length();
					maxDistanceTravAbleString = maxDistanceTravAbleString.substring(0,maxDistanceTravAbleSize-2);
					maxDistanceTravAble = Integer.parseInt(maxDistanceTravAbleString);
					// System.out.println("maxDistanceTravAble is " + maxDistanceTravAble);
					break;
				case 3://ignorable case
					//ignore
					break;
				case 4: //ignorable case
					//ignore
					break;
				case 5: //num backhauls case
					numBackhaulsString = cell.toString();
					int numBackhaulsStringSize = numBackhaulsString.length();
					numBackhaulsString = numBackhaulsString.substring(0,numBackhaulsStringSize-2);
					numBackhauls = Integer.parseInt(numBackhaulsString);
					// System.out.println("numBackhauls is " + numBackhauls);
					break;
				case 6: //num shipments case
					numShipmentsString = cell.toString();
					int numShipmentsStringSize = numShipmentsString.length();
					numShipmentsString = numShipmentsString.substring(0,numShipmentsStringSize-2);
					numShipments = Integer.parseInt(numShipmentsString);
					// System.out.println("numShipments is " + numShipments);
					break;
				default:
					System.out.println("Default case was hit in first line of reading!");
					break;
				} //end switch
				//get next cell
				cell = sheet.getRow(0).getCell(++colCounter);



			} //end first row
			ZeusProblemInfo.setNumDepots(1);
			ZeusProblemInfo.setProbFileName(VRPBFileName);
			//ProblemInfo.probType=
			//ProblemInfo.noOfVehs = !!!?
			ZeusProblemInfo.setNoOfShips(numCustomers);
			ZeusProblemInfo.setNumCustomers(numShipments); // num linehauls


			if(maxDistanceTravAble == 0)
			{
				maxDistanceTravAble = 1000000;
			}

			if(maxCapacity == 0)
			{
				maxCapacity = 1000000;
			}
			String serviceType = "1";
			int numTruckTypes = 1;
			//our problem only has one truck type
			for(int i =0; i < numTruckTypes; i++)
			{
				VRPBTruckType truckType = new VRPBTruckType(i,maxDistanceTravAble,maxCapacity,serviceType);
				ZeusProblemInfo.addTruckTypes(truckType);
			}

			Vector custType = new Vector();

			for(int ct=0; ct < 2;ct++)
			{
				custType.add(new Integer(1));
			}

			// put in first line here for file
			// more obsolute code...... 
			File outputFile = new File(ZeusProblemInfo.getOutputPath()+"readInData.xls");
			//if this isnt the first time running through, go into this loop
			if(outputFile.exists())
			{
				try{
					FileInputStream readFile = new FileInputStream(new File(ZeusProblemInfo.getOutputPath() + "readInData.xls" ));

					//create workbook,sheet,and cell for POI to read
					HSSFWorkbook workbook2 = new HSSFWorkbook(readFile);
					HSSFSheet sheet2 = workbook2.getSheetAt(0);
					int rowNum=sheet2.getPhysicalNumberOfRows();
					rowNum+=2;
					file.close();
					Row row2 = sheet2.createRow(rowNum++);
					Cell cell2 = row2.createCell(0);


					cell2.setCellValue("Number of customers");
					cell2 = row2.createCell(1);
					cell2.setCellValue("Capacity of trucks");
					cell2 = row2.createCell(2);
					cell2.setCellValue("Max Distance");
					cell2 = row2.createCell(3);
					cell2.setCellValue("Number Backhauls");
					cell2 = row2.createCell(4);
					cell2.setCellValue("Number regular shipments");

					row2 = sheet2.createRow(rowNum++);
					//put in first line of info
					cell2=row2.createCell(0);
					cell2.setCellValue(ZeusProblemInfo.getNoOfShips());
					cell2=row2.createCell(1);
					cell2.setCellValue(maxCapacity);
					cell2=row2.createCell(2);
					cell2.setCellValue(maxDistanceTravAble);
					cell2=row2.createCell(3);
					cell2.setCellValue(numBackhauls);
					cell2=row2.createCell(4);
					cell2.setCellValue(numShipments);

					try {
						FileOutputStream out =
								new FileOutputStream(new File(ZeusProblemInfo.getOutputPath()+"readInData.xls"));
						workbook2.write(out);
						out.close();
						// System.out.println("Excel written successfully to " + ProblemInfo.outputPath+"readInData.xls");

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//if this is the first time running through, go into this loop and output starting info
			else
			{
				int rowNum=0;
				HSSFWorkbook workbook2 = new HSSFWorkbook();
				HSSFSheet sheet2 = workbook2.createSheet("Readin data");

				Row row2 = sheet2.createRow(rowNum);
				Cell cell2 = row2.createCell(0);

				cell2.setCellValue("Number of customers");
				cell2 = row2.createCell(1);
				cell2.setCellValue("Capacity of trucks");
				cell2 = row2.createCell(2);
				cell2.setCellValue("Max Distance");
				cell2 = row2.createCell(3);
				cell2.setCellValue("Number Backhauls");
				cell2 = row2.createCell(4);
				cell2.setCellValue("Number regular shipments");


				rowNum=1;
				row2 = sheet2.createRow(rowNum);
				//put in first line of info
				cell2=row2.createCell(0);
				cell2.setCellValue(ZeusProblemInfo.getNoOfShips());
				cell2=row2.createCell(1);
				cell2.setCellValue(maxCapacity);
				cell2=row2.createCell(2);
				cell2.setCellValue(maxDistanceTravAble);
				cell2=row2.createCell(3);
				cell2.setCellValue(numBackhauls);
				cell2=row2.createCell(4);
				cell2.setCellValue(numShipments);

				try {
					FileOutputStream out =
							new FileOutputStream(new File(ZeusProblemInfo.getOutputPath()+"readInData.xls"));
					workbook2.write(out);
					out.close();
					// System.out.println("Excel written successfully to " + ProblemInfo.outputPath+"readInData.xls");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}




			//start second row of data to read in...depot.... depot x coord, depot y coord, depot index
			colCounter =0;

			int depotXCoord=0;
			int depotYCoord =0;
			int depotIndex=0;

			String depotXCoordString;
			String depotYCoordString;
			String depotIndexString;
			cell = sheet.getRow(1).getCell(colCounter);
			while(colCounter <= 2)
			{

				switch(colCounter)
				{
				case 0:
					depotXCoordString = cell.toString();
					int depotXCoordStringSize = depotXCoordString.length();
					depotXCoordString = depotXCoordString.substring(0,depotXCoordStringSize-2);
					depotXCoord = Integer.parseInt(depotXCoordString);
					//System.out.println("depotXCoord is " + depotXCoord);
					break;
				case 1:
					depotYCoordString = cell.toString();
					int depotYCoordStringSize = depotYCoordString.length();
					depotYCoordString = depotYCoordString.substring(0,depotYCoordStringSize-2);
					depotYCoord = Integer.parseInt(depotYCoordString);
					//System.out.println("depotYCoord is " + depotYCoord);
					break;

				case 2:
					depotIndexString = cell.toString();
					int depotIndexStringSize = depotIndexString.length();
					depotIndexString = depotIndexString.substring(0,depotIndexStringSize-2);
					depotIndex = Integer.parseInt(depotIndexString);
					//System.out.println("depotIndex is " + depotIndex);
					break;


				}
				cell = sheet.getRow(1).getCell(++colCounter);


			} // end second row
			//adds the depot to the depot linked list (since our problem only deals with one depot.
			VRPBDepot depot = new VRPBDepot(depotIndex,depotXCoord,depotYCoord);
			mainDepots.insertDepotLast(depot);

			depot = (VRPBDepot)mainDepots.getHead().getNext();
			for(int i=0; i < ZeusProblemInfo.getTruckTypesSize();i++)
			{
				VRPBTruckType ttype = (VRPBTruckType) ZeusProblemInfo.getTruckTypesAt(i);
				depot.getMainTrucks().insertTruckLast(new VRPBTruck(ttype,depot.getxCoord(),depot.getyCoord()));
			}
			//add depot to file here

			try{
				FileInputStream readFile = new FileInputStream(new File(ZeusProblemInfo.getOutputPath() + "readInData.xls" ));

				//create workbook,sheet,and cell for POI to read
				HSSFWorkbook workbook2 = new HSSFWorkbook(readFile);
				HSSFSheet sheet2 = workbook2.getSheetAt(0);
				int rowNum=sheet2.getPhysicalNumberOfRows();
				rowNum +=2;
				file.close();
				Row row2 = sheet2.createRow(rowNum++);
				Cell cell2 = row2.createCell(0);

				cell2.setCellValue("DepotX");
				cell2 = row2.createCell(1);
				cell2.setCellValue("DepotY");
				cell2 = row2.createCell(2);
				cell2.setCellValue("DepotNum");

				row2=sheet2.createRow(rowNum++);
				cell2=row2.createCell(0);
				cell2.setCellValue(depotXCoord);
				cell2=row2.createCell(1);
				cell2.setCellValue(depotYCoord);
				cell2=row2.createCell(2);
				cell2.setCellValue(depotIndex);

				try {
					FileOutputStream out2 =
							new FileOutputStream(new File(ZeusProblemInfo.getOutputPath()+"readInData.xls"));

					workbook2.write(out2);
					out2.close();
					//System.out.println("Excel written successfully to " + ProblemInfo.outputPath+"readInData.xls");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}



			//last row, shipments.  empty, x coord, y coord, amount
			int rowCounter = 2;
			colCounter=1;

			int xCoord=0;
			int yCoord=0;
			int shipmentAmount=0;
			int regularShipmentAmount =0;
			int backhaulShipmentAmount=0;
			String shipmentType = "";
			String xCoordString;
			String yCoordString;
			String shipmentAmountString;
			int counter3 =0;
			int shipmentIndex =0;
			// cell = sheet.getRow(rowCounter).getCell(colCounter);

			//cell = null pointer exception through POI when null row is hit
			while(rowCounter < numCustomers + 2)
			{

				cell = sheet.getRow(rowCounter).getCell(colCounter);  //cell = null pointer exception through POI when null row is hit
				while(colCounter <= 3)
				{

					switch(colCounter)
					{
					case 1:
						xCoordString = cell.toString();
						int xCoordStringSize = xCoordString.length();
						xCoordString = xCoordString.substring(0,xCoordStringSize-2);
						xCoord = Integer.parseInt(xCoordString);
						//System.out.println("xCoord is " + xCoord);
						counter3++;

						if(rowCounter <= numCustomers - numBackhauls +1)
						{
							//System.out.println("Regular Shipment");
							shipmentType = "1"; //regularShipment
						}
						else
						{
							//System.out.println("Backhaul");
							shipmentType = "2"; //backhaul
						}
						break;

					case 2:
						yCoordString = cell.toString();
						int yCoordStringSize = yCoordString.length();
						yCoordString = yCoordString.substring(0,yCoordStringSize-2);
						yCoord = Integer.parseInt(yCoordString);
						//System.out.println("yCoord is " + yCoord);
						break;
					case 3:
						shipmentAmountString = cell.toString();
						int shipmentAmountStringSize = shipmentAmountString.length();
						shipmentAmountString = shipmentAmountString.substring(0,shipmentAmountStringSize-2);
						shipmentAmount = Integer.parseInt(shipmentAmountString);
						//System.out.println("shipmentAmount is " + shipmentAmount);
						break;
					}
					colCounter++;

					if(colCounter <=3 )
						cell = sheet.getRow(rowCounter).getCell(colCounter);

				}
				shipmentIndex++;

				//insert the shipment into the mainShipments linked list 
				mainShipments.insertShipment(shipmentType, xCoord, yCoord, shipmentAmount,shipmentIndex);//calls insert shipment so that the shipment can be created and added to the linked list


				colCounter = 1;
				++rowCounter;

			}


			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//print the read in data to the console
	public void printDataToConsole()
	{
		//prints out the shipment data to the excel file
		try
		{
			mainShipments.printVRPBShipmentsToConsole();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Write out the data file that was read in
	 * @param file name of file used for generating the data
	 */

	//write the shipments to a file (this is what makes my code obsolute)
	//@param file that you are doing and heuristic that you are doing
	public void writeDataFile(String file, String heuristic) {

		mainShipments.writeVRPBShipments(file, heuristic);

	}

	/**
	 * Will write a long detailed solution for the problem
	 * @param file name of the file to write to and heuristic that you are using
	 */
	public void writeLongSolution(String file, String heuristic) {


		mainDepots.printDepotLinkedList(file, heuristic);

	}
	// will write a short detailed solution for the problem to the filename
	//@param file name of the file you are using and heuristic you are using
	public void writeShortSolution(String fileString, String heuristic) {



		HSSFWorkbook workbook2 = new HSSFWorkbook();
		HSSFSheet sheet2 = workbook2.createSheet("Shipment data");
		int rowNum = 0;
		Row row2 = sheet2.createRow(rowNum);
		Cell cell2 = row2.createCell(0);

		cell2.setCellValue("File:");
		cell2=row2.createCell(1);
		cell2.setCellValue(fileString);
		cell2=row2.createCell(2);
		cell2.setCellValue("Num Depots");
		cell2=row2.createCell(3);
		cell2.setCellValue(ZeusProblemInfo.getNumDepots());
		cell2=row2.createCell(4);
		cell2.setCellValue("Num Customers");
		cell2=row2.createCell(5);
		cell2.setCellValue(ZeusProblemInfo.getNumCustomers());
		cell2=row2.createCell(6);
		cell2.setCellValue("Num Trucks");
		cell2=row2.createCell(7);
		cell2.setCellValue(ZeusProblemInfo.getNumTrucks());
		cell2=row2.createCell(8);
		cell2.setCellValue("Processing Time:");
		cell2=row2.createCell(9);
		cell2.setCellValue((endTime-startTime)/1000 + " seconds");

		row2=sheet2.createRow(++rowNum);
		cell2=row2.createCell(0);
		cell2.setCellValue(mainDepots.getAttributes().toDetailedString());
		row2=sheet2.createRow(++rowNum);


		Depot depotHead = mainDepots.getHead();
		Depot depotTail = mainDepots.getTail();

		while(depotHead != depotTail)
		{
			Truck truckHead = depotHead.getMainTrucks().getHead().getNext();
			Truck truckTail = depotHead.getMainTrucks().getTail();

			while(truckHead != truckTail)
			{

				cell2=row2.createCell(0);
				cell2.setCellValue("Truck # ");
				cell2=row2.createCell(1);
				cell2.setCellValue(truckHead.getTruckNum());
				cell2=row2.createCell(2);
				cell2.setCellValue("MaxCap:");
				cell2=row2.createCell(3);
			    cell2.setCellValue(truckHead.getTruckType().getMaxCapacity());
				cell2=row2.createCell(4);
				cell2.setCellValue("Demand: ");
				cell2=row2.createCell(5);
				cell2.setCellValue(truckHead.getAttributes().getTotalDemand());
				cell2=row2.createCell(6);
				cell2.setCellValue("Route:");

				Nodes nodesHead = truckHead.getMainNodes().getHead().getNext();
				Nodes nodesTail = truckHead.getMainNodes().getTail();
				int counter =6;

				
				 while (nodesHead != nodesTail)
	              {
	                cell2=row2.createCell(++counter);
		        	  cell2.setCellValue(nodesHead.getIndex());

	                nodesHead = nodesHead.getNext();
	              }

				row2=sheet2.createRow(++rowNum);
				truckHead = truckHead.getNext();
			}


			row2=sheet2.createRow(++rowNum);
			row2=sheet2.createRow(++rowNum);
			depotHead = depotHead.getNext();
		}

		for (int i = 0; i < optInformation.size(); i++) {
			row2=sheet2.createRow(++rowNum);
			cell2=row2.createCell(5);
			cell2.setCellValue((double) optInformation.elementAt(i));

		}
		for(int i=0; i < 12; i++)
		{
			sheet2.autoSizeColumn(i);
		}



		try {
			FileOutputStream shipmentsOutToFile =
					new FileOutputStream(new File(ZeusProblemInfo.getOutputPath() + fileString + "_shortAnswers_"+heuristic+".xls"));
			workbook2.write(shipmentsOutToFile);
			shipmentsOutToFile.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
