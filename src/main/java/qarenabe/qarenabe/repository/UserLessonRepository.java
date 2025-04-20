package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Lesson;
import qarenabe.qarenabe.entity.UserCourse;
import qarenabe.qarenabe.entity.UserLesson;

import java.util.List;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
    UserLesson findByUserCourseAndLesson(UserCourse userCourse, Lesson lesson);

    @Query("SELECT ul.isCompleted FROM UserLesson ul WHERE ul.userCourse.id = :userCourseId AND ul.lesson.id = :lessonId")
    boolean isLessonCompleted( Long userCourseId,  Long lessonId);

    List<UserLesson> findByUserCourse(UserCourse userCourse);
}
