package qarenabe.qarenabe.service.Device;

import qarenabe.qarenabe.entity.Device;
import qarenabe.qarenabe.repository.DeviceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Override
    public List<Device> getListDevicesByCategoryId(Long id) {
        try {
            List<Device> listDevices = deviceRepository.findByCategoryDeviceId(id);
            return listDevices;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Device createDevice(Device device) {
        try {
            Device deviceRes = deviceRepository.save(device);
            return deviceRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
