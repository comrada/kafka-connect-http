package io.github.comrada.kafka.connect.http.response.jackson;

import static com.fasterxml.jackson.core.JsonPointer.compile;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.array;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.deserialize;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.item1;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.item1Json;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.item2;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.itemArray;
import static io.github.comrada.kafka.connect.http.response.jackson.JacksonSerializerTest.Fixture.itemArrayNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JacksonSerializerTest {

  @InjectMocks
  JacksonSerializer serializer;

  @Mock
  ObjectMapper mapper;

  @Test
  void whenSerialize_thenSerializedByMapper() throws JsonProcessingException {

    given(mapper.writeValueAsString(item1Json)).willReturn(item1);

    assertThat(serializer.serialize(item1Json)).isEqualTo(item1);
  }

  @Test
  void whenDeserialize_thenDeserializedByMapper() throws IOException {

    given(mapper.readTree(item1.getBytes())).willReturn(item1Json);

    assertThat(serializer.deserialize(item1.getBytes())).isEqualTo(item1Json);
  }

  @Test
  void whenGetArrayAtPointerObject_thenObject() {
    assertThat(serializer.getArrayAt(deserialize(item1), compile("/"))).containsExactly(deserialize(item1));
  }

  @Test
  void whenGetArrayAtPointerArray_thenAllItems() {
    assertThat(serializer.getArrayAt(deserialize(array), compile("/"))).containsExactly(deserialize(item1),
        deserialize(item2));
  }

  @Test
  void whenGetArrayAtPointerItems_thenAllItems() {
    assertThat(serializer.getArrayAt(deserialize(itemArray), compile("/items"))).containsExactly(deserialize(item1),
        deserialize(item2));
  }

  @Test
  void whenGetNullAtPointerItems_thenNoItem() {
    assertThat(serializer.getArrayAt(deserialize(itemArrayNull), compile("/items"))).isEmpty();
  }

  @Test
  void whenGetObjectAtRoot_thenRoot() {
    assertThat(serializer.getObjectAt(deserialize(item1), compile("/"))).isEqualTo(deserialize(item1));
  }

  @Test
  void whenGetObjectAtProperty_thenProperty() {
    assertThat(serializer.getObjectAt(deserialize(item1), compile("/k1"))).isEqualTo(deserialize(item1).at("/k1"));
  }

  interface Fixture {

    ObjectMapper mapper = new ObjectMapper();
    String item1 = "{\"k1\":\"v1\"}";
    JsonNode item1Json = deserialize(item1);
    String item2 = "{\"k2\":\"v2\"}";
    String array = "[" + item1 + "," + item2 + "]";
    String itemArray = "{\"items\":[" + item1 + "," + item2 + "]}";
    String itemArrayNull = "{\"items\":null}";

    @SneakyThrows
    static JsonNode deserialize(String body) {
      return mapper.readTree(body);
    }
  }
}
