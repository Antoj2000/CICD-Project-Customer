package ie.atu.customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "customer-service", url = "http://localhost:8080")

public interface CustomerServiceClient {

    @GetMapping("/customer/{email}")   //Retrieve customer by email
    Customer getCustomerByEmail(@PathVariable String email);

    @PutMapping("/customer/{email}")   //updates customer email
    Customer updateCustomer(@PathVariable String email, @RequestBody Customer customer);
}
