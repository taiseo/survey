$(document).ready(function(){
	attachActiveToNav();
	initTinyMce();
	initDatepicker();
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
		var datepicker = $('.datepicker').datepicker({
			format: 'yyyy-mm-dd'
		});
		datepicker.on('changeDate', function(){
			datepicker.datepicker('hide');
		});
	}
}