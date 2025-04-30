package qarenabe.qarenabe.service.UserDeviceService;

import java.util.List;

import qarenabe.qarenabe.dto.UserDeviceDTO;
import qarenabe.qarenabe.entity.UserDevice;

public interface UserDeviceService {
    public List<UserDeviceDTO> getListUserDeviceByUserId(Long userId);
    public UserDeviceDTO createUserDevice(UserDevice userDevice);
    public UserDeviceDTO updateUserDevice(Long userDeviceId,String versionSelected, List<Long> browserIds);
    public String deleteById(Long id);

}
