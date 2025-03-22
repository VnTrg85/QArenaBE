package qarenabe.qarenabe.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.entity.UserRole;
import qarenabe.qarenabe.service.UserRole.UserRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    
    @GetMapping("/getAll")
    @JsonManagedReference
     public ResponseEntity<List<UserRole>> getUserRoles() {
        return ResponseEntity.ok(userRoleService.getUserRoles());
    }
    @PostMapping("/add")
    public ResponseEntity<UserRole> createUserRole(@RequestBody UserRole userRole) {
        userRoleService.addUserRole(userRole);
        return new ResponseEntity<>(userRole, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable Long id, @RequestBody UserRole userRole) {
        userRole.setId(id);
        userRoleService.updateUserRole(userRole);;
        return new ResponseEntity<>(userRole,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id){
        userRoleService.deleteUserRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 

}
