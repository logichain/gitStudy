 
var onFinalCheck = false;

var fieldTypedef=
	["任意类型",		"整型","实数",	"字母","日期[YYYY-MM-DD 或者 YYYY/MM/DD]","时间[hh:mm:ss]"];
function checkNullField(str){
	if(str == "" || str == null || str.lenght == 0)
		return true;
	else
	  return false;
}

function getStrLength(str){
	var len = 0;
	var ch;
	if(str==null)
		return len;
	for(var i = 0; i<str.length;i++){
		ch=str.charCodeAt(i);
		if(ch>255)
			len+=3;
		else
			len++;
	}
	return len;
}
function isDigital(str){
	var j=0;
	var ch="";
	//str=trim(str);
	//alert(333);
	if (str.length==0) return false;
  for (var i=0;i<str.length;i++){
  	ch=str.charAt(i);
    if (ch=="."){
    	j++;
    	if (j>1){
    		return false;
    	}
    }else if ((parseInt(ch)+"")=="NaN"){
    	return false;
    }
  }
  return true;
}

function isInteger(val){
	for (i=0;i<val.length;i++){
		p = val.charAt(i);
		if (p<'0'||p>'9')
			return	false;
	}
	return	true;
}

function checktailNum(str,fieldType,tailNum){

	var j=0;
	var ch="";
	var a=0;
	if(fieldType!=2)
		return true;
	str=trim(str);
	if (str.length==0) return false;
  for (var i=0;i<str.length;i++){
  	ch=str.charAt(i);
    if (ch=="."){
    	j++;
    }
		if (j>1){
    	a++;
    }
  }
  if(a>tailNum)
  	return false;
  else
  	return true;
}
function isLetter(val){
	var str = val.toUpperCase();
	for (i=0;i<val.length;i++){
		p = val.charAt(i);
		if (p<'A'||p>'Z')
			return	false;
	}
	return	true;
}

function checkFieldfieldType(str,fieldType){
	if(fieldType==0)
		return true;
	if(fieldType==1){
		if(isInteger(str))
			return true;
		else
			return false;
	}
	if(fieldType==2){
		if(isDigital(str))
			return true;
		else
			return false;
	}
	if(fieldType==3){
			if(isLetter(str))
				return true;
			else
				return false;
	}
	if(fieldType==4){
			if(checkDateStr(str))
				return true;
			else
				return false;
	}
	if(fieldType==5){
			if(checkTimeStr(str))
				return true;
			else
				return false;
	}
}
function checkMaximunLength(str,len,fieldType){
	if(getStrLength(str)>len){
	  return false;
	}
	else
	{
	  return true;
	}
}

function IsNum(str){
	var j=0;
	var ch="";
	str=trim(str);
	if (str.length==0) return false;
  for (var i=0;i<str.length;i++){
  	ch=str.charAt(i);
    if (ch=="."){
    	j++;
    	if (j>1){
    		return false;
    	}
    }else if ((parseInt(ch)+"")=="NaN"){
    	return false;
    }
  }
  return true;
}

      function checkDateStr(date_str) {
        var rc = true;
        var date_arr = new Array();
        var year,month,day;

        if (date_str.indexOf("-") != -1)
            date_arr = date_str.split("-");
        else if(date_str.indexOf("/") != -1)
            date_arr = date_str.split("/");
        else
            return false;

        if (date_arr.length!=3){
            return false;
        }

        if (date_arr[0]==''||date_arr[0].length>4||date_arr[1]==''||date_arr[2]==''){
            return false;
        }


        if (IsNum(date_arr[0])) year = parseInt(date_arr[0], 10);
        else return false;

        if (IsNum(date_arr[1])) month = parseInt(date_arr[1], 10);
        else return false;

        if (IsNum(date_arr[2])) day = parseInt(date_arr[2], 10);
        else return false;

        if(year<1900 || year>2030)
            rc = false;
        if( (month < 1) || (month > 12) || (day < 1) || (day > 31) )
            return false;

        switch (month) {
        case 1 :
        case 3 :
        case 5 :
        case 7 :
        case 8 :
        case 10:
        case 12:
        if (day >31)
        rc = false;
        break;

        case 4 :
        case 6 :
        case 9 :
        case 11:
        if (day >30)
        rc = false;
        break;

        case 2 :
        if ( (year%4 == 0) && (year%100 != 0) || (year%400 == 0) ) {
        if( day > 29)
            rc = false;
        }
        else {
        if( day >28)
            rc = false;
        }
        break;

        default :
            rc = false;
        }

        return rc;
    }

      function checkTimeStr(time_str) {
        var rc = true;
        var time_arr = new Array();
        var year,month,day;
        if (time_str.indexOf(":") != -1)
            time_arr = time_str.split(":");
        else
            return false;

        if (time_arr.length!=3){
            return false;
        }

        if (time_arr[0]==''||time_arr[0].length>2||time_arr[1]==''||time_arr[2]==''){
            return false;
        }


        if (IsNum(time_arr[0])) hour = parseInt(time_arr[0], 10);
        else return false;

        if (IsNum(time_arr[1])) minute = parseInt(time_arr[1], 10);
        else return false;

        if (IsNum(time_arr[2])) second = parseInt(time_arr[2], 10);
        else return false;

        if( (second < 0) || (second > 59) || (minute < 0) || (minute > 59) || (hour < 0) || (hour > 23) )
            return false;


        return rc;
    }

    //--------------------ltrim(str)函数----------------------------
	 function ltrim(str){
		str=str+"";
		if (str.length==0){
			return str;
		}
		var i=0;
		while ((i<str.length)&&(str.substring(i,i+1)==" ")) i++;
		return str.substring(i,str.length);
	 }
//--------------------rtrim(instr)函数----------------------------
	 function rtrim(instr){
		instr=instr+"";
		var last_space;
		var ret;
		last_space = instr.length;
		while ((instr.charAt(last_space - 1 ) == " " )&&(last_space > 0)) {
		  last_space --;
		}

		if (last_space==0){
			return ""
		}else{
			return instr.substring( 0, last_space );
		}
	 }
//--------------------trim(str)函数----------------------------

       function trim(s){
           return   ltrim(rtrim(s));
       }

//str为待校验的值
//len 为str允许的最大长度
//fiedlType 为str的类型。其中fieldType＝1，为整型；fieldType＝2，为实型；fieldType＝3，大写A——Z之间的字母；fieldType＝4，为Data型；fieldType＝5，为Time型
//checkNull 为str是否可以为Null
//tailNum为小数的精度
//canNull一般可默认为0
//name字段的中文名称
function basecheck(str,len,fieldType,tailNum,checkNull,name,canNull){
	var msg ="";
	if(str!="" || canNull==0){
		if(checkNull!=0){
			if(checkNullField(str)){
				msg =name+"不允许为空";
			}
		}
		if(!checktailNum(str,fieldType,tailNum))
			msg = name+"输入精度错误，小数位数应为:";
		if(!checkMaximunLength(str,len,fieldType))
			msg =name+"长度超过限制:长度应为"+len+"个字符或"+Math.floor(len/3)+"个汉字";
	//	if(!checkNullField(str.length))
	//		msg =name+"不允许为空";
		if(!checkFieldfieldType(str,fieldType))
			msg =name+"类型输入错误，类型应为:"+fieldTypedef[fieldType];
		if(msg.length>0){
			alert(msg);
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}



//校验控件的值
//obj为目标表单
function checkForm(obj)
{
    var formElements = obj.elements;
    for (var idx=0;idx<formElements.length;idx++){
      if(formElements[idx].type!="hidden" && formElements[idx].type!="button"){
        if(!basecheck(formElements[idx].value,formElements[idx]._maxlength,formElements[idx]._type,0,formElements[idx]._isnull,formElements[idx]._name,0)){
          formElements[idx].focus();
          return false;
          break;
        }
      }
    }
}

//禁掉所有控件
//obj为目标表单
function disabledAllObj(obj){
  for(var j=0;j<obj.all.length;j++){
    if(obj.all[j].tagName=="INPUT" || obj.all[j].tagName =="A" || obj.all[j].tagName=="SELECT"){
      obj.all[j].disabled = true;
      obj.all[j].style.cursor='wait';
    }
  }
}

//给下拉框装值
//str 为值，obj为下拉框对象
function inputValue(str,obj){
			for(var idx=0;idx<obj.options.length;idx++){
				if(obj.options[idx].value == str){
					obj.options[idx].selected=true;
				}
			}	
}
//为表单中每个元素清值
function resetValue(formObj){
	var obj = formObj.elements;
	for(var i=0;i<obj.length;i++){
		if(obj[i].type!="button" || obj[i].type!="hidden"){
			obj[i].value="";
		}
	}
}

//初始化年度

function initNd(obj){
	var dd = new Date();
	var month = String(dd.getMonth()+1);
	var year = String(dd.getYear());
	var yearNum=parseInt(year);
  
	for(var i=yearNum-20;i<yearNum+20;i++) {
		var oOption = document.createElement("OPTION");
		oOption.value=i;
		oOption.text=i;
		if(i==year){
			oOption.selected=true;
		}
		obj.add(oOption);
	}
}

