package qarenabe.qarenabe.service.CategoryDeviceService;

import qarenabe.qarenabe.entity.CategoryDevice;
import qarenabe.qarenabe.repository.CategoryDeviceRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryDeviceServiceImpl implements CategoryDeviceService {
    
    @Autowired
    private CategoryDeviceRepository categoryDeviceRepository;


    @Override
    public List<qarenabe.qarenabe.entity.CategoryDevice> getListCategoryDevice() {   
        try {
            List<qarenabe.qarenabe.entity.CategoryDevice> listCategoryDevice = categoryDeviceRepository.findAll();
            return listCategoryDevice;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    public CategoryDevice createCategoryDevice(CategoryDevice categoryDevice) {
       try {
            CategoryDevice resCategoryService = categoryDeviceRepository.save(categoryDevice);
            return resCategoryService;
       } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
       }
    }   




}
