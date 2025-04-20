package qarenabe.qarenabe.service.Device;

import qarenabe.qarenabe.entity.CategoryDevice;
import qarenabe.qarenabe.entity.Device;
import qarenabe.qarenabe.repository.CategoryDeviceRepository;
import qarenabe.qarenabe.repository.DeviceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private CategoryDeviceRepository categoryDeviceRepository;
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
            CategoryDevice categoryDevice = categoryDeviceRepository.findById(device.getCategoryDevice().getId()).orElseThrow(() -> new EntityNotFoundException("Category Device not found with ID"));
            device.setCategoryDevice(categoryDevice);
            Device deviceRes = deviceRepository.save(device);
            return deviceRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
