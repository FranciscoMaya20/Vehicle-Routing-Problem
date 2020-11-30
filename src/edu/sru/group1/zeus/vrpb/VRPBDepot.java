/*
 

  Based off Dr. Thangiah template of Zeus

This is the class that creates the depot for the VPRB. 

 */


package edu.sru.group1.zeus.vrpb;

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
public class VRPBDepot extends Depot implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBDepot()
	{
		setAttributes(new VRPBAttributes());
		setMainTrucks(new VRPBTruckLinkedList());
	}

	/*
	 * 
	 * The constructor of the VRPB DEPOT
	 * @param depotnum, xCoord, Ycoord
	 */
	public VRPBDepot(int d, int x, int y)
	{
		//index number and x and y coords
		setDepotNum(d);
		setXCoord(x);
		setYCoord(y);

		setAttributes(new VRPBAttributes());
		setMainTrucks(new VRPBTruckLinkedList());


	}

	//returns truck linked list
	public VRPBTruckLinkedList getMainTrucks()
	{
		return (VRPBTruckLinkedList)super.getMainTrucks();

	}
	public VRPBDepot getNext()
	{
		return (VRPBDepot)super.getNext();
	}

}
