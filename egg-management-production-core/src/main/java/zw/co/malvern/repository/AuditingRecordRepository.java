package zw.co.malvern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.malvern.domain.RecordAuditing;

public interface AuditingRecordRepository extends JpaRepository<RecordAuditing,Long> {
}
