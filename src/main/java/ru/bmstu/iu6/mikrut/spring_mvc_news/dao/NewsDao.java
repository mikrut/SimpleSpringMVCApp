package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by mikrut on 17.03.17.
 */
@Repository("newsDao")
public class NewsDao extends AbstractDao<Long, News> implements INewsDao  {

    @Nullable
    public News findById(long id) {
        return getByKey(id);
    }

    @NotNull
    public List<News> findByQuery(INewsQuery query) {
        CriteriaQuery<News> criteriaQuery = createEntityCriteria();
        query.setupCriteriaQuery(criteriaQuery, getSession().getCriteriaBuilder());

        Query<News> hQuery = getSession().createQuery(criteriaQuery);
        return hQuery.list();
    }

    @NotNull
    @Override
    public List<News> findAllNews() {
        CriteriaQuery<News> criteria = createEntityCriteria();
        Root<News> newsRoot = criteria.from(News.class);
        criteria.select(newsRoot);

        return getSession().createQuery(criteria).list();
    }

    public long saveNews(@NotNull News news) {
        persist(news);
        getSession().flush();
        return news.getId();
    }

    public void deleteNews(long id) {
        Query<News> query = getSession().createQuery("delete from " + News.class.getSimpleName() + " where id = ?", News.class);
        query.setParameter(0, id, StandardBasicTypes.LONG);
        query.executeUpdate();
    }
}
