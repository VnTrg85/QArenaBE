
package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.CategoryDevice;
@Repository
public interface CategoryDeviceRepository  extends JpaRepository<CategoryDevice,Long> {

    
}

