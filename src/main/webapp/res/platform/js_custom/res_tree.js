var type_sys = "SYS";
var type_res = "RES";

$(document).ready(function(){
	$('#treeDemo').tree({
		url:'root.json',
		lines : true,
		onClick : treeOnClick,
	    loadFilter: function(data)
	    {
	    	return parseSysTree(data);
	    }
	});
});

function reloadTree()
{
	$('#treeDemo').tree("reload");
}

function treeOnClick(node)
{
	if (node.attributes.type == type_sys)
	{
		$("#centerFrame").attr("src", "edit.do?sysId="+node.id);
	}	
	if (node.attributes.type == type_res)
	{
		$("#centerFrame").attr("src", "edit.do?resId="+node.id);
	}	
}


function parseSysTree(data)
{
	var childNodes = new Array();
	for (var i=0, l=data.length; i<l; i++) {
		childNodes[i] = {};
		childNodes[i].id = data[i].sysId;
		childNodes[i].iconCls = "icon-sys";
		childNodes[i].text = data[i].sysName.replace(/\.n/g, '.');
		childNodes[i].attributes = {"type": type_sys};
		if (data[i].resources.length > 0)
		{
			childNodes[i].children = parseResTree(data[i].resources);
		}
	}
	return childNodes;
}

function parseResTree(data)
{
	var childNodes = new Array();
	for (var i=0, l=data.length; i<l; i++) {
		childNodes[i] = {};
		childNodes[i].id = data[i].resId;
		childNodes[i].text = data[i].resName.replace(/\.n/g, '.');
		childNodes[i].attributes = {"type": type_res};
		if (data[i].childrenMenus.length > 0)
		{
			childNodes[i].children = parseResTree(data[i].childrenMenus);
		}
	}
	return childNodes;
}