package qarenabe.qarenabe.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessCodeEnum {

    ADD_SUCCESS(1000, "Add Success", HttpStatus.OK),
    UPDATE_SUCCESS(1001, "Update Success", HttpStatus.OK),
    DELETE_SUCCESS(1002, "Delete Success", HttpStatus.OK);
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
