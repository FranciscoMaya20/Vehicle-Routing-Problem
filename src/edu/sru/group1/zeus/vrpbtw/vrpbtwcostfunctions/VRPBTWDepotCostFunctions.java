package edu.sru.group1.zeus.vrpbtw.vrpbtwcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.group1.zeus.vrpbtw.VRPBTWDepot;

public class VRPBTWDepotCostFunctions extends VRPBTWAbstractCostFunctions implements java.io.Serializable
{
	public double getTotalCost(Object o) 
	{
		VRPBTWDepot depot = (VRPBTWDepot) o;
		setTotalCost(o);

		return depot.getAttributes().getTotalCost();
	}
	/*public double getTotalConstraintCost(Object o) {
	    VRPBDepot depot = (VRPBDepot) o;
	    setTotalConstraintCost(o);

	    return depot.getAttributes().totalConstraintCost;
	     }*/

	/*public double getTotalCrossRoadPenaltyCost(Object o) {
	    VRPBDepot depot = (VRPBDepot) o;
	    setTotalCrossRoadPenaltyCost(o);

	    return depot.getAttributes().totalCrossRoadPenaltyCost;
	     }*/

	/*public double getTotalTurnAroundPenaltyCost(Object o) {
	    VRPBDepot depot = (VRPBDepot) o;
	    setTotalTurnAroundPenaltyCost(o);

	    return depot.getAttributes().totalTurnAroundPenaltyCost;
	     }*/

	public float getTotalDemand(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		setTotalDemand(o);

		return (int) depot.getAttributes().getTotalDemand();
	}

	public double getTotalDistance(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		setTotalDistance(o);

		return depot.getAttributes().getTotalDistance();
	}

	public double getTotalTravelTime(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		setTotalTravelTime(o);

		return depot.getAttributes().getTotalTravelTime();
	}

	public double getMaxTravelTime(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		setMaxTravelTime(o);

		return depot.getAttributes().getMaxTravelTime();
	}

	public double getAvgTravelTime(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		setAvgTravelTime(o);

		return depot.getAttributes().getAvgTravelTime();
	}

	public void setTotalCost(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		depot.getAttributes().setTotalCost(ZeusProblemInfo.getTruckLLLevelCostF().getTotalCost(
				depot.getMainTrucks()));
	}

	/*public void setTotalConstraintCost(Object o) {
	    VRPBDepot depot = (VRPBDepot) o;
	    depot.getAttributes().totalConstraintCost = ZeusProblemInfo.truckLLLevelCostF.
	        getTotalConstraintCost(depot.getMainTrucks());
	     }*/

	/*public void setTotalCrossRoadPenaltyCost(Object o) {
	    VRPBDepot depot = (VRPBDepot) o;
	    depot.getAttributes().totalCrossRoadPenaltyCost = ZeusProblemInfo.truckLLLevelCostF.
	        getTotalCrossRoadPenaltyCost(depot.getMainTrucks());
	     }*/

	/* public void setTotalTurnAroundPenaltyCost(Object o) {
	     VRPBDepot depot = (VRPBDepot) o;
	   depot.getAttributes().totalTurnAroundPenaltyCost = ZeusProblemInfo.truckLLLevelCostF.
	         getTotalTurnAroundPenaltyCost(depot.getMainTrucks());
	   }*/

	public void setTotalDemand(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		depot.getAttributes().setTotalDemand((int) ZeusProblemInfo.getTruckLLLevelCostF().
				getTotalDemand(depot.getMainTrucks()));
	}

	public void setTotalDistance(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		depot.getAttributes().setTotalDistance((float) ZeusProblemInfo.getTruckLLLevelCostF().
				getTotalDistance(depot.getMainTrucks()));
	}

	public void setTotalTravelTime(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		depot.getAttributes().setTotalTravelTime( ZeusProblemInfo.getTruckLLLevelCostF().
				getTotalTravelTime(depot.getMainTrucks()));
	}

	public void setMaxTravelTime(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		depot.getAttributes().setMaxTravelTime(ZeusProblemInfo.getTruckLLLevelCostF().
				getMaxTravelTime(depot.getMainTrucks()));
	}

	public void setAvgTravelTime(Object o) {
		VRPBTWDepot depot = (VRPBTWDepot) o;
		depot.getAttributes().setAvgTravelTime(ZeusProblemInfo.getTruckLLLevelCostF().
				getAvgTravelTime(depot.getMainTrucks()));
	}

	/** @todo Might not need CrossRoadPenaltyCost and TurnAroundPenaltyCost */
	public void calculateTotalsStats(Object o) {
		setTotalDemand(o);
		setTotalDistance(o);
		setTotalTravelTime(o);
		setMaxTravelTime(o);
		setAvgTravelTime(o);
		//setTotalCrossRoadPenaltyCost(o);
		// setTotalTurnAroundPenaltyCost(o);
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
