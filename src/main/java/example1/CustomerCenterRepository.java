package example1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerCenterRepository extends CrudRepository<CustomerCenter, Long> {

    List<CustomerCenter> findByOrderId(Long orderId);
    //List<CustomerCenter> findByOrderId(Long orderId);

}