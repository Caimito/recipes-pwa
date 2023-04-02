package eu.caimito.recipe;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification_subscriptions")
public class NotificationSubscription {
  @Id
  private String id;
  private String endpoint;
  private String p256dh;
  private String auth;

  public NotificationSubscription(String endpoint, String p256dh, String auth) {
    this.endpoint = endpoint;
    this.p256dh = p256dh;
    this.auth = auth;
  }

  public String getId() {
    return id;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getP256dh() {
    return p256dh;
  }

  public String getAuth() {
    return auth;
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", endpoint='" + getEndpoint() + "'" +
        ", p256dh='" + getP256dh() + "'" +
        ", auth='" + getAuth() + "'" +
        "}";
  }

}
