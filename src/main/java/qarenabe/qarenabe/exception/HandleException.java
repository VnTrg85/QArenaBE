package qarenabe.qarenabe.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorMessage);
        apiResponse.setCode(ErrorCodeEnum.INVALID_INPUT.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCodeEnum.MISSING_REQUIRED_PARAMETER.getMessage());
        apiResponse.setCode(ErrorCodeEnum.INVALID_INPUT.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleInvalidFormat(HttpMessageNotReadableException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCodeEnum.INVALID_REQUEST_BODY.getMessage());
        apiResponse.setCode(ErrorCodeEnum.INVALID_INPUT.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDatabaseConstraint(DataIntegrityViolationException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCodeEnum.DATA_INTEGRITY_VIOLATION.getMessage());
        apiResponse.setCode(ErrorCodeEnum.DATA_INTEGRITY_VIOLATION.getCode());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCodeEnum.INVALID_PARAMETERS.getMessage() + ex.getMessage());
        apiResponse.setCode(ErrorCodeEnum.INVALID_PARAMETERS.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
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
