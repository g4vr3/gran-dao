package ad.grandao.repository;

import ad.grandao.model.Barco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BarcoRepository extends MongoRepository<Barco,String> {
}
