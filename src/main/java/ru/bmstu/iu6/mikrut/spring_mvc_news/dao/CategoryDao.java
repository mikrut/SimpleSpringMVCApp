package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;

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

}
