package eu.caimito.recipe;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;

@Service
public class NotificationService {
  private Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

  @Autowired
  private NotificationRepository notificationSubscriptionRepository;

  private PushService pushService = new PushService();

  @Autowired
  private ObjectMapper objectMapper;

  private static final String VAPID_PUBLIC_KEY = "BPTF1s1q8g9Cb8Skg0OncKEJ__0rznBVFrxF8pjE5GxW-xC6Z9TeNHIDZcGyu8QkFKHdzsUzs92q7DGoEoTbtf8";
  private static final String VAPID_PRIVATE_KEY = "5I6Zo1JIgIThnqmG-9lVG10B1fX5wYyY7P4i3te-LHQ";
  private static final String VAPID_SUBJECT = "mailto:info@caimito.net";
  private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000L; // 12 hours in milliseconds

  public void sendPushNotification(String title, String body)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    // Add BouncyCastle as an algorithm provider
    if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
      Security.addProvider(new BouncyCastleProvider());
    }

    pushService.setPublicKey(VAPID_PUBLIC_KEY);
    pushService.setPrivateKey(VAPID_PRIVATE_KEY);

    notificationSubscriptionRepository.findAll().forEach(subscription -> {
      try {
        sendNotification(subscription, title, body);
      } catch (Exception e) {
        LOGGER.error("Cannot notify ALL due to {}", e);
      }
    });
  }

  private void sendNotification(NotificationSubscription subscription, String title, String body) {
    LOGGER.info("Sending notification to {}", subscription.getEndpoint());
    try {

      WebPushMessage message = new WebPushMessage();
      message.title = title;
      message.message = body;
      message.clickTarget = "/";

      Notification notification = new Notification(
          subscription.getEndpoint(),
          subscription.getP256dh(), // getPublicKey
          subscription.getAuth(),
          objectMapper.writeValueAsBytes(message));

      pushService.send(notification);
    } catch (Exception e) {
      LOGGER.error("Cannot notify due to {}", e);
    }
  }

  class WebPushMessage {
    public String title;
    public String clickTarget;
    public String message;
  }

}
