package tbs.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Theatre {
    public static List<String> scannedList = new ArrayList<>();

    Theatre() {
    }

    //Initialising List scannedList from given file path using Scanner in order to find theatre IDs, seating .etc
    public List<String> makeScannerList(String path) {
        File file = new File(path);
        Scanner inputStream;
        //Check if file exists from given path name
        try {
            inputStream = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            return null;
        }
        //If file is successful add all lines from file into ScannerList
        while (inputStream.hasNext()) {
            scannedList.add(inputStream.nextLine());
        }
        inputStream.close();
        return scannedList;
    }

    //Filter given lines of theatre information from scannedList and filter out only theatreIDs into a list<string>
    //and arrange alphabetically (and numerically).
    public List<String> filterTheatreIDs(List<String> theatreInfoList) {
        List<String> theatreIDs = new ArrayList<>();

        for (String row : theatreInfoList) {
            int charPos = 0;
            int count = 0;
            //Get initial position where ID begins (right after '\t')
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == '\t') {
                    count++;
                }
                if (count == 1) {
                    charPos = i + 1;
                    break;
                }
            }
            //Concatenate with existing characters that represent theatreID for current row in theatreInfoList
            String id  = ""; // "T__"
            char c = row.charAt(charPos);
            while (c != '\t')  {
                id = id + c;
                charPos++;
                c = row.charAt(charPos);
            }
            theatreIDs.add(id);
        }
        Collections.sort(theatreIDs);
        return theatreIDs;
    }

}
