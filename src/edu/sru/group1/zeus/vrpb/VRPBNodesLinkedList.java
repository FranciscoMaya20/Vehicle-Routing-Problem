/*
 

  Based off Dr. Thangiah template of Zeus

  VRPBNodesLinkedList is the linked list of nodes.  This is the file that houses the insertion heuristic.

 */


package edu.sru.group1.zeus.vrpb;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.group1.zeus.vrpb.VRPBNodes;
//import the parent class
import edu.sru.thangiah.zeus.core.NodesLinkedList;
/**
*
* <p>VRPBNodesLinkedList:</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: </p>
* @author Nassir Weaver, Francisco Maya, Tyler Kimmel (Sam R. Thangiah's VRP as reference/starting point)
* @version 1.0
*/
public class VRPBNodesLinkedList  extends NodesLinkedList implements java.io.Serializable,java.lang.Cloneable
{
	public VRPBNodesLinkedList() 
	{
		setHead(new VRPBNodes());
		setTail(new VRPBNodes());
		linkHeadTail();
		setAttributes(new VRPBAttributes());
	}
	public VRPBNodesLinkedList(VRPBTruckType tT, double depotX, double depotY, int tN) {

		//super(tT, depotX, depotY, tN);
		setTruckType(tT);
		setTruckNum(tN);
		//Set the feasibility check to be done for the route
		setFeasibility(new VRPBFeasibility(getTruckType().getMaxDuration(),
				getTruckType().getMaxCapacity(), this));
		setHead(new VRPBNodes(new VRPBShipment(0, depotX, depotY, 0, 0, "D", "D")));
		setTail(new VRPBNodes(new VRPBShipment( -1, depotX, depotY, 0, 0, "D", "D")));
		linkHeadTail();
		//assign the VRP attributes
		setAttributes(new VRPBAttributes());
	}
	public VRPBNodes getVRPHead() {
		return (VRPBNodes) getHead();
	}
	public boolean getInsertShipment(VRPBNodesLinkedList currNodeLL,
			VRPBShipment theShipment) {
		return false;
	}
	public boolean insertShipment(Shipment theShipment) {
		//method for inserting the shipment into a truck
		VRPBNodesLinkedList status = (VRPBNodesLinkedList) ZeusProblemInfo.getInsertShipType();
		return status.getInsertShipment(this, (VRPBShipment) theShipment);
	}

	/**
	 * Same as insertShipment except the insertion parameter must be specified
	 * as a point cell and also the previous point cell is returned. This method
	 * is used by the local optimization methods
	 * @param insertNode Nodes that is to be inserted into the route
	 * @return Nodes that is previous to the inserted Nodes.
	 */
	/** @todo  This method should be inside the class that inherits the NodesLinkedList */
	public Nodes insertNodes(Nodes insertNode) {
		boolean isDiagnostic = false;
		Nodes pcBeforeInsertNode = null;
		Shipment theShipment = insertNode.getShipment();

		if (isDiagnostic) {
			System.out.println("========================================");
			System.out.println("In InsertNodes in Nodeslinked list");
			System.out.println("Nodes to be inserted " + theShipment.getIndex());
			System.out.println("Route to be inserted " + this.getRouteString());
			System.out.println("Cost before insertion " + this.getCost());
		}

		// the route is empty
		if (getHead().getNext() == getTail()) {
			getHead().setNext(insertNode);
			getTail().setPrev(insertNode);
			insertNode.setPrev(getHead());
			insertNode.setNext(getTail());

			pcBeforeInsertNode = getHead(); //return head depot

			if (isDiagnostic) {
				System.out.println("Route is empty");
				System.out.println("After inserting the node " + this.getRouteString());
			}

			if (getFeasibility().isFeasible()) {
				this.removeNodes(insertNode); //route is infeasible, remove this cell
				if (isDiagnostic) {
					System.out.println("Insertion infeasible - Returning null");
					System.out.println("Returning to original route " +
							this.getRouteString());
					System.out.println("================== Exiting insertNodes ");
				}
				return null;
			}
		}
		// the route is not empty
		else {
			double cost = Double.MAX_VALUE;
			Nodes costCell = null; //cell after which the new cell was inserted to achieve cost
			Nodes prevCell = getHead();
			Nodes nextCell = getHead().getNext();

			if (isDiagnostic) {
				System.out.println("Route is not empty");
			}

			//loop through all the cells looking for the cheapest place to put the
			//new cell.
			while (nextCell != null) {
				//insert the cell after current prevCell
				prevCell.setNext(insertNode);
				insertNode.setPrev(prevCell);
				insertNode.setNext(nextCell);
				nextCell.setPrev(insertNode);

				//calculate the cost
				//double tempCost = ProblemInfo.nodesLLLevelCostF.getTotalConstraintCost(this);
				//double tempCost = 0; // ------------- FIX THIS ---------------
				double tempCost = ZeusProblemInfo.getNodesLLLevelCostF().getTotalCost(this);
				if (isDiagnostic) {
					System.out.println("After inserting node " + this.getRouteString());
					System.out.println("Cost after insertion " + tempCost);
				}

				//check to see if the new route exceeds the maximum distance allowed
				if (getFeasibility().isFeasible()) {
					//decide if this cell should be saved
					if (tempCost < cost) {
						cost = tempCost;
						costCell = prevCell;
					}
					if (isDiagnostic) {
						System.out.println("Insertion is feasible ");
						System.out.println("Cost before and after insertion " + cost + " " +
								tempCost);
					}
				}

				//remove the new cell
				prevCell.setNext(nextCell);
				nextCell.setPrev(prevCell);
				insertNode.setNext(null);
				insertNode.setPrev(null);
				if (isDiagnostic) {
					System.out.println("After removing the node from the route " +
							this.getRouteString());
				}

				//set prevCell and nextCell to the next cells in linked list
				prevCell = nextCell;
				nextCell = prevCell.getNext();
			}

			if (costCell != null) {
				//put the cell in the cheapest place you found
				prevCell = costCell;
				nextCell = prevCell.getNext();
				prevCell.setNext(insertNode);
				insertNode.setPrev(prevCell);
				insertNode.setNext(nextCell);
				nextCell.setPrev(insertNode);

				pcBeforeInsertNode = prevCell;
				if (isDiagnostic) {
					System.out.println("Cost is not null");
					System.out.println(
							"After inserting the cell in the cheapest place found" +
									this.getRouteString());
				}
			}
			else {
				if (isDiagnostic) {
					System.out.println("Cost is null");
				}
				return null;
			}
		}
		theShipment.setIsAssigned(true);
		//ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(this);
		return pcBeforeInsertNode;
	}

	/**
	 * This was in the LinearGreedyInsertShipment class and was move to the VRPNodesLinkedList class
	 * @return Object
	 */
	public Object clone() {

		VRPBNodesLinkedList clonedNodesLinkedList = new VRPBNodesLinkedList();

		clonedNodesLinkedList.setAttributes((Attributes)this.getAttributes().clone());
		//clonedNodesLinkedList.setCollapsed(this.collapsed);
		clonedNodesLinkedList.setFeasibility( (VRPBFeasibility)this.getFeasibility().
				clone());
		clonedNodesLinkedList.setTruckType( (VRPBTruckType)this.getTruckType().clone());
		clonedNodesLinkedList.setTruckNum(this.getTruckNum());
		clonedNodesLinkedList.setHead( (VRPBNodes)this.getHead().clone());

		this.expandRoute();

		if (this.getHead() != this.getTail()) {
			VRPBNodes currentNodes = (VRPBNodes) clonedNodesLinkedList.getHead();
			VRPBNodes nextNodes = (VRPBNodes)this.getHead().getNext();

			while (nextNodes != null) {
				currentNodes.setNext( (VRPBNodes) nextNodes.clone()); //create the next depot
				currentNodes.getNext().setPrev(currentNodes); //set the next depot's prev
				currentNodes = (VRPBNodes) currentNodes.getNext();
				nextNodes = (VRPBNodes) nextNodes.getNext();

				//once next is null, we have found the tail of the list
				if (nextNodes == null) {
					clonedNodesLinkedList.setTail(currentNodes);
					currentNodes.setNext(null);
				}
			}
		}

		//Set the route for the feasibility
		clonedNodesLinkedList.getFeasibility().setRoute(clonedNodesLinkedList);

		return clonedNodesLinkedList;
	}
}

/*
 This is the LinearGreedyInsertShipment heuristic.  This is created before the createInitalRoutes() in VRPB and 
 when getInsertShipment is called(through createInitalRoutes) it runs through the getInsertShipment function below if the linearGreedyInsertShipment
 was selected
  
 */
class LinearGreedyInsertShipment
extends VRPBNodesLinkedList {
	public boolean getInsertShipment(VRPBNodesLinkedList currNodeLL,
			VRPBShipment theShipment) {

		
		VRPBNodes tmpPtr;
		//currNodeLL is the reference to the current node linked list being considered for insertion
		//theShipment is the shipment to be inserted
		VRPBNodes theCell = new VRPBNodes(theShipment);

		// the route is empty
		if (currNodeLL.getHead().getNext() == currNodeLL.getTail()) {
			currNodeLL.setHeadNext(theCell);
			currNodeLL.getTail().setPrev(theCell);
			theCell.setPrev(currNodeLL.getHead());
			theCell.setNext(currNodeLL.getTail());

			//print out current route
			/*{
		    	  System.out.println("Printing VRPNodesLinkedList");
		    	  VRPNodes tempPtr= (VRPNodes)currNodeLL.getHead();
		    	  while (tempPtr != (VRPNodes)currNodeLL.getTail())
		    	  {
		    		  System.out.println("Node number and Demand is:"+tempPtr.getIndex()+" "+tempPtr.getDemand());
		    		  tempPtr = tempPtr.getVRPNext();
		    	  }
		    	  System.out.println("Done Printing");    	  
		      }*/
				
			//if its not feasible, return route to what it was and return false
			if (!currNodeLL.getFeasibility().isFeasible()) {
				//remove the inserted node
				tmpPtr = (VRPBNodes) currNodeLL.getHead().getNext();
				tmpPtr.setNext(null);
				tmpPtr.setPrev(null);

				//point the head and tail to each other
				currNodeLL.setHeadNext(currNodeLL.getTail());
				currNodeLL.getTail().setPrev(currNodeLL.getHead());

				//print out current route
				/*{
		      	  System.out.println("Printing Undo VRPNodesLinkedList");
		      	  VRPNodes tempPtr= (VRPNodes)currNodeLL.getHead();
		      	  while (tempPtr != (VRPNodes)currNodeLL.getTail())
		      	  {
		      		  System.out.println("Node number and Demand is:"+tempPtr.getIndex()+" "+tempPtr.getDemand());
		      		  tempPtr = tempPtr.getVRPNext();
		      	  }
		      	  System.out.println("Done Printing");    	  
		        }*/

				return false;
			}
		}
		// the route is not empty
		else {
			double cost = Double.MAX_VALUE;
			VRPBNodes costCell = null; //cell after which the new cell was inserted to achieve cost

			VRPBNodes prevCell = (VRPBNodes) currNodeLL.getHead();
			VRPBNodes nextCell = (VRPBNodes) currNodeLL.getHead().getNext();

			while (nextCell != currNodeLL.getTail()) {
				//insert the cell after current prevCell
				prevCell.setNext(theCell);
				theCell.setPrev(prevCell);
				theCell.setNext(nextCell);
				nextCell.setPrev(theCell);

				//check to see if the new route is feasible
				if (currNodeLL.getFeasibility().isFeasible()) {
					//calculate the cost
					double tempCost = ZeusProblemInfo.getNodesLLLevelCostF().getTotalCost(
							currNodeLL);

					//decide if this cell should be saved
					if (tempCost < cost) {
						cost = tempCost;
						costCell = prevCell;
					}
				}
				//remove the new cell
				prevCell.setNext(nextCell);
				nextCell.setPrev(prevCell);
				theCell.setNext(null);
				theCell.setPrev(null);

				//set prevCell and nextCell to the next cells in linked list
				prevCell = nextCell;
				nextCell = (VRPBNodes) prevCell.getNext();
				//print out current route
				/*{
		      	  System.out.println("Printing VRPNodesLinkedList");
		      	  VRPNodes tempPtr= (VRPNodes)currNodeLL.getHead();
		      	  while (tempPtr != (VRPNodes)currNodeLL.getTail())
		      	  {
		      		  System.out.println("Node number and Demand is:"+tempPtr.getIndex()+" "+tempPtr.getDemand());
		      		  tempPtr = tempPtr.getVRPNext();
		      	  }
		      	  System.out.println("Done Printing");    	  
		        }*/
			}



			if (costCell != null) {
				prevCell = costCell;
				nextCell = (VRPBNodes) prevCell.getNext();
				prevCell.setNext(theCell);
				theCell.setPrev(prevCell);
				theCell.setNext(nextCell);
				nextCell.setPrev(theCell);
			}
			else {
				return false;
			}
		}

		theShipment.setIsAssigned(true);
		System.out.println(currNodeLL.getAttributes().getTotalDemand());
		ZeusProblemInfo.getNodesLLLevelCostF().calculateTotalsStats(currNodeLL);
		

		{
			System.out.println("Route is:");
			VRPBNodes tempPtr= (VRPBNodes)currNodeLL.getHead();
			while (tempPtr != (VRPBNodes)currNodeLL.getTail())
			{
				System.out.print(tempPtr.getIndex()+"("+tempPtr.getDemand()+")-");
				tempPtr = tempPtr.getVRPBNext();
			} 	  
			System.out.println();
		}

		return true;
	}

	//The WhoAmI methods gives the id of the assigned object
	//It is a static method so that it can be accessed without creating an object
	public static String WhoAmI() {
		return ("Insertion Type: Linear greedy insertion heuristic");
	}
}



