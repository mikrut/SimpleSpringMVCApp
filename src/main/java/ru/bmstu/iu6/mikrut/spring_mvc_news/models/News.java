package ru.bmstu.iu6.mikrut.spring_mvc_news.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by mikrut on 17.03.17.
 */

@Entity
@Table(name="news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=5, max=100)
    @Column(name = "name", nullable = false)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publication_date", columnDefinition = "timestamp")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date publicationDate;

    @Lob
    @Column(name = "contents")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public News(String name, String contents, Date publicationDate, Category category) {
        this.id = id;
        this.name = name;
        this.contents = contents;
        this.publicationDate = publicationDate;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getContents() {
        return contents;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
