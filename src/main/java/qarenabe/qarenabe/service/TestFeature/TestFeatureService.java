package qarenabe.qarenabe.service.TestFeature;

import java.util.List;

import qarenabe.qarenabe.dto.TestFeatureDTO;
import qarenabe.qarenabe.entity.TestFeature;

public interface TestFeatureService {
    public List<TestFeatureDTO>  getFeaturesByTestProject(Long testProjectId);
    public TestFeatureDTO createTestFeature(TestFeature testFeature);
}
