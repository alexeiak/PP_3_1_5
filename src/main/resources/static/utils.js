export function getPrettyRoles(user) {
    let roles = [];

    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""))
    }

    return roles
}