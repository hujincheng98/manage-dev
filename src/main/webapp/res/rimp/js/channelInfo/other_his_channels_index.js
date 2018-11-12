/**
 * 格式化日期输出
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var dataTable = '#dg';
/**
 * 新增
 */
function add(){
    $('#addDialog').dialog('open');
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
    if ($('#hos_other_channels_add_form').form('validate')) {
        var json = $("#hos_other_channels_add_form").toJson();
        var r = doPost('save.json', json);
        if (r.state == 'success') {
            $.messager.alert('提示','保存成功','info', function (){
                $('#addDialog').dialog('close');
                $('#hos_other_channels_add_form').form('clear');
                $(dataTable).datagrid("reload");
            });
        } else {
            $.messager.alert('错误', '保存失败', 'error');
        }
    }
}
/**
 * 查询按钮
 * @returns
 */
var _search = function () {
    $(dataTable).datagrid('load', $('#searchform').toJson());
}
/**
 * 修改方法
 * @returns
 */
var update = function (){
    if ($('#hos_other_channels_edit_form').form('validate')) {
        var json = $("#hos_other_channels_edit_form").toJson();
        var r = doPost('update.json', json);
        if (r.state == 'success') {
            $.messager.alert('提示','更新成功','info', function (){
                $('#editDialog').dialog('close');
                $('#hos_other_channels_edit_form').form('clear');
                $(dataTable).datagrid("reload");
            });
        } else {
            $.messager.alert('错误', '更新失败', 'error');
        }
    }
}

/**
 * 启用按钮
 * @returns
 */
var enabled = function () {
    var row = $(dataTable).datagrid("getSelected");
    if (row.chState == 'state_1') {
        $.messager.alert('提示','该渠道已启用','warning');
        return;
    }
    $.ajax({
        url : 'update.json',
        cache : true,
        type : 'POST',
        data : JSON.stringify({chHisId : row.chHisId, chState : 'state_1'}),
        contentType : 'application/json',
        async : false,
        success : function(data)
        {
            if (data.state == 'success') {
                $(dataTable).datagrid("reload");
            } else {
                $.messager.alert('错误', '启用失败', 'error');
            }
        }
    });
}

/**
 * 停用按钮
 * @returns
 */
var disable = function () {
    var row = $(dataTable).datagrid("getSelected");
    if (row.chState == 'state_0') {
        $.messager.alert('提示','该渠道已停用','warning');
        return;
    }
    $.ajax({
        url : 'update.json',
        cache : true,
        type : 'POST',
        data : JSON.stringify({chHisId : row.chHisId, chState : 'state_0'}),
        contentType : 'application/json',
        async : false,
        success : function(data)
        {
            if (data.state == 'success') {
                $(dataTable).datagrid("reload");
            } else {
                $.messager.alert('错误', '停用失败', 'error');
            }
        }
    });
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
                var data = [];
                $.ajax({
                    url : 'delete.json',
                    data : {chHisId : row.chHisId},
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
$(function (){

    $(dataTable).datagrid({
        url:'list.json',
        title: "业务渠道信息",
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
        idField : 'chId', // 主键
        columns:[[
            {field:'chHisId', title:'业务渠道编号', checkbox:true, width:10, sortable:true},
            {field:'chHisName', title:'业务渠道名称', width:10, sortable:true},
            {field:'chHisUrl', title:'业务渠道地址', width:15, sortable:true},
            {field:'chHisNamespace', title:'命名空间', width:10, sortable:true},
            {field:'hosName', title:'所属医院', width:10, sortable:true},
            {field:'chState', title:'渠道状态', width:10, sortable:true,
                formatter : function(value,row,index){
                    if (row.chState != 'state_1')
                        return "<span title='红色为停用状态'><font color='red'>停用</font></span>";
                    else
                        return "<span>启用</span>";
                }},
            {field:'createDate', title:'接入号源时间', width:10, sortable:true,formatter:function(value,row,index){
                return new Date(row.createDate).Format("yyyy-MM-dd hh:mm:ss");
            }},
            {field:'chHisToken', title:'平台认证token', width:15, sortable:true}
        ]]
    });

    $(dataTable).datagrid('hiddenHeaderChecked', false);
});