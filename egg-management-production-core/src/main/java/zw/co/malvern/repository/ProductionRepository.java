package zw.co.malvern.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.malvern.domain.Egg;

import java.time.LocalDateTime;

public interface ProductionRepository extends JpaRepository<Egg,Long> {

    Page<Egg> findAllByRecordCreationTimeBetween(String startDate, String endDate, Pageable pageable);
}
