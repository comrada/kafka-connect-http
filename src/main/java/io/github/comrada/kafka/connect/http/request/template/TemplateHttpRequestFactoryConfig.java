package io.github.comrada.kafka.connect.http.request.template;

import static org.apache.kafka.common.config.ConfigDef.Importance.HIGH;
import static org.apache.kafka.common.config.ConfigDef.Importance.LOW;
import static org.apache.kafka.common.config.ConfigDef.Importance.MEDIUM;
import static org.apache.kafka.common.config.ConfigDef.Type.CLASS;
import static org.apache.kafka.common.config.ConfigDef.Type.STRING;

import io.github.comrada.kafka.connect.http.request.template.freemarker.BackwardsCompatibleFreeMarkerTemplateFactory;
import io.github.comrada.kafka.connect.http.request.template.spi.TemplateFactory;
import java.util.Map;
import lombok.Getter;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

@Getter
public class TemplateHttpRequestFactoryConfig extends AbstractConfig {

  private static final String URL = "http.request.url";
  private static final String METHOD = "http.request.method";
  private static final String HEADERS = "http.request.headers";
  private static final String QUERY_PARAMS = "http.request.params";
  private static final String BODY = "http.request.body";
  private static final String TEMPLATE_FACTORY = "http.request.template.factory";

  private final String url;
  private final String method;
  private final String headers;
  private final String queryParams;
  private final String body;
  private final TemplateFactory templateFactory;

  TemplateHttpRequestFactoryConfig(Map<String, ?> originals) {
    super(config(), originals);
    url = getString(URL);
    method = getString(METHOD);
    headers = getString(HEADERS);
    queryParams = getString(QUERY_PARAMS);
    body = getString(BODY);
    templateFactory = getConfiguredInstance(TEMPLATE_FACTORY, TemplateFactory.class);
  }

  public static ConfigDef config() {
    return new ConfigDef()
        .define(URL, STRING, HIGH, "HTTP URL Template")
        .define(METHOD, STRING, "GET", HIGH, "HTTP Method Template")
        .define(HEADERS, STRING, "", MEDIUM, "HTTP Headers Template")
        .define(QUERY_PARAMS, STRING, "", MEDIUM, "HTTP Query Params Template")
        .define(BODY, STRING, "", LOW, "HTTP Body Template")
        .define(TEMPLATE_FACTORY, CLASS, BackwardsCompatibleFreeMarkerTemplateFactory.class, LOW, "Template Factory Class");
  }
}
