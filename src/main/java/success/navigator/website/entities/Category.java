package success.navigator.website.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Category implements Comparable<Category> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String name;
    private String description;
    private Integer categoryOrder;

    @ManyToMany
    private List<Task> tasks = new ArrayList<>();

    @Override
    public int compareTo(Category o) {
        return this.categoryOrder - o.categoryOrder;
    }
}
