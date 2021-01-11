package recipesforme.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    private String group_name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "groups")
    @JsonIgnore
    private Set<Word> words = new HashSet<>();

    protected Group() {}

    public Group(String groupName) {
        this.group_name = groupName;
    }

    public String getGroupName() {
        return group_name;
    }

    public void setGroupName(String groupName) {
        this.group_name = groupName;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }

    public void addWord(Word word){
        this.words.add(word);
    }

    public void addWords(Set<Word> words){
        this.words.addAll(words);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Group {");
        sb.append("group name = ").append(this.group_name).append(" }");
        return sb.toString();
    }
}
