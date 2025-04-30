package qarenabe.qarenabe.service.BugType;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.repository.BugTypeRepository;

@Service
public class BugTypeServiceImpl implements BugTypeService {

    @Autowired
    private BugTypeRepository bugTypeRepository;
    @Override
    public BugType createBugType(BugType bugType) {
        try {
            BugType resEntity = bugTypeRepository.save(bugType);
            return resEntity;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<BugType> getBugTypes() {    
        try {
            List<BugType> listBugType = bugTypeRepository.findAll();
            return listBugType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public BugType getBugTypeById(Long id) {
        try {
            BugType bugType = bugTypeRepository.findById(id).get();
            return bugType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
