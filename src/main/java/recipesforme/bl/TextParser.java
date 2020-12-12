package recipesforme.bl;

import org.apache.commons.validator.GenericValidator;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;
import recipesforme.models.*;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextParser {

    public void parseRecipe(MultipartFile path) {
        Recipe recipe = new Recipe();
        int row = 1;
        int col;
        String[] rowWords;
        ArrayList<Pair<Word, Position>> recipeWords;

        try {
            InputStream inputStream = path.getInputStream();
            List<String> allLines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.toList());

            allLines.forEach(line -> {
                if (GenericValidator.isDate(line, "dd/mm/yyyy", true)){
                    recipe.setDate(Date.valueOf(line));
                } else if (GenericValidator.isUrl(line)){
                    recipe.setPath(line);
                } else if (line.contains("|")){
                    String[] title = line.split("\\|");
                    recipe.setRecipeName(title[0]);
                    Author author = new Author(title[1]);
                    recipe.setAuthors(author);
                    recipe.setSiteName(title[2]);
                } else if (line.contains("Level")) {
                    Level level = new Level(line);

                } else if (line.contains("Total")) {
                    //recipe.setTotalTime();
                } else if (line.contains("Active") || line.contains("Prep")) {

                } else if (line.contains("Cook")) {

                } else if (line.contains("Yield")) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseParagraph() {
    }

    public void parseWord() {
    }

    public Date parseDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        Date date;
        try {
            date = (Date) dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public void createPosition() {
    }

}
