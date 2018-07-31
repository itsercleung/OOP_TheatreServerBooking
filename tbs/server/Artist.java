package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Artist {
    /* Artist class: deals with initial inputs: name and artistIDList in order to make artistID, and also uses this output
       to compare whether ArtistID already exists or not.
     */
    private String name;
    private String artistID;
    private List<String> artistIDList = new ArrayList<>();
    private List<String> artistNameList = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistID() {
        return artistID;
    }

    public List<String> getArtistIDList() {
        Collections.sort(artistIDList);
        return artistIDList;
    }

    public List<String> getArtistNameList() {
        Collections.sort(artistNameList);
        return artistNameList;
    }

    //MakeArtistID method uses input name: To make an artistID and also compares if this artistID already exists or not
    public void makeArtistID() {
        Boolean flag = false;
        artistID =  name.replaceAll("\\s","").toUpperCase(); //ArtistID is made my capitalizing and no-space name input

        //If artistID (name input) is empty artistID(1) error
        if (artistID.equals("")) {
            artistID = "1";
            flag = true;
        }
        if (!artistID.equals("")) {
            if (artistIDList.size() != 0) {
                //For loop compares if current artistID already exists
                for (String artist : artistIDList) {
                    //If current artistID already exists artistID (2) error
                    if (artistID.equals(artist)) {
                        artistID = "2";
                        flag = true;
                    }
                }
            }
            //Else if everything isi successful, add artistID and name to separate lists
            if (!flag) {
                artistIDList.add(artistID);
                artistNameList.add(name.toLowerCase());
            }
        }
    }
}
