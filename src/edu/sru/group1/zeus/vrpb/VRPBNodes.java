package edu.sru.group1.zeus.vrpb;

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
public class VRPBNodes extends Nodes implements  java.io.Serializable, java.lang.Cloneable
{
	private VRPBShipment theShipment;
	
	public VRPBNodes()
	{

	}
	public VRPBNodes(VRPBShipment s)
	{
		
		//super(s);
		super.setShipment(s);
			
		
	}
 public VRPBShipment getShipment()
	{
		  return (VRPBShipment)super.getShipment();
	}
	public VRPBNodes getVRPBNext()
	{
		return (VRPBNodes) getNext();
	}
	public Object clone() 
	{
		VRPBNodes clonedNode = new VRPBNodes();

		clonedNode.theShipment = (VRPBShipment)this.theShipment.clone();

		return clonedNode;
	}
}
