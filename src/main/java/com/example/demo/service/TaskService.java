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

@Service
public class TaskService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void add(Task task) {
        entityManager.persist(task);
    }

    public List<Task> findAll() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t ORDER BY t.expirationDate", Task.class);
        return query.getResultList();
    }

    public List<Task> findAllByCategory(Category category) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.category=?1 ORDER BY t.expirationDate", Task.class);
        query.setParameter(1, category);
        return query.getResultList();
    }

    public List<Task> findAllByStatus(Status status) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.status=?1 ORDER BY t.expirationDate", Task.class);
        query.setParameter(1, status);
        return query.getResultList();
    }

    public List<Task> findAllByStatusAndCategory(Status status, Category category) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.status=?1 AND t.category=?2 ORDER BY t.expirationDate", Task.class);
        query.setParameter(1, status);
        query.setParameter(2, category);
        return query.getResultList();
    }
}
