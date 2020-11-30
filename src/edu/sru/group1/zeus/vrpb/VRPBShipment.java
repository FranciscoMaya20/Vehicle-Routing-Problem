/*
 

  Based off Dr. Thangiah template of Zeus

  This is where the VPRB shipment gets created.  It gets created as you are reading in the data

 */

package edu.sru.group1.zeus.vrpb;

import edu.sru.thangiah.zeus.core.Shipment;
/**
*
* <p>VRPBShipment:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBShipment extends Shipment implements java.io.Serializable, java.lang.Cloneable
{
	private double extraVariable;
	public VRPBShipment() 
	{
	}
	public VRPBShipment(int i, double x, double y, float q, float d, String t,
			String p)
	{
		//super(i, x, y, q, d, t, p);
		setIndex(i);
		setXCoord(x);
		setYCoord(y);
		setDemand(q);
		//serviceTime = d;
		setTruckTypeNeeded(t);
		//pickUpPointName = p;

	}
	public VRPBShipment(int i, float x, float y, int d, int q, int e, int comb,
			String t,
			int[] vComb, int[][] cuComb) 
	{
		//super(ind, x, y, d, q, e, comb, t, vComb, cuComb);
		setIndex(i);
		setXCoord(x);
		setYCoord(y);
		setDemand(q);
		//serviceTime = d;

		// frequency = e;
		// noComb = comb;
		setTruckTypeNeeded(t);
		//visitComb = vComb;
		//currentComb = cuComb;
		setIsAssigned(false);
		setIsSelected(false);
		setNext(null);

		//the combinations to be created should not exceed the maximum allowable
		//combination
		/*for (int i = 0; i < noComb; i++) {
			visitComb[i] = vComb[i];
			}*/

		extraVariable = Math.random();
	}
	public VRPBShipment(int i, int x, int y, int d, int q, int e, int comb,
			String t,
			int[] vComb, int[][] cuComb) 
	{
		//super(ind, x, y, d, q, e, comb, t, vComb, cuComb);
		//serviceTime = d;
		//frequency = e;
		//noComb = comb;
		//visitComb = vComb;
		//currentComb = cuComb;

		setIndex(i);
		setXCoord(x);
		setYCoord(y);
		setDemand(q);
		//serviceTime = d;

		// frequency = e;
		// noComb = comb;
		setTruckTypeNeeded(t);
		//visitComb = vComb;
		//currentComb = cuComb;
		setIsAssigned(false);

		setNext(null);

		//the combinations to be created should not exceed the maximum allowable
		//combination
		/*for (int i = 0; i < noComb; i++) {
	      visitComb[i] = vComb[i];
	    }*/

		extraVariable = Math.random();
	}

	//this is the VRPB shipment object that is created for VRPB
	//@param customer type, x coord, ycoord, capacity, and shipment index
	public VRPBShipment(String custType ,int xCoord ,int yCoord ,int capacity,int shipmentIndex)
	{
		//System.out.println("VRPBShipment: " + custType);
		setCustomerType(custType);
		setXCoord(xCoord);
		setYCoord(yCoord);

		//if it is a regular shipment dropOffVolume is set
		if(custType.equals("1"))
		{
			//regular shipment
			setDemand(capacity);
			setDropOffWeight(capacity);
			setIsDropOff(true);
			setIsPickup(false);
		}
		//if it is a backhaul, pickUpVolume is set 
		else if (custType.equals("2"))
		{
			//backhaul shipment
			setDemand(-capacity);
			setPickupWeight(capacity);
			setIsDropOff(false);
			setIsPickup(true);
		}
		
		setTruckTypeNeeded("1");
		setIndex(shipmentIndex);
	}
	public double getExtraVariable() {
		//get the next shipment from the shipment linked list
		//but return it as a VRPShipment
		return extraVariable;
	}
}
