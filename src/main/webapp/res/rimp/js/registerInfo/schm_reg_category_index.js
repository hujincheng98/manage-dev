$(function (){
    var dataTable = '#schmregcategory';
    var editIndex = undefined; // 当前编辑行
    /**
     * 按钮定义
     */
    $btnAdd = $("#addCategory").linkbutton({
        onClick:function() {
            add();
        }
    });//新增按钮
    $btnEdit = $("#editCategory").linkbutton({
        onClick:function() {
            edit();
        }
    });//修改按钮
    $btnSave = $("#save").linkbutton({
        disabled:true,
        onClick:function() {
            save();
        }
    });//保存按钮

    //删除按钮
    $btnDel = $("#delete").linkbutton({
        onClick:function () {
          deleted();
        }
    });//删除按钮

    $btnCancel = $("#cancel").linkbutton({
        disabled:true,
        onClick:function() {
            $.messager.confirm('提示:','您确认要取消吗?',function(r){
                if(r){
                    cancel();
                }
            });
        }
    });//取消按钮

    $btnSearch = $("#btnSearch").linkbutton({
        onClick:function() {
            $(dataTable).datagrid('load', $('#searchform').toJson());
        }
    });//查询按钮

    $btnReset= $("#btnReset").linkbutton({
        onClick:function() {
            $('#searchform').form('clear');
            $(dataTable).datagrid('load', $('#searchform').toJson());
        }
    });//重置按钮

    function btnNormalMode(){
        $btnAdd.linkbutton("enable");
        $btnEdit.linkbutton("enable");
        $btnDel.linkbutton('enable');
        $btnSave.linkbutton("disable");
        $btnCancel.linkbutton("disable");
    }
    $(dataTable).datagrid({
        title: "挂号类别管理",
        fit: true,//缩放窗口的时候适应
        rownumbers: true,
        singleSelect: false,
        nowrap: true,//把数据显示在一行里
        url: 'list.json', // 请求的数据源
        pagination: true, // 显示分页
        pageSize: 10, // 页大小
        pageList: [10, 20, 30, 40, 50], // 页大小下拉选项此项各value是pageSize的倍数
        striped: true, // 行背景交换
        idField: 'id', // 主键
        remoteSort: false,//前端排序这个是必须的
        toolbar: "#button-bar",
        onAfterEdit : onAfterEdit,
        onBeginEdit:function(index,row){
            $btnEdit.linkbutton("disable");
            $btnAdd.linkbutton("disable");
            $btnSave.linkbutton("enable");
            $btnCancel.linkbutton("enable");
            $btnDel.linkbutton("disable");
        },
        onCancelEdit:function(index,row){
            btnNormalMode();
        },
        columns: [[// 显示的列
            {
                field: 'id',
                title: '',
                checkbox: true
            }, {
                field: 'createDate',
                sortable: true,
                hidden: true//用来默认排序的
            }, {
                field: 'regcategory',
                title: '挂号类别',
                width: '16%',
                sortable: true,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
                    }
                }
            }, {
                field: 'servcoding',
                title: '业务系统编码',
                width: '17%',
                sortable: true,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
                    }
                }
            }, {
                field: 'regfee',
                title: '挂号费',
                width: '12%',
                sortable: true,
                editor: {
                    type: 'numberbox',
                  	options : {value:"0", min:0, precision:2,required:true }
                }
            }, {
                field: 'fee',
                title: '诊金',
                width: '26%',
                sortable: true,
                editor: {
                    type: 'numberbox',
                    options: { value: "0", min: 0, precision: 2, required: true }
                }
            }, {
                field: 'flatfee',
                title: '工本费',
                width: '26%',
                sortable: true,
                editor: {
                    type: 'numberbox',
                    options: { value: "0", min: 0, precision: 2, required: true }
                }
            }]]
    });
    /**
     * 结束编辑事件
     */
    function onAfterEdit(rowIndex, rowData, changes) {
        var inserted = $(dataTable).datagrid('getChanges', 'inserted');
        var updated = $(dataTable).datagrid('getChanges', 'updated');
        if (inserted.length < 1 && updated.length < 1) {
            editIndex = undefined;
            $(dataTable).datagrid('unselectAll');
            return;
        }
        var url = '';
        if (inserted.length > 0) {
            url = 'save.json';
        }
        if (updated.length > 0) {
            url = 'update.json';
        }
        $.ajax({
            url : url,
            data : JSON.stringify(rowData),
            type: 'POST',
            dataType : 'json',
            contentType : 'application/json',
            success : function(r) {
                if (r.state == 'success') {
                    btnNormalMode();
                    $.messager.alert('提示','操作成功','info',function (){
                        $(dataTable).datagrid('acceptChanges');
                        editIndex = undefined;
                        $(dataTable).datagrid('reload');
                    });
                } else {
                    $.messager.alert('错误', r.message, 'error');
                    $(dataTable).datagrid('beginEdit', rowIndex);
                    editIndex = rowIndex;
                }
                $(dataTable).datagrid('unselectAll');
            }
        });

    }
    /**
     * 新增
     * @returns
     */
    function add(){
        if(editIndex != undefined) {
            $.messager.alert('提示', "请先保存或取消正在编辑的行",'warning');
            return;
        } else {
            $(dataTable).datagrid('insertRow',{
                index: 0,	// index start with 0
                row: {}
            });
            $(dataTable).datagrid('beginEdit', 0);
            editIndex = 0;
        }
    }
    /**
     * 修改
     * @returns
     */
    function edit() {
        if(editIndex != undefined) {
            $.messager.alert('提示', '请先保存或取消正在编辑的行', 'warning');
            return;
        } else {
            var row = $(dataTable).datagrid("getSelected");
            if (row) {
                var rowIndex = $(dataTable).datagrid("getRowIndex", row);
                $(dataTable).datagrid('beginEdit', rowIndex);
                /*var pabaRuleType = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'pabaRuleType'});
                var hosId = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'hosId'});
                pabaRuleType.target.combobox({ disabled: true });
                hosId.target.combobox({ disabled: true });*/
                editIndex = rowIndex;
            } else {
                $.messager.alert('提示', '请选择要修改的数据.', 'warning');
            }
        }
    }

    /**
     * 删除
     */
    function deleted() {
        var rows = $(dataTable).datagrid("getSelections");
        console.log(rows);
        if(rows.length ==0){
            $.messager.alert('提示','请选择要删除的数据.');
            return;
        }
        var ids = '';
        for (var i = 0; i < rows.length; i++) {
            if (i == rows.length - 1) {
                ids += rows[i].id;
            } else {
                ids += rows[i].id + ',';
            }
        }
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
            if (r){
                $.ajax({
                    url : 'delete.json',
                    cache : true,
                    type : 'POST',
                    data: {'ids' : ids},
                    dataType: 'json',
                    async : false,
                    success : function(data)
                    {
                        $(dataTable).datagrid("clearSelections");
                        if (data.state == 'success') {
                            $.messager.alert("提示", data.message, 'info', function (){
                                $(dataTable).datagrid("reload");
                            });
                        } else  if (data.state == 'fail') {
                            $.messager.alert("提示", data.message, 'info');
                        } else {
                            $.messager.alert('错误', data.message, 'error');
                        }
                    }
                });
            }
        });
    }
    /**
     * 取消
     */
    function cancel() {
        if (editIndex == undefined) {
            $.messager.alert('提示', "没有要取消的行数据",'warning');
            return;
        } else {
            editIndex = undefined;
            $(dataTable).datagrid("rejectChanges");
            $(dataTable).datagrid("unselectAll");
        }
    }
    /**
     * 保存
     */
    function save() {
        if (editIndex == undefined) {
            $.messager.alert('提示', "没有要保存的行数据",'warning');
            return;
        } else {
            if ($(dataTable).datagrid('validateRow', editIndex)) {
                $(dataTable).datagrid('acceptChanges');
                editIndex = undefined;
            }
        }
    }
    $(dataTable).datagrid('hiddenHeaderChecked', false);
});