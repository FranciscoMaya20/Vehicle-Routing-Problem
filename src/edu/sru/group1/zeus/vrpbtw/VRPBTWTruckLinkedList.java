package edu.sru.group1.zeus.vrpbtw;

import edu.sru.thangiah.zeus.core.TruckLinkedList;
import edu.sru.thangiah.zeus.core.Settings;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
/**
*
* <p>VRPBTruckLinkedList:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBTWTruckLinkedList extends TruckLinkedList implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBTWTruckLinkedList()
	{
		//Housekeeping for the linked list
		setHead(new VRPBTWTruck()); //header node for head
		setTail(new VRPBTWTruck()); //tail node for tail
		linkHeadTail();			  //point head and tail to each other

		//Assign the attributes	
		setAttributes(new VRPBTWAttributes());
	}
	public VRPBTWTruck getHead() {
		return (VRPBTWTruck) super.getHead();
	}
	public VRPBTWTruck getTail() {
		return (VRPBTWTruck) super.getTail();
	}
	public boolean insertShipment(VRPBTWShipment theShipment) {
		boolean status = false;
		//int loopCount=0;
		
		
		if (theShipment.getCustomerType().equals("1"))
		{
			//System.out.println("Success!1: " + theShipment.getCustomerType());
			VRPBTW.backhaulCount += theShipment.getPickupWeight();
		}
		else if(theShipment.getCustomerType().equals("0"))
		{
			//System.out.println("Success!2: " + theShipment.getCustomerType());
			VRPBTW.shipmentCount+= theShipment.getDropOffWeight();
		}
		else
		{
			//System.out.println("Problem: " + theShipment.getCustomerType() + " " + (theShipment.getCustomerType()).getClass());
			System.out.println("PROBLEM IN INSERT SHIPMENT counting");
		}
		VRPBTWTruck truck = (VRPBTWTruck)this.getHead().getNext();
		
		//loopCount = 1;
		while (truck != this.getTail()) { 
			if (truck.compatableWith(theShipment)) {
				status = truck.getVRPBTWMainNodes().insertShipment(theShipment);

				// break out of the loop if a good truck is found.
				if (status) {
					break;
				}
			}

			truck = (VRPBTWTruck) truck.getNext();
			//System.out.println("Loop count in insert "+loopCount++);
		}

		//can we create new trucks?
		if ( (status == false) && (Settings.lockTrucks == false)) {
			/** @todo  Is this really needed */
			//create a pointer to the last truck for reference
			VRPBTWTruck last = (VRPBTWTruck)this.getTail().getPrev();
			VRPBTWTruck first = (VRPBTWTruck)this.getHead();

			//loop to find the correct truck type for this customer
			for (int i = 0; i < ZeusProblemInfo.getTruckTypesSize(); i++) {
				VRPBTWTruckType type = (VRPBTWTruckType) ZeusProblemInfo.getTruckTypesAt(i);

				if (type.getServiceType().equals(theShipment.getTruckTypeNeeded())) {
					//create a new truck with the truck number of the last + 1, the matching truck type
					//and use the first customer in the last truck (the depot) to get the depot x,y
					//If it is set to null, it w
					//VRPTruck newTruck = null;
					/*VRPTruck newTruck    = new VRPTruck(type,
		                                           last.getMainNodes().getHead().getNext().
		                                           getShipment()
		                                           .getXCoord(),
		                                           last.getMainNodes().getHead().getNext().
		                                           getShipment()
		                                           .getYCoord());*/



					VRPBTWTruck newTruck    = new VRPBTWTruck(type,
							first.getNext().getDepotX(),
							first.getNext().getDepotY());


					//attempt to put this shipment into the new truck
					status = newTruck.getVRPBTWMainNodes().insertShipment(theShipment);

					if (status == true) {
						//the customer was inserted to the new truck, so insert the new truck to the linked list
						System.out.println("** Inserting new Truck");
						System.out.println("Depot x and y is:"+ first.getNext().getDepotX()+" "+first.getNext().getDepotY());
						this.insertTruckLast(newTruck);

						return status;
					}
					else {
						//customer could not be inserted into the new truck so return false
						//and dont insert the new truck into the linked list (garbage collector
						//will delete it)
						return status;
					}
				}
			}
		}

		return status;
	}

	/**
	 * Returns a clone of this object
	 * @return Object clone
	 */
	public Object clone() {
		VRPBTWTruckLinkedList clonedTruckLinkedList = new VRPBTWTruckLinkedList();

		clonedTruckLinkedList.setAttributes((VRPBTWAttributes)this.getAttributes().clone());
		clonedTruckLinkedList.setHead((VRPBTWTruck)this.getHead().clone());

		if (this.getHead() != this.getTail()) {
			VRPBTWTruck currentTruck = (VRPBTWTruck) clonedTruckLinkedList.getHead();
			VRPBTWTruck nextTruck = (VRPBTWTruck)this.getHead().getNext();

			while (nextTruck != null) {
				currentTruck.setNext( (VRPBTWTruck) nextTruck.clone()); //create the next depot
				currentTruck.getNext().setPrev(currentTruck); //set the next depot's prev
				currentTruck = (VRPBTWTruck) currentTruck.getNext();
				nextTruck = (VRPBTWTruck) nextTruck.getNext();

				//once next is null, we have found the tail of the list
				if (nextTruck == null) {
					clonedTruckLinkedList.setTail(currentTruck);
					currentTruck.setNext(null);
				}

			}
		}
		else { //only 1 depot
			clonedTruckLinkedList.setTail(clonedTruckLinkedList.getHead());
		}
		return clonedTruckLinkedList;
	}

}
