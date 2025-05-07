package qarenabe.qarenabe.service.UserCourse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import qarenabe.qarenabe.dto.CompleteResponse;
import qarenabe.qarenabe.dto.LessonResponseDTO;
import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.entity.*;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.CertificateMapper;
import qarenabe.qarenabe.mapper.LessonMapper;
import qarenabe.qarenabe.mapper.UserCourseMapper;
import qarenabe.qarenabe.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Service
public class UserCourseServiceImpl implements UserCourseService {
    UserCourseRepository userCourseRepository;
    UserCourseMapper userCourseMapper;  // Sử dụng UserCourseMapper
    LessonMapper lessonMapper;
    CertificateRepository certificateRepository;
    AnswerRepository answerRepository;
    QuestionRepository questionRepository;
    CourseRepository courseRepository;

    @Override
    public List<UserCourseResponseDTO> getAllUserCourse(Long userId) {
        // Lấy danh sách các UserCourse của người dùng
        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);

        // Chuyển đổi UserCourse thành UserCourseResponseDTO
        return userCourses.stream().map(item -> {
            Course course = item.getCourse();

            // Tạo mới UserCourseResponseDTO và gán dữ liệu
            UserCourseResponseDTO responseDTO = userCourseMapper.toUserCourseResponse(item);

            // Thêm thông tin khóa học phụ thuộc (DependenceCourse)
            if (course.getRequiredCourse() != null) {
                responseDTO.setRequiredCourse(course.getRequiredCourse().getTitle());
            }

            List<LessonResponseDTO> lessons = course.getLessons().stream()
                    .map(lessonMapper::toLessonResponse)
                    .toList();

            responseDTO.setLessons(lessons);

            return responseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public CompleteResponse completeCourseAndUnlockNextCourse(Long userId, Long courseId, List<Long> selectedAnswerIds) {
        Course currentCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        UserCourse userCourse = userCourseRepository
                .findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.INVALID_COURSE_ID));

        List<Question> questions = questionRepository.findByCourseId(courseId);
        int totalQuestions = questions.size();

        if (totalQuestions == 0) {
            return new CompleteResponse("no_quiz", "No quiz available.", 0L);
        }

        int correctAnswers = (int) answerRepository.findAllById(selectedAnswerIds).stream()
                .filter(Answer::getIsCorrect)
                .count();

        long score = Math.round(((double) correctAnswers / totalQuestions) * 100);

        if (score >= 80) {
            userCourse.setIsCompleted(true);
            userCourseRepository.save(userCourse);

            boolean hasCertificate = certificateRepository.existsByUserAndCourse(userCourse.getUser(), currentCourse);
            if (!hasCertificate) {
                Certificate certificate = new Certificate();
                certificate.setUser(userCourse.getUser());
                certificate.setCourse(currentCourse);
                certificate.setTitleCertificate(currentCourse.getTitle());
                certificate.setDescription("You have successfully completed the course: " + currentCourse.getTitle());
                certificate.setImagePath(currentCourse.getLinkImg());
                certificateRepository.save(certificate);
            }

            for (Course dependentCourse : currentCourse.getDependentCourses()) {
                UserCourse dependentUserCourse = userCourseRepository
                        .findByUserIdAndCourseId(userId, dependentCourse.getId())
                        .orElse(null);
                if (dependentUserCourse != null) {
                    dependentUserCourse.setIsBlocked(false);
                    userCourseRepository.save(dependentUserCourse);
                }
            }
            return new CompleteResponse("pass", "You passed the quiz! 🎉", score);
        }
        return new CompleteResponse("fail", "You did not pass. Please try again.", score);
    }
}

