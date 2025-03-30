package qarenabe.qarenabe.exception;

import lombok.Getter;
import qarenabe.qarenabe.enums.ErrorCodeEnum;

@Getter
public class AppException extends  RuntimeException{
    private final ErrorCodeEnum errorCode;
    public AppException(ErrorCodeEnum errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
