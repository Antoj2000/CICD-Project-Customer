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

    @PutMapping("/{name}")
    public ResponseEntity<?>updateCustomer(@PathVariable String name, @RequestBody @Valid Customer updatedCustomers, BindingResult result){
        if (result.hasErrors()) {
            List<ErrorDetails>errors = new ArrayList<>();
            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorDetails(fieldName, errorMessage));
            });
            return ResponseEntity.badRequest().body(errors);
        }

        Customer updatedCustomer = myService.updateCustomer(name, updatedCustomers);
        if (updatedCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with Employee ID: " + name);
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String name){
        try {
            myService.deleteCustomer(name);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with Employee ID " + name + " not found");
        }
    }




}
