console.log("this is script file");

const toggleSidebar = () => {
	if($(".sidebar").is(":visible")) {
		//true
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}else{
		//false
		$(".sidebar").css("display","block");
		$(".content").css("margint-left","20%");
		
	}
}