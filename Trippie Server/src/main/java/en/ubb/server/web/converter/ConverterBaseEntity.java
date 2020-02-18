package en.ubb.server.web.converter;

import en.ubb.server.core.model.BaseEntity;
import en.ubb.server.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Integer>, Dto extends BaseDto>
        extends Converter<Model, Dto> {

}