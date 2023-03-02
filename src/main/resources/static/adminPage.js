// import { getPrettyRoles } from 'utils.js';

async function loadHeader() {
    const user = await fetch('/user/current').then(response => response.json());
    document.getElementById("navbar-email").innerHTML = user.email;
    document.getElementById("navbar-roles").innerHTML = getPrettyRoles(user).toString();
}

/* Users tab */
async function loadAdminPage() {
    let page = await fetch('/admin/users');
    let listAllUser = await page.json();
    setTableData(listAllUser);
}

function setTableData(listAllUser) {
    let dataHtml = '';

    for (let user of listAllUser) {
        let roles = getPrettyRoles(user);

        dataHtml += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${roles}</td>
                        <td>
                            <button type="button" class="btn btn-primary" data-bs-toogle="modal"
                            data-bs-target="#editModal" 
                            onclick="loadDataForEditModal(${user.id})">Edit</button>
                        </td>
                            
                        <td>
                            <button class="btn btn-danger" data-bs-toogle="modal"
                            data-bs-target="#deleteModal" 
                            onclick="deleteModalData(${user.id})">Delete</button>
                        </td>
                       
                    </tr>
        `}

    const tableBody = document.getElementById('admintbody');
    tableBody.innerHTML = dataHtml;
}


/* Current user tab */
async function loadUserPage() {
    let user = fetch('/admin/current').then(response => response.json());
    getInformationAboutUser(user);
}

function getInformationAboutUser(user) {
    let roles = getPrettyRoles(user);

    let dataHtml = `
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
loadAdminPage();
loadUserPage();



function getPrettyRoles(user) {
    let roles = [];
    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""));
    }
    return roles;
}

