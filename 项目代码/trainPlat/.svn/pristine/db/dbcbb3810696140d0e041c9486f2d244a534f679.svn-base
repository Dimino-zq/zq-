$(document).ready(function(){
	var path=getRootPath();
	
	initForm();
	
    //点击提交查询信息
    $("#btn_query").click(function(){
        var queryParams=$('#form_query').serializeJSON();
        $('#tbl_teacherinform').datagrid('load',queryParams);
    });
	
	
	function initForm(){
		var url=path+"/teacher/getStuTrainInfo";
	    $("#tbl_teacherinform").datagrid({
			loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            //fit:true,
            rownumbers:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
	    	columns:[[
				{field:"studentNo",title:"学号",align:"center",width:150,fixed:true},
				{field:"studentName",title:"姓名",align:"center",width:80,fixed:true},
				{field:"major",title:"专业",align:"center",width:200,fixed:true},
				{field:"classNumber",title:"班级",align:"center",width:50,fixed:true},
				{field:"trainProgress",title:"实习进度",align:"left",width:100,
					 formatter: function (value, row, index) {
					 	if (null != row && null != row.weeks && null != row.trainProgress) {
					 		var progress = "<table><tr>";
					 		for(var i=0; i<row.trainProgress; i++) {
					 			//console.log(row.studentName);
					 			progress+="<td style='border-radius:20px;background:greenyellow;height:15px;width:30px;'></td>";
					 		}
					 		if(row.trainProgress <row.weeks) {
					 			progress+="<td style='border-radius:20px;background:red;height:15px;width:30px;'></td>";
						 		for(var i=0; i<(row.weeks-row.trainProgress-1); i++) {
						 			progress+="<td style='border-radius:20px;background:gainsboro;height:15px;width:30px;'></td>";
						 		}
					 		}
					 		progress+="</tr></table>";
					 		return progress;
					 	}
					 }
				},
			]],
			onLoadSuccess: function (data){
				if(null!=data) {
					if(null!=data.error)
						console.log(data.error);
				} else {
					alert("未知错误！");
				}
			}
	    });
	}
	
});