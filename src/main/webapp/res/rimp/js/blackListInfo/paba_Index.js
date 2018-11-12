$(function(){
    var dataTable = $("#tableList");

    /**
     * 查询
     */
    $("#search").linkbutton({
        onClick:function(){
            var startDate = $("#startDate").datebox('getValue');
            var endDate = $("#endDate").datebox('getValue');
            if (startDate != '') {
                startDate += " 00:00:00";
            }
            if (endDate != '') {
                endDate += " 23:59:59";
            }
            $(dataTable).datagrid('load', {
                'searchParams[pabaPatientName]' : $("#serpabaPatientName").val(),
                'searchParams[pabaPatientCard]' : $("#serpabaPatientCard").val(),
                'searchParams[pabaPatientState]' : $('#serpabaPatientState').combobox('getValue'),
                'searchParams[pabaOffStartDate]' :  startDate,
                'searchParams[pabaOffEndDate]' : endDate
            });
        }
    });
    /**
     * 重置
     */
    $("#reset").click(function(){
        $("#serpabaPatientState").combobox('clear');// 清空选择表格数据
        $("#searchform").form('clear');
        $(dataTable).datagrid("load",{});
//		$("#resetform").click(); 
    });
    //锁定
    $("#enabled").click(function(){
        var rowData = $(dataTable).datagrid("getSelections");
        if(!rowData){
            $.messager.alert("提示", "请选择要操作的行！",'warning');
            return;
        }
        for (var i = 0; i < rowData.length; i++){
            if(rowData[i].pabaPatientState==="PABA_STATE_0"){
                $.messager.alert("提示", "选中的行有已锁定患者信息",'warning');
                return;
            }
        }

        $.messager.confirm('提示:','你确认要锁定当前人员吗?',function(event){
            if(event){
                IsEnabled("PABA_STATE_0");
            }
        });
    });
    //解锁
    $("#disEnabled").click(function(){
        //判断是否已是解锁状态
        var rowData = $(dataTable).datagrid("getSelections");
        if(!rowData){
            $.messager.alert("提示", "请选择要操作的行！",'warning');
            return;
        }
        for (var i = 0; i < rowData.length; i++){
            if(rowData[i].pabaPatientState==="PABA_STATE_1"){
                $.messager.alert("提示", "选中的行有已解锁患者信息",'warning');
                return;
            }
        }
        $.messager.confirm('提示:','你确认要解锁当前人员吗?',function(event){
            if(event){
                IsEnabled("PABA_STATE_1");
            }
        });
    });
    //启用停用
    function IsEnabled(state){
        var rows = $("#tableList").datagrid("getSelections");

        if (rows != null && rows.length>0){
            var ids = '';
            for (var i = 0; i < rows.length; i++) {
                if (i == rows.length - 1) {
                    ids += rows[i].pabaPatientId;
                } else {
                    ids += rows[i].pabaPatientId + ',';
                }
            }
            var json={"pabaPatientIds":ids,"pabaPatientState":state};
            $.post("update.do",json, function(result) {
                if(result.state==0){
                    $(dataTable).datagrid('load',{});
                    $(dataTable).datagrid("clearSelections");
                    $.messager.alert('提示','操作成功','info');
                }else{
                    $.messager.alert('错误', '操作失败', 'error');
                }
            });
        }else{
            $.messager.alert("提示", "请选择要操作的行。");
        }
    }
});

$(function (){
    $('#tableList').datagrid('hiddenHeaderChecked', false);
});
