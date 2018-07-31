package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Seats {
    /* Seats class: uses initial inputs: performanceID, theatreIDList and theatreSeatingDim to create seatingDimStr for
       specific performanceID and then creates an overall list of the current available seats for the theatre - (row,seat).
       It also intakes ticketIDPerformanceList to override the current available seats and makes them unavailable (by removing them).
     */
    private String performanceID;
    private String seatingDimStr;
    private List<String> theatreIDList;
    private List<String> theatreSeatingDim;
    private List<String> availableSeatList = new ArrayList<>();

    private List<String> ticketIDPerformanceList;
    private List<String> takenSeatList = new ArrayList<>();

    //Constructor used for makeAvailableSeats and makeSeatingDim methods
    Seats(String performanceID, List<String> theatreIDList, List<String> theatreSeatingDim) {
        this.performanceID = performanceID;
        this.theatreIDList = theatreIDList;
        this.theatreSeatingDim = theatreSeatingDim;
    }

    //Constructor used for makeTakenSeats class
    Seats(String performanceID, List<String> ticketIDPerformanceList, List<String> theatreIDList, List<String> theatreSeatingDim) {
        this.performanceID = performanceID;
        this.ticketIDPerformanceList = ticketIDPerformanceList;
        this.theatreIDList = theatreIDList;
        this.theatreSeatingDim = theatreSeatingDim;
    }

    public List<String> getAvailableSeatList() {
        return availableSeatList;
    }

    public String getSeatingDimStr() {
        return seatingDimStr;
    }

    //MakeAvailableSeats method makes a list of Seats with given SeatingDimStr in (row,seat) format. This takes into
    //account the seats that are available and not available (if ticket for seat has been purchased)
    public void makeAvailableSeats() {
        //Nested for loop creates specified seating list
        for (int rowNumber = 1; rowNumber <= Integer.parseInt(seatingDimStr); rowNumber++) {
            for (int seatNumber = 1; seatNumber <= Integer.parseInt(seatingDimStr); seatNumber++) {
                availableSeatList.add(String.valueOf(rowNumber) + '\t' + seatNumber);
            }
        }

        //Nested for loop finds takenSeat and removes it
        for (String seat : takenSeatList) {
            int count = 0;
            for (String avaSeat : availableSeatList) {
                if (seat.equals(avaSeat)) {
                    availableSeatList.remove(count);
                    break;
                }
                count++;
            }
        }
    }

    //MakeseatingDimStr method: finds the specific Seating dimension from theatreList input and compares for given performanceID
    public void makeSeatingDimStr() {
        int count = 0;
        String theatreID = "";
        //Get theatreID from performanceID: In order to know which theatre and thus which theatre Seating dimension it is.
        for (int i = 0; i < performanceID.length(); i++) {
            if (performanceID.charAt(i) == '-') {
                theatreID = performanceID.substring(0,i);
                break;
            }
        }
        if (theatreID.equals("")) {
            seatingDimStr = "0"; //Else seatingDimStr error (0)
        }
        //Find seatingDimStr from theatreID found from above and use that to find specific seating dimension value
        for (String row : theatreIDList) {
            if (row.equals(theatreID)) {
                seatingDimStr = theatreSeatingDim.get(count);
            }
            count++;
        }
    }

    //MakeTakenSeats method makes a list of taken seats from given ticketID list, where it specifies row and seat
    public void makeTakenSeats() {
        for (String row : ticketIDPerformanceList) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == ':') {
                    takenSeatList.add(row.substring(i+1,i+2) + '\t' + row.substring(i+2,i+3));
                    break;
                }
            }
        }
    }
}
