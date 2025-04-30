package qarenabe.qarenabe.service.Device;

import java.util.List;

import qarenabe.qarenabe.entity.Device;

public interface DeviceService {
    public List<Device> getListDevicesByCategoryId(Long id);
    public Device createDevice(Device device);
}
