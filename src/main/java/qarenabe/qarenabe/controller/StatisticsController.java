package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.ProjectMonthlyStatsResponseDTO;
import qarenabe.qarenabe.dto.UserMonthlyStatsResponseDTO;
import qarenabe.qarenabe.service.StatisticsService.StatisticsService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@RequestMapping("/statistics")
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController {

    StatisticsService statisticsService;

    @GetMapping(value = "doGetUser")
    ApiResponse<UserMonthlyStatsResponseDTO> doGetUser(@RequestParam(value = "Year") int year, @RequestParam(value = "Month") int month) {
        return ApiResponse.<UserMonthlyStatsResponseDTO>builder().data(
                statisticsService.getUserRegistrationStatsByMonth(year, month))
                .build();
    }

    @GetMapping(value = "doGetProject")
    ApiResponse<ProjectMonthlyStatsResponseDTO> doGetProject(@RequestParam(value = "Year") int year, @RequestParam(value = "Month") int month) {
        return ApiResponse.<ProjectMonthlyStatsResponseDTO>builder().data(
                        statisticsService.getProjectStatsByMonth(year, month))
                .build();
    }


}
