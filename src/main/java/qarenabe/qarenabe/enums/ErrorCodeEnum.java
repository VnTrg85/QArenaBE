package qarenabe.qarenabe.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCodeEnum {

    INVALID_PARAMETERS(9994, "Invalid parameters: ", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_VIOLATION(9995, "Data integrity violation in the database", HttpStatus.CONFLICT),
    INVALID_REQUEST_BODY(9996, "Invalid request body or data format", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(9997, "Invalid input data", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_PARAMETER(9998, "Missing required parameter", HttpStatus.CONFLICT),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    COURSE_EXISTED(1002, "Course Existed", HttpStatus.BAD_REQUEST),
    COURSE_NOT_EXISTED(1003, "Course Not Existed", HttpStatus.BAD_REQUEST),
    INVALID_COURSE_ID(1004, "Invalid Course ID ", HttpStatus.BAD_REQUEST),
    REQUIRED_COURSE_NOT_FOUND(105, "Required Course Not Found ", HttpStatus.BAD_REQUEST),
    COURSE_DEPENDENCY(105, "course Is Dependent ", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    IMG_IS_NULL(1006, "link img Null", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1009, "Token is invalid", HttpStatus.UNAUTHORIZED),
    LESSON_EXISTED(1012, "Lesson Existed", HttpStatus.BAD_REQUEST),
    LESSON_NOT_EXISTED(1013, "Lesson Not Existed", HttpStatus.BAD_REQUEST),
    INVALID_LESSON_ID(1014, "Invalid Lesson ID ", HttpStatus.BAD_REQUEST),
    ;

    ErrorCodeEnum(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    int code;
    String message;
    HttpStatus status;
}
