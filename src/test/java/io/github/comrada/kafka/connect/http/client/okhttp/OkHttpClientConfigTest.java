package io.github.comrada.kafka.connect.http.client.okhttp;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.comrada.kafka.connect.http.auth.BasicHttpAuthenticator;
import io.github.comrada.kafka.connect.http.auth.ConfigurableHttpAuthenticator;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OkHttpClientConfigTest {

  @Test
  void whenNoConnectionTimeoutMillis_thenDefault() {
    assertThat(config(emptyMap()).getConnectionTimeoutMillis()).isEqualTo(2000L);
  }

  @Test
  void whenConnectionTimeoutMillis_thenInitialized() {
    assertThat(
        config(ImmutableMap.of("http.client.connection.timeout.millis", "42")).getConnectionTimeoutMillis()).isEqualTo(42L);
  }

  @Test
  void whenNoReadTimeoutMillis_thenDefault() {
    assertThat(config(emptyMap()).getConnectionTimeoutMillis()).isEqualTo(2000L);
  }

  @Test
  void whenReadTimeoutMillis_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.read.timeout.millis", "42")).getReadTimeoutMillis()).isEqualTo(42L);
  }

  @Test
  void whenNoKeepAliveDuration_thenDefault() {
    assertThat(config(emptyMap()).getKeepAliveDuration()).isEqualTo(300000L);
  }

  @Test
  void whenKeepAliveDuration_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.ttl.millis", "42")).getKeepAliveDuration()).isEqualTo(42L);
  }

  @Test
  void whenMaxIdleConnections_thenDefault() {
    assertThat(config(emptyMap()).getMaxIdleConnections()).isEqualTo(1);
  }

  @Test
  void whenMaxIdleConnections_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.max-idle", "42")).getMaxIdleConnections()).isEqualTo(42L);
  }

  @Test
  void whenAuthenticator_thenDefault() {
    assertThat(config(emptyMap()).getAuthenticator()).isInstanceOf(ConfigurableHttpAuthenticator.class);
  }

  @Test
  void whenAuthenticator_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.auth",
        "io.github.comrada.kafka.connect.http.auth.BasicHttpAuthenticator")).getAuthenticator()).isInstanceOf(
        BasicHttpAuthenticator.class);
  }

  @Test
  void whenNoProxyHost_thenDefault() {
    assertThat(config(emptyMap()).getProxyHost()).isEqualTo("");
  }

  @Test
  void whenProxyHost_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.proxy.host", "host")).getProxyHost()).isEqualTo("host");
  }

  @Test
  void whenNoProxyPort_thenDefault() {
    assertThat(config(emptyMap()).getProxyPort()).isEqualTo(3128);
  }

  @Test
  void whenProxyPort_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.proxy.port", "8080")).getProxyPort()).isEqualTo(8080);
  }

  @Test
  void whenNoProxyUsername_thenDefault() {
    assertThat(config(emptyMap()).getProxyUsername()).isEqualTo("");
  }

  @Test
  void whenProxyUsername_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.proxy.username", "user")).getProxyUsername()).isEqualTo("user");
  }

  @Test
  void whenNoProxyPassword_thenDefault() {
    assertThat(config(emptyMap()).getProxyPassword()).isEqualTo("");
  }

  @Test
  void whenProxyPassword_thenInitialized() {
    assertThat(config(ImmutableMap.of("http.client.proxy.password", "pass")).getProxyPassword()).isEqualTo("pass");
  }

  private static OkHttpClientConfig config(Map<String, String> config) {
    return new OkHttpClientConfig(config);
  }
}
