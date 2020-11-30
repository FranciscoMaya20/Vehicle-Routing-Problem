package edu.sru.group1.zeus.vrpbtw;

import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.group1.zeus.vrpbtw.VRPBTWTruckType;
import edu.sru.thangiah.zeus.core.Attributes;
import edu.sru.thangiah.zeus.core.Truck;
/**
*
* <p>VRPBTruck:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBTWTruck extends Truck implements java.io.Serializable, java.lang.Cloneable  {

	public VRPBTWTruck() {

		//Assign the nodes linkes list
		setMainNodes(new VRPBTWNodesLinkedList());

		//Assign the attributes
		setAttributes(new VRPBTWAttributes());
	}
	public VRPBTWTruck(VRPBTWTruckType tt, double depX, double depY) {
		//super(tt, depX, depY);
		setAttributes(new VRPBTWAttributes());
		setDepotX(depX);
		setDepotY(depY);
		setTruckNum(ZeusProblemInfo.getNumTrucks());
		setTruckType(tt);

		setMainNodes(new VRPBTWNodesLinkedList(tt, depX, depY, getTruckNum()));

	}
	public VRPBTWNodesLinkedList getVRPBTWMainNodes() {
		return (VRPBTWNodesLinkedList) getMainNodes();
	}
	public VRPBTWTruck getVRPBTWNext() {
		return (VRPBTWTruck) getNext();
	}
	public Object clone() {
		VRPBTWTruck clonedTruck = new VRPBTWTruck();

		clonedTruck.setAttributes((VRPBTWAttributes)this.getAttributes().clone());
		clonedTruck.setDepotX(this.getDepotX());
		clonedTruck.setDepotY(this.getDepotY());
		clonedTruck.setMainNodes((VRPBTWNodesLinkedList)this.getMainNodes().clone());
		clonedTruck.setTruckNum(this.getTruckNum());
		clonedTruck.setTruckType((VRPBTWTruckType)this.getTruckType().clone());

		return clonedTruck;
	}
}
