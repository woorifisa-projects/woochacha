var userEmail = document.getElementById("userEmail");
var userPwd = document.getElementById("userpassword");
var userNickname = document.getElementById("usernickname");
var userGender = document.getElementById("usergender");
// var goRegisterBtn = document.getElementById("gouserregister");


axios.defaults.withCredentials = true;

var loginBtn = document.getElementById("user-login");
var registerBtn = document.getElementById("userregister");

loginBtn.addEventListener("click", function () {
  const request_data = {
    "userEmail": userEmail.value,
    "password": userPwd.value,
  };
  console.log(request_data);
  axios.post("http://localhost:8080/users/login", request_data).then(() => {
    console.log("######### success");
    location.href = "./mainPage.html";
  });
});
