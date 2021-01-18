package recipesforme.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.expr.NewArray;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import recipesforme.bl.BasicFunctions;
import recipesforme.bl.TextParser;
import recipesforme.bl.services.*;
import recipesforme.models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TextLoaderController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private WordService wordService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BasicFunctions basicFunctions;

    @Autowired
    private TextParser textParser;

    @Autowired
    private PhraseService phraseService;

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(TextLoaderController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/showWords")
    public String listWords(Model model) {
        model.addAttribute("words", this.wordService.findAll());

        return "showWords";
    }

    @GetMapping("/words")
    public List<Word> listWords(@RequestParam Optional<String> id,
                                @RequestParam Optional<String> recipeName,
                                @RequestParam Optional<Integer> row,
                                @RequestParam Optional<Integer> col) {
        List<Word> wordList = new ArrayList<>();
        if (!id.isEmpty()) {
            Optional<Word> dbWord = this.wordService.findById(id.get());
            if (!dbWord.isEmpty()) {
                wordList.add(dbWord.get());
            }
        } else if (!recipeName.isEmpty() && !row.isEmpty() && !col.isEmpty()) {
            Optional<UUID> rUUID = Optional.empty();
            Optional<Recipe> r = this.recipeService.findByRecipeName(recipeName.get());
            if (!r.isEmpty()) {
                rUUID = Optional.of(r.get().getRecipeId());
            }
            this.positionService.findByPositionDetails(row.get(), col.get(), rUUID, null)
                    .forEach(position -> wordList.addAll(position.getWords()));
        } else {
            wordList.addAll(this.wordService.findAll());
        }
        return wordList;
    }

    @GetMapping("/dataMining")
    public void countWordsInGroupsOfRecipe(@RequestParam String recipeName) {
        this.recipeService.countWordsInGroupsOfRecipe(recipeName);
    }

    @GetMapping("/getRecipes")
    public List<Object> getRecipe(@RequestParam Optional<String> recipeName,
                                  @RequestParam Optional<String> siteName,
                                  @RequestParam Optional<String> authorName,
                                  @RequestParam Optional<String> path,
                                  @RequestParam Optional<Integer> yieldMin,
                                  @RequestParam Optional<Integer> yieldMax,
                                  @RequestParam Optional<String> category,
                                  @RequestParam Optional<String> level,
                                  @RequestParam Optional<List<String>> words) {
        return this.recipeService.findByDetails(recipeName, siteName, authorName, path,
                yieldMin, yieldMax, category, level, words);
    }

    @GetMapping("/words/noGroup")
    public List<Word> listNoGroupWords() {
        return this.wordService.findAllWithoutGroup();
    }

    @GetMapping("/words/withGroup")
    public List<Word> listGroupWords(@RequestParam String groupName) {
        return this.wordService.findAllWithGroup(groupName);
    }

    @GetMapping("/words/context")
    public List<String> listRecipePositions(@RequestParam String recipeId) {
        List<String> recipePositions = new ArrayList<>();
        this.positionService.findRecipeText(recipeId).forEach(word -> recipePositions.add(word));
        return recipePositions;
    }

    @GetMapping("/words/statistics")
    public JsonNode listStatistics() {
        String responseString = "{ \"averageWordLength\":\"" + this.wordService.averageWordLength() + "\", " +
                "\"maxWordLength\":\"" + this.wordService.maxWordLength() + "\", " +
                "\"countWords\":\"" + this.wordService.countWords() + "\", " +
                "\"averageRowLength\":\"" + this.positionService.averageRowLength() + "\", " +
                "\"maxRowLength\":\"" + this.positionService.maxRowLength() + "\", " +
                "\"averageColLength\":\"" + this.positionService.averageColLength() + "\", " +
                "\"maxColLength\":\"" + this.positionService.maxColLength() + "\", " +
                "\"countPositions\":\"" + this.positionService.countPositions() + "\", " +
                "\"countRecipes\":\"" + this.recipeService.countRecipes() + "\"}";
        ObjectMapper responseMapper = new ObjectMapper();
        try {
            JsonNode response =  responseMapper.readTree(responseString);
            return response;
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    @PostMapping("/groups/addWords")
    public List<Word> addGroupWords(@RequestParam String groupName,
                                    @RequestParam List<String> wordList) {
        Optional<Group> group = this.groupService.findById(groupName);
        if (group.isEmpty())
            return null;
        List<Word> words = this.wordService.findAll(wordList);
        words.forEach(w -> {
            w.addGroup(group.get());
            group.get().addWord(w);
        });
        this.wordService.saveAll(words);
        this.groupService.save(group.get());
        return words;
    }

    @PostMapping("/groups/newGroup")
    public List<String> newGroup(@RequestParam String groupName,
                                 @RequestParam List<String> wordList) {
        if (!this.groupService.findById(groupName).isEmpty())
            return new ArrayList<>();
        Group group = new Group(groupName);
        List<Word> words = this.wordService.findAll(wordList);
        List<String> newWords = new ArrayList<>(wordList);
        words.forEach(w -> {
            group.addWord(w);
            newWords.remove(w.getWord());
        });
        newWords.forEach(w -> {
            group.addWord(new Word(w));
        });
        this.groupService.save(group);
        return newWords;
    }

    @GetMapping("/phrases/findPhrase")
    public List<Phrase> findPhrase(@RequestParam Optional<String> text) {
        List<Phrase> phrases = new ArrayList<>();
        try {
            if (text.isEmpty()) {
                return this.phraseService.findAll();
            }
            phrases.add(this.basicFunctions.findPhrase(text.get().replace('_', ' ')));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseEntity.notFound();
        }
        return phrases;
    }

    @PostMapping("/phrases/addPhrase")
    public Phrase addPhrase(@RequestParam Optional<String> text) {
        List<Phrase> phrases = findPhrase(text);
        phrases.forEach(phrase -> {
            phrase.getPositions().forEach(position -> position.setPhrase(phrase));
            this.positionService.saveAll(phrase.getPositions());
        });
        return phrases.get(0);
    }

    @GetMapping("/phrases")
    public List<Phrase> getAllPhrase() {
        return this.phraseService.findAll();
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/levels")
    public List<String> findLevels() {
        List<String> levelNames = new ArrayList<>();
        this.levelService.findAll().forEach(level -> levelNames.add(level.getLevelName()));
        return levelNames;
    }

    @GetMapping("/categories")
    public List<String> findCategories() {
        List<String> categoriesNames = new ArrayList<>();
        this.categoryService.findAll().forEach(category -> categoriesNames.add(category.getCategory()));
        return categoriesNames;
    }

    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestBody MultipartFile file,
                                   @RequestParam Optional<String> recipeName,
                                   @RequestParam Optional<String> siteName,
                                   @RequestParam Optional<String> authorName,
                                   @RequestParam Optional<String> path,
                                   @RequestParam Optional<String> date,
                                   @RequestParam Optional<Time> cookTime,
                                   @RequestParam Optional<Time> prepTime,
                                   @RequestParam Optional<Time> totalTime,
                                   @RequestParam Optional<Integer> yieldMin,
                                   @RequestParam Optional<Integer> yieldMax,
                                   @RequestParam Optional<String> category,
                                   @RequestParam Optional<String> level,
                                   RedirectAttributes redirectAttributes) {
        textParser.insertNewRecipe(file, recipeName, siteName, authorName, path, date, cookTime, prepTime,
                totalTime, yieldMin, yieldMax, category, level);

        return "Upload complete";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
