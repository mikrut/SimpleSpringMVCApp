package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import org.jetbrains.annotations.Nullable;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;

/**
 * Created by Михаил on 19.03.2017.
 */
public interface ICategoryDao {

    @Nullable
    Category findById(long id);

}