package qarenabe.qarenabe.controller;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.TransactionResponseDTO;
import qarenabe.qarenabe.entity.Transaction;
import qarenabe.qarenabe.service.Transaction.TransactionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TransactionController {
    TransactionService transactionService;

    @GetMapping(value = "doGetTransaction")
    public ApiResponse<List<TransactionResponseDTO>> doGetTransaction() {
        return ApiResponse.<List<TransactionResponseDTO>>builder().data(
                transactionService.geAllTransactionResponseDtoList()
        ).build();
    }
}
