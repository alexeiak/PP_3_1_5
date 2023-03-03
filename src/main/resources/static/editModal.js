const idField_edit = document.getElementById('edit-id');
const nameField_edit = document.getElementById('edit-first_name');
const lastnNmeField_edit = document.getElementById('edit-last_name');
const ageField_edit = document.getElementById('edit-age');
const emailField_edit = document.getElementById('edit-email');
const passwordField_edit = document.getElementById('edit-password');
const roleField_edit = document.getElementById('edit-role');


async function loadDataForEditModal(id) {
    let user = await fetch('/admin/users/' + id).then(response => response.json());

    idField_edit.value = `${user.id}`;
    nameField_edit.value = `${user.firstName}`;
    lastnNmeField_edit.value = `${user.lastName}`;
    ageField_edit.value = `${user.age}`;
    emailField_edit.value = `${user.email}`;
    passwordField_edit.value = ``;

    Array.from(roleField_edit.options).forEach(option => {
        option.selected = user.roles.some(role => role.id.toString() === option.value);
    });

    const editModalWindow = document.getElementById("editModal");
    const newEditModalWindow = new bootstrap.Modal(editModalWindow);
    newEditModalWindow.show();
}



async function editUser() {

    const btn_closeEditModalWindow = document.getElementById("editClose");
    const btn_submitEditForm = document.getElementById("editBtn");




    const emailHint = document.getElementById('email-hint');


    btn_submitEditForm.addEventListener('click', async function(event) {
        event.preventDefault(); // prevent form submission

        // const emailInput = document.getElementById('edit-email');

        const form_edit = document.getElementById('formForEditing');
        const enteredEmail = form_edit.email.value;

        console.log('\n!!!enteredEmail: ' + enteredEmail)

        const response = await fetch('/admin/check-email', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: enteredEmail })
        });

        const data = await response.json();

        console.log('\n!!!data: ' + data)

        if (data === true) {
            emailHint.textContent = 'Email already exists =(';
            btn_closeEditModalWindow.addEventListener('click', function() {
                emailHint.textContent = '';
                // loadAdminPage();
            });
        } else {
            let roles = Array.from(form_edit.roles.options)
                .filter(role => role.selected)
                .map(role => ({id: role.value}));

            const editUser = {
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


            // const btn_closeEditModalWindow = document.getElementById("editClose");
            let urlEdit = '/admin/users/' + idField_edit.value;

            fetch(urlEdit, editUser).then(() => {
                btn_closeEditModalWindow.click();
                emailHint.textContent = '';
                loadAdminPage();
            });
            // btn_submitEditForm.addEventListener('click', function() {
            //
            // });

            btn_closeEditModalWindow.addEventListener('click', function() {
                fetch(urlEdit, editUser).then(() => {
                    // btn_closeEditModalWindow.click();
                    emailHint.textContent = '';
                    loadAdminPage();
                });
            });
        }

    });


}






//
// async function editUser() {
//     const emailInput = document.getElementById('edit-email');
//     const btn_closeEditModalWindow = document.getElementById("editClose");
//     const btn_submitEditForm = document.getElementById("editBtn");
//
//
//     const form_edit = document.getElementById('formForEditing');
//     const enteredEmail = form_edit.email.value;
//
//     const emailHint = document.getElementById('email-hint');
//
//     const response = await fetch('/admin/check-email', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify({ email: enteredEmail })
//     });
//
//
//     const data = await response.json();
//
//     if (data === true) {
//         emailHint.textContent = 'Email already exists =(';
//         btn_closeEditModalWindow.addEventListener('click', function() {
//             emailHint.textContent = '';
//             // loadAdminPage();
//         });
//     } else {
//         let roles = Array.from(form_edit.roles.options)
//             .filter(role => role.selected)
//             .map(role => ({id: role.value}));
//
//         const editUser = {
//             method: 'PATCH',
//             headers: {
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify({
//                 firstName: form_edit.firstName.value,
//                 lastName: form_edit.lastName.value,
//                 age: form_edit.age.value,
//                 email: form_edit.email.value,
//                 password: form_edit.password.value,
//                 roles: roles
//             })
//         };
//
//
//         // const btn_closeEditModalWindow = document.getElementById("editClose");
//         let urlEdit = '/admin/users/' + idField_edit.value;
//
//         btn_submitEditForm.addEventListener('click', function() {
//             fetch(urlEdit, editUser).then(() => {
//                 btn_closeEditModalWindow.click();
//                 emailHint.textContent = '';
//                 loadAdminPage();
//             });
//         });
//
//         btn_closeEditModalWindow.addEventListener('click', function() {
//             fetch(urlEdit, editUser).then(() => {
//                 // btn_closeEditModalWindow.click();
//                 emailHint.textContent = '';
//                 loadAdminPage();
//             });
//         });
//     }
//
//
// }




//
// async function editUser() {
//     const form_edit = document.getElementById('formForEditing');
//
//     let roles = Array.from(form_edit.roles.options)
//         .filter(role => role.selected)
//         .map(role => ({id: role.value}));
//
//     let editUser = {
//         method: 'PATCH',
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify({
//             firstName: form_edit.firstName.value,
//             lastName: form_edit.lastName.value,
//             age: form_edit.age.value,
//             email: form_edit.email.value,
//             password: form_edit.password.value,
//             roles: roles
//         })
//     };
//
//
//     /* Email already exists check */
// const emailInput = document.getElementById('edit-email');
//
// emailInput.addEventListener('blur', async function() {
//     const enteredEmail = form_edit.email.value;
//
//     const response = await fetch('/admin/check-email', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify({ email: enteredEmail })
//     });
//
//     // boolean
//     const data = await response.json();
//     if (data.exists) {
//         emailInput.setCustomValidity('Email already exists =(');
//     } else {
//         emailInput.setCustomValidity('User is created!');
//     }
// });
//
//
//
// const btn_closeEditModalWindow = document.getElementById("editClose");
// let urlEdit = '/admin/users/' + idField_edit.value;
//
// fetch(urlEdit, editUser).then(() => {
//     btn_closeEditModalWindow.click();
//     loadAdminPage();
// });
// }
