package edu.sru.group1.zeus.vrpb;

import edu.sru.thangiah.zeus.core.ZeusProblemInfo;

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
public class VRPBTruck extends Truck implements java.io.Serializable, java.lang.Cloneable  {

	public VRPBTruck() {

		//Assign the nodes linkes list
		setMainNodes(new VRPBNodesLinkedList());

		//Assign the attributes
		setAttributes(new VRPBAttributes());
	}
	public VRPBTruck(VRPBTruckType tt, double depX, double depY) {
		//super(tt, depX, depY);
		setAttributes(new VRPBAttributes());
		setDepotX(depX);
		setDepotY(depY);
		setTruckNum(ZeusProblemInfo.getNumTrucks());
		setTruckType(tt);

		setMainNodes(new VRPBNodesLinkedList(tt, depX, depY, getTruckNum()));

	}
	public VRPBNodesLinkedList getVRPBMainNodes() {
		return (VRPBNodesLinkedList) getMainNodes();
	}
	public VRPBTruck getVRPBNext() {
		return (VRPBTruck) getNext();
	}
	public Object clone() {
		VRPBTruck clonedTruck = new VRPBTruck();

		clonedTruck.setAttributes((VRPBAttributes)this.getAttributes().clone());
		clonedTruck.setDepotX(this.getDepotX());
		clonedTruck.setDepotY(this.getDepotY());
		clonedTruck.setMainNodes((VRPBNodesLinkedList)this.getMainNodes().clone());
		clonedTruck.setTruckNum(this.getTruckNum());
		clonedTruck.setTruckType((VRPBTruckType)this.getTruckType().clone());

		return clonedTruck;
	}
}
