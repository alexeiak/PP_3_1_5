const idField_edit = document.getElementById('edit-id');
const nameField_edit = document.getElementById('edit-first_name');
const lastnNmeField_edit = document.getElementById('edit-last_name');
const ageField_edit = document.getElementById('edit-age');
const emailField_edit = document.getElementById('edit-email');
const roleField_edit = document.getElementById('edit-role');


async function loadDataForEditModal(id) {
    let user = await fetch('/admin/users/' + id).then(response => response.json());

    idField_edit.value = `${user.id}`;
    nameField_edit.value = `${user.firstName}`;
    lastnNmeField_edit.value = `${user.lastName}`;
    ageField_edit.value = `${user.age}`;
    emailField_edit.value = `${user.email}`;

    Array.from(roleField_edit.options).forEach(option => {
        option.selected = user.roles.some(role => role.id.toString() === option.value);
    });

    const editModalWindow = document.getElementById("editModal");
    const newEditModalWindow = new bootstrap.Modal(editModalWindow);
    newEditModalWindow.show();
}


async function editUser() {
    const form_edit = document.getElementById('formForEditing');

    let roles = Array.from(form_edit.roles.options)
        .filter(role => role.selected)
        .map(role => ({id: role.value}));

    let editUser = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            firstName: form_edit.firstName.value,
            lastName: form_edit.lastName.value,
            age: form_edit.age.value,
            email: form_edit.email.value,
            password: form_edit.password.value,
            roles: roles
        })
    };

    const btn_closeEditModalWindow = document.getElementById("editClose");
    let urlEdit = '/admin/users/' + idField_edit.value;

    await fetch(urlEdit, editUser).then(() => {
        btn_closeEditModalWindow.click();
        loadAdminPage();
    });
}
