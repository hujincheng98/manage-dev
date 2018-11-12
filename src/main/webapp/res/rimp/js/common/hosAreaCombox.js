/**
 * 医院下拉列表
 */
$(function(){
	$("._common_hos_area_combox").each(function(index, data){
		var _this = $(this);
		$(this).combogrid({
			width : 120,
		    panelWidth:480,
            idField:'areaId',
            textField:'areaName',
    		pagination : true,// 是否分页
    		singleSelect : true,// 只允许选择一行记录
    		fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/hosarea/list.json",
            delay : 200, // 输入到请求的时间间隔 （毫秒）
            editable:false,
            required:false,
            columns:[[
                {field:'areaName',title:'院区名称',hidden:true},
                {field:'areaAddr',title:'院区位置',width:120}
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
    							'searchParams[areaName]' : searchKey
    					};
    					var url = platRoot+"/rimp/hosarea/list.json";
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
    			}
    		}
		});  
	});
	
});