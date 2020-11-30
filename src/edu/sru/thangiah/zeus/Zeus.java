package edu.sru.thangiah.zeus;

import  edu.sru.thangiah.zeus.vrp.VRPRoot;
import edu.sru.group1.zeus.vrpb.VRPBRoot;
import edu.sru.group1.zeus.vrpbtw.VRPBTWRoot;


import java.util.Scanner;

/**
 * Calls the main functions of Zeus.
 * Title: Zeus
 * Description: This class calls the main root method for the problem to be solved
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class Zeus {
  /**
   * Main function for Zeus
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
    
	  
	
	//Solve the VRP Problem
		
		// VRPRoot theRoot = new VRPRoot();
		  
	//Solve the VRPB Problem
		
	//VRPBRoot theVRPBRoot = new VRPBRoot();
	VRPBTWRoot theVRPBTWRoot = new VRPBTWRoot();
	 
	  
	System.gc();

  }
}
