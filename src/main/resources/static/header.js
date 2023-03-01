const currentUser = fetch('/admin/current')
    .then(response => response.json());

console.log('\n!!!! currentUser: ' + currentUser)
console.log(currentUser)

currentUser.then(user => {
    // let roles = ''
    // let roles = getPrettyRoles(user).toString()

    // user.roles.forEach(role => {
    //     roles += ' '
    //     roles += role.name
    // })

    document.getElementById("navbar-email").innerHTML = user.email
    document.getElementById("navbar-roles").innerHTML = getPrettyRoles(user).toString()
})


function getPrettyRoles(user) {
    let roles = [];

    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""))
    }

    return roles
}