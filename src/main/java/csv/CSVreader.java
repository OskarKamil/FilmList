package csv;

import def.FilmRecord;
import def.SettingsManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVreader {
    private String filePath;
    private Scanner filmsFile;
    private String fileColumns = null;
    private StringBuilder lineFromFile;
    private Collection<String> valuesFromLine;
    private Iterator<String> iterator;

    public CSVreader(String newFilePath) {
        this.filePath = newFilePath;
        File file = new File(newFilePath);
        if (file.exists()) {
            try {
                filmsFile = new Scanner(new File(filePath), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("File not found.");
            }
        } else {
            String defaultNewFile = "Title\tOriginal Title\tType\tRelease year\tRating\tWatch date\tComments\t";
            try {
                filmsFile = new Scanner(defaultNewFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            filePath = "";
            SettingsManager.setLastPath("");
        }

        filmsFile.useDelimiter("[\n;]");

        this.readFirstLine();
        this.prepareValuesFromCurrentLine();
        System.out.print("This CSV file's structures is: ");
        while (iterator.hasNext()) System.out.print("[" + iterator.next() + "] ");
        System.out.println();
    }

    public void closeFile() {
        filmsFile.close();
    }

    public List<FilmRecord> getAllFilmsRecordsFromFile() {
        List<FilmRecord> listOfAllFilms = new ArrayList<>(3000);
        while (hasNextLine()) {
            FilmRecord newRecord = getNextFilmRecordFromFile();
            newRecord.setIdInList(listOfAllFilms.size() + 1);
            listOfAllFilms.add(newRecord);

        }
        return listOfAllFilms;
    }

    public String getFileColumns() {
        return fileColumns;
    }

    public String getFilePath() {
        return filePath;
    }

    private String getFileStructure() {
        fileColumns = nextLine();
        return fileColumns;
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

    public boolean hasNextLine() {
        return filmsFile.hasNextLine();
    }

    public String nextLine() {
        if (hasNextLine()) return filmsFile.nextLine();
        else return "";
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

    public String readFirstLine() {
        lineFromFile = new StringBuilder(getFileStructure());
        valuesFromLine = null;
        return lineFromFile.toString();
    }

    public String readNextLine() {
        lineFromFile = new StringBuilder(nextLine());
        valuesFromLine = null;
        return lineFromFile.toString();
    }
}
