package camilo.opertationquasarfire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.builder.SpringApplicationBuilder;

@ExtendWith(MockitoExtension.class)
class ServletInitializerTests {
    @Mock
    private SpringApplicationBuilder springApplicationBuilder;

    @Test
    void testIt() {
        ServletInitializer servletInitializer = new ServletInitializer();
        when(springApplicationBuilder.sources(OpertationquasarfireApplication.class))
                .thenReturn(springApplicationBuilder);

        SpringApplicationBuilder result = servletInitializer.configure(springApplicationBuilder);

        verify(springApplicationBuilder).sources(OpertationquasarfireApplication.class);
        assertEquals(springApplicationBuilder, result);
    }
}
