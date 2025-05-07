package qarenabe.qarenabe.service.StatisticsService;

import qarenabe.qarenabe.dto.ProjectMonthlyStatsResponseDTO;
import qarenabe.qarenabe.dto.UserMonthlyStatsResponseDTO;

public interface StatisticsService {
    UserMonthlyStatsResponseDTO getUserRegistrationStatsByMonth(int year, int month);

    ProjectMonthlyStatsResponseDTO getProjectStatsByMonth(int year, int month);
}
