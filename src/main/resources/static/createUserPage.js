const form_newUser = document.getElementById('formForNewUser');
const selectedRoles = document.querySelector('#roles').selectedOptions;

form_newUser.addEventListener('submit', addNewUser);

async function addNewUser(event) {
    event.preventDefault();

    let listOfRole = [];
    for (let i = 0; i < selectedRoles.length; i++) {
        listOfRole.push({
            id:selectedRoles[i].value
        });
    }

    let createUser = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            firstName: form_newUser.firstname.value,
            lastName: form_newUser.lastname.value,
            age: form_newUser.age.value,
            email: form_newUser.email.value,
            password: form_newUser.password.value,
            roles: listOfRole
        })
    };


    await fetch('/admin/users', createUser).then(() => {
        form_newUser.reset();
        loadAdminPage();

        const tabButton = document.querySelector('#user_table-tab');
        tabButton.click();
    });
}
