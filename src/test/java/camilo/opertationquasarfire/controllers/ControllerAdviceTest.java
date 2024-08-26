package camilo.opertationquasarfire.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import camilo.opertationquasarfire.exceptions.InformationException;
import camilo.opertationquasarfire.exceptions.ResquestException;
import camilo.opertationquasarfire.models.ErrorResponse;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceTest {

    @InjectMocks
    private ControllerAdvice controllerAdvice;

    @Mock
    private ResquestException resquestException;

    @Mock
    private InformationException informationException;

    @Mock
    private HttpMessageNotReadableException httpMessageNotReadableException;

    @Mock
    private NoResourceFoundException noResourceFoundException;

    @Mock
    private HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException;

    @Test
    void testHttpMessageNotReadableExceptionHandler() {
        ResponseEntity<ErrorResponse> response = controllerAdvice.httpMessageNotReadableExceptionHandler(httpMessageNotReadableException);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Required request body is wrong or missing.", response.getBody().getMessage());

    }

    @Test
    void testHttpRequestMethodNotSupportedExceptionHandler() {
        ResponseEntity<ErrorResponse> response = controllerAdvice.httpRequestMethodNotSupportedExceptionHandler(httpRequestMethodNotSupportedException);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The request url was not found.", response.getBody().getMessage());
    }

    @Test
    void testInformationExceptionHandler() {
        when(informationException.getMessage()).thenReturn("There is not enough information from satellites.");
        ResponseEntity<ErrorResponse> response = controllerAdvice.informationExceptionHandler(informationException);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is not enough information from satellites.", response.getBody().getMessage());
    }

    @Test
    void testNoResourceFoundExceptionHandler() {
        ResponseEntity<ErrorResponse> response = controllerAdvice.noResourceFoundExceptionHandler(noResourceFoundException);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The request url was not found.", response.getBody().getMessage());
    }

    @Test
    void testResquestExceptionHandler() {
        when(resquestException.getMessage()).thenReturn("There is not enough information from satellites.");
        ResponseEntity<ErrorResponse> response = controllerAdvice.resquestExceptionHandler(resquestException);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is not enough information from satellites.", response.getBody().getMessage());
    }
}
