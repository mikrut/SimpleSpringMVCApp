package ru.bmstu.iu6.mikrut.spring_mvc_news.dao;

import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Created by mikrut on 17.03.17.
 */
public interface INewsQuery {
    void setupCriteriaQuery(CriteriaQuery<News> criteriaQuery, CriteriaBuilder builder);
}
