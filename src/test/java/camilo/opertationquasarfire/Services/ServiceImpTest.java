package camilo.opertationquasarfire.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import camilo.opertationquasarfire.exceptions.InformationException;
import camilo.opertationquasarfire.exceptions.ResquestException;
import camilo.opertationquasarfire.models.Position;
import camilo.opertationquasarfire.models.Satellite;
import camilo.opertationquasarfire.models.SatelliteRequest;
import camilo.opertationquasarfire.models.SpaceshipResponse;
import camilo.opertationquasarfire.repositories.Repository;

@ExtendWith(MockitoExtension.class)
class ServiceImpTest {

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
                assertNotNull(result);
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
                                new Satellite("kenobi", new Position(-500, -200), 100.0,
                                                List.of("este", "", "", "mensaje", "")),
                                new Satellite("skywalker", new Position(100, -100), 115.5,
                                                List.of("", "es", "", "", "secreto")),
                                new Satellite("sato", new Position(500, 100), 142.7,
                                                List.of("este", "", "un", "", "")));
                List<Double> distances = List.of(satellites.get(0).getDistance(), satellites.get(1).getDistance(),
                                satellites.get(2).getDistance());
                List<List<String>> messages = List.of(
                                satellites.get(0).getMessage(),
                                satellites.get(1).getMessage(),
                                satellites.get(2).getMessage());
                when(repository.getSatellites()).thenReturn(satellites);
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
                Satellite satellite = new Satellite("kenobi", new Position(-500, -200));
                SatelliteRequest request = new SatelliteRequest("kenobi", 100.0,
                                List.of("este", "", "", "mensaje", ""));
                when(repository.getSatelliteByName("kenobi")).thenReturn(satellite);
                Satellite result = service.setSatellite("kenobi", request);
                assertNotNull(result);
                assertEquals(100.0, result.getDistance());
                assertEquals(List.of("este", "", "", "mensaje", ""), result.getMessage());
        }

        @Test
        void testExceptionRQE1() {
                List<SatelliteRequest> satellitesRequest = new ArrayList<>();
                ResquestException e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE1", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
                satellitesRequest.add(new SatelliteRequest("kenobi", 100.0, List.of("este", "", "", "mensaje", "")));
                satellitesRequest.add(new SatelliteRequest("skywalker", 115.5, List.of("", "es", "", "", "secreto")));
                e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE1", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
        }

        @Test
        void testExceptionRQE2() {
                List<SatelliteRequest> satellitesRequest = new ArrayList<>();
                satellitesRequest.add(null);
                satellitesRequest.add(new SatelliteRequest("skywalker", 115.5, List.of("", "es", "", "", "secreto")));
                satellitesRequest.add(new SatelliteRequest("sato", 142.7, List.of("este", "", "un", "", "")));
                ResquestException e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE2", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
                satellitesRequest.set(0, new SatelliteRequest(null, 100.0, List.of("este", "", "", "mensaje", "")));
                e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE2", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
        }

        @Test
        void testExceptionRQE3() {
                List<SatelliteRequest> satellitesRequest = List.of(
                                new SatelliteRequest("darthvader", 100.0, List.of("este", "", "", "mensaje", "")),
                                new SatelliteRequest("skywalker", 115.5, List.of("", "es", "", "", "secreto")),
                                new SatelliteRequest("sato", 142.7, List.of("este", "", "un", "", "")));
                ResquestException e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE3", e.getCode());
                assertEquals("That satellite does not exist: 'darthvader'.", e.getMessage());
        }

        @Test
        void testExceptionRQE4() {
                List<SatelliteRequest> satellitesRequest = List.of(
                                new SatelliteRequest("kenobi", 100.0, null),
                                new SatelliteRequest("skywalker", 115.5, List.of("", "es", "", "", "secreto")),
                                new SatelliteRequest("sato", 142.7, List.of("este", "", "un", "", "")));
                Satellite satellite = new Satellite("kenobi", new Position(-500, -200));
                when(repository.getSatelliteByName("kenobi")).thenReturn(satellite);
                ResquestException e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE4", e.getCode());
                assertEquals("There is not enough information from satellite: 'kenobi'.", e.getMessage());

                satellitesRequest.get(0).setDistance(null);
                satellitesRequest.get(0).setMessage(List.of("este", "", "", "mensaje", ""));
                e = assertThrows(
                                ResquestException.class,
                                () -> service.getSpaceshipData(satellitesRequest));
                assertEquals("RQE4", e.getCode());
                assertEquals("There is not enough information from satellite: 'kenobi'.", e.getMessage());
        }

        @Test
        void testExceptionINE1() {
                List<Satellite> satellites = null;
                when(repository.getSatellites()).thenReturn(satellites);
                InformationException e = assertThrows(
                                InformationException.class,
                                () -> service.getSpaceshipData());
                assertEquals("INE1", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
                satellites = new ArrayList<>();
                satellites.add(new Satellite("kenobi", new Position(-500, -200), 100.0,
                                List.of("este", "", "", "mensaje", "")));
                satellites.add(new Satellite("skywalker", new Position(100, -100), 115.5,
                                List.of("", "es", "", "", "secreto")));
                e = assertThrows(
                                InformationException.class,
                                () -> service.getSpaceshipData());
                assertEquals("INE1", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
        }

        @Test
        void testExceptionINE2() {
                List<Satellite> satellites = List.of(
                                new Satellite("kenobi", new Position(-500, -200), 100.0, null),
                                new Satellite("skywalker", new Position(100, -100), 115.5,
                                                List.of("", "es", "", "", "secreto")),
                                new Satellite("sato", new Position(500, 100), 142.7,
                                                List.of("este", "", "un", "", "")));
                when(repository.getSatellites()).thenReturn(satellites);
                InformationException e = assertThrows(
                                InformationException.class,
                                () -> service.getSpaceshipData());
                assertEquals("INE2", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());

                satellites.get(0).setDistance(null);
                satellites.get(0).setMessage(List.of("este", "", "", "mensaje", ""));
                e = assertThrows(
                                InformationException.class,
                                () -> service.getSpaceshipData());
                assertEquals("INE2", e.getCode());
                assertEquals("There is not enough information from satellites.", e.getMessage());
                
        }

        @Test
        void testExceptionINE3() {
                SatelliteRequest request = new SatelliteRequest(100.0, List.of("este", "", "", "mensaje", ""));
                when(repository.getSatelliteByName("darthvader")).thenReturn(null);
                InformationException e = assertThrows(
                                InformationException.class,
                                () -> service.setSatellite("darthvader", request));
                assertEquals("INE3", e.getCode());
                assertEquals("That satellite does not exist: 'darthvader'.", e.getMessage());
        }
}
