package edu.sru.thangiah.zeus.vrp;

import java.io.*;
import java.util.*;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;


/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRP {

  int m = 0, //number of vehicles
      n = 0, //number of customers
      t = 0, //number of days(or depots)
      D = 0, //maximum duration of route
      Q = 0; //maximum capacity of vehicle

  long startTime, endTime; //track the CPU processing time
  private Vector mainOpts = new Vector(); //contains the collections of optimizations
  private Vector optInformation = new Vector(); //contains information about routes
  private VRPShipmentLinkedList mainShipments = new VRPShipmentLinkedList(); //customers read in from a file or database that are available
  private VRPDepotLinkedList mainDepots = new VRPDepotLinkedList(); //depots linked list for the VRP problem
  private VRPQualityAssurance vrpQA; //check the integrity and quality of the solution

  //constructor for the class
  public VRP(String dataFile) throws IOException {

    //Truck types are placed into a vector
	  //Not sure if needed
    //ZeusProblemInfo.Truck = new Vector();

    //Type of shipment insertion to be performed
    //ProblemInfo.insertShipType = new Object();

    boolean isDiagnostic = false;
    Shipment tempShip;
    Depot thisDepot;
    int type;
    int depotNo;
    int countAssignLoop;
    boolean status;
    String outputFileName;

    /** @todo  Need to put in a VRP file and read in VRP data. The readfile method will have to be changed to match the format of the
     * vrp file*/
    //read in the MDVRP data
    readDataFromFile(ZeusProblemInfo.getInputPath() + dataFile);
    Settings.printDebug(Settings.COMMENT,
                        "Read Data File: " + ZeusProblemInfo.getInputPath() + dataFile);
    printDataToConsole();
    writeDataFile(dataFile.substring(dataFile.lastIndexOf("/") + 1));

    //Ensure that the shipment linked list has been loaded with the data
    if (mainShipments.getVRPHead() == null) {
      Settings.printDebug(Settings.ERROR,
                          "VRP: Shipment linked list is empty");
    }

    //Set up the shipment selection type
    //ProblemInfo.selectShipType = new ClosestEuclideanDistToDepot();
    //Settings.printDebug(Settings.COMMENT,ClosestEuclideanDistToDepot.WhoAmI());
    ZeusProblemInfo.setSelectShipType(new SmallestPolarAngleToDepot());
    Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());
    //ProblemInfo.selectShipType = new SmallestPolarAngleShortestDistToDepot();
    //Settings.printDebug(Settings.COMMENT,SmallestPolarAngleShortestDistToDepot.WhoAmI());

    //set up the shipment insertion type
    ZeusProblemInfo.setInsertShipType(new LinearGreedyInsertShipment());
    Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());

    //Capture the CPU time required for solving the problem
    startTime = System.currentTimeMillis();
    // captures the initial information on solving the problem
    // returns the total customer and total distance after the initial solution
    createInitialRoutes();
    System.out.println("Completed initial routes");

    //Get the initial solution
    //Depending on the Settings status, display information on the routes
    //Trucks used, total demand, dist, travel time and cost
    Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
    Settings.printDebug(Settings.COMMENT,
                        "Initial Stats: " + mainDepots.getSolutionString());
    //At this point all shipments have been assigned
    writeLongSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));
    //writeShortSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));

    //Check for the quality and integrity of the solution
    System.out.println("Starting QA");
    vrpQA = new VRPQualityAssurance(mainDepots, mainShipments);
    if (vrpQA.runQA() == false) {
      Settings.printDebug(Settings.ERROR, "QA FAILED!");
    }
    else {
      Settings.printDebug(Settings.COMMENT, "QA succeeded");
     
     
    }
/** @todo  GUI still needs to be implemented */
    //Call to the graphical user inter face
    //Vector emptyVector = new Vector(0);
    //VRPZeusGui gui = new VRPZeusGui(mainDepots, mainShipments, emptyVector);

     ZeusGui guiPost = new ZeusGui(mainDepots, mainShipments);

  } 

  /**
   * Creates the initial solution for the problem
   */
  public void createInitialRoutes() {
    //OptInfo has old and new attributes
    VRPDepot currDepot = null; //current depot
    VRPShipment currShip = null; //current shipment
    //int countLoop=0;

    //check if selection and insertion type methods have been selected
    if (ZeusProblemInfo.getSelectShipType() == null) {
      Settings.printDebug(Settings.ERROR,
                          "No selection shipment type has been assigned");

    }
    if (ZeusProblemInfo.getInsertShipType() == null) {
      Settings.printDebug(Settings.ERROR,
                          "No insertion shipment type has been assigned");
    }

 
    //countLoop=1;
    while (!mainShipments.isAllShipsAssigned()) {
      double x, y;
      int i = 0;
      //Get the x an y coordinate of the depot
      //Then use those to get the customer, that has not been allocated,
      // that is closest to the depot
      currDepot = (VRPDepot) mainDepots.getVRPHead().getNext();
      x = mainDepots.getHead().getXCoord();
      y = mainDepots.getHead().getYCoord();
      //Send the entire mainDepots and mainShipments to get the next shipment
      //to be inserted including the current depot
      VRPShipment theShipment = mainShipments.getNextInsertShipment(mainDepots,
          currDepot, mainShipments, currShip);

      if (theShipment == null) { //shipment is null, print error message
        Settings.printDebug(Settings.COMMENT, "No shipment was selected");
      }
      //The selected shipment will be inserted into the route
      if (!mainDepots.insertShipment(theShipment)) {
        Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +
                            "> cannot be routed");
      }
      else {
        Settings.printDebug(Settings.COMMENT,
                            "The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
                            "> was routed");
        //tag the shipment as being routed
        theShipment.setIsAssigned(true);
      }
    }

    ZeusProblemInfo.getDepotLLLevelCostF().calculateTotalsStats(mainDepots);
  }

  //read in the data from the requested file in token format
  public int readDataFromFile(String VRPFileName) throws IOException {
    // read in the MDVRP data from the listed file and load the information
    // into the availShipments linked list

    //type = 0 (MDVRP)
    //     = 1 (PTSP)
    //     = 2 (PVRP)
    char ch;
    String temp = "";
    int index = 0,
        j = 0,
        type = 0; //type
    //m        = 0,                           //number of vehicles
    //n        = 0,                           //number of customers
    //t        = 0,                           //number of days(or depots)
    //D        = 0,                           //maximum duration of route
    //Q        = 0;                           //maximum load of vehicle
    int p = 3; //Np neighborhood size

    int depotIndex;

    //Open the requested file
    FileInputStream fis;
    InputStreamReader isr;
    BufferedReader br;
    try {
      fis = new FileInputStream(VRPFileName);
      isr = new InputStreamReader(fis);
      br = new BufferedReader(isr);
    }
    catch (Exception e) {
      System.out.println("File is not present");
      return 0;
    }



    //This section will get the initial information from the data file
    //Read in the first line from the file
    String readLn;
    StringTokenizer st;

    //read in the first line
    try {
      readLn = br.readLine();
      //print out the line that was read
      //System.out.println("This is s:" + s);

      st = new StringTokenizer(readLn);
      while (st.hasMoreTokens()) { //while there are more tokens
        //int shValue =  Integer.parseInt(st.nextToken());
        switch (index) {
          case 0:
            type = Integer.parseInt(st.nextToken());
            break;
          case 1:
            m = Integer.parseInt(st.nextToken());
            break;
          case 2:
            n = Integer.parseInt(st.nextToken());
            break;
          case 3:
            t = Integer.parseInt(st.nextToken());
            break;
          case 4:
            D = Integer.parseInt(st.nextToken());
            break;
          case 5:
            Q = Integer.parseInt(st.nextToken());
            break;
        } //end switch
        index += 1;
      } //end while
    }
    catch (Exception e) {
      System.out.println("Line could not be read in");
    }

    //Put the problem information into the ProblemInfo class
    //set the problem info for the problem
    ZeusProblemInfo.setNumDepots(1); //Set the number of depots to 1 for this problem
    ZeusProblemInfo.setProbFileName(VRPFileName); //name of the file being read in
    ZeusProblemInfo.setProbType(type); //problem type
    ZeusProblemInfo.setNoOfVehs(m); //number of vehicles
    ZeusProblemInfo.setNoOfShips(n); //number of shipments
    ZeusProblemInfo.setNoOfDays(t); //number of days (horizon) or number of depots for MDVRP
    if (Q == 0) { //if there is no maximum capacity, set it to a very large number
      Q = 999999999;
    }
    if (D == 0) { //if there is no travel time, set it to a very large number
      D = 999999999; //if there is not maximum distance, set it to a very large number
      //ProblemInfo.maxCapacity = Q;  //maximum capacity of a vehicle
      //ProblemInfo.maxDistance = D;  //maximum distance of a vehicle
    }
    /** @todo  There three variables need to be defined at the beginning of
     * the method */
    float maxCapacity = Q; //maximum capacity of a vehicle
    float maxDistance = D; //maximum distance of a vehicle

    String serviceType = "1"; //serviceType is the trucktype. Should match with
    //required truck type
    //In some problems, different truck types might be present to solve
    //the problem. For this problem, we assume that there is only one
    //truck type that is available.
    //loop through each truck type and store each one in the vector
    int numTruckTypes = 1;
    for (int i = 0; i < numTruckTypes; i++) {
      VRPTruckType truckType = new VRPTruckType(i, maxDistance,
                                                maxCapacity, serviceType);
      ZeusProblemInfo.addTruckTypes(truckType);
    }

    /** @todo DO we really need customer types for the VRP problem? */
    //Some problems tend to have different customer types. In this problem
    //there is only one customter type. The integer value for the customer type
    //should match with the integer value for the truck type for the compatibiliy
    //check to work
    //read in the different customer types
    Vector custTypes = new Vector();
    //Obtain the different customer types
    for (int ct = 0; ct < 1; ct++) {
      custTypes.add(new Integer(1));
    }

    //place the number of depots and number of shipments in the linked list instance
    //These no longer seem to be needed for the shipment linked list. The total number of
    //shipments are tallied when they are inserted into the linked list
    //mainShipments.numShipments = n;
    //mainShipments.noDepots = t;
    //mainShipments.maxCapacity = Q;
    //mainShipments.maxDuration = D ;

    //display the information from the first line
    //System.out.println("typePvrp is       " + type);
    //System.out.println("numVeh is         " + m);
    //System.out.println("numCust is        " + n);
    //System.out.println("days is           " + t);
    //System.out.println("Depot duration is " + D);
    //System.out.println("capacity is       " + Q);

    if (type != 0) { //then it is not an MDVRP problem
      System.out.println("Problem is not an MDVRP problem");
      br.close();
      return 0;
    }

    //This section will get the depot x and y for the PVRP and the PTSP.
    float x = 0, //x coordinate
        y = 0; //y coordinate
    int i = 0, //customer number
        d = 0, //service duration
        q = 0, //demand
        f = 0, //frequency of visit
        a = 0, //number of combinations allowed
        vIndex = 1,
        custCnt = 0;
    int runTimes;

    //Use 1 less the maximum as the 0 index is not used
    //declare the total number of combinations
    int list[] = new int[ZeusProblemInfo.getMaxCombinations()];
    //array of 0'1 and 1's for the combinations
    int currentComb[][] = new int[ZeusProblemInfo.getMaxHorizon()][ZeusProblemInfo.
        getMaxCombinations()];
    //if MDVRP problem, readn in n+t lines
    if (type == 0) {
      runTimes = n + t;
      //if  PVRP/PTSP, read in n+1 lines
    }
    else {
      runTimes = n + 1;

      //This section will get the customers/depots and related information
    }
    try {
      readLn = br.readLine();
      //print out the line that was read in
      //System.out.println("This is s:" + s);

      //The first for loop runtimes dependent upon how many lines are to be read
      //in
      //The next for loop reads the line into s.  Then the entire string in s
      //is processed until the the entire line is processed and there are no
      //more characters are to be processed. There is a case for each index
      //except for the combinations.  The combinations are processed
      //until the last character in s is processed

      for (int k = 0; k < runTimes; k++) {
        index = 0;
        temp = "";
        vIndex = 0;
        custCnt++;
        st = new StringTokenizer(readLn);
        if (k < n) { //it is a shipment
          while (st.hasMoreElements()) {
            switch (index) {
              case 0:
                i = Integer.parseInt(st.nextToken());
                //System.out.println("custNum is " + custNum);
                break;
              case 1: //x = Double.parseDouble(temp);
                x = Integer.parseInt(st.nextToken());
                //System.out.println("x is " + vertexX);
                break;
              case 2:
                y = Integer.parseInt(st.nextToken());
                //y = Double.parseDouble(temp);
                //System.out.println("y is " + vertexY);
                break;
              case 3:
                d = Integer.parseInt(st.nextToken());
                //System.out.println("duration is " + duration);
                break;
              case 4:
                q = Integer.parseInt(st.nextToken());
                //System.out.println("demand is " + demand);
                break;
              case 5:
                f = Integer.parseInt(st.nextToken());
                //System.out.println("frequency is " + frequency);
                break;
              case 6:
                a = Integer.parseInt(st.nextToken());
                //System.out.println("number of comb is " + numComb);
                break;
              default:
                list[vIndex] = Integer.parseInt(st.nextToken());
                //System.out.println("visitComb[" + vIndex +"] is " + visitComb[vIndex]);
                vIndex++;
                break;
            } //end switch
            index += 1;
          } //end while
          //Each combination gets its own set of 0 and 1 combinations
          //a = number of Combinations, list = [] of comb as ints,
          //l=index of combination to be decoded,
          //t = days in planning horizon or #depots
          for (int l = 0; l < a; l++) {
            currentComb[l] = mainShipments.getCurrentComb(list, l, t); // current visit comb

            //insert the customer data into the linked list
          }
          Integer custType = (Integer) custTypes.elementAt(0);
          mainShipments.insertShipment(i, x, y, q, d, f, a, custType.toString(),
                                       list, currentComb);

          //  type = (Integer) custTypes.elementAt(0);
          //       shipment = new Shipment(mainShipments.getNumShipments() +
          //                               1, x, y, 1, d, type.toString(), "" + i);

        }
        else { //it is a depot - add it to the depot linked list
          while (st.hasMoreElements() && index < 3) { //No more than three values are there for depot information
            switch (index) {
              case 0:
                i = Integer.parseInt(st.nextToken());
                //System.out.println("custNum is " + custNum);
                break;
              case 1: //x = Double.parseDouble(temp);
                x = Integer.parseInt(st.nextToken());
                //System.out.println("x is " + vertexX);
                break;
              case 2:
                y = Integer.parseInt(st.nextToken());
                //y = Double.parseDouble(temp);
                //System.out.println("y is " + vertexY);
                break;
              default:
                System.out.println("Default in reading the file was initiated");
                vIndex++;
            } //end switch
            index += 1;
          } //while

          //insert the depot into the depot linked list
          VRPDepot depot = new VRPDepot(i - n, x, y); //n is the number of customers
          mainDepots.insertDepotLast(depot);

          //Each depot has a mainTrucks. The different truck types available are
          //inserted into the mainTrucks type. For the VRP, there is only one truck type
          depot = (VRPDepot) mainDepots.getHead().getNext();
          for (i = 0; i < ZeusProblemInfo.getNumTrucks(); i++) {
            VRPTruckType ttype = (VRPTruckType) ZeusProblemInfo.getTruckTypesAt(i);
            depot.getMainTrucks().insertTruckLast(new VRPTruck(ttype,
                depot.getXCoord(), depot.getYCoord()));
          }
        } //else
        //read the next line from the file
        try {
          readLn = br.readLine();
        }
        catch (Exception e) {
        	e.printStackTrace();
          System.out.println("Reading in the next line");
        } //try
        //System.out.println("This is s:" + s);
      } //end for
    }
    catch (Exception e) {
    	e.printStackTrace();
      System.out.println("Reading the line");
    }

    //print out the shipment numbers on command line
    //  mainShipments.printShipNos();
    //call method to send the data to file
    try {
      //availShipments.outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
      //outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
    }
    catch (Exception e) {
      System.out.println("Shipment information could not be sent to the file");
    }
    br.close();
    return 1;
  }
  
  /**
   * Print  out the data to the console
   */

  public void printDataToConsole() {
    try {
      mainShipments.printVRPShipmentsToConsole();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Write out the data file that was read in
   * @param file name of file used for generating the data
   */

  public void writeDataFile(String file) {
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath() +file +"_students.txt"));
      mainShipments.writeVRPShipments(ps);
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

  /**
   * Will write a long detailed solution for the problem
   * @param file name of the file to write to
   */
  public void writeLongSolution(String file) {
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath() + file + "_long.txt"));
      mainDepots.printDepotLinkedList(ps);
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

  /**
   * Will write a short solution for the problem
   * @param file name of the file to write to
   */
  public void writeShortSolution(String file) {
    try {
      //PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
      //outputPath + "/" + file + "_short.txt"));
      PrintStream ps = new PrintStream(new FileOutputStream(ZeusProblemInfo.getOutputPath() + file + "_short.txt"));

      ps.println("File: " + file + " Num Depots: " +
                 ZeusProblemInfo.getNumDepots() + " Num Pick Up Points: " +
                 ZeusProblemInfo.getNumCustomers() + " Num Trucks: " +
                 ZeusProblemInfo.getNumTrucks() + " Processing Time: " +
                 (endTime - startTime) / 1000 + " seconds");
      ps.println(mainDepots.getAttributes().toDetailedString());
      ps.println();

      Depot depotHead = mainDepots.getHead();
      Depot depotTail = mainDepots.getTail();

      while (depotHead != depotTail) {
        Truck truckHead = depotHead.getMainTrucks().getHead();
        Truck truckTail = depotHead.getMainTrucks().getTail();

        while (truckHead != truckTail) {
          ps.print("Truck #" + truckHead.getTruckNum() + " MaxCap: " +
        		  truckHead.getTruckType().getMaxCapacity() + " Demand: " +
        		  truckHead.getAttributes().getTotalDemand() + " ROUTE:");

          Nodes nodesHead = truckHead.getMainNodes().getHead();
          Nodes nodesTail = truckHead.getMainNodes().getTail();

          while (nodesHead != nodesTail) {
            ps.print(nodesHead.getIndex() + " ");
            nodesHead = nodesHead.getNext();
          }

          ps.println();
          truckHead = truckHead.getNext();
        }

        ps.println();
        ps.println();
        depotHead = depotHead.getNext();
      }
      for (int i = 0; i < optInformation.size(); i++) {
        ps.println(optInformation.elementAt(i));
      }
      ps.close();
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

} //End of VRP file
