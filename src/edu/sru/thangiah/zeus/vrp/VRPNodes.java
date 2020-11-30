package edu.sru.thangiah.zeus.vrp;

//import the parent class
import edu.sru.thangiah.zeus.core.Nodes;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPNodes
    extends Nodes
    implements java.io.Serializable, java.lang.Cloneable {
	private VRPShipment theShipment;
  public VRPNodes() {
  }

  /**
   * Constructor
   * @param s shipment conatining this cells information
   */
  public VRPNodes(VRPShipment s) 
  {
    //super(s);
   super.setShipment(s);
  }

  /**
   * Returns the next point cell in the linked list
   * @return next point cell
   */
  public VRPNodes getVRPNext() {
    return (VRPNodes) getNext();
  }
  public VRPShipment getShipment()
  {
	  return (VRPShipment)super.getShipment();
  }

  /**
   * Creates a copy of this node and returns it. It will not create the next
   * and prev links, because this would cause infinate recursion. These are
   * set in the nodes linked list clone() function.
   * @return Object node clone
   */
  public Object clone() {
    VRPNodes clonedNode = new VRPNodes();

    clonedNode.theShipment = (VRPShipment)this.theShipment.clone();

    return clonedNode;
  }

}
