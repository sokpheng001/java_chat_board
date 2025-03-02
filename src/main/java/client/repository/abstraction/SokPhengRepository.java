package client.repository.abstraction;

import java.util.List;

/**
 * <p>This interface is created for define the task of Entity repository</p>
 * @author Kim Chansokpheng
 * @version 1.0
 * @param <C> is the class model that repository is defined for
 * @param <I> is the data type of Entity class ID
 *
 */
public interface SokPhengRepository<C, I> {
    /**
     * <p>This methods is used for saving or creating entity to database</p>
     * @param c is the object of class entity you want to save
     * @return return type as class ID data type
     */
    I save (C c);
    List<C> findAll();
    I delete(I id);
    I update(I id);
}
