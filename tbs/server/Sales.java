package tbs.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sales {
    /* Sales class: uses initial inputs: performanceIDList, ticketIDList, seatingDimStrList, startTimeStrMap, premiumTicketMap,
       cheapTicketMap inputs to create a sales report list where each line consists of format:
       <performanceID> "\t" <start time> "\t" <number of tickets sold> "\t' <totals sales receipts for performance>
     */
    private List<String> performanceIDList;
    private List<String> ticketIDList;
    private List<String> seatingDimStrList;
    private Map<String,String> startTimeStrMap;
    private Map<String,String> premiumTicketMap;
    private Map<String,String> cheapTicketMap;

    private List<String> salesReportList = new ArrayList<>();

    //Constructor is used for makeSalesReportList method
    Sales(List<String> performanceIDList, List<String> ticketIDList, List<String> seatingDimStrList
            , Map<String,String> startTimeStrMap, Map<String,String> premiumTicketMap, Map<String,String> cheapTicketMap){
        this.performanceIDList = performanceIDList;
        this.ticketIDList = ticketIDList;
        this.seatingDimStrList = seatingDimStrList;
        this.startTimeStrMap = startTimeStrMap;
        this.premiumTicketMap = premiumTicketMap;
        this.cheapTicketMap = cheapTicketMap;
    }

    public List<String> getSalesReportList() {
        return salesReportList;
    }

    //MakeSalesReportList uses all mentioned inputs to meet specific format for a whole salesReport
    public void makeSalesReportList() {
        String startTimeStrKey = "";
        String premPriceStrKey = "";
        String cheapPriceStrKey = "";
        int seatingDimCount = 0;

        //Nested for loop that checks through every performanceID to find following list of values:
        for (String performanceID : performanceIDList) {
            int totalTickets = 0;
            int totalPrice = 0;
            //Check each startTimeStr to see if performanceID associated with startTimeStr in map and stores in String
            for (String startTimeStr : startTimeStrMap.keySet()) {
                if (startTimeStr.contains(performanceID)) {
                    startTimeStrKey = startTimeStrMap.get(startTimeStr);
                    break;
                }
            }
            //Check each premPriceStr to see if performanceID associated with premPriceStr in map and stores in String
            for (String premPriceStr : premiumTicketMap.keySet()) {
                if (premPriceStr.contains(performanceID)) {
                    premPriceStrKey = premiumTicketMap.get(premPriceStr);
                    break;
                }
            }
            //Check each cheapPriceStr to see if performanceID associated with cheapPriceStr in map and stores in String
            for (String cheapPriceStr : cheapTicketMap.keySet()) {
                if (cheapPriceStr.contains(performanceID)) {
                    cheapPriceStrKey = cheapTicketMap.get(cheapPriceStr);
                    break;
                }
            }
            //Check each ticketID to see if performanceID is associated with that ticketID in list and stores in String
            for (String ticketID : ticketIDList) {
                for (int i = 0; i < ticketID.length(); i++) {
                    if (ticketID.charAt(i) == ':') {
                        if (performanceID.equals(ticketID.substring(0,i))) {
                            int ticketRow = Integer.parseInt(ticketID.substring(i+1,i+2));
                            int premRow = Integer.parseInt(seatingDimStrList.get(seatingDimCount)) / 2; //Calculates premRow PRICE row POSITION
                            //If current ticketRow is less or equal to premRow, then apply current premPrice onto totalPrice
                            if (ticketRow <= premRow) {
                                totalPrice = totalPrice + Integer.parseInt(premPriceStrKey.substring(1));
                            }
                            //Otherwise apply cheapPrice onto totalPrice
                            else {
                                totalPrice = totalPrice + Integer.parseInt(cheapPriceStrKey.substring(1));
                            }
                            totalTickets++; //Ticket counts up for every ticket comparison
                        }
                    }
                }
            }
            seatingDimCount++; //Count seating dimensions for every performance (since sorted list
            salesReportList.add(performanceID + '\t' + startTimeStrKey + '\t' + totalTickets + '\t' + '$' + totalPrice);
        }
    }
}
