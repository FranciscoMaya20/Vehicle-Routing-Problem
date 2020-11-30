package edu.sru.group1.zeus.vrpbtw.vrpbtwcostfunctions;

import edu.sru.thangiah.zeus.core.*;

import edu.sru.group1.zeus.vrpbtw.VRPBTWDepotLinkedList;

public class VRPBTWDepotLLCostFunctions
extends VRPBTWAbstractCostFunctions implements
java.io.Serializable {

	public double getTotalCost(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		setTotalCost(o);

		return depotLL.getAttributes().getTotalCost();
	}

	/* public double getTotalConstraintCost(Object o) {
 VRPBDepotLinkedList depotLL = (VRPBDepotLinkedList) o;
 setTotalConstraintCost(o);

 return depotLL.getAttributes().totalConstraintCost;
}*/

	/*public double getTotalCrossRoadPenaltyCost(Object o) {
VRPBDepotLinkedList depotLL = (VRPBDepotLinkedList) o;
setTotalCrossRoadPenaltyCost(o);

return depotLL.getAttributes().totalCrossRoadPenaltyCost;
 }*/

	/*public double getTotalTurnAroundPenaltyCost(Object o) {
VRPBDepotLinkedList depotLL = (VRPBDepotLinkedList) o;
setTotalTurnAroundPenaltyCost(o);

return depotLL.getAttributes().totalTurnAroundPenaltyCost;
 }*/

	public float getTotalDemand(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		setTotalDemand(o);

		return (int) depotLL.getAttributes().getTotalDemand();
	}

	public double getTotalDistance(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		setTotalDistance(o);

		return depotLL.getAttributes().getTotalDistance();
	}

	public double getTotalTravelTime(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		setTotalTravelTime(o);

		return depotLL.getAttributes().getTotalTravelTime();
	}

	public double getMaxTravelTime(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		setMaxTravelTime(o);

		return depotLL.getAttributes().getMaxTravelTime();
	}

	public double getAvgTravelTime(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		setAvgTravelTime(o);

		return depotLL.getAttributes().getAvgTravelTime();
	}

	public void setTotalCost(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		double cost = 0;

		Depot d = depotLL.getHead();

		while (d != depotLL.getTail()) {
			cost += ZeusProblemInfo.getDepotLevelCostF().getTotalCost(d);
			d = d.getNext();
		}

		depotLL.getAttributes().setTotalCost(cost);
	}

	/*public void setTotalConstraintCost(Object o) {
VRPBDepotLinkedList depotLL = (VRPBDepotLinkedList) o;
double cost = 0;

Depot d = depotLL.getHead();

while (d != depotLL.getTail()) {
  cost += ProblemInfo.depotLevelCostF.getTotalConstraintCost(d);
  d = d.getNext();
}

depotLL.getAttributes().totalConstraintCost = cost;
 }*/

	/*public void setTotalCrossRoadPenaltyCost(Object o) {
VRPBDepotLinkedList depotLL = (VRPBDepotLinkedList) o;
depotLL.getAttributes().totalCrossRoadPenaltyCost = 0;

Depot d = depotLL.getHead();

while (d != depotLL.getTail()) {
  depotLL.getAttributes().totalCrossRoadPenaltyCost += ProblemInfo.
      depotLevelCostF.getTotalCrossRoadPenaltyCost(d);
  d = d.getNext();
}
 }*/

	/*public void setTotalTurnAroundPenaltyCost(Object o) {
VRPBDepotLinkedList depotLL = (VRPBDepotLinkedList) o;
depotLL.getAttributes().totalTurnAroundPenaltyCost = 0;

Depot d = depotLL.getHead();

while (d != depotLL.getTail()) {
  depotLL.getAttributes().totalTurnAroundPenaltyCost += ProblemInfo.
      depotLevelCostF.getTotalTurnAroundPenaltyCost(d);
  d = d.getNext();
}
 }*/

	public void setTotalDemand(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		depotLL.getAttributes().setTotalDemand(0);

		Depot d = depotLL.getHead();

		while (d != depotLL.getTail()) {
			depotLL.getAttributes().setTotalDemand(depotLL.getAttributes().getTotalDemand() + (float) ZeusProblemInfo.getDepotLevelCostF().
					getTotalDemand(d));
			d = d.getNext();
		}
	}

	public void setTotalDistance(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		depotLL.getAttributes().setTotalDistance(0);

		Depot d = depotLL.getHead();

		while (d != depotLL.getTail()) {
			depotLL.getAttributes().setTotalDistance(depotLL.getAttributes().getTotalDistance()  + (float) ZeusProblemInfo.getDepotLevelCostF().
					getTotalDistance(d));
			d = d.getNext();
		}
	}

	public void setTotalTravelTime(Object o) {
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		depotLL.getAttributes().setTotalTravelTime(0);

		Depot d = depotLL.getHead();

		while (d != depotLL.getTail()) {
			depotLL.getAttributes().setTotalTravelTime(depotLL.getAttributes().getTotalTravelTime() + ZeusProblemInfo.getDepotLevelCostF().
					getTotalTravelTime(d));
			d = d.getNext();
		}
	}

	public void setMaxTravelTime(Object o) {
		double max = 0;
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		Depot d = depotLL.getHead();

		while (d != depotLL.getTail()) {
			if (ZeusProblemInfo.getDepotLevelCostF().getMaxTravelTime(d) > max) {
				max = ZeusProblemInfo.getDepotLevelCostF().getMaxTravelTime(d);
			}

			d = d.getNext();
		}

		depotLL.getAttributes().setMaxTravelTime(max);
	}

	public void setAvgTravelTime(Object o) {
		double avg = 0;
		VRPBTWDepotLinkedList depotLL = (VRPBTWDepotLinkedList) o;
		Depot d = depotLL.getHead();

		if (ZeusProblemInfo.getDepotLLLevelCostF().getTotalDemand(depotLL) != 0) {
			while (d != depotLL.getTail()) {
				avg +=
						(ZeusProblemInfo.getDepotLevelCostF().getAvgTravelTime(d) *
								ZeusProblemInfo.getDepotLevelCostF().getTotalDemand(d));
				d = d.getNext();
			}

			depotLL.getAttributes().setAvgTravelTime(avg /
					ZeusProblemInfo.getDepotLLLevelCostF().getTotalDemand(depotLL));
		}
		else {
			depotLL.getAttributes().setAvgTravelTime(0);
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

