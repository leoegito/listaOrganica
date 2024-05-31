package leoegito.listaOrganica.Mappers;

import leoegito.listaOrganica.DTO.UserDto;
import leoegito.listaOrganica.Model.MyUser;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(MyUser myUser);

}
