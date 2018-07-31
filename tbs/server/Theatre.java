package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theatre {
    /* Theatre Class: Uses initial input scannedList, which is the scanned list from current CSV file.
       This class uses the scannedList to create 4 specified lists: theatreNames, theatreIDs, theatreSeatingDim and
       theatreFloorArea.
     */
    private List<String> theatreNames = new ArrayList<>();
    private List<String> theatreIDs = new ArrayList<>();
    private List<String> theatreSeatingDim = new ArrayList<>();
    private List<String> theatreFloorArea = new ArrayList<>();
    private List<String> scannedList;

    //Constructor used for makeTheatreLists
    Theatre (List<String> scannedList) {
        this.scannedList = scannedList;
    }

    public List<String> getTheatreNames() {
        return theatreNames;
    }

    public List<String> getClsTheatreIDs() {
        return theatreIDs;
    }

    public List<String> getTheatreSeatingDim() {
        return theatreSeatingDim;
    }

    public List<String> getTheatreFloorArea() {
        return theatreFloorArea;
    }

    //MakeTheatreLists method uses scannedList to produce specific lists from each section of the string which is split from '/t'
    //gets categorized
    public void makeTheatreLists() {
        Collections.sort(scannedList);
        //Go through scannedList in rows of string and in a nested for loop, find the char when position of char(i)
        //equals to '\t':
        for (String row : scannedList) {
            int count = 0;
            int startChar = 0;
            int endChar;

            //This for loop goes through to store valid information given from scannedList
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == '\t') {
                    endChar = i;
                    //If char at i equals to '\t' at count = 0: TheatreNames: thus store in theatreNames List
                    if (count == 0) {
                        if (!row.substring(startChar,endChar).equals("")) {
                            theatreNames.add(row.substring(startChar,endChar));
                            startChar = i + 1;
                            count++;
                        }
                    }
                    //If char at i equals to '\t' at count = 1: TheatreIDs: thus store in theatreIDs List
                    else if (count == 1) {
                        if (!row.substring(startChar,endChar).equals("")) {
                            theatreIDs.add(row.substring(startChar,endChar));
                            startChar = i + 1;
                            count++;
                        }
                    }
                    //If char at i equals to '\t' at count = 2: TheatreSeatingDimension: thus store in theatreSeatingDim List
                    else if (count == 2) {
                        if (!row.substring(startChar,endChar).equals("")) {
                            theatreSeatingDim.add(row.substring(startChar,endChar));
                            startChar = i + 1;
                            count++;
                        }
                    }
                }
                //If count = 3: TheatreFloorArea: thus store in theatreFloorArea List
                else if ((count == 3) && (i == row.length() - 1)) {
                    endChar = row.length() - 1;
                    if (!row.substring(startChar,endChar).equals("")) {
                        theatreFloorArea.add(row.substring(startChar,endChar));
                        count++;
                    }
                }
            }
        }
    }
}
