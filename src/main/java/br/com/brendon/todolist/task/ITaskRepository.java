package br.com.brendon.todolist.task;

import java.util.List; // Importar a interface correta do Java
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser); // Usar java.util.List

    TaskModel findByIdAndIdUser(UUID id, UUID idUser); // Corrigido

}
