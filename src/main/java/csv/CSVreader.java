package csv;

import def.FilmRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVreader {
    private Scanner filmsFile;
    private String fileColumns = null;
    private StringBuilder lineFromFile;
    private Collection<String> valuesFromLine;
    private Iterator<String> iterator;

    public CSVreader() {
        try {
            // URL filmsCSVfile = getClass().getClassLoader().getResource("txt/MyFilms.csv");
            // assert filmsCSVfile != null;
            filmsFile = new Scanner(new File("src/main/resources/txt/MyFilms.csv"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("File not found.");
        }
        filmsFile.useDelimiter("[\n;]");

        this.readFirstLine();
        this.prepareValuesFromCurrentLine();
        System.out.print("This CSV file's structures is: ");
        while (iterator.hasNext()) System.out.print("[" + iterator.next() + "] ");
        System.out.println();
    }

    public String getFileColumns() {
        return fileColumns;
    }

    public void closeFile() {
        filmsFile.close();
    }

    public void prepareValuesFromCurrentLine() {
        String[] valuesArray = (lineFromFile.toString().split("[\t]"));
        valuesFromLine = new ArrayList<>(List.of(valuesArray));
        iterator = valuesFromLine.iterator();
    }

    public String readFirstLine() {
        lineFromFile = new StringBuilder(getFileStructure());
        valuesFromLine = null;
        return lineFromFile.toString();
    }

    public String nextLine() {
        if (hasNextLine()) return filmsFile.nextLine();
        else return "";
    }

    private String getFileStructure() {
        fileColumns = nextLine();
        return fileColumns;
    }

    public boolean hasNextLine() {
        return filmsFile.hasNextLine();
    }

    public String readNextLine() {
        lineFromFile = new StringBuilder(nextLine());
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
        while (hasNextLine()) {
            FilmRecord newRecord = getNextFilmRecordFromFile();
            newRecord.setIdInList(listOfAllFilms.size()+1);
            listOfAllFilms.add(newRecord);

        }
        return listOfAllFilms;
    }
}
