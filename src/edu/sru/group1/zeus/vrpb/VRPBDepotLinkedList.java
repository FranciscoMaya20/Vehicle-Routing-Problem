package edu.sru.group1.zeus.vrpb;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.DepotLinkedList;
import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.thangiah.zeus.core.Truck;

/**
*
* <p>VRPBDepotLinkedList:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBDepotLinkedList extends DepotLinkedList implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBDepotLinkedList()
	{
		setHead(new VRPBDepot());
		setTail(new VRPBDepot());
		linkHeadTail();
		setAttributes(new VRPBAttributes());

	}
	public boolean insertShipment(VRPBShipment theShipment)
	{
		boolean status = false;

		VRPBDepot depot = (VRPBDepot)super.getHead().getNext();
		VRPBTruckLinkedList truckLL = (VRPBTruckLinkedList)depot.getMainTrucks();
		
		while(depot != this.getTail())
		{
			truckLL = (VRPBTruckLinkedList)depot.getMainTrucks();
			
			status = truckLL.insertShipment(theShipment);

			if(status)
			{
				break;
			}
			depot = (VRPBDepot) depot.getNext();
		}
		return status;
	}
	public VRPBDepot getVRPBHead()
	{
		return (VRPBDepot)getHead();
	}

	public Object clone()
	{
		VRPBDepotLinkedList cloneDepotLinkedList = new VRPBDepotLinkedList();

		cloneDepotLinkedList.setAttributes((VRPBAttributes)this.getAttributes().clone());
		cloneDepotLinkedList.setHead((VRPBDepot)this.getHead().clone());

		cloneDepotLinkedList.getHead().setPrev(null);

		if(this.getHead() != this.getTail())
		{
			VRPBDepot currentDepot = (VRPBDepot) cloneDepotLinkedList.getHead();
			VRPBDepot nextDepot = (VRPBDepot)this.getHead().getNext();

			while(nextDepot != null)
			{
				currentDepot.setNext((VRPBDepot)nextDepot.clone());
				currentDepot.getNext().setPrev(currentDepot);
				currentDepot=(VRPBDepot)currentDepot.getNext();
				nextDepot = (VRPBDepot)nextDepot.getNext();


				if(nextDepot == null)
				{
					cloneDepotLinkedList.setTail(currentDepot);
					currentDepot.setNext(null);
				}
			}
		}
		else
		{
			cloneDepotLinkedList.setTail(cloneDepotLinkedList.getHead());
		}
		return cloneDepotLinkedList;
	}
	
	public void printDepotLinkedList(String fileString, String heuristic)
	{
		HSSFWorkbook workbook2 = new HSSFWorkbook();
		  HSSFSheet sheet2 = workbook2.createSheet("Long Solution data");
		 int rowNum = 0;
		  Row row2 = sheet2.createRow(rowNum);
		  Cell cell2 = row2.createCell(0);
		  cell2.setCellValue("File:");
		  cell2=row2.createCell(1);
		  cell2.setCellValue(fileString);
		  row2=sheet2.createRow(++rowNum);
		  cell2=row2.createCell(0);
		  cell2.setCellValue("Number of depots:");
		  cell2=row2.createCell(1);
		  cell2.setCellValue(ZeusProblemInfo.getNumDepots());
		  row2=sheet2.createRow(++rowNum);
		  row2=sheet2.createRow(++rowNum);
		  cell2=row2.createCell(0);
		  boolean nodeHeadings = false; //used so that each node doesnt print the headings for the data, just once per truck 
		 boolean isFirstNode = true;
		  float distanceCalc = 0;
		  float totalDistance =0;
		  float distancePerTruck=0;
		  float totalDemand = 0;
		  float totalDemandPerTruck = 0;
		
		
		  
		  
		  Depot depot = getHead().getNext();
		  
		  try
		  {
			  while(depot != getTail())
			  {
				  cell2.setCellValue("Depot number");
					cell2=row2.createCell(1);
					cell2.setCellValue("XCoord");
					cell2=row2.createCell(2);
					cell2.setCellValue("YCoord");
					cell2=row2.createCell(3);
					cell2.setCellValue("Total Demand");
					cell2=row2.createCell(4);
					cell2.setCellValue("Total Distance");
					cell2=row2.createCell(5);
					cell2.setCellValue("Number of trucks");
					  row2=sheet2.createRow(++rowNum);
					
				  cell2=row2.createCell(0);
				 cell2.setCellValue(depot.getDepotNum());
				 cell2=row2.createCell(1);
				 cell2.setCellValue(depot.getxCoord());
				 cell2=row2.createCell(2);
				 cell2.setCellValue(depot.getYCoord());
				 cell2=row2.createCell(3);
				 cell2.setCellValue(depot.getAttributes().getTotalDemand());
				 cell2=row2.createCell(4);
				 cell2.setCellValue(depot.getAttributes().getTotalDistance());
				 cell2=row2.createCell(5);
				 cell2.setCellValue(depot.getMainTrucks().getSize());
				
				 row2=sheet2.createRow(++rowNum);
				 row2=sheet2.createRow(++rowNum);
				 
				 
				  Truck truck = depot.getMainTrucks().getHead().getNext();
			        Truck truckTail = depot.getMainTrucks().getTail();
			        
			        while(truck!= truckTail)
			        {
			        	cell2=row2.createCell(0);
			        	 cell2.setCellValue("Truck Number");
							cell2=row2.createCell(1);
							cell2.setCellValue("Service Type");
							cell2=row2.createCell(2);
							cell2.setCellValue("Total Demand of truck");
							cell2=row2.createCell(3);
							cell2.setCellValue("Total Distance of truck");
							cell2=row2.createCell(4);
							cell2.setCellValue("Max Capacity of Truck");
							cell2=row2.createCell(5);
							cell2.setCellValue("Max Distance of Truck");
							cell2=row2.createCell(6);
							cell2.setCellValue("Size of truck");
							  row2=sheet2.createRow(++rowNum);
			        	
			        	  cell2=row2.createCell(0);
			        	 cell2.setCellValue(truck.getTruckNum());
						 cell2=row2.createCell(1);
						 cell2.setCellValue(truck.getTruckType().getServiceType());
						 cell2=row2.createCell(2);
						 cell2.setCellValue(truck.getAttributes().getTotalDemand());
						 cell2=row2.createCell(3);
						 cell2.setCellValue(truck.getAttributes().getTotalDistance());
						 cell2=row2.createCell(4);
						 cell2.setCellValue(truck.getTruckType().getMaxCapacity());
						 cell2=row2.createCell(5);
						 cell2.setCellValue(truck.getTruckType().getMaxDuration());
						 cell2=row2.createCell(6);
						 cell2.setCellValue(truck.getMainNodes().getSize());
						 row2=sheet2.createRow(++rowNum);
						  row2=sheet2.createRow(++rowNum);
						 
						 Nodes cell = truck.getMainNodes().getHead().getNext();
						 Nodes cellTail = truck.getMainNodes().getTail();
						 
						 while(cell != cellTail)
						 {
							 if(nodeHeadings == false)
							 {
								 cell2=row2.createCell(0);
								 cell2.setCellValue("Shipment Index");
									cell2=row2.createCell(1);
									cell2.setCellValue("Demand (Negative is backhaul)");
									cell2=row2.createCell(2);
									cell2.setCellValue("XCoord");
									cell2=row2.createCell(3);
									cell2.setCellValue("YCoord");
									cell2=row2.createCell(4);
									cell2.setCellValue("Truck Type needed");
									cell2 = row2.createCell(5);
									cell2.setCellValue("Distance from last node (or depot)");
								  row2=sheet2.createRow(++rowNum);
								  nodeHeadings = true;
								 
							 }
							
							 
							 
							 cell2=row2.createCell(0);
							 cell2.setCellValue(cell.getIndex());
							 cell2=row2.createCell(1);
							 cell2.setCellValue(cell.getDemand());
							 cell2=row2.createCell(2);
							 cell2.setCellValue(cell.getShipment().getXCoord());
							 cell2=row2.createCell(3);
							 cell2.setCellValue(cell.getShipment().getYCoord());
							 cell2=row2.createCell(4);
							 cell2.setCellValue(cell.getShipment().getTruckTypeNeeded());
							 cell2 = row2.createCell(5);
							 
							 if(isFirstNode)
							 {
				
								 distanceCalc = (float) (Math.sqrt(Math.pow((cell.getShipment().getXCoord() - depot.getXCoord()), 2) + Math.pow((cell.getShipment().getYCoord() - depot.getYCoord()), 2)));
								 
								 isFirstNode = false;
							 }
							 else
							 {
								 distanceCalc = (float) (Math.sqrt(Math.pow((cell.getShipment().getXCoord() - cell.getPrev().getShipment().getXCoord()), 2)+ Math.pow((cell.getShipment().getYCoord() - cell.getPrev().getShipment().getYCoord()), 2)));
							 }
							 totalDistance += distanceCalc;
							 distancePerTruck += distanceCalc;
							 totalDemandPerTruck+= cell.getDemand();
							 totalDemand += cell.getDemand();
							 cell2.setCellValue(distanceCalc);
							 distanceCalc = 0;
							 
							 
							 
							 
							 row2=sheet2.createRow(++rowNum);
							 
							 cell = cell.getNext();
						 }
						 truck = truck.getNext();
						 row2=sheet2.createRow(++rowNum);
						 cell2=row2.createCell(6);
						 cell2.setCellValue("Total Distance of Truck Number " + truck.getPrev().getTruckNum());
						 cell2=row2.createCell(7);
						 cell2.setCellValue("Calculated distance = Computed Distance?");
						 cell2=row2.createCell(8);
						 cell2.setCellValue("Total demand of Truck Number " + truck.getPrev().getTruckNum());
						 cell2=row2.createCell(9);
						 cell2.setCellValue("Calculated Demand = Computed Demand?");
						 row2=sheet2.createRow(++rowNum);
						 cell2=row2.createCell(6);
						 cell2.setCellValue(distancePerTruck);
						 cell2=row2.createCell(7);
						 if(distancePerTruck == truck.getPrev().getAttributes().getTotalDistance())
						 {
							 cell2.setCellValue("Passed!");
						 }
						 else
						 {
							 cell2.setCellValue("Failed!" + distancePerTruck + " != " + truck.getPrev().getAttributes().getTotalDistance());
						 }
						 cell2=row2.createCell(8);
						 cell2.setCellValue(totalDemandPerTruck);
						 cell2=row2.createCell(9);
						 
						 if(totalDemandPerTruck == truck.getPrev().getAttributes().getTotalDemand())
						 {
							 cell2.setCellValue("Passed!");
						 }
						 else
						 {
							 cell2.setCellValue("Failed " + totalDemandPerTruck + " != " + truck.getPrev().getAttributes().getTotalDemand());
						 }
						 row2=sheet2.createRow(++rowNum);
						 
						 nodeHeadings = false;
						 isFirstNode = true;
						 distancePerTruck = 0;
						 totalDemandPerTruck=0;
			        }
			        depot = depot.getNext();
				 
			  }
			  depot =  getHead().getNext();
			  row2=sheet2.createRow(++rowNum);
			  row2 = sheet2.createRow(++rowNum);
			  cell2=row2.createCell(0);
			 cell2.setCellValue("Total Distance Calculated");
			 cell2=row2.createCell(1);
			 cell2.setCellValue("Total Distance Correct?");
			 cell2=row2.createCell(2);
			 cell2.setCellValue("Total Demand Calculated");
			 cell2=row2.createCell(3);
			 cell2.setCellValue("Total Demand Correct?");
			 row2 = sheet2.createRow(++rowNum);
			 cell2=row2.createCell(0);
			 cell2.setCellValue(totalDistance);
			 cell2=row2.createCell(1);
			 
			 if(totalDistance == depot.getAttributes().getTotalDistance())
			 {
				 cell2.setCellValue("Correct Distance!");
				 
				 
			 }
			 else
			 {
				 cell2.setCellValue("Incorrect Distance "+ totalDistance + " != " + depot.getAttributes().getTotalDistance() );
			 }
			 cell2=row2.createCell(2);
			 cell2.setCellValue(totalDemand);
			 cell2=row2.createCell(3);
			 
			 if(totalDemand == depot.getAttributes().getTotalDemand())
			 {
				 cell2.setCellValue("Correct Demand!");
			 }
			 else
			 {
				 cell2.setCellValue("Incorrect Demand " + totalDemand + " != " + depot.getAttributes().getTotalDemand());
			 }
			 
			for(int i=0; i < 10; i++)
			{
				sheet2.autoSizeColumn(i);
			}
		  }
		  catch(Exception e)
		  {
			  System.out.println("Error in printDepotLinkedList" +e);
			  e.printStackTrace();
		  }
	 try {
			    FileOutputStream longAnswerToFile = 
			            new FileOutputStream(new File(ZeusProblemInfo.getOutputPath() + fileString + "_longAnswers_"+heuristic+".xls"));
			    workbook2.write(longAnswerToFile);
			    longAnswerToFile.close();
			  
			     
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
	}

}
