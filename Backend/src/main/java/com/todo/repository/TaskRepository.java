package com.todo.repository;

import com.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 1. Inherit from `JpaRepository`, which provides basic CRUD operations, and you don't need to write SQL yourself.
 * 2. `Long` is the type of the primary key ID.
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
