package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
