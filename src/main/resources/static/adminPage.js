// const adminurl = '/admin/users';

// import { getPrettyRoles } from './utils.js';
// import { getUserPage } from './userPage.js';

async function getAdminPage() {

    let currentUser = fetch('/admin/current')
        .then(response => response.json());

    let page = await fetch('/admin/users');

    if(page.ok) {
        let listAllUser = await page.json();
        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}


async function getUserPage() {
    // let page = await fetch('/admin/current');

    let user = fetch('/admin/current')
        .then(response => response.json());


    // let user = await page.json();

    console.log('\n!!!! userPage: ' + user)
    console.log(user)
    console.log('!!!!\n')

    getInformationAboutUser(user);

}

function loadTableData(listAllUser) {
    const tableBody = document.getElementById('admintbody');
    let dataHtml = '';

    for (let user of listAllUser) {
        let roles = getPrettyRoles(user);
        // let roles = [];
        // for (let role of user.roles) {
        //     roles.push(" " + role.name.toString()
        //         .replaceAll("ROLE_", ""))
        // }

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

    tableBody.innerHTML = dataHtml;
}




function getInformationAboutUser(user) {
    const tableBody = document.getElementById('usertbody');

    console.log('\n\ntableBody');
    console.log(tableBody);
    // console.log('userSata', JSON.stringify(user))

    // let dataHtml = '';


    let roles = [];
    for (let role of user.roles) {roles
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""))
    }

    let dataHtml =
        `<tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles}</td>   
            </tr>`;

    tableBody.innerHTML = dataHtml;
}





function getPrettyRoles(user) {
    let roles = [];

    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""))
    }

    return roles
}

getAdminPage();
getUserPage();

