package edu.sru.group1.zeus.vrpbtw.vrpbtwcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.group1.zeus.vrpbtw.VRPBTWTruck;

public class VRPBTWTruckCostFunctions extends VRPBTWAbstractCostFunctions implements java.io.Serializable
{
	public double getTotalCost(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		setTotalCost(o);

		return truck.getAttributes().getTotalCost();
	}

	/* public double getTotalConstraintCost(Object o) {
	     VRPBTruck truck = (VRPBTruck) o;
	     setTotalConstraintCost(o);

	     return truck.getAttributes().totalConstraintCost;
	   }*/

	/*public double getTotalCrossRoadPenaltyCost(Object o) {
	    VRPBTruck truck = (VRPBTruck) o;
	    setTotalCrossRoadPenaltyCost(o);

	    return truck.getAttributes().totalCrossRoadPenaltyCost;
	     }*/

	/*public double getTotalTurnAroundPenaltyCost(Object o) {
	    VRPBTruck truck = (VRPBTruck) o;
	    setTotalTurnAroundPenaltyCost(o);

	    return truck.getAttributes().totalTurnAroundPenaltyCost;
	     }*/

	public float getTotalDemand(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		setTotalDemand(o);

		return (int) truck.getAttributes().getTotalDemand();
	}

	public double getTotalDistance(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		setTotalDistance(o);

		return truck.getAttributes().getTotalDistance();
	}

	public double getTotalTravelTime(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		setTotalTravelTime(o);

		return truck.getAttributes().getTotalTravelTime();
	}

	public double getMaxTravelTime(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		setMaxTravelTime(o);

		return truck.getAttributes().getMaxTravelTime();
	}

	public double getAvgTravelTime(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		setAvgTravelTime(o);

		return truck.getAttributes().getAvgTravelTime();
	}

	public void setTotalCost(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		truck.getAttributes().setTotalCost(ZeusProblemInfo.getNodesLLLevelCostF().getTotalCost(
				truck.getMainNodes()));
	}

	/*public void setTotalConstraintCost(Object o) {
	    VRPBTruck truck = (VRPBTruck) o;
	    truck.getAttributes().totalConstraintCost = ProblemInfo.nodesLLLevelCostF.
	        getTotalConstraintCost(truck.getMainNodes());
	     }*/

	/*public void setTotalCrossRoadPenaltyCost(Object o) {
	    VRPBTruck truck = (VRPBTruck) o;
	    truck.getAttributes().totalCrossRoadPenaltyCost = ProblemInfo.nodesLLLevelCostF.
	        getTotalCrossRoadPenaltyCost(truck.getMainNodes());
	     }*/

	/*public void setTotalTurnAroundPenaltyCost(Object o) {
	    VRPBTruck truck = (VRPBTruck) o;
	   truck.getAttributes().totalTurnAroundPenaltyCost = ProblemInfo.nodesLLLevelCostF.
	        getTotalTurnAroundPenaltyCost(truck.getMainNodes());
	     }*/

	public void setTotalDemand(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		truck.getAttributes().setTotalDemand(ZeusProblemInfo.getNodesLLLevelCostF().getTotalDemand(
				truck.getMainNodes()));
	}

	public void setTotalDistance(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		truck.getAttributes().setTotalDistance(ZeusProblemInfo.getNodesLLLevelCostF().
				getTotalDistance(truck.getMainNodes()));
	}

	public void setTotalTravelTime(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		truck.getAttributes().setTotalTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
				getTotalTravelTime(truck.getMainNodes()));
	}

	public void setMaxTravelTime(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		truck.getAttributes().setMaxTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
				getMaxTravelTime(truck.getMainNodes()));
	}

	public void setAvgTravelTime(Object o) {
		VRPBTWTruck truck = (VRPBTWTruck) o;
		truck.getAttributes().setAvgTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
				getAvgTravelTime(truck.getMainNodes()));
	}

	/** @todo Might not need CrossRoadPenaltyCost and TurnAroundPenaltyCost */
	public void calculateTotalsStats(Object o) {
		setTotalDemand(o);
		setTotalDistance(o);
		setTotalTravelTime(o);
		setMaxTravelTime(o);
		setAvgTravelTime(o);
		//setTotalCrossRoadPenaltyCost(o);
		//setTotalTurnAroundPenaltyCost(o);
		setTotalCost(o);
		//setTotalConstraintCost(o);
	}

	@Override
	public int getTotalDays(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalStops(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTotalDays(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTotalStops(Object arg0) {
		// TODO Auto-generated method stub
		
	}

}
