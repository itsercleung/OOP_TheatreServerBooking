package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Performance{
    /* Performance class: uses initial inputs: actID, theatreID, startTimeStr, premiumPriceStr and cheapSeatsStr to create
       a valid performanceID. Requirements is the check if startTimeStr, premiumPriceStr and cheapSeatsStr are valid according
       to descriptions below.
       Also creates a performanceIDList that handles every given ActID can output all performanceIDs in a list associated with ActID
     */
    private String performanceID;
    private String actID;
    private String theatreID;
    private String startTimeStr;
    private String premiumPriceStr;
    private String cheapSeatsStr;

    private List<String> performanceIDsOfAct = new ArrayList<>();
    private List<String> performanceIDList = new ArrayList<>();

    //Constructor for making performanceID
    Performance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
        this.actID = actID;
        this.theatreID = theatreID;
        this.startTimeStr = startTimeStr;
        this.premiumPriceStr = premiumPriceStr;
        this.cheapSeatsStr = cheapSeatsStr;
    }

    //Constructor for making performanceIDList
    Performance(String actID, List<String> performanceIDsOfAct) {
        this.actID = actID;
        this.performanceIDsOfAct = performanceIDsOfAct;
    }

    public String getPerformanceID() {
        return performanceID;
    }

    public List<String> getPerformanceIDList() {
        return performanceIDList;
    }

    //makePerformanceID uses initial inputs and creates format of PerformanceID
    public void makePerformanceID() {
        String altStartTimeStr = startTimeStr.replaceAll("[^\\d]", ""); //Remove all symbols of startTime
        performanceID = theatreID + '-' + actID.substring(0,3) + '-' + altStartTimeStr; //Combine theatreID, sub ActID and startTime to make ID
    }

    //checkPerformanceFormat checks initial inputs of startTimeStr, and prices to see if they are valid
    public void checkPerformanceFormat() {
        //For loop that checks each char value (if contains '-' and 'T' which are all specific to ISO8601 format)
        for (int i = 0; i < startTimeStr.length(); i++) {
            if ((startTimeStr.charAt(4) != '-') || (startTimeStr.charAt(7) != '-') || (startTimeStr.charAt(13) != ':')
                    || (startTimeStr.charAt(10) != 'T')) {
                performanceID = "1"; //PerformanceID (1) error
                break;
            }
        }
        //For loop that checks if prices start with '$' char
        for (int i = 0; i < premiumPriceStr.length(); i++) {
            if (premiumPriceStr.charAt(0) != '$' || cheapSeatsStr.charAt(0) != '$') {
                performanceID = "2"; //PerformanceID (2) Error
                break;
            }
            //Also checks if price is a valid Number String!
            try {
                Integer checkPrem = Integer.parseInt(premiumPriceStr.substring(1));
                Integer checkCheap = Integer.parseInt(cheapSeatsStr.substring(1));
            } catch (NumberFormatException E) {
                performanceID = "2"; //PerformanceID (2) Error
                break;
            }
        }
    }

    //MakePerformanceIDList finds every performanceID for the given actID and outputs as list
    public void makePerformanceIDList() {
        //Using performanceIDsofAct which is concatenated [performanceID+actID]
        for (String row : performanceIDsOfAct) {
            int charPosAct = 0;
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == ',') {
                    charPosAct = i + 1;
                    break;
                }
            }
            String subActID = row.substring(charPosAct);

            if (subActID.equals(actID)) {
                performanceIDList.add(row.substring(0,charPosAct-1));
            }
        }
    }

}
