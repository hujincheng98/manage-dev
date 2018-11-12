var dataTable = '#dg';

/**
 * 查询按钮
 * @returns
 */
var _search = function () {
    $(dataTable).datagrid('load', $('#searchform').toJson());
    $('#report').linkbutton('enable');
}


/**
 * 导出按钮
 * @returns
 */
var report = function (url) {
    $(dataTable).datagrid('load', $('#searchform').toJson());
    var data = $(dataTable).datagrid('getData');
    if (data && data.rows && data.rows.length > 0) {
        url += "?page=1&rows=10000&" + $('#searchform').serialize();
        console.log(url);
        window.location.href = url;
    } else {
        $.messager.alert("提示", "数据列表为空，不能导出!", "error");
    }
}

$(function(){
    var nowdays = new Date();
    var year = nowdays.getFullYear();
    var month=nowdays.getMonth()+1;
    month =(month<10 ? "0"+month:month);
    if (month == 0) {
        month = 12;
        year = year - 1;
    }
    var firstDay = year + "-" + month + "-" + "01";
    var myDate = new Date(year, month, 0);
    var lastDay = year + "-" + month + "-" + myDate.getDate();
    $('#schmStartDate').datebox('setValue', firstDay);
    $('#schmEndDate').datebox('setValue', lastDay);

    $("#showTimesBtn").click(function(){
        var rows = $("#dg").datagrid("getSelections");
        if(rows.length>1){
            $.messager.alert("提示", "请选择要一条查看的记录.");
            return;
        }
        if (rows != null && rows.length > 0)
        {
            if (rows[0].schmId != null || rows[0].schmId != undefined)
            {
                $(this).attr("url", platRoot+"/rimp/schm/showTimesEdit.do?schmId=" + rows[0].schmId);
                $(this).attr("title", "排班 - 时段查看");
                openTab(this);

            }
        }
        else
        {
            $.messager.alert("提示", "请先选择要查看的记录.");
        }
    });
    $("#schmEdit").click(function () {
        window.localStorage.removeItem ("schmId");
        var rows = $("#dg").datagrid("getSelections");
        if(rows.length>1){
            $.messager.alert("提示", "请选择一条要查看的记录.");
            return;
        }
        if (rows != null && rows.length > 0) {
            if (rows[0].schmId != null || rows[0].schmId != undefined) {
               if(rows[0].schmStateName=="停用"){
                   $.messager.alert("提示", "该排班已停用不可修改！");
               } else {
                   $('#editSchmWindow').window('open');
                   $('#editSchmWindow').window('center');
                   var data = { "schmId": rows[0].schmId };
                   window.schmId = rows[0].schmId;
                   $.ajax({
                       cache: false,
                       url: 'select.json',
                       type: 'POST',
                       data: data,
                       async: false,
                       success: function (data) {
                           console.log(data);
                           $("#editForm").form('load', data);
                           $("#docmId1").combogrid("setValue", data.docmName);
                           $("#docmIdtocontext").val(data.docmId);
                           $("#docmTitle").val(data.docmTitleName);
                           $("#schmDeptIDtocontext").val(data.schmDeptId);
                           $("#shiftId1").val(data.shiftName);
                           $("#servcoding").val(data.servCoding);
                           $("#regcategoryName").combogrid("setValue", data.regcategory);
                           $("#regcategoryIDtocontext").val(data.regCategoryId)
                           console.log($("#regcategoryName").val());
                       }
                   });
               }
            }
        }
        else {
            $.messager.alert("提示", "请先选择要修改的记录.");
        }
    });
    //转换函数
    function formatterWeek(w) {
        var x;
        switch (w) {
            case "星期日":x=7;
                break;
            case "星期一":x=1;
                break;
            case "星期二":x=2;
                break;
            case "星期三":x=3;
                break;
            case "星期四":x=4;
                break;
            case "星期五":x=5;
                break;
            case "星期六":x=6;
                break;
        };
        return x
    }
    $(dataTable).datagrid({
        url:'list.json',
        type : 'POST',
        queryParams:$("#searchform").toJson(),
        title: "排班列表",
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
        pageList : [ 10, 20, 30, 40, 50 ,100,500,1000,5000], // 页大小下拉选项此项各value是pageSize的倍数
        striped : true, // 行背景交换
        idField : 'schmId', // 主键
        columns:[[
            {field:'schmId', title:'排班编号', checkbox:true, width:10},
            {field:'schmDate', title:'排班日期', width:15, sortable:true},
            {title:'星期', field:'schmWeek', width:10, sortable:true,sorter:function(a,b){
                    return formatterWeek(a)>formatterWeek(b)?1:-1
                }},
            {title:'医生', field:'docmName', width:10, sortable:true},
            {title:'职称', field:'docmTitleName', width:10, sortable:true},
            {title:'排班科室', field:'schmDeptName', width:10, sortable:true},
            {title:'所属科室', field:'deptName', width:10, sortable:true},
            {title:'班次', field:'shiftName', width:10, sortable:true},
            {title:'开始时间', field:'schmOnWork', width:10, sortable:true},
            {title:'截止时间', field:'schmOffWork', width:10, sortable:true},
            {title:'总限数', field:'schmRegiSum', width:10, sortable:true},
            {title:'线上挂号限数', field:'schmOnRegiSum', width:10, sortable:true},
            {title:'线上已挂号数', field:'schmRegiOnNum', width:10, sortable:true},
            {title:'线下挂号限数', field:'schmDownRegiSum', width:10, sortable:true},
            {title:'线下已挂号数', field:'schmRegiDownNum', width:10, sortable:true},
            {title:'线上预约限数', field:'schmOnSum', width:10, sortable:true},
            {title:'线上已预约数', field:'schmUpreNum', width:10, sortable:true},
            {title:'线下预约限数', field:'schmDownSum', width:10, sortable:true},
            {title:'线下已预约数', field:'schmDownreNum', width:10, sortable:true},
            {title:'挂号类别', field:'regcategory', width:10, sortable:true},
            {title:'状态', field:'schmStateName', width:10, sortable:true,
                formatter: function(value,row,index){
                    if (row.schmState == 'state_0')
                        return "<span title='红色为停用状态'><font color='red'>"+ row.schmStateName + "</font></span>";
                    else
                        return row.schmStateName;
                }}
        ]],
        onLoadSuccess:function(){
            $('#dg').datagrid("clearSelections");
        }

    });


    var showTimesTable = $("#showTimesTable");
    $(showTimesTable).edatagrid({
        url:'',
        title: "时段明细列表",
        loadMsg:'数据加载中,请稍后......',
        border:true,
        fitColumns:true,
        remoteSort:false,
        singleSelect : true,
        toolbar : '#tool-bar',
        rownumbers: false,
        showFooter: true,
        columns:[[
            {field:'shiftId', hidden:true},
            {field:'orgId', hidden:true},
            {field:'remarks', hidden:true},
            {field:'createDate', hidden:true},
            {field:'createUser', hidden:true},
            {field:'updateDate', hidden:true},
            {field:'updateUser', hidden:true},
            {field:'tislId',title:'选择', checkbox:true},
            {field:'tislStartDate',title:'时段起始时间', editor:{type:'datebox', options : {required:true, dateFmt:'H:mm', minDate:'#F{$dp.$D(\'shiftStartDate\')}', maxDate:'#F{$dp.$D(\'shiftEndDate\')||\'18:00\'}'}}, width:"20%"},
            {field:'tislEndDate',title:'时段终止时间', editor:{type:'datebox', options : {required:true, dateFmt:'H:mm', minDate:'#F{$dp.$D(\'shiftStartDate\')}', maxDate:'#F{$dp.$D(\'shiftEndDate\')||\'18:00\'}'}}, width:"20%"},
            {field:'tislOnline',title:'线上可预约限数百分比(%)', editor:{type:'numberbox', options : {value:0, min:1,  precision:0}}, width:"22%"},
            {field:'tislOffline',title:'线下可预约限数百分比(%)', editor:{type:'numberbox', options : {value:0, min:1,  precision:0}}, width:"22%"}
        ]]
    });


    $("#addTimesBtn").click(
        function()
        {
            $(showTimesTable).datagrid("appendRow", {});
            var editIndex = $(showTimesTable).datagrid("getRows").length-1;
            //$(dataTable).datagrid("selectRow", editIndex);
            $(showTimesTable).datagrid("beginEdit", editIndex);
        }
    );

    //重置
    $("#clearBtn").click(function(){
        $("#_common_docm_comboxgrid").combogrid('clear');// 清空选择表格数据
        $("#_common_dept_comboxgrid").combogrid('clear');	// 清空选择tree数据
        $('#schmStartDate').combo("setValue","");
        $('#schmStartDate').combo("setText","");
        $('#schmEndDate').combo("setValue","");
        $('#schmEndDate').combo("setText","");
    });

    //启用提交
    $("#OnEnable").click(
        function()
        {
            var rows = $("#dg").datagrid("getSelections");
            if(rows == null){
                return;
            }
            var ids = '';
            for(var i = 0; i < rows.length; i++){
                if (rows[i].schmState == 'state_1') {
                    $.messager.alert('提示','该排班已启用','warning');
                    return;
                }
                if (i == rows.length - 1) {
                    ids += rows[i].schmId;
                } else {
                    ids += rows[i].schmId + ',';
                }
            }
            var data={"schmId":ids,"schmState":"state_1"};
            $.messager.confirm("警告", "您确定要启用所选择的记录?", function(isTrue){
                if(isTrue){
                    $.ajax({
                        url : 'update.json',
                        type : 'POST',
                        data:data,
                        dataType : 'JSON',
                        success : function(data)
                        {
                            $('#dg').datagrid("clearSelections");
                            $('#dg').datagrid("reload");

                        },
                        error : function (XMLHttpRequest, textStatus, errorThrown) {
                            alert(textStatus);
                        }
                    });
                }
            });

        }
    );


    //停用提交
    $("#OffEnable").click(
        function()
        {
            var rows = $("#dg").datagrid("getSelections");
            if(rows == null){
                return;
            }
            var ids = '';
            for(var i = 0; i < rows.length; i++){
                if (rows[i].schmState == 'state_0') {
                    $.messager.alert('提示','该排班已停用','warning');
                    return;
                }
                if (i == rows.length - 1) {
                    ids += rows[i].schmId;
                } else {
                    ids += rows[i].schmId + ',';
                }
            }
            var data={"schmId":ids,"schmState":"state_0"};
            $.messager.confirm("警告", "您确定要停用所选择的记录?", function(isTrue){
                if(isTrue){
                    $.ajax({
                        url : 'update.json',
                        type : 'POST',
                        data:data,
                        dataType : 'JSON',
                        success : function(data)
                        {
                            $('#dg').datagrid("clearSelections");
                            $('#dg').datagrid("reload");
                        },
                        error : function (XMLHttpRequest, textStatus, errorThrown) {
                            alert(textStatus);
                        }
                    });
                }
            });
        }
    );

    /**
     * 删除
     */
    $("#deleteBtn").click(function(){

        var rows = $("#dg").datagrid("getSelections");
        if (rows != null && rows.length>0){
            var ids = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    ids += rows[i].schmId;
                } else {
                    ids += rows[i].schmId + ',';
                }
            }
            $.messager.confirm("警告", "您确定要删除所选择的记录?", function(isTrue){
                if(isTrue){
                    var data = {"ids" : ids};
                    $.ajax({
                        url : 'delete.json',
                        type : 'POST',
                        data : data,
                        async : false,
                        success : function(data){
                            if (data.state == 'success') {
                                $('#dg').datagrid("clearSelections");
                                $('#dg').datagrid("reload");
                            } else {
                                $.messager.alert("警告", data.message, "warning");
                            }
                        }
                    });
                }
            });
        }else{
            $.messager.alert("提示", "请选择要删除的行。");
        }
    });
    /**
     * 批量删除
     */
    $("#deleteBatchBtn").click(function(){
        var json = $("#searchform").toJson();
        $.ajax({
            url : 'listSchm.json',
            type : 'POST',
            data : json,
            success : function(data)
            {
                var rows = data;
                if (rows != null && rows.length>0){
                    var schmId = '';
                    for (var i = 0; i < rows.length; i++) {
                        if (i == rows.length - 1) {
                            schmId += rows[i].schmId;
                        } else {
                            schmId += rows[i].schmId + ',';
                        }
                    }
                    $.messager.confirm("警告", "批量删除后，请在HIS系统中删除对应排班数据。", function(isTrue){
                        if(isTrue){
                            $.messager.confirm("警告", "您确定要删除查询到的记录?", function(isTrue){
                                if(isTrue){
                                    var data = {"schmId" : schmId};
                                    $.ajax({
                                        url : 'deleteBatch.json',
                                        type : 'POST',
                                        data : data,
                                        async : false,
                                        success : function(data){
                                            if (data.state == 'success') {
                                                $('#dg').datagrid("reload");
                                                // $('#dg').datagrid("load", data);
                                            } else {
                                                $.messager.alert("警告", data.message, "warning");
                                            }
                                        }
                                    });
                                }
                            });
                        }else {
                            return;
                        }
                    });
                }else{
                    $.messager.alert("提示", "暂无符合条件的医生排班！");
                }
            }
        });
    });

    //查询
    $("#searchBtn").click(function(){
        var json = $("#searchform").toJson();
        $.ajax({
            url : 'list.json',
            type : 'POST',
            data : json,
            success : function(data)
            {
                $('#dg').datagrid("load", json);
            }
        });
    });

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

});




// 重新加载数据源
function reload() {
    $('#deptquery').combogrid('grid').datagrid('reload');
}

// 设置select中的值
function setValue() {
    $("#deptquery").combogrid("setValue", "003");
}
// 获取下拉列表中选择的值
function getValue() {
    var val = $('#deptquery').combogrid('getValue');
}
// 禁用组件
function disable() {
    $('#deptquery').combogrid('disable');
}
// 启用组件
function enable() {
    $('#deptquery').combogrid('enable');
}
// 获取选中的表格的数据
function getGridValue() {
    var grid = $("#deptquery").combogrid("grid");// 获取表格对象
    var row = grid.datagrid('getSelected');// 获取行数据
    alert("选择的grid中的数据如下：code:" + row.code + " name:" + row.name + " addr:"
        + row.addr + " col4:" + row.col4);
}
function clear() {
    $("#deptquery").combogrid("clear");
}