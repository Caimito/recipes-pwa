package eu.caimito.recipe;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<NotificationSubscription, String> {

}
