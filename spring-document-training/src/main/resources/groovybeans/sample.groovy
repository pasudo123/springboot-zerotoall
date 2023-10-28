package groovybeans

import com.example.springdocumenttraining.chapter01.CoffeeRepository
import com.example.springdocumenttraining.chapter01.CoffeeService

beans {
    coffeeRepository(CoffeeRepository)
    coffeeService(CoffeeService, coffeeRepository)
}