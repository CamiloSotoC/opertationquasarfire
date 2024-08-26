package camilo.opertationquasarfire.repositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import camilo.opertationquasarfire.models.Satellite;

@ExtendWith(MockitoExtension.class)
class RepositoryTest {

    @InjectMocks
    private Repository repository;

    @Value("${satellite.kenobiX}")
    private double kenobiX;
    @Value("${satellite.kenobiY}")
    private double kenobiY;
    @Value("${satellite.skywalkerX}")
    private double skywalkerX;
    @Value("${satellite.skywalkerY}")
    private double skywalkerY;
    @Value("${satellite.satoX}")
    private double satoX;
    @Value("${satellite.satoY}")
    private double satoY;

    @BeforeEach
    void setup() {
        repository.setAll(kenobiX, kenobiY, skywalkerX, skywalkerY, satoX, satoY);
        assertNull(repository.getSatellites());
        repository.init();
        assertNotNull(repository.getSatellites());
        assertEquals(3, repository.getSatellites().size());
    }

    @Test
    void testGetSatellites() {
        List<Satellite> satellites = repository.getSatellites();
        assertNotNull(satellites);
        assertEquals(3, satellites.size());
    }

    @Test
    void testGetSatelliteByName() {
        Satellite satellite = repository.getSatelliteByName("Kenobi");
        assertNotNull(satellite);
        assertEquals("Kenobi", satellite.getName());
        assertEquals(kenobiX, satellite.getPosition().getX());
        assertEquals(kenobiY, satellite.getPosition().getY());
        satellite = repository.getSatelliteByName("Unknown");
        assertNull(satellite);
    }

    @Test
    void testGetDistances() {
        List<Double> distances = repository.getDistances();
        assertNotNull(distances);
        assertEquals(3, distances.size());
        assertNull(distances.get(0));
        assertNull(distances.get(1));
        assertNull(distances.get(2));
        List<Satellite> satellites = repository.getSatellites();
        satellites.get(0).setDistance(100.0);
        satellites.get(1).setDistance(115.5);
        satellites.get(2).setDistance(142.7);
        distances = repository.getDistances();
        assertNotNull(distances);
        assertEquals(3, distances.size());
        assertEquals(100.0, distances.get(0));
        assertEquals(115.5, distances.get(1));
        assertEquals(142.7, distances.get(2));
    }
    @Test
    void testGetMessages() {
        List<List<String>> messages = repository.getMessages();
        assertNotNull(messages);
        assertEquals(3, messages.size());
        assertNull(messages.get(0));
        assertNull(messages.get(1));
        assertNull(messages.get(2));
        List<Satellite> satellites = repository.getSatellites();
        satellites.get(0).setMessage(List.of("este", "", "", "mensaje", ""));
        satellites.get(1).setMessage(List.of("", "es", "", "", "secreto"));
        satellites.get(2).setMessage(List.of("este", "", "un", "", ""));
        messages = repository.getMessages();
        assertNotNull(messages);
        assertEquals(3, messages.size());
        assertEquals(List.of("este", "", "", "mensaje", ""), messages.get(0));
        assertEquals(List.of("", "es", "", "", "secreto"), messages.get(1));
        assertEquals(List.of("este", "", "un", "", ""), messages.get(2));
    }
}
