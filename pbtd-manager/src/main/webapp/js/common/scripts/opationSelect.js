var fromsob, toobj, froms, arry, nowIndex, newoption;
/** 单元素向上移动 */
function moveUp(sid) {
	var objSelect = document.getElementById(sid);
	if (objSelect.value == "") {
		alert("请选则一个要上移的负责人");
		return;
	} else {
		var i = objSelect.options.selectedIndex;
		var j = i - 1;
		if (i > 0) {
			var Temp_Text = objSelect.options[j].text;
			var Temp_ID = objSelect.options[j].value;
			objSelect.options[j].text = objSelect.options[i].text;
			objSelect.options[j].value = objSelect.options[i].value;
			objSelect.options[j + 1].text = Temp_Text;
			objSelect.options[j + 1].value = Temp_ID;
			objSelect.selectedIndex = j;
		}
	}
}
/** 单元素向下移动 */
function moveDown(sid) {
	var objSelect = document.getElementById(sid);
	if (objSelect.value == "") {
		alert("请选则一个要下移的负责人");
		return;
	} else {
		var i = objSelect.options.selectedIndex;
		if (i != objSelect.length - 1) {
			var j = i + 1;
			if (i < objSelect.length) {
				var Temp_Text = objSelect.options[j].text;
				var Temp_ID = objSelect.options[j].value;
				objSelect.options[j].text = objSelect.options[i].text;
				objSelect.options[j].value = objSelect.options[i].value;
				objSelect.options[i].text = Temp_Text;
				objSelect.options[i].value = Temp_ID;
				objSelect.selectedIndex = j;
			}
		}
	}
}
// 删除选项
function deleteOption(sid) {
	var objSelect = document.getElementById(sid);
	if (objSelect.value == "") {
		alert("请选则一个要删除的负责人");
		return;
	} else {
		var i = objSelect.selectedIndex;
		objSelect.remove(i);
	}
}
// -->
