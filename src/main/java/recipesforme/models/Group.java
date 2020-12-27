package recipesforme.models;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    private UUID group_id;

    private String title;

    @ManyToMany(mappedBy = "groups")
    private Set<Word> words = new HashSet<>();

    protected Group() {}

    public Group(String title) {
        this.group_id = UUID.randomUUID();
        this.title = title;
    }

    public UUID getGroup_id() {
        return group_id;
    }

    public void setGroup_id(UUID group_id) {
        this.group_id = group_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return Objects.equals(this.group_id, other.group_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Group{");
        sb.append("group_id = ").append(group_id);
        sb.append(", title = '").append(title).append("'}");
        return sb.toString();
    }
}
