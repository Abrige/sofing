package it.unicam.cs.agritrace;

import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
public class CertificationRepositoryTest {

    @Autowired
    private CertificationRepository repository;

    @Test
    void testFindByName() {
        Optional<Certification> cert = repository.findByName("Bio");
        assertTrue(cert.isPresent());
        assertEquals("Bio", cert.get().getName());
    }

    @Test
    void testSaveAndRetrieveCertification() {
        Certification cert = new Certification();
        cert.setId(3);
        cert.setName("Organic Certification");
        cert.setIssuingBody("Global Org");
        cert.setIsActive(true);

        repository.save(cert);

        Certification retrieved = repository.findById(3).orElseThrow();
        assertThat(retrieved.getIsActive()).isTrue();
        assertThat(retrieved.getName()).isEqualTo("Organic Certification");
    }
}