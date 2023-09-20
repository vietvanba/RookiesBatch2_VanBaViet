package com.example.RookieShop.dto;

import com.example.RookieShop.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
    private int role_id;
    private String role_name;

    public RoleDTO(int role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }
    public RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getRole_id(), role.getRole_name());
    }

    public Role toEntity(RoleDTO roleDTO) {

        return new Role(roleDTO.getRole_id(), roleDTO.getRole_name());
    }

    public List<RoleDTO> toListDTO(List<Role> listRole) {

        List<RoleDTO> listRoleDTO = new ArrayList<>();
        listRole.forEach(x -> {
            listRoleDTO.add(new RoleDTO(x.getRole_id(), x.getRole_name()));
        });

        return listRoleDTO;
    }
}
