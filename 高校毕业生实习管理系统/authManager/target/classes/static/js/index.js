/**
 * 登录
 */
$(document).ready(function(){
	var path=getRootPath();
	
	$(".easyui-layout .easyui-accordion ul li a").click(function(){
		var navStr="首页"+"&rarr;"+$(this).attr("data-parent")+"&rarr;"+$(this).text();
		//设置导航
//		$.messager.progress();
		$("#main_display").panel({title: navStr});
		var url=path+$(this).attr("data-link");
		$("#main_frame").attr("src",url);
		
//		$.post(url,"",function(data){
//			checkLogin(data);
//			$("#main_display").empty();
//			$("#main_display").html(data);
//			$.parser.parse('#main_display');
//			$.messager.progress('close');
//		},"html");
		
	});
	
	$("#accordion_div .panel-title").click(function(){
		if($(this).text()=="退出"){
			if(confirm("确定退出系统吗？")){
				var url=path+"/loginOut";
				$.post(url, function(data){
					window.location.href = getRootPath();
				});			
			}
		}
	});	

});
	
$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
$.fn.datebox.defaults.parser = function(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}


