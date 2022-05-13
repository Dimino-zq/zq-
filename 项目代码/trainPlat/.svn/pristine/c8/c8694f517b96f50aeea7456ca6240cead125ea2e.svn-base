$(document).ready(function() {
	
	var pageNo=1;
	var pageSize=10;
	var total=0;
	var path = getRootPath();
	length();
	var levels = null;//存储等级数据
	level();
	nowwork();
	var oper="default";
	

	//点击企业logo弹出详细信息
	/*$("#a_logo").click(function(){
		$("#dlg_cominfo").dialog("open");
	});*/

	$("#btn_query").click(function(){
		var postData="&searchText="+$("#txt_searchText").textbox("getValue");
		pageNo=1;
		oper="click";
		$(".fl").empty();
		$(".fr").empty();
		var url=path + "/traininformation/getStudentTrainInformationByCon?pageNo="+pageNo;
		$.post(url,postData,function(data){
			displayInfo(data);
		});
		//获取总数
		var url1=path + "/traininformation/getStudentTrainInformationCountByCon";
		$.post(url1,postData,function(data){
			total=data;
		});
	});
	/**
	 * 打开申请表对话框
	 * @author tomset
	 */
	$("button[name='a_apply']").click(function(){
		//当实习周期不存在时，禁用实习时间框
		if($('#help').attr("deptCycle") == "false") {
//    		$("#input_applydate").datebox("disable");
//    		$("#input_endDate").datebox("disable");
    		$.messager.alert("错误", "无法获取实训周期信息，请重新登录后再尝试！", "error");
    		return;
		}
		
		//复选款控制保存按钮是否有效
		if ($('#check_agreement').checkbox('options').checked)
			$("#btn_save").linkbutton("enable");
		else
			$("#btn_save").linkbutton("disable");
		$("#input_company").combobox("setValue",$(this).attr("companyId"));
		$("#input_surcomjob").textbox("setValue",$(this).attr("stationName"));
		$("#input_detailId").val($(this).attr("detailId"));
		$("#dlg_stuApplication").dialog("open");
	});
	/**
	 * @author tomset
	 * 关闭申请表对话框
	 */
	$("#btn_cancel").click(function(){
		clearForm();
		$("#dlg_stuApplication").dialog("close");
	});
	
	/**
 * 查看功能，跳转页面，html形式显示工作记录
 * @author clnxx
 */
	$("a[name='a_logo']").click(function() {
		console.log("data:" + $(this).attr("comName"))
		$("#dlg_cominfo").dialog("setTitle", "企业信息");
		$("#dlg_cominfo").dialog("open");

		$("#info_comName").html($(this).attr("comName"));
		$("#info_industry").html($(this).attr("industry"));
		$("#info_comcontacts").html($(this).attr("comcontacts"));
		$("#info_phone").html($(this).attr("phone"));
		$("#info_comAddress").html($(this).attr("comAddress"));

	});


	/*var row=$("tbl_information").datagrid("getSelected");
	$("#pictureimg").attr("src","data:image/jpeg;base64,"+row.lvlPicPath);*/

	function nowwork() {
		oper="default";
		$(".fl").empty();
		$(".fr").empty();
		//获取总数
		var url1=path + "/traininformation/getStudentDefaultTrainInformationCount";
		$.post(url1,"",function(data){
			total=data;
		});
		
		
		$.ajax({
			url: path + "/traininformation/getStudentDefaultTrainInformation?pageNo="+pageNo,
			type: "post",
			async: false,
			cache: false,
			// data : formData,
			processData: false,
			contentType: false,
			beforeSend: function() {
				console.log("正在进行，请稍候");
			},
			success: function(data) {
				displayInfo(data);
			},
			dataType: "json"
		});
	}
	function  displayInfo(data){
		console.log(data);
		var dataObj = eval(data); //返回的result为json格式的数据
			intofu = "",
			w2 = "",
			w1 = "";
		var plans=dataObj.rows;
		$.each(plans, function(index, item) {
			var lvlgif = "";
			console.log(item);
			console.log(index);
			$.each(levels, function(index, level) {
				if (item.company.currentLvl == level.lvlName) {
					lvlgif = level.lvlPicPath;
				}
			});
			intofu = "";
			$.each(item.details, function(index, detail) {
				console.log(detail);
				intofu += '<table>' +
					'<tr style="height:30px;">' +
					'<td style="width:200px" id="post">岗位:' + detail.stationName + '</td>' +
					'<td style="width:200px"><button type="button" class="hi" name="a_apply" companyId=' + item.company.companyId + ' detailId=' + detail.detailId + ' stationName=' + detail.stationName + '>申请该岗位</button></td>' +
					'</tr>' +
					'<tr style="height:30px;">' +
					'<td style="width:400px" id="major">所需专业:' + detail.majorName + '</td>' +
					'</tr>' +
					'<tr style="height:30px;">' +
					'<td style="width:200px" id="planrecruit">计划招聘人数:' + detail.planStu + '</td>' +
					'<td style="width:200px" id="recruitok">已招聘人数:' + detail.actualStu + '</td>' +
					'</tr>' +
					'</table>' +
					'<div class="ge">' + '</div>'
					;
			});

			if (index % 2 == 0) {
				w1 += '<div class="ad">' +
					'<table class="blo" id="tbl_information">' +
					'<tr class="hang">' +
					'<td class="a">' +
					'<div class="photo" id="logophoto" style="height:160px;width: 200px;">' +
					"<a href='#' title='点击查看企业信息！' name='a_logo' comName='" + item.company.comName + "' industry='" + item.company.industry + "' comcontacts='" + item.company.comcontacts + "' phone='" + item.company.phone + "' comAddress='" + item.company.comAddress + "' >" +
					"<img src='data:image/gif;base64," + item.company.logoPath + "' ></a>" +
					'<table id="tbl_cominfo"></table>' +
					'<ul class="work">' + '<li style="list-style:none;margin-left: 30px;" id="name"><strong>名称：</strong>' + item.company.comName + '</li>' +
					'<li style="list-style:none;margin-left: 30px;" id="level"><strong>等级：</strong>' + "<img src='data:image/gif;base64," + lvlgif + "' style='height: 20px;width: 106px;margin-bottom: -5px;margin-left: -5px;'  title=" + item.company.currentLvl + ">" + '</li>' +

					'</ul>' +
					'</div>' +

					' </td>' +
					'<td class="b">' +
					'<div id="pp">' +
					'<div class="title">' + '招聘描述' +

					'</div>' +
					'<div class="detail" ><p>' + item.planDesc + '</p></div>' +

					'</div>' +
					'<div class="title">' +
					'招聘信息' + '</div>' +
					'<div class="infor">' +



					intofu
					+ '</div>' +
					'</td>' +
					'</tr>' +
					'</table>' +
					'</div>';
			} else {
				w2 += '<div class="ad">' +
					'<table class="blo">' +
					'<tr class="hang">' +
					'<td class="a">' +
					'<div class="photo" id="logophoto" style="height:160px;width: 200px;">' +
					"<a href='#' title='点击查看企业信息！' name='a_logo' comName='" + item.company.comName + "' industry='" + item.company.industry + "' comcontacts='" + item.company.comcontacts + "' phone='" + item.company.phone + "' comAddress='" + item.company.comAddress + "' >" +
					"<img src='data:image/gif;base64," + item.company.logoPath + "' ></a>" +
					'<table id="tbl_cominfo"></table>' +
					'<ul class="work">' + '<li style="list-style:none;margin-left: 30px;" id="name"><strong>名称：</strong>' + item.company.comName + '</li>' +
					'<li style="list-style:none;margin-left: 30px;" id="level"><strong>等级：</strong>' + "<img src='data:image/gif;base64," + lvlgif + "' style='height: 20px;width: 106px;margin-bottom: -5px;margin-left: -5px;' title=" + item.company.currentLvl + ">" + '</li>' +
					'</ul>' +
					'</div>' +

					' </td>' +
					'<td class="b">' +
					'<div id="pp">' +
					'<div class="title">' + '招聘描述' +

					'</div>' +
					'<div class="detail" ><p>' + item.planDesc + '</p></div>' +

					'</div>' +
					'<div class="title">' +
					'招聘信息' + '</div>' +
					'<div class="infor">' +
					intofu
					+ '</div>' +
					'</td>' +
					'</tr>' +
					'</table>' +
					'</div>';
			}
		});
		$(".fl").append(w1);
		$(".fr").append(w2);
	}

	function level() {
		$.ajax({
			url: path + "/syscompany/getAllSysLevel",
			type: "post",
			async: false,
			cache: false,
			success: function(data) {
				if (null != data)
					levels = data;
				else
					console.log("加载等级数据失败！");
			},
			error: function() {
				console.log("加载等级数据失败！");
			}
		});
	}

	function length() {
		$('#input_surcomjob').textbox('textbox').attr('maxlength', 30)
		$('#input_surcomcontent').textbox('textbox').attr('maxlength', 100)
		$('#input_phoneOrQQ').numberbox('textbox').attr('maxlength', 11)
		$('#input_adress').textbox('textbox').attr('maxlength', 80)
	}
	//重置对话框表单函数
	function clearForm(){
		$("#input_applyId").val("");
		$("#input_company").combobox("setValue","");
		$("#input_surcomjob").textbox("setValue","");
		$("#input_insurance").combobox("setValue","");
		$("#input_surcomcontent").textbox("setValue","");
		$("#input_adress").textbox("setValue","");
		$("#input_applydate").datebox("setValue","");
		$("#input_endDate").datebox("setValue","");
		$("#input_teacherName").combobox("setValue","");
		//$("#input_teacherNo").textbox("setValue","");
		$("#input_phoneOrQQ").textbox("setValue","");
		$("#input_status").combobox("setValue","");
	}
	
	$("#div_center").scroll(function(){
		alert(total);
		if (pageNo*pageSize<total){
			var divHeight = $(this).height();
		    var nScrollHeight = $(this)[0].scrollHeight;
		    var nScrollTop = $(this)[0].scrollTop;
		    if(nScrollTop + divHeight >= nScrollHeight) {
		      //到达底部了;
				var url="";
				if (oper=="default"){
					url=path + "/traininformation/getStudentDefaultTrainInformation?pageNo="+pageNo;
					var postData="";
				}else{
					url=path + "/traininformation/getStudentTrainInformationByCon?pageNo="+pageNo;
					var postData="&searchText="+$("#txt_searchText").textbox("getValue");
				}
				$.post(url,postData,function(data){
					displayInfo(data);
				});
		    }
		}
	});
	
});