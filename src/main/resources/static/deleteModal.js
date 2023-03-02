const idField_delete = document.getElementById('id_del');
const nameField_delete = document.getElementById('name_del');
const lastNameField_delete = document.getElementById('lastname_del');
const ageField_delete = document.getElementById('age_del');
const emailField_delete = document.getElementById('email_del');
const roleField_delete = document.getElementById("delete-role");


async function deleteModalData(id) {
    let user = await fetch('/admin/users/' + id).then(response => response.json());

    idField_delete.value = `${user.id}`;
    nameField_delete.value = `${user.firstName}`;
    lastNameField_delete.value = `${user.lastName}`;
    ageField_delete.value = `${user.age}`;
    emailField_delete.value = `${user.email}`;

    Array.from(roleField_delete.options).forEach(option => {
        option.selected = user.roles.some(role => role.id.toString() === option.value);
    });

    const deleteModalWindow = document.getElementById("deleteModal");
    const newDeleteModalWindow = new bootstrap.Modal(deleteModalWindow);
    newDeleteModalWindow.show();
}


async function deleteUser() {
    let deleteUser = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    };

    const btn_closeDeleteModalWindow = document.getElementById("closeDelete");
    let urlDel = '/admin/users/' + idField_delete.value;

    fetch(urlDel, deleteUser).then(() => {
        btn_closeDeleteModalWindow.click();
        loadAdminPage();
    })
}
