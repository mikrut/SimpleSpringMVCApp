package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News_;

import javax.persistence.criteria.CriteriaBuilder;
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
        Root<News> newsRoot = criteriaQuery.from(News.class);
        CriteriaBuilder builder = getSession().getCriteriaBuilder();

        criteriaQuery.orderBy(builder.desc(newsRoot.get(News_.publicationDate)));
        query.setupCriteriaQuery(criteriaQuery, builder, newsRoot);

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


    @Override
    public void updateNews(long id, String name, String contents) {
        News news = findById(id);
        news.setName(name);
        news.setContents(contents);
        getSession().save(news);
    }

    public long saveNews(@NotNull News news) {
        persist(news);
        getSession().flush();
        return news.getId();
    }

    public void deleteNews(long id) {
        Query query = getSession().createQuery("delete from " + News.class.getSimpleName() + " where id = ?");
        query.setParameter(0, id, StandardBasicTypes.LONG);
        query.executeUpdate();
    }
}
