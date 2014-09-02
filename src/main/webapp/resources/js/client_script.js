$(document).ready(function(){
	bind_page_button();
});

function bind_page_button(){
	$('.js-prev').click(function(){
		show_page('prev');
	});
	$('.js-next').click(function(){
		show_page('next');
	});
//	$('.nav a').click(function(){
//		var page_no = $(this).index('.nav a') + 1;
//		show_page(page_no)
//	});
}

function show_page(keyword){
	var $current = $('.questions-block:visible');
	var page_no, $prev, $next, $target;
	
	$('.js-prev, .js-next').show();
	
	if(keyword == 'prev'){
		$prev = $('.questions-block:visible').prev();
		if($prev.length > 0){
			$current.slideUp();
			$prev.slideDown();
		}
		page_no = $prev.index('.questions-block') + 1;
	}
	if(keyword == 'next'){
		$next = $('.questions-block:visible').next();
		if($next.length > 0){
			$current.slideUp();
			$next.slideDown();
		}
		page_no = $next.index('.questions-block') + 1;
	}
//	if(parseInt(keyword) !== NaN){
//		page_no = keyword;
//		$target = $('.questions-block:nth-child(' + page_no + ')');
//		$current.
//		$current.slideUp();
//		$target.slideDown();
//	}
	if(page_no == 1){
		$('.js-prev').hide();
	}
	if(page_no == ($('.questions-block').length) ){
		$('.js-next').hide();
	}
	$('.nav li').removeClass('active');
	$('.nav li:nth-child(' + page_no + ')').addClass('active');
}