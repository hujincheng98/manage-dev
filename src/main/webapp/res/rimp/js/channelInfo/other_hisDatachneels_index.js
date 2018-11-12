var dataTable = '#dg';
/**
 * 新增
 */
function add(){
	var data = $(dataTable).datagrid("getData");
	if (data.total == 0) {
		$('#addDialog').dialog('open');
	} else {
		$.messager.alert('提示', '请不要重复添加', 'warning');
	}
}
/**
 * 更新按钮
 * @returns
 */
var edit = function (){
    var row = $(dataTable).datagrid("getSelected");
    if(!row){
        $.messager.alert('提示','请选择要修改的数据.');
    } else {
        $('#editDialog').dialog('open');
    }
}

/**
 * 保存按钮
 * @returns
 */
var save = function () {
    if ($('#other_hisDatachneels_add_form').form('validate')) {
        var json = $("#other_hisDatachneels_add_form").toJson();
        var r = doPost('save.json', json);
        if (r.state == 'success') {
            $.messager.alert('提示','保存成功','info', function (){
                $('#addDialog').dialog('close');
                $('#other_hisDatachneels_add_form').form('clear');
                $(dataTable).datagrid("reload");
            });
        } else {
            $.messager.alert('错误', '保存失败', 'error');
        }
    }
}

/**
 * 修改方法
 * @returns
 */
var update = function (){
    if ($('#other_hisDatachneels_edit_form').form('validate')) {
        var json = $("#other_hisDatachneels_edit_form").toJson();
        var r = doPost('update.json', json);
        if (r.state == 'success') {
            $.messager.alert('提示','更新成功','info', function (){
                $('#editDialog').dialog('close');
                $('#other_hisDatachneels_edit_form').form('clear');
                $(dataTable).datagrid("reload");
            });
        } else {
            $.messager.alert('错误', '更新失败', 'error');
        }
    }
}

/**
 * 删除按钮
 * @returns
 */
var _remove = function (){
    var row = $(dataTable).datagrid("getSelected");
    if(row){
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
            if (r){
                $.ajax({
                    url : 'delete.json',
                    data : {id : row.id},
                    cache : true,
                    type : 'POST',
                    async : false,
                    success : function(data)
                    {
                        if (data.state == 'success') {
                            $.messager.alert("提示", "删除成功！", 'info', function (){
                                $(dataTable).datagrid("reload");
                            });
                        } else {
                            $.messager.alert('错误', data.message, 'error');
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示','请选择要删除的数据.');
    }
}

/**
 * 同步科室
 * @returns
 */
var syncDept = function () {
	var row = $(dataTable).datagrid("getSelected");
    if(row){
        $.ajax({
            url : 'syncDept.do',
            data : {id : row.id},
            cache : true,
            type : 'POST',
            async : false,
            success : function(data)
            {
                if (data.success) {
                    $.messager.alert("提示", "医生同步成功！", 'info');
                } else {
                    $.messager.alert('错误', data.message, 'error');
                }
            }
        });
    } else {
        $.messager.alert('提示','请选择同步渠道.');
    }
}

/**
 * 同步医生
 * @returns
 */
var syncDocm = function () {
	var row = $(dataTable).datagrid("getSelected");
    if(row){
        $.ajax({
            url : 'syncDocm.do',
            data : {id : row.id},
            cache : true,
            type : 'POST',
            async : false,
            success : function(data)
            {
                if (data.success) {
                    $.messager.alert("提示", "医生同步成功！", 'info');
                } else {
                    $.messager.alert('错误', data.message, 'error');
                }
            }
        });
    } else {
        $.messager.alert('提示','请选择同步渠道.');
    }
}

$(function (){

    $(dataTable).datagrid({
        url:'list.json',
        title: "数据服务信息",
        loadMsg:'数据加载中,请稍后......',
        border:true,
        fitColumns:true,
        remoteSort:false,
        toolbar: "#button-bar",
        singleSelect : true,
        rownumbers: true,
        showFooter: true,
        pagination:true,
        nowrap : true,//把数据显示在一行里
        pageSize : 10, // 页大小
        pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
        striped : true, // 行背景交换
        idField : 'id', // 主键
        columns:[[
            {field:'chHisName', title:'渠道名称', width:15, sortable:true},
            {field:'chHisUrl', title:'渠道地址', width:20, sortable:true},
            {field:'schmSynchNumday', title:'排班同步递增天数', width:15, sortable:true},
            {field:'schmSynchDate', title:'排班同步最后日期', width:15, sortable:true},
            {field:'hosName', title:'所属医院', width:15, sortable:true},
            {field:'chState', title:'渠道状态', width:10, sortable:true,
                formatter : function(value,row,index){
                    if (row.chState != 'state_1')
                        return "<span title='红色为停用状态'><font color='red'>停用</font></span>";
                    else
                        return "<span>启用</span>";
                }}
        ]]
    });

    $(dataTable).datagrid('hiddenHeaderChecked', false);
});