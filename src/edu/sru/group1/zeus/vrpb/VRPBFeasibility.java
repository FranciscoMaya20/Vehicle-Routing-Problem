package edu.sru.group1.zeus.vrpb;

import edu.sru.thangiah.zeus.core.Feasibility;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
/**
*
* <p>VRPBFeasibility:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBFeasibility extends Feasibility
implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBFeasibility(double maxd, float maxq, VRPBNodesLinkedList thisR) 
	{
		super(thisR);
		setMaxDuration(maxd);
		setMaxCapacity(maxq);
		//thisRoute = thisR;
	}
	public boolean isFeasible() 
	{
		double currentDistance;
		double currentDemand;

		currentDistance = ZeusProblemInfo.getNodesLLLevelCostF().getTotalDistance(getRoute());
		currentDemand = ZeusProblemInfo.getNodesLLLevelCostF().getTotalDemand(getRoute());
		
		

		//System.out.println("Current Distance ="+ currentDistance);
		//System.out.println("Current Demand ="+ currentDemand);
			
													//issue
		
			if ((currentDistance <= getMaxDuration()) && (currentDemand <= getMaxCapacity())) //&& (currentDemand >= 0)) 
			{
				
					
					return true;	
			
			}
			else
				return false; 
		}
			
		
	
		

}
