$(function(){
    $.extend({
        showProgress:function(opts){
            var op = $.extend({
                loadMsg:"数据处理中，请稍后...",
                panel:document.body
            },opts);
            var _1cb = $(op.panel);
            if(!_1cb.children("div.datagrid-mask").length){
                $("<div class=\"datagrid-mask\" style=\"display:block\"></div>").appendTo(_1cb);
                var msg=$("<div class=\"datagrid-mask-msg\" style=\"display:block;left:50%\"></div>").html(op.loadMsg).appendTo(_1cb);
                msg._outerHeight(40);
                msg.css({marginLeft:(-msg.outerWidth()/2),lineHeight:(msg.height()+"px")});
            }
        },
        hideProgress:function(wrap){
            var _1cc = $(wrap);
            _1cc.children("div.datagrid-mask-msg").remove();
            _1cc.children("div.datagrid-mask").remove();
        }
    });

    //全部科室相关不可用
    $("#allDeptCheckBox").change(function() {

        if(this.checked){
            $("#deptId").combogrid({
                disabled:true
            });

            $("#docmId").combogrid({
                disabled:true
            });
            $("#shiftId").combogrid({
                disabled:true
            });
            $("#schmWeek").combogrid({
                disabled:true
            });
            $("#allDocmCheckBox").attr("disabled",true);
            $("#allShiftCheckBox").attr("disabled",true);
            $("#allSchmWeekCheckBox").attr("disabled", true);
        }else{
            $("#deptId").combogrid({
                disabled:false
            });

            $("#docmId").combogrid({
                disabled:false
            });
            $("#shiftId").combogrid({
                disabled:false
            });
            $("#schmWeek").combogrid({
                disabled:false
            });
            $("#allDocmCheckBox").attr("disabled",false);
            $("#allShiftCheckBox").attr("disabled", false);
            $("#allSchmWeekCheckBox").attr("disabled", false);

        }
    });

    //选择科室，选择医生不可用
    // $('#deptId').combogrid({
    //     onChange: function (n,o) {
    //         $("#docmId").combogrid({
    //             disabled:true
    //         });
    //     }
    // });



    //选择医生，选择科室不可用
    // $('#docmId').combogrid({
    //     onChange: function (n,o) {
    //         $("#deptId").combogrid({
    //             disabled:true
    //         });
    //     }
    // });


    //全部医生相关不可用
    $("#allDocmCheckBox").change(function() {

        if(this.checked){
            $("#docmId").combogrid({
                disabled:true
            });
            $("#allDeptCheckBox").attr("disabled",true);
            $("#allShiftCheckBox").attr("disabled",true);
            $("#allSchmWeekCheckBox").attr("disabled", true);
        }else{

            $("#docmId").combogrid({
                disabled:false
            });
            $("#allDeptCheckBox").attr("disabled",false);
            $("#allShiftCheckBox").attr("disabled",false);
            $("#allSchmWeekCheckBox").attr("disabled", false);

        }
    });

    //全部班次相关不可用
    $("#allShiftCheckBox").change(function () {
            if(this.checked){
                $("#shiftId").combogrid({
                    disabled:true
                });
                $("#allDeptCheckBox").attr("disabled",true);
                $("#allSchmWeekCheckBox").attr("disabled",true);
                $("#allDocmCheckBox").attr("disabled", true);
            }else{
                $("#shiftId").combogrid({
                    disabled:false
                });
                $("#allDeptCheckBox").attr("disabled",false);
                $("#allSchmWeekCheckBox").attr("disabled",false);
                $("#allDocmCheckBox").attr("disabled", false);
            }
        }
    );

    //全部星期相关不可用
    $("#allSchmWeekCheckBox").change(function () {
        if(this.checked){
            $("#schmWeek").combogrid({
                disabled:true
            });
            $("#allDeptCheckBox").attr("disabled",true);
            $("#allShiftCheckBox").attr("disabled",true);
            $("#allDocmCheckBox").attr("disabled", true);
        }else{
            $("#schmWeek").combogrid({
                disabled:false
            });
            $("#allDeptCheckBox").attr("disabled",false);
            $("#allShiftCheckBox").attr("disabled",false);
            $("#allDocmCheckBox").attr("disabled", false);
        }
    });
    //安排下周相关不可用
    $("#nextWeek").change(function() {
        if(this.checked){
            searchNextWeekDate();
            $("#startDate").attr("disabled",true);
            $("#endDate").attr("disabled",true);
        }else{
            $("#startDate").attr("disabled",false);
            $("#endDate").attr("disabled",false);
        }
    });

    //自定义相关不可用
    $("#userdefine").change(function() {
        $("#startDate").val("");//清空
        $("#endDate").val("");//清空
        $("#startDate").attr("disabled",false);
        $("#endDate").attr("disabled",false);
    });



    //保存提交
    $("#saveAutoSchmBtn").click(
        function()
        {
            var alldept="";
            var alldocm="";
            var deptid="";
            var docmid="";
            var nextWeek="";
            var userdefine="";
            var startDate="";
            var endDate="";
            var allshift = "";
            var shiftId = "";
            var allWeek = "";
            var schmWeek = "";
            //所有被选中

//				$("#allDeptCheckBox").attr('checked', true);

            if($("#allDeptCheckBox")[0].checked){
                alldept="1";
            }else{
                alldept="0";
            }
            if($("#allDocmCheckBox")[0].checked){
                alldocm="1";
            }else{
                alldocm="0";
            }
            if($("#allShiftCheckBox")[0].checked){
                allshift = "1";
            }else {
                allshift = "0";
            }
            if($("#allSchmWeekCheckBox")[0].checked){
                allWeek = "1";
            }else {
                allWeek = "0";
            }

            //选择医生
            var grid = $("#docmId").combogrid("grid");// 获取表格对象
            var row = grid.datagrid('getSelected');// 获取行数据
            if(row!=null){
                docmid = row.docmId;
            }
            //选择科室

            grid = $("#deptId").combogrid('grid');	// 获取表格对象
            row = grid.datagrid('getSelected');// 获取行数据
            if (row != null) {
                deptid = row.deptId;
            }

            //选择班次

            grid = $("#shiftId").combogrid("grid");// 获取表格对象
            row = grid.datagrid('getSelected');// 获取行数据
            if (row != null) {
                shiftId = row.shiftId;
            }

            //选择星期
            // grid = $("#schmWeek").combogrid("grid");// 获取表格对象
            // row = grid.datagrid('getSelected');// 获取行数据
            // if (row != null) {
            //     schmWeek = row.schmWeek;
            // }


                schmWeek = $("#schmWeek").combobox("getValue");// 获取表格对象



            //安排下周被选中
            if($("#nextWeek")[0].checked){
                nextWeek = "1";
            }else{
                //清空起始日期结束日期
                startDate="";
                endDate="";
                nextWeek = "0";
            }
            //自定义被选中
            if($("#userdefine")[0].checked){
                startDate = $("#startDate").val();
                endDate = $("#endDate").val();
                if(startDate == null || startDate==""){
                    $.messager.alert('警告','请选择起始日期!');
                    return;
                }

                if(endDate == null || endDate==""){
                    $.messager.alert('警告','请选择终止日期!');
                    return;
                }


                if(endDate<startDate){
                    $.messager.alert('警告','请选择终止日期大于起始日期!');
                    return;
                }
                userdefine="1";
            }else{
                startDate="";
                endDate="";
                userdefine="0";
            }


            if(nextWeek=="0" && userdefine=="0"){
                $.messager.alert('警告','请选择安排下周或者自定义!');
                return;
            }

            if(alldept=="0" && alldocm=="0" && docmid=="" && deptid=="" && shiftId=="" && allshift=="0" && schmWeek=="" && allWeek=="0"){
                $.messager.alert('警告','请在"科室、医生、班次、星期、全部科室、全部医生、全部班次、全部星期"中至少选择一个！' );
                return;
            }



            var json={alldept:alldept,alldocm:alldocm,deptid:deptid,docmid:docmid,nextWeek:nextWeek,userdefine:userdefine,startDate:startDate,endDate:endDate,allshift:allshift,shiftId:shiftId,schmWeek:schmWeek,allWeek:allWeek};


            $.showProgress({
                panel:"#autoSchmWindow"
            });
            $.ajax({
                url : 'auto_schm.json',
                type : 'POST',
                data:json,
                dataType : 'JSON',
                success : function(data)
                {
                    $.hideProgress("#autoSchmWindow");
                    if(data=="suuce"){
                        $.messager.alert('提示信息','数据保存成功','info');
                        $("#dg").datagrid('load', $("#searchform").toJson());

                    }else if(data=="failBase"){
                        $.messager.alert('提示信息','没有相关基础排班','info');
                    }



                },
                error : function (XMLHttpRequest, textStatus, errorThrown) {
                    $.hideProgress("#autoSchmWindow");
                    alert(textStatus);
                }
            });

        }
    );

    //重置
    $("#canselAutoSchmBtn").click(function(){
        $("#docmId").combogrid('clear');// 清空选择表格数据
        $("#deptId").combogrid('clear');	// 清空选择tree数据
        $("#shiftId").combogrid('clear');
        $("#schmWeek").combogrid('clear');
        $("#startDate").val("");//清空
        $("#endDate").val("");//清空
        //重置可用

        $("#docmId").combogrid({
            disabled:false
        });

        $("#deptId").combogrid({
            disabled:false
        });

        $("#shiftId").combogrid({
            disabled: false
        });
        $("#schmWeek").combogrid({
            disabled: false
        });

        $("#allDeptCheckBox").attr("disabled",false);
        $("#allDocmCheckBox").attr("disabled",false);
        $("#allShiftCheckBox").attr("disabled",false);
        $("#allSchmWeekCheckBox").attr("disabled",false);


        $("#allDeptCheckBox").attr("checked",false);
        $("#allDocmCheckBox").attr("checked",false);
        $("#allShiftCheckBox").attr("checked", false);
        $("#allSchmWeekCheckBox").attr("checked", false);

        $("#nextWeek").attr("checked",false);
        $("#userdefine").attr("checked",false);

        $("#startDate").attr("disabled",false);
        $("#endDate").attr("disabled",false);



    });


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





});

//安排下周相关,服务端获取下周的日期范围，不能再客户端获得，单机修改日期后，日期获取不正确
var searchNextWeekDate = function () {
    $.ajax({
        url : 'nextWeekDate.json',
        type : 'POST',
        dataType : 'JSON',
        success : function(data)
        {
            if($("#nextWeek").is(':checked')){
                $("#startDate").my97('setValue',data.startDate);//清空
                $("#endDate").my97('setValue',data.endDate);//清空
            }
        },
        error : function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
        }
    });
}

