package en.ubb.server.web.converter;

public interface Converter<Model, Dto>{
    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
