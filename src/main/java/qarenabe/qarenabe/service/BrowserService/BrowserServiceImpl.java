package qarenabe.qarenabe.service.BrowserService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.entity.Browser;
import qarenabe.qarenabe.repository.BrowserRepository;

@Service
public class BrowserServiceImpl implements BrowserService {

    @Autowired
    private BrowserRepository browserRepository;
    @Override
    public Browser createBrowser(Browser browser) {
        try {
            Browser resEntity = browserRepository.save(browser);
            return resEntity;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Browser> getBrowsers() {    
        try {
            List<Browser> listBrowsers = browserRepository.findAll();
            return listBrowsers;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Browser getBrowserById(Long id) {
        try {
            Browser browser = browserRepository.findById(id).get();
            return browser;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
