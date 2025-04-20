package qarenabe.qarenabe.service.UserDeviceService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.UserDeviceDTO;
import qarenabe.qarenabe.entity.Browser;
import qarenabe.qarenabe.entity.Device;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.entity.UserDevice;
import qarenabe.qarenabe.repository.BrowserRepository;
import qarenabe.qarenabe.repository.DeviceRepository;
import qarenabe.qarenabe.repository.UserDeviceRepository;
import qarenabe.qarenabe.repository.UserRepository;

@Service
public class  UserDeviceServiceImpl implements UserDeviceService {
    @Autowired
    private UserDeviceRepository userDeviceRepository;
    @Autowired
    private BrowserRepository browserRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired 
    private UserRepository userRepository;
    @Override
    public List<UserDeviceDTO> getListUserDeviceByUserId(Long userId) {
        try {
            List<UserDevice> listUserDevices = userDeviceRepository.findAllByUser_Id(userId);
            List<Browser> browsers = browserRepository.findAll();
            List<UserDeviceDTO> listRes = new ArrayList<>();
            for (UserDevice userDevice : listUserDevices) {
                UserDeviceDTO temp = new UserDeviceDTO(userDevice.getId(), userDevice.getVersionSelected(),userDevice.getBrowserIds(),browsers,userDevice.getDevice());
                listRes.add(temp);
            }
            return listRes;

        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public UserDeviceDTO createUserDevice(UserDevice userDevice) {
        try {
            Device device = deviceRepository.findById(userDevice.getDevice().getId()).orElseThrow(() -> new EntityNotFoundException("Device not found with ID"));
            User user = userRepository.findById(userDevice.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            userDevice.setDevice(device);
            userDevice.setUser(user);
            List<Browser> browsers = browserRepository.findAll();
            UserDevice saveEntity =  userDeviceRepository.save(userDevice);
            UserDeviceDTO resEntity = new UserDeviceDTO(saveEntity.getId(),saveEntity.getVersionSelected(),saveEntity.getBrowserIds(),browsers, saveEntity.getDevice());
            return resEntity;
        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public UserDeviceDTO updateUserDevice(Long userDeviceId, String versionSelected, List<Long> browserIds) {
        try {
            UserDevice userDevice = userDeviceRepository.findById(userDeviceId).orElseThrow(() -> new EntityNotFoundException("Device not found with ID"));
            userDevice.setBrowserIds(browserIds);
            userDevice.setVersionSelected(versionSelected);
            List<Browser> browsers = browserRepository.findAll();
            UserDevice saveEntity =  userDeviceRepository.save(userDevice);
            UserDeviceDTO resEntity = new UserDeviceDTO(saveEntity.getId(),saveEntity.getVersionSelected(),saveEntity.getBrowserIds(),browsers, saveEntity.getDevice());
            return resEntity;
        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public String deleteById(Long id) {
      try {
            UserDevice userDevice = userDeviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User device not found with ID"));
            userDeviceRepository.delete(userDevice);
            return "Delete successfully";
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }


    
}
