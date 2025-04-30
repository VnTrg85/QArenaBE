

package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Device;
@Repository
public interface DeviceRepository  extends JpaRepository<Device,Long> {

    List<Device> findByCategoryDeviceId(Long id);
}

