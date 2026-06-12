package com.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SchoolController {

    @Autowired
    private SchoolService service;

    // Головна сторінка з таблицею
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("records", service.getAllRecords());
        return "index";
    }

    // Сторінка з формою додавання
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("school", new School());
        return "add";
    }

    // Обробка збереження з форми
    @PostMapping("/add")
    public String addRecord(@ModelAttribute School school) {
        service.saveRecord(school);
        return "redirect:/";
    }

    // Видалення запису
    @PostMapping("/delete/{id}")
    public String deleteRecord(@PathVariable String id) {
        service.deleteRecord(id);
        return "redirect:/";
    }
}