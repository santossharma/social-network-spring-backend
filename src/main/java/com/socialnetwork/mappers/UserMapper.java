package com.socialnetwork.mappers;

import com.socialnetwork.dto.ProfileDTO;
import com.socialnetwork.dto.SignUpDTO;
import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.dto.UserSummaryDTO;
import com.socialnetwork.entities.User;
import com.socialnetwork.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Mapper(componentModel = "spring", imports = {UserStatus.class, Period.class, LocalDate.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(constant = "CREATED", target = "status")
    //@Mapping(target = "age", expression = "java(Period(LocalDate.now(), user.getBirthDate()).getYears()") // another way to set age field using expression
    @Mapping(target = "age", expression = "java(birthDateToAge(user))", dependsOn = "status") // using default function
    UserDTO toUserDTO(User user);

    default int birthDateToAge(User user) {
        Period period = Period.between(LocalDate.now(), user.getBirthDate());
        return period.getYears();
    }

    UserSummaryDTO toUserSummaryDTO(User user);
    List<UserSummaryDTO> toUserSummaryDTOs(List<User> user);

    @Mapping(source = "id", target = "userDTO.id")
    @Mapping(source = "firstName ", target = "userDTO.firstName")
    @Mapping(source = "lastName", target = "userDTO.lastName")
    ProfileDTO toProfileDTO(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDTO signUpDTO);

}
