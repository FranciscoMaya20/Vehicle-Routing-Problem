package edu.sru.thangiah.zeus.vrp.vrpcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.VRPTruck;

/**
 * Cost functions specific to VRPTruck level
 * <p>Title: Zeus - A Unified Object Oriented Model for VRP's</p>
 * <p>Description: cost functions specific to VRP Truck level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class VRPTruckCostFunctions
    extends VRPAbstractCostFunctions implements
    java.io.Serializable  {

  public double getTotalCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setTotalCost(o);

    return truck.getAttributes().getTotalCost();
  }

  /* public double getTotalConstraintCost(Object o) {
     VRPTruck truck = (VRPTruck) o;
     setTotalConstraintCost(o);

     return truck.getAttributes().totalConstraintCost;
   }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setTotalCrossRoadPenaltyCost(o);

    return truck.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setTotalTurnAroundPenaltyCost(o);

    return truck.getAttributes().totalTurnAroundPenaltyCost;
     }*/

  public float getTotalDemand(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setTotalDemand(o);

    return (int) truck.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setTotalDistance(o);

    return truck.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setTotalTravelTime(o);

    return truck.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setMaxTravelTime(o);

    return truck.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    VRPTruck truck = (VRPTruck) o;
    setAvgTravelTime(o);

    return truck.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().setTotalCost(ZeusProblemInfo.getNodesLLLevelCostF().getTotalCost(
        truck.getMainNodes()));
  }

  /*public void setTotalConstraintCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().totalConstraintCost = ZeusProblemInfo.nodesLLLevelCostF.
        getTotalConstraintCost(truck.getMainNodes());
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().totalCrossRoadPenaltyCost = ZeusProblemInfo.nodesLLLevelCostF.
        getTotalCrossRoadPenaltyCost(truck.getMainNodes());
     }*/

  /*public void setTotalTurnAroundPenaltyCost(Object o) {
    VRPTruck truck = (VRPTruck) o;
   truck.getAttributes().totalTurnAroundPenaltyCost = ZeusProblemInfo.nodesLLLevelCostF.
        getTotalTurnAroundPenaltyCost(truck.getMainNodes());
     }*/

  public void setTotalDemand(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().setTotalDemand(ZeusProblemInfo.getNodesLLLevelCostF().getTotalDemand(
        truck.getMainNodes()));
  }

  public void setTotalDistance(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().setTotalDistance(ZeusProblemInfo.getNodesLLLevelCostF().
        getTotalDistance(truck.getMainNodes()));
  }

  public void setTotalTravelTime(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().setTotalTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
        getTotalTravelTime(truck.getMainNodes()));
  }

  public void setMaxTravelTime(Object o) {
    VRPTruck truck = (VRPTruck) o;
    truck.getAttributes().setMaxTravelTime(ZeusProblemInfo.getNodesLLLevelCostF().
        getMaxTravelTime(truck.getMainNodes()));
  }

  public void setAvgTravelTime(Object o) {
    VRPTruck truck = (VRPTruck) o;
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
