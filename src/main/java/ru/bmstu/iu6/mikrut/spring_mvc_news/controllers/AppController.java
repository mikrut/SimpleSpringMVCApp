package ru.bmstu.iu6.mikrut.spring_mvc_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;
import ru.bmstu.iu6.mikrut.spring_mvc_news.service.INewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikrut on 16.03.17.
 */
@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private INewsService newsService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String getNews(ModelMap model) {
        List<News> news = newsService.findAllNews();
        model.addAttribute("news", news);
        return "allnews";
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}",
                    "/category/{category_id}/news"
            }, method = RequestMethod.GET)
    public String getCategoryNews(@PathVariable("category_id") long categoryId,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "text", required = false) String text,
                                  ModelMap model) {
        List<News> news = newsService.findByParameters(categoryId, name, text);
        model.addAttribute("news", news);
        // TODO: use another jsp template
        return "allnews";
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news/{news_id}"
            }, method = RequestMethod.GET)
    public String getNews(@PathVariable("news_id") long newsId, ModelMap model) {
        News news = newsService.findById(newsId);
        List<News> list = new ArrayList<>(1);
        list.add(news);
        model.addAttribute("news", list);
        // TODO: use another jsp template
        return "allnews";
    }

}
