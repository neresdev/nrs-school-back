package com.nrs.school.back;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class ApplicationTest {
    
    @Test
    void applicationTest(){
        var args = new String[]{};
        Application.main(args);
    }
}
