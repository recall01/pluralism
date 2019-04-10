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
//修改分页视图
function changePage(data,currentPage) {
    var maxPage = data.data;
    var dom ="";
    if(maxPage == null){
        alert("获取最大页数失败");
        return false;
    }
    if(currentPage == 1){
        dom = dom + "<li><a href='#'><i class='fa fa-angle-left'></i></a></li><li class='active'><a id='active' class='text-white bg-theme'>1</a></li>";
        if(maxPage - currentPage == 1){
            dom = dom + "<li><a>2</a></li><li> <a href='#' aria-label='Next'><i class='fa fa-angle-right'></i></a></li>";
        }else if(maxPage - currentPage >1){
            dom = dom + "<li><a>2</a></li><li><a>3</a></li><li> <a onclick='nextPage()' href='#' aria-label='Next'><i class='fa fa-angle-right'></i></a></li>";
        }
    }else {
        dom = dom + "<li><a href='#'><i class='fa fa-angle-left'></i></a></li><li><a>"+(currentPage-1)+"</a></li><li class='active'><a id='active' class='text-white bg-theme'>"+currentPage+"</a></li>";
        if(maxPage - currentPage>0){
            dom = dom + "<li><a>"+(currentPage+1)+"</a></li><li> <a onclick='nextPage()' href='#' aria-label='Next'><i class='fa fa-angle-right'></i></a></li>";
        }
    }
    console.log(dom);
    $("#pagination").append(dom);
}
function nextPage() {
    //获取当前页码数
    var currentPage = document.getElementById("active");
    if(currentPage == null){
        currentPage = 1 ;
    }else {
        currentPage = currentPage.innerText;
    }
    //当前页码数+1
    currentPage++;
    //获取下一页工作列表,并更新试图
    var Jobdata = getJobListData(currentPage);
    if(Jobdata == null){
        alert("获取工作列表失败");
        return false;
    }
    //先移除之前填充的html代码
    $("#test").children().remove();
    changeJobList(Jobdata);
    //获取最大页码
    var pageData = getMaxPage();
    if(pageData == null){
        alert("获取最大页码数据失败");
        return false;
    }
    //先移除之前填充的html代码
    $("#pagination").children().remove();
    changePage(pageData,currentPage);
//    changePage(Jobdata,currentPage);
}

function getJobListData(currentPage) {
    var jobData = null;
    $.ajax({
        type:"GET",
        async:false,
        url:"http://localhost:8080/job/jobList?page="+currentPage,
        success:function (data) {
            jobData = data;
        },
        error:function (data) {
            alert("请求失败");
        }
    });
    return jobData;
}
function getMaxPage() {
    var pageData = null;
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/job/getMaxPage",
        async:false,
        success:function (data) {
            pageData = data;
        },
        error:function (data) {
            alert("请求失败");
        }
    });
    return pageData;
}