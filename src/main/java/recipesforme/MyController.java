package recipesforme;

import recipesforme.models.Word;
import recipesforme.services.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private IWordService wordService;

    @GetMapping("/showWords")
    public String findWords(Model model){

        var words = (List<Word>) wordService.findAll();

        model.addAttribute("words", words);

        return "showWords";
    }
}
