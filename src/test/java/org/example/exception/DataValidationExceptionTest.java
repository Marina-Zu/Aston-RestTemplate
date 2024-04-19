package org.example.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataValidationExceptionTest {
    @Test
    public void testThrowExceptionWithMessage() {
        String message = "Test Message";
        try {
            throw new DataValidationException(message);
        } catch (DataValidationException e) {
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testConstructorWithNullMessage() {
        DataValidationException exception = new DataValidationException(null);
        assertNull(exception.getMessage());
    }
}
