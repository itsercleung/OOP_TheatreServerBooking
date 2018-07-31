package tbs.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TBSServerImpl implements TBSServer {
    private Artist artistObj = new Artist();
    //List<String> fields
    private List<String> theatreIDList = new ArrayList<>();
    private List<String> theatreSeatingDim = new ArrayList<>();
    private List<String> artistIDList = new ArrayList<>();
    private List<String> actIDsOfArtist = new ArrayList<>();
    private List<String> actIDList = new ArrayList<>();
    private List<String> performanceIDsOfAct = new ArrayList<>();
    private List<String> performanceIDList = new ArrayList<>();
    private List<String> ticketIDList = new ArrayList<>();
    //Map HashMap fields
    private Map<String, String> startTimeStrMap = new HashMap<>();
    private Map<String,String> premiumTicketMap = new HashMap<>();
    private Map<String,String> cheapTicketMap = new HashMap<>();

    public String initialise(String path){
        File file = new File(path);
        Scanner inputStream;
        //Check if file exists from given path name: if not catch FileNotFoundException otherwise return "".
        try {
            inputStream = new Scanner(file);
        } catch (FileNotFoundException e) {
            return "ERROR: File is invalid or does not exist in directory!";
        }
        List<String> theatreInfoList = new ArrayList<>();
        while (inputStream.hasNext()) {
            theatreInfoList.add(inputStream.nextLine()); //For each line store into theatreInfoList
        }
        inputStream.close();
        //Get theatreIDs and theatreSeatingDim Lists from theatreList by using Theatre Class MAKE and GET item
        Theatre theatreIDsObj = new Theatre(theatreInfoList);
        theatreIDsObj.makeTheatreLists();
        theatreIDList = theatreIDsObj.getClsTheatreIDs();
        theatreSeatingDim = theatreIDsObj.getTheatreSeatingDim();
        List<String> theatreNames = theatreIDsObj.getTheatreNames();
        List<String> theatreFloorArea = theatreIDsObj.getTheatreFloorArea();
        //If any line ini CSV file is incorrect to the format: "THEATRE" "\t" <theatre ID> "\t" <seating dimension> "\t" <floor area>
        //then output ERROR! IF statement compares sizes of all lists from theatreInfoList. If valid format: all sizes are equal.
        try {
            if (theatreInfoList.size() != theatreSeatingDim.size() || theatreInfoList.size() != theatreFloorArea.size()
                    || theatreInfoList.size() != theatreNames.size() || theatreInfoList.size() != theatreIDList.size()) {
                throw new ExceptionEx(ExceptionCodes.INVALID_FORMAT);
            }
            return "";
        } catch (ExceptionEx e) {
            return e.getErrorMsg();
        }
    }

    public List<String> getTheatreIDs() {
        //Return theatreIDList which can be added from addTheatreIDList method: can be empty if no theatreIDs exist
        return theatreIDList;
    }

    public List<String> getArtistIDs() {
        //Return artistIDList which can be added onto from addArtist method: can be empty if no artistIDs exist
        return artistIDList;
    }

    public List<String> getArtistNames() {
        //Return artistNames from Artist Class which uses the object field from above: Can be empty if no artist has been added
        return artistObj.getArtistNameList();
    }

    public List<String> getActIDsForArtist(String artistID) {
        List<String> tempActIDList = new ArrayList<>();
        //Checking if artistID already exists and is in artistIDList
        try {
            Boolean flag = true;
            for (String row : artistIDList) {
                if (row.equals(artistID)) {
                    Acts actListObj = new Acts(artistID, actIDsOfArtist);
                    actListObj.makeActIDList();
                    tempActIDList = actListObj.getActIDList();
                    flag = false;
                }
            }
            if (flag) {
                throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            List<String> errorList = new ArrayList<>();
            errorList.add(e.getErrorMsg());
            return errorList;
        }
        return tempActIDList;
}

    public List<String> getPeformanceIDsForAct(String actID) {
        //Checking if actID already exists and is in actIDList
        try {
            Boolean flag = true;
            for (String row : actIDList) {
                if (row.equals(actID)) {
                    flag = false;
                }
            }
            if (flag) {
                throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            List<String> errorList = new ArrayList<>();
            errorList.add(e.getErrorMsg());
            return errorList;
        }
        //Get performanceIDList by using field performanceIDsOfAct in Performance class method (makePerformanceIDList)
        Performance performanceListObj = new Performance(actID, performanceIDsOfAct);
        performanceListObj.makePerformanceIDList();
        performanceIDList = performanceListObj.getPerformanceIDList();
        return performanceIDList;
    }

    public List<String> getTicketIDsForPerformance(String performanceID) {
        //Checking if performanceID is valid and already exists in performanceIDList
        try {
            Boolean flag = true;
            for (String row : performanceIDList) {
                if (row.equals(performanceID)) {
                    flag = false;
                }
            }
            if (flag) {
                throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            List<String> errorList = new ArrayList<>();
            errorList.add(e.getErrorMsg());
            return errorList;
        }
        //Get total ticketIDs for each performance, using field ticketIDList in Ticket class method (makeTicketIDPerformanceList)
        Tickets ticketsForPerformanceObj = new Tickets(ticketIDList, performanceID);
        ticketsForPerformanceObj.makeTicketIDPerformanceList();
        return ticketsForPerformanceObj.getTicketIDPerformanceList();
    }

    public String addArtist(String name) {
        //Given name of Artist, create a UNIQUE ID for the Artist using Artist Class:
        //If the made ArtistID is correctly made, put into artistIDList field, else return ERROR if result is invalid.
        artistObj.setName(name);
        artistObj.makeArtistID();
        try {
            String tempArtistID = artistObj.getArtistID();
            switch (tempArtistID) {
                case "1":
                    throw new ExceptionEx(ExceptionCodes.EMPTY_INPUT);
                case "2":
                    throw new ExceptionEx(ExceptionCodes.ALREADY_EXISTS);
                default:
                    artistIDList.add(tempArtistID);
                    break;
            }
        } catch (ExceptionEx e) {
            return e.getErrorMsg();
        }
        return artistObj.getArtistID();
    }

    public String addAct(String title, String artistID, int minutesDuration) {
        //addAct creates a concatenated string list of ActID (if valid from ActID class) + artistID for actIDsOfArtist
        //as well as total actIDs for actIDList. actIDsOfArtist concatenated string list allows for program to assign
        //the correct actIDs that are assigned to the artistID [This is used for getActsForArtist].
        List<String> artistList = artistObj.getArtistIDList();
        Acts actIDObj = new Acts(title, artistID, minutesDuration, artistList);
        actIDObj.makeActID();
        String returnActID = actIDObj.getActID();
        //If current ActID is not an empty string (from Act class method getActID) then ActID added successfully:
        //and add to existing lists of actIDsOfArtist and actIDList fields.
        try {
            switch (returnActID) {
                case "1":
                    throw new ExceptionEx(ExceptionCodes.EMPTY_INPUT);
                case "2":
                    throw new ExceptionEx(ExceptionCodes.INVALID_MINUTES);
                case "3":
                    throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            return e.getErrorMsg();
        }
        actIDsOfArtist.add(returnActID + ',' + artistID);
        actIDList.add(returnActID);
        return returnActID;
    }

    public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
        //First, compare actID to actIDList given from addAct method: Here we can first see if actID input exists in overall actIDs
        String performanceID;
        int count = 0;
        for (String act : actIDList) {
            if (act.equals(actID)) {
                count++;
                break;
            }
        }
        //Second, compare theatreID to theatreIDList: Here we can see if theatreID input exists in overall given theatreIDs
        for (String theatre : theatreIDList) {
            if (theatre.equals(theatreID)) {
                count++;
                break;
            }
        }
        //If these two steps are met, then success: PerformanceID will be created from current input information and using
        //theatre class with performanceObj object performing such methods within the class
        try {
            if (count == 2) {
                Performance performanceObj = new Performance(actID, theatreID, startTimeStr, premiumPriceStr, cheapSeatsStr);
                performanceObj.makePerformanceID();
                performanceObj.checkPerformanceFormat();
                performanceID = performanceObj.getPerformanceID();
                //If performanceID doesn't equal empty string, then success: Add current performanceID to performanceIDsOfAct field list,
                //and then store the startTime, premiumTicket and cheapTicket input variables into a map to be used in SALESREPORT method.
                try {
                    switch (performanceID) {
                        case "1":
                            throw new ExceptionEx(ExceptionCodes.INVALID_TIME_FORMAT);
                        case "2":
                            throw new ExceptionEx(ExceptionCodes.INVALID_PRICE);
                        default:
                            performanceIDsOfAct.add(performanceID + ',' + actID);
                            //Map: stores all startTimeStr, premiumPriceStr, cheapSeatsStr in MAPS for each PerformanceID for collection and use
                            startTimeStrMap.put(performanceID, startTimeStr);
                            premiumTicketMap.put(performanceID, premiumPriceStr);
                            cheapTicketMap.put(performanceID, cheapSeatsStr);
                            break;
                    }
                } catch (ExceptionEx e) {
                    return e.getErrorMsg();
                }
            }
            else {
                throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            return e.getErrorMsg();
        }
        return performanceID;
    }

    public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
        //Finding seating dimension string from given SeatingDimensionList from initialization method
        Tickets makeTicketObj = new Tickets(performanceID, rowNumber, seatNumber);
        Seats seatCheckObj = new Seats(performanceID, theatreIDList, theatreSeatingDim);
        seatCheckObj.makeSeatingDimStr();
        String seatingDimStr = seatCheckObj.getSeatingDimStr();
        //If current row number seat number DOES NOT meet range of seating dimension, or is less or equal to 0 then ERROR
        try {
            if ((rowNumber > Integer.parseInt(seatingDimStr)) || (rowNumber <= 0) || (seatNumber > Integer.parseInt(seatingDimStr)) || (seatNumber <= 0)) {
                throw new ExceptionEx(ExceptionCodes.INVALID_SEAT);
            }
        } catch (ExceptionEx e) {
            return e.getErrorMsg();
        }

        makeTicketObj.makeTicketID();
        String ticketID = makeTicketObj.getTicketID();
        //if initially thee ticketIDList is empty, just add current ticketIDList
        if (ticketIDList.isEmpty()) {
            ticketIDList.add(ticketID);
            return ticketID;
        }
        //For loop that compares all of ticketIDList with current ticketID to check whether or not the ticket is already been issued
        for (String row : ticketIDList) {
            //If the ticketID has already been issued, then current ticketID already exists! Thus seat has already been taken!
            try {
                if (row.equals(ticketID)) {
                    throw new ExceptionEx(ExceptionCodes.ALREADY_EXISTS);
                }
            } catch (ExceptionEx e) {
                return e.getErrorMsg();
            }
        }
        //If all successful from above and ticketID is unique: Add current ticketID to the list for comparison and return.
        ticketIDList.add(ticketID);
        return ticketID;
    }

    public List<String> seatsAvailable(String performanceID) {
        //If performanceID doesn't exist already in performanceIDList then return ERROR
        try {
            boolean flag = true;
            for (String row : performanceIDList) {
                if (performanceID.equals(row)) {
                    flag = false;
                }
            }
            if (flag) {
                throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            List<String> errorList = new ArrayList<>();
            errorList.add(e.getErrorMsg());
            return errorList;
        }
        //Get ticketPerformanceList by using Ticket instance with current ticketIDList and input performanceID.
        //This contains current tickets issued to specific performances, and will be used to compare whether or not Seats have already been booked or not.
        Tickets ticketsForPerformanceObj = new Tickets(ticketIDList, performanceID);
        ticketsForPerformanceObj.makeTicketIDPerformanceList();
        List<String> ticketIDPerformanceList = ticketsForPerformanceObj.getTicketIDPerformanceList();
        //Using getSeatingListObj, the object will be made with constructor that requires performanceID for identifying specific tickets,
        //ticketIDPerformanceList to provide total tickets with performances attached, theatreIDList to see if performanceID belongs
        //in which theatreID, and theatreSeatingDim to find the theatre seating dimension of the specific performance ID.
        Seats getSeatingListObj = new Seats(performanceID, ticketIDPerformanceList, theatreIDList, theatreSeatingDim);
        getSeatingListObj.makeTakenSeats();
        getSeatingListObj.makeSeatingDimStr();
        getSeatingListObj.makeAvailableSeats();
        return getSeatingListObj.getAvailableSeatList();
    }

    public List<String> salesReport(String actID) {
        //Check if ACTID input exists!
        try {
            Boolean flag = true;
            for (String row : actIDList) {
                if (row.equals(actID)) {
                    flag = false;
                }
            }
            if (flag) {
                throw new ExceptionEx(ExceptionCodes.DOES_NOT_EXIST);
            }
        } catch (ExceptionEx e) {
            List<String> errorList = new ArrayList<>();
            errorList.add(e.getErrorMsg());
            return errorList;
        }

        //Initially, must find performanceIDList given the specific actID (where this list contains all performances with that actID.
        Performance performanceSalesObj = new Performance(actID, performanceIDsOfAct);
        performanceSalesObj.makePerformanceIDList();
        performanceIDList = performanceSalesObj.getPerformanceIDList();
        //Then, find seating dimension list for the specific performanceIDs in actID: Seating dimension can differentiate with each
        //performance ID.
        List<String> seatingDimStrList = new ArrayList<>();
        for (String performanceID : performanceIDList) {
            Seats seatCheckObj = new Seats(performanceID, theatreIDList, theatreSeatingDim);
            seatCheckObj.makeSeatingDimStr();
            seatingDimStrList.add(seatCheckObj.getSeatingDimStr());
        }
        //Using sales class to create sales object using constructor
        Sales salesReportObj = new Sales(performanceIDList, ticketIDList, seatingDimStrList, startTimeStrMap, premiumTicketMap, cheapTicketMap);
        salesReportObj.makeSalesReportList();
        return salesReportObj.getSalesReportList();
    }

    public List<String> dump() {
        return null;
    }
}