import java.util.Scanner;
import java.util.StringTokenizer;

public class CSVtextParser {
    String lineFromFile;
    boolean firstLineRead;
    CSVreader reader;

    public CSVtextParser(){
        super();
        reader = new CSVreader();
     //TODO LEFT HERE
    }

    public FilmRecord getNextFilmRecordFromFile(){
        lineFromFile = reader.nextLine();

        return null;
    }
}
