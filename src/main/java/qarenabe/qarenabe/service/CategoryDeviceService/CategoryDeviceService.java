package qarenabe.qarenabe.service.CategoryDeviceService;

import java.util.List;

import qarenabe.qarenabe.entity.CategoryDevice;

public interface CategoryDeviceService {
    public List<CategoryDevice> getListCategoryDevice();
    public CategoryDevice createCategoryDevice(CategoryDevice categoryDevice);

}
