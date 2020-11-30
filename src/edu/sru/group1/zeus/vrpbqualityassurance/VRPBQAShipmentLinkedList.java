package edu.sru.group1.zeus.vrpbqualityassurance;

import java.util.Vector;
import edu.sru.thangiah.zeus.qualityassurance.*;

public class VRPBQAShipmentLinkedList extends QAShipmentLinkedList implements java.io.Serializable, java.lang.Cloneable
{
	public VRPBQAShipmentLinkedList() {
	  }

	  public boolean customerServicedOnlyOnce(VRPBQADepotLinkedList qaDepots) {
	    //loop through all the shipments and mark which are serviced and count the number of times
	    //the customers are serviced. Each shipment should be serviced no more than once
	    for (int i = 0; i < qaDepots.getDepots().size(); i++) {
	      VRPBQADepot d = (VRPBQADepot) qaDepots.getDepots().elementAt(i);
	      for (int j = 0; j < d.getTrucks().size(); j++) {
	        VRPBQATruck t = (VRPBQATruck) d.getTrucks().elementAt(j);
	        for (int k = 0; k < t.getNodes().size(); k++) {
	          VRPBQANode n = (VRPBQANode) t.getNodes().elementAt(k);
	          for (int l = 0; l < getShipments().size(); l++) {
	            VRPBQAShipment s = (VRPBQAShipment) getShipments().elementAt(l);
	            if (s.getIndex() == n.getIndex()) {
	              s.setServecount(s.getServecount()+1);
	              break;
	            }
	          }
	        }
	      }
	    }

	    boolean ret = true;
	    //loop through shipments and look for anomolies
	    for (int l = 0; l < getShipments().size(); l++) {
	      VRPBQAShipment s = (VRPBQAShipment) getShipments().elementAt(l);
	      if (s.getServecount() != 1) {
	        edu.sru.thangiah.zeus.core.Settings.printDebug(edu.sru.thangiah.zeus.
	            core.Settings.ERROR,
	            "Shipment " + s.getIndex() + " is serviced " + s.getServecount() + " time(s)");
	        ret = false;
	      }
	    }

	    return ret;
	  }
}
