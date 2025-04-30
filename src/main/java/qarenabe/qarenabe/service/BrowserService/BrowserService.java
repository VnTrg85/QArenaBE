package qarenabe.qarenabe.service.BrowserService;

import java.util.List;

import qarenabe.qarenabe.entity.Browser;

public interface BrowserService {
    public Browser createBrowser(Browser browser);
    public List<Browser> getBrowsers();
    public Browser getBrowserById(Long id);
}
