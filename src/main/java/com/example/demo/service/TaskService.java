package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Status;
import com.example.demo.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void add(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void changeStatus(Task task) {
        if (task.getStatus() == Status.COMPLETED) {
            task.setStatus(Status.TO_DO);
        } else {
            task.setStatus(Status.COMPLETED);
        }
        entityManager.merge(task);
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Task.class, id));
    }

    public List<Task> findAll() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t ORDER BY t.expirationDate", Task.class);
        return query.getResultList();
    }

    private List<Task> findAllByCategory(Category category) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.category=?1 ORDER BY t.expirationDate", Task.class);
        query.setParameter(1, category);
        return query.getResultList();
    }

    private List<Task> findAllByStatus(Status status) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.status=?1 ORDER BY t.expirationDate", Task.class);
        query.setParameter(1, status);
        return query.getResultList();
    }

    private List<Task> findAllByStatusAndCategory(Status status, Category category) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.status=?1 AND t.category=?2 ORDER BY t.expirationDate", Task.class);
        query.setParameter(1, status);
        query.setParameter(2, category);
        return query.getResultList();
    }

    public List<Task> getTasks(Status status, Category category) {
        List<Task> tasks;
        if (status != null) {
            if (category != null) {
                tasks = findAllByStatusAndCategory(status, category);
            } else {
                tasks = findAllByStatus(status);
            }
        } else {
            if (category != null) {
                tasks = findAllByCategory(category);
            } else {
                tasks = findAll();
            }
        }
        return tasks;
    }
}
