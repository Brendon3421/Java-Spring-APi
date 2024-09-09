package br.com.brendon.todolist.users;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Data // getters e setters
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    // @Column(name = "usuario") mapeia no banco de dados a coluna usuario 
    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    // setters e getters

}
