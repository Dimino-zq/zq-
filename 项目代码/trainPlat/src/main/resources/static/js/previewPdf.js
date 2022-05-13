/**
 * 
 */
(function ($) {
    $.fn.previewPdf= function(options){
        var defaults = {
        		width:600,
        		height:510,
        		title:'',
        		data:null
        }
        var options = $.extend(defaults, options);
        var path=getRootPath();
		var dlgId="previewfile"+$(this).attr("id");
		var that=$(this);
		 //创建对话框
	    if ($("#"+dlgId).html()==undefined){
	    	var html='<div id="'+dlgId+'" class="easyui-dialog" title="'+options.title+'附件预览"  '+
		 	'style="width:'+options.width+'px;height:'+options.height+'px;overflow: hidden" '+
			        	'data-options="resizable:false,modal:true,closable:true,closed:true"> '+
			        	'<div  style="width: 100%; height:100%;margin: 0 auto;">'+
						'<form id="exportPdfForm" method="post" style="display:none;" target="pdfcontainer" action="">'+
				      	'<input type="hidden" id="txt_commonpdfPath" name="pdfPath" value=""/>'+
						'</form>'+
						'<iframe frameborder="0" scrolling="auto" name ="pdfcontainer" style="width: 100%; height:100%;" src=""></iframe>'+
			    		'</div>'+
			     '</div>';
		 	$(that).append(html);
		 	$.parser.parse("#"+$(that).attr("id")); // 解析某个具体节点
		}
		var materials =options.data;
		if (materials==null){
			$.messager.alert("提示","没有要预览的数据","info");
			$("#"+dlgId).dialog('close');
		}else{
			var url=path+"/syscompany/initPdfView";
			$("#txt_commonpdfPath").val(materials)
			$("#exportPdfForm").attr("action",url);
			$("#exportPdfForm").submit();
			$("#"+dlgId).dialog('open');
		}
		
    };
   
})(jQuery);