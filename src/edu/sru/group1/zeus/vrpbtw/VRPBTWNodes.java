package edu.sru.group1.zeus.vrpbtw;

import edu.sru.thangiah.zeus.core.Nodes;

/**
*
* <p>VRPBNodes:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBTWNodes extends Nodes implements  java.io.Serializable, java.lang.Cloneable
{
	private VRPBTWShipment theShipment;
	
	public VRPBTWNodes()
	{

	}
	public VRPBTWNodes(VRPBTWShipment s)
	{
		
		//super(s);
		super.setShipment(s);
			
		
	}
 public VRPBTWShipment getShipment()
	{
		  return (VRPBTWShipment)super.getShipment();
	}
	public VRPBTWNodes getVRPBTWNext()
	{
		return (VRPBTWNodes) getNext();
	}
	public Object clone() 
	{
		VRPBTWNodes clonedNode = new VRPBTWNodes();

		clonedNode.theShipment = (VRPBTWShipment)this.theShipment.clone();

		return clonedNode;
	}
}
