/*
 
  
  Based off Dr. Thangiah template of Zeus
    
  VRPBRoot is called from Zeus main, creating an instance of VRPB, and sending each file to VRPB.java as it iterates through

 Group 5
 Nassir Weaver
 Francisco Maya
 Tyler Kimmel
 */


package edu.sru.group1.zeus.vrpb;

import java.io.File;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.group1.zeus.vrpb.vrpbcostfunctions.*;
/**
*
* <p>VRPBRoot:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBRoot 
{

	public VRPBRoot()
	{
		ZeusProblemInfo.setNodesLLLevelCostF(new VRPBNodesLLCostFunctions());
		 ZeusProblemInfo.setTruckLevelCostF(new VRPBTruckCostFunctions());
		ZeusProblemInfo.setTruckLLLevelCostF(new VRPBTruckLLCostFunctions());
		ZeusProblemInfo.setDepotLevelCostF(new VRPBDepotCostFunctions());
		 ZeusProblemInfo.setDepotLLLevelCostF(new VRPBDepotLLCostFunctions());

		ZeusProblemInfo.setTempFileLocation("\\temp");
		ZeusProblemInfo.setInputPath("\\data\\vrpb\\problems\\VRPBH Data Excel\\");
		ZeusProblemInfo.setOutputPath("\\data\\vrpb\\results\\");

		String fileName = "";
		
		String[] heuristicsArray = {"Polar","PolarClosest","EuclidianClosest"};//array to hold the heuristics to run all of the heuristics
		

		/*
		 	This code is run every time VRPB root is run,  deleting all of the files that are in the data\vrpb\results folder 
		  	so that they can be recreated.  
		
		 */
		File directoryResults = new File(ZeusProblemInfo.getOutputPath());
		File[]directoryListingResults = directoryResults.listFiles();

		if(directoryResults.isDirectory())
		{
			if(directoryListingResults!=null)
			{
				for(File child:directoryListingResults)
				{
					/*if(child.toString().contains("solutionCheck"))
					{
						System.out.println("here");
					}
					else
					{*/
						child.delete();
					//}
					
				}
			}
			else
			{
				System.out.println("ERROR OUTPUT: No Files Found In Directory");//no files in directory
			}

		}
		else
		{
			System.out.println("ERROR OUTPUT: Files Don't Exist");// files do not exist
		}


	/*
	  
	  This code runs through an iteration of the input files.
	  This is used so that the program can run through all of the files at once instead of asking the user
	  which file they would like to run.  
	 */

		File directory = new File(ZeusProblemInfo.getInputPath());
		File[]directoryListing = directory.listFiles();
		if(directory.isDirectory())
		{
			if(directoryListing != null)
			{
				for(File child:directoryListing)
				{
					
					String completeName = child.toString();

					String [] splitName = completeName.split("\\\\");
					int sizeSplitName = splitName.length;
					fileName = splitName[sizeSplitName-1];
					
					if(fileName.equals("ReadMe.txt") || fileName.equals("checkAnswers.xls"))
					{
						//do nothing
					}
					else
					{
						
						for(String heuristic:heuristicsArray)
							{
								new VRPB(fileName,heuristic);
							}
							
						
						
					}

					

				}
			}
			else
			{
				System.out.println("ERROR INPUT: No Files Found In Directory");//null directory
			}
		}
		else
		{
			System.out.println("ERROR INPUT: Files Don't Exist");//not directory
		}
	}	





}




