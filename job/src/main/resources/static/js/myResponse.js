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
//修改获取工作列表视图
function changeJobList(data) {
    var jobList = data.data;
    var hasTail = false;
    var dom = "";
    for (var i=0;i<jobList.length;i++){
        var job = jobList[i];
        if(i%2==0){
            //奇数数列，加头部
            hasTail = false;
            dom = dom + "<div class='row'><div class='col-sm-6 e-left'><div class='e-desc'><h6><a>"+job.jobName+"</a></h6><p class='testP'>"+job.introduction+"</p><hr><ul class='e-tags'>";
            var jobTypes = job.jobType;
            for(var j=0;j<jobTypes.length;j++){
                var jobType = jobTypes[j];
                dom = dom+"<li>"+jobType+"</li>";
            }
            dom = dom +"</ul><hr><ul class='desc-list'><li><span>地点:</span>"+job.location+"</li><li><span>薪资:</span>"+job.salary+"</li><li><span>经验:</span>"+job.experience+"</li><li><span>发布者: </span>"+job.user.userName+"</li><li><span>发布时间: </span>"+job.pubTime+"</li></ul><input type='submit' value='申请职位' class='form-control bg-theme text-uppercase'></div></div>"
        }else {
            //偶数数列，加尾部
            hasTail = true;
            dom = dom + "<div class='col-sm-6 e-left'><div class='e-desc'><h6><a>"+job.jobName+"</a></h6><p class='testP'>"+job.introduction+"</p><hr><ul class='e-tags'>";
            var jobTypes = job.jobType;
            for(var j=0;j<jobTypes.length;j++){
                var jobType = jobTypes[j];
                dom = dom+"<li>"+jobType+"</li>";
            }
            dom = dom + "</ul><hr><ul class='desc-list'><li><span>地点:</span>"+job.location+"</li><li><span>薪资:</span>"+job.salary+"</li><li><span>经验:</span>"+job.experience+"</li><li><span>发布者: </span>"+job.user.userName+"</li><li><span>发布时间: </span>"+job.pubTime+"</li></ul><input type='submit' value='申请职位' class='form-control bg-theme text-uppercase'></div></div></div>";
        }
    }
    if(!hasTail){
        dom = dom + "</div>";
    }
    console.log(dom);
    $("#test").append(dom);
}