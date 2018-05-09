package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private List<String> artistList = new ArrayList<>();

    public Artist() {
    }

    public void setArtistList(List<String> artistList) {
        this.artistList = artistList;
    }

    public List<String> getArtistList() {
        return artistList;
    }

    public String makeArtistID(String artistName) {
        String ArtistID = artistName.replaceAll("\\s","").toUpperCase();

        if (ArtistID.equals("")) {
            return "ERROR: Input Artist is empty!";
        }
        if (artistList.size() != 0) {
            for (String artist : artistList) {
                if (ArtistID.equals(artist)) {
                    return "ERROR: Input Artist is already taken!";
                }
            }
        }
        artistList.add(ArtistID);
        return ArtistID;
    }
}
