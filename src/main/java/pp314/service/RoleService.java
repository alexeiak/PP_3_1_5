package pp314.service;

import pp314.model.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);

    List<Role> getListRoles();

    List<Role> getRolesListById(List<Integer> id);

    Role getRoleById(Integer id);
}
