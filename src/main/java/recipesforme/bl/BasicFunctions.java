package recipesforme.bl;

import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.apache.commons.*;
import recipesforme.bl.services.*;
import recipesforme.models.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BasicFunctions {

    @Autowired
    private WordService wordService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private ParagraphService paragraphService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private LevelService levelService;

    public Set<Pair<Position, Set<Position>>> findWordContext(String word) {
        Set<Pair<Position, Set<Position>>> contextWords = new HashSet<>();
        Optional<Word> foundedWord = wordService.findById(word);
        if (foundedWord.isPresent()) {
            Set<Position> positions = foundedWord.get().getPositions();
            positions.forEach(curPos -> {
                Set<Position> positionsAround = new HashSet<>();
                this.positionService.findRowBefore(curPos.getPos_id(), curPos.getRecipe().getRecipeId(),
                        curPos.getRow()).forEach(positionsAround::add);
                this.positionService.findRow(curPos.getPos_id(), curPos.getRecipe().getRecipeId(),
                        curPos.getRow()).forEach(positionsAround::add);
                Set<Position> positionsAfter = new HashSet<>();
                this.positionService.findRowAfter(curPos.getPos_id(), curPos.getRecipe().getRecipeId(),
                        curPos.getRow()).forEach(positionsAround::add);
                Pair<Position, Set<Position>> currPair = new Pair<>(curPos, positionsAround);
                contextWords.add(currPair);
            });
        }
        return contextWords;
    }

    public Set<Word> findWordByPosition(int row, int col, Optional<UUID> recipe_id,
                                   Optional<UUID> paragraph) throws Exception {
        Set<Word> wordPositons = new HashSet<>();
        if (recipe_id.isPresent()) {
            Optional<Recipe> recipe = this.recipeService.findById(recipe_id.get());
            if (!recipe.isPresent()){
                throw new Exception("Recipe doesn't exists");
            }
            this.positionService.findByPositionDetails(row, col, recipe_id, paragraph)
                    .forEach(position -> wordPositons.addAll(position.getWords()));
        }
        return wordPositons;
    }

    public Group createGroup(String groupName, Set<Word> words){
        Group newGroup = new Group(groupName);
        newGroup.setWords(words);
        this.groupService.save(newGroup);
        words.forEach(word -> word.addGroup(newGroup));
        this.wordService.saveAll(words);
        return newGroup;
    }

    public Set<Position> findPositionsOfWord(String word) throws Exception {
        Optional<Word> originalWord = this.wordService.findById(word);
        if (!originalWord.isPresent())
            throw new Exception("Word doesn't exist");
        return originalWord.get().getPositions();
    }

    // TODO
    public Phrase findPhrase(String text) throws Exception {
        Phrase phrase = new Phrase(text);
        String[] phraseParts = text.split(" ");
        List<Position> neighbors = new ArrayList<>();
        Optional<Word> firstWord = this.wordService.findById(phraseParts[0]);
        if (!firstWord.isPresent() || firstWord.isEmpty()){
            throw new Exception("Phrase doesn't exists");
        }
        List<Position> optionalNeighbors = new ArrayList<>(firstWord.get().getPositions());
        int index = 1;
        while (index < phraseParts.length) {
            neighbors.clear();
            optionalNeighbors.forEach(position -> {
                neighbors.addAll(findPhrasePartNeighbors(position, phraseParts[index]));
            });
            optionalNeighbors.clear();
            optionalNeighbors.addAll(neighbors);

        }
        return phrase;
    }

    private List<Position> findPhrasePartNeighbors(Position position, String nextWord){
        List<Position> neighbors = new ArrayList<>();
        List<Position> optionalNeighbors = new ArrayList<>();
        this.positionService.findByPositionDetails(position.getRow(), position.getCol()+1,
                Optional.of(position.getRecipe().getRecipeId()), Optional.of(position.getParagraph().getParagraph_id()))
                .forEach(optionalNeighbors::add);
        optionalNeighbors.stream().forEach(optionalNeighbor -> {
            if (optionalNeighbor.getWords().stream().findFirst().get().getWord() == nextWord){
                neighbors.add(optionalNeighbor);
            }
        });
        return neighbors;
    }

    private List<Position> findNextOptionalWordNeighbors(Word word){
        List<Position> optionalNeighbors = new ArrayList<>();
        word.getPositions().forEach(position -> {
            this.positionService.findByPositionDetails(position.getRow(), position.getCol()+1,
                    Optional.of(position.getRecipe().getRecipeId()), Optional.empty()).forEach(optionalNeighbors::add);
            this.positionService.findByPositionDetails(position.getRow()+1, 1,
                    Optional.of(position.getRecipe().getRecipeId()), Optional.empty()).forEach(optionalNeighbors::add);
        });
        return optionalNeighbors;
    }
}
