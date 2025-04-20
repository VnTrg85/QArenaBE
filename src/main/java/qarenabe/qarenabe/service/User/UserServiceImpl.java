package qarenabe.qarenabe.service.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.entity.*;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.repository.*;
import qarenabe.qarenabe.service.Security.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
      UserRepository userRepository;
    @Autowired 
      SecurityService securityService;
    @Autowired 
      UserRoleRepository userRoleRepository;
    @Autowired
      CourseRepository courseRepository;
    @Autowired
      UserCourseRepository userCourseRepository;
    @Autowired
      UserLessonRepository  userLessonRepository;
    @Autowired
      LessonRepository lessonRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public String addUser(User user) {
        try {
            String encodePass = securityService.encode(user.getPassword());
            user.setPassword(encodePass);
            Long roleId = user.getUserRole().getId();
            UserRole userRole = userRoleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("UserRole not found with id:" + roleId));
            user.setUserRole(userRole);
            userRepository.save(user);
            addUserCourse(user);
            return ""+user.getId();
        } catch (Exception e) {
            return e.getMessage();
        }
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
            // User userRes = new User();
            // userRes.setName(user.get().getName());
            // userRes.setPhone(user.get().getPhone());
            // userRes.setEmail(user.get().getEmail());
            // userRes.setUserRole(user.get().getUserRole());
            return user;
        } catch (Exception e) {
            return null;
        }

    }

}
