package edu.sru.group1.zeus.vrpbtw;

import edu.sru.group1.zeus.vrpbtw.vrpbtwcostfunctions.VRPBTWNodesLLCostFunctions;
import edu.sru.thangiah.zeus.core.Feasibility;
import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
/**
*
* <p>VRPBFeasibility:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Ryan Schlernitzauer, Andrew Butterfield, Adam Riddle (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBTWFeasibility extends Feasibility
implements java.io.Serializable, java.lang.Cloneable
{	
	public VRPBTWFeasibility(double maxd, float maxq, VRPBTWNodesLinkedList thisR) 
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
				if(VRPBTWNodesLLCostFunctions.getTimeFeasibility(getRoute()))
				{
					return true;
				}
				else
					return false;
			}
			else
				return false; 
		}
}
