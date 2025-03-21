package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.MongoOrder;

@Repository
public interface MongoOrderRepository extends MongoRepository<MongoOrder, String> {
}


