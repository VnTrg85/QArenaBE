package qarenabe.qarenabe.service.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.Course;
import qarenabe.qarenabe.entity.Lesson;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.entity.UserCourse;
import qarenabe.qarenabe.entity.UserLesson;
import qarenabe.qarenabe.entity.UserRole;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.repository.CourseRepository;
import qarenabe.qarenabe.repository.LessonRepository;
import qarenabe.qarenabe.repository.UserCourseRepository;
import qarenabe.qarenabe.repository.UserLessonRepository;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.repository.UserRoleRepository;
import qarenabe.qarenabe.service.Security.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private  UserRepository userRepository;
    @Autowired 
    private SecurityService securityService;
    @Autowired 
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private UserLessonRepository  userLessonRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public User addUser(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        UserRole role = userRoleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new RuntimeException("Vai trò không hợp lệ!"));
        String encodePass = securityService.encode(request.getPassword());
        User user = new User();
        user.setPassword(encodePass);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setCreate_at(request.getCreate_at());
        user.setUserRole(role);

        return userRepository.save(user);
    }
      void addUserCourse(User user ) {
        if (user.getUserRole().getName().equals("TESTER")) {
            List<Course> allCourses = courseRepository.findAll();
            List<Long> courseIds = new ArrayList<>();
            for (Course item : allCourses) {
                Long id = item.getId();
                courseIds.add(id);
            }
            for (Course course : allCourses) {
                UserCourse userCourse = new UserCourse();
                userCourse.setUser(user);
                userCourse.setCourse(course);
                userCourse.setIsCompleted(false);
                userCourseRepository.save(userCourse);
            }
            addUserLesson(courseIds,user);
        }
    }
    public void addUserLesson(List<Long> courseIds,User user){
        for (Long courseId : courseIds) {

            UserCourse userCourse = userCourseRepository.findByUserIdAndCourseId(user.getId(), courseId)
                    .orElseThrow(() -> new AppException(ErrorCodeEnum.INVALID_COURSE_ID));
            List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
            List<UserLesson> userLessons = new ArrayList<>();
            for (Lesson lesson : lessons) {
                UserLesson userLesson = new UserLesson();
                userLesson.setUserCourse(userCourse);
                userLesson.setLesson(lesson);
                userLesson.setIsCompleted(false);
                userLessons.add(userLesson);
            }

            userLessonRepository.saveAll(userLessons);
        }
    }


    @Override
    public String updateUser(User user) {
        try {
            userRepository.save(user);
            return "Updated";
        } catch (Exception e) {
            return "Failed to update user";
        }
    }

    @Override
    public String deleteUser(Long id) {
        try {
            userRepository.deleteById(id);;
            return "Deleted";
        } catch (Exception e) {
            return "Failed to delete user";
        }
    }


    @Override
    public User getUser(Long id) {
        try {
            User user = userRepository.findById(id).get();
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public UserDTO getUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email).get();
            UserDTO userRes = new  UserDTO(user.getId(),user.getAvatar(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getUserRole().getId());
            return userRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
