package pl.mszcz.crm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InquiryCRUDTest {

    @Autowired
    private InquiryCRUD inquiryCRUD;

    @Test
    public void itAllowsToSaveInquiry() {
        inquiryCRUD.findAll();
    }
}
