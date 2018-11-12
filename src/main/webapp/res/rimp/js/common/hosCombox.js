/**
 * 医院下拉列表
 */
$(function(){
	$("._common_hos_combox").each(function(index, data){
		var _this = $(this);
		$(this).combogrid({
			width : 170,
		    panelWidth:480,
            idField:'hosId',
            textField:'hosName',
    		pagination : true,// 是否分页
    		singleSelect : true,// 只允许选择一行记录
    		fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/hos/list.json",
            delay : 200, // 输入到请求的时间间隔 （毫秒）
            editable:false,
            required:false,
            columns:[[
                {field:'hosId',title:'医院编号',hidden:true},
                {field:'hosName',title:'医院名称',width:120},
                {field:'hosLevelName',title:'医院等级',width:120},
                {field:'hosAddr',title:'医院地址',width:120},
                {field:'hosTelep',title:'联系电话',width:120}
            ]],
            keyHandler : {
            	up : function() {
            		c_comboxgrid_keyUp(this);
				},
				down : function() {
					c_comboxgrid_keyDown(this);
				},
    			enter : function() {
    				c_comboxgrid_enterKey(this);
    			},
    			query : function(searchKey)
    			{
    				if (searchKey != null)
    				{
						var grid = $(this).combogrid("grid");
						var searchParam = {
    							'searchParams[hosName]' : searchKey
    					};
    					var url = platRoot+"/rimp/hos/list.json";
    					$(grid).datagrid('options').queryParams = searchParam;
    					$(grid).datagrid('options').url = url;
        				$(grid).datagrid('reload');
        				$(this).combogrid("setValue", searchKey);
    				}
    			}
    		},
    		onLoadSuccess : function (){
    			var row = $(dataTable).datagrid("getSelected");
    			if (row) {
    				$(this).combogrid("setValue", row.hosId);
    			} else {
    				var grid = $(this).combogrid('grid');
    				var data = grid.datagrid('getData');
    				$(this).combogrid('setValue', data.rows[0].hosId);
    			}
    		}
		});  
	});
	
});