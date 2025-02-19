package br.com.mysamples.processor;

import br.com.six2six.fixturefactory.processor.Processor;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
@Slf4j
@Transactional
public class EntityManagerProcessor implements Processor {


	private final EntityManager entityManager;


	public EntityManagerProcessor(final EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public void execute(final Object result) {
		if (result.getClass().isAnnotationPresent(Entity.class)) {
			try {
				persisteCompositeClass(result);
				findOrPersist(result);
			} catch (IllegalAccessException illegalAccessException) {
				log.error("Error to access the fields ", illegalAccessException);
			}

		}
	}


	private Object findOrPersist(Object result) throws IllegalAccessException {
		for (Field field : result.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)) {
				field.setAccessible(true);
				final Object value = field.get(result);
				final Object databasedObject = entityManager.find(result.getClass(), value);
				if (Objects.nonNull(value) && Objects.nonNull(databasedObject)) {
					return databasedObject;
				}
				entityManager.persist(result);
				entityManager.flush();
				break;
			}
		}
		return result;
	}

	private void persisteCompositeClass(Object result) throws IllegalAccessException {
		for (Field field : result.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(ManyToOne.class)) {
				field.setAccessible(true);
				final Object manyToOneField = field.get(result);
				if (Objects.nonNull(manyToOneField)) {

					persisteCompositeClass(manyToOneField);
					Object returnedValue = findOrPersist(manyToOneField);
					field.set(result, returnedValue);

				}
			}
		}
	}
}
