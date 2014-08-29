$(document).ready(function(){
	attachActiveToNav();
});

function attachActiveToNav(){
	$('.navbar li a').each(function(){
		if(location.href.indexOf($(this).attr('href')) > -1){
			$(this).parents('li').addClass('active');
		}
	});
}