package ru.bmstu.iu6.mikrut.spring_mvc_news.service;

import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.iu6.mikrut.spring_mvc_news.dao.NewsDao;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News_;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by mikrut on 17.03.17.
 */
@Service("newsService")
@Transactional
public class NewsService implements INewsService {

    @Autowired
    private NewsDao dao;

    @Nullable
    public News findById(long id) {
        return dao.findById(id);
    }

    @NotNull
    public List<News> findByParameters(@Nullable Long categoryId, @Nullable String name, @Nullable String text) {
        return dao.findByQuery((query, builder) -> {
            Root<News> newsRoot = query.from(News.class);
            // FIXME: escape special symbols from query
            // FIXME: use hibernate search
            if (categoryId != null) {
                query.where(builder.equal(newsRoot.get(News_.category), categoryId));
                if (name != null) {
                    query.where(builder.like(newsRoot.get(News_.name), name + "%"));
                }
                if (text != null) {
                    query.where(builder.like(newsRoot.get(News_.contents), "%" + name + "%"));
                }
            } else {
                throw new IllegalArgumentException("Category ID must be specified");
            }
        });
    }

    public long saveNews(@NotNull News news) {
        // TODO: check id security (?)
        return dao.saveNews(news);
    }

    public void updateNews(@NotNull News news) {
        News updated = dao.findById(news.getId());
        if (updated != null) {
            updated.setCategory(news.getCategory());
            updated.setContents(news.getContents());
            updated.setName(news.getName());
            updated.setPublicationDate(news.getPublicationDate());
        }
    }

    public void deleteNews(long id) {
        dao.deleteNews(id);
    }
}