package qarenabe.qarenabe.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public enum SuccessCodeEnum {

    ADD_SUCCESS(1000, "Add Success", HttpStatus.OK),
    UPDATE_SUCCESS(1000, "Update Success", HttpStatus.OK),
    DELETE_SUCCESS(1000, "Delete Success", HttpStatus.OK),
    COMPLETE_COURSE(1000, "Congratulation, You Complete This Course ", HttpStatus.OK),
    EXITS_COMPLETE_COURSE(1000, "You Were Complete This Course ", HttpStatus.OK),
    ;

    SuccessCodeEnum(int code, String msg, HttpStatus statusCode) {
        this.code = code;
        this.msg = msg;
        this.statusCode = statusCode;
    }

    int code;
    String msg;
    HttpStatus statusCode;
}
