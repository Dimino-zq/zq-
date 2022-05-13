/**
 * 登录
 */
$(document).ready(function(){
	var path=getRootPath();
	
	$("#btn_login").click(function(){
		
		if ($("#txt_userName").val()==""){
			alert("用户名为空");
			return false;
		}
		$("#loginForm").submit();
	});
    //输入完密码捕获回车事件
    $("#txt_userPass").keydown(function(e){
        var curKey = e.which;
        if (curKey == 13) {
            $("#loginForm").submit();
        }
    });
});