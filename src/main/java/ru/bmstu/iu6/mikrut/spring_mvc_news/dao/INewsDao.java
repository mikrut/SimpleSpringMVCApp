package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;

import java.util.List;

/**
 * Created by mikrut on 17.03.17.
 */
public interface INewsDao {

    @Nullable
    News findById(long id);

    @NonNls
    @NotNull
    List<News> findByQuery(INewsQuery query);

    long saveNews(@NotNull News news);

    void deleteNews(long id);

}