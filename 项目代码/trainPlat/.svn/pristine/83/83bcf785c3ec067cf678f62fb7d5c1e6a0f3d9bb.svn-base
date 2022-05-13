/**
 * 登录
 */
$(document).ready(function(){
	var path=getRootPath();
	$("#btn_login").click(function(){
		if ($("#txt_userName").val()==""){
			alert("请输入工号！");
			return false;
		}if ($("#txt_userPass").val()==""){
			alert("请输入密码！");
			return false;
		}else{
			$("#loginForm").submit();
		}
	});
    //输入完密码捕获回车事件
    $("#txt_userPass").keydown(function(e){
        var curKey = e.which;
        if (curKey == 13) {
            $("#loginForm").submit();
        }
    });

});