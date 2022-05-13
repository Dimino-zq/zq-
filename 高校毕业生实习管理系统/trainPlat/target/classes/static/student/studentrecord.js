$(document).ready(function(){
	//获取上下文
	var path=getRootPath();
	
	var recordStatus = "";		//实习工作记录情况（是否完成实习要求，工作记录数到了上限）
	
	var latestRecord = null;		//最新实习工作记录
	//length();

	//由于实习申请已做时间控制，故可以通过实习申请对实习工作记录做时间控制
	var application = null;		//存储当前用户已通过的实习申请，error，tip
	getApplication();			//获取实训周期
	
	
	//初始化数据表格
	initTable();
	//初始化表单
	clearForm();
	
	
	/**
	 * 新增实习记录
	 */
	//点击增加对话框打开
	$("#btn_addDlg").click(function(){
		//初始化对话框
		//初始化工作时间
		if(null != latestRecord) {
	    	var ls2 = new Date(latestRecord.endDate);
	    	ls2.setDate(ls2.getDate()+1);
	    	$("#input_startDate").textbox("setValue",ls2.getFullYear() +"-"+ (ls2.getMonth()+1) +"-"+ ls2.getDate());
	    	ls2.setDate(ls2.getDate()+6);
	    	$("#input_endDate").textbox("setValue",ls2.getFullYear() +"-"+ (ls2.getMonth()+1) +"-"+ ls2.getDate());
		} else if(null != application) {
        	$("#input_startDate").textbox("setValue",application.startDate);
			
	    	var ls2 = new Date(application.startDate);
	    	ls2.setDate(ls2.getDate()+6);
	    	$("#input_endDate").textbox("setValue",ls2.getFullYear() +"-"+ (ls2.getMonth()+1) +"-"+ ls2.getDate());
		}
		//初始化实习岗位
		if(recordStatus != "finish") {
			if (application != null) {
				$("#input_comPost").textbox("setValue",application.comPost);
			} else {
				$.messager.alert("错误", "无法获取当前实习岗位信息，请手动填写！", "error");
			}
			openDlg();
		} else {
			$.messager.alert("提示", "你已完成实习任务，无需再填写实习工作记录！", "info");
		}

	})


	
	//增加新实习记录函数
	function createRecord(postData) {
		//检查表单数据是否符合要求
		if (checkForm(postData)) {
			var url = path + "/studentrec/saverecord";
			//post提交数据
			$.post(url, postData, function (data) {
				if (data != null) {
					if (data.error != null) {
						$.messager.alert("错误", data.error, "error");
					} else {
						$.messager.alert("提示", data.tip, "info", function () {
							$("#dialog_work").dialog("close");
							clearForm();
							initTable();
						});
					}
				} else {
					$.messager.alert("错误", "未知错误！", "error");
				}
			});
		} else {
			return;
		}
	}
	
	/**
	 * 修改实习记录
	 */
	//打开修改对话框
	$("#btn_updDlg").click(function(){
		var row = $("#tbl_stuRecord").datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中一行数据!","info");
			return false;
		}
		else
		{
			$("#input_recordId").val(row.recordId);
			$("#input_comPost").textbox("setValue",row.comPost);
			if (row.workRecord) {
				$("#input_startDate").textbox("setValue",row.workRecord.startDate);
				$("#input_endDate").textbox("setValue",row.workRecord.endDate);
				$("#input_workContents").textbox("setValue",row.workRecord.workContents);
				$("#input_maingains").textbox("setValue",row.workRecord.maingains);
				$("#input_notes").textbox("setValue",row.workRecord.notes);
				openDlg();
			} else {
				$.messager.alert("错误", "系统异常，请刷新页面！", "info");
			}
		}
	});
	
	//保存修改后的实习记录函数
	function updateRecord(postData) {
		if (checkForm(postData)) {
			var url = path + "/studentrec/updaterecord";
			$.post(url, postData, function (data) {
				if (data != null) {
					if (data.error != null)
						$.messager.alert("错误", data.error, "error");
					else {
						$.messager.alert("提示", data.tip, "info", function () {
							$("#dialog_work").dialog("close");
							clearForm();
							initTable();
						});
					}
				} else
					$.messager.alert("错误", "未知错误！", "error");
			});
		}
	}
	
	
	//点击删除-删除表单
	$("#btn_delDlg").click(function () {
		var row = $("#tbl_stuRecord").datagrid("getSelected");
		//检查是否选中数据
		if (row == null) {
			$.messager.alert("提示", "请选中一行数据!", "info");
		} else {
			$.messager.confirm("确认", "您确认想要删除记录吗？", function (r) {
				if (r) {
					var recordId = row.recordId;
					var postData = {"recordId": recordId};
					var url = path + "/studentrec/deleteRecord";
					$.post(url, postData, function (data) {
						if (null != data) {
							if (data.error != null) {
								$.messager.alert("错误", data.error, "error");
							}
							else {
								$.messager.alert("提示", data.tip, "info", function () {
									initTable();
								});
							}
						} else {
							$.messager.alert("错误", "未知错误！", "error");
						}
					});
				}
			});
		}
	});
	
	
	/**
	 * 查看工作记录功能，跳转页面，html形式显示工作记录
	 */
	$("#btn_allInfo").click(function(){
		var row = $("#tbl_stuRecord").datagrid("getSelected");
		    if (row==null){
		    	$.messager.alert("提示","请选中一行数据!","info");
		    	return false;
		    } else {
				var workLogId=row.workRecord.workLogId;
				var url=path+"/teacher/workrec";
				//post跳转网页
				var temp_form = document.createElement("form");
				temp_form .action = url;
				temp_form .target = "_self";
				temp_form .method = "post";
				temp_form .style.display = "none"; 
				var opt = document.createElement("textarea");
				opt.name = "teastudentworkrecId";
				opt.value = workLogId;
				temp_form .appendChild(opt);
				document.body.appendChild(temp_form);
				temp_form .submit();
		    }
	});
	
	//加载数据表格函数
	function initTable(){
		var url=path+"/studentrec/getrecord";
		$("#tbl_stuRecord").datagrid({
			loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            fit:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
			rownumbers: true,//序号递增
            columns:[[ 
            	{field: "comPost",title: "实习岗位",width:150},
            	{field: "content",title: "工作主要内容",width:150},
            	{field: "startDate",title: "开始日期",width:150},
				{field: "endDate",title: "结束日期",width:150},
				{field: "compleStatus",title: "完成情况",width:150},
				{field: "guidStatus",title: "指导情况",width:150},
			]],
			onLoadSuccess:function(data){
				if(null == application.trainCycle) {
					$.messager.alert("错误", "实训周期异常！", "error");
					return;
				}
				//初始化实习状态
				recordStatus = "";
            	//初始化按钮
				if(application != null) {
	            	if(data.rows.length == 0) {
	            		$("#btn_addDlg").linkbutton("enable");
	            		$("#btn_delDlg").linkbutton("disable");
	            		$("#btn_allInfo").linkbutton("disable");
	            		$("#btn_totalInfo").linkbutton("disable");
	            		$("#btn_updDlg").linkbutton("disable");
	                	//开始时间的选择范围，工作时间应在实习申请中填写的时间范围内
	                	$('#input_startDate').datebox('calendar').calendar({
	                	    validator: function(date){
	                	    	var d1 = new Date(application.trainCycle.planStartTime);
	                	    	d1.setDate(d1.getDate()-1);
	                	        return d1<=date;
	                	    }
	                	});
	                	$("#input_startDate").textbox("setValue",application.startDate);
	        	    	var ls2 = new Date(application.startDate);
	        	    	ls2.setDate(ls2.getDate()+6);
	        	    	$("#input_endDate").textbox("setValue",ls2.getFullYear() +"-"+ (ls2.getMonth()+1) +"-"+ ls2.getDate());
	            	} else {
	            		$("#btn_addDlg").linkbutton("enable");
	            		$("#btn_delDlg").linkbutton("enable");
	            		$("#btn_allInfo").linkbutton("enable");
	            		$("#btn_totalInfo").linkbutton("enable");
	            		$("#btn_updDlg").linkbutton("enable");
	            		if(data.rows.length>= application.trainCycle.weeks)
	            			recordStatus = "finish";
	            		//控制新增加的实训工作记录时间，工作时间应在实习申请中填写的时间范围内且时间不少于7天
	            		latestRecord = data.rows[data.rows.length-1];
	                	$('#input_startDate').datebox('calendar').calendar({
	                	    validator: function(endDate){
	                	    	var d1 = new Date(application.trainCycle.planStartTime);
	                	    	d1.setDate(d1.getDate()-1);
	                	        return d1<=endDate;
	                	    }
	                	});
	                	//console.log(data.rows[data.rows.length-1]);
	            	}
//	            	var tmp = null;	//临时存放结束时间
//	            	$('#input_endDate').datebox('calendar').calendar({
//	            	    validator: function(date){
//	            	    	//结束时间应该至少满足实习持续周数要求
//	            	        var d1 = new Date($("#input_startDate").datebox("getValue"));
//	            	        d1.setDate(d1.getDate()+5);//开始时间+持续天数
//	            	        tmp = d1;
//	            	        var d2 = new Date(application.endDate);
//	            	        return d1<=date && date<=d2;
//	            	    }
//	            	});
//	            	//console.log(tmp.getFullYear() +"-"+ (tmp.getMonth()+1) +"-"+ tmp.getDate());
//	            	if(tmp<=new Date(application.endDate)) {
//	            		$("#input_endDate").textbox("setValue",tmp.getFullYear() +"-"+ (tmp.getMonth()+1) +"-"+ (tmp.getDate()+1));
//	            	}
	        		if(null != latestRecord) {
	        	    	var ls2 = new Date(latestRecord.endDate);
	        	    	ls2.setDate(ls2.getDate()+1);
	        	    	$("#input_startDate").textbox("setValue",ls2.getFullYear() +"-"+ (ls2.getMonth()+1) +"-"+ ls2.getDate());
	        	    	ls2.setDate(ls2.getDate()+6);
	        	    	$("#input_endDate").textbox("setValue",ls2.getFullYear() +"-"+ (ls2.getMonth()+1) +"-"+ ls2.getDate());
	        		}
				} else {
            		$("#btn_addDlg").linkbutton("disable");
            		$("#btn_delDlg").linkbutton("disable");
            		$("#btn_allInfo").linkbutton("disable");
            		$("#btn_totalInfo").linkbutton("disable");
            		$("#btn_updDlg").linkbutton("disable");
				}
			}
       })
	}
	
	//初始化表单函数
	function clearForm(){
		$("#input_recordId").val("");
		$("#input_startDate").textbox("setValue","");
		$("#input_endDate").textbox("setValue","");
		$("#input_workContents").textbox("setValue","");
		$("#input_maingains").textbox("setValue","");
		$("#input_comPost").textbox("setValue","");
		$("#input_notes").textbox("setValue","");
	}
	
	//打开对话框函数
	function openDlg(){
		$("#dialog_work").dialog("open");
	}
	
	//点击保存按钮
	$("#btn_save").click(function(){
		//获取表单中的数据
		var postData=$("#form_workinfo").serializeJSON();
		//判断表单数据是否正确获取到
		if (postData)
		{
			//判断实习记录的Id是否存在，存在则调用更新函数，否则调用增加函数
			if(postData.recordId!=null && postData.recordId!="")
				updateRecord(postData);
			else
				createRecord(postData);
		}
		else
			alert("postData is null!");
	});
	
	//点击关闭对话框关闭
	$("#btn_close").click(function(){
		$("#dialog_work").dialog("close");
		clearForm();
	})
	
	//表单字段校验函数
	function checkForm(postData)
	{
		if (postData.comPost == "")
			$("#input_comPost").next("span").find("input[type='text']").focus();
		else if(postData.startDate == "")
			$("#input_startDate").next("span").find("a").click();
		else if(postData.endDate == "")
			$("#input_endDate").next("span").find("a").click();
//		else if((Math.floor((new Date(postData.endDate).getTime()-new Date(postData.startDate).getTime()) / (24 * 3600 * 1000)+1))<7)
//			$.messager.alert("提示", "周工作时间至少要有7天！", "tip");
		else if (postData.workContents == "")
			$("#input_workContents").next("span").find("input[type='text']").focus();
		else if (postData.maingains == "")
			$("#input_maingains").next("span").find("textarea").focus();
		else
			return true;
		return false;
	}
	
	//初始化结束时间框
	$("#input_startDate").datebox({
	    onSelect: function(startDate){
	        var ls = new Date(startDate);
	        ls.setDate(ls.getDate()+6);//开始时间+持续天数
        	$("#input_endDate").textbox("setValue",ls.getFullYear() +"-"+ (ls.getMonth()+1) +"-"+ ls.getDate());
/*对实习工作记录结束时间不进行严格控制，但结束时间不可选
 * 	    	var ls = null;
	    	//选择开始时间后先清除结束时间（重选结束时间），防止时间控制导致重新打开修改框时无法设置数据
	    	$("#input_endDate").textbox("setValue","");
        	//结束时间的选择范围，结束时间应该大于开始时间
        	$('#input_endDate').datebox('calendar').calendar({
        	    validator: function(date){
        	    	//结束时间应该至少满足实习持续周数要求
        	        var d1 = new Date(startDate);
        	        d1.setDate(d1.getDate()+6);//开始时间+持续天数
        	        ls = d1;
        	        var d2 = new Date(application.endDate);
        	        return d1<=date && date<=d2;
        	    }
        	});
        	$("#input_endDate").textbox("setValue",ls.getFullYear() +"-"+ (ls.getMonth()+1) +"-"+ ls.getDate());*/
	    }
	});
	
	//获取实训周期
	function getApplication() {
		$.ajax({
			url:path+"/studentrec/getlatestapplication",
            type : "post",
            async : false,
            cache:true,
            success: function (e){
            	if(null != e && "" != e) {
            		console.log(e);
            		if(null != e.tip) {
            			if(null!=e.tip.application) {	//变更申请
                			application = {
	                			startDate: e.tip.newComStartDate,
	                			endDate: e.tip.newComEndDate,
	                			comPost: e.tip.newStation,
	                			trainCycle: e.tip.application.trainCycle,
                			};
            			} else {	//实习申请
                			application = {
                    			startDate: e.tip.applydate,
                    			endDate: e.tip.endDate,
                    			comPost: e.tip.surcomjob,
                    			trainCycle: e.tip.trainCycle,
                    		};
            			}
            			console.log(application);
            		} else if (null != e.error){
            			$.messager.alert("错误", e.error, "error");	//获取当前实习申请失败
            		} else {
            			$.messager.alert("错误", "未知错误！"+e, "error");
            		}
            	} else {
            		$.messager.alert("错误", "无法获取当前的实习申请！", "error");		//实习申请不存在时提示
            	}
            }
		});
	}
	
	//限制文本框字符长度	
	$(function () {
        $('#input_comPost').textbox('textbox').attr('maxlength',30) 
		$('#input_workContents').textbox('textbox').attr('maxlength',100)
		$('#input_maingains').textbox('textbox').attr('maxlength',400)
		$('#input_notes').textbox('textbox').attr('maxlength',100)
    });
})