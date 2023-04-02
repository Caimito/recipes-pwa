package eu.caimito.recipe;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
  private final static Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private NotificationService notificationService;

  @PostMapping("/subscribe")
  public ResponseEntity<Void> subscribe(@RequestBody PushSubscription subscription) {
    LOGGER.info("Received subscription: {}", subscription);

    notificationRepository.save(
        new NotificationSubscription(subscription.getEndpoint(), subscription.getKeys().getP256dh(),
            subscription.getKeys().getAuth()));

    return ResponseEntity.ok().build();
  }

  @PostMapping("/unsubscribe")
  public ResponseEntity<Void> unsubscribe(@RequestBody PushSubscription subscription) {
    LOGGER.info("Received unsubscription: {}", subscription);

    // notificationRepository.deleteByEndpoint(subscription.getEndpoint());

    return ResponseEntity.ok().build();
  }

  @PostMapping("/send")
  public ResponseEntity<Void> send(@RequestBody String message)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    LOGGER.info("Received message: {}", message);

    notificationService.sendPushNotification("Notification", message);

    return ResponseEntity.ok().build();
  }

}
