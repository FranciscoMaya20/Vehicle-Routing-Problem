package edu.sru.group1.zeus.vrpbtw.vrpbtwqualityassurance;
import edu.sru.thangiah.zeus.qualityassurance.*;


public class VRPBTWQADepotLinkedList extends QADepotLinkedList implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBTWQADepotLinkedList() 
		{
	  }

	  public boolean checkDistanceConstraint() {
	    boolean status = true;
	    for (int i = 0; i < getDepots().size(); i++) {
	      VRPBTWQADepot depot = (VRPBTWQADepot) getDepots().elementAt(i);
	      status = status && depot.checkDistanceConstraint(depot);
	    }
	    return status;
	  }

	  public boolean checkCapacityConstraint() {
	   boolean status = true;
	   for (int i = 0; i < getDepots().size(); i++) {
	     VRPBTWQADepot depot = (VRPBTWQADepot) getDepots().elementAt(i);
	     status = status && depot.checkCapacityConstraint();
	   }
	   return status;
	 }

}
