function signup() {
    const username = document.getElementById("username_reg").value;
    const password = document.getElementById("password_reg").value;
    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/register',
        headers: {
            'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")
        },
        data: JSON.stringify({
            "username": username,
            "password": password
        }),
        success: function (data) {
            clear_validation();
            const {response, message} = data;
            if (response === "failure") {
                const validation = document.getElementById("register_validation_" + data.target);
                validation.textContent = message;
                validation.style.display = "block";

            } else if (response === "success") {
                const success_validation = document.getElementById("register_validation_success");
                success_validation.textContent = message;
                success_validation.style.display = "block";
            }
        }
    });
}

function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const validation = document.getElementById("login_validation_username");
    $.ajax({
        type: "POST",
        url: '/login',
        headers: {
            'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")
        },
        data: jQuery.param({
            "username": username,
            "password": password
        }),
        credentials: 'include',
        success: function (data) {
            if (data.response === "success") {
                window.location.replace(window.location.href);
            } else if (data.response === "failure") {
                validation.textContent = data.message;
                validation.style.display = "block";
            }
        }
    });

}

function clear_validation() {
    document.getElementById("login_validation_username").style.display = "none";
    document.getElementById("register_validation_username").style.display = "none";
    document.getElementById("register_validation_password").style.display = "none";
    document.getElementById("register_validation_success").style.display = "none";
}