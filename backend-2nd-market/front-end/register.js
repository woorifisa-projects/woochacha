var userEmail = document.getElementById("userEmail");
var userPwd = document.getElementById("userpassword");
var userNickname = document.getElementById("usernickname");
var userGender = document.getElementById("usergender");
var registerBtn = document.getElementById("userregister");


axios.defaults.withCredentials = true;

    registerBtn.addEventListener("click", function() {
        const request_data =  {
            "userEmail" : userEmail.value,
            "password" : userPwd.value,
            "nickname" : userNickname.value,
            "gender" : userGender.value
        }
        
        console.log(request_data);
        axios.post("http://localhost:8080/users/register", request_data)
        .then(()=>{
            console.log("######### success");
            location.href = "./mainPage.html";
        })

    });