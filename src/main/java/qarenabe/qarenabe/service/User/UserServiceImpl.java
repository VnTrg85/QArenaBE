package qarenabe.qarenabe.service.User;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
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
import qarenabe.qarenabe.dto.MemberResponseDTO;
import qarenabe.qarenabe.entity.*;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.repository.*;
import qarenabe.qarenabe.service.Security.*;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
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
        addUserCourse(user);

        return userRepository.save(user);
    }
      void addUserCourse(User user ) {
        if (user.getUserRole().getName().equals("tester")) {
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
    public UserDTO updateUser(User user) {
        try {
            User userRes = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            userRes.setName(user.getName());
            userRes.setPhone(user.getPhone());
            userRes.setAddress(user.getAddress());
            userRes.setCity(user.getCity());
            userRes.setDate_of_birth(user.getDate_of_birth());
            userRepository.save(userRes);
            UserDTO userdto = new  UserDTO(userRes.getId(),userRes.getAvatar(), userRes.getName(), userRes.getEmail(), userRes.getPhone(), userRes.getAddress(),userRes.getCity(),userRes.getDate_of_birth(),userRes.getCreate_at(), userRes.getUserRole().getId());
            return userdto;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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

    @Override
    public UserDTO getUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email).get();
            UserDTO userRes = new  UserDTO(user.getId(),user.getAvatar(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getCity(),user.getDate_of_birth(),user.getCreate_at(), user.getUserRole().getId());
            return userRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    @Override
    public void updateAvatar(UserDTO user) {
        try {
            User userRes = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            userRes.setAvatar(user.getAvatar());
            userRepository.save(userRes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public UserDTO getPayoutInfor(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            UserDTO userDTO = new UserDTO(user.getPayout_method(), user.getPayout_account_info());
            return userDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDTO updatePayoutInfor(UserDTO userDTO) {
        try {
            User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            user.setPayout_method(userDTO.getPayout_method());
            user.setPayout_account_info(userDTO.getPayout_account_info());
            userRepository.save(user);
            return userDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
