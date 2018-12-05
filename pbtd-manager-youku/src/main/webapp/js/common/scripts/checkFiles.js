var max_size,max_width,max_heigth;
var img_obj;
//var extend_name = new Array(".jpg",".gif",".xls",".doc",".txt",".ppt",".png",".rar",".zip"); //文件格式
var extend_name = new Array(".jpg",".gif",".bmp",".zip",".png",".pmd"); //文件格式
max_size=2048; //最大2M
max_width=800;    //图片宽度
max_height=1280; //图片长度
/*---------------------------------------------------
函数名:checkFileSize
函数功能:检测传的文件大小是否不超过max_size
参数说明:path 传文件的绝对路径 类型为String
返回值: true 没超过 false 超过
---------------------------------------------------*/
function checkFileSize(path)
{
var fso;
var file_obj;
fso= new ActiveXObject("Scripting.FileSystemObject")
if(fso.fileExists(path))
{
      file_obj=fso.getFile(path);
      if(file_obj.size > max_size)
      {  
          return false;
      }
      else
          return true;   
}
else  
     return false;
}
/*-----------------------------------------------
函数名:chkImgSize
函数功能:检测传入图片的宽高是否不超出变量max_width和max_height设定的值
参数说明:obj 传入图片标签对象
返回值: 没有返回值
------------------------------------------------*/
function chkImgSize(obj)
{
var path;
path=obj.value;
if(img_obj) 
     img_obj.removeNode(true);
img_obj=document.createElement("img");
img_obj.style.position="absolute";
img_obj.style.visibility="hidden";  
img_obj.attachEvent("onerror",function(){oe(obj)});
img_obj.attachEvent("onreadystatechange",function () {orsc(obj)});
document.body.insertAdjacentElement("beforeEnd",img_obj);
img_obj.src=path;

}
function oe(obj)
{
alert("图片不能正常打开，请检查！");
obj.outerHTML=obj.outerHTML;
}
function orsc(obj)
{ 
if(img_obj.readyState!="complete")
{
  
   setTimeout("orsc()",1000,obj);  
   //obj.outerHTML=obj.outerHTML;
}
else
{  
   if(img_obj.clientWidth > max_width || img_obj.clientHeight > max_height)
   {
   
    alert("图片尺寸超出规定范围,请把图片尺寸调整到"+max_width+"*"+max_height+"以内再上传！");
    obj.outerHTML=obj.outerHTML;
    return false;
   }
  
}
}
/*-----------------------------------------------------------------------
函数名:chkExtend(path)
函数功能:检测传入的文件是否合法
参数说明:path 文件绝对路径
返回值说明:0 为成功 1 为在数组中没有找到该文件夹的扩展名 2 为取扩展名失败.
--------------------------------------------------------------------------*/
function chkExtend(path)
{
var i,n;
n=path.substring(path.length-4,path.length);
n=n.toLowerCase();
if(n == "")
   return 2;
for(i=0;i<extend_name.length;i++)
{
   if(extend_name[i]==n)
    return 0;
}
return 1;
}

/*--------------------------------------------------
函数名：isImg
函数功能：检测参数传入的文件名是否为.gif或.jpg
--------------------------------------------------*/
function isImg(path)
{
var n;
n=path.substring(path.length-4,path.length);
n=n.toLowerCase();
if(n!=".jpg")
{  
   if(n!=".gif")
   {
	   	if(n!=".bmp")
	   	{
		   	n="";
		  }
   }    
}
if(n=="")
   return false;
else
   return true;
}
/*--------------------------------------------------
函数名：isZip
函数功能：检测参数传入的文件名是否为.rar或.zip
--------------------------------------------------*/
function isZip(path)
{
var n;
n=path.substring(path.length-4,path.length);
n=n.toLowerCase();
if(n!=".rar")
{
   if(n!=".zip")
    n="";
}
if(n!="")
   return true;
else
   return false;
}


function checkFile(obj)
{
   if(isZip(obj.value))
   {  //20M
       max_size=20480;
}
   else  
       max_size=2048;  //2M
  
   if(chkExtend(obj.value)!=0)
   {
    alert("文件格式不允许上传，请您检查！");
    obj.outerHTML=obj.outerHTML;
    return false;
   }
/*   
   if(isImg(obj.value))
   {
    chkImgSize(obj);   
   }  
 */ 
  /* if(!checkFileSize(obj.value))
   {
    alert("文件大小超出了"+max_size/1024+"kb\n请使用WinRAR或WinZip软件压缩后再试！");
    obj.outerHTML=obj.outerHTML;
    return false;
   } */
  
}