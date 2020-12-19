package recipesforme.bl;

import org.apache.commons.validator.GenericValidator;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;
import recipesforme.models.*;
import recipesforme.services.ParagraphService;
import recipesforme.services.PositionService;
import recipesforme.services.WordService;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextParser {

    // Main recipe
    Recipe recipe;
    List<Word> wordsList;
    List<Position> positionList;

    public void parseRecipe(MultipartFile path) {
        wordsList = new ArrayList<>();
        positionList = new ArrayList<>();
        recipe = new Recipe();
        Paragraph currParagraph = new Paragraph();

        try {
            InputStream inputStream = path.getInputStream();
            List<String> allLines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.toList());
            String[] lines = (String[]) allLines.stream().filter(s -> !s.isEmpty()).toArray();
            for (int row = 0; row < lines.length; row++) {
                Paragraph temp = isParagraph(lines[row]);
                if (temp != null) {
                    currParagraph = temp;
                }
                boolean isDate = GenericValidator.isDate(lines[row], "dd/m/yyyy", true);
                if (isDate) {
                    setDate(lines[row]);
                } else if (GenericValidator.isUrl(lines[row])) {
                    recipe.setPath(lines[row]);
                } else if (lines[row].contains("|")) {
                    setTitleDetails(lines[row]);
                } else if (lines[row].contains("Level")) {
                    Level level = new Level(lines[row]);
                    recipe.setLevels(level);
                } else if (lines[row].contains("Active") || lines[row].contains("Prep")
                        || lines[row].contains("Cook") || lines[row].contains("Total")) {
                    setTime(lines[row]);
                } else if (lines[row].contains("Yield")) {
                    setYields(lines[row]);
                } //else if (line.contains(paragraph)

                // Take care of the words
                setLineWords(lines[row], currParagraph, row);
            }

            new WordService().saveAll(wordsList);
            new PositionService().saveAll(positionList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Paragraph isParagraph(String line) {
        List<Paragraph> paragraphs = new ParagraphService().findByTitle(line.split(":")[0]);
        return (paragraphs.size()>0?paragraphs.get(0):null);
    }

    public void setLineWords(String line, Paragraph paragraph, int row) {
        String[] words = (String[]) Arrays.stream(line.split("\\s")).filter(s -> !s.isEmpty()).toArray();
        for (int col = 1; col <= words.length; col++) {
            Word newWord = new Word(words[col]);
            wordsList.add(newWord);
            Position newPos = new Position(row, col, paragraph);
            positionList.add(newPos);
        }
    }

    private Date parseDate(String strDate) {
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

    private void setDate(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            recipe.setDate(formatter.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setTime(String strTime) {

        Pattern pattern = Pattern.compile("(?:(\\w*)): (?:(?:(\\d*) hr)?)(?: ?)(?:(?:(\\d*) min)?)");
        Matcher matcher = pattern.matcher(strTime);
        matcher.matches();
        String timeType = matcher.group(1);
        String timeString = (matcher.groupCount() > 3) ? (matcher.group(3) + ":" + matcher.group(6))
                : ("00:" + matcher.group(3)) + ":00";
        Time time = Time.valueOf(timeString);
        switch (timeType) {
            case "Total":
                recipe.setTotalTime(time);
                break;

            case "Cook":
                recipe.setCookTime(time);
                break;

            case "Prep":

            case "Active":
                recipe.setPrepTime(time);
                break;
        }
    }

    private void setTitleDetails(String title) {
        String[] titleParts = title.split(" \\| ");
        recipe.setRecipeName(titleParts[0]);
        Author author = new Author(titleParts[1]);
        recipe.setAuthors(author);
        recipe.setSiteName(titleParts[2]);
    }

    private void setYields(String line) {
        Pattern pattern = Pattern.compile("(Yield:) (((?:(\\d*) (?:to) )?)(?:(\\d*)) servings)");
        Matcher matcher = pattern.matcher(line);
        matcher.matches();
        recipe.setMinYield(Integer.parseInt(matcher.group(2)));
        if (matcher.groupCount() > 3) {
            recipe.setMaxYield(Integer.parseInt(matcher.group(3)));
        }
    }
}
