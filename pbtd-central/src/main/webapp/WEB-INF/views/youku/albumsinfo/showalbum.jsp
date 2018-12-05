<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p id="ptag" style="display:none;">${albumjson}</p>
<pre style="width:100%;height:100%;" id="data-test"></pre>
<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	 var text = document.getElementById('ptag').innerText; //获取json格式内容
	 var jsonStr = JSON.stringify(JSON.parse(text), null, 2);//将字符串转换成json对象	
	 //document.getElementById('data-test').innerText= formatJson(jsonStr);
	 formatJson(jsonStr,'','data-test');
	// 工具方法
	 function formatJson(json, options,preid) {
	                     var reg = null,
	                         formatted = '',
	                         pad = 0,
	                         PADDING = '    '; // one can also use '\t' or a different number of spaces
	                     // optional settings
	                     options = options || {};
	                     // remove newline where '{' or '[' follows ':'
	                     options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
	                     // use a space after a colon
	                     options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

	                     // begin formatting...

	                     // make sure we start with the JSON as a string
	                     if (typeof json !== 'string') {
	                         json = JSON.stringify(json);
	                     }
	                     // parse and stringify in order to remove extra whitespace
	                     json = JSON.parse(json);
	                     json = JSON.stringify(json);

	                     // add newline before and after curly braces
	                     reg = /([\{\}])/g;
	                     json = json.replace(reg, '\r\n$1\r\n');

	                     // add newline before and after square brackets
	                     reg = /([\[\]])/g;
	                     json = json.replace(reg, '\r\n$1\r\n');

	                     // add newline after comma
	                     reg = /(\,)/g;
	                     json = json.replace(reg, '$1\r\n');

	                     // remove multiple newlines
	                     reg = /(\r\n\r\n)/g;
	                     json = json.replace(reg, '\r\n');

	                     // remove newlines before commas
	                     reg = /\r\n\,/g;
	                     json = json.replace(reg, ',');

	                     // optional formatting...
	                     if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
	                         reg = /\:\r\n\{/g;
	                         json = json.replace(reg, ':{');
	                         reg = /\:\r\n\[/g;
	                         json = json.replace(reg, ':[');
	                     }
	                     if (options.spaceAfterColon) {
	                         reg = /\:/g;
	                         json = json.replace(reg, ': ');
	                     }

	                     $.each(json.split('\r\n'), function(index, node) {
	                         var i = 0,
	                             indent = 0,
	                             padding = '';

	                         if (node.match(/\{$/) || node.match(/\[$/)) {
	                             indent = 1;
	                         } else if (node.match(/\}/) || node.match(/\]/)) {
	                             if (pad !== 0) {
	                                 pad -= 1;
	                             }
	                         } else {
	                             indent = 0;
	                         }

	                         for (i = 0; i < pad; i++) {
	                             padding += PADDING;
	                         }

	                         formatted += padding + node + '\r\n';
	                         pad += indent;
	                     });

	                     //return formatted;
	                     document.getElementById(preid).innerText=formatted;
	                 };
</script>
</body>
</html>