package ie.atu.customer;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")  //Base URL Mapping
@Validated
public class CustomerController {

    private final CustomerService myService;

    public CustomerController(CustomerService myService) {

        this.myService = myService;
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer, BindingResult result ){
        if (result.hasErrors()){
            List<ErrorDetails> errors = new ArrayList<>();
            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorDetails(fieldName, errorMessage));
            });
            return ResponseEntity.badRequest().body(errors);
        }
        //Delegates logic to service layer
        Customer savedCustomer = myService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = myService.getAllCustomers();
        return ResponseEntity.ok(customers);  // Returns 200 OK with list of customers
    }

    @PutMapping("/{email}")
    public ResponseEntity<?>updateCustomer(@PathVariable String email, @RequestBody @Valid Customer updatedCustomer, BindingResult result){
        if (result.hasErrors()) {
            List<ErrorDetails>errors = new ArrayList<>();
            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorDetails(fieldName, errorMessage));
            });
            return ResponseEntity.badRequest().body(errors);
        }

        Customer updatedCustomerEntity = myService.updateCustomer(email, updatedCustomer);
        if (updatedCustomerEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with email: " + email);
        }
        return ResponseEntity.ok(updatedCustomerEntity);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String email){
        try {
            myService.deleteCustomer(email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer account deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with email: " + email + " not found");
        }
    }




}
