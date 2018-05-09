package tbs.server;

import java.util.List;

public class TBSServerImpl implements TBSServer {
    private Artist artistIDObj = new Artist();

    // Creating theatreInfoList from CSV file and checking exception for if file is valid (exists) in directory.
    public String initialise(String path) {
        Theatre listObj = new Theatre();
        List<String> testList = listObj.makeScannerList(path);

        if (testList == null) {
            return "ERROR: File format incorrect or not found!";
        }
        return "";
    }

    public List<String> getTheatreIDs() {
        List<String> theatreList = Theatre.scannedList;
        Theatre theatreIDsObj = new Theatre();

        return theatreIDsObj.filterTheatreIDs(theatreList);
    }

    public List<String> getArtistIDs() {
        return null;
    }


    public List<String> getArtistNames() {
        return null;
    }

    public List<String> getActIDsForArtist(String artistID) {
        return null;
    }

    public List<String> getPerformanceIDsForAct(String actID) {
        return null;
    }

    public List<String> getTicketIDsForPerformance(String performanceID) {
        return null;
    }

    public String addArtist(String name) {
        String artistID = artistIDObj.makeArtistID(name);
        return artistID;
    }

    public String addAct(String title, String artistID, int minutesDuration) {
        return null;
    }

    public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
        return null;
    }

    public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
        return null;
    }

    public List<String> seatsAvailable(String performanceID) {
        return null;
    }

    public List<String> salesReport(String actID) {
        return null;
    }

    public List<String> dump() {
        return null;
    }
}
