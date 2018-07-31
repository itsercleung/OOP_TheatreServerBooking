package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Acts {
    /* Acts class: deals with initial inputs: minutesDuration, title, artistID and artistIDList to make ActID
       and also deals with making an ActID list for checking if ActID list exists or not.
     */
    private int minutesDuration;
    private String title;
    private String artistID;
    private String actID;
    private List<String> artistIDList;

    private List<String> actIDsOfArtist;
    private List<String> actIDList = new ArrayList<>();

    //Constructor for making ActID
    Acts(String title, String artistID, int minutesDuration, List<String> artistIDList) {
        this.title = title;
        this.artistID = artistID;
        this.minutesDuration = minutesDuration;
        this.artistIDList = artistIDList;
    }

    //Constructor for making ActIDList
    Acts(String artistID, List<String> actIDsOfArtist) {
        this.artistID = artistID;
        this.actIDsOfArtist = actIDsOfArtist;
    }

    public String getActID() {
        return actID;
    }

    public List<String> getActIDList() {
        Collections.sort(actIDList);
        return actIDList;
    }

    //MakeActID class: Uses title, artistID, minutesDuration and artistIDList to compare if artistID exists or not.
    public void makeActID() {
        boolean flag = true;
        actID = title.replaceAll("\\s","").toUpperCase();

        //If title is empty actID (1) error
        if (title.equals("")) {
            actID = "1";
        }
        //If minutesDuration invalid actID (2) error
        else if (minutesDuration <= 0) {
            actID = "2";
        }
        else {
            //For loop compares if artistID exists from artistIDList: If artistID doesn't exist actID (3) error
            for (String artist : artistIDList) {
                if (artistID.equals(artist)) {
                    flag = false;
                }
            }
            if (flag) {
                actID = "3";
            }
        }
    }

    //MakeActIDList uses actIDsOfArtist [ActID,ArtistID] and artistID to find all actIDs corresponding to specific artistID and makes list out of it
    public void makeActIDList() {
        //For loop finds automated ',' char
        for (String row : actIDsOfArtist) {
            int charPosArtist = 0;
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == ',') {
                    charPosArtist = i + 1;
                    break;
                }
            }
            //Given the ',' char position, and compares ArtistID to actIDsOFArtist list to find ActID associated
            String subArtistID = row.substring(charPosArtist);
            if (subArtistID.equals(artistID)) {
                actIDList.add(row.substring(0,charPosArtist-1));
            }
        }
    }
}
