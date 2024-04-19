package org.example.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class RepositoryExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Test Message";
        RepositoryException exception = new RepositoryException(message);
        assertEquals(message, exception.getMessage());
    }
}

