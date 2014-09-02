$(document).ready(function(){
	bind_page_button();
	bind_save();
	bind_etc_response();
});

function bind_etc_response(){
	$('.js-etc').keyup(function(){
		$(this).prev().val($(this).val());
	});
}

function bind_save(){
	$('.client-question input[type=text], .client-question textarea').on('blur', function(){
		save(this);
	});
	$('.client-question input[type=radio], .client-question input[type=checkbox]').on('click', function(){
		save(this);
	});
}

function save(input_obj){
	$question = $(input_obj).parents('.client-question');
	var params = {
		'questionId': $question.data('id'),
		'respondent': $('.client').data('client'),
		'response': get_content($question)
	};
	if(params.response == '$$$etc$$$'){
		return false;
	}
	$.post(survey.context_path + '/responses', params, function(data){
		console.log(data);
	});
}

function get_content($question){
	var type = $question.data('type');
	switch(type){
		case '주관식1':
		case '주관식2':
			return $question.find('[name=content]').val();
			break;
		case '객관식1':
		case '점수범위':
			return $question.find('[name=content]:checked').val();
			break;
		case '객관식2':
			var contents = [];
			$question.find('[name=content]:checked').each(function(i, el){
				contents.push($(el).val());
			});
			return contents;
	}
}

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