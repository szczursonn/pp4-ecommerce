package pl.mszcz.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NameProvider {

    @Bean
    public List<String> allNames() {
        return Arrays.asList("Kuba", "Michal", "Aga", "Kasia", "Krzys");
    }
}
