
// import { getPrettyRoles } from './utils.js';
// import { getUserPage } from './userPage.js';


// Header
const currentUser = fetch('/admin/current').then(response => response.json());

currentUser.then(user => {
    document.getElementById("navbar-email").innerHTML = user.email;
    document.getElementById("navbar-roles").innerHTML = getPrettyRoles(user).toString();
})


// Users tab
async function getAdminPage() {
    let currentUser = fetch('/admin/current').then(response => response.json());
    let page = await fetch('/admin/users');

    if(page.ok) {
        let listAllUser = await page.json();
        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`);
    }
}


function loadTableData(listAllUser) {
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


// Current user tab
async function getUserPage() {
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



function getPrettyRoles(user) {
    let roles = [];
    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""));
    }

    return roles;
}


getAdminPage();
getUserPage();

