package by.alst.project.jdbc.mapper;

public interface Mapper<T, F> {
    T mapFrom(F f);
}
