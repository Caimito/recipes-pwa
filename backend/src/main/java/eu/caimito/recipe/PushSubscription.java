package eu.caimito.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PushSubscription {
  private String endpoint;
  private PushSubscriptionKeys keys;

  @JsonCreator
  public PushSubscription(
      @JsonProperty("endpoint") String endpoint,
      @JsonProperty("keys") PushSubscriptionKeys keys) {
    this.endpoint = endpoint;
    this.keys = keys;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public PushSubscriptionKeys getKeys() {
    return keys;
  }

  @Override
  public String toString() {
    return "PushSubscription{" +
        "endpoint='" + endpoint + '\'' +
        ", keys=" + keys +
        '}';
  }

  public static class PushSubscriptionKeys {
    private String p256dh;
    private String auth;

    @JsonCreator
    public PushSubscriptionKeys(
        @JsonProperty("p256dh") String p256dh,
        @JsonProperty("auth") String auth) {
      this.p256dh = p256dh;
      this.auth = auth;
    }

    public String getP256dh() {
      return p256dh;
    }

    public String getAuth() {
      return auth;
    }

  }
}
