package pl.mszcz.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InquiryController {
    @Autowired
    InquiryCRUD inquiryCRUD;

    @GetMapping("/contacts")
    List<Inquiry> all() {
        return inquiryCRUD.findAll();
    }

    @PostMapping("/contacts")
    void createInquiry(@RequestBody Inquiry inquiry) {
        inquiryCRUD.save(inquiry);
    }
}
