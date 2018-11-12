
/**
 * 班次名称表单公共js
 * 页面中需包含id为"_common_shift_comboxgrid"的input元素
 */
$(function(){
    $("._common_shift_comboxgrid").each(function(index, data){
        var _this = $(this);
        $(this).combogrid({
            width : 180,
            panelWidth:480,
            idField:'shiftName',
            textField:'shiftName',
            pagination : true,// 是否分页
            singleSelect : true,// 只允许选择一行记录
            fitColumns:true,
            mode : 'remote',// 远程连接方式
            method : 'POST',// 请求方式
            dataType : 'json',
            url:platRoot+"/rimp/shift/list.json",
            delay : 200, // 输入到请求的时间间隔 （毫秒）
            required:false,
            columns:[[
                {field:'shiftId',title:'班次',hidden:true},
                {field:'shiftName',title:'班次名称 ',width:120},
                {field:'shiftStartDate',title:'开始时间',width:120},
                // {field:'deptId',title:'所属科室' ,hidden:true},
                {field:'shiftEndDate',title:'结束时间',width:120}
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
                            'searchParams[shiftName]' : searchKey
                        };
                        var url = platRoot+"/rimp/shift/list.json";
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