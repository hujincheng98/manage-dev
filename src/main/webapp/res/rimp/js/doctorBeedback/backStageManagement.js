$(function() {

	var editIndex = -1;
	var dataTable = $("#dg");
	
	var rowPfid = "";
	var rowIsReply = "";
	
	var printView = function(){
		//跳转至打印页面
		var printPfid = $('#pfid').val();
		/*window.location.href = "printView.do?pfid="+printPfid;*/
		window.open("isreply.do?pfid="+printPfid+"&printView=1");
		/*debugger
		closePrintView();
		var headstr = "<html><head><title></title><style type='text/css'>*{font-size: 16px;}body{text-align:center}</style></head><body>";  
		var footstr = "</body>";
		var printData = document.getElementById('div_print').innerHTML;		
		var oldstr = document.body.innerHTML;
		var oldHeadStr = "<html><head><title></title><script type='text/javascript' src='${res}/js/doctorBeedback/backStageManagement.js'></script></head><body>";
		//var oldstr = document.getElementById('backStageM').innerHTML;
		document.body.innerHTML = headstr+printData +footstr;
		window.print();
		//window.location.reload();
		closePrintView();
		document.body.innerHTML = oldHeadStr+oldstr+footstr;*/
	}
	
	/*function closePrintView(){
		$('#dd').dialog('close');
		$(dataTable).datagrid("clearSelections");
	}*/
	
	//查看回复或已回复
    $(document).on('click','.row-btn',function(){
    	var dataPfid = $(this).attr('data-pfid');
   	    var dataRep = $(this).attr('data-rep');
      	if($.trim(dataRep) == '1'){
    		$('#dd').dialog({
        	    title: '后台管理',   
        	    width: 720,   
        	    height: 500,   
        	    closed: false,   
        	    cache: false,
        	    href: 'isreply.do?pfid='+dataPfid+'&state='+dataRep,   
        	    modal: true,
        	    toolbar:[{
    				text:'打印预览',
    				iconCls:'icon-print',
    				handler:printView
    			}]
    	   });   		
    }
      	if($.trim(dataRep) == '0'){
    		$('#dd').dialog({
        	    title: '后台管理',   
        	    width: 720,   
        	    height: 500,   
        	    closed: false,   
        	    cache: false,
        	    href: 'isreply.do?pfid='+dataPfid+'&state='+dataRep,   
        	    modal: true,
        	    toolbar:[{
    				text:'打印预览',
    				iconCls:'icon-print',
    				handler:printView
    			},
    			{
    				text:'提交',
    				iconCls:'icon-save',
    				handler:save
    			}]
    	   });
      }
      	
      setTimeout(function () { 
    	  //获得题库信息
      	$.ajax({
      		url : '/rimp/backStageManagement/PatFeedbackQuestion.json',
      		type : 'GET',
      		data : {"pfid": dataPfid},
      		async : true,
      		dataType : 'JSON',
      		success : function(data) {
      		   //渲染数据
      			produceQuesion(data);
      		},
      		error : function(data){			
      		}
      	});
     }, 100); 
      	
      /*//获得题库信息
    	$.ajax({
    		url : '/rimp/backStageManagement/PatFeedbackQuestion.json',
    		type : 'GET',
    		data : {"pfid": dataPfid},
    		async : true,
    		dataType : 'JSON',
    		success : function(data) {
    		   //渲染数据
    			produceQuesion(data);
    		},
    		error : function(data){			
    		}
    	});*/
    	
  });		
    		
  //提交回复内容
  var save = function(){
	    var subPfid = $('#pfid').val();
	    var subData = {};
	    subData.pfid = subPfid;
	    //回复内容不能为空
		if($('#reply').val().trim()==""){
			$.messager.alert("提示", "回复内容不能为空", "info");
			return true;
		}	  
		//回复
		subData.reply = $('#reply').val().trim();
		
		$.ajax({
			url : '/rimp/backStageManagement/save.json',
			type : 'POST',
			data :subData,
			async : false,
			dataType : 'JSON',
			success : function(data) {
				//data = JSON.parse(data);
				var state = data.state
				if (data.state == 'success') {
					$.messager.alert("提示", data.message, "info", function() {
					$('#dd').dialog('close');
					$('#dg').datagrid("reload");
					$(dataTable).datagrid("clearSelections");
					});
				}
				if (data.state == 'fail') {
					$.messager.alert("提示", data.message, "info", function() {
						
					});
				}
			}
		});			  
  }
    
  //渲染题库的方法
	function produceQuesion(data){
		
		var templet = $("#templet");
		 if(data.length>0){
			//渲染数据
			var begin = $("<h3 align='left'>满意度调查：</h3>");
			templet.append(begin);
			for(var i=0; i<data.length; i++){
				//问题
				var question = data[i].question;
				var quePage = $("<h4 align='left'>"+(i+1)+question+"</h4>");
				templet.append(quePage);
				//答案: 满意：1,基本满意：2, 一般：3, 不满意：4
				var answer = data[i].value;
				//<div class=easyui-panel style='widows: 100px;height: 30px;border: 1px solid #95B8E7;'>
				var choosePage = $("<div>"+ 
				   "<button disabled='disabled' id="+(i+1)+"button0 class="+(i+1)+"button value='1' style='width: 80px;height: 25px;margin-right: 20px;background-color:transparent;'>满意</button>"+
				   "<button id="+(i+1)+"button1 class="+(i+1)+"button value='2' disabled='disabled' style='width: 80px;height: 25px;margin-right: 20px;background-color:transparent;'>基本满意</button>"+
				   "<button id="+(i+1)+"button2 class="+(i+1)+"button value='3' disabled='disabled' style='width: 80px;height: 25px;margin-right: 20px;background-color:transparent;'>一般</button>"+
				   "<button id="+(i+1)+"button3 class="+(i+1)+"button value='4' disabled='disabled' style='width: 80px;height: 25px;margin-right: 20px;background-color:transparent;'>不满意</button>"				   
				+"</div>");
				templet.append(choosePage);
				//console.info(templet);
				//为答案填充颜色value
				var str = "."+(i+1)+"button";
				var rowClass = $(str);
				for(var j=0; j<rowClass.length;j++){
					//console.log(rowClass[j]);
					if(rowClass[j].value==answer){
						//为答案填充颜色
						var id = "#"+rowClass[j].id;
						$(id).css('background-color', '#CCE8CF');
						$(id).attr("style","color:red;width: 80px;height: 25px;margin-right: 20px;background-color:transparent;");
					}
				}
			}
		 }else{
			 //没有数据
			 var notData = $("<h4>暂无数据</h4>");
			 templet.append(notData);
		 }
	}
    
    
	//*************
	// 查询
	$("#searchBtn").click(function() {
		var json = $("#searchform").toJson();
		console.log(json);
		if (json != "") {
			$(dataTable).datagrid("load", json);
		}
	});
	/*$(document).on("click","#searchBtn",function() {
		var json = $("#searchform").toJson();
		console.log(json);
		if (json != "") {
			$(dataTable).datagrid("load", json);
		}
	});*/

	// 重置
	$("#clearBtn").click(function() {
		$('#department').textbox('setValue', '');
		$('#createdateStartDate').combo("setValue","");
    	$('#createdateStartDate').combo("setText","");
    	$('#createdateEndDate').combo("setValue","");
    	$('#createdateEndDate').combo("setText","");
    	$("#chid").combobox('clear');
    	$("#fbtype").combobox('clear');
		$("#isreply").combobox('clear');// 清空选择表格数据
		$("#searchBtn").click();
	});
	
	//excel导出
		$("#export").on('click',function(){
			var replydateStartDate = $("#createdateStartDate").datebox('getValue');
			var replydateEndDate = $("#createdateEndDate").datebox('getValue');
			var department = $("#department").val();
			var isreply = $('#isreply').combobox('getValue');
			var chid = $('#chid').combobox('getValue');
			var fbtype = $('#fbtype').combobox('getValue');			
			window.location.href = "makePatFeedback.json?replydateStartDate="+replydateStartDate+"&replydateEndDate="+replydateEndDate+"&department="+department+"&isreply="+isreply+"&chid="+chid+"&fbtype="+fbtype;
			event.stopPropagation()
			return false;
		});
	/*$("#export").click(function(){
		var replydateStartDate = $("#createdateStartDate").datebox('getValue');
		var replydateEndDate = $("#createdateEndDate").datebox('getValue');
		var department = $("#department").val();
		var isreply = $('#isreply').combobox('getValue');
		var chid = $('#chid').combobox('getValue');
		window.location.href = "exportExcel.json?replydateStartDate="+replydateStartDate+"&replydateEndDate="+replydateEndDate+"&department="+department+"&isreply="+isreply+"&chid="+chid;
	});*/

	$(dataTable).datagrid({
		url : 'list.json',
		title : "后台管理",
		loadMsg : '数据加载中,请稍后......',
		border : true,
		fitColumns : true,
		remoteSort : false,
		toolbar : "#button-bar",
		singleSelect : false,
		rownumbers : true,
		showFooter : true,
		pagination : true,
		fit : false,// 自适应大小
		nowrap : true,// 把数据显示在一行里
		pageSize : 10, // 页大小
		pageList : [10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
		striped : true, // 行背景交换
		idField : 'pfid', // 主键
		columns : [ [ {
			field : 'question',
			title : '序号',
			width : 5,
			sortable : true,
			checkbox:true
		},
		{
			field : 'fbtype',
			title : '反馈类型',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){
				//adtype_01:咨询,adtype_02:建议,adtype_03:投诉,adtype_04:其他
                if (row.fbtype=='adtype_01'){
                    return '咨询';
                }
                else if (row.fbtype=='adtype_02'){
                	 return '建议';
                }
                else if (row.fbtype=='adtype_03'){
                	return '投诉';
                }
                else if (row.fbtype=='adtype_04'){
                	return '其他';
                }
            }
		},
		{
			field : 'title',
			title : '标题',
			width : 5,
			sortable : true
		},
		{
			field : 'createdate',
			title : '反馈时间',
			width : 5,
			sortable : true
		},		
		{
			field : 'patientname',
			title : '患者姓名',
			width : 5,
			sortable : true
		}, {
			field : 'telephone',
			title : '联系电话',
			width : 5,
			sortable : true
		}, {
			field : 'department',
			title : '就诊科室',
			width : 5,
			sortable : true
		},
		{
			field : 'chName',
			title : '反馈渠道',
			width : 5,
			sortable : true
		}, 
		/*{
			field : 'chid',
			title : '反馈渠道',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){
				if (row.chid=='756e03c5143e4a97bc9847212ab3a24d'){
					return '妇幼微信';
				}
			}
		}, */
		{
			field : 'isreply',
			title : '状态',
			width : 3,
			sortable : true,
			formatter: function(value,row,index){
                if (row.isreply=='0'){
                    return '未回复';
                }
                else if (row.isreply=='1'){
                	 return '已回复';
                }
            }
		}, {
			field : 'hfstate',
			title : '操作',
			width : 3,
			sortable : true,
			formatter: function(value,row,index){			
				//主键
            	var pfid = row.pfid;
            	var rep = row.isreply;
            	rowPfid = row.pfid;
                if (row.isreply=='0'){
                	return '<a data-pfid='+pfid+' data-rep='+rep+' class="row-btn" style=font-size:12px;>回复</a>';          
                }
                else if (row.isreply=='1'){               	                	
                	return '<a data-pfid='+pfid+' data-rep='+rep+' class="row-btn" style=font-size:12px;>查看回复</a>';
                }
            }
		}] ],
		onLoadSuccess : function() {
			$(dataTable).datagrid("clearSelections");
		}
	});
	
	//删除
	$("#deleteBtn").click(function(){
		
		var rows = $('#dg').datagrid('getSelections');
		
		if (rows && rows.length > 0)
		{
			var ids = '';
			for (var i = 0; i < rows.length; i++) {
				if (i == rows.length - 1) {
					ids += rows[i].pfid;
				} else {
					ids += rows[i].pfid + ',';
				}
			}
			$.messager.confirm("警告", "您确定要删除所选择的记录?", function(isTrue){
				if(isTrue)
				{
					var data = {ids : ids};
					$.ajax({
						url : 'delete.json',
						type : 'POST',
						data : data,
						async : false,
						dataType : 'json',
						success : function(data)
						{
							$('#dg').datagrid("clearSelections");//清除前一次选中状态
							if (data.state == 'success') {
								$.messager.alert("提示", "删除成功！", 'info', function (){
									$('#dg').datagrid("reload");
								});
							} else {
								$.messager.alert('错误', data.message, 'error');
								$('#dg').datagrid("reload");
							}
						}
					});
					
				}
			});
			
		}
		else
		{
			$.messager.alert("提示", "请选择要删除的行。");
		}
	});

});
