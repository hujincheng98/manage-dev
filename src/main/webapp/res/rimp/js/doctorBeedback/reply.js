$(function() {
	var pfid = $('#pfid').val();
	//获得题库信息
	$.ajax({
		url : '/rimp/backStageManagement/PatFeedbackQuestion.json',
		type : 'GET',
		data : {"pfid": pfid},
		async : false,
		dataType : 'JSON',
		success : function(data) {
		   //渲染数据
			produceQuesion(data);
		},
		error : function(data){			
		}
	});
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
				var choosePage = $("<div class=easyui-panel>"+ 
				   "<button disabled='disabled' id="+(i+1)+"button0 class="+(i+1)+"button value='1' style='width: 100px;height: 30px;margin-right: 20px;background-color:transparent;'>满意</button>"+
				   "<button id="+(i+1)+"button1 class="+(i+1)+"button value='2' disabled='disabled' style='width: 100px;height: 30px;margin-right: 20px;background-color:transparent;'>基本满意</button>"+
				   "<button id="+(i+1)+"button2 class="+(i+1)+"button value='3' disabled='disabled' style='width: 100px;height: 30px;margin-right: 20px;background-color:transparent;'>一般</button>"+
				   "<button id="+(i+1)+"button3 class="+(i+1)+"button value='4' disabled='disabled' style='width: 100px;height: 30px;margin-right: 20px;background-color:transparent;'>不满意</button>"				   
				+"</div>");
				templet.append(choosePage);
				//为答案填充颜色value
				var str = "."+(i+1)+"button";
				var rowClass = $(str);
				for(var j=0; j<rowClass.length;j++){
					console.log(rowClass[j]);
					if(rowClass[j].value==answer){
						//为答案填充颜色
						var id = "#"+rowClass[j].id;
						//$(id).css('background-color', '#CCE8CF');
						$(id).attr("style","color:black;width: 100px;height: 30px;margin-right: 20px;background-color:transparent;font-weight:900;font-size: 18px;");
						//var flgImg = $('<img src="../../../../plug-in/icons/tabicons/tabicons_01_01.png"></img>');
						//$(id).before(flgImg);					
					}
				}
			}
		 }else{
			 //没有数据
			 var notData = $("<h4>暂无数据</h4>");
			 templet.append(notData);
		 }
	}
	//打印
	$("#printBtn").click(function(){
		//指定区域打印
		var bdhtml=window.document.body.innerHTML; 
        var sprnstr="<!--startprint-->"; 
        var eprnstr="<!--endprint-->"; 
        var prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); 
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); 
        window.document.body.innerHTML=prnhtml; 
        window.print();
        window.close();
	});
	//返回
	$("#backBtn").click(function(){
	 	//window.location.href = "/rimp/backStageManagement/index.do?state=back&createdateStartDate="+createdateStartDate+"&createdateEndDate="+createdateEndDate+"&department="+department+"&isreply="+isreply+"&fb="+fb+"&currentPage="+currentPage;
		window.location.href = "/rimp/backStageManagement/index.do";
	});
	//提交就医反馈 
	$("#btn").click(function() {
		//回复内容不能为空
		if($('#reply').val().trim()==""){
			$.messager.alert("提示", "回复内容不能为空", "info");
			return true;
		}
		
		var data = {};
		data.pfid = pfid;
		//data.patientname = $('#patientname').val();
		//data.telephone = $('#telephone').val();
		//data.department = $('#department').val();
		//就医意见
		//data.content = $('#opinion').val();
		//回复
		data.reply = $('#reply').val().trim();
		$.ajax({
			url : '/rimp/backStageManagement/save.json',
			type : 'POST',
			data :data,
			async : false,
			dataType : 'JSON',
			success : function(data) {
				//data = JSON.parse(data);
				var state = data.state
				if (data.state == 'success') {
					$.messager.alert("提示", data.message, "info", function() {
					window.location.href = "/rimp/backStageManagement/index.do";
					});
				}
				if (data.state == 'fail') {
					$.messager.alert("提示", data.message, "info", function() {
						window.location.href = "/rimp/backStageManagement/index.do";
					});
				}
			}
		});
	});
});