package edu.sru.thangiah.zeus.vrp;

import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.thangiah.zeus.core.Attributes;
//import the parent class
import edu.sru.thangiah.zeus.core.Truck;

public class VRPTruck
    extends Truck implements java.io.Serializable, java.lang.Cloneable {
  public VRPTruck() {
	
	  //Assign the nodes linkes list
	 setMainNodes(new VRPNodesLinkedList());
	  
    //Assign the attributes
    setAttributes(new VRPAttributes());
  }

  /**
   * Constructor
   * @param tt truck type
   * @param depotX depot's x coordinate
   * @param depotY depot's y coordinate
   */
  public VRPTruck(VRPTruckType tt, double depX, double depY) {
    //super(tt, depX, depY);
    setAttributes(new VRPAttributes());
    setDepotX(depX);
    setDepotY(depY);
    setTruckNum(ZeusProblemInfo.getNumTrucks()+1);
    setTruckType(tt);

    setMainNodes(new VRPNodesLinkedList(tt, depX, depY, getTruckNum()));

  }

  /**
   * Returns the visit nodes linked list (route)
   * @return route
   */
  public VRPNodesLinkedList getVRPMainNodes() {
    return (VRPNodesLinkedList) getMainNodes();
  }

  /**
   * Returns the next depot in the linked list
   * @return next depot
   */
  public VRPTruck getVRPNext() {
    return (VRPTruck) getNext();
  }

  /**
   * Creates a clone of the current trucks. Does not create the next and prev
   * links, that is the responsibility of the truck linked list clone() function
   * @return Object truck clone
   */
  public Object clone() {
    VRPTruck clonedTruck = new VRPTruck();

    clonedTruck.setAttributes((VRPAttributes)this.getAttributes().clone());
    clonedTruck.setDepotX(this.getDepotX());
    clonedTruck.setDepotY(this.getDepotY());
    clonedTruck.setMainNodes((VRPNodesLinkedList)this.getMainNodes().clone());
    clonedTruck.setTruckNum(this.getTruckNum());
    clonedTruck.setTruckType((VRPTruckType)this.getTruckType().clone());

    return clonedTruck;
  }

}
