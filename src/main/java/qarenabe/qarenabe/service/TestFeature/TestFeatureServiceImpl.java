package qarenabe.qarenabe.service.TestFeature;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.TestFeatureDTO;
import qarenabe.qarenabe.entity.TestFeature;
import qarenabe.qarenabe.repository.TestFeatureRepository;

@Service
public class TestFeatureServiceImpl implements TestFeatureService{
    @Autowired
    private TestFeatureRepository testFeatureRepository;
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
    public TestFeatureDTO createTestFeature(TestFeature testFeature) {
        try {
            TestFeature res = testFeatureRepository.save(testFeature);
            TestFeatureDTO val = new TestFeatureDTO(res.getId(), res.getName(),res.getInput(),res.getOutput(), res.getBugType(), res.getTestProject().getId());
            return val;
        } catch (Exception e) {
            throw new RuntimeException("Can not create Test Feature", e);
        }
    }

    
    
}
