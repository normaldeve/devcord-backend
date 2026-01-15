package com.junwoo.devcordbackend.domain.user.mapper;

import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.user.dto.UserSearchResponse;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "online", ignore = true)
    AuthDTO toAuthDTO(UserEntity userEntity);

    @Mapping(source = "id", target = "userId")
    UserSearchResponse toUserSearchRespose(UserEntity user);
}
