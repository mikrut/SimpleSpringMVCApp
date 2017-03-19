package ru.bmstu.iu6.mikrut.spring_mvc_news.service;

import org.jetbrains.annotations.Nullable;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;

/**
 * Created by Михаил on 19.03.2017.
 */
public interface ICategoryService {

    @Nullable
    Category findById(long id);

}
