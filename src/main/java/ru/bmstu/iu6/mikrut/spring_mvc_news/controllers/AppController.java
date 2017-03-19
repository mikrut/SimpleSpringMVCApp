package ru.bmstu.iu6.mikrut.spring_mvc_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;
import ru.bmstu.iu6.mikrut.spring_mvc_news.service.ICategoryService;
import ru.bmstu.iu6.mikrut.spring_mvc_news.service.INewsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mikrut on 16.03.17.
 */
@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private INewsService newsService;

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String getNews(ModelMap model) {
        List<News> news = newsService.findAllNews();
        model.addAttribute("news", news);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
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
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("chosen_category_id", categoryId);
        return "allnews";
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news/{news_id}"
            }, method = RequestMethod.GET)
    public String getNews(@PathVariable("news_id") long newsId, ModelMap model) {
        News news = newsService.findById(newsId);
        model.addAttribute("news", news);
        return "news";
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news"
            }, method = RequestMethod.POST
    )
    public String createNews(@PathVariable("category_id") long categoryId,
                             @RequestParam("name") String name,
                             @RequestParam("contents") String contents) {
        Category category = categoryService.findById(categoryId);
        News news = new News(name, contents, new Date(), category);
        long newsId = newsService.saveNews(news);
        return "redirect:/category/" + categoryId + "/news/" + newsId;
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news/{news_id}"
            }, method = RequestMethod.PUT
    )
    public String updateNews(@PathVariable("news_id") long newsId,
                             @RequestParam("name") String name,
                             @RequestParam("contents") String contents,
                             ModelMap model) {
        News news = newsService.findById(newsId);
        news.setName(name);
        news.setContents(contents);
        newsService.saveNews(news);
        List<News> list = new ArrayList<>(1);
        list.add(news);
        model.addAttribute("news", list);
        model.addAttribute("chosen_category_id", news.getCategory().getId());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        // TODO: use another jsp template
        return "allnews";
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news/{news_id}"
            }, method = RequestMethod.DELETE
    )
    public ResponseEntity<String> deleteNews(@PathVariable("news_id") long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            value = {
                    "/new",
                    "/category/{category_id}/new"
            }
    )
    public String createNews(@PathVariable(value = "category_id", required = false) Long categoryId, ModelMap model) {
        if (categoryId != null)
            model.addAttribute("category_id", categoryId);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "edit";
    }

}
