package csv;

import def.FilmRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CSVtextParser {
    private final CSVreader reader;
    private StringBuilder lineFromFile;
    private Collection<String> valuesFromLine;
    private Iterator<String> iterator;
    private boolean firstLineRead;

    public CSVtextParser() {
        super();
        reader = new CSVreader();
        this.readNextLine();
        this.prepareValuesFromCurrentLine();
        System.out.print("This CSV file's structures is: ");
        while (iterator.hasNext()) System.out.print("[" + iterator.next() + "] ");
        System.out.println();
    }

    public String readNextLine() {
        lineFromFile = new StringBuilder(reader.nextLine());
        valuesFromLine = null;
        return lineFromFile.toString();
    }

    public String nextValueFromLine() {
        if (valuesFromLine == null) {
            String[] valuesArray = (lineFromFile.toString().split("[\t]"));
            valuesFromLine = new ArrayList<>(List.of(valuesArray));
            iterator = valuesFromLine.iterator();
        }
        return iterator.next();
    }

    public void prepareValuesFromCurrentLine() {
        String[] valuesArray = (lineFromFile.toString().split("[\t]"));
        valuesFromLine = new ArrayList<>(List.of(valuesArray));
        iterator = valuesFromLine.iterator();
    }

    public FilmRecord getNextFilmRecordFromFile() {
        readNextLine();
        prepareValuesFromCurrentLine();
        FilmRecord record = new FilmRecord();
        if (iterator.hasNext()) record.setEnglishTitle(iterator.next());
        if (iterator.hasNext()) record.setOriginalTitle(iterator.next());
        if (iterator.hasNext()) record.setType(iterator.next());
        if (iterator.hasNext()) record.setReleaseYear((iterator.next()));
        if (iterator.hasNext()) record.setRating((iterator.next()));
        if (iterator.hasNext()) record.setWatchDate(iterator.next());
        if (iterator.hasNext()) record.setComments(iterator.next());
        return record;
    }

    public List<FilmRecord> getAllFilmsRecordsFromFile() {
        List<FilmRecord> listOfAllFilms = new ArrayList<>(3000);
        while (reader.hasNextLine()) {
            listOfAllFilms.add(getNextFilmRecordFromFile());
        }
        return listOfAllFilms;
    }
}
