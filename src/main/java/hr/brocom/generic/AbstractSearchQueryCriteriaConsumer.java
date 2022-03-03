package hr.brocom.generic;

import hr.brocom.generic.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Setter
public class AbstractSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSearchQueryCriteriaConsumer.class);

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;
    private Class type;

    public AbstractSearchQueryCriteriaConsumer(final Predicate predicate, final CriteriaBuilder builder, final Root r, final Class type) {
        this.predicate = predicate;
        this.builder = builder;
        this.r = r;
        this.type = type;
    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void accept(final SearchCriteria param) {
        final OperationEnum operation = param.getOperation();
        final Path path = r.get(param.getKey());
        switch (operation) {
            case GTE:
                predicate = builder.and(predicate, builder
                        .greaterThanOrEqualTo(path, param.getValue().toString()));
                break;
            case GT:
                predicate = builder.and(predicate, builder
                        .greaterThan(path, param.getValue().toString()));
                break;
            case LTE:
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                        path, param.getValue().toString()));
                break;
            case LT:
                predicate = builder.and(predicate, builder.lessThan(
                        path, param.getValue().toString()));
                break;
            case EQ:
                createEqualsPredicate(path, param);
                break;
            case NEQ:
                predicate = builder.and(predicate, builder.notEqual(
                        path, param.getValue()));
                break;
            case LIKE:
                predicate = createLikeParameter(param);
                break;

            default:
                throw new IllegalArgumentException();
        }

    }

    private void createEqualsPredicate(final Path path, final SearchCriteria param) {
        if (path.getJavaType() == String.class) {
            predicate = builder.and(predicate, builder.like(builder.lower(
                    path), "%" + param.getValue().toString().toLowerCase() + "%"));
        } else if (path.getJavaType().isEnum()) {
            predicate = builder.and(predicate, builder.equal(
                    path.as(String.class), param.getValue().toString()));
        } else if (path.getJavaType() == Boolean.class) {
            predicate = builder.and(predicate, builder.equal(path, param.getValue()));
        } else if (path.getJavaType() == List.class) {
            predicate = builder.and(predicate, createListPredicate(path, param));
        } else if (BaseEntity.class.isAssignableFrom(path.getJavaType())) {
            predicate = builder.and(predicate, builder.equal(path.<String>get("id"), param.getValue()));
        } else {
            predicate = builder.and(predicate, builder.equal(
                    path, param.getValue().toString()));
        }
    }

    private <T extends BaseEntity> Predicate createListPredicate(final Path path, final SearchCriteria param) {

        try {
            final T t = ((Class<T>) ((ParameterizedType) type.getDeclaredField(param.getKey()).getGenericType())
                    .getActualTypeArguments()[0]).getConstructor().newInstance();
            t.setId(((Integer) param.getValue()).longValue());
            return builder.isMember(t, path);

        } catch (NoSuchFieldException | InvocationTargetException | InstantiationException
                | IllegalAccessException | NoSuchMethodException e) {
            LOGGER.error("Something went wrong", e);
        }

        return null;
    }

    private Predicate createLikeParameter(final SearchCriteria param) {
        final Object value = param.getValue();
        final Double doubleValue;
        if (value instanceof Integer) {
            doubleValue = ((Integer) value).doubleValue();
        } else if (value instanceof Double) {
            doubleValue = (Double) value;
        } else {
            return builder.and(predicate, builder.like(r.get(param.getKey()), param.getValue().toString()));
        }

        final Double start = getStart(doubleValue, param.getTolerance());
        final Double end = getEnd(doubleValue, param.getTolerance());

        return builder.and(predicate, builder.between(r.get(param.getKey()), start, end));

    }

    private Double getStart(final Double integer, final Integer tolerance) {
        if (tolerance == null) {
            return integer;
        }
        return integer - (integer * (tolerance.doubleValue() / 100));
    }

    private Double getEnd(final Double integer, final Integer tolerance) {
        if (tolerance == null) {
            return integer;
        }
        return integer + (integer * (tolerance.doubleValue() / 100));
    }

}
