package ie.atu.customer;

import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor

public class ErrorDetails {
    private String errorStatus;
    private String errorMessage;
    // No-argument constructor
    public ErrorDetails() {
    }

    // Constructor with all arguments
    public ErrorDetails(String errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

    // Getters and setters
    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}