$(document).ready(function(){
	var path=getRootPath();
	length();
	var departCycle = null;		//存储当前用户所处的实训周期
	var days = null;	//原实习实际持续时间,用户选择的结束时间-原实习开始时间
	getCycle();			//获取实训周期
	
	//加载数据表格
	initTable();
	
	//“已通过”状态的变更申请
	var lastApplication = null;
	
	/**
	 * 创建申请表功能
	 */
	//打开增加变更申请对话框
	$("#btn_addDlg").click(function(){
		var oldComName = null;		//原单位名称
		var oldStartDate = null;	//原申请实习开始时间
		var oldEndDate = null;		//原申请实习结束时间
		//初始化对话框
		console.log(lastApplication);
		//若不存在变更申请，就上实习申请表中查询，否则从最新已通过的变更申请中获取时间
		if(lastApplication==null){
			$.ajax({
				url: path + "/studentapp/getapplication",
	            type: "post",
	            async: false,
	            cache: true,
	            success: function (data){
					if (data != null) {
						if(data[0].company != null){
							oldComName = data[0].company.comName;
							oldStartDate = data[0].applydate;
							oldEndDate = data[0].endDate;
						}
					} else {
						$.messager.alert("错误", "无法获取原单位信息！", "error");
					}
	            }
			});
		}else{
			if(null!=lastApplication.newCompany) {
				oldComName = lastApplication.newCompany.comName;
				oldStartDate = lastApplication.newComStartDate;
				oldEndDate = lastApplication.newComEndDate;
			}
		}
		$("#input_oldComName").textbox("setValue",oldComName);
		$("#input_oldComStartDate").datebox("setValue",oldStartDate);
    	//原始实习单位实际结束时间的选择范围
    	$('#input_oldComEndDate').datebox('calendar').calendar({
    	    validator: function(date){
    	    	var d1 = new Date(oldStartDate);	//最小结束时间
    	    	d1.setDate(d1.getDate()-1);
    	        var d2 = new Date(oldEndDate);	//最大结束时间
    	        return d1<=date && date<=d2;
    	    }
    	});
    	$('#input_oldComEndDate').datebox('calendar').calendar('moveTo', new Date(oldEndDate));
		openDlg();
	})
	
	//关闭创建对话框
	$("#btn_close").click(function(){
		$("#dlg_stuSecApplication").dialog("close");
		clearForm();
	})
	
	//保存创建的申请表
	function createApplication(postData) {
		//检查表单数据是否符合要求
		if (checkForm(postData)) {
			var url = path + "/studentapp/savechgapplication";
			//post提交数据
			$.post(url, postData, function (data) {
				if (data != null) {
					if (data.error != null) {
						$.messager.alert("错误", data.error, "error");
					} else {
						$.messager.alert("提示", data.tip, "info", function () {
							$("#dlg_stuSecApplication").dialog("close");
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
	 * 修改申请表功能
	 */
	//打开修改对话框
	$("#btn_updDlg").click(function(){
		var row = $("#tbl_stuSecApplication").datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中一行数据!","info");
			return false;
		}
		else
		{
			$("#input_secApplyId").val(row.secApplyId);
			$("#input_oldComName").textbox("setValue",row.oldComName);
			$("#input_oldComStartDate").datebox("setValue",row.oldComStartDate);
	    	//原始实习单位实际结束时间的选择范围
	    	$('#input_oldComEndDate').datebox('calendar').calendar({
	    	    validator: function(date){
	    	    	var d1 = new Date(row.oldComStartDate);	//原实习申请的实习开始时间
	    	        var d2 = new Date(departCycle.planEndTime);	//原实习申请的实习结束时间
	    	        return d1<=date && date<=d2;
	    	    }
	    	});
			$("#input_oldComEndDate").datebox("setValue",row.oldComEndDate);
	    	//新实习开始时间的选择范围，原实习申请的实际结束时间之后，实习周期结束之前
	    	$('#input_newComStartDate').datebox('calendar').calendar({
	    	    validator: function(startDate){
	    	    	var d1 = new Date(row.oldComEndDate);
	    	    	d1.setDate(d1.getDate());
	    	    	//结束时间-(持续天数-已完成实习天数)，能选择的开始时间的最大值，防止出现实习时间不够持续时间的情况
	    	        var d2 = new Date(departCycle.planEndTime);
	    	        //开始时间
	    	        var start = new Date(row.oldComStartDate).getTime();
	    	        var end = d1.getTime();		//结束时间
	    	        days =Math.floor((end - start) / (24 * 3600 * 1000));
	    	        d2.setDate(d2.getDate()-(departCycle.weeks*7-days));
	    	        return d1<=startDate && startDate<=d2;
	    	    }
	    	});
			if (row.newCompany)
				$("#input_company").combobox("setValue",row.newCompany.companyId);
			$("#input_newComStartDate").datebox("setValue",row.newComStartDate);
			
        	$('#input_newComEndDate').datebox('calendar').calendar({
        	    validator: function(date){
        	    	//结束时间应该至少满足实习持续周数要求
        	        var d1 = new Date(row.newComStartDate);
        	        d1.setDate(d1.getDate()+(departCycle.weeks*7-days)-1);//开始时间+持续天数
        	        var d2 = new Date(departCycle.planEndTime);
        	        return d1<=date && date<=d2;
        	    }
        	});
			$("#input_newComEndDate").datebox("setValue",row.newComEndDate);
			$("#input_newContent").textbox("setValue",row.newContent);
			$("#input_newStation").textbox("setValue",row.newStation);
			$("#input_reason").textbox("setValue",row.reason);
			$("#input_newAdress").textbox("setValue",row.newAdress);
			openDlg();
		}
	});
	
	//实习单位下拉框
	$("#input_company").combobox({
		url:path+"/syscompany/getAllSysCompanyForStudent",
		editable:false,
		textField: "comName",
		valueField: "companyId",
	});

	//保存修改后的申请表
	function updateApplication(postData) {
		if (checkForm(postData)) {
			var url = path + "/studentapp/updatechgapplication";
			$.post(url, postData, function (data) {
				if (data != null) {
					if (data.error != null)
						$.messager.alert("错误", data.error, "error");
					else {
						$.messager.alert("提示", data.tip, "info", function () {
							$("#dlg_stuSecApplication").dialog("close");
							clearForm();
							initTable();
						});
					}
				} else
					$.messager.alert("错误", "未知错误！", "error");
			});
		}
	}
	
	/**
	 * 查看变更申请表功能，跳转页面，html形式显示变更申请表表格
	 */
	$("#btn_allInfo").click(function(){
		var row = $("#tbl_stuSecApplication").datagrid("getSelected");
		    if (row==null){
		    	$.messager.alert("提示","请选中一行数据!","info");
		    	return false;
		    } else {
				var secApplicationId=row.secApplyId;
				var url=path+"/studentapp/gotohtmlapplication2";
				//post跳转网页
				var temp_form = document.createElement("form");
				temp_form .action = url;
				temp_form .target = "_self";
				temp_form .method = "post";
				temp_form .style.display = "none"; 
				var opt = document.createElement("textarea");
				opt.name = "secApplicationId";
				opt.value = secApplicationId;
				temp_form .appendChild(opt);
				document.body.appendChild(temp_form);
				temp_form .submit();
		    }
	});
	
	/**
	 * 删除未提交（未通过）的申请表功能
	 */
	//点击删除按钮
	$("#btn_delDlg").click(function () {
		var row = $("#tbl_stuSecApplication").datagrid("getSelected");
		//检查是否选中数据
		if (row == null) {
			$.messager.alert("提示", "请选中一行数据!", "info");
		} else {
			$.messager.confirm("确认", "您确认想要删除记录吗？", function (r) {
				if (r) {
					var secApplyId = row.secApplyId;
					var postData = {"secApplicationId": secApplyId};
					var url = path + "/studentapp/deletechgapplication";
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
	 * 提交申请表，等待审批
	 */
	//点击提交按钮
	$("#btn_subDlg").click(function () {
		var row = $("#tbl_stuSecApplication").datagrid("getSelected");
		if (row == null) {
			$.messager.alert("提示", "请选中一行数据!", "info");
			return false;
		} else if (row.filePath == null || row.filePath == "") {
			$.messager.alert("提示", "您还未提交附件！!", "info");
			return false;
		} else {
			$.messager.confirm("确认", "您确认要提交申请吗？一旦提交，在审批结束之前您将无法进行任何操作！", function (r) {
				if (r) {
					var secApplyId = row.secApplyId;
					var postData = {"secApplicationId": secApplyId};
					var url = path + "/studentapp/submitchgapplication";
					$.post(url, postData, function (data) {
						if (null != data) {
							if (data.error != null) {
								$.messager.alert("错误", data.error, "error");
							} else {
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
	
	//重置对话框表单函数
	function clearForm(){
		$("#input_secApplyId").val("");
		$("#input_oldComName").textbox("setValue","");
		$("#input_oldComStartDate").datebox("setValue","");
		$("#input_oldComEndDate").datebox("setValue","");
		$("#input_newComStartDate").datebox("setValue","");
		$("#input_newComEndDate").datebox("setValue","");
		$("#input_newContent").textbox("setValue","");
		$("#input_newStation").textbox("setValue","");
		$("#input_reason").textbox("setValue","");
		$("#input_newAdress").textbox("setValue","");
	}
	
	//打开变更申请对话框函数
	function openDlg(){
		//当实习周期不存在时，禁用实习时间框
		if(null == departCycle) {
    		$("#input_newComStartDate").datebox("disable");
    		$("#input_newComEndDate").datebox("disable");
		}
		$("#dlg_stuSecApplication").dialog("open");
	}
	
	//初始化数据表格
    function initTable() {
        var url = path + "/studentapp/getchgapplication";
        $("#tbl_stuSecApplication").datagrid({
        	loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            fit:true,
            rownumbers:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
            columns:[[
	        {field: "comName",title: "新实习单位",width:150,
					formatter:function (value,row,index) {
						if (row.newCompany)
							return row.newCompany.comName;
					}},
                {field: 'newComStartDate',title: '新实习开始日期',width:150},
                {field: 'newComEndDate',title: '新实习结束日期',width: 150},
//            	{field: 'oldComName',title: '原实习单位',width:150},
//              {field: 'oldComStartDate',title: '原实习开始日期',width:150},
//              {field: 'oldComEndDate',title: '原实习结束日期',width:150},
                {field: 'newStation',title: '新实习岗位',width:150},
//                {field: 'newContent',title: '新实习内容',width:150},
//                {field: 'newAdress',title: '新实习住宿地址',width:150},
//                {field: 'reason',title: '变更理由',width:150},
				{field: "filePath",title: "附件",width:150,
					formatter:function (value,row,index) {
						if(null==row.filePath || row.filePath == "")
							return "<div style='color:red'>无附件</div>";
						else
							return  "<a href='#' file='"+row.filePath+"'>查看</a>"; 
					}},
				{field: 'statusVal',title: '状态',width:150},
				{field: 'status',title: '',hidden:true,
					formatter: function(value,row,index) {
						//同时间内，只允许存在一个“已通过”的申请，一个既不是“已通过”也不是“作废”状态的申请
						if (row.status == "DApprove") {
							lastApplication = row;
						} else if (row.status != "EApprove") {
		            		$("#btn_addDlg").linkbutton("disable");
						}
					}}
            ]],
            onBeforeLoad: function() {
        		$("#btn_addDlg").linkbutton("enable");
        		$("#btn_recordDlg").linkbutton("enable");
        		$("#btn_allInfo").linkbutton("enable");
            },
            onLoadSuccess: function(data) {
            	//初始化按钮
            	if(data.rows.length == 0)
            	{
            		$("#btn_delDlg").linkbutton("disable");
            		$("#btn_allInfo").linkbutton("disable");
            		$("#btn_photo").linkbutton("disable");
            		$("#btn_updDlg").linkbutton("disable");
            		$("#btn_subDlg").linkbutton("disable");
            		$("#btn_recordDlg").linkbutton("disable");
            	} else {
                	//默认选中最新的数据
            		$("#tbl_stuSecApplication").datagrid("selectRow",data.rows.length-1);
                	//查看附件点击事件
                	$("a[file]").click(function() {
						var url=path+"/getRemoteImgFile?filePath="+$(this).attr("file");
                		$("#perview").attr("src",url);
                		$("#dialog_photo").dialog({
                			top:20,
                			title:data.rows[0].application.student.studentName+"的附件",
                			minHeight:200,
                			maxHeight:document.body.clientHeight-30,
                			onClose: function() {
                				$("#perview").attr("src","");
                			}
                			//content:"<img alt='附件' src='"+path+filePath+"' style='width:650px;'>"
                		});
        				//$("#dialog_photo").dialog("resize",{height: document.body.clientHeight-30});
                		$("#dialog_photo").dialog("open");
                	});
            	}
            },
            onSelect: function(index,row) {
            	if (row.status == "DApprove" || row.status == "EApprove" || row.status == "CApprove") {
            		$("#btn_delDlg").linkbutton("disable");
            		$("#btn_photo").linkbutton("disable");
            		$("#btn_updDlg").linkbutton("disable");
            		$("#btn_subDlg").linkbutton("disable");
            	} else {
            		$("#btn_delDlg").linkbutton("enable");
            		$("#btn_photo").linkbutton("enable");
            		$("#btn_updDlg").linkbutton("enable");
            		if (row.status == "FApprove" || row.filePath == null)
                		$("#btn_subDlg").linkbutton("disable");
            		else
            			$("#btn_subDlg").linkbutton("enable");
            	}
            }
        });
    };

	//点击保存按钮
	$("#btn_save").click(function(){
		var postData=$("#form_stuSecApplication").serializeJSON();
		if (postData)
		{
			if(postData.secApplyId!=null && postData.secApplyId!="")
				updateApplication(postData);
			else
				createApplication(postData);
		}
		else
			alert("postData is null!");
	});
	
	//表单字段校验
	function checkForm(postData)
	{
		if (postData.oldComEndDate == "")
			$("#input_oldComEndDate").next("span").find("a").click();
		else if (postData.companyId == "")
			$("#input_company").next("span").find("a").click();
		else if (postData.newComStartDate == "")
			$("#input_newComStartDate").next("span").find("a").click();
		else if (postData.newComEndDate == "")
			$("#input_newComEndDate").next("span").find("a").click();
		else if (postData.newContent == "" || postData.teacherName == "")
			$("#input_newContent").next("span").find("input[type='text']").focus();
		else if (postData.newStation == "")
			$("#input_newStation").next("span").find("input[type='text']").focus();
		else if (postData.reason == "" || postData.teacherName == "")
			$("#input_reason").next("span").find("input[type='text']").focus();
		else if (postData.newAdress == "")
			$("#input_newAdress").next("span").find("input[type='text']").focus();
		else
			return true;
		return false;
	}
	
	/**
	 * 上传附件
	 */
	$("#btn_photo").click(function () {
		var row = $("#tbl_stuSecApplication").datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中一行数据!","info");
			return false;
		} else {
			$("#dialog_importphoto").dialog("open");
		}
	});

	$("#btn_mod").click(function () {
		var form = $("#form_importphoto")[0];
		var file = $(form).find('input[type=file]')[0].files[0];
		if(file == null) {
			$.messager.alert("错误", "请先选择一个文件！", "error");
		}
		var fileName = file.name;
		var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
		if (file_typename.toLowerCase() == '.jpg') {
			phoFileUpload(form);
		} else {
			$.messager.alert("错误", "请选择一个.jpg格式的文件！", "error");
		}

	})

	//上传
	function phoFileUpload(form) {
		var formData = new FormData(form);
		var secApplicationId = $("#tbl_stuSecApplication").datagrid("getSelected").secApplyId;
		formData.append("secApplicationId",secApplicationId);
		console.log(formData);
		$.ajax({
			url : path+ "/studentapp/uploadfile2",
			type : "post",
			async : false,
			cache:false,
			data : formData,
			processData : false,
			contentType : false,
			beforeSend : function() {
				$.messager.progress({
				 	text:"上传中..."
				});
			},
			success : function(e) {
				$.messager.progress("close");
				if (null != e.error) {
					$.messager.alert("错误", e.error, "error");
				} else {
					$.messager.alert("提示", e.tip, "info", function () {
						$("#dialog_importphoto").dialog("close");
						//initTable();
						location.reload();
					});
				}
			},
			error : function(e) {
				$.messager.progress("close");
				$.messager.alert("错误", "未知错误", "error");
			}
		});
	}
	
	//审批记录功能
	$("#btn_recordDlg").click(function () {
		var row = $("#tbl_stuSecApplication").datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中一行数据!","info");
			return false;
		} else {
			//初始化审批记录对话框
			$('#dialog_checkRecord').dialog({
				title: '审批记录',
				width: 560,
				height: 300,
				closable:true,
				closed: false,
				resizable:false,
				constrain:true,
				modal: true
			});
			//初始化审批记录表
			var secApplicationId = $("#tbl_stuSecApplication").datagrid("getSelected").secApplyId;//选中记录的Id
			$("#tbl_checkRecord").datagrid({
				loadMsg:"正在读取审批记录...",
				url:path+"/studentapp/getreviewlog2",
				border:false,
				nowrap: false,
				striped:true,
				//fit:true,
				autoRowHeight:false,
				singleSelect:true,
				fitColumns:true,
				queryParams: {secApplicationId: secApplicationId},
				columns:[[
					{field: "userName",title: "用户",width:90},
					{field: "action",title: "操作",width:80},
					{field: "chgStatus",title: "状态",width:150},
					{field: "memo",title: "备注",width:290,height:90,
//						//当备注内容过多时控制显示控制
//						 formatter: function (value, row, index) {
//						 	if (row)
//						 		if (row.memo.length>10) {
//						 			$("#tbl_checkRecord").datagrid("fixRowHeight",index);
//						 			return row.memo;
//						 		}
//						 		else
//						 			return row.memo;
//						 }
					},
				]],
				//高亮显示新增的审批记录
				// rowStyler: function(index,row){
				// 	if (row)
				// 		if (row.readStatus == "未读" && row.userType == "teacher"){
				// 			return 'color:red;';
				// 		}
				// },
				onLoadSuccess:function(data){
					//默认选择第一行
					if(data.rows.length != 0)
						$("#tbl_checkRecord").datagrid("selectRow",0);
				}
			});
		}
	});
	
	//选择原实习结束时间时设置新实习开始时间范围
	$("#input_oldComEndDate").datebox({
	    onSelect: function(date){
	    	date.setHours(8,0,0,0);
	    	if((new Date(date)).getTime()==(new Date(departCycle.planEndTime)).getTime()) {
	    		$("#input_newComStartDate").datebox("disable");
	    		$("#input_newComEndDate").datebox("disable");
	    		$.messager.alert("提示","你已完成实习任务，无需进行实习变更!","info");
	    	} else {
		    	//选择开始时间后先清除新实习开始结束时间（重选开始结束时间），防止时间控制导致重新打开修改框时无法设置数据
		    	$("#input_newComStartDate").datebox({
		    		disabled: false,
		    		value: "",
		    		//disabled: false
		    	});
		    	$("#input_newComEndDate").datebox({
		    		disabled: false,
		    		value: "",
		    		//disabled: false
		    	});
		    	//新实习开始时间的选择范围，原实习申请的实际结束时间之后，实习周期结束之前
		    	$('#input_newComStartDate').datebox('calendar').calendar({
		    	    validator: function(startDate){
		    	    	var d1 = new Date(date);
		    	    	return d1<=startDate;
/*新实习开始时间控制
 * 		    	    	//结束时间-(持续天数-已完成实习天数)，能选择的开始时间的最大值，防止出现实习时间不够持续时间的情况
		    	        var d2 = new Date(departCycle.planEndTime);
		    	        //开始时间
		    	        var start = new Date($("#input_oldComStartDate").datebox("getValue")).getTime();
		    	        var end = d1.getTime();		//结束时间
		    	        days =Math.floor((end - start) / (24 * 3600 * 1000))+1;
		    	        if(departCycle.weeks*7>days)
		    	        	d2.setDate(d2.getDate()-(departCycle.weeks*7-days)+1);
		    	        return d1<=startDate && startDate<=d2;*/
		    	        
		    	    }
		    	});
	        	//定位新实习时间到日历对应的页，默认原实习实际结束时间
		    	var ls = new Date(date);
		    	ls.setDate(date.getDate()+1);
	        	$('#input_newComStartDate').datebox('calendar').calendar('moveTo', ls);
	    	}
	    }
	});
	
	//选择新实习开始时间时设置结束时间结束范围
	$("#input_newComStartDate").datebox({
	    onSelect: function(startDate){
	    	//选择开始时间后先清除新实习结束时间（重选结束时间），防止时间控制导致重新打开修改框时无法设置数据
	    	$("#input_newComEndDate").datebox("setValue","");
        	//结束时间的选择范围，结束时间应该大于(开始时间+剩余实习时间)
        	$('#input_newComEndDate').datebox('calendar').calendar({
        	    validator: function(date){
        	    	//结束时间应该至少满足实习持续周数要求
        	        var d1 = new Date(startDate);
        	        return d1<=date;
/*新实习结束时间控制
 *         	        if(departCycle.weeks*7>days)
        	        	d1.setDate(d1.getDate()+(departCycle.weeks*7-days)-1);//开始时间+持续天数
        	        var d2 = new Date(departCycle.planEndTime);
        	        return d1<=date && date<=d2;*/
        	        
        	    }
        	});
        	//定位新实习结束时间到日历对应的页，默认周期结束时间
        	//$('#input_newComEndDate').datebox('calendar').calendar('moveTo', new Date(departCycle.planEndTime));
        	$('#input_newComEndDate').datebox('calendar').calendar('moveTo', new Date(startDate));
	    }
	});
	
	//获取实训周期
	function getCycle() {
		$.ajax({
			url:path+"/studentapp/getcycle",
            type : "post",
            async : false,
            cache:true,
            success: function (e){
            	if(null != e && "" != e) {
            		console.log(e);
                	departCycle = e;
                	$('#help').tooltip("update","实习日期应在"+departCycle.planStartTime+"和"+departCycle.planEndTime+"范围内，且总实习时间不少于"+departCycle.weeks+"周！");		//提示实习申请时间的填写范围
                	//结束时间控制在选择开始时间后执行
            	} else {
            		//$("#help").css("display","inline");		//实习周期不存在时提示
            		$('#help').tooltip("update","实训周期尚未开启！请重新登录后重试！");		//实习周期不存在时提示
            	}
            }
		});
	}
	
	//限制文本框字符长度
	function length(){
			  $('#input_newContent').textbox('textbox').attr('maxlength',50) 
			  $('#input_newStation').textbox('textbox').attr('maxlength',30)
			  $('#input_reason').textbox('textbox').attr('maxlength',100)
			  $('#input_newAdress').textbox('textbox').attr('maxlength',80)
		
		}
})