
async function loadHeader() {
    const user = await fetch('/user/current').then(response => response.json());
    document.getElementById("navbar-email").innerHTML = user.email;
    document.getElementById("navbar-roles").innerHTML = getPrettyRoles(user).toString();
}


async function loadUserPage() {
     let page = await fetch('/user/current');
     let user = await page.json();
     getInformationAboutUser(user);
}

function getInformationAboutUser(user) {
    let dataHtml = '';
    let roles = getPrettyRoles(user);

    dataHtml = `
             <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles}</td>   
            </tr>
    `;

    const tableBody = document.getElementById('usertbody');
    tableBody.innerHTML = dataHtml;
}

loadHeader();
loadUserPage();



function getPrettyRoles(user) {
    let roles = [];
    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""));
    }
    return roles;
}
