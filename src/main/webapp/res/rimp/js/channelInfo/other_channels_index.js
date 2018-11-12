/**
 * 新增按钮
 */
var add = function () {
    $('#addDialog').dialog('open');
}

var dataTable = '#dg';

var messCodeArr = [];

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
 * 删除按钮
 * @returns
 */
var _remove = function (){
    var rows = $(dataTable).datagrid("getSelections");
    if (rows && rows.length > 0)
    {
        var chIdArr = '';
        for (var i = 0; i < rows.length; i++) {
            if (i == rows.length - 1) {
                chIdArr += rows[i].chId;
            } else {
                chIdArr += rows[i].chId + ',';
            }
        }
        $.messager.confirm('确认','您确认想要删除记录吗？',function(isTrue){
            if (isTrue){
                var data = {chIdArr : chIdArr};
                $.ajax({
                    url : 'delete.json',
                    data : data,
                    cache : true,
                    type : 'POST',
                    async : false,
                    dataType : 'json',
                    success : function(data)
                    {
                        $('#dg').datagrid("clearSelections");//清除前一次选中状态
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
 * 保存按钮
 * @returns
 */
var save = function () {
    if ($('#hos_other_channels_add_form').form('validate')) {
        var json = $("#hos_other_channels_add_form").toJson();
        json.ext1="";
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
    if(!row){
        $.messager.alert('提示','请选择要启用的渠道.');
    } else {
        if (row.chState == 'state_1') {
            $.messager.alert('提示','该渠道已启用','warning');
            return;
        }
        $.ajax({
            url : 'update.json',
            cache : true,
            type : 'POST',
            data : JSON.stringify({chId : row.chId, chState : 'state_1'}),
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

}

/**
 * 停用按钮
 * @returns
 */
var disable = function () {
    var row = $(dataTable).datagrid("getSelected");
    if(!row){
        $.messager.alert('提示','请选择要停用的渠道.');
    } else {
    if (row.chState == 'state_0') {
        $.messager.alert('提示','该渠道已停用','warning');
        return;
    }
    $.ajax({
        url : 'update.json',
        cache : true,
        type : 'POST',
        data : JSON.stringify({chId : row.chId, chState : 'state_0'}),
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
    });}
}

/**
 * 查询按钮
 * @returns
 */
var _search = function () {
    $(dataTable).datagrid('load', $('#searchform').toJson());
}

/**
 * 消息管理按钮
 * @returns
 */
var messManage = function () {
    var row = $(dataTable).datagrid("getSelected");
    if (row.ext1 == ''){
        messCodeArr = [];
        $.ajax({
            url : 'findMessCodes.json',
            cache : true,
            type : 'POST',
            data: {'chId' : row.chId},
            dataType: 'json',
            async : false,
            success : function(data)
            {
                if (data.state == 'success') {
                    $(data.message).each(function (e){
                        messCodeArr.push($(this)[0].messCode);
                    });
                }
            }
        });
        $('#messManageDialog').dialog('open');
    } else {
        $.messager.alert('错误', 'HIS渠道不支持', 'error');
    }
}

/**
 * 保存消息管理
 * @returns
 */
var messSave = function (){
    var row = $(dataTable).datagrid("getSelected");
    var grid = $('#messList').combogrid('grid');
    var rows = grid.datagrid("getChecked");
    var messCodes = '';
    for (var i = 0; i < rows.length; i++) {
        if (i == rows.length - 1) {
            messCodes += rows[i].messCode;
        } else {
            messCodes += rows[i].messCode + ',';
        }
    }
    $.ajax({
        url : 'messSave.json',
        cache : true,
        type : 'POST',
        data: {'messCodes' : messCodes, 'chId' : row.chId, 'hosId' : row.hosId},
        dataType: 'json',
        async : false,
        success : function(data)
        {
            if (data.state == 'success') {
                $.messager.alert('提示','保存成功','info', function (){
                    $('#messManageDialog').dialog('close');
                    $('#messList').combogrid('clear');
                });
            } else {
                $.messager.alert('错误', data.message, 'error');
            }
        }
    });
}

/**
 * 保存接口权限
 * @returns
 */
var saveResAuth = function(){

    /*var ser = $("form").serialize();
     var json = $("form").toJson();
     $.ajax({
     url : 'saveResAuth.json',
     type : 'GET',
     data : {'ser': ser,'json': json},
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
     }else{
     $.messager.alert("提示", "数据保存失败！");
     }
     }
     });*/
    $('#res_auth_add_form').form('submit', {
        url : 'saveResAuth.json',
        onSubmit: function(){
            $.messager.progress({
                //msg:'努力保存中...',
                text:'努力保存中...',
            });

        },
        success : function(data) {
            data = JSON.parse(data);
            if (data.state == 'success') {
                $.messager.alert("提示", data.message, "info", function() {
                    $('#dd').dialog('close');
                    $('#dg').datagrid("reload");
                    $(dataTable).datagrid("clearSelections");
                    $.messager.progress('close');
                });
            } else {
                $.messager.alert("提示", "数据保存失败！");
            }
        }
    });
}

/**
 * 全选接口
 */
var chooseAllRes = function(){
    $('.res_auth_content input[type="checkbox"]').each(function(){
        $(this).prop("checked", true);
    });
}

/**
 * 全部不选
 */
var notChooseAllRes = function(){
    $('.res_auth_content input[type="checkbox"]').each(function(){
        $(this).prop("checked", false);
    });
}

/**
 * 点击父级复选框勾选所有子级复选框
 */
$(document).on('click','.res-auth-parent input[type="checkbox"]',function(){
    var appearIndex = $(this).attr('id').indexOf("-");
    var parId = $(this).attr('id').substring(0,appearIndex);
    var child = parId+"-child";
    var parent = parId+"-parent";
    var chId = $('#chId').val();
    if($('#'+parent).is(':checked') ) {
        $('#'+child+' ul li input[type="checkbox"]').each(function(){
            $(this).prop("checked", true);
        });
    }else{
        $('#'+child+' ul li input[type="checkbox"]').each(function(){
            $(this).prop("checked", false);
        });
    }
});

/**
 * 点击子级复选框若子级全选则父级被选中
 */
$(document).on('click','.res-auth-child input[type="checkbox"]',function(){
    var appearIndex = $(this).attr('id').indexOf("-");
    var id = $(this).attr('id').substring(0,appearIndex);
    var child = id+"-child";
    var parent = id+"-parent";
    var childCheck = false;
    $('#'+child+' ul li input[type="checkbox"]').each(function(){
        if($(this).is(':checked')){ //子级复选框选中
            childCheck = true;
        }else{ //子级复选框未选中
            $('#'+parent).prop("checked", false);
            childCheck = false;
            return false; //跳出循环
        }
    });
    if(childCheck){ //子级复选框全部选中
        $('#'+parent).prop("checked", true);
    }
});

/**
 * 动态生成接口数据
 */
function loadResData(data){
    var templet = $("#templet");
    //var dataList = JSON.parse(data);
    var dataList = data;
    var outSph = "";
    var j = 0;
    if(dataList.length>0){
        for ( var i = 0; i < dataList.length; i++) {
            //最外层div
            outSph = $('<div class="clearfix res-auth-floor"></div>');
            var parIsCheck = dataList[i].checked;
            if($.trim(parIsCheck) == 'true'){
                parIsCheck = 'checked=checked';
            }else{
                parIsCheck = '';
            }
            var parent = $('<div id=res'+(i+1)+' class="res-auth-parent" style="float:left;width:100px;height: 99%;"> <label><input type="checkbox" id="res'+(i+1)+'-parent" value='+dataList[i].chId +' '+parIsCheck+' />'+dataList[i].model+'</label></div>');
            outSph.append(parent);
            var child = $('<div id="res'+(i+1)+'-child" class="res-auth-child"></div>');
            var childUl = $('<ul class="clearfix pl100"></ul>');
            for ( j = 0; j < dataList[i].children.length; j++) {
                //判断复选框是否被选中
                var isChecked = dataList[i].children[j].checked;
                if($.trim(isChecked) == 'true'){
                    isChecked = 'checked=checked';
                }else{
                    isChecked = '';
                }
                var childLi = $('<li><label><input type="checkbox" id="res'+(i+1)+'-child'+(j+1)+'" name=resId value='+dataList[i].children[j].id+' '+isChecked +' />'+dataList[i].children[j].apiName+'</label></li>');
                childUl.append(childLi);
            }
            child.append(childUl);
            outSph.append(child);
            templet.append(outSph);
        }
    }

}
/**
 * 接口权限设置页面
 * @returns
 */
$(document).on('click','.row-btn',function(){
    var dataChId = $(this).attr("data-chId");
    var dataToken = $(this).attr("data-token");
    //渠道表中对应的hosId
    var chaHosId = $(this).attr("data-hosId");
    $('#dd').dialog({
        title: '接口权限设置',
        width: 840,
        height: 600,
        closed: false,
        cache: false,
        href: 'resAuthView.do?chId='+dataChId+'&token='+dataToken+'&chaHosId='+chaHosId,
        modal: true,
        toolbar:[
            {
                text:'保存',
                iconCls:'icon-save',
                handler:saveResAuth
            },
            /*{
             text:'全选',
             iconCls:'icon-search',
             handler:chooseAllRes
             },
             {
             text:'全不选',
             iconCls:'icon-edit',
             handler:notChooseAllRes
             },*/
            {
                text:'取消',
                iconCls:'icon-cancel',
                handler:function(){
                    $.messager.confirm('提示:','您确认要取消吗?',function(r){
                        if(r){
                            $('#dd').dialog('close');
                            $('#res_auth_add_form').form('clear');
                        }
                    });
                }
            }
        ]
    });

    //动态加载接口权限数据
    setTimeout(function () {
        //获得接口数据
        $.ajax({
            url : 'loadRes.json',
            type : 'GET',
            data : {"chId": dataChId,"token":dataToken},
            async : true,
            dataType : 'JSON',
            success : function(data) {
                //渲染数据
                loadResData(data);
            },
            error : function(data){
            }
        });
    }, 500);

});

$(function (){
    var columns =[[
        {field:'chId', title:'渠道编号', checkbox:true, width:30, sortable:true},
        {field:'chName', title:'渠道名称', width:30, sortable:true},
        {field:'chUrl', title:'渠道地址', width:30, sortable:true},
        {field:'hosName', title:'所属医院', width:30, sortable:true},
        {field:'chTypeName', title:'渠道类型', width:30, sortable:true},
        {field:'isReservationName', title:'是否可预约', width:30, sortable:true},
        {field:'isRegistrationName', title:'是否可挂号', width:30, sortable:true},
        {field:'chGradeName', title:'渠道评分', width:30, sortable:true},
        {field:'chStateName', title:'渠道状态', width:30, sortable:true,
            formatter : function(value,row,index){
                if (row.chState == 'state_0')
                    return "<span title='红色为停用状态'><font color='red'>"+ row.chStateName + "</font></span>";
                else
                    return row.chStateName;
            }},
        {field:'ext1Name', width:30, title:'HIS渠道', sortable:false},
        {field:'ext1', width:30, title:'HIS渠道', hidden:true},
        {field:'validDate', title:'接入号源有效期', width:30, sortable:true},
        {field:'createDate', title:'接入号源时间', width:30, sortable:true},
        {field:'token', title:'平台认证token', width:30, sortable:true},
        /*{field:'appId', title:'appId', width:15, sortable:true}*/

    ]]
    if(isSetting){
        columns[0].push({field:'设置',
            title:'接口权限设置',
            width:30,
            formatter : function(value,row,index){
                var chId = row.chId;
                var token = row.token;
                var hosId = row.hosId;
                var chState = row.chState;
                if(chState == 'state_0'){ //渠道状态:停用
                    return '';
                }else{
                    return '<a data-chId='+chId+' data-token='+token+' data-hosId='+hosId+' class="row-btn"  style="cursor:pointer;color:blue;">设置</a>';
                }
                //return '<a data-chId='+chId+' data-token='+token+' data-hosId='+hosId+' class="row-btn"  style="cursor:pointer">设置</a>';
            }
        })
    }
    $(dataTable).datagrid({
        url:'list.json',
        title: "渠道信息",
        loadMsg:'数据加载中,请稍后......',
        border:true,
        fitColumns:true,
        remoteSort:false,
        toolbar: "#button-bar",
        singleSelect : false,
        rownumbers: true,
        showFooter: true,
        pagination:true,
        nowrap : true,//把数据显示在一行里
        pageSize : 10, // 页大小
        pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
        striped : true, // 行背景交换
        idField : 'chId', // 主键
        fitColumns:true,
        columns: columns
    });
    //	隐藏表格头部选择框
    //	$(dataTable).datagrid('hiddenHeaderChecked', false);

});
