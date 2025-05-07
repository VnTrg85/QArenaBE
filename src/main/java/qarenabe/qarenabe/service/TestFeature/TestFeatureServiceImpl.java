package qarenabe.qarenabe.service.TestFeature;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.TestFeatureDTO;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.entity.TestFeature;
import qarenabe.qarenabe.repository.BugTypeRepository;
import qarenabe.qarenabe.repository.TestFeatureRepository;
import qarenabe.qarenabe.service.BugType.BugTypeService;

@Service
public class TestFeatureServiceImpl implements TestFeatureService{
    @Autowired
    private TestFeatureRepository testFeatureRepository;
    @Autowired 
    private BugTypeService bugTypeService;
    @Override
    public List<TestFeatureDTO> getFeaturesByTestProject(Long testProjectId) {
        try {
            List<TestFeature> listData =  testFeatureRepository.findAllByTestProjectId(testProjectId);
            List<TestFeatureDTO> listRes = new ArrayList<>();
            for (TestFeature testFeature : listData) {
                TestFeatureDTO val = new TestFeatureDTO(testFeature.getId(), testFeature.getName());
                listRes.add(val);
            }
            return listRes;
        }catch (EntityNotFoundException e) {
            throw new RuntimeException("Error happen when retrieving data");
        }
    }
    
    @Override
    public List<TestFeatureDTO> getDetailFeaturesByTestProject(Long testProjectId) {
        try {
            List<TestFeature> listData =  testFeatureRepository.findAllByTestProjectId(testProjectId);
            List<TestFeatureDTO> listRes = new ArrayList<>();
            for (TestFeature testFeature : listData) {
                ArrayList<BugType> bugtypeList = new ArrayList<>();
                if(testFeature.getBugTypeId() != null) {
                    for (Long bugTypeID  : testFeature.getBugTypeId()) {
                        BugType bugType = bugTypeService.getBugTypeById(bugTypeID);
                        bugtypeList.add(bugType);
                    }
                }
                
                TestFeatureDTO val = new TestFeatureDTO(testFeature.getId(), testFeature.getName(), testFeature.getInput(),testFeature.getOutput(), bugtypeList);
                listRes.add(val);
            }
            return listRes;
        }catch (EntityNotFoundException e) {
            throw new RuntimeException("Error happen when retrieving data");
        }
    }
    @Override
    public TestFeatureDTO createTestFeature(TestFeature testFeature) {
        try {
            TestFeature res = testFeatureRepository.save(testFeature);
            ArrayList<BugType> bugtypeList = new ArrayList<>();
            if(testFeature.getBugTypeId() != null) {
                for (Long bugTypeID  : res.getBugTypeId()) {
                    BugType bugType = bugTypeService.getBugTypeById(bugTypeID);
                    bugtypeList.add(bugType);
                }
            }
            TestFeatureDTO val = new TestFeatureDTO(res.getId(), res.getName(),res.getInput(),res.getOutput(), bugtypeList);
            return val;
        } catch (Exception e) {
            throw new RuntimeException("Can not create Test Feature", e);
        }
    }

    @Override
    public TestFeatureDTO getFeatureById(Long id) {
        try {
            TestFeature testFeature = testFeatureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Test feature not found with ID"));
            ArrayList<BugType> bugtypeList = new ArrayList<>();
            if(testFeature.getBugTypeId() != null) {
                for (Long bugTypeID  : testFeature.getBugTypeId()) {
                    BugType bugType = bugTypeService.getBugTypeById(bugTypeID);
                    bugtypeList.add(bugType);
                }
            }
            TestFeatureDTO res = new TestFeatureDTO(testFeature.getId(),testFeature.getName(),testFeature.getInput(), testFeature.getOutput(), bugtypeList);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Boolean deleteTestFeature(Long id) {
        try {
            TestFeature testFeature = testFeatureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Test feature not found with ID"));
            testFeatureRepository.delete(testFeature);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    
    
}
