$(document).ready(function(){
	attachActiveToNav();
	initTinyMce();
	initDatepicker();
	initOnlyNumber();
});

function attachActiveToNav(){
	$('.navbar li a').each(function(){
		if(location.href.indexOf($(this).attr('href')) > -1){
			$(this).parents('li').addClass('active');
		}
	});
}

function initTinyMce(){
	if($('.wysiwyg').length > 0){
		tinymce.init({
			selector: '.wysiwyg'
		});
	}
}

function initDatepicker(){
	if($('.datepicker').length > 0){
		$('.datepicker').datepicker({
			language: "kr",
			autoclose: true,
			format: 'yyyy-mm-dd'
		});
	}
}

function deinitDatepicker(){
	if($('.datepicker').length > 0){
		$('.datepicker').datepicker('remove');
	}
}

function initOnlyNumber(){
	$('body').on('blur', '.js-number', function(){
		$(this).val($(this).val().replace(/[^\-0-9]/g, ''));
	});
}

function mynoty(text, option){
	var defaults = {
		layout: 'topRight',
		type: 'success',
		timeout: 2000,
		maxVisible: 10,
		text: text
	};
	var option = $.extend(defaults, option);
	noty(option);
}