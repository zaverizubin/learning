package in.focalworks.zubin.backend.service;

import java.util.Optional;

import in.focalworks.zubin.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T> {

	Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

	long countAnyMatching(Optional<String> filter);

}
