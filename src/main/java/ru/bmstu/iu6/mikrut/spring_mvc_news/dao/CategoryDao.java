package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Михаил on 19.03.2017.
 */
@Repository("categoryDao")
public class CategoryDao extends AbstractDao<Long, Category> implements ICategoryDao {

    @Nullable
    @Override
    public Category findById(long id) {
        return getByKey(id);
    }

    @NotNull
    @Override
    public List<Category> findAll() {
        CriteriaQuery<Category> criteria = createEntityCriteria();
        Root<Category> newsRoot = criteria.from(Category.class);
        criteria.select(newsRoot);

        return getSession().createQuery(criteria).list();
    }
}
