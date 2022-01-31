package models;

public interface AppRepository<T,ID> {

    public T save(T entity) throws Exception;
    public T findById(ID id) ;
    public T delete(T entity);
}
