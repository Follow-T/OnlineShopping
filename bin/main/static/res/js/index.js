var url=window.location.href;
var type;
if(url.search("=")==-1){
	type="all";
}else{
	type=url.split("=")[1];
}
$("#"+type).css("color","#5FB878");

var canshu=url.split("/")[3];
if(canshu.search("index")!=-1){
	$("#index").css("display","block");
}
if(canshu.search("commodity")!=-1){
	$("#commodity").css("display","block");
}
if(canshu.search("registershop")!=-1){
	$("#regStore").css("display","block");
}
if(canshu.search("my")!=-1){
	$("#my").css("display","block");
}
if(url.search("trolley")!=-1){
	$("#trolley").css("display","block");
}
