package CSV;

import def.FilmRecord;

import java.util.*;

public class CSVtextParser {
    private final CSVreader reader;
    private String lineFromFile;
    private boolean firstLineRead;

    public CSVtextParser() {
        super();
        reader = new CSVreader();
        lineFromFile = reader.nextLine();
        String[] valuesArray = (lineFromFile.split("[\t]"));
        Collection<String> valuesArrayList = new ArrayList<>(List.of(valuesArray));
        Iterator<String> iterator = valuesArrayList.iterator();
        System.out.print("This CSV file's structures is: ");
        while (iterator.hasNext())
            System.out.print("["+iterator.next()+"] ");
    }

    public String nextLine(){
        return reader.nextLine();
    }

    public FilmRecord getNextFilmRecordFromFile() {
        lineFromFile = reader.nextLine();

        return null;
    }
}
