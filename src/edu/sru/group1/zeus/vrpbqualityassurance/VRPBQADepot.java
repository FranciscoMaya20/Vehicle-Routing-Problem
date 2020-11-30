package edu.sru.group1.zeus.vrpbqualityassurance;
import edu.sru.thangiah.zeus.qualityassurance.*;

public class VRPBQADepot extends QADepot  implements java.io.Serializable, java.lang.Cloneable 
{
	public VRPBQADepot() {
	  }

	  public boolean checkDistanceConstraint(VRPBQADepot depot) {
	    boolean status = true;
	    for (int i = 0; i < getTrucks().size(); i++) {
	      VRPBQATruck truck = (VRPBQATruck) getTrucks().elementAt(i);
	      truck.checkDistanceConstraint(truck);
	    }
	    return status;
	  }

	  public boolean checkCapacityConstraint() {
	    boolean status = true;
	    for (int i = 0; i < getTrucks().size(); i++) {
	      VRPBQATruck truck = (VRPBQATruck) getTrucks().elementAt(i);
	      truck.checkCapacityConstraint(truck);
	    }
	    return status;
	  }
}
