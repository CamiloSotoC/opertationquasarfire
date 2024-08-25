package camilo.opertationquasarfire.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;
import camilo.opertationquasarfire.Repositories.Repository;
import camilo.opertationquasarfire.Utils.UtilRebuilMessage;

@ExtendWith(MockitoExtension.class)
public class ServiceImpTest {

    @InjectMocks
    private ServiceImp service;

    @Mock
    private Repository repository;

    @Test
    void testGetLocation() {
        List<Double> distances = List.of(100.0, 115.5, 142.7);
        List<Satellite> satellites = List.of(
                new Satellite("kenobi", new Position(-500, -200)),
                new Satellite("skywalker", new Position(100, -100)),
                new Satellite("sato", new Position(500, 100)));
        when(repository.getSatellites()).thenReturn(satellites);
        Position result = service.getLocation(distances);

        assertNotNull(result);
        assertEquals(-58.315252587138595, result.getX());
        assertEquals(-69.55141837312165, result.getY());
    }

    @Test
    void testGetMessage() {
        List<List<String>> messages = List.of(
                List.of("este", "", "", "mensaje", ""),
                List.of("", "es", "", "", "secreto"),
                List.of("este", "", "un", "", ""));
        String result = service.getMessage(messages);
        assertEquals("este es un mensaje secreto", result);

    }

    @Test
    void testGetSatellites() {
        List<Satellite> satellites = List.of(
                new Satellite("kenobi", new Position(-500, -200)),
                new Satellite("skywalker", new Position(100, -100)),
                new Satellite("sato", new Position(500, 100)));
        when(repository.getSatellites()).thenReturn(satellites);
        List<Satellite> result = service.getSatellites();
        assertEquals(satellites, result);
    }

    @Test
    void testGetSpaceshipData() {
        List<SatelliteRequest> satellitesRequest = List.of(
                new SatelliteRequest("kenobi", 100.0, List.of("este", "", "", "mensaje", "")),
                new SatelliteRequest("skywalker", 115.5, List.of("", "es", "", "", "secreto")),
                new SatelliteRequest("sato", 142.7, List.of("este", "", "un", "", "")));
        List<Satellite> satellites = List.of(
                new Satellite("kenobi", new Position(-500, -200)),
                new Satellite("skywalker", new Position(100, -100)),
                new Satellite("sato", new Position(500, 100)));
        List<Double> distances = List.of(100.0, 115.5, 142.7);
        List<List<String>> messages = List.of(
                List.of("este", "", "", "mensaje", ""),
                List.of("", "es", "", "", "secreto"),
                List.of("este", "", "un", "", ""));
        when(repository.getSatellites()).thenReturn(satellites);
        when(repository.getSatelliteByName("kenobi")).thenReturn(satellites.get(0));
        when(repository.getSatelliteByName("skywalker")).thenReturn(satellites.get(1));
        when(repository.getSatelliteByName("sato")).thenReturn(satellites.get(2));
        when(repository.getDistances()).thenReturn(distances);
        when(repository.getMessages()).thenReturn(messages);
        SpaceshipResponse result = service.getSpaceshipData(satellitesRequest);
        assertNotNull(result);
        assertEquals(-58.315252587138595, result.getPosition().getX());
        assertEquals(-69.55141837312165, result.getPosition().getY());
        assertEquals("este es un mensaje secreto", result.getMessage());
    }

    @Test
    void testGetSpaceshipData2() {
        List<Satellite> satellites = List.of(
                new Satellite("kenobi", new Position(-500, -200), 100.0, List.of("este", "", "", "mensaje", "") ),
                new Satellite("skywalker", new Position(100, -100), 115.5, List.of("", "es", "", "", "secreto") ),
                new Satellite("sato", new Position(500, 100), 142.7, List.of("este", "", "un", "", "") ));
        List<Double> distances = List.of(satellites.get(0).getDistance(), satellites.get(1).getDistance(), satellites.get(2).getDistance());
        List<List<String>> messages = List.of(
            satellites.get(0).getMessage(),
            satellites.get(1).getMessage(),
            satellites.get(2).getMessage());
        when(repository.getSatellites()).thenReturn(satellites);
        when(repository.getSatelliteByName("kenobi")).thenReturn(satellites.get(0));
        when(repository.getSatelliteByName("skywalker")).thenReturn(satellites.get(1));
        when(repository.getSatelliteByName("sato")).thenReturn(satellites.get(2));
        when(repository.getDistances()).thenReturn(distances);
        when(repository.getMessages()).thenReturn(messages);
        SpaceshipResponse result = service.getSpaceshipData();
        assertNotNull(result);
        assertEquals(-58.315252587138595, result.getPosition().getX());
        assertEquals(-69.55141837312165, result.getPosition().getY());
        assertEquals("este es un mensaje secreto", result.getMessage());

    }

    @Test
    void testSetSatellite() {

    }
}
