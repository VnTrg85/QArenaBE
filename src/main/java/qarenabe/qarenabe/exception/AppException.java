package qarenabe.qarenabe.exception;

import lombok.Getter;
import qarenabe.qarenabe.enums.ErrorCodeEnum;

@Getter
public class AppException extends  RuntimeException{
      ErrorCodeEnum errorCode;
    public AppException(ErrorCodeEnum errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
