package edu.ifpb.denarius_tracker.service;

import java.util.List;

public interface ServiceGeneric<T, ID> {

    public List<T> findAll();

    public T findById(ID id);

    public T save(T t);
    
}
