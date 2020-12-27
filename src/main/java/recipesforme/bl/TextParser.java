package recipesforme.bl;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import recipesforme.bl.services.*;
import recipesforme.models.*;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TextParser {

    @Autowired
    private WordService wordService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private ParagraphService paragraphService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private LevelService levelService;

    // Main recipe
    Recipe recipe;
    List<Word> wordsList;
    List<Position> positionList;
    List<Paragraph> paragraphList;

    public void parseRecipe(MultipartFile path) {

        this.wordsList = new ArrayList<>();
        this.positionList = new ArrayList<>();
        this.recipe = new Recipe();
        this.paragraphList = new ArrayList<>();
        this.paragraphList = this.paragraphService.findAll();
        Paragraph currParagraph;
        if (this.paragraphList.isEmpty()){
            currParagraph = new Paragraph();
            this.paragraphService.save(currParagraph);
        } else {
            currParagraph = this.paragraphList.get(0);
        }

        try {
            InputStream inputStream = path.getInputStream();
            List<String> allLines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.toList());
            String[] lines = allLines.stream().filter(s -> !s.isEmpty()).toArray(String[]::new);
            for (int row = 0; row < lines.length; row++) {
                Paragraph temp = isParagraph(lines[row]);
                if (temp != null) {
                    currParagraph = temp;
                }
                boolean isDate = GenericValidator.isDate(lines[row], "dd/m/yyyy", true);
                if (isDate) {
                    setDate(lines[row]);
                } else if (GenericValidator.isUrl(lines[row])) {
                    this.recipe.setPath(lines[row]);
                } else if (lines[row].contains("|")) {
                    setTitleDetails(lines[row]);
                } else if (lines[row].contains("Level")) {
                    setLevel(lines[row].split(": ")[1]);
                } else if (lines[row].contains("Active:") || lines[row].contains("Prep:")
                        || lines[row].contains("Cook:") || lines[row].contains("Total:")) {
                    setTime(lines[row]);
                } else if (lines[row].contains("Yield:")) {
                    setYields(lines[row]);
                } //else if (line.contains(paragraph)

                // Take care of the words
                if (!isDate && !GenericValidator.isUrl(lines[row])) {
                    setLineWords(lines[row], currParagraph, row);
                }
            }

            this.wordService.saveAll(this.wordsList);
            this.positionService.saveAll(this.positionList);
            this.recipeService.save(this.recipe);

        } catch (Exception e) {
            e.printStackTrace();
            this.recipeService.delete(this.recipe);
        }
    }

    private void setLevel(String line) {
        Level level = new Level(line);
        Optional<Level> temp = levelService.findByLevelName(line);
        if (!temp.isPresent()) {
            this.levelService.save(level);
        } else {
            level = temp.get();
        }
        this.recipe.setLevels(level);
    }

    private Paragraph isParagraph(String line) {
        int index = this.paragraphList.indexOf(line.split(":")[0]);
        return ((index >= 0) ? this.paragraphList.get(index) : null);
    }

    public void setLineWords(String line, Paragraph paragraph, int row) {
        String[] words = Arrays.stream(line.replaceAll("\\p{Punct}", "")
                .split("\\s")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        for (int col = 0; col < words.length; col++) {
            Word newWord = new Word(words[col]);
            Position newPos = new Position(row, col+1, this.recipe, paragraph);
            newWord.addPosition(newPos);
            this.wordsList.add(newWord);
            newPos.setWord(newWord);
            this.positionList.add(newPos);
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

    private void setDate(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            this.recipe.setDate(formatter.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setTime(String strTime) {

        Pattern pattern = Pattern.compile("(?:(\\w*)): (?:(?:(\\d*) hr)?)(?: ?)(?:(?:(\\d*) min)?)");
        Matcher matcher = pattern.matcher(strTime);
        matcher.matches();
        String timeType = matcher.group(1);
        String hr = (matcher.group(2) == null) ? "00" : matcher.group(2);
        String min = (matcher.group(3) == null) ? "00" : matcher.group(3);
        Time time = Time.valueOf(hr + ":" + min + ":00");
        switch (timeType) {
            case "Total":
                this.recipe.setTotalTime(time);
                break;

            case "Cook":
                this.recipe.setCookTime(time);
                break;

            case "Prep":

            case "Active":
                this.recipe.setPrepTime(time);
                break;
        }
    }

    @Transactional
    private void setTitleDetails(String title) {
        String[] titleParts = title.split(" \\| ");
        this.recipe.setRecipeName(titleParts[0]);
        this.setAuthor(titleParts[1]);
        this.recipe.setSiteName(titleParts[2]);
    }

    private void setAuthor(String author){
        Optional<Author> a = this.authorService.findByAuthorName(author);
        if (!a.isPresent()) {
            this.recipe.setAuthors(this.authorService.save(new Author(author)));
        } else {
            this.recipe.setAuthors(a.get());
        }
    }

    private void setYields(String line) {
        Pattern pattern = Pattern.compile("(Yield:) (((?:(\\d*) (?:to) )?)(?:(\\d*)) servings)");
        Matcher matcher = pattern.matcher(line);
        matcher.matches();
        this.recipe.setMinYield(Integer.parseInt(matcher.group(5)));
        if (matcher.groupCount() > 6) {
            this.recipe.setMaxYield(Integer.parseInt(matcher.group(8)));
        }
    }
}
