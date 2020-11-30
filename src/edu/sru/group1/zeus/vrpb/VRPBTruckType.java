package edu.sru.group1.zeus.vrpb;

//import the parent class
import edu.sru.thangiah.zeus.core.TruckType;
/**
*
* <p>VRPBTruckType:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBTruckType
    extends TruckType
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPBTruckType() {
  }

  /**
   * Constructor
   * @param N type number
   * @param D max duration
   * @param Q max capacity
   * @param s type of customers the truck can service
   */
  public VRPBTruckType(int N, float D, float Q, String s) {
    setTruckNo(N);
    setServiceType(s);

    if (D == 0) {
      setMaxDuration(Integer.MAX_VALUE);
    }
    else {
    	setMaxDuration(D);
    }

    if (Q == 0) {
      setMaxCapacity(Integer.MAX_VALUE);
    }
    else {
      setMaxCapacity(Q);
    }

    setFixedCost(getMaxCapacity());
    setVariableCost((double) getMaxCapacity() / 1000);
  }

}
