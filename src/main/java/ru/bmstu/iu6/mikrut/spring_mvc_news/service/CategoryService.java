package ru.bmstu.iu6.mikrut.spring_mvc_news.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.iu6.mikrut.spring_mvc_news.dao.CategoryDao;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;

/**
 * Created by Михаил on 19.03.2017.
 */
@Service("categoryService")
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryDao dao;

    @Nullable
    @Override
    public Category findById(long id) {
        return dao.findById(id);
    }

}
