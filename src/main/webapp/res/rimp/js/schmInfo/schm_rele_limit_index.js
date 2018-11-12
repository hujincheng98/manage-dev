$.extend($.fn.numberspinner.defaults.rules, {
        rateCheck:{
            validator:function(value,param){
	              if(/^[+]?[0-9]\d*$/.test(value)){
	                return parseFloat(value) >= parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
	              }else{
	                return false;
	              }
            },
            message:'请输入1到100之间整数'
       }
  });
$(function() {
	var formData = null;
	$("#checkBtn").change(function(){
		if($(this).attr("checked")){
			$("#dataTable input[name=ruleFlag]").val("1");
			$("#releNextDate").my97({
				disabled:false
			});
			$("#releNextDate").my97("setValue",formData.ruleDate);
			$("#dataTable").form("validate");
		}else{
			$("#dataTable input[name=ruleFlag]").val("0");
			formData.ruleDate = $("#releNextDate").my97("getValue");
			$("#releNextDate").my97({
				disabled:true
			});
		}
	});
	
	$("#hosId").combobox({
		onSelect:function(record){
			console.log(record)
		}
	});
	
	$("#cancelBtn").linkbutton({
		onClick:function(){
			window.parent.closeCurrentTab();
		}
	});
	$("#dataTable").form('load','select.json');
	$("#dataTable").form({
		onLoadSuccess:function(data){
//			console.log(data)
			formData = data;
			if("1"==data.ruleFlag){
				$("#checkbox").checkbox("checkAll");
				$("#releNextDate").my97("setValue",data.ruleDate);
			}else{
				$("#releNextDate").my97({
					disabled:true
				});
			}
		}
	});
	$("#saveNumberBtn").click(function(){
		
		if($("#dataTable").form('validate')){
			var json=$("#dataTable").toJson();
			if(json.ruleDate==""){
				json.ruleDate = formData.ruleDate;
			}
			$.ajax({
				url : 'update.json',
				type : 'POST',
				data : json,
				success : function(data)
				{
					if (data.state)
					{
						$.messager.alert("提示", "保存成功！");
					}
				}
			});
		}else{
			//$("#dataTable").form('load','select.json');
		}
	});
	
});