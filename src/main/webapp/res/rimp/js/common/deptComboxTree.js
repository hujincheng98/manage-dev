
/**
 * 医生信息表单公共js
 * 页面中需包含id为"_common_dept_comboxtree"的input元素
 */
// function initDeptTree() {
//     $("._common_dept_comboxtree").each(function(index, data){
//         $("._common_dept_comboxtree").combotree({
//             width : 170,
//             panelWidth:200,
//             editable:true,
//             url: platRoot+"/rimp/dept/list_deptTree.json"
//         });
//     });
// };

/**
 * 科室信息表单公共js
 * 页面中需包含id为"_common_dept_comboxgrid"的input元素
 */
$(function () {

    $("._common_dept_comboxtree").each(function (index, data) {
        $("._common_dept_comboxtree").combotree({
            width : 170,
            panelWidth:200,
            editable:true,
            url: platRoot+"/rimp/dept/list_deptTree.json"
        });
    });

});