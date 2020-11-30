/*
 

  Based off Dr. Thangiah template of Zeus

This is the class that creates the depot for the VPRB. 

 */


package edu.sru.group1.zeus.vrpbtw;

import edu.sru.thangiah.zeus.core.Depot;

/**
*
* <p>VRPBDepot:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBTWDepot extends Depot implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBTWDepot()
	{
		setAttributes(new VRPBTWAttributes());
		setMainTrucks(new VRPBTWTruckLinkedList());
	}

	/*
	 * 
	 * The constructor of the VRPB DEPOT
	 * @param depotnum, xCoord, Ycoord
	 */
	public VRPBTWDepot(int x, int y)
	{
		//index number and x and y coords
		//setDepotNum(d);
		setXCoord(x);
		setYCoord(y);

		setAttributes(new VRPBTWAttributes());
		setMainTrucks(new VRPBTWTruckLinkedList());


	}

	//returns truck linked list
	public VRPBTWTruckLinkedList getMainTrucks()
	{
		return (VRPBTWTruckLinkedList)super.getMainTrucks();

	}
	public VRPBTWDepot getNext()
	{
		return (VRPBTWDepot)super.getNext();
	}

}
