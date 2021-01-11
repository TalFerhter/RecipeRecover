package recipesforme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import recipesforme.models.Group;
import recipesforme.models.Phrase;
import recipesforme.models.Recipe;
import recipesforme.models.Word;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TextLoaderController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private WordService wordService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private GroupService groupService;

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
    public List<Word> listWords(@RequestParam Optional<String> id) {
        if (!id.isEmpty()){
            List<Word> wordList = new ArrayList<>();
            Optional<Word> dbWord = this.wordService.findById(id.get());
            if(!dbWord.isEmpty()){
                wordList.add(dbWord.get());
            }
            return wordList;
        }
        return this.wordService.findAll();
    }

    @GetMapping("/recipes")
    public List<Recipe> getRecipe(@RequestParam Optional<String> recipeName,
                                  @RequestParam Optional<List<String>> words) {
        if (recipeName.isEmpty() && words.isEmpty()){
            return this.recipeService.findAll();
        } else if (recipeName.isEmpty()){
            return this.wordService.findAllRecipes(words.get());
        } else if (words.isEmpty()){
            return this.recipeService.findByPartOfRecipeName(recipeName.get());
        }
        return this.wordService.findAllWithRecipe(words.get(), recipeName.get());
    }

    @GetMapping("/words/noGroup")
    public List<Word> listNoGroupWords() {
        return this.wordService.findAllWithoutGroup();
    }

    @GetMapping("/words/withGroup")
    public List<Word> listGroupWords(@RequestParam String groupName) {
        return this.wordService.findAllWithGroup(groupName);
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

    @GetMapping("/phrases/findPhrase")
    public List<Phrase> findPhrase(@RequestParam Optional<String> text){
        List<Phrase> phrases = new ArrayList<>();
        try {
            if(text.isEmpty()){
              return this.phraseService.findAll();
            }
            phrases.add(this.basicFunctions.findPhrase(text.get().replace('_',' ')));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseEntity.notFound();
        }
        return phrases;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
