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
	tinymce.init({
		selector: '.wysiwyg'
	});
}

function initDatepicker(){
	var datepicker = $('.datepicker').datepicker({
		format: 'yyyy-mm-dd'
	});
	datepicker.on('changeDate', function(){
		datepicker.datepicker('hide');
	});
}