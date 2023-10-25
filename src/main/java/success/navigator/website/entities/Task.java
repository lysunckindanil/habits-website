package success.navigator.website.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String name;
    private String description;
    @Column(length = 1024)
    private String image;
    private Integer points;

}
