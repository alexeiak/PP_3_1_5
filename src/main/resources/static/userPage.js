// const userurl = '/api/user';
// const userurl = '/admin/current';
const userurl = '/user/current';
// const userurl = '/admin/users';

const authUser = fetch(userurl)
    .then(response => response.json())

console.log('\n!!! authUser: ', authUser)
console.log(authUser)

authUser.then(user => {
        let roles = ''
        user.roles.forEach(role => {
            roles += ' '
            roles += role.name
        })
        document.getElementById("navbar-email").innerHTML = user.email
        document.getElementById("navbar-roles").innerHTML = roles
    }
)


 async function getUserPage() {
    let page = await fetch(userurl);

    if(page.ok) {
        let user = await  page.json();
        getInformationAboutUser(user);
    } else {
        alert(`Error, ${page.status}`)
    }
}

function getInformationAboutUser(user) {
    const tableBody = document.getElementById('usertbody');

    let dataHtml = '';


    let roles = [];
    console.log('userSata', JSON.stringify(user))
    for (let role of user.roles) {roles
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""))
    }

    dataHtml =
            `<tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles}</td>   
            </tr>`

    tableBody.innerHTML = dataHtml;
}

getUserPage();