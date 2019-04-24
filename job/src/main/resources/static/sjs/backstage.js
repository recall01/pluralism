/*用户账号页面*/
function userInfo() {
    //获取当前页码
    var currentPage = $("#currentPage option:selected").val();
    if(currentPage == null){
        currentPage =1;
    }
    getUsersInfo(currentPage);
    getUserPage();
}
function getUsersInfo(page) {
    //ajax请求后台数据
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/user/usersInfo?page="+page,
        success:function (data) {
            if(data.status == 200){
                $("#tablebody").children().remove();
                var users = data.data;
                var dom ="";
                for (var i=0;i<users.length;i++){
                    var user = users[i];
                    dom = dom+"<div class='row'><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userId+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userName+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userPhone+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userEmail+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.registerTime+"</div><div class='col-lg-1 col-md-1 col-sm-1 col-xs-1'>"+user.roleType+"</div><div class='col-lg-1 col-md-1 col-sm-1 col-xs-1'><button class='btn btn-success btn-xs' onclick='changeUser("+user.userId+")' data-toggle='modal' data-target='#changeSource'>修改</button><button class='btn btn-danger btn-xs' data-toggle='modal' onclick='deleteSource("+user.userId+")' data-target='#deleteSource'>删除</button></div></div>";
                }
                console.log(dom);
                $("#tablebody").append(dom);
            }else {
                alert("获取工作类型失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function addUser() {
    var userName = $("#userName").val();
    var userPhone = $("#userPhone").val();
    var userEmail = $("#userEmail").val();
    if(userName ==null||userName.length<2){
        alert("用户名不符合");
        return false;
    }
    if(userPhone ==null||userPhone.length!=11){
        alert("手机号不符合");
        return false;
    }
    if(userEmail ==null||userEmail.length<6){
        alert("邮箱不符合");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/user/register",
        data:{
            "userName":userName,
            "userPhone":userPhone,
            "userEmail":userEmail,
        },
        success:function (data) {
            if(data.status == 200){
                alert("注册成功");
                location.href="http://localhost:8080/backstage";
            }else {
                alert("注册失败"+data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function delectUser() {
    var userId = $("#d_userId").html();
    if(userId==null||""==userId){
        alert("获取数据失败");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/user/delectUser",
        data:{
            "userId":userId,
        },
        success:function (data) {
            if(data.status == 200){
                alert("删除成功");
                location.href="http://localhost:8080/backstage";
            }else {
                alert(data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function deleteSource(userId) {
    document.getElementById("d_userId").innerText = userId;
}
function changeUser(userId) {
    if(userId == null||""==userId){
        alert("获取用户信息失败");
        return false;
    }
    //ajax请求获取相关信息
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/user/getUserInfoById?userId="+userId,
        success:function (data) {
            if(data.status == 200){
                //填充数据
                var user = data.data;
                /*$("#tablebody").children().remove();
                var users = data.data;
                var dom ="";
                for (var i=0;i<users.length;i++){
                    var user = users[i];
                    dom = dom+"<div class='row'><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userId+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userName+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userPhone+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.userEmail+"</div><div class='col-lg-2 col-md-2 col-sm-2 col-xs-2'>"+user.registerTime+"</div><div class='col-lg-1 col-md-1 col-sm-1 col-xs-1'>"+user.roleType+"</div><div class='col-lg-1 col-md-1 col-sm-1 col-xs-1'><button class='btn btn-success btn-xs' onclick='changeUser("+user.userId+")' data-toggle='modal' data-target='#changeSource'>修改</button><button class='btn btn-danger btn-xs' data-toggle='modal' data-target='#deleteSource'>删除</button></div></div>";
                }
                console.log(dom);
                $("#tablebody").append(dom);*/
                document.getElementById("userId").value = user.userId;
                document.getElementById("c_userName").value = user.userName;
                document.getElementById("c_userPhone").value = user.userPhone;
                document.getElementById("c_userEmail").value = user.userEmail;
                document.getElementById("c_registerTime").value = user.registerTime;
            }else {
                alert("获取工作类型失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function getUserPage(cpage) {
    if(cpage == null){
        cpage = 1;
    }
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/user/getUserPage",
        success:function (data) {
            if(data.status == 200){
                $("#currentPage").children().remove();
                //填充数据
                var count = data.data;
                var page = Math.ceil(count/10);
                //填充currentPage
                //当cpage>1时，填充前面数据
                if(cpage > 1){
                    for (var i=1;i<cpage&&i<3;i++){
                        $("#currentPage").prepend("<option value ='"+(cpage-i)+"'>"+(cpage-i)+"</option>");
                    }
                }
                $("#currentPage").append("<option selected value ='"+cpage+"'>"+cpage+"</option>");
                //填充后面数据
                if(page>cpage){
                    for (var i=1;i<=(page-cpage)&&i<3;i++){
                        var c = Number(cpage) + Number(i);
                        $("#currentPage").append("<option value ='"+c+"'>"+c+"</option>");
                    }
                }

                document.getElementById("userTotalPage").innerText = "共"+page+"页";
            }else {
                alert("获取页数信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
//选页逻辑
$(function () {
    $("#currentPage").change(function (data) {
        var page = $("#currentPage option:selected").attr("value");
        //重新加载数据
        getUsersInfo(page);
        //从新修改页码
        getUserPage(page);
    })
})
/*学生管理界面*/
function studentInfo() {
    //获取当前页码
    var studentPage = $("#studentPage option:selected").val();
    if(studentPage == null){
        studentPage =1;
    }
    getStudentsInfo(studentPage);
    getStudentPage(studentPage);
}
function getStudentsInfo(page) {
    //ajax请求后台数据
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/student/getStudentsInfo?page="+page,
        success:function (data) {
            if(data.status == 200){
                $("#charbody").children().remove();
                var students = data.data;
                var dom ="";
                for (var i=0;i<students.length;i++){
                    var student = students[i];
                    if(student.introduction.length>28){
                        student.introduction = (student.introduction).substr(0,27)+"...";
                    }else if(student.introduction.length == 0){
                        student.introduction = "无";
                    }
                    if(student.specialty.length>18){
                        student.specialty = (student.specialty).substr(0,17)+"...";
                    }else if(student.specialty.length == 0){
                        student.specialty = "无";
                    }
                    dom = dom+"<div class='row'><div class='col-xs-1'>"+student.stuId+"</div><div class='col-xs-1'>"+student.user.userId+"</div><div class='col-xs-1'>"+student.user.userName+"</div><div class='col-xs-3'>"+student.introduction+"</div><div class='col-xs-2'>"+student.specialty+"</div><div class='col-xs-1'>"+student.salary+"</div><div class='col-xs-2'>"+student.jobType+"</div><div class='col-xs-1'><button class='btn btn-success btn-xs' data-toggle='modal' onclick='changeStudent("+student.userId+")' data-target='#changeChar'>修改</button></div></div>";
                }
                console.log(dom);
                $("#charbody").append(dom);
            }else {
                alert("获取学生信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function changeStudent(userId) {
    if(userId == null||""==userId){
        alert("获取学生信息失败");
        return false;
    }
    //ajax请求获取相关信息
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/student/getStudentInfoById?userId="+userId,
        success:function (data) {
            if(data.status == 200){
                //填充数据
                var student = data.data;
                document.getElementById("s_stuId").value = student.stuId;
                document.getElementById("s_introduction").value = student.introduction;
                document.getElementById("s_specialty").value = student.specialty;
                document.getElementById("s_salary").value = student.salary;
                document.getElementById("s_jobType").value = student.jobType;
                document.getElementById("s_userId").value = student.userId;
            }else {
                alert("获取学生信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function changeStudentInfo() {
    var userId = $("#s_userId").val();
    var stuId = $("#s_stuId").val();
    var introduction = $("#s_introduction").val();
    var specialty = $("#s_specialty").val();
    var salary = $("#s_salary").val();
    var jobType = $("#s_jobType").val();
    if(userId==null||""==userId){
        alert("获取用户信息失败");
        return false;
    }
    if(stuId==null||""==stuId||introduction==null||""==introduction||specialty==null||""==specialty||salary==null||""==salary||jobType==null||""==jobType){
        alert("数据不能为空");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/student/changeStudentInfo",
        data:{
            "userId":userId,
            "stuId":stuId,
            "introduction":introduction,
            "specialty":specialty,
            "salary":salary,
            "jobType":jobType,
        },
        success:function (data) {
            if(data.status == 200){
                location.href="http://localhost:8080/backstage";
            }else {
                alert(data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function getStudentPage(cpage) {
    if(cpage == null||0==cpage){
        cpage = 1;
    }
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/student/getStudentPage",
        success:function (data) {
            if(data.status == 200){
                $("#studentPage").children().remove();
                //填充数据
                var count = data.data;
                var page = Math.ceil(count/10);
                //填充currentPage
                //当cpage>1时，填充前面数据
                if(cpage > 1){
                    for (var i=1;i<cpage&&i<3;i++){
                        $("#studentPage").prepend("<option value ='"+(cpage-i)+"'>"+(cpage-i)+"</option>");
                    }
                }
                $("#studentPage").append("<option selected value ='"+cpage+"'>"+cpage+"</option>");
                //填充后面数据
                if(page>cpage){
                    for (var i=1;i<=(page-cpage)&&i<3;i++){
                        var c = Number(cpage) + Number(i);
                        $("#studentPage").append("<option value ='"+c+"'>"+c+"</option>");
                    }
                }
                document.getElementById("studentTotalPage").innerText = "共"+page+"页";
            }else {
                alert("获取页数信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
//选页逻辑
$(function () {
    $("#studentPage").change(function (data) {
        var page = $("#studentPage option:selected").attr("value");
        //重新加载数据
        getStudentsInfo(page);
        //从新修改页码
        getStudentPage(page);
    })
})
/*兼职管理界面*/
function jobsInfo() {
    //获取当前页码
    var jobPage = $("#jobPage option:selected").val();
    if(jobPage == null){
        jobPage =1;
    }
    getJobsInfo(jobPage);
    getJobPage(jobPage);
}
function getJobsInfo(page) {
    //ajax请求job后台数据
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/job/jobList?page="+page+"&count=10",
        success:function (data) {
            if(data.status == 200){
                $("#jobbody").children().remove();
                var jobs = data.data;
                var dom ="";
                for (var i=0;i<jobs.length;i++){
                    var job = jobs[i];
                    if(job.introduction.length>18){
                        job.introduction = (job.introduction).substr(0,17)+"...";
                    }else if(job.introduction.length == 0){
                        job.introduction = "无";
                    }
                    if(job.jobName.length>18){
                        job.jobName = (job.jobName).substr(0,17)+"...";
                    }else if(job.jobName.length == 0){
                        job.jobName = "无";
                    }
                    if(job.jobType.length>18){
                        job.jobType = (job.jobType).substr(0,17)+"...";
                    }else if(job.jobType.length == 0){
                        job.jobType = "无";
                    }
                    if(job.location.length>9){
                        job.location = (job.location).substr(0,8)+"...";
                    }else if(job.location.length == 0){
                        job.location = "无";
                    }
                    if(job.pubTime.length>11){
                        job.pubTime = (job.pubTime).substr(0,10);
                    }else if(job.pubTime.length == 0){
                        job.pubTime = "无";
                    }
                    dom = dom+"<div class='row'><div class='col-xs-2'>"+job.jobName+"</div><div class='col-xs-2'>"+job.introduction+"</div><div class='col-xs-2'>"+job.jobType+"</div><div class='col-xs-1'>"+job.location+"</div><div class='col-xs-1'>"+job.salary+"</div><div class='col-xs-1'>"+job.experience+"</div><div class='col-xs-1'>"+job.user.userName+"</div><div class='col-xs-1'>"+job.pubTime+"</div><div class='col-xs-1'><button class='btn btn-success btn-xs' data-toggle='modal' data-target='#changJobInfo' onclick='changeJob("+job.jobId+")'>修改</button><button class='btn btn-danger btn-xs' data-toggle='modal' data-target='#deleteJob'onclick='addJobId("+job.jobId+")'>删除</button></div></div>";
                }
                console.log(dom);
                $("#jobbody").append(dom);
            }else {
                alert("获取学生信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
    //ajax请求工作类型
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/user/jobType",
        success:function (data) {
            if(data.status == 200){
                $("#p_jobType").children().remove();
                var jobList = data.data;
                $("#p_jobType").prepend("<option value ='0'>所有</option>");
                for (var i=0;i<jobList.length;i++){
                    var job = jobList[i];
                    $("#p_jobType").append("<option value ='"+job.typeName+"'>"+job.typeName+"</option>");
                }
            }else {
                alert("获取工作类型失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function addJobId(jobId) {
    document.getElementById("j_jobId").innerText = jobId;
}
function deleteJob() {
    var jobId = $("#j_jobId").html();
    if(jobId==null||""==jobId){
        alert("获取数据失败");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/job/delectJobById",
        data:{
            "jobId":jobId,
        },
        success:function (data) {
            if(data.status == 200){
                alert("删除成功");
                location.href="http://localhost:8080/backstage";
            }else {
                alert(data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function changeJob(jobId) {
    if(jobId == null||""==jobId){
        alert("获取兼职信息失败");
        return false;
    }
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/job/getJobById?jobId="+jobId,
        success:function (data) {
            if(data.status == 200){
                //填充数据
                var job = data.data;
                document.getElementById("h_jobId").innerText = job.jobId;
                document.getElementById("j_jobName").value = job.jobName;
                document.getElementById("j_introduction").value = job.introduction;
                document.getElementById("j_jobType").value = job.jobType;
                document.getElementById("j_location").value = job.location;
                document.getElementById("j_salary").value = job.salary;
                document.getElementById("j_experience").value = job.experience;
            }else {
                alert("获取兼职信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function changeJobInfo() {
    var jobId = $("#h_jobId").html();
    var jobName = $("#j_jobName").val();
    var introduction = $("#j_introduction").val();
    var jobType = $("#j_jobType").val();
    var location = $("#j_location").val();
    var salary = $("#j_salary").val();
    var experience = $("#j_experience").val();
    if(jobId==null||""==jobId||jobName==null||""==jobName||introduction==null||""==introduction||jobType==null||""==jobType||location==null||""==location||salary==null||""==salary||experience==null||""==experience){
        alert("数据不能为空");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/job/changeJobInfo",
        data:{
            "jobId":jobId,
            "jobName":jobName,
            "introduction":introduction,
            "jobType":jobType,
            "location":location,
            "salary":salary,
            "experience":experience,
        },
        success:function (data) {
            if(data.status == 200){
                alert("修改成功");
                location.href="http://localhost:8080/backstage";
            }else {
                alert(data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function addJobType() {
    var jobType = $("#a_jobType").val();
    if(jobType==null||""==jobType){
        alert("工作类型不能为空");
        return false;
    }
    $.ajax({
        type:"POST",
        contentType:"application/x-www-form-urlencoded",
        url:"http://localhost:8080/job/addJobType",
        data:{
            "jobType":jobType,
        },
        success:function (data) {
            if(data.status == 200){
                alert("添加成功");
                location.href="http://localhost:8080/backstage";
            }else {
                alert(data.msg);
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
function getJobPage(cpage) {
    if(cpage == null||0==cpage){
        cpage = 1;
    }
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/job/getJobPage",
        success:function (data) {
            if(data.status == 200){
                $("#jobPage").children().remove();
                //填充数据
                var count = data.data;
                var page = Math.ceil(count/10);
                //填充currentPage
                //当cpage>1时，填充前面数据
                if(cpage > 1){
                    for (var i=1;i<cpage&&i<3;i++){
                        $("#jobPage").prepend("<option value ='"+(cpage-i)+"'>"+(cpage-i)+"</option>");
                    }
                }
                $("#jobPage").append("<option selected value ='"+cpage+"'>"+cpage+"</option>");
                //填充后面数据
                if(page>cpage){
                    for (var i=1;i<=(page-cpage)&&i<3;i++){
                        var c = Number(cpage) + Number(i);
                        $("#jobPage").append("<option value ='"+c+"'>"+c+"</option>");
                    }
                }
                document.getElementById("jobTotalPage").innerText = "共"+page+"页";
            }else {
                alert("获取页数信息失败");
            }
        },
        error:function (data) {
            alert("请求失败");
        }
    });
}
//选页逻辑
$(function () {
    $("#jobPage").change(function (data) {
        var page = $("#jobPage option:selected").attr("value");
        //重新加载数据
        getJobsInfo(page);
        //从新修改页码
        getJobPage(page);
    })
})