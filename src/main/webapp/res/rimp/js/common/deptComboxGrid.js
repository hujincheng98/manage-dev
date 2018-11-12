
/**
 * 科室信息表单公共js
 * 页面中需包含id为"_common_dept_comboxgrid"的input元素
 */
$(function(){

    $("._common_dept_comboxgrid").each(function(index, data){
		var _this = $(this);
		$(this).combogrid({
			width : 180,
		    panelWidth:480,
            idField:'deptId',
            textField:'deptName',
    		pagination : true,// 是否分页
    		singleSelect : true,// 只允许选择一行记录
    		fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/dept/list.json",
            delay : 200, // 输入到请求的时间间隔 （毫秒）
            required:false,
            columns:[[
                {field:'deptId',title:'科室编号',hidden:true},
                {field:'deptName',title:'科室名称',width:120},
                {field:'deptTyepName',title:'科别',width:120},
                {field:'deptAttr',title:'部门',width:120},
                {field:'hosAreaName',title:'院区',width:120}
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
    							'searchParams[deptName]' : searchKey
    					};
    					var url = platRoot+"/rimp/dept/list.json";
    					$(grid).datagrid('options').queryParams = searchParam;
    					$(grid).datagrid('options').url = url;
        				$(grid).datagrid('reload');
        				$(this).combogrid("setValue", searchKey);
    				}
    			}
    		}
		});
	});

});