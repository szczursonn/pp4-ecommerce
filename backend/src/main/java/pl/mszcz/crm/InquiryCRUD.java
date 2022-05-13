package pl.mszcz.crm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCRUD  extends JpaRepository<Inquiry, Long> {
}
