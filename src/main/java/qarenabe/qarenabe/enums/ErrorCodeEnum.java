package qarenabe.qarenabe.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCodeEnum {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    COURSE_EXISTED(1002, "Course Existed", HttpStatus.BAD_REQUEST),
    COURSE_NOT_EXISTED(1003, "Course Not Existed", HttpStatus.BAD_REQUEST),
    INVALID_COURSE_ID(1004, "Invalid Course ID ", HttpStatus.BAD_REQUEST);
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
