package ie.atu.customer;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//Responsible for handling global exceptions across the application
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex){

        List<ErrorDetails> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            // For each field error, we extract the field name and the error message.
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            // Create an ErrorDetails object to store the field and the corresponding error message.
            ErrorDetails errorDetails = new ErrorDetails(fieldName, errorMessage);
            // Add the error details to the list.
            errors.add(errorDetails);
        });
        // Return the list of errors, which will be serialized into a JSON respons
        return errors;
    }
}
