package ru.bmstu.iu6.mikrut.spring_mvc_news.service;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bmstu.iu6.mikrut.spring_mvc_news.dao.INewsQuery;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;

import java.util.List;

/**
 * Created by mikrut on 17.03.17.
 */
public interface INewsService {

    @Nullable
    News findById(long id);

    @NonNls
    @NotNull
    List<News> findByParameters(@Nullable Long categoryId,
                                @Nullable String name,
                                @Nullable String text);

    long saveNews(@NotNull News news);

    void updateNews(long id, String name, String contents);

    void deleteNews(long id);

    @NonNls
    @NotNull
    List<News> findAllNews();

}
