package codejava;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private ProductRepository repo;

    @RequestMapping("/")
    public String Hello() {
        return "hello world";
    }

    @GetMapping("/products")
    public List<Product> list() {
        return repo.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        try {
            Product product = repo.findById(id).get();
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public void add(@RequestBody Product product) {
        System.out.println(product.toString());
        repo.save(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product existProduct = repo.findById(id).get();
            existProduct.setName(product.getName());
            existProduct.setPrice(product.getPrice());
            repo.save(existProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
