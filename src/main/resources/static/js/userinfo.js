function getUserDetails(){
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/userinfo/details",
    })
        .done(value => appendValue(value));
    getAdminInfo();
}

function appendValue(el) {
    const list = $('.user-details');
    let rolesString = "";
    let filesString ="";
    const url = "http://localhost:8080/";
    el.files.forEach(file =>{
        filesString += "<a href=\"" + url + file.id +"\">" + file.name + "</a><br>";
    });
    el.roles.forEach(role =>{
        rolesString += role.name + "<br>";
    });
    list.append(`<tr>
                    <td class="userDetail">Name</td>
                    <td class="userValue">${el.name}</td>
                 </tr>
                 <tr>
                    <td class="userDetail">Email</td>
                    <td class="userValue">${el.email}</td>
                 </tr>
                 <tr>
                    <td class="userDetail">Role</td>
                    <td class="userValue">${rolesString}</td>
                 </tr>
                 <tr>
                    <td class="userDetail">Files</td>
                    <td class="userValue">${filesString}</td>
                 </tr>`);
}

getUserDetails();

function getAdminInfo() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/userinfo/isAdmin",
        success: function (data) {
            if(data){
                $('#admin').append('<a class="navbar-brand navText" href="/admins">Admin</a>')
            }
        },
    })
}