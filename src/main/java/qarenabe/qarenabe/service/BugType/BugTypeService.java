package qarenabe.qarenabe.service.BugType;

import java.util.List;

import qarenabe.qarenabe.entity.BugType;

public interface BugTypeService {
    public BugType createBugType(BugType bugType);
    public List<BugType> getBugTypes();
    public BugType getBugTypeById(Long id);
}
