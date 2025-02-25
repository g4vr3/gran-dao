package ad.grandao.repository;

import ad.grandao.model.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocheRepository extends JpaRepository<Coche,String> {
}
