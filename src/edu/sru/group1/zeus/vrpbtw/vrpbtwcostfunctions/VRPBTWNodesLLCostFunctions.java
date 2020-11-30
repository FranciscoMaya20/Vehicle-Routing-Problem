package edu.sru.group1.zeus.vrpbtw.vrpbtwcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.group1.zeus.vrpbtw.VRPBTWNodesLinkedList;
//import edu.sru.group1.zeus.vrpbtw.VRPBTWNodes;
import edu.sru.group1.zeus.vrpbtw.VRPBTWShipment;
import edu.sru.group1.zeus.vrpbtw.VRPBTWShipmentLinkedList;

public class VRPBTWNodesLLCostFunctions extends VRPBTWAbstractCostFunctions implements java.io.Serializable
{
	public double getTotalCost(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		setTotalCost(o);

		return vNodes.getAttributes().getTotalCost();
	}

	/*public double getTotalConstraintCost(Object o) {
		    VRPBNodesLinkedList vNodes = (VRPBNodesLinkedList) o;
		    setTotalConstraintCost(o);

		    return vNodes.getAttributes().totalConstraintCost;
		     }*/

	/*public double getTotalCrossRoadPenaltyCost(Object o) {
		    VRPBNodesLinkedList vNodes = (VRPBNodesLinkedList) o;
		    setTotalCrossRoadPenaltyCost(o);

		    return vNodes.getAttributes().totalCrossRoadPenaltyCost;
		     }*/

	/*public double getTotalTurnAroundPenaltyCost(Object o) {
		    VRPBNodesLinkedList vNodes = (VRPBNodesLinkedList) o;
		    setTotalTurnAroundPenaltyCost(o);

		    return vNodes.getAttributes().totalTurnAroundPenaltyCost;
		     }*/

	public float getTotalDemand(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		setTotalDemand(o);

		return (int) vNodes.getAttributes().getTotalDemand();
	}

	public double getTotalDistance(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		setTotalDistance(o);

		return vNodes.getAttributes().getTotalDistance();
	}

	public double getTotalTravelTime(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		setTotalTravelTime(o);

		return vNodes.getAttributes().getMaxTravelTime();
	}

	public double getMaxTravelTime(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		setMaxTravelTime(o);

		return vNodes.getAttributes().getMaxTravelTime();
	}

	public double getAvgTravelTime(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		setAvgTravelTime(o);

		return vNodes.getAttributes().getMaxTravelTime();
	}

	public void setTotalCost(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		double cost = 0;
		double dist = getTotalDistance(o);

		/* if (vNodes.getHead().getNext() != vNodes.getTail()) {
		        cost = vNodes.getTruckType().getFixedCost();
		        cost += (vNodes.getTruckType().getVariableCost() * dist);
		            }

		            vNodes.getAttributes().totalCost = cost;
		 */

		vNodes.getAttributes().setTotalCost(dist);
	}

	/*public void setTotalConstraintCost(Object o) {
		    VRPBNodesLinkedList vNodes = (VRPBNodesLinkedList) o;
		    double cost = 0;
		    double dist = getTotalDistance(o);

		    if (vNodes.getHead().getNext() != vNodes.getTail()) {
		      cost = vNodes.getTruckType().getFixedCost();
		      cost += (vNodes.getTruckType().getVariableCost() * dist);
		      cost += getAvgTravelTime(o);
		      cost += getMaxTravelTime(o);
		      cost += getTotalCrossRoadPenaltyCost(o);
		      cost += getTotalTurnAroundPenaltyCost(o);
		    }

		    vNodes.getAttributes().totalConstraintCost = cost;
		     }*/

	/* public void setTotalCrossRoadPenaltyCost(Object o) {
		     VRPBNodesLinkedList vNodes = (VRPBNodesLinkedList) o;
		     double crossRoadPenaltyCost = 0;

		       double actualRouteCost = getTotalCost(o);
		       double fixedPenalty = 0;
		       double variablePenalty = 0;
		       Nodes current = vNodes.getHead();
		       Nodes next = current.getNext();
		       int currentDMI = 0;
		       int nextDMI = 0;
		       int lastDMI = 0;
		       MatrixNode matrixNode;

		       //if route is of size 2 or 3, then it is impossible to have a cross road
		       //penalty cost.  so just return 0.
		       if (vNodes.getSize() < 3) {
		           vNodes.getAttributes().totalCrossRoadPenaltyCost = 0;

		           return;
		       }

		       //loop through the complete route and check to see if there are students
		       //being picked up that have to cross the road
		       while ((current != null) && (next != null)) {
		           //if getNext() point cell is a pick up point
		           if (next.getType() == ProblemInfo.PICK_UP_POINT) {
		               //get distance matrix indices for the two pick up points
		               currentDMI = current.getDistanceMatrixIndex();
		               nextDMI = next.getDistanceMatrixIndex();

		               //make sure that they are not an indentical pick up point
		               if (currentDMI != nextDMI) {
		      matrixNode = SBVRPData.distanceMatrix.getDataAt(currentDMI, nextDMI);
		                   lastDMI = currentDMI; //save this for later

		                   //check to see if we need to impose a cross road penalty cost
		                   if (matrixNode.imposeCrossRoadPenaltyCost()) {
		                       fixedPenalty = SBVRPData.pickUpPoints.findPickUpPoint(next.getPickUpPointName()).getCrossRoadCostPenalty() * actualRouteCost;

		      String busTypeNeeded = next.getShipment().getBusTypeNeeded();

		                       //get variable cross road penalty cost based on type of student
		                       //and how many of them there are
		                       if (busTypeNeeded.equals(SBVRPConstants.REGULAR)) {
		                           variablePenalty = next.getDemand() * SBVRPConstants.R_CROSS_ROAD_COST_PENALTY * actualRouteCost;
		      } else if (busTypeNeeded.equals(SBVRPConstants.WHEELCHAIR)) {
		                           variablePenalty = next.getDemand() * SBVRPConstants.W_CROSS_ROAD_COST_PENALTY * actualRouteCost;
		      } else if (busTypeNeeded.equals(SBVRPConstants.WHEELCHAIR_ELEVATOR)) {
		                           variablePenalty = next.getDemand() * SBVRPConstants.WE_CROSS_ROAD_COST_PENALTY * actualRouteCost;
		      } else if (busTypeNeeded.equals(SBVRPConstants.MONITOR)) {
		                           variablePenalty = next.getDemand() * SBVRPConstants.M_CROSS_ROAD_COST_PENALTY * actualRouteCost;
		                       }

		                       //add up total cross road penalty cost
		   crossRoadPenaltyCost = crossRoadPenaltyCost + fixedPenalty + variablePenalty;
		                   }
		               } else { //currentDMI == nextDMI
		                        //this is for cases where the route is expanded and the
		                        //point cells could be multiple students in a row from
		                        //the same pick up point.  fixed penalty was charged for
		                        //the first student in the series, so now we just have to
		                        //add the individual variable penalties of the other students
		      matrixNode = SBVRPData.distanceMatrix.getDataAt(lastDMI, nextDMI);

		                   //check to see if we need to impose a cross road penalty cost
		                   if (matrixNode.imposeCrossRoadPenaltyCost()) {
		      String busTypeNeeded = next.getShipment().getBusTypeNeeded();

		                       //get variable cross road penalty cost based on type of student
		                       //and how many of them there are
		                       if (busTypeNeeded.equals(SBVRPConstants.REGULAR)) {
		   variablePenalty = next.getDemand() * SBVRPConstants.R_CROSS_ROAD_COST_PENALTY;
		      } else if (busTypeNeeded.equals(SBVRPConstants.WHEELCHAIR)) {
		   variablePenalty = next.getDemand() * SBVRPConstants.W_CROSS_ROAD_COST_PENALTY;
		      } else if (busTypeNeeded.equals(SBVRPConstants.WHEELCHAIR_ELEVATOR)) {
		   variablePenalty = next.getDemand() * SBVRPConstants.WE_CROSS_ROAD_COST_PENALTY;
		      } else if (busTypeNeeded.equals(SBVRPConstants.MONITOR)) {
		   variablePenalty = next.getDemand() * SBVRPConstants.M_CROSS_ROAD_COST_PENALTY;
		                       }

		                       //add up total cross road penalty cost
		      crossRoadPenaltyCost = crossRoadPenaltyCost + variablePenalty;
		                   }
		               }
		           }

		           current = next;
		           next = current.getNext();
		       }

		     vNodes.getAttributes().totalCrossRoadPenaltyCost = crossRoadPenaltyCost = 0;
		   }
	 */
	/* public void setTotalTurnAroundPenaltyCost(Object o) {
		    VRPBNodesLinkedList vNodes = (VRPBNodesLinkedList) o;
		    double turnAroundPenaltyCost = 0;


		             double actualRouteCost = getTotalCost(o);
		             Nodes startPC = vNodes.getHead();
		             Nodes destinationPC = startPC.getNext();
		             int startIndex = 0;
		             int destinationIndex;
		             int[] traveledRoadSegments;
		             int lastTraveledRoadSegment = -1;
		             MatrixNode matrixNode;

		             while ((startPC != null) && (destinationPC != null)) {
		        startIndex = startPC.getDistanceMatrixIndex();
		        destinationIndex = destinationPC.getDistanceMatrixIndex();

		        if (startIndex != destinationIndex) {
		   matrixNode = SBVRPData.distanceMatrix.getDataAt(startIndex, destinationIndex);

		            if (startPC.getType() == SBVRPConstants.PICK_UP_POINT) {
		     if (matrixNode.imposeTurnAroundPenaltyCost(lastTraveledRoadSegment)) {
		                    turnAroundPenaltyCost += (SBVRPData.pickUpPoints.findPickUpPoint(matrixNode.getStartName()).getTurnAroundCostPenalty() * actualRouteCost);
		                }
		            }

		            if (matrixNode.getFinishType() == SBVRPConstants.PICK_UP_POINT) {
		                traveledRoadSegments = matrixNode.getSegmentArray();

		                if (traveledRoadSegments.length != 1) {
		   lastTraveledRoadSegment = traveledRoadSegments[traveledRoadSegments.length - 2];
		                } else {
		                    lastTraveledRoadSegment = -1;
		                }
		            }
		        }

		        startPC = destinationPC;
		        destinationPC = startPC.getNext();
		             }

		    vNodes.getAttributes().totalTurnAroundPenaltyCost = turnAroundPenaltyCost;
		     }
	 */

	/*
	 * Traverse through the linked and get the totalDemand for the route
	 */
	public void setTotalDemand(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;

		Nodes tempCell = vNodes.getHead().getNext();
		int tempD = 0;
		while (tempCell != vNodes.getTail()) {
			 tempD += tempCell.getDemand();
			 tempCell = tempCell.getNext();
		}

		vNodes.getAttributes().setTotalDemand(tempD);
	}

	/*
	 * Traverse through the linked and get the totalDistance for the route
	 */

	public void setTotalDistance(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		float distTravelled = 0;
		Nodes left = vNodes.getHead();
		Nodes right = vNodes.getHead().getNext(); //node after head

		while (right != vNodes.getTail()) { //head){

			try {
				distTravelled += (float) Math.sqrt( (float) ( ( (right.getShipment()
						.getXCoord() -
						left.getShipment().getXCoord()) * (right.getShipment()
								.getXCoord() -
								left.getShipment().getXCoord())) +
								( (right.getShipment().getYCoord() -
										left.getShipment().getYCoord()) * (right.getShipment()
												.getYCoord() -
												left.getShipment().getYCoord()))));
			}
			catch (ArithmeticException e) {
				System.out.println(e);
			}

			//System.out.println(distTravelled);
			left = right;
			right = right.getNext();
		}

		vNodes.getAttributes().setTotalDistance(distTravelled);
	}

	/*
	 * Traverse through the route and obtain the totalTravelTime
	 */
	
	public static boolean getTimeFeasibility(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;
		double distTravelled = 0;
		double waitingTime = 0;
		final double maxWaitingTime = ((VRPBTWShipment) vNodes.getHead().getShipment()).getMaxWait();
		boolean status = false;
		boolean isDiagnostic = false;
		Nodes left = vNodes.getHead();
		Nodes right = vNodes.getHead().getNext(); //node after head

		do { //head){

			try {
				distTravelled += left.getShipment().getServiceTime();
				distTravelled += ((VRPBTWShipment) left.getShipment()).getWaitingTime();
				distTravelled += (float) Math.sqrt( (float) ( ( (right.getShipment()
						.getXCoord() -
						left.getShipment().getXCoord()) * (right.getShipment()
								.getXCoord() -
								left.getShipment().getXCoord())) +
								( (right.getShipment().getYCoord() -
										left.getShipment().getYCoord()) * (right.getShipment()
												.getYCoord() -
												left.getShipment().getYCoord()))));
			}
			catch (ArithmeticException e) {
				System.out.println(e);
			}
			
			if(isDiagnostic) {
				System.out.println("Left: Earliest:" + ((VRPBTWShipment) left.getShipment()).getEarliestTime() + " Latest: " + ((VRPBTWShipment) left.getShipment()).getLatestTime() + " Waiting: " + ((VRPBTWShipment) left.getShipment()).getWaitingTime());
				System.out.println("Right: Earliest:" + ((VRPBTWShipment) right.getShipment()).getEarliestTime() + " Latest: " + ((VRPBTWShipment) right.getShipment()).getLatestTime() + " Arrival: " + distTravelled);
			}
			
			
			/*if(((VRPBTWShipment) right.getShipment()).getLatestTime() < ((VRPBTWShipment) left.getShipment()).getLatestTime()) {
				status = false;
				break;
			}*/
			if(distTravelled < ((VRPBTWShipment) right.getShipment()).getEarliestTime()) {
				waitingTime = ((VRPBTWShipment) right.getShipment()).getEarliestTime() - distTravelled;
				((VRPBTWShipment) right.getShipment()).setWaitingTime(Math.round(waitingTime));
				((VRPBTWShipment) right.getShipment()).setArrivalTime(Math.round(distTravelled));
				status = true;
			}
			else if(distTravelled >= ((VRPBTWShipment) right.getShipment()).getEarliestTime() 
					&& distTravelled <= ((VRPBTWShipment) right.getShipment()).getLatestTime()) {
				((VRPBTWShipment) right.getShipment()).setWaitingTime(0);
				((VRPBTWShipment) right.getShipment()).setArrivalTime(Math.round(distTravelled));
				status = true;
			}
			else {
				status = false;
				break;
			}
			

			//System.out.println(distTravelled);
			left = right;
			right = right.getNext();
		} while (right != vNodes.getTail());

		return status; 
	}
	

	public void setTotalTravelTime(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;

		double travTime = 0;
		double dist = getTotalDistance(o);
		double milesPerMinute = ZeusProblemInfo.getAverageBusSpeed() / 60;
		travTime = milesPerMinute * dist;

		Nodes theCell = vNodes.getHead().getNext(); //start at the first customer

		while (theCell != vNodes.getTail()) {
			if (theCell.getShipment().getTruckTypeNeeded().equals(ZeusProblemInfo.getRegular())) {
				travTime += (ZeusProblemInfo.getWEPickupTime() * theCell.getDemand());
			}
			else if (theCell.getShipment().getTruckTypeNeeded().equals(ZeusProblemInfo.
					getWheelChair())) {
				travTime += (ZeusProblemInfo.getWEPickupTime() * theCell.getDemand());
			}
			else if (theCell.getShipment().getTruckTypeNeeded().equals(ZeusProblemInfo.
					getWheelChairElevator())) {
				travTime += (ZeusProblemInfo.getWEPickupTime() * theCell.getDemand());
			}
			else if (theCell.getShipment().getTruckTypeNeeded().equals(ZeusProblemInfo.getMonitor())) {
				travTime += (ZeusProblemInfo.getMPickUpTime() * theCell.getDemand());
			}

			theCell = theCell.getNext();
		}

		vNodes.getAttributes().setMaxTravelTime(travTime);
	}

	public void setMaxTravelTime(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;

		double linehaul = 0;

		/*
		        if (vNodes.getHead().getNext() != vNodes.getTail()) {
		     double backhaul = ProblemInfo.distanceMatrix.getDataAt(vNodes.getHead()
		     .getDistanceMatrixIndex(),
		              vNodes.getHead().getNext().getDistanceMatrixIndex())
		                                                      .getActualDistance();
		          backhaul = (backhaul * ProblemInfo.averageBusSpeed) / 60;
		          linehaul = getTotalTravelTime(o) - backhaul;
		        }
		 */
		vNodes.getAttributes().setMaxTravelTime(linehaul);
	}

	public void setAvgTravelTime(Object o) {
		VRPBTWNodesLinkedList vNodes = (VRPBTWNodesLinkedList) o;

		//System.out.println("calculating average travel time");
		double aTT = 0;

		/*
		        if (vNodes.getHead().getNext() != vNodes.getTail()) {
		          Nodes tempCell = vNodes.getHead().getNext();
		          double haulTime = getTotalTravelTime(o);

		          while (tempCell.getNext() != null) {
		            //add the travel time while on the road (distance)
		            double backhaul = 0;

		            //if the first cell and the next cell are not the same pick up point
		            if (tempCell.getDistanceMatrixIndex() != tempCell.getNext()
		     .getDistanceMatrixIndex()) {
		              MatrixNode node = ProblemInfo.distanceMatrix.getDataAt(tempCell.getDistanceMatrixIndex(),
		                  tempCell.getNext().getDistanceMatrixIndex());
		              backhaul = node.getDistance(vNodes.getTruckType().getTruckNo());

		              backhaul = (backhaul * ProblemInfo.averageBusSpeed) / 60;

		              aTT += (haulTime - backhaul);
		              haulTime = haulTime - backhaul;
		            }

		            //add the travel time for the student getting on the bus
		     if (tempCell.getShipment().getBusTypeNeeded().equals(ProblemInfo.REGULAR)) {
		              aTT += (ProblemInfo.R_PICK_UP_TIME * tempCell.getDemand());
		            } else if (tempCell.getShipment().getBusTypeNeeded().equals(ProblemInfo.WHEELCHAIR)) {
		              aTT += (ProblemInfo.W_PICK_UP_TIME * tempCell.getDemand());
		            } else if (tempCell.getShipment().getBusTypeNeeded().equals(ProblemInfo.WHEELCHAIR_ELEVATOR)) {
		              aTT += (ProblemInfo.WE_PICK_UP_TIME * tempCell.getDemand());
		            } else if (tempCell.getShipment().getBusTypeNeeded().equals(ProblemInfo.MONITOR)) {
		              aTT += (ProblemInfo.M_PICK_UP_TIME * tempCell.getDemand());
		            }

		            tempCell = tempCell.getNext();
		          }

		          //System.out.println("average after getting on bus: " + aTT);
		          //System.out.println("final average: " + aTT + "/" + (getSize()-2) + " = " + (aTT / (getSize()-2)));
		          if (vNodes.isCollapsed()) {
		            //System.out.println("collapsed size = " + vNodes.getCollapsedSize() + " regular size = " + vNodes.getSize());
		            aTT = aTT / (vNodes.getCollapsedSize());
		          } else {
		            aTT = aTT / (vNodes.getSize() - 2);
		          }
		        }
		 */
		vNodes.getAttributes().setMaxTravelTime(aTT);
	}


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
