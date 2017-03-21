package ru.bmstu.iu6.mikrut.spring_mvc_news.service;

import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.iu6.mikrut.spring_mvc_news.dao.NewsDao;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;
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

    @Override
    @NonNls
    @NotNull
    public List<News> findByParameters(@Nullable Long categoryId, @Nullable String name, @Nullable String text) {
        return dao.findByQuery((query, builder, newsRoot) -> {
            // FIXME: escape special symbols from query
            // FIXME: use hibernate search
            // FIXME: use http://hibernate.org/search/documentation/getting-started/#introduction-to-full-text
            Predicate categoryPredicate = null;
            if (categoryId != null)
                categoryPredicate = builder.equal(newsRoot.get(News_.category), categoryId);

            Predicate conditionPredicate = null;
            if (name != null) {
                conditionPredicate = builder.like(builder.lower(newsRoot.get(News_.name)), "%" + name.toLowerCase() + "%");
            }
            if (text != null) {
                Predicate textPredicate = builder.like(builder.lower(newsRoot.get(News_.contents)), "%" + text.toLowerCase() + "%");
                if (conditionPredicate != null) {
                    conditionPredicate = builder.or(conditionPredicate, textPredicate);
                } else {
                    conditionPredicate = textPredicate;
                }

            }

            if (categoryPredicate != null && conditionPredicate != null) {
                query.where(builder.and(categoryPredicate, conditionPredicate));
            } else if (categoryPredicate != null) {
                query.where(categoryPredicate);
            } else if (conditionPredicate != null) {
                query.where(conditionPredicate);
            }
        });
    }

    @Override
    @NonNls
    @NotNull
    public List<News> findAllNews() {
        return dao.findAllNews();
    }

    @Override
    public long saveNews(@NotNull News news) {
        // TODO: check id security (?)
        return dao.saveNews(news);
    }

    @Override
    public void updateNews(long id, String name, String contents) {
        dao.updateNews(id, name, contents);
    }

    @Override
    public void deleteNews(long id) {
        dao.deleteNews(id);
    }
}
