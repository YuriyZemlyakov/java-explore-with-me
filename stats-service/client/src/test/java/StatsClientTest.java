//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import ru.practicum.ewmClient.StatsClient;
//import ru.practicum.ewmDto.HitDto;
//
//import java.time.LocalDateTime;
//
//public class StatsClientTest {
//    static StatsClient client;
//    static HitDto dto;
//
//    @BeforeAll
//    static void setUp() {
//        client = new StatsClient();
//        dto = new HitDto();
//        dto.setApp("abc");
//        dto.setIp("123.12.45");
//        dto.setUri("/test/222");
//        dto.setTimestamp(LocalDateTime.now());
//    }
//
//    @Test
//    void sendHitTest() {
//        Assertions.assertDoesNotThrow(() -> client.sendHit(dto));
//    }
//}
