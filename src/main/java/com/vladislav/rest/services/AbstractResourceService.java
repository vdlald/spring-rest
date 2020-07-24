package com.vladislav.rest.services;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
abstract class AbstractResourceService<T, ID> {

    private final JpaRepository<T, ID> repository;
    private final Class<T> clazz;

    public List<T> getAll() {
        return repository.findAll();
    }

    public T getById(ID id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Could not find " + clazz.getSimpleName() + " with id: " + id));
    }

    public T save(T object) {
        return repository.save(object);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

}
