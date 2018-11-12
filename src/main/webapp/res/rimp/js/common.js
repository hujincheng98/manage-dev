
/**
 * 回车键自动变tab键 
 */
$(function(){
	/*$("body").live("keydown",function(e){	
		var mykey=0;
		if(window.event)
			mykey = window.event.keyCode;
		else if (e.which)
			mykey = e.which;
		if(mykey==13){
			var srcobj = e.target;
			 if(srcobj.type!='button' && srcobj.type!='submit' &&srcobj.type!='reset' 
				&& srcobj.type!='textarea' && srcobj.type!=''){
				 var el=getNextElement(e.target);
				 if(el!=null){
					 if (el.type!='hidden')
						 el.focus();
					 else
						 while (el.type=='hidden')
							 el=getNextElement(el);
					 el.focus();	
				 }				 			 
				 return false; 
			 }
		}
	});
	
	function getNextElement (field) {
		var form = field.form;
		if(form==null)
			return null;
		for (var e = 0; e < form.elements.length; e++) { 
			if (field == form.elements[e])
				break;
		}
		return form.elements[++e % form.elements.length];
	}*/
});

$.extend($.fn.datagrid.defaults.editors, {
	combogrid: {
		init: function(container, options){
			var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container); 
			input.combogrid(options);
			return input;
		},
		destroy: function(target){
			$(target).combogrid('destroy');
		},
		getValue: function(target){
			return $(target).combogrid('getValue');
		},
		setValue: function(target, value){
			$(target).combogrid('setValue', value);
		},
		resize: function(target, width){
			$(target).combogrid('resize',width);
		}
	},
	datebox: {  
        init: function(container, options){  
            var input = $("<input type='datebox' class='easyui-my97' />").appendTo(container);
            if (options != undefined)
        	{
            	input.my97(options);
        	}
            return input;  
        },  
        getValue: function(target){  
            return $(target).my97('getValue');  
        },  
        setValue: function(target, value){
        	if (value != undefined)
        	{
        		$(target).my97('setValue', value);
        	}
        },  
        resize: function(target, width){  
            var input = $(target);  
            if ($.boxModel == true){  
                input.width(width - (input.outerWidth() - input.width()));  
            } else {  
                input.width(width);  
            }  
        }  
    },
    readonlytext:{
    	init: function(container, options){   
    		var input = $('<input type="text" style="border:none;background:transparent;" readonly="readonly">').appendTo(container);  
            return input;   
        },   
        getValue: function(target){   
            return $(target).val();   
        },   
        setValue: function(target, value){   
            $(target).val(value);   
        },   
        resize: function(target, width){   
            var input = $(target);   
            if ($.boxModel == true){   
                input.width(width - (input.outerWidth() - input.width()));   
            } else {   
                input.width(width);   
            }   
        }   
    }   
});



/**
 * 获取地址栏参数的值
 * @param name
 * @returns
 */
function getURLParameter(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
