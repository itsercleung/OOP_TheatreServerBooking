package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tickets {
    /* Tickets class: uses initial inputs performanceID, row number and seat number to make a performanceID.
       Also uses TicketID output to make TicketIDList from an additional TicketIDPerformanceList.
     */
    private String ticketID;
    private String performanceID;
    private int rowNumber;
    private int seatNumber;

    private List<String> ticketIDList = new ArrayList<>();
    private List<String> ticketIDPerformanceList = new ArrayList<>();

    //Constructor used for MakeTicketID
    Tickets(String performanceID, int rowNumber, int seatNumber) {
        this.performanceID = performanceID;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    //Constructor used for MakeTicketIDPerformanceList
    Tickets(List<String> ticketIDList, String performanceID) {
        this.ticketIDList = ticketIDList;
        this.performanceID = performanceID;
    }

    public String getTicketID() {
        return ticketID;
    }

    public List<String> getTicketIDPerformanceList() {
        Collections.sort(ticketIDPerformanceList);
        return ticketIDPerformanceList;
    }

    //MakeTicketID method combines inputs: performanceID + rowNum and seatNum. This ensures every ticketID is unique
    public void makeTicketID() {
        ticketID = performanceID + ":" + rowNumber + seatNumber;
    }

    //MakeTicketIDPerformanceList method finds tickets for every performance associated with it: Compares PID with TID with
    //ticketIDPerformanceList and outputs list of tickets for each performance
    public void makeTicketIDPerformanceList() {
        String tempPerformanceID = "";
        //For every ticket, substring performanceID in between 0 and ':' of ticketID format
        for (String row : ticketIDList) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == ':') {
                    tempPerformanceID = row.substring(0,i);
                    break;
                }
            }
            //If substring tempPerformanceID equals current input performanceID, add ticket row to list
            if (tempPerformanceID.equals(performanceID)) {
                ticketIDPerformanceList.add(row);
            }
        }
    }

}
