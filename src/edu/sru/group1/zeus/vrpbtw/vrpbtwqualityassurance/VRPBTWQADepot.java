package edu.sru.group1.zeus.vrpbtw.vrpbtwqualityassurance;
import edu.sru.thangiah.zeus.qualityassurance.*;

public class VRPBTWQADepot extends QADepot  implements java.io.Serializable, java.lang.Cloneable 
{
	public VRPBTWQADepot() {
	  }

	  public boolean checkDistanceConstraint(VRPBTWQADepot depot) {
	    boolean status = true;
	    for (int i = 0; i < getTrucks().size(); i++) {
	      VRPBTWQATruck truck = (VRPBTWQATruck) getTrucks().elementAt(i);
	      truck.checkDistanceConstraint(truck);
	    }
	    return status;
	  }

	  public boolean checkCapacityConstraint() {
	    boolean status = true;
	    for (int i = 0; i < getTrucks().size(); i++) {
	      VRPBTWQATruck truck = (VRPBTWQATruck) getTrucks().elementAt(i);
	      truck.checkCapacityConstraint(truck);
	    }
	    return status;
	  }
}
