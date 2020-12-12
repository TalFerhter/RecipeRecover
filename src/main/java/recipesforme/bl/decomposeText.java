package recipesforme.bl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class decomposeText {

    public void decomposeRecipe(File path) {
        try {
            Scanner myReader = new Scanner(path);
            String title = myReader.nextLine();
            String author = myReader.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void decomposeParagraph() {}
    public void decomposeWord() {}
    public void createPosition() {}

}
