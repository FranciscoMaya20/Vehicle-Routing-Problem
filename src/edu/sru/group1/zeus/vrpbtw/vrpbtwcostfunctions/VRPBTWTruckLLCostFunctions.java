package edu.sru.group1.zeus.vrpbtw.vrpbtwcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.group1.zeus.vrpbtw.*;

public class VRPBTWTruckLLCostFunctions  extends VRPBTWAbstractCostFunctions implements java.io.Serializable
{
	public double getTotalCost(Object o) {
		VRPBTWTruckLinkedList tLL = (VRPBTWTruckLinkedList) o;
		setTotalCost(o);

		return tLL.getAttributes().getTotalCost();
	}

	/* public double getTotalConstraintCost(Object o) {
		     VRPBTruckLinkedList tLL = (VRPBTruckLinkedList) o;
		     setTotalConstraintCost(o);

		     return tLL.getAttributes().totalConstraintCost;
		   }*/

	/*public double getTotalCrossRoadPenaltyCost(Object o) {
		    VRPBTruckLinkedList tLL = (VRPBTruckLinkedList) o;
		    setTotalCrossRoadPenaltyCost(o);

		    return tLL.getAttributes().totalCrossRoadPenaltyCost;
		     }*/

	/*public double getTotalTurnAroundPenaltyCost(Object o) {
		    VRPBTruckLinkedList tLL = (VRPBTruckLinkedList) o;
		    setTotalTurnAroundPenaltyCost(o);

		    return tLL.getAttributes().totalTurnAroundPenaltyCost;
		     }*/

	public float getTotalDemand(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		setTotalDemand(o);

		return (int) truckLL.getAttributes().getTotalDemand();
	}

	public double getTotalDistance(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		setTotalDistance(o);

		return truckLL.getAttributes().getTotalDistance();
	}

	public double getTotalTravelTime(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		setTotalTravelTime(o);

		return truckLL.getAttributes().getTotalTravelTime();
	}

	public double getMaxTravelTime(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		setMaxTravelTime(o);

		return truckLL.getAttributes().getMaxTravelTime();
	}

	public double getAvgTravelTime(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		setAvgTravelTime(o);

		return truckLL.getAttributes().getAvgTravelTime();
	}

	public void setTotalCost(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		truckLL.getAttributes().setTotalCost(0);

		Truck t = truckLL.getHead();

		while (t != truckLL.getTail()) {
			if (!t.isEmptyMainNodes()) {
				truckLL.getAttributes().setTotalCost(truckLL.getAttributes().getTotalCost() + ZeusProblemInfo.getTruckLevelCostF().
						getTotalCost(t));
			}

			t = t.getNext();
		}
	}

	/*public void setTotalConstraintCost(Object o) {
		    VRPBTruckLinkedList truckLL = (VRPBTruckLinkedList) o;
		    truckLL.getAttributes().totalConstraintCost = 0;

		    Truck t = truckLL.getHead();

		    while (t != getTail()) {
		      if (!t.isEmpty()) {
		        truckLL.getAttributes().totalConstraintCost += ProblemInfo.truckLevelCostF.
		            getTotalConstraintCost(t);
		      }

		      t = t.getNext();
		    }
		     }*/

	/*public void setTotalCrossRoadPenaltyCost(Object o) {
		    VRPBTruckLinkedList truckLL = (VRPBTruckLinkedList) o;
		    truckLL.getAttributes().totalCrossRoadPenaltyCost = 0;

		    Truck t = truckLL.getHead();

		    while (t != getTail()) {
		      if (!t.isEmpty()) {
		        truckLL.getAttributes().totalCrossRoadPenaltyCost += ProblemInfo.
		            truckLevelCostF.getTotalCrossRoadPenaltyCost(t);
		      }

		      t = t.getNext();
		    }
		     }*/

	/* public void setTotalTurnAroundPenaltyCost(Object o) {
		     VRPBTruckLinkedList truckLL = (VRPBTruckLinkedList) o;
		     truckLL.getAttributes().totalTurnAroundPenaltyCost = 0;

		     Truck t = truckLL.getHead();

		     while (t != getTail()) {
		       if (!t.isEmpty()) {
		         truckLL.getAttributes().totalTurnAroundPenaltyCost += ProblemInfo.
		             truckLevelCostF.getTotalTurnAroundPenaltyCost(t);
		       }

		       t = t.getNext();
		     }
		   }*/

	public void setTotalDemand(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		truckLL.getAttributes().setTotalDemand(0);

		Truck t = truckLL.getHead();

		while (t != truckLL.getTail()) {
			if (!t.isEmptyMainNodes()) {
				truckLL.getAttributes().setTotalDemand(truckLL.getAttributes().getTotalDemand() + ZeusProblemInfo.getTruckLevelCostF().
						getTotalDemand(t));
			}

			t = t.getNext();
		}
	}

	public void setTotalDistance(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		truckLL.getAttributes().getTotalDistance();

		Truck t = truckLL.getHead();

		while (t != truckLL.getTail()) {
			if (!t.isEmptyMainNodes()) {
				truckLL.getAttributes().setTotalDistance(truckLL.getAttributes().getTotalDistance() + ZeusProblemInfo.getTruckLevelCostF().
						getTotalDistance(t));
			}

			t = t.getNext();
		}
	}

	public void setTotalTravelTime(Object o) {
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		truckLL.getAttributes().setTotalTravelTime(0);

		Truck t = truckLL.getHead();

		while (t != truckLL.getTail()) {
			if (!t.isEmptyMainNodes()) {
				truckLL.getAttributes().setTotalTravelTime(truckLL.getAttributes().getTotalTravelTime() + ZeusProblemInfo.getTruckLevelCostF().
						getTotalTravelTime(t));
			}

			t = t.getNext();
		}
	}

	public void setMaxTravelTime(Object o) {
		double max = 0;
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		Truck t = truckLL.getHead();

		while (t != truckLL.getTail()) {
			if (!t.isEmptyMainNodes()) {
				if (ZeusProblemInfo.getTruckLevelCostF().getMaxTravelTime(t) > max) {
					max = ZeusProblemInfo.getTruckLevelCostF().getMaxTravelTime(t);
				}
			}

			t = t.getNext();
		}

		truckLL.getAttributes().setMaxTravelTime(max);
	}

	public void setAvgTravelTime(Object o) {
		double avg = 0;
		VRPBTWTruckLinkedList truckLL = (VRPBTWTruckLinkedList) o;
		Truck t = truckLL.getHead();

		if ( (truckLL.getSize() != 0) &&
				(ZeusProblemInfo.getTruckLLLevelCostF().getTotalDemand(truckLL) != 0)) {
			while (t != truckLL.getTail()) {
				if (!t.isEmptyMainNodes()) {
					avg +=
							(ZeusProblemInfo.getTruckLevelCostF().getAvgTravelTime(t) *
									ZeusProblemInfo.getTruckLevelCostF().getTotalDemand(t));
				}

				t = t.getNext();
			}

			truckLL.getAttributes().setAvgTravelTime(avg /
					ZeusProblemInfo.getTruckLLLevelCostF().getTotalDemand(truckLL));
		}
		else {
			truckLL.getAttributes().setAvgTravelTime(0);
		}
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
