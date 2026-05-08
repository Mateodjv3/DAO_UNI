package daos;

public interface DAOI<T> {
    void create(T entidad);
    T read(int id);
    void update(T entidad);
    void delete(int id);
}
