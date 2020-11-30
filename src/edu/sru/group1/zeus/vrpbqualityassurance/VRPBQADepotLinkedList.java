package edu.sru.group1.zeus.vrpbqualityassurance;
import edu.sru.thangiah.zeus.qualityassurance.*;


public class VRPBQADepotLinkedList extends QADepotLinkedList implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBQADepotLinkedList() 
		{
	  }

	  public boolean checkDistanceConstraint() {
	    boolean status = true;
	    for (int i = 0; i < getDepots().size(); i++) {
	      VRPBQADepot depot = (VRPBQADepot) getDepots().elementAt(i);
	      status = status && depot.checkDistanceConstraint(depot);
	    }
	    return status;
	  }

	  public boolean checkCapacityConstraint() {
	   boolean status = true;
	   for (int i = 0; i < getDepots().size(); i++) {
	     VRPBQADepot depot = (VRPBQADepot) getDepots().elementAt(i);
	     status = status && depot.checkCapacityConstraint();
	   }
	   return status;
	 }

}
