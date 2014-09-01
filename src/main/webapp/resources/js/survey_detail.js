$(document).ready(function(){
	bind_add_question();
	bind_add_response_item();
	bind_sortable();
	bind_remove();
	bind_save();
});

function bind_add_question(){
	$('.js-add-question').click(function(){
		var title = '<h2>' + $(this).val() + '</h2>';
		var template_id = $(this).data('template-id');
		$('.js-questions-area').append($($('#' + template_id).html()).prepend(title));
		
		bind_sortable_response_items();
	});
}

function bind_add_response_item(){
	$('.js-questions-area').on('click', '.js-add-reponse-item', function(){
		var template_id = $(this).data('template-id');
		$(this)
			.parents('.question')
			.find('.js-response-items-area')
			.append($('#' + template_id).html());
	});
	
}

function bind_sortable(){
	$('.js-questions-area').sortable({
		// TODO DB 순서 변경 로직 추가
		handle: '.js-move-question, h2'
	});
}

function bind_sortable_response_items(){
	$('.js-questions-area .js-response-items-area').sortable({
		handle: '.js-move-response-item'
	});
}

function bind_remove(){
	$('.js-questions-area').on('click', '.js-remove-question', function(){
		// TODO DB 삭제 로직 추가
		$(this).parents('.question').remove();
	});
}

function bind_save(){
	$('.js-questions-area').on('blur', '.question-form input', function(){
		var $question_form = $(this).parents('.question-form');
		var $question = $(this).parents('.question');
		var question_obj = $question_form.serializeObject();
		
		// id가 있으면 수정. 이 경우 REST API에 맞게 HTTP method를 PUT으로 보낸다.
		if(question_obj.id){
			question_obj._method = 'PUT'; 
		}
		
		// 순서를 정한다.
		question_obj.orderNo = $question.index('.question');
		
		$.post(survey.context_path + '/questions', question_obj, function(insertedQuestion){
			$question_form.find('[name=id]').val(insertedQuestion.id);
			mynoty(insertedQuestion.content + ' 질문 관련 정보를 저장했습니다.');
		}, 'json');
	});
	
	$('.js-questions-area').on('blur', '.response-item-form input', function(){
		var $question_form = $(this).parents('.question-form');
		var $question = $(this).parents('.question');
		var question_obj = $question_form.serializeObject();
		
		// id가 있으면 수정. 이 경우 REST API에 맞게 HTTP method를 PUT으로 보낸다.
		if(question_obj.id){
			question_obj._method = 'PUT'; 
		}
		
		// 순서를 정한다.
		question_obj.orderNo = $question.index('.question');
		
		$.post(survey.context_path + '/questions', question_obj, function(insertedQuestion){
			console.log(insertedQuestion);
			$question_form.find('[name=id]').val(insertedQuestion.id);
		}, 'json');
	});
}