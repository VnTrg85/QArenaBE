package qarenabe.qarenabe.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.enums.ErrorCodeEnum;

@ControllerAdvice
public class HandleException {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(exception.getErrorCode().getMessage());
        apiResponse.setCode(exception.getErrorCode().getCode());

        return ResponseEntity.status(exception.getErrorCode().getStatus()).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception exception) {
        exception.printStackTrace(); // Debug lỗi chưa bắt được
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setCode(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getCode());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
