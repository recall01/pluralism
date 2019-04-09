/*            导航栏事件            */
//注册按钮点击事件
$("#register").on("click",function () {
    var pass2 = document.getElementById("password2").value;
    var pass1 = document.getElementById("password1").value;
    if (pass1 != pass2){
        alert("Passwords Don't Match");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/user/register",
        data:$('#registerForm').serialize(),
        success:function (data) {
            if(data.status == 200){
                alert("注册成功");
                location.href="http://localhost:8080/index";
            }else {
                alert("注册失败"+data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
    return false;
});
//登录按钮点击事件
$("#login").on("click",function () {
    var userPhone = $("#userPhone").val();
    var userPassword = $("#userPassword").val();
    if(userPhone == null){
        alert("用户名不能为空");
        return false;
    }
    if(userPassword == null){
        alert("密码不能为空");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/user/login",
        data:{
            "userPhone":userPhone,
            "userPassword":userPassword
        },
        success:function (data) {
            if(data != null){
                location.href="index.html";
            }else {
                alert("登录失败");
            }
        },
        error:function (data) {
            alert("请求失败"+userPhone+" "+userPassword);
        }
    });
    return false;
});
$("#userInfo").on("click",function () {
    var userId = $("#userId").html();
    if(userId == null||"" == userId){
        alert("请求参数出错,请重新登录");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/user/userInfo",
        data:{
            "userId":userId,
        },
        success:function (data) {
            if(data.status == 200){
                location.href="http://localhost:8080/studentInfo";
            }else {
                alert(data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
});
//退出系统按钮
$("#exit").on("click",function () {
    location.href="http://localhost:8080/exit";
});