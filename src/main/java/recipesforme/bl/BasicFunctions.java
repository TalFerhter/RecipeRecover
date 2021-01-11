package recipesforme.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import recipesforme.bl.services.*;
import recipesforme.models.*;

import java.util.*;

@Component
public class BasicFunctions {

    @Autowired
    private WordService wordService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private ParagraphService paragraphService;

    @Autowired
    private PhraseService phraseService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private NeighborService neighborService;

    public BasicFunctions() {}

    /**
     * Find word current+next+before row
     * @param word
     * @return
     */
    /*public Set<Pair<Position, Set<Position>>> findWordContext(String word) {
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
                Pair<Position, Set<Position>> currPair = Pair.of(curPos, positionsAround);
                contextWords.add(currPair);
            });
        }
        return contextWords;
    }*/

    /**
     * Find the word position by the params
     * @param row
     * @param col
     * @param recipe_id
     * @param paragraph
     * @return
     * @throws Exception
     */
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

    /**
     * Create group with the set of words
     * @param groupName
     * @param words
     * @return
     */
    public Group createGroup(String groupName, Set<Word> words){
        Group newGroup = new Group(groupName);
        newGroup.setWords(words);
        this.groupService.save(newGroup);
        words.forEach(word -> word.addGroup(newGroup));
        this.wordService.saveAll(words);
        return newGroup;
    }

    /**
     * Return the position of the word
     * @param word
     * @return
     * @throws Exception
     */
    public Set<Position> findPositionsOfWord(String word) throws Exception {
        Optional<Word> originalWord = this.wordService.findById(word);
        if (!originalWord.isPresent())
            throw new Exception("Word doesn't exist");
        return originalWord.get().getPositions();
    }

    /**
     * Find the phrase accordingly to neighbors positions
     * @param text
     * @return
     * @throws Exception
     */
    public Phrase findPhrase(String text) throws Exception {
        Phrase phrase = new Phrase(text);
        String[] phraseParts = text.split(" ");
        Optional<Word> firstWord = this.wordService.findById(phraseParts[0]);
        if (!firstWord.isPresent() || firstWord.isEmpty()){
            throw new Exception("Phrase doesn't exists");
        }
        firstWord.get().getPositions().stream().forEach(position -> {
            List<Neighbor> firstNeighbors = this.neighborService.findNNeighbors(position.getPos_id(), phraseParts.length);
            boolean isPhrase = true;
            for (int i = 0; i < firstNeighbors.size() && isPhrase; i++) {
                Word currWord = (Word)firstNeighbors.get(i).getPosition().getWords().toArray()[0];
                if (!currWord.getWord().equals(phraseParts[i])){
                    isPhrase = false;
                }
            }
            if (isPhrase){
                phrase.addPosition(position);
            }
        });

        return phrase;
    }

    /**
     * Creating phrase using findPhrase
     * @param text
     * @return
     * @throws Exception
     */
    public Phrase createPhrase(String text) throws Exception {
        return this.phraseService.save(this.findPhrase(text));
    }
}
