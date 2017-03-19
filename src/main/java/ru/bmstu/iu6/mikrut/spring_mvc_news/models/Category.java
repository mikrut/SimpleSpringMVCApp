package ru.bmstu.iu6.mikrut.spring_mvc_news.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mikrut on 17.03.17.
 */

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 5, max = 20)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<News> news = new HashSet<News>();

    public String getName() {
        return name;
    }
}
