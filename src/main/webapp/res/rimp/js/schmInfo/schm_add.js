$(function() {
	var dataTable = $("#addTable");
	var addTableTile = $("#addTableTile");


	var schmId = getURLParameter("schmId");

	$("#addBtn").click(
		function()
		{
			var shiftId = $("#shiftId").val();
            var schmDeptName = $("#schmDeptName").val();
			var regcategoryName =$("#regcategoryName").val();
			var startDate = $('#shiftStartDate').val();
			var endDate = $('#shiftEndDate').val();
			var rows =  $(dataTable).datagrid("getRows");
			if (rows.length > 0) {
				var tislEndDateValue = "";
				var tislEndDate = $(dataTable).datagrid("getEditor", {index : rows.length - 1, field: 'tislEndDate'});
				if (tislEndDate) {
					tislEndDateValue = $(tislEndDate.target).val();
				} else {
					tislEndDateValue = rows[rows.length - 1].tislEndDate;
				}
				if(endDate == tislEndDateValue){
					return;
				}
				$(dataTable).datagrid("appendRow", {"schmId":schmId,"shiftId" : shiftId, 'tislStartDate' : tislEndDateValue, 'tislEndDate' : endDate });
			} else {
				$(dataTable).datagrid("appendRow", {"schmId":schmId, "shiftId" : shiftId, 'tislStartDate' : startDate, 'tislEndDate' : endDate});
			}
			var editIndex = $(dataTable).datagrid("getRows").length-1;
			$(dataTable).datagrid("beginEdit", editIndex);
		}
	);

	$("#saveBtn").click(
		function()
		{
			var edatagrid = $(dataTable).edatagrid("getRows");
			var msg = validateTotal(edatagrid);
			msg += validateGreaterThan(edatagrid);
			msg += validateTimes(edatagrid);
			if (msg != "")
			{
				$.messager.alert("提示", msg);
				return false;
			}
			var json = $(addForm).toJson();
			$.ajax({
				url : 'save.json',
				type : 'POST',
				data : json,
				success : function(data)
				{
					$.messager.alert('提示信息','数据保存成功','info');
				},
				error : function (XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			});
		}
	);


	$("#saveSchmBtn").click(
			function()
			{

				//空判断
				var schmDate = $("#schmDate").datebox('getValue');
				var docmId = $("#docmIdtocontext").val();
				var schmDeptName = $("#schmDeptIDtocontext").val();
				var shiftId = $("#schmShiftIDtocontext").val();
				var regcategory = $("#regcategoryIDtocontext").val();
				var schmOnRegiSum = $("#schmOnRegiSum").val();
				var schmDownRegiSum = $("#schmDownRegiSum").val();
				var schmOnSum = $("#schmOnSum").val();
				var schmDownSum = $("#schmDownSum").val();

				if(schmDate==null || schmDate==""){
					$.messager.alert("警告", "排班日期不能为空!");
					return false;
				}

				if(docmId==null || docmId==""){
					$.messager.alert("警告", "医生不能为空!");
					return false;
				}

				if(schmDeptName==null || schmDeptName==""){
					$.messager.alert("警告", "排班科室不能为空!");
					return false;
				}

				if(shiftId==null || shiftId==""){
					$.messager.alert("警告", "班次不能为空!");
					return false;
				}

				if(schmOnRegiSum==null || schmOnRegiSum==""){
					$.messager.alert("警告", "线上挂号限数不能为空!");
					return false;
				}

				if(schmDownRegiSum==null || schmDownRegiSum==""){
					$.messager.alert("警告", "线下挂号限数不能为空!");
					return false;
				}

				if(schmOnSum==null || schmOnSum==""){
					$.messager.alert("警告", "线上预约限数不能为空!");
					return false;
				}

				if(schmDownSum==null || schmDownSum==""){
					$.messager.alert("警告", "线下预约限数不能为空!");
					return false;
				}


				var json = $(addForm).toJson();
				$.ajax({
					url : 'save.json',
					type : 'POST',
					data : json,
					success : function(data)
					{
						if(data=="exist"){
							$.messager.alert('提示信息','相同排班日期、医生、排班科室、班次的排班已经存在!','info');
						}else if(data=="succ"){
							$.messager.alert('提示信息','数据保存成功!','info');
						}else{
							$.messager.alert('提示信息','出现异常保存失败','info');
						}

					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
					}
				});
			}
		);


	$("#deleteBtn").click(
		function()
		{
			var row = $(dataTable).datagrid("getSelected");
			if (row != null)
			{
				if (row.schmDetailId != null)
				{
					var data = {"schmDetailId" : row.schmDetailId, "schmId" : row.schmId };
					$.ajax({
						cache : false,
						url : 'delete_detail.json',
						type : 'POST',
						data : data,
						async : false,
						success : function(data)
						{
							if (data.state == 'success')
							{
								$.messager.alert("提示", "删除成功", 'info', function (){
									var index = $(dataTable).datagrid("getRowIndex", row);
									$(dataTable).datagrid("deleteRow", index);
								});
							} else {
								$.messager.alert("警告", data.message, 'warning');
							}
						}
					});
				}
			}
			else
			{
				$.messager.alert("提示", "请选择要删除的行。");
			}
		}
	);

	$("#updateBtn").click(
		function()
		{
			var json = $(addForm).toJson();
			$(dataTable).datagrid("acceptChanges");
			var edatagrid = $(dataTable).edatagrid("getRows");
			var msg = validateTotalNum(edatagrid);
			msg += validateGreaterThan(edatagrid);
			msg += validateTimes(edatagrid);
			if (msg != "")
			{
				$.messager.alert("提示", msg);
				return false;
			}
			$(edatagrid).each(function(index, data){
				json["details[" + index + "].detailDowngoNum"] = data.detailDowngoNum == null ? "" : data.detailDowngoNum;
				json["details[" + index + "].detailDownreNum"] = data.detailDownreNum == null ? "" : data.detailDownreNum;
				json["details[" + index + "].detailUpgoNum"] = data.detailUpgoNum == null ? "" : data.detailUpgoNum;
				json["details[" + index + "].detailUpreNum"] = data.detailUpreNum == null ? "" : data.detailUpreNum;
				json["details[" + index + "].orgId"] = data.orgId == null ? "" : data.orgId;
				json["details[" + index + "].remarks"] = data.remarks == null ? "" : data.remarks;
				json["details[" + index + "].schmDetailId"] = data.schmDetailId == null ? "" : data.schmDetailId;
				json["details[" + index + "].schmDownNum"] = data.schmDownNum == null ? "" : data.schmDownNum;
				json["details[" + index + "].schmId"] = data.schmId == null ? "" : data.schmId;
				json["details[" + index + "].schmOnNum"] = data.schmOnNum == null ? "" : data.schmOnNum;
				json["details[" + index + "].schmState"] = data.schmState == null ? "" : data.schmState;
				json["details[" + index + "].shiftId"] = data.shiftId == null ? "" : data.shiftId;
				json["details[" + index + "].tislEndDate"] = data.tislEndDate == null ? "" : data.tislEndDate;
				json["details[" + index + "].tislStartDate"] = data.tislStartDate == null ? "" : data.tislStartDate;
			});
			var data = json;
			$.ajax({
				cache : true,
				url : 'update_detail.json',
				type : 'POST',
				data : data,
				async : false,
				success : function(data)
				{
					if (data.state == 'success')
					{
						$.messager.alert("提示", data.message, "info", function(){
							location.reload();
						});
					}
					else
					{
						$.messager.alert("警告", data.message, "warning");
					}
				}
			});
		}
	);

	//表格初始化
	$(dataTable).edatagrid({
		url:'listbyshitid.json',
		title: "时段明细列表",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : true,
		rownumbers: false,
		showFooter: true,
		method : 'POST',// 请求方式
        dataType : 'json',
        contentType: "application/json;charset=utf-8",
		columns:[[
		          {field:'schmDetailId', title:"选择", checkbox:true},
		          {field:'orgId', hidden:true},
		          {field:'remarks', hidden:true},
		          {field:'schmId', hidden:true},
		          {field:'shiftId', hidden:true},
		          {field:'schmState', hidden:true},
		    {field:'tislStartDate',title:'时段起始时间', editor:{type:'datebox', options : {required:true, dateFmt:'H:mm', minDate:'#F{$dp.$D(\'shiftStartDate\')}', maxDate:'#F{$dp.$D(\'shiftEndDate\')}'}}, width:"220px"},
		    {field:'tislEndDate',title:'时段终止时间', editor:{type:'datebox', options : {required:true, dateFmt:'H:mm', minDate:'#F{$dp.$D(\'shiftStartDate\')}', maxDate:'#F{$dp.$D(\'shiftEndDate\')}'}}, width:"220px"},
			{field:'schmOnNum',title:'线上可预约限数', editor:{type:'numberbox', options : {value:0, min:0, precision:0}}, width:"220px"},
			{field:'schmDownNum',title:'线下可预约限数', editor:{type:'numberbox', options : {value:0, min:0, precision:0}}, width:"220px"}
		]]
	});

	//表格初始化
	$(addTableTile).edatagrid({
		url:'listbyshitid.json',
		title: "时段明细列表",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : true,
		rownumbers: false,
		showFooter: true,
		method : 'POST',// 请求方式
        dataType : 'json',
        contentType: "application/json;charset=utf-8",
		columns:[[
		          {field:'schmDetailId', title:"选择", checkbox:true},
		          {field:'orgId', hidden:true},
		          {field:'remarks', hidden:true},
		          {field:'schmId', hidden:true},
		          {field:'shiftId', hidden:true},
		          {field:'schmState', hidden:true},
		    {field:'tislStartDate',title:'时段起始时间', width:"220px"},
		    {field:'tislEndDate',title:'时段终止时间', width:"220px"},
			{field:'tislOnline',title:'线上可预约限数(%)', width:"220px"},
			{field:'tislOffline',title:'线下可预约限数(%)', width:"220px"}
		]]
	});





	//日期事件
	$('#schmDate').datebox({
		editable: false,
	    onSelect: function(date){
	        if(date.getDay()==0) {
	        	$("#schmWeek").val("星期日");
	        }
	        if(date.getDay()==1) {
	        	$("#schmWeek").val("星期一");
	        }
	        if(date.getDay()==2) {
	        	$("#schmWeek").val("星期二");
	        }
	        if(date.getDay()==3) {
	        	$("#schmWeek").val("星期三");
	        }
	        if(date.getDay()==4) {
	        	$("#schmWeek").val("星期四");
	        }
	        if(date.getDay()==5) {
	        	$("#schmWeek").val("星期五");
	        }
	        if(date.getDay()==6) {
	        	$("#schmWeek").val("星期六");
	        }
	    }
	});

	//医生列表选择
	if($("#docmId")){
		$('#docmId').combogrid({
		    panelWidth:450,
            idField:'docmId',
            textField:'docmName',
    		pagination : true,// 是否分页
    		singleSelect : true,// 只允许选择一行记录
    		fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/hosdocm/list.json",
            delay : 200, // 输入到请求的时间间隔 （毫秒）
            required:false,
            columns:[[
                {field:'docmId',title:'医生编号',hidden:true},
                {field:'docmName',title:'姓名 ',width:120},
                {field:'docmTitleName',title:'职称',width:120},
                {field:'deptId',title:'所属科室' ,hidden:true},
                {field:'deptName',title:'所属科室',width:120}
            ]],
            keyHandler: {
            	up: function() {
            		c_comboxgrid_keyUp(this);
				},
				down: function() {
					c_comboxgrid_keyDown(this);
				},
    			enter: function() {
    				c_comboxgrid_enterKey(this);
    			},
    			query: function(searchKey)
    			{
    				if (searchKey != null)
    				{
						var grid = $(this).combogrid("grid");
						var searchParam = {
    							'searchParams[docmName]' : searchKey
    					};
    					var url = platRoot+"/rimp/hosdocm/list.json";
    					$(grid).datagrid('options').queryParams = searchParam;
    					$(grid).datagrid('options').url = url;
        				$(grid).datagrid('reload');
        				$(this).combogrid("setValue", searchKey);
    				}
    			}
    		},
    		onSelect: function(rec){
					console.log('11111111111111')
    			var grid = $("#docmId").combogrid("grid");// 获取表格对象
    			var row = grid.datagrid('getSelected');// 获取行数据
    			$("#docmTitle").val(row.docmTitleName);
    			$("#deptName").val(row.deptName);
    			$('#schmDeptName').combogrid('setValue', row.deptName);
    			$('#docmIdtocontext').val(row.docmId);
    			$('#deptIdtocontext').val(row.deptId);
    			$('#schmDeptIDtocontext').val(row.deptId);
    			$('#hosId').val(row.deptHosId);
            }
		});
	}

	//科室选择
	if($("#schmDeptName")){
		$('#schmDeptName').combogrid({
            panelWidth:450,
            idField:'deptId',
            textField:'deptName',
            pagination : true,// 是否分页
            singleSelect : true,// 只允许选择一行记录
            fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/dept/list.json",
            delay : 300, // 输入到请求的时间间隔 （毫秒）
            required:false,
            columns:[[
                {field:'deptId',title:'科室编号',hidden:true},
                {field:'deptName',title:'科室名称',width:120},
                {field:'deptTyepName',title:'科别',width:120},
                {field:'deptAttr',title:'部门',width:120},
                {field:'hosAreaName',title:'院区',width:120}
            ]],
            keyHandler: {
                up: function() {
                    c_comboxgrid_keyUp(this);
                },
                down: function() {
                    c_comboxgrid_keyDown(this);
                },
                enter: function() {
                    c_comboxgrid_enterKey(this);
                },
                query: function(searchKey)
                {
                    if (searchKey != null)
                    {
                        var grid = $(this).combogrid("grid");
                        var searchParam = {
                            'searchParams[deptName]' : searchKey
                        };
                        var url = platRoot+"/rimp/dept/list.json";
                        $(grid).datagrid('options').queryParams = searchParam;
                        $(grid).datagrid('options').url = url;
                        $(grid).datagrid('reload');
                        $(this).combogrid("setValue", searchKey);
                    }
                }
            },
			onSelect: function (index, row){
					// var grid = $(this).combogrid("grid"); 获取表格对象
					//  var row = grid.datagrid('getSelected'); 获取行数据
					// $('#schmDeptName').val(row.deptName);
                    $(this).val(row.deptName);
					$('#schmDeptIDtocontext').val(row.deptId);
					// $('#hosId').val(row.hosId);
		    }
		});
	}


    //挂号类别选择
    if($("#regcategoryName")){
        $('#regcategoryName').combogrid({
            panelWidth:300,
            idField:'id',
            textField:'regcategory',
            pagination : true,// 是否分页
            singleSelect : true,// 只允许选择一行记录
            fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/regcategory/list.json",
            delay : 200, // 输入到请求的时间间隔 （毫秒）
            required:false,
            columns:[[
                {field:'id',title:'编号',hidden:true},
                {field:'regcategory',title:'挂号类别',width:120},
                {field:'regfee',title:'挂号费',width:120},
				{field:'servcoding',title:'HIS编码',hidden:true}
            ]],
            keyHandler: {
                up: function() {
                    c_comboxgrid_keyUp(this);
                },
                down: function() {
                    c_comboxgrid_keyDown(this);
                },
                enter: function() {
                    c_comboxgrid_enterKey(this);
                },
                query: function(searchKey)
                {
                    if (searchKey != null)
                    {
                        var grid = $(this).combogrid("grid");
                        var searchParam = {
                            'searchParams[regcategory]' : searchKey
                        };
                        var url = platRoot+"/rimp/regcategory/list.json";
                        $(grid).datagrid('options').queryParams = searchParam;
                        $(grid).datagrid('options').url = url;
                        $(grid).datagrid('reload');
                        $(this).combogrid("setValue", searchKey);
                    }
                }
            },
            onSelect: function (index, row){
                // var grid = $(this).combogrid("grid"); 获取表格对象
                //  var row = grid.datagrid('getSelected'); 获取行数据
                console.log(row)
                // $('#schmDeptName').val(row.deptName);
                $(this).val(row.regcategory);
                $('#regcategoryIDtocontext').val(row.id);
                $('#servcoding').val(row.servcoding);
                $('#regcategory').val(row.regcategory);
            }
        });
    }

    //班次列表选择
	if($("#shiftId")){
		$('#shiftId').combogrid({
		    panelWidth:480,
            idField:'shiftId',
            textField:'shiftName',
    		pagination : true,// 是否分页
    		singleSelect : true,// 只允许选择一行记录
    		fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/shift/list.json",
            required:false,
            columns:[[
                //{field:'shiftId',title:'班次编号'},
                {field:'shiftName',title:'班次名称 ',width:120},
                {field:'shiftStartDate',title:'起始时间',width:120},
                {field:'shiftEndDate',title:'终止时间',width:120}
            ]],
            keyHandler: {
                up: function() {
                    c_comboxgrid_keyUp(this);
                },
                down: function() {
                    c_comboxgrid_keyDown(this);
                },
                enter: function() {
                    c_comboxgrid_enterKey(this);
                },
    			query: function(searchKey)
    			{
    				if (searchKey != null && searchKey != "")
    				{
    					var grid = $(this).combogrid("grid");
    					setTimeout(function () {
        					var searchParam = {
        							'searchParams[shiftName]' : searchKey
        					};
        					var url = platRoot+"/rimp/shift/list.json";
        					$(grid).datagrid('options').queryParams = searchParam;
        					$(grid).datagrid('options').url = url;
            				$(grid).datagrid('load');
                            $(this).combogrid("setValue", searchKey);
                        }, 500);

    				}
    			}
    		},
    		onSelect: function(rec){
    			var grid = $("#shiftId").combogrid("grid");// 获取表格对象
				var row = grid.datagrid('getSelected');// 获取行数据
    			$("#shiftStartDate").val(row.shiftStartDate);
    			$("#shiftEndDate").val(row.shiftEndDate);
    			$(this).val(row.shiftName);
    			$("#schmShiftIDtocontext").val(row.shiftId);

    			//生成班次时段信息
    			var url = $(addTableTile).datagrid('options').url;
				$(addTableTile).datagrid('options').queryParams = {shiftId:row.shiftId};
				$(addTableTile).datagrid('options').url = url;
				$(addTableTile).datagrid('load');
            }
		});
	}

	if (schmId != null || schmId != undefined)
	{
		var data = {"schmId" : schmId};
		$.ajax({
			cache : false,
			url : 'select.json',
			type : 'POST',
			data : data,
			async : false,
			success : function(data)
			{
				$("#addForm").form('load', data);
				var shiftId = data.shiftId;
				if (shiftId != null || shiftId != undefined)
				{
					var data = {"id" : shiftId};
					$.ajax({
						cache : false,
						url:platRoot+"/rimp/shift/select.json",
						type : 'POST',
						data : data,
						async : false,
						success : function(shiftdata)
						{
							$("#shiftStartDate").val(shiftdata.shiftStartDate);
							$("#shiftEndDate").val(shiftdata.shiftEndDate);
						}
					});
				}
			}
		});
		$.ajax({
			cache : false,
			url : "selectDeatils.json",
			type : 'POST',
			data : data,
			async : false,
			success : function(shiftdata)
			{
				$(dataTable).datagrid("loadData", shiftdata);
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

});


function validateTotalNum(datagrid)
{
	var schmOnSum = $("#schmOnSum").val();
	var schmDownSum = $("#schmDownSum").val();
	var totalOnline = 0;
	var totalOffline = 0;
	$(datagrid).each(function(index, data){
		totalOnline += parseInt(data.schmOnNum);
		totalOffline += parseInt(data.schmDownNum);
	});
	var msg = "";
	if (totalOnline != schmOnSum)
	{
		msg += "[线上可预约限数]总和不为" + schmOnSum + "。<br />";
	}
	if (totalOffline != schmDownSum)
	{
		msg += "[线下可预约限数]总和不为" + schmDownSum + "。<br />";
	}
	return msg;

}
