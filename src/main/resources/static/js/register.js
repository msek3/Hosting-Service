let $password = $("#password");
let $confirmPassword = $("#confirm_password");
let nickname = $("#Name");
let email = $("#Email");

$("form span").hide();
$('#info').hide();

function isPasswordValid() {
    return $password.val().length > 7 && $password.val().length < 17;
}

function arePasswordsMatching() {
    return $password.val() === $confirmPassword.val();
}

function isNameValid() {
    let regex = /^[a-zA-Z0-9]+$/;
    return nickname.val().match(regex) && nickname.val().length > 3 && nickname.val().length < 17;
}

function isEmailValid() {
    let regex = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-z]+$/;
    return email.val().match(regex);
}

function canSubmit() {
    return isPasswordValid() && arePasswordsMatching() && isNameValid() && isEmailValid();
}

function passwordEvent(){
    if(isPasswordValid()) {
        $password.next().hide();
    } else {
        $password.next().show();
    }
}

function confirmPasswordEvent() {
    if(arePasswordsMatching()) {
        $confirmPassword.next().hide();
    } else {
        $confirmPassword.next().show();
    }
}

function emailEvent() {
    if(isEmailValid()) {
        email.next().hide();
    } else {
        email.next().show();
    }
}

function nameEvent() {
    if(isNameValid()) {
        nickname.next().hide();
    } else {
        nickname.next().show();
    }
}

function enableSubmitEvent() {
    $("#submit").prop("disabled", !canSubmit());
}


$password.focus(passwordEvent).keyup(passwordEvent).keyup(confirmPasswordEvent).keyup(enableSubmitEvent);
$confirmPassword.focus(confirmPasswordEvent).keyup(confirmPasswordEvent).keyup(enableSubmitEvent);
email.focus(emailEvent).keyup(emailEvent).keyup(enableSubmitEvent);
nickname.focus(nameEvent).keyup(nameEvent).keyup(enableSubmitEvent);

enableSubmitEvent();

function register() {
    let user = {
        id : null,
        name : nickname.val(),
        email : email.val(),
        password : $password.val(),
    };
    let userJson = JSON.stringify(user);

    $.ajax({
        method: "POST",
        url: "http://localhost:8080/register",
        contentType: "application/json",
        dataType: 'json',
        data: userJson,
        error: function (data) {
            if(data.status === 200){
                window.location.href="http://localhost:8080/";
            } else {
                $('#info').show();
            }
        }
    });

}
$('#submit').on('click', function (f) {
    f.preventDefault(p=> {register()})
});
