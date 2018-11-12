/*
	主页加载方法
 */

var classId = '#tabs';
var jq = top.jQuery;

$(function() {
	$('.easyui-opentab').click(function() {
		openTab(this);
	});
});

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

function openTab(ele)
{
	var subtitle = $(ele).attr('title');
	var url = $(ele).attr('url');
	if (subtitle == null || subtitle == undefined)
	{
		$.messager.alert("警告", "无法打开tab页。<br />请在触发点击事件的元素上增加 title 属性，并设置唯一的标题。");
		return false;
	}
	if (url == null || url == undefined)
	{
		$.messager.alert("警告", "无法打开tab页。<br />请在触发点击事件的元素上增加 url 属性，并设置唯一的路径。");
		return false;
	}
	
	if ($("body").find(classId).length == 0)
	{
		openOrSelectTabInParent(subtitle, url);
	}
	else
	{
		openOrSelectTab(subtitle, url);
	}
}

function closeTab()
{
	if ($("body").find(classId).length == 0)
	{
		closeTabInParent();
	}
	else
	{
		closeTabInThis();
	}
}

//##############################################如果 tabs处于父框架页面####################################################
function openOrSelectTabInParent(subtitle, url)
{
	if (!jq(classId).tabs('exists', subtitle)) {
		jq(classId).tabs(
			'add',
			{
				fit : true,
				title : subtitle,
				content : '<iframe src="'+ url + '" frameBorder="0" border="0" scrolling="yes" style="width:100%; height:100%"/>',
				closable : true,
				tools:[{
					iconCls:'icon-mini-refresh',
					handler:function(){
						updateTabInParent(url,subtitle);
					}
				}]
			});
		return false;
	} else {
		jq(classId).tabs('select', subtitle);
		return false;
	}
}

function updateTabInParent(url, subtitle){
//	jq(classId).tabs('select', subtitle);
	var tab = jq(classId).tabs('getSelected');  // 获取选择的面板
	var title = tab.panel('options').title;
	var content = tab.panel('options').content;
	jq(classId).tabs('update', {
		tab: tab,
		options : {
			title : title,
			content : content,
			tools : {}
		}
	});
}

function closeTabInParent()
{
	var tab = jq(classId).tabs('getSelected');
	var index = jq(classId).tabs('getTabIndex',tab);
	jq(classId).tabs('close',index);
}



//##############################################如果 tabs处于当前页面####################################################
function openOrSelectTab(subtitle, url)
{
	if (!$(classId).tabs('exists', subtitle)) {
		$(classId).tabs(
			'add',
			{
				fit : true,
				title : subtitle,
				content : '<iframe src="'+ url + '" frameBorder="0" border="0" scrolling="yes" style="width:100%; height:100%"/>',
				closable : true,
				tools:[{
					iconCls:'icon-mini-refresh',
					handler:function(){
						updateTab(url,subtitle);
					}
				}]
			});
		return false;
	} else {
		$(classId).tabs('select', subtitle);
		return false;
	}
}

function updateTab(url, subtitle){
	var tab = $(classId).tabs('getSelected');  // 获取选择的面板
	var title = tab.panel('options').title;
	var content = tab.panel('options').content;
	jq(classId).tabs('update', {
		tab: tab,
		options : {
			title : title,
			content : content,
			tools : {}
		}
	});
}

function closeTabInThis()
{
	var tab = $(classId).tabs('getSelected');
	var index = $(classId).tabs('getTabIndex',tab);
	$(classId).tabs('close',index);
}

$(function(){
    
	//tabs绑定右键菜单
    $(".tabs-header").bind('contextmenu',function(e){
        e.preventDefault();
        $('#rcmenu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    });
    //关闭当前标签页
    $("#closecur").bind("click",function(){
        var tab = $(classId).tabs('getSelected');
        var index = $(classId).tabs('getTabIndex',tab);
        $(classId).tabs('close',index);
    });
    //关闭所有标签页
    $("#closeall").bind("click",function(){
        var tablist = $(classId).tabs('tabs');
        for(var i=tablist.length-1;i>=0;i--){
            $(classId).tabs('close',i);
        }
    });
    //关闭非当前标签页（先关闭右侧，再关闭左侧）
    $("#closeother").bind("click",function(){
        var tablist = $(classId).tabs('tabs');
        var tab = $(classId).tabs('getSelected');
        var index = $(classId).tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
            $(classId).tabs('close',i);
        }
        var num = index-1;
        for(var i=num;i>=0;i--){
            $(classId).tabs('close',0);
        }
    });
    //关闭当前标签页右侧标签页
    $("#closeright").bind("click",function(){
        var tablist = $(classId).tabs('tabs');
        var tab = $(classId).tabs('getSelected');
        var index = $(classId).tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
            $(classId).tabs('close',i);
        }
    });
    //关闭当前标签页左侧标签页
    $("#closeleft").bind("click",function(){
        var tab = $(classId).tabs('getSelected');
        var index = $(classId).tabs('getTabIndex',tab);
        var num = index-1;
        for(var i=0;i<=num;i++){
            $(classId).tabs('close',0);
        }
    });
     
});

