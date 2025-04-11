// package qarenabe.qarenabe.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import qarenabe.qarenabe.entity.BugType;
// import qarenabe.qarenabe.entity.CategoryDevice;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


// @RestController
// @RequestMapping("/categoryDevice")
// public class CategoryDeviceController {

//     @Autowired
//     private 


//     @GetMapping("/getAll")
//     public ResponseEntity<?> getListCategoryDevice(@RequestParam String param) {
//         Map<String, Object> response = new HashMap<>();
//          try {
//             CategoryDevice res = cate.createBugType(entity);
//             response.put("status", "success");
//             response.put("data", res);
//             return ResponseEntity.ok(response);
//         } catch (BadCredentialsException e) {
//             response.put("status", "error");
//             response.put("data", e.getMessage());
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//         }
//     }
    
// }
