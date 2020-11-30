package edu.sru.thangiah.zeus.vrp;

import java.io.IOException;

import edu.sru.thangiah.zeus.core.ZeusProblemInfo;
import edu.sru.thangiah.zeus.vrp.vrpcostfunctions.*;

public class VRPRoot {
  /**
   * Constructor. Runs the VRP and calculates the total CPU time
 * @throws IOException 
   */
  public VRPRoot() throws IOException {

    //Settings for the ProblemInfo class
    //Problem info consists of a set of static values that are used by a number
    //of different classes. The following has to be set in order for the program
    //to function correctly.
    ZeusProblemInfo.setNodesLLLevelCostF(new VRPNodesLLCostFunctions());
    ZeusProblemInfo.setTruckLevelCostF(new VRPTruckCostFunctions());
    ZeusProblemInfo.setTruckLLLevelCostF(new VRPTruckLLCostFunctions());
    ZeusProblemInfo.setDepotLevelCostF(new VRPDepotCostFunctions());
    ZeusProblemInfo.setDepotLLLevelCostF(new VRPDepotLLCostFunctions());
    //Paths for temporary, input and output files
    //ProblemInfo.currDir gives the working directory of the program
    ZeusProblemInfo.setTempFileLocation(ZeusProblemInfo.getWorkingDirectory()+"\\temp");
    ZeusProblemInfo.setInputPath(ZeusProblemInfo.getWorkingDirectory()+"\\data\\vrp\\problems\\");

    ZeusProblemInfo.setOutputPath(ZeusProblemInfo.getWorkingDirectory()+"\\data\\vrp\\results\\");

    //Solve the VRP for the enclosed data
    new VRP("mdvrp_p01.txt");

  }
}
