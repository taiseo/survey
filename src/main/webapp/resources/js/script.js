$(document).ready(function(){
	attach_active_to_nav();
	init_tinymce();
	init_datepicker();
	init_only_number();
	init_underscore_template();
});

function attach_active_to_navNav(){
	$('.navbar li a').each(function(){
		if(location.href.indexOf($(this).attr('href')) > -1){
			$(this).parents('li').addClass('active');
		}
	});
}

function init_tinymcee(){
	if($('.wysiwyg').length > 0){
		tinymce.init({
			selector: '.wysiwyg'
		});
	}
}

function init_datepicker(){
	if($('.datepicker').length > 0){
		$('.datepicker').datepicker({
			language: "kr",
			autoclose: true,
			format: 'yyyy-mm-dd'
		});
	}
}

function init_only_number(){
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

function init_underscore_template(){
	_.templateSettings = {
	    interpolate: /\<\@\=(.+?)\@\>/gim,
	    evaluate: /\<\@(.+?)\@\>/gim,
	    escape: /\<\@\-(.+?)\@\>/gim
	};
}