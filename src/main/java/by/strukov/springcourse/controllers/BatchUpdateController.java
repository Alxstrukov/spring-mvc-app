package by.strukov.springcourse.controllers;


import by.strukov.springcourse.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchUpdateController {
    private final PersonDAO personDAO;

    @Autowired
    public BatchUpdateController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String without() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("with")
    public String withOut() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }
}
