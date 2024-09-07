package by.alst.project.jdbc.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
