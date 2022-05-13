$(document).ready(function() {
	var path = getRootPath();
	syscycle();
	initCombobox();
	
	function addone(){
		$('input', $("#txt_startschoolyear").next('span')).blur(function() {
			var a = Number($("#txt_startschoolyear").val());
			$("#txt_endschoolyear").numberbox("setValue", a + 1);
		});
	}
	function obj(){
		$('input', $("#txt_endtTime").next('span')).blur(function() {
			objone()
			var startTime=$("#txt_startTime").datebox("getValue");
	    	var start=new Date(startTime.replace("-", "/").replace("-", "/"));			
	    	var endTime=$("#txt_endtTime").datebox("getValue");
	    	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
			var date=end.getTime()-start.getTime()
			var days=Math.floor(date/(24*3600*1000))
			var dayseven = parseInt(days/7);
			
			$("#txt_conWeeks1").numberbox("setValue",dayseven);
		});
	}
	function objone(){
		$('input', $("#txt_startTime").next('span')).blur(function() {
			obj()
			var startTime=$("#txt_startTime").datebox("getValue");
	    	var start=new Date(startTime.replace("-", "/").replace("-", "/"));			
	    	var endTime=$("#txt_endtTime").datebox("getValue");
	    	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
			var date=end.getTime()-start.getTime()
			var days=Math.floor(date/(24*3600*1000))
			var dayseven = parseInt(days/7);
			
			$("#txt_conWeeks1").numberbox("setValue",dayseven);
		});
	}
	$("#btn_addDlg").click(function() {
		$('#txt_status').combobox({readonly:true});
		$("#dlg_cycle").dialog("open");
		addone();
		/*$('input', $("#txt_startschoolyear").next('span')).blur(function() {
			var a = Number($("#txt_startschoolyear").val());
			$("#txt_endschoolyear").numberbox("setValue", a + 1);
		});*/
	});
	$("#btn_close").click(function() {
		$("#btn_save").show();
		clearForm();
		$("#dlg_cycle").dialog("close");
	});
	
	$("#btn_close_tip").click(function() {
		
		$("#dlg_cycle1").dialog("close");
		
		
		/*$("table[class$='ddv']").each(function(){
    		console.log($(this));
    		console.log($("#tbl_cycle"));
    		var a=$(this).datagrid("getSelections");
    		if(a){
    			$(this).datagrid("clearSelections");
    		}
    		var row = $(this).datagrid("getSelected");
        	var index=$(this).datagrid('getRowIndex',row);
        	alert(index);
        	console.log(row);
    		 
    		 
    	});*/
	});
	
	$("#btn_close_sub").click(function() {
		clearForm1();
		$("#dlg_cycle2").dialog("close");
		
	});
	$("#btn_query").click(function() {
		var queryParams = $('#form_query').serializeJSON();
		$('#tbl_cycle').datagrid('load', queryParams);
	});
	
	$("#btn_addDlg_sub").click(function(){
		
		var row = $('#tbl_cycle').datagrid("getSelected");
		if($('#tbl_cycle').datagrid('getRows').length==0){
			$.messager.alert("提示","请先增加一个实训周期！","info");
		}
		else if (row == null) {
			$.messager.alert("提示","请选中需要增加的实训周期","info");
			return false;
		} else {
			obj();
			$("#txt_sysTrainCycle").val(row.cycleId);
			 $("#dlg_cycle2").dialog("open");
			 
			
			
			 
			 
			 console.log(row);
			 
			/* $("#btn_save_sub").click(function() {
			 
			
			//增加
				
				//$.messager.alert("提示","增加新的申请原有正在进行的周期会结束，您确定要增加一个新的实训周期吗？","info");
				var url = path + "/systraincycle/saveSysDepartTrainCycle";
				var postData = $('#form_subadd').serializeJSON();
				$.post(url, postData, function(data) {
					if (data == "succ") {
						$.messager.alert("提示","修改成功","info");
						$("#dlg_cycle").dialog("close");
						$("#btn_query").click();
						$("#form_add").form("reset");
						clearForm();
					}else{
						$.messager.alert("提示","修改失败：" + data,"info");
						
					}
				});
				 $("#dlg_cycle2").dialog("close");
				 
			 });*/
			 
			
			 
		}
    	
		
		
		
		
	});
	$("#btn_updDlg_sub").click(function() {
		
		
		console.log($("table[class$='ddv']"));
		console.log($(".12ddv"));
		
		/*var row = $(".12ddv").datagrid("getSelected");
    	var index=$(".12ddv").datagrid('getRowIndex',row);
    	console.log(row);
    	console.log(index);*/
		
		
		var selectNum=0;
		var selectRowTr;
		console.log($("table[class*='ddv']"));
		$("table[class*='ddv']").each(function(){											
			/*console.log($(this).datagrid());*/
			/*console.log($(this).datagrid().length);*/
			/*console.log($(this).datagrid('getData').total);	*/
			/*console.log($(this).datagrid('getRows').length);*/

			$(this).datagrid();
			var row = $(this).datagrid("getSelected");	
				
	    	var index=$(this).datagrid('getRowIndex',row);
	    	$(this).datagrid("selectRow",index);
	    	if(index != (-1)){
				selectNum++;
				if(selectNum > 3){
					return false;
				}
				selectRowTr=$(this).datagrid("getSelected");
			}
	    	
	    	console.log(row);
	    	console.log(selectRowTr);
	    	
	    	console.log("index:"+index);		
		});
		console.log(selectRowTr);
		
		console.log("selectNum:"+selectNum);
		if($('#tbl_cycle').datagrid('getRows').length==0){
			$.messager.alert("提示","请先增加一个实训周期！","info");
		}		
		else if(selectNum != 1){
			$("#dlg_cycle2").dialog("open");
			$("#dlg_cycle2").dialog("close");
			//提示错误
			$.messager.alert("提示","请选择一个需要修改的专业","info");
		} else {
			$("#txt_sysDepartTrainCycleId").val(selectRowTr.sysDepartTrainCycleId);
			$("#input_departName").combobox("setValue", selectRowTr.departName);
			$("#input_majorName").combobox("setValue", selectRowTr.majorName);
			$("#txt_startTime").datebox("setValue", selectRowTr.planStartTime);
			$("#txt_endtTime").datebox("setValue", selectRowTr.planEndTime);
			$("#txt_conWeeks1").numberbox("setValue", selectRowTr.weeks);
			$("#txt_sysTrainCycle").val( selectRowTr.sysTrainCycle.cycleId);
			$("#txt_bigStartTime").val( selectRowTr.sysTrainCycle.planstartTime);
			$("#txt_bigEndTime").val( selectRowTr.sysTrainCycle.planendTime);
			
			$("#dlg_cycle2").dialog("open");
			$('#dlg_cycle2').dialog('setTitle', '修改系专业周期');
			
			/*//增加与修改新专业周期btn_save_sub
			$("#btn_save_sub").click(function() {
				var sysDepartTrainCycleId = $("#txt_sysDepartTrainCycleId").val();
				if (sysDepartTrainCycleId == "") {
					
					
					
				} else {
					//修改
					var url = path + "/systraincycle/updSysDepartTrainCycle";
					var postData = $('#form_subadd').serializeJSON();
					
					$.post(url, postData, function(data) {
						if (data == "succ") {
							$.messager.alert("提示","修改成功","info");
							$("#dlg_cycle").dialog("close");
							$("#btn_query").click();
							$("#form_add").form("reset");
							clearForm();
						}else{
							$.messager.alert("提示","修改失败：" + data,"info");
							
						}
					});
				}
				
				});*/
			
			
		}
	});
	$("#btn_save").click(function() {
		var startschoolyear = $("#txt_startschoolyear").textbox("getValue");
		if (startschoolyear.length != 4) {
			$.messager.alert("提示","请填写4位正确的有效年份！","info");
			$("#txt_startschoolyear").next("span").find("input[type='text']").focus();
			return false;
			}
		
		/*var semester = $("#txt_semester").combobox("getValue");
		if (semester == "") {
			$.messager.alert("提示","请填写学期！","info");
			$("#txt_semester").next("span").find("input[type='text']").focus();
			return false;
		}*/
		var planstartTime = $("#txt_planstartTime").datebox("getValue");
		if (planstartTime == "") {
			$.messager.alert("提示","请输入实习计划开始时间!","info");
			$("#txt_planstartTime").next("span").find("input[type='text']").focus();
			return false;
		}
		var planendTime = $("#txt_planendTime").datebox("getValue");
		if (planendTime == "") {
			$.messager.alert("提示","请输入实习计划结束时间!","info");
			$("#txt_planendTime").next("span").find("input[type='text']").focus();
			return false;
		}
		/*var conWeeks = $("#txt_conWeeks").textbox("getValue");
		if (conWeeks == "") {
			$.messager.alert("提示","请填写持续周期！","info");
			$("#txt_conWeeks").next("span").find("input[type='text']").focus();
			return false;
		}*/
		var status = $("#txt_status").textbox("getValue");
		if (status == "") {
			$.messager.alert("提示","请填写目前状态！","info");
			$("#txt_status").next("span").find("input[type='text']").focus();
			return false;
		}
		var startTime_sub=$("#txt_planstartTime").datebox("getValue");
	    var start_sub=new Date(startTime_sub.replace("-", "/").replace("-", "/"));
	    var endTime_sub=$("#txt_planendTime").datebox("getValue");
	    var end_sub=new Date(endTime_sub.replace("-", "/").replace("-", "/"));
		
		   
			if(end_sub<start_sub){
			 	$.messager.alert("提示","计划开始时间不能小于计划结束时间","info");	
			
		}else{
			var txt_cycleId = $("#txt_cycleId").val();
		if (txt_cycleId == "") {
			$("#dlg_cycle1").dialog("open");
			//$.messager.alert("提示","增加新的申请原有正在进行的周期会结束，您确定要增加一个新的实训周期吗？","info");
		} 
		else{
			var url = path + "/systraincycle/updSysTrainCycle";
			var postData = $('#form_add').serializeJSON();
			$.post(url, postData, function(data) {
				if (data == "succ") {
					$.messager.alert("提示","修改成功","info");
					$("#dlg_cycle").dialog("close");
					$("#btn_query").click();
					$("#form_add").form("reset");
					clearForm();
				}else{
					$.messager.alert("提示","修改失败：" + data,"info");
					
				}
			});
		}
		}
		
	});
	//增加新周期
	$("#btn_save_tip").click(function() {
		var txt_cycleId = $("#txt_cycleId").val();
		if (txt_cycleId == "") {
			$("#dlg_cycle1").dialog("open");
			var url = path + "/systraincycle/saveSysTrainCycle";
			var postData = $('#form_add').serializeJSON();
			$.post(url, postData, function(data) {
				if (data == "succ") {
					$.messager.alert("提示","增加成功","info");
					$("#dlg_cycle").dialog("close");
					$("#dlg_cycle1").dialog("close");
					$("#btn_query").click();
					clearForm();
				} else {
					$.messager.alert("提示","周期编号重复，请更改后重试");
					$("#txt_comName").next("span").find("input[type='text']").focus();
				}
			});
		}
	});
	


	//点击修改
	$("#btn_updDlg").click(function() {
		/*$('input', $("#txt_startschoolyear").next('span')).blur(function() {
			var a = Number($("#txt_startschoolyear").val());
			$("#txt_endschoolyear").numberbox("setValue", a + 1);
		});*/
		addone();
		$('#txt_status').combobox({readonly:false});
		$("#btn_delDlg").show();
		var row = $('#tbl_cycle').datagrid("getSelected");
		if($('#tbl_cycle').datagrid('getRows').length==0){
			$.messager.alert("提示","请先增加一个实训周期！","info");
		}
		else if (row == null) {
			$.messager.alert("提示","请选中要修改的行","info");
			return false;
		} else {
			$("#txt_cycleId").val(row.cycleId);
			$("#txt_startschoolyear").numberbox("setValue", row.startschoolyear);
			$("#txt_endschoolyear").numberbox("setValue", row.endschoolyear);
			$("#txt_semester").combobox("setValue", row.semester);
			$("#txt_planstartTime").datebox("setValue", row.planstartTime);
			$("#txt_planendTime").datebox("setValue", row.planendTime);
			$("#txt_conWeeks").numberbox("setValue", row.conWeeks);
			$("#txt_status").textbox("setValue", row.status);
			$("#dlg_cycle").dialog("open");
			$('#dlg_cycle').dialog('setTitle', '修改实训周期');
		}
	});
	
	
	
	//增加与修改新专业周期btn_save_sub
	$("#btn_save_sub").click(function() {
		
			var departName = $("#input_departName").combobox("getValue");
			if (departName=="") {
				$.messager.alert("提示","请选择院系！","info",function (){
				$("#input_departName").next("span").find("a").click();
			});
				return false;
				}
		    var majorName = $("#input_majorName").combobox("getValue");
			if (majorName=="") {
				$.messager.alert("提示","请选择专业！","info",function (){
				$("#input_majorName").next("span").find("a").click();
			});
				return false;
				}
			var StartTime = $("#txt_startTime").datebox("getValue");
			if (StartTime=="") {
				
				$.messager.alert("提示","请选择开始时间！","info",function (){
				$("#txt_startTime").next("span").find("a").click();
			});
				return false;
				}
			var EndtTime = $("#txt_endtTime").datebox("getValue");
			if (EndtTime=="") {
				$.messager.alert("提示","请选择结束时间！","info",function (){
				$("#txt_endtTime").next("span").find("a").click();
			});
				return false;
				}
			var weeks=$("#txt_conWeeks1").numberbox("getValue");
			if (weeks=="") {
				$.messager.alert("提示","请输入持续周数！","info",function (){
				$("#txt_conWeeks1").next("span").find("input[type='text']").focus();
			});
				return false;
				}
			var weeks=$("#txt_conWeeks1").numberbox("getValue");
			if (weeks==0) {
				$.messager.alert("提示","持续周数不能为0！","info",function (){
				$("#txt_conWeeks1").next("span").find("input[type='text']").focus();
			});
				return false;
				}
			var weeks=$("#txt_conWeeks1").numberbox("getValue");
	        var startTime=$("#txt_startTime").datebox("getValue");
	    	var start=new Date(startTime.replace("-", "/").replace("-", "/"));			
	    	var endTime=$("#txt_endtTime").datebox("getValue");
	    	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
			var date=end.getTime()-start.getTime()
			var days=Math.floor(date/(24*3600*1000))
			var dayseven = days/7;
			
			console.log(dayseven);
		    console.log(startTime);
			if(end<start){
			 	$.messager.alert("提示","开始时间不能小于结束时间","info");	
			
			}else if(weeks>days/7){
				$.messager.alert("提示","持续周数不能大于时间范围","info");	
			}else{
				                  
				var sysDepartTrainCycleId = $("#txt_sysDepartTrainCycleId").val();
				console.log(sysDepartTrainCycleId);
				if (sysDepartTrainCycleId == "") {
															
			//增加功能的时间校验		
			var row = $('#tbl_cycle').datagrid("getSelected");

            var bigStartTime  ;
  			var bigEndTime ;
		   
			 	bigStartTime = row.planstartTime;
				bigEndTime = row.planendTime;
			    console.log(row);
				console.log(bigStartTime);console.log(bigEndTime);
				var bigstartTime=new Date(bigStartTime.replace("-", "/").replace("-", "/"));
				var bigendTime=new Date(bigEndTime.replace("-", "/").replace("-", "/"));
				if(start<bigstartTime){
					$.messager.alert("提示","开始时间不能小于计划开始时间","info");
				}else if(bigendTime<end){
					$.messager.alert("提示","结束时间不能大于计划结束时间","info");
				}else{
					
			//增加
			
			//$.messager.alert("提示","增加新的申请原有正在进行的周期会结束，您确定要增加一个新的实训周期吗？","info");
			var url = path + "/systraincycle/saveSysDepartTrainCycle";
			var postData = $('#form_subadd').serializeJSON();
			$.post(url, postData, function(data) {
			
				
				if(data == "succ"){
				
						$.messager.alert("提示","添加成功","info");
						
						
						$("#btn_query").click();
						$("#form_add").form("reset");
						clearForm1();
				}else if(data == "hasMajor"){
						$.messager.alert("提示","添加失败:专业已存在" ,"info");
					
					}else{
						$.messager.alert("提示","添加失败" ,"info");
					}			
			                            });
		        }
		     

			
			
		} else {
			//修改功能的时间校验
		
			 var bigStartTime=$("#txt_bigStartTime").val();
		     var bigEndTime=$("#txt_bigEndTime").val();
             console.log(bigStartTime);console.log(bigEndTime);
			 var bigstartTime=new Date(bigStartTime.replace("-", "/").replace("-", "/"));
			 var bigendTime=new Date(bigEndTime.replace("-", "/").replace("-", "/"));
			if(end<start){
			 	$.messager.alert("提示","开始时间不能小于结束时间","info");	
			
			}else if(start<bigstartTime){
				$.messager.alert("提示","开始时间不能小于计划开始时间","info");
			}else if(bigendTime<end){
					$.messager.alert("提示","结束时间不能大于计划结束时间","info");
			}else{
				//修改
			var url = path + "/systraincycle/updSysDepartTrainCycle";
			var postData = $('#form_subadd').serializeJSON();
			$.post(url, postData, function(data) {
				if (data == "succ") {
					$.messager.alert("提示","修改成功","info");
					$("#dlg_cycle").dialog("close");
					$("#btn_query").click();
					$("#form_add").form("reset");
					clearForm1();
				}else{
					$.messager.alert("提示","修改失败：" + data,"info");
					
				}
			});
		}
		       
		}  $("#dlg_cycle2").dialog("close");
			}
			
		
		
		
	
	});
	
	
	
	function clearForm() {
		$("#txt_cycleId").val("");
		$("#txt_startschoolyear").numberbox("setValue", "");
		$("#txt_endschoolyear").numberbox("setValue", "");
		$("#txt_semester").combobox("setValue", "1");
		$("#txt_planstartTime").datebox("setValue", "");
		$("#txt_planendTime").datebox("setValue", "");
		$("#txt_status").textbox("setValue", "进行中");
		$("#txt_conWeeks").numberbox("setValue", "");
		$('#dlg_cycle').dialog('setTitle', '增加实训周期');
	}
	function clearForm1() {
		$("#txt_sysDepartTrainCycleId").val("");
		console.log($("#txt_sysDepartTrainCycleId").val());
		$("#input_departName").combobox("setValue", "");
		$("#input_majorName").combobox("setValue", "");
		$("#txt_startTime").datebox("setValue", "");
		$("#txt_endtTime").datebox("setValue", "");
		$("#txt_conWeeks1").numberbox("setValue", "");
		$("#txt_bigStartTime").val("");
		$("#txt_bigEndTime").val("");
		$('#dlg_cycle2').dialog('setTitle', '增加实训周期');
	}
	function checkEndTime(){
	    
	    
	    return true;
	}

	/*function syscycle1(){
		
		var row = $("#tbl_cycle").datagrid("getSelected");
		if ( row.status=='进行中' ){
				$("#btn_delDlg").hide();
			}
		else {
				$('#btn_delDlg').linkbutton("enable");
		}
	}*/
		
	$("#tbl_cycle").datagrid({  
                onClickRow: function (index, row) { 
				var status = row.status;
				if (status=="进行中"){
					
					$('#btn_delDlg').linkbutton("disable");
					$('#btn_updDlg').linkbutton("enable");
					$('#btn_addDlg_sub').linkbutton("enable");
					$('#btn_updDlg_sub').linkbutton("enable");
				}
				else if(status=="已结束"){
					$('#btn_updDlg').linkbutton("disable");
					$('#btn_delDlg').linkbutton("enable");
					$('#btn_addDlg_sub').linkbutton("disable");
					$('#btn_updDlg_sub').linkbutton("disable");
					
				}
				else{
					$('#btn_delDlg').linkbutton("enable");
				}
				
		
		}
	});
	/*$("#tbl_cycle").datagrid({  
                onClickRow: function (index, row) { 
				var status = row.status;
				if (status=="已结束"){
					$('#btn_updDlg').linkbutton("disable");
				}
				
				else{
					$('#btn_updDlg').linkbutton("enable");
				}
				
		
		}
	});*/
	
	
	$("#btn_delDlg").click(function() {
		var row = $("#tbl_cycle").datagrid("getSelected");
		if (null != row) {
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){
				if (r)
        	    	{
						var cycleId = row.cycleId;
						var postData = { "cycleId": cycleId };
						$.post((path + "/systraincycle/deleteSysTrainCycle"), postData, function(data) {
							if ("deletesuccess" == data) {
							$.messager.alert("提示","删除成功！","info");
							syscycle();
							}else {
								$.messager.alert("提示","当前实训周期被引用不能删除!","info"); 
							}
						});
			
					}
			});
		}
		else {
			$.messager.alert("提示","请选中一行！","info");
			return false;
		}
	});
	function initCombobox()
    {
    	//院系和专业下拉列表框数据的初始化以及联动
    	$("#input_departName").combobox({
    		valueField:'departName',
    		textField:'departName',
    		url:path+'/student/getdepart',
    		onSelect:function(record){
    			var param = record.departId;
    			$("#input_majorName").combobox({
    				value:null,
    				valueField:'majorName',
    				textField:'majorName',
    				url:path+'/student/getmajor',
    				queryParams:{"departId":param}
    			});
    		}
    	});
}

	var fatherinternalTimer;

    var soninternalTimer; 
	function syscycle() {
	    var url = path + "/systraincycle/getSysTrainCycleByCon";
	    $('#tbl_cycle').datagrid({ 
	        sortName:"status",
	        loadMsg: "加载数据中......",
	        url: path + "/systraincycle/getSysTrainCycleByCon",
	        border: false,
	        striped: true,
	        fit: true,
	        rownumbers: true,
	        autoRowHeight: false,
	        singleSelect:true,
	        fitColumns: true,
	        pagePosition: "bottom",
	        pagination: true,
	        sortOrder : 'desc',
	        pageSize: 10,
	        pageList: [10, 20, 50],
	        pageNumber: 1,
	        remoteSort:false,      
	        columns:[[
	                  { field: "startschoolyear", title: "开始学年度", width: 100,sortable :true},
	                      { field: 'endschoolyear', title: '结束学年度', width: 130 },
	                     /* { field: 'semester', title: '学期', width: 100,sortable: true, sorter: function (a, b) { return (a > b ? 1 : -1) }	  },*/
	                      { field: 'planstartTime', title: '实习计划开始时间', width: 100 },
	                     /* { field: 'conWeeks', title: '持续周数', width: 100 },*/
	                      { field: 'planendTime', title: '实习计划结束时间', width: 100 },
	                      
	                      { field: 'status', title: '状态', width: 100 ,sortable :true}
	                  ]],
	        view: detailview,
	        detailFormatter: function (index, yeyerow) {   //用以初始化并返回一个DIV容器
	            return '<div style="padding:2px"><table class="' + yeyerow.cycleId + 'ddv"></table></div>';
	        },
	        onExpandRow: function (index, yeyerow) {     //展开后触发事件
	            $('.' + yeyerow.cycleId + 'ddv').datagrid({
	            	url: path + "/systraincycle/getAllSysTrainCycle",
	                fitColumns: true,
	                rownumbers: true,
	                remoteSort:false,
	                sortOrder : 'desc',
	                loadMsg: '加载中...',
	                height: 'auto',
	                queryParams:{"cycleId":yeyerow.cycleId},
	                columns:[[
	                                   { field: "departName", title: "系名称", width: 100,sortable :true,sorter: function (a, b) { return (a > b ? 1 : -1) }},
	                                   { field: "majorName", title: "专业名称", width: 100},                                   
	                                   { field: "planStartTime", title: "开始时间", width: 100},
	                                   { field: "weeks", title: "持续周数", width: 100},
	                                   { field: "planEndTime", title: "结束时间", width: 100}
	                                   
		                                  
	                                   ]],
	                onResize: function () {   //事件会在窗口或框架被调整大小时发生
	                    $.each($('#tbl_cycle').datagrid('getRows'), function (i, row) {
	                        $('#tbl_cycle').datagrid('fixRowHeight', i);
	                    });
	                    $('#tbl_cycle').datagrid('fixDetailRowHeight', index);
	                },	                
	    	        onClickRow:function(index,row){   		    	        	  	        	
	    	        	var rowa = $('.' + yeyerow.cycleId + 'ddv').datagrid("getSelected");
	    	        	var indexa=$('.' + yeyerow.cycleId + 'ddv').datagrid('getRowIndex',rowa);
	    	        	/*console.log(rowa);
	    	        	console.log(indexa);
	    	        	console.log(index);*/	    	        	
	    	        	if(indexa != (-1)){
	    	        		$('.' + yeyerow.cycleId + 'ddv').datagrid("unselectAll");
	    	        		$('.' + yeyerow.cycleId + 'ddv').datagrid("selectRow",index);
	    	        	}else{
	    	        		$('.' + yeyerow.cycleId + 'ddv').datagrid("unselectAll");	    	        		
	    	        	}    		    	        	    	        	
	    	        },
	                onLoadSuccess: function () {   //当数据载入成功时触发
	                	
	                    clearTimeout(fatherinternalTimer);
	                    fatherinternalTimer =
	                    setInterval(function () {
	                        $.each($('#tbl_cycle').datagrid('getRows'), function (i, row) {
	                            $('#tbl_cycle').datagrid('fixRowHeight', i);
	                        });
	                        $('#tbl_cycle').datagrid('fixDetailRowHeight', index);
	                    }, 10);
	                }/*,
	                view: detailview,
	                detailFormatter: function (index_child, babarow) {    //用以初始化并返回一个DIV容器
	                    return '<div style="padding:0px"><table class="' + babarow.sysTrainCycle.cycleId + 'ddvv"></table></div>';
	                }*/
	                
	                /*,
	                onExpandRow: function (index_child, babarow) {   //展开后触发事件
	                    $('.' + babarow.sysTrainCycle.cycleId + 'ddvv').datagrid({
	                    	url: path + "/systraincycle/getAllSysTrainCycle",
	                        fitColumns: true,
	                        rownumbers: true,
	                        loadMsg: '加载中...',
	                        height: 'auto',
	                        queryParams:{"cycleId":babarow.sysTrainCycle.cycleId},
	                        columns:[[
	                                  { field: "majorName", title: "专业名称", width: 100,sortable :true},
	                                  { field: "weeks", title: "持续周数", width: 100,sortable :true}
	                                  ]],
	                        onResize: function () {    //事件会在窗口或框架被调整大小时发生
	                            $.each($('.' + babarow.sysTrainCycle.cycleId + 'ddv').datagrid('getRows'), function (i, row) {
	                                $('.' + babarow.sysTrainCycle.cycleId + 'ddv').datagrid('fixRowHeight', i);
	                            });
	                            $.each($('#tbl_cycle').datagrid('getRows'), function (i, row) {
	                                $('#tbl_cycle').datagrid('fixRowHeight', i);
	                                $('#tbl_cycle').datagrid('fixDetailRowHeight', i);
	                            });
	                            //父表格改变大小
	                            $('.' + babarow.sysTrainCycle.cycleId + 'ddv').datagrid('fixDetailRowHeight', index_child);
	                            //爷爷表格改变大小
	                            $('#tbl_cycle').datagrid('fixDetailRowHeight', index);
	                        },
	                        onLoadSuccess: function () {   //当数据载入成功时触发
	                            $.each($('.' + babarow.sysTrainCycle.cycleId + 'ddv').datagrid('getRows'), function (i, row) {
	                                $('.' + babarow.sysTrainCycle.cycleId + 'ddv').datagrid('fixRowHeight', i);
	                            });
	                            $.each($('#tbl_cycle').datagrid('getRows'), function (i, row) {
	                                $('#tbl_cycle').datagrid('fixRowHeight', i);
	                            });
	                            $('.' +  babarow.sysTrainCycle.cycleId + 'ddv').datagrid('fixDetailRowHeight', index_child);
	                            $('#tbl_cycle').datagrid('fixDetailRowHeight', index);
	                            //延迟执行一次后，点击缩进的话不能恢复原形,所以不用延迟函数，而是使用间隔函数
	                            clearTimeout(soninternalTimer);
	                            soninternalTimer =
	                            setInterval(function () {
	                                $.each($('.' +  babarow.sysTrainCycle.cycleId  + 'ddv').datagrid('getRows'), function (i, row) {
	                                    $('.' +  babarow.sysTrainCycle.cycleId  + 'ddv').datagrid('fixRowHeight', i);
	                                });
	                                $.each($('#tbl_cycle').datagrid('getRows'), function (i, row) {
	                                    $('#tbl_cycle').datagrid('fixRowHeight', i);
	                                    $('#tbl_cycle').datagrid('fixDetailRowHeight', i);
	                                });
	                                $('.' + yeyerow.system_id + 'ddv').datagrid('fixDetailRowHeight', index_child);
	                                $('#tbl_cycle').datagrid('fixDetailRowHeight', index);
	                            }, 5);
	                        }
	                    });
	                }*/
	            });
	        }
	    });
	  }








	

	
});



//btl 
/*<!--
						<td>学期:</td>
	          			<td style="width:160px"><select class="easyui-combobox" id="txt_semester" name="semester" style="width:150px;" data-options="required:true,missingMessage:'必填'">
	          			<option>1</option>
	          			<option>2</option>
	          			</select>
	          			</select></td>
	          			--!>


<!--
	                <tr style="height:40px;"> 
	                	<td>持续周数:</td>
	          			<td style="width:160px"><input class="easyui-numberbox" id="txt_conWeeks" name="conWeeks" style="width:150px;" data-options="required:true,missingMessage:'必填'"></td>
	                </tr>
	                --!>*/