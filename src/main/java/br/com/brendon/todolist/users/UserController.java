package br.com.brendon.todolist.users;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/users")
public class UserController {
    /*
     * String(Texto)
     * Integer (Int) numero inteiros
     * double (double) numeros 0.0000
     * float (float) Numeros 0.000
     * Char( A C)
     * Date (data)
     * void = sem retorno
     */
    /*
     * body
     */
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            System.out.println("ja existe esse usuario");
            // message de erro para a requisicao
            // status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ja existe");
        }
        var passwordhashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordhashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }

}
