package edu.sru.thangiah.zeus.vrp.vrpcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.VRPDepot;

/**
 * Cost Functions specific to the VRP Depot level.
 * <p>Title: Zeus - A Unified Object Oriented Model for VRP's</p>
 * <p>Description: cost functions specific to VRPDepot level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0

 */
public class VRPDepotCostFunctions
    extends VRPAbstractCostFunctions implements
    java.io.Serializable {

  public double getTotalCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalCost(o);

    return depot.getAttributes().getTotalCost();
  }

  /*public double getTotalConstraintCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalConstraintCost(o);

    return depot.getAttributes().totalConstraintCost;
     }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalCrossRoadPenaltyCost(o);

    return depot.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalTurnAroundPenaltyCost(o);

    return depot.getAttributes().totalTurnAroundPenaltyCost;
     }*/

  public float getTotalDemand(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalDemand(o);

    return (int) depot.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalDistance(o);

    return depot.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalTravelTime(o);

    return depot.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setMaxTravelTime(o);

    return depot.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setAvgTravelTime(o);

    return depot.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().setTotalCost(ZeusProblemInfo.getTruckLLLevelCostF().getTotalCost(
        depot.getMainTrucks()));
  }

  /*public void setTotalConstraintCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().totalConstraintCost = ZeusProblemInfo.truckLLLevelCostF.
        getTotalConstraintCost(depot.getMainTrucks());
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().totalCrossRoadPenaltyCost = ZeusProblemInfo.truckLLLevelCostF.
        getTotalCrossRoadPenaltyCost(depot.getMainTrucks());
     }*/

  /* public void setTotalTurnAroundPenaltyCost(Object o) {
     VRPDepot depot = (VRPDepot) o;
   depot.getAttributes().totalTurnAroundPenaltyCost = ZeusProblemInfo.truckLLLevelCostF.
         getTotalTurnAroundPenaltyCost(depot.getMainTrucks());
   }*/

  public void setTotalDemand(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().setTotalDemand((int) ZeusProblemInfo.getTruckLLLevelCostF().
        getTotalDemand(depot.getMainTrucks()));
  }

  public void setTotalDistance(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().setTotalDistance((float) ZeusProblemInfo.getTruckLLLevelCostF().
        getTotalDistance(depot.getMainTrucks()));
  }

  public void setTotalTravelTime(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().setTotalTravelTime( ZeusProblemInfo.getTruckLLLevelCostF().
        getTotalTravelTime(depot.getMainTrucks()));
  }

  public void setMaxTravelTime(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().setMaxTravelTime(ZeusProblemInfo.getTruckLLLevelCostF().
        getMaxTravelTime(depot.getMainTrucks()));
  }

  public void setAvgTravelTime(Object o) {
    VRPDepot depot = (VRPDepot) o;
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
