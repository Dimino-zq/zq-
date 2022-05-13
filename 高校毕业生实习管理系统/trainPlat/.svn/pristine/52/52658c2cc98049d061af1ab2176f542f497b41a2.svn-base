$(document).ready(function(){
	var path=getRootPath();
	
	
	$("#btn_checkcompany").click(function(){
		var  row=$('#tbl_tapp').datagrid("getSelected");
		if (row==null){
			$.messager.alert("提示","请选中要增加协议的企业","info");
			return false;
		}else{
			$("#txt_checkcompanyId").val(row.companyId);
		}
    	$("#checkcompany").dialog("open");
  	});
	
	
	
    teachercheckcompany();

  //点击提交查询信息
    $("#btn_query").click(function(){
        var queryParams=$('#form_query').serializeJSON();
        $('#tbl_tapp').datagrid('load',queryParams);
    });
	
    //审批企业
    $("#checkcom_yes").click(function(){
    	
	      var url=path+"/syscompany/checkSysCom";
	      var postData=$('#form_checkcompany').serializeJSON();
	      $.post(url,postData,function(data){
	        if (data=="succ"){
	          $.messager.alert("提示","保存成功","info")
	          $("#checkcompany").dialog("close");
	          $("#btn_query").click();
	          
	        }else{
	        $.messager.alert("提示","系统维护中，请稍后重试","info");
	        }
	      });
		  
  });	
		
	
	//显示表格内容功能
	function teachercheckcompany(){
		    var url=path+"/teacher/getTeacherCheckSysCompanyByCon";
		    $("#tbl_tapp").datagrid({
	    	loadMsg:"加载数据中......",
            url:url,
            border:false,
            striped:true,
            fit:true,
            rownumbers:true,
            autoRowHeight:false,
            singleSelect:true,
            fitColumns:true,
            pagePosition:"bottom",
            pagination:true,
            pageSize:10,
            pageList:[2,10,20,50],
            pageNumber:1,
			    columns:[[
		      {field:"comName",title:"实习单位名称",width:100},
		      {field:"addupScore",title:"积分",width:60},
		      {field:"currentLvl",title:"企业等级",width:85},
		      {field:'datasource',title:'数据来源',width:150},
		      {field:'industry',title:'所属行业',width:300},
		  	  {field:'comcontacts',title:'企业联系人',width:100},
			  {field:'phone',title:'联系方式',width:80},		  
			  {field:'signTime',title:'签约时间',width:80},
		      {field:'comAddress',title:'实习单位地址',width:150},
		      {field:'checkstate',title:'审核状态',width:80,formatter:function(value,row,index){
                  if (row.checkstate=="已审批")
                  {
                      return row.checkstate;
                  }
                  else
                  {
                      return "未审批";
                  }
               }}
			      ]]
		    })
		}
	
});