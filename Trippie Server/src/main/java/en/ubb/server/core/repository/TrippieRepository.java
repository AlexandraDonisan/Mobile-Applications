package en.ubb.server.core.repository;

import java.io.Serializable;

import en.ubb.server.core.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
@Transactional
@Repository
public interface TrippieRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {
}
