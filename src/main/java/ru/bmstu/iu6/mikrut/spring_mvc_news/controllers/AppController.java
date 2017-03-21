package ru.bmstu.iu6.mikrut.spring_mvc_news.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.Category;
import ru.bmstu.iu6.mikrut.spring_mvc_news.models.News;
import ru.bmstu.iu6.mikrut.spring_mvc_news.service.ICategoryService;
import ru.bmstu.iu6.mikrut.spring_mvc_news.service.INewsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

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
    public String getNews(@RequestParam(value="q", required = false) String q, ModelMap model) {
        List<News> news = newsService.findByParameters(null, q, q);
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
                                  @RequestParam(value = "q", required = false) String q,
                                  ModelMap model) {
        List<News> news = newsService.findByParameters(categoryId, q, q);
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
                    "/category/{category_id}/news/{news_id}/edit"
            }, method = RequestMethod.GET)
    public String editNews(@PathVariable("news_id") long newsId, ModelMap model) {
        News news = newsService.findById(newsId);
        model.addAttribute("news", news);
        model.addAttribute("category_id", news.getCategory().getId());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "edit";
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news"
            }, method = RequestMethod.POST
    )
    public @ResponseBody ResponseEntity<String> createNews(@PathVariable("category_id") long categoryId,
                                  @RequestParam("name") String name,
                                  @RequestParam("contents") String contents) {
        Category category = categoryService.findById(categoryId);
        JSONObject response = new JSONObject();
        try {
            News news = new News(name, contents, new Date(), category);
            long newsId = newsService.saveNews(news);
            response.put("status", "ok");
            response.put("redirect", "/category/" + category.getId() + "/news/" + newsId);
            return new ResponseEntity<>(response.toJSONString(), HttpStatus.OK);
        } catch (ConstraintViolationException ex) {
            response.put("status", "error");
            JSONArray errorsArray = new JSONArray();
            response.put("errors", errorsArray);
            for (ConstraintViolation violation : ex.getConstraintViolations()) {
                JSONObject error = new JSONObject();
                error.put(violation.getPropertyPath(), violation.getMessage());
                errorsArray.add(error);
            }
            return new ResponseEntity<>(response.toJSONString(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(
            value = {
                    "/category/{category_id}/news/{news_id}"
            }, method = RequestMethod.PUT
    )
    public @ResponseBody ResponseEntity<String> updateNews(@PathVariable("news_id") long newsId,
                             @RequestParam("name") String name,
                             @RequestParam("contents") String contents,
                             ModelMap model) {
        JSONObject response = new JSONObject();
        try {
            newsService.updateNews(newsId, name, contents);
            News news = newsService.findById(newsId);
            response.put("status", "ok");
            response.put("redirect", "/category/" + news.getCategory().getId() + "/news/" + newsId);
            return new ResponseEntity<>(response.toJSONString(), HttpStatus.OK);
        } catch (ConstraintViolationException ex) {
            response.put("status", "error");
            JSONArray errorsArray = new JSONArray();
            response.put("errors", errorsArray);
            for (ConstraintViolation violation : ex.getConstraintViolations()) {
                JSONObject error = new JSONObject();
                error.put(violation.getPropertyPath(), violation.getMessage());
                errorsArray.add(error);
            }
            return new ResponseEntity<>(response.toJSONString(), HttpStatus.BAD_REQUEST);
        }
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
